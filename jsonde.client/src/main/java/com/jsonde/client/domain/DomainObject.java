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
	private long id;

	/**
	 * 
	 * @return
	 */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DomainObject{" +
                "id=" + id +
                '}';
    }
}
