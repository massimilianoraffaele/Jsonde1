package com.jsonde.client.domain;
/**
 * 
 * @author admin
 *
 */
public class Method extends DomainObject {

    private long classId;
    private String name;

    /**
     * 
     * @return
     */
    public long getClassId() {
        return classId;
    }

    /**
     * 
     * @param classId
     */
    public void setClassId(long classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
