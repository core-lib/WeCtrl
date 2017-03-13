package org.qfox.wectrl.common;

import java.util.Random;

/**
 * Created by payne on 2017/3/12.
 */
public abstract class Randoms {

    public static String number(int length) {
        String base = "0123456789";
        return from(base, length);
    }

    public static String letter(int length) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        return from(base, length);
    }

    public static String string(int length) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return from(base, length);
    }

    public static String from(String base, int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(base.length());
            sb.append(base.charAt(index));
        }
        return sb.toString();
    }

}
