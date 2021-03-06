/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 *
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

package org.qfox.wectrl.common.weixin.aes;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * SHA1 class
 * <p>
 * 计算公众平台的消息签名接口.
 */
public class SHA1 {

    /**
     * 用SHA1算法生成安全签名
     *
     * @return 安全签名
     * @throws AesException
     */
    public static String sign(String... values) throws AesException {
        try {
            String[] array = values.clone();
            StringBuffer sb = new StringBuffer();
            // 字符串排序
            Arrays.sort(array);
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer signature = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                String shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    signature.append(0);
                }
                signature.append(shaHex);
            }
            return signature.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AesException(AesException.ComputeSignatureError);
        }
    }
}
