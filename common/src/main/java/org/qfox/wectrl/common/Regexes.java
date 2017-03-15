package org.qfox.wectrl.common;

/**
 * Created by payne on 2017/3/15.
 */
public interface Regexes {
    String DOMAIN_REGEX = "^http(s)?://[a-zA-Z0-9_-]+\\.[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*$";
    String PUSH_URL_REGEX = "^http(s)?://[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*(:\\d+)?/[^?#]+$";

}
