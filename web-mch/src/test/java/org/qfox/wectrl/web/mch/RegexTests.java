package org.qfox.wectrl.web.mch;

import org.junit.Test;

/**
 * Created by yangchangpei on 17/3/2.
 */
public class RegexTests {

    @Test
    public void testRegex() {
        String domain = "http://rxhclothes.d.qfoxtech.com";
        String redirectURI = "https://baidu.com//login";
        String path = redirectURI.replaceFirst("http(s)?://[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*(:\\d+)?/", "");
        String redirectURL = domain + (path.startsWith("/") ? "" : "/") + path;
        System.out.println(redirectURL);
    }

}
