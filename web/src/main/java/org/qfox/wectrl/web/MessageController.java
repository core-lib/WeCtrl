package org.qfox.wectrl.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.qfox.jestful.client.Client;
import org.qfox.jestful.client.scheduler.Callback;
import org.qfox.jestful.core.annotation.*;
import org.qfox.wectrl.common.weixin.aes.SHA1;
import org.qfox.wectrl.common.weixin.aes.WXBizMsgCrypt;
import org.qfox.wectrl.core.base.App;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.base.Verification;
import org.qfox.wectrl.core.weixin.User;
import org.qfox.wectrl.core.weixin.message.*;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.base.VerificationService;
import org.qfox.wectrl.service.transaction.SessionProvider;
import org.qfox.wectrl.service.weixin.TokenService;
import org.qfox.wectrl.service.weixin.UserService;
import org.qfox.wectrl.service.weixin.WeixinMessageService;
import org.qfox.wectrl.service.weixin.cgi_bin.WeixinCgiBinAPI;
import org.qfox.wectrl.web.handler.EventHandler;
import org.qfox.wectrl.web.handler.MessageHandler;
import org.qfox.wectrl.web.msg.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import rx.Observable;
import rx.schedulers.Schedulers;

import javax.annotation.Resource;
import java.io.StringReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangchangpei on 17/3/1.
 */
@Jestful("/message")
@Controller
public class MessageController implements ApplicationContextAware {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Map<Class<?>, List<MessageHandler>> messageHandlers = new HashMap<>();
    private final Map<Class<?>, List<EventHandler>> eventHandlers = new HashMap<>();

    @Resource
    private ApplicationService applicationServiceBean;

    @Resource
    private VerificationService verificationServiceBean;

    @Resource
    private WeixinMessageService weixinMessageServiceBean;

    @Resource
    private TokenService tokenServiceBean;

    @Resource
    private UserService userServiceBean;

    @Resource
    private SessionProvider defaultSessionProvider;

    private final Client client = Client.builder().setContentCharsets("UTF-8").addPlugins("characterEncodingPlugin; charset=UTF-8").build();

    private final XmlMapper mapper = new XmlMapper() {
        {
            this.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
            this.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
            this.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
            this.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
            this.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
            this.configure(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY, false);
            this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            this.configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false);
        }
    };

    @GET("/")
    public String verify(@Query("signature") String signature,
                         @Query("timestamp") String timestamp,
                         @Query("nonce") String nonce,
                         @Query("echostr") String echostr,
                         Application app) {

        boolean verified = false;
        try {
            if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce) || StringUtils.isEmpty(echostr)) {
                throw new IllegalArgumentException("signature/timestamp/nonce/echostr must not null or empty");
            }

            String sign = SHA1.sign(app.getToken(), timestamp, nonce);
            if (!sign.equals(signature)) {
                throw new IllegalArgumentException("incorrect signature");
            }

            verified = true;
            return "@:" + echostr;
        } catch (Exception e) {
            verified = false;
            logger.error("error occurred when verifying : {}", e);
            return "@:";
        } finally {
            if (verified) {
                applicationServiceBean.updateToVerified(app.getAppID());
            }

            Verification verification = new Verification();
            verification.setApplication(app == null ? null : new App(app));
            verification.setMerchant(app == null ? null : app.getMerchant());
            verification.setSignature(signature);
            verification.setTimestamp(timestamp);
            verification.setNonce(nonce);
            verification.setEchostr(echostr);
            verification.setToken(app == null ? null : app.getToken());
            verification.setEncoding(app == null ? null : app.getEncoding());
            verification.setSuccess(verified);
            verificationServiceBean.save(verification);
        }
    }

    @POST(value = "/", consumes = "application/xml")
    public String receive(@Query("signature") String signature,
                          @Query("timestamp") String timestamp,
                          @Query("nonce") String nonce,
                          @Query("encrypt_type") String encryptType,
                          @Query("msg_signature") String msgSignature,
                          @Body Data data,
                          Application app) throws Exception {

        if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce)) {
            throw new IllegalArgumentException("signature/timestamp/nonce must not be null or empty");
        }

        {
            String sign = SHA1.sign(app.getToken(), timestamp, nonce);
            if (!sign.equals(signature)) {
                throw new IllegalArgumentException("incorrect signature");
            }
        }

        Data clone = data.clone();

        if ("aes".equalsIgnoreCase(encryptType)) {
            String sign = SHA1.sign(app.getToken(), timestamp, nonce, data.getEncrypt());
            if (!sign.equals(msgSignature)) {
                throw new IllegalArgumentException("incorrect message signature");
            }
            WXBizMsgCrypt crypt = new WXBizMsgCrypt(app.getToken(), app.getEncoding().getPassword(), app.getAppID());
            String decrypted = crypt.decrypt(data.getEncrypt());
            data = mapper.readValue(new StringReader(decrypted), Data.class);
        }

        String appID = app.getAppID();
        String openID = data.getSender();
        User user = userServiceBean.getUserWithEnvironment(appID, openID);
        // 没有默认的应用环境
        if (user == null || user.getEnvironment() == null) {
            throw new IllegalStateException("未配置默认应用环境");
        }
        String endpoint = user.getEnvironment().getPushURL();

        MessageAPI messageAPI = client.create(MessageAPI.class, endpoint);
        messageAPI.push(signature, timestamp, nonce, encryptType, msgSignature, clone, new Callback<String>() {
            @Override
            public void onCompleted(boolean success, String result, Throwable throwable) {

            }

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFail(Throwable throwable) {

            }
        });

        switch (data.getType()) {
            case UNKNOWN:
                break;
            case TEXT:
                Text text = new Text();
                text.setAppId(appID);
                text.setSender(data.getSender());
                text.setReceiver(data.getReceiver());
                text.setTimeCreated(data.getTimeCreated());
                text.setMsgId(data.getMsgId());
                text.setContent(data.getContent());
                if (weixinMessageServiceBean.merge(text) == 1) {
                    fire(text);
                }
                break;
            case IMAGE:
                Image image = new Image();
                image.setAppId(appID);
                image.setSender(data.getSender());
                image.setReceiver(data.getReceiver());
                image.setTimeCreated(data.getTimeCreated());
                image.setMsgId(data.getMsgId());
                image.setPicURL(data.getPicURL());
                image.setMediaId(data.getMediaId());
                if (weixinMessageServiceBean.merge(image) == 1) {
                    fire(image);
                }
                break;
            case VOICE:
                Voice voice = new Voice();
                voice.setAppId(appID);
                voice.setSender(data.getSender());
                voice.setReceiver(data.getReceiver());
                voice.setTimeCreated(data.getTimeCreated());
                voice.setMsgId(data.getMsgId());
                voice.setFormat(data.getFormat());
                voice.setRecognition(data.getRecognition());
                voice.setMediaId(data.getMediaId());
                if (weixinMessageServiceBean.merge(voice) == 1) {
                    fire(voice);
                }
                break;
            case VIDEO:
                Video video = new Video();
                video.setAppId(appID);
                video.setSender(data.getSender());
                video.setReceiver(data.getReceiver());
                video.setTimeCreated(data.getTimeCreated());
                video.setMsgId(data.getMsgId());
                video.setThumbMediaId(data.getThumbMediaId());
                video.setMediaId(data.getMediaId());
                if (weixinMessageServiceBean.merge(video) == 1) {
                    fire(video);
                }
                break;
            case SHORTVIDEO:
                ShortVideo shortVideo = new ShortVideo();
                shortVideo.setAppId(appID);
                shortVideo.setSender(data.getSender());
                shortVideo.setReceiver(data.getReceiver());
                shortVideo.setTimeCreated(data.getTimeCreated());
                shortVideo.setMsgId(data.getMsgId());
                shortVideo.setThumbMediaId(data.getThumbMediaId());
                shortVideo.setMediaId(data.getMediaId());
                if (weixinMessageServiceBean.merge(shortVideo) == 1) {
                    fire(shortVideo);
                }
                break;
            case LOCATION:
                Location location = new Location();
                location.setAppId(appID);
                location.setSender(data.getSender());
                location.setReceiver(data.getReceiver());
                location.setTimeCreated(data.getTimeCreated());
                location.setMsgId(data.getMsgId());
                location.setLocationX(data.getLocationX());
                location.setLocationY(data.getLocationY());
                location.setScale(data.getScale());
                location.setLabel(data.getLabel());
                if (weixinMessageServiceBean.merge(location) == 1) {
                    fire(location);
                }
                break;
            case LINK:
                Link link = new Link();
                link.setAppId(appID);
                link.setSender(data.getSender());
                link.setReceiver(data.getReceiver());
                link.setTimeCreated(data.getTimeCreated());
                link.setMsgId(data.getMsgId());
                link.setTitle(data.getTitle());
                link.setDescription(data.getDescription());
                link.setUrl(data.getUrl());
                if (weixinMessageServiceBean.merge(link) == 1) {
                    fire(link);
                }
                break;
            case EVENT:
                switch (data.getEvent()) {
                    case UNKNOWN:
                        break;
                    case SUBSCRIBE:
                        Subscribe subscribe = new Subscribe();
                        subscribe.setAppId(appID);
                        subscribe.setSender(data.getSender());
                        subscribe.setReceiver(data.getReceiver());
                        subscribe.setTimeCreated(data.getTimeCreated());
                        subscribe.setEventKey(data.getEventKey());
                        subscribe.setTicket(data.getTicket());
                        if (weixinMessageServiceBean.merge(subscribe) == 1) {
                            fire(subscribe);
                        }
                        break;
                    case UNSUBSCRIBE:
                        Unsubscribe unsubscribe = new Unsubscribe();
                        unsubscribe.setAppId(appID);
                        unsubscribe.setSender(data.getSender());
                        unsubscribe.setReceiver(data.getReceiver());
                        unsubscribe.setTimeCreated(data.getTimeCreated());
                        if (weixinMessageServiceBean.merge(unsubscribe) == 1) {
                            fire(unsubscribe);
                        }
                        break;
                    case SCAN:
                        Scan scan = new Scan();
                        scan.setAppId(appID);
                        scan.setSender(data.getSender());
                        scan.setReceiver(data.getReceiver());
                        scan.setTimeCreated(data.getTimeCreated());
                        scan.setEventKey(data.getEventKey());
                        scan.setTicket(data.getTicket());
                        if (weixinMessageServiceBean.merge(scan) == 1) {
                            fire(scan);
                        }
                        break;
                    case LOCATION:
                        Coordinate coordinate = new Coordinate();
                        coordinate.setAppId(appID);
                        coordinate.setSender(data.getSender());
                        coordinate.setReceiver(data.getReceiver());
                        coordinate.setTimeCreated(data.getTimeCreated());
                        coordinate.setLatitude(data.getLatitude());
                        coordinate.setLongitude(data.getLongitude());
                        coordinate.setAccuracy(data.getPrecision());
                        if (weixinMessageServiceBean.merge(coordinate) == 1) {
                            fire(coordinate);
                        }
                        break;
                    case CLICK:
                        Click click = new Click();
                        click.setAppId(appID);
                        click.setSender(data.getSender());
                        click.setReceiver(data.getReceiver());
                        click.setTimeCreated(data.getTimeCreated());
                        click.setEventKey(data.getEventKey());
                        click.setTicket(data.getTicket());
                        if (weixinMessageServiceBean.merge(click) == 1) {
                            fire(click);
                        }
                        break;
                    case VIEW:
                        View view = new View();
                        view.setAppId(appID);
                        view.setSender(data.getSender());
                        view.setReceiver(data.getReceiver());
                        view.setTimeCreated(data.getTimeCreated());
                        view.setEventKey(data.getEventKey());
                        view.setTicket(data.getTicket());
                        if (weixinMessageServiceBean.merge(view) == 1) {
                            fire(view);
                        }
                        break;
                }
                break;
        }

        return "@:success";
    }

    private void fire(final Message message) {
        Observable.from(messageHandlers.entrySet())
                .filter(entry -> entry.getKey().isInstance(message))
                .flatMap(entry -> Observable.from(entry.getValue()))
                .map(messageHandler -> messageHandler.handle(message))
                .filter(msg -> msg != null)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(msg -> defaultSessionProvider.execute(() -> WeixinCgiBinAPI.INSTANCE.message(tokenServiceBean.getApplicationAccessToken(message.getAppId()).getAccess_token(), msg)));
    }

    private void fire(final Event event) {
        Observable.from(eventHandlers.entrySet())
                .filter(entry -> entry.getKey().isInstance(event))
                .flatMap(entry -> Observable.from(entry.getValue()))
                .map(eventHandler -> eventHandler.handle(event))
                .filter(msg -> msg != null)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(msg -> defaultSessionProvider.execute(() -> WeixinCgiBinAPI.INSTANCE.message(tokenServiceBean.getApplicationAccessToken(event.getAppId()).getAccess_token(), msg)));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        {
            Map<String, MessageHandler> map = applicationContext.getBeansOfType(MessageHandler.class);
            flag:
            for (MessageHandler<?> messageHandler : map.values()) {
                Class<?> clazz = messageHandler.getClass();
                while (clazz != Object.class) {
                    Type[] interfaces = clazz.getGenericInterfaces();
                    for (Type type : interfaces) {
                        if (type instanceof ParameterizedType) {
                            ParameterizedType parameterizedType = (ParameterizedType) type;
                            Type rawType = parameterizedType.getRawType();
                            if (rawType == MessageHandler.class) {
                                Class<?> messageType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                                List<MessageHandler> list = messageHandlers.get(messageType);
                                if (list == null) {
                                    list = new ArrayList<>();
                                    messageHandlers.put(messageType, list);
                                }
                                list.add(messageHandler);
                                continue flag;
                            }
                        }
                    }
                    clazz = clazz.getSuperclass();
                }
            }
        }

        {
            Map<String, EventHandler> map = applicationContext.getBeansOfType(EventHandler.class);
            flag:
            for (EventHandler<?> eventHandler : map.values()) {
                Class<?> clazz = eventHandler.getClass();
                while (clazz != Object.class) {
                    Type[] interfaces = clazz.getGenericInterfaces();
                    for (Type type : interfaces) {
                        if (type instanceof ParameterizedType) {
                            ParameterizedType parameterizedType = (ParameterizedType) type;
                            Type rawType = parameterizedType.getRawType();
                            if (rawType == EventHandler.class) {
                                Class<?> eventType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                                List<EventHandler> list = eventHandlers.get(eventType);
                                if (list == null) {
                                    list = new ArrayList<>();
                                    eventHandlers.put(eventType, list);
                                }
                                list.add(eventHandler);
                                continue flag;
                            }
                        }
                    }
                    clazz = clazz.getSuperclass();
                }
            }
        }
    }

}
