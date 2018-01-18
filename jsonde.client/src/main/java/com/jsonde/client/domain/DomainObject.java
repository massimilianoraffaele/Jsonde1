package com.jsonde.client.domain;

import java.io.Serializable;
/**
 * 
 * @author admin
 *
 */
public abstract class DomainObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long aa;

	/**
	 * 
	 * @return
	 */
    public long getId() {
        return aa;
    }
    
    /**
     * 
     * @param id
     */
    public void setId(long id) {
        this.aa = id;
    }

    @Override
    public String toString() {
        return "DomainObject{" +
                "id=" + aa +
                '}';
    }
}
