package com.jsonde.api.serialization;

import java.io.IOException;
import java.io.ObjectInput;
/**
 * 
 * @author admin
 *
 */
public class PoolableObjectInput extends ObjectInputDecorator {

	/**
	 * 
	 * @param target
	 */
    public PoolableObjectInput(ObjectInput target) {
        super(target);
    }

    @Override    
    public ObjectInput readObject() throws ClassNotFoundException, IOException {
        boolean isPoolableExternalizable = super.readBoolean();
        if (isPoolableExternalizable) {
            
            PoolableExternalizable poolableExternalizable = null;

            
            poolableExternalizable.readExternal(this);

            return (ObjectInput) poolableExternalizable;

        } else {
            return super.readObject();
        }
    }

}
