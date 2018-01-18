package com.jsonde.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
/**
 * 
 * @author admin
 *
 * @param <T>
 */
public class ObjectIdGenerator<T> {

    private AtomicLong sequence = new AtomicLong();
    private Map<ObjectWrapper<T>, Long> objectIds = new HashMap<ObjectWrapper<T>, Long>();

    /**
     * 
     * @param object
     * @return
     */
    public long getId(T object) {
    	synchronized (this){
        ObjectWrapper<T> objectWrapper = wrap(object);

        if (!objectIds.containsKey(objectWrapper)) {
            objectIds.put(objectWrapper, sequence.getAndIncrement());
        }

        return objectIds.get(objectWrapper);

    	}
    }	
    
    /**
     * 
     * @param object
     * @return
     * @throws ObjectIsAbsentException
     */
    public long pollId(T object) throws ObjectIsAbsentException {
    	synchronized (this){
        ObjectWrapper<T> objectWrapper = wrap(object);

        if (objectIds.containsKey(objectWrapper)) {
            return objectIds.get(objectWrapper);
        } else {
            throw new ObjectIsAbsentException();
        }

    	}
    }	

    @SuppressWarnings("unchecked")
    
    /**
     * 
     * @param object
     * @return
     */
    public ObjectWrapper<T> wrap(T object) {
        if (object instanceof ObjectWrapper) {
            return (ObjectWrapper<T>) object;
        } else {
            return new ObjectWrapper<T>(object);
        }
    }
/**
 * 
 * @param m
 * @param n
 * @return
 */
    public static <M, N> Pair<M, N> pair(M m, N n) {
        return new Pair<M, N>(m, n);
    }


    /**
     * 
     * @author albertomadio
     * ObjectWrapper
     * @param <T>
     */
    protected static class ObjectWrapper<T> {

        private final T object;
        
        /**
         * 
         * @param object
         */
        private ObjectWrapper(T object) {
            this.object = object;
        }

        @SuppressWarnings("unchecked")
        protected ObjectWrapper() {
            object = (T) this;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(object);
        }

        @Override
        public boolean equals(Object obj) {
        	if (getClass() != obj.getClass()){      	
        		if(obj instanceof ObjectWrapper){
            if (null == object) {
                return null == obj;
            } else {
                return object == obj;
            }		      
        }return false;
      }
      return false;
    }
  }
}
