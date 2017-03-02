package org.qfox.wectrl.core.base;


import org.qfox.wectrl.core.Domain;

import javax.persistence.MappedSuperclass;

/**
 * Created by yangchangpei on 17/2/15.
 */
@MappedSuperclass
public class Resource extends Domain {
    private static final long serialVersionUID = 7210071012850916230L;

    protected String directory;
    protected String name;
    protected String extension;
    protected String type;
    protected int size;

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
