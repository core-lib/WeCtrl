package org.qfox.wectrl.web;

import org.qfox.jestful.core.annotation.*;
import org.qfox.jestful.server.exception.NotFoundStatusException;
import org.qfox.wectrl.core.base.App;
import org.qfox.wectrl.core.base.Application;
import org.qfox.wectrl.core.base.Verification;
import org.qfox.wectrl.core.weixin.message.*;
import org.qfox.wectrl.service.base.ApplicationService;
import org.qfox.wectrl.service.base.VerificationService;
import org.qfox.wectrl.service.weixin.WeixinMessageService;
import org.qfox.wectrl.web.aes.SHA1;
import org.qfox.wectrl.web.auth.Authorized;
import org.qfox.wectrl.web.handler.EventHandler;
import org.qfox.wectrl.web.handler.MessageHandler;
import org.qfox.wectrl.web.msg.Msg;
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
import javax.servlet.http.HttpServletRequest;
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

    @Authorized(required = false)
    @GET("/")
    public String verify(@Query("signature") String signature,
                         @Query("timestamp") String timestamp,
                         @Query("nonce") String nonce,
                         @Query("echostr") String echostr,
                         HttpServletRequest request) {
        Application app = null;
        boolean verified = false;
        try {
            if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce) || StringUtils.isEmpty(echostr)) {
                throw new IllegalArgumentException("signature/timestamp/nonce/echostr must not null or empty");
            }

            String appID = request.getServerName().split("\\.")[0];
            app = applicationServiceBean.getApplicationByAppID(appID);
            if (app == null) {
                throw new NotFoundStatusException("/message", "GET", null);
            }

            String sign = SHA1.sign(app.getToken(), timestamp, nonce);
            if (!signature.equals(sign)) {
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
                          @Body Msg msg,
                          Application app,
                          HttpServletRequest request) throws Exception {
        if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce)) {
            throw new IllegalArgumentException("signature/timestamp/nonce must not null or empty");
        }

        String appID = app.getAppID();

        switch (msg.getType()) {
            case UNKNOWN:
                break;
            case TEXT:
                Text text = new Text();
                text.setAppId(appID);
                text.setSender(msg.getSender());
                text.setReceiver(msg.getReceiver());
                text.setTimeCreated(msg.getTimeCreated());
                text.setMsgId(msg.getMsgId());
                text.setContent(msg.getContent());
                if (weixinMessageServiceBean.merge(text) == 1) {
                    fire(text);
                }
                break;
            case IMAGE:
                Image image = new Image();
                image.setAppId(appID);
                image.setSender(msg.getSender());
                image.setReceiver(msg.getReceiver());
                image.setTimeCreated(msg.getTimeCreated());
                image.setMsgId(msg.getMsgId());
                image.setPicURL(msg.getPicURL());
                image.setMediaId(msg.getMediaId());
                if (weixinMessageServiceBean.merge(image) == 1) {
                    fire(image);
                }
                break;
            case VOICE:
                Voice voice = new Voice();
                voice.setAppId(appID);
                voice.setSender(msg.getSender());
                voice.setReceiver(msg.getReceiver());
                voice.setTimeCreated(msg.getTimeCreated());
                voice.setMsgId(msg.getMsgId());
                voice.setFormat(msg.getFormat());
                voice.setRecognition(msg.getRecognition());
                voice.setMediaId(msg.getMediaId());
                if (weixinMessageServiceBean.merge(voice) == 1) {
                    fire(voice);
                }
                break;
            case VIDEO:
                Video video = new Video();
                video.setAppId(appID);
                video.setSender(msg.getSender());
                video.setReceiver(msg.getReceiver());
                video.setTimeCreated(msg.getTimeCreated());
                video.setMsgId(msg.getMsgId());
                video.setThumbMediaId(msg.getThumbMediaId());
                video.setMediaId(msg.getMediaId());
                if (weixinMessageServiceBean.merge(video) == 1) {
                    fire(video);
                }
                break;
            case SHORTVIDEO:
                ShortVideo shortVideo = new ShortVideo();
                shortVideo.setAppId(appID);
                shortVideo.setSender(msg.getSender());
                shortVideo.setReceiver(msg.getReceiver());
                shortVideo.setTimeCreated(msg.getTimeCreated());
                shortVideo.setMsgId(msg.getMsgId());
                shortVideo.setThumbMediaId(msg.getThumbMediaId());
                shortVideo.setMediaId(msg.getMediaId());
                if (weixinMessageServiceBean.merge(shortVideo) == 1) {
                    fire(shortVideo);
                }
                break;
            case LOCATION:
                Location location = new Location();
                location.setAppId(appID);
                location.setSender(msg.getSender());
                location.setReceiver(msg.getReceiver());
                location.setTimeCreated(msg.getTimeCreated());
                location.setMsgId(msg.getMsgId());
                location.setLocationX(msg.getLocationX());
                location.setLocationY(msg.getLocationY());
                location.setScale(msg.getScale());
                location.setLabel(msg.getLabel());
                if (weixinMessageServiceBean.merge(location) == 1) {
                    fire(location);
                }
                break;
            case LINK:
                Link link = new Link();
                link.setAppId(appID);
                link.setSender(msg.getSender());
                link.setReceiver(msg.getReceiver());
                link.setTimeCreated(msg.getTimeCreated());
                link.setMsgId(msg.getMsgId());
                link.setTitle(msg.getTitle());
                link.setDescription(msg.getDescription());
                link.setUrl(msg.getUrl());
                if (weixinMessageServiceBean.merge(link) == 1) {
                    fire(link);
                }
                break;
            case EVENT:
                switch (msg.getEvent()) {
                    case UNKNOWN:
                        break;
                    case SUBSCRIBE:
                        Subscribe subscribe = new Subscribe();
                        subscribe.setAppId(appID);
                        subscribe.setSender(msg.getSender());
                        subscribe.setReceiver(msg.getReceiver());
                        subscribe.setTimeCreated(msg.getTimeCreated());
                        subscribe.setEventKey(msg.getEventKey());
                        subscribe.setTicket(msg.getTicket());
                        if (weixinMessageServiceBean.merge(subscribe) == 1) {
                            fire(subscribe);
                        }
                        break;
                    case UNSUBSCRIBE:
                        Unsubscribe unsubscribe = new Unsubscribe();
                        unsubscribe.setAppId(appID);
                        unsubscribe.setSender(msg.getSender());
                        unsubscribe.setReceiver(msg.getReceiver());
                        unsubscribe.setTimeCreated(msg.getTimeCreated());
                        if (weixinMessageServiceBean.merge(unsubscribe) == 1) {
                            fire(unsubscribe);
                        }
                        break;
                    case SCAN:
                        Scan scan = new Scan();
                        scan.setAppId(appID);
                        scan.setSender(msg.getSender());
                        scan.setReceiver(msg.getReceiver());
                        scan.setTimeCreated(msg.getTimeCreated());
                        scan.setEventKey(msg.getEventKey());
                        scan.setTicket(msg.getTicket());
                        if (weixinMessageServiceBean.merge(scan) == 1) {
                            fire(scan);
                        }
                        break;
                    case LOCATION:
                        Coordinate coordinate = new Coordinate();
                        coordinate.setAppId(appID);
                        coordinate.setSender(msg.getSender());
                        coordinate.setReceiver(msg.getReceiver());
                        coordinate.setTimeCreated(msg.getTimeCreated());
                        coordinate.setLatitude(msg.getLatitude());
                        coordinate.setLongitude(msg.getLongitude());
                        coordinate.setAccuracy(msg.getPrecision());
                        if (weixinMessageServiceBean.merge(coordinate) == 1) {
                            fire(coordinate);
                        }
                        break;
                    case CLICK:
                        Click click = new Click();
                        click.setAppId(appID);
                        click.setSender(msg.getSender());
                        click.setReceiver(msg.getReceiver());
                        click.setTimeCreated(msg.getTimeCreated());
                        click.setEventKey(msg.getEventKey());
                        click.setTicket(msg.getTicket());
                        if (weixinMessageServiceBean.merge(click) == 1) {
                            fire(click);
                        }
                        break;
                    case VIEW:
                        View view = new View();
                        view.setAppId(appID);
                        view.setSender(msg.getSender());
                        view.setReceiver(msg.getReceiver());
                        view.setTimeCreated(msg.getTimeCreated());
                        view.setEventKey(msg.getEventKey());
                        view.setTicket(msg.getTicket());
                        if (weixinMessageServiceBean.merge(view) == 1) {
                            fire(view);
                        }
                        break;
                }
                break;
        }

        return "@:";
    }

    private void fire(final Message message) {
        Observable.from(messageHandlers.entrySet())
                .filter(entry -> entry.getKey().isInstance(message))
                .flatMap(entry -> Observable.from(entry.getValue()))
                .map(messageHandler -> {
                    messageHandler.handle(message);
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
    }

    private void fire(final Event event) {
        Observable.from(eventHandlers.entrySet())
                .filter(entry -> entry.getKey().isInstance(event))
                .flatMap(entry -> Observable.from(entry.getValue()))
                .map(messageHandler -> {
                    messageHandler.handle(event);
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
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
