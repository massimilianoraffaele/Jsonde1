package com.jsonde.api.serialization;

import java.io.Externalizable;
/**
 * 
 * @author admin
 *
 */
public interface PoolableExternalizable extends Externalizable {

	/**
	 * 
	 * @return
	 */
	
    String getFactoryClassName();

}
