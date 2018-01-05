package com.jsonde.api.serialization;

import java.io.IOException;
import java.io.ObjectOutput;
/**
 * 
 * @author admin
 *
 */
public class PoolableObjectOutput extends ObjectOutputDecorator {

	/**
	 * 
	 * @param target
	 */
    public PoolableObjectOutput(ObjectOutput target) {
        super(target);
    }

    @Override
    public void writeObject(Object obj) throws IOException {
        if (obj instanceof PoolableExternalizable) {
            writeBoolean(true);
            writePoolableExternalizable((PoolableExternalizable) obj);
        } else {
            writeBoolean(false);
            super.writeObject(obj);
        }
    }

    public void writePoolableExternalizable(PoolableExternalizable poolableExternalizable) throws IOException {
        writeUTF(poolableExternalizable.getFactoryClassName());
        poolableExternalizable.writeExternal(this);
    }

}
