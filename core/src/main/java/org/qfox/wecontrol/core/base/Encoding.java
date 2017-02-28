package org.qfox.wecontrol.core.base;

import org.qfox.wecontrol.common.base.EncodingMode;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by yangchangpei on 17/2/28.
 */
@Embeddable
public class Encoding implements Serializable {
    private static final long serialVersionUID = 2008285094523331265L;

    private EncodingMode mode;
    private String algorithm;
    private String password;

    public EncodingMode getMode() {
        return mode;
    }

    public void setMode(EncodingMode mode) {
        this.mode = mode;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
