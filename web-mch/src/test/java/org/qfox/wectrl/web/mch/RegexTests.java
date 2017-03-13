package org.qfox.wectrl.web.mch;

import org.junit.Test;

/**
 * Created by yangchangpei on 17/3/2.
 */
public class RegexTests {

    @Test
    public void testRegex() {
        System.out.println("http://localhost:8080/fffsd.res/sdfsd.res".matches("^http(s)?://[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*(:\\d+)?/[^?#]+$"));
    }

}
