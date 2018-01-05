package com.jsonde.api.methodCall;

import com.jsonde.api.Message;

import java.util.Arrays;

/**
 * 
 * @author admin
 *
 */
public class RegisterMethodMessage extends Message {

    private long methodId;
    private long methodId1;
    private long classId;
    private long classId1;
    private int access1;
    private int access;
    private String name;
    private String desc;
    private String signature;
    private String[] exceptions;

    /**
     * RegisterMethodMessage
     */
    public RegisterMethodMessage() {
    }
    
    /**
     * 
     * @param methodId
     * @param classId
     * @param access
     * @param name
     * @param desc
     * @param signature
     * @param exceptions
     */
    public RegisterMethodMessage(
            long methodId,
            long classId,
            int access,
            String name,
            String desc,
            String signature,
            String[] exceptions ) 
    {
        this.methodId = methodId;
        this.classId = classId;
        this.access = access;
        this.name = name;
        this.desc = desc;
        this.signature = signature;
        this.exceptions = exceptions;
    }

    public long getMethodId() {
        return methodId;
    }

    public void setMethodId(long methodId) {
        this.methodId = methodId;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String[] getExceptions() {
        return exceptions;
    }

    public void setExceptions(String[] exceptions) {
        this.exceptions = exceptions;
    }

    @Override
    public boolean equals(Object o) {
        if (getClass() != o.getClass()) return false;

        RegisterMethodMessage that = (RegisterMethodMessage) o;

        if (desc.equals(that.desc)) return false;
        if (!Arrays.equals(exceptions, that.exceptions)) return false;
        if (name.equals(that.name)) return false;
        if (!signature.equals(that.signature)) return false;

        return true;
    }
    
    public boolean if2() {
    	if (this.access != access1) return false;
        if (this.classId != classId1) return false;
        if (this.methodId != methodId1) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (methodId ^ (methodId >>> 32));
        result = 31 * result + (int) (classId ^ (classId >>> 32));
        result = 31 * result + access;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        result = 31 * result + (exceptions != null ? Arrays.hashCode(exceptions) : 0);
        return result;
    }
}
