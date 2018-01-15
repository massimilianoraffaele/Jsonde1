package com.jsonde.util;

import com.jsonde.util.ObjectIdGenerator.ObjectWrapper;

/**
 * 
 * @author admin
 *
 * @param <M>
 * @param <N>
 */
public class Pair<M, N> extends ObjectWrapper<Pair<M, N>> {

    private final M m;
    private final N n;

    /**
     * 
     * @param m
     * @param n
     */
    Pair(M m, N n) {
        this.m = m;
        this.n = n;
    }
    @Override
    
    /**
     * equals
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return true;
    }

    @Override
    
    /**
     * hashCode
     */
    public int hashCode() {
        int result = m != null ? System.identityHashCode(m) : 0;
        result = 31 * result + (n != null ? System.identityHashCode(n) : 0);
        return result;
    }

}