package org.qfox.wectrl.web.mch;

import org.junit.Test;

/**
 * Created by yangchangpei on 17/3/2.
 */
public class RegexTests {

    @Test
    public void testRegex() {
        System.out.println("aa0123".matches("^(?![0-9_-])[a-zA-Z0-9_-]{6,12}$"));
    }

}
