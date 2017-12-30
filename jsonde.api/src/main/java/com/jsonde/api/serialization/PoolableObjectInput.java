package com.jsonde.api.serialization;

import java.io.IOException;
import java.io.ObjectInput;
/**
 * 
 * @author admin
 *
 */
public class PoolableObjectInput extends ObjectInputDecorator {

    public PoolableObjectInput(ObjectInput target) {
        super(target);
    }

    @Override
    public ObjectInput readObject() throws ClassNotFoundException, IOException {
        boolean isPoolableExternalizable = super.readBoolean();
        if (isPoolableExternalizable) {
            String factoryClassName = super.readUTF();

            Class factoryClass = Class.forName(factoryClassName);

            PoolableExternalizable poolableExternalizable = null;

            /*try {
                Method createMethod = factoryClass.getMethod("create");
                poolableExternalizable = PoolableExternalizable.class.cast(createMethod.invoke(null));
            }  catch (IllegalAccessException e) {
                throw new IOException(e);
            } catch (InvocationTargetException e) {
                throw new IOException(e);
            } catch (NoSuchMethodException e) {
                throw new IOException(e);
            }*/

            poolableExternalizable.readExternal(this);

            return (ObjectInput) poolableExternalizable;

        } else {
            return super.readObject();
        }
    }

}
