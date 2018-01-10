package com.jsonde.api.methodCall;

import com.jsonde.util.pool.ObjectPool;
import com.jsonde.util.pool.ObjectPoolException;
/**
 * 
 * @author admin
 *
 */
public class MethodCallDtoFactory extends ObjectPool<MethodCallDto> {

    private static final MethodCallDtoFactory instance = new MethodCallDtoFactory();

    @Override
    protected MethodCallDto create() throws ObjectPoolException {
        return new MethodCallDto();
    }

    @Override
    protected void activate(MethodCallDto element) {
        super.activate(element);
        element.flags = 0;
    }

    /**
     * 
     * @return
     */
    public static MethodCallDto getMethodCallDtoFromPool() throws Error{
        try {
            return instance.takeFromPool();
        } catch (ObjectPoolException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public static void returnMethodCallDtoToPool(MethodCallDto methodCallDto) {
        instance.returnToPool(methodCallDto);
    }

}
