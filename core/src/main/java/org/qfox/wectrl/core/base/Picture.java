package org.qfox.wectrl.core.base;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by payne on 2017/1/28.
 */
@Entity
@Table(name = "base_picture_tbl")
public class Picture extends Resource {
    private static final long serialVersionUID = 7568367188905671264L;

    private int x;
    private int y;
    private int w;
    private int h;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    @Override
    public String toString() {
        return "http://img.qfoxtech.com" + directory + name + extension + "?x-oss-process=image/crop,x_" + x + ",y_" + y + ",w_" + w + ",h_" + h;
    }
}
