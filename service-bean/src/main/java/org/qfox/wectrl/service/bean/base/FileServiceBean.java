package org.qfox.wectrl.service.bean.base;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import org.qfox.wectrl.service.base.FileService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;

/**
 * <p>
 * Description:
 * </p>
 * <p>
 * <p>
 * Company: 广州市俏狐信息科技有限公司
 * </p>
 *
 * @author yangchangpei 646742615@qq.com
 * @version 1.0.0
 * @date 2015年9月8日 下午5:04:43
 */
@Service
public class FileServiceBean implements FileService {
    private String bucket = "qfox";
    private String key = "BbPMXiDU4EMMFAgf";
    private String secret = "tBoH7hdF4YTG6FDVAuPwZtJ6nx1Sz5";
    private String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
    private OSSClient client = new OSSClient(endpoint, key, secret);

    public String upload(String key, InputStream in) throws IOException {
        while (key.startsWith("/")) {
            key = key.substring(1);
        }
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(in.available());
        meta.setLastModified(new Date());
        return client.putObject(bucket, key, in, meta).getETag();
    }

    public String upload(String key, File file) throws FileNotFoundException {
        while (key.startsWith("/")) {
            key = key.substring(1);
        }
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(file.length());
        meta.setLastModified(new Date(file.lastModified()));
        return client.putObject(bucket, key, new FileInputStream(file), meta).getETag();
    }

    public String upload(String key, InputStream in, Metadata metadata) throws IOException {
        while (key.startsWith("/")) {
            key = key.substring(1);
        }
        ObjectMetadata meta = metadata.toObjectMetadata();
        meta.setContentLength(in.available());
        meta.setLastModified(new Date());
        return client.putObject(bucket, key, in, meta).getETag();
    }

    public String upload(String key, File file, Metadata metadata) throws FileNotFoundException {
        while (key.startsWith("/")) {
            key = key.substring(1);
        }
        ObjectMetadata meta = metadata.toObjectMetadata();
        meta.setContentLength(file.length());
        meta.setLastModified(new Date(file.lastModified()));
        return client.putObject(bucket, key, new FileInputStream(file), meta).getETag();
    }

}
