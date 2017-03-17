package org.qfox.wectrl.web.msg;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.qfox.jestful.xml.XmlRequestSerializer;

/**
 * Created by yangchangpei on 17/3/17.
 */
public class TextXMLRequestSerializer extends XmlRequestSerializer {
    private static final long serialVersionUID = 901382359889547575L;

    @Override
    public String getContentType() {
        return "text/xml";
    }

    public TextXMLRequestSerializer() {
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

}
