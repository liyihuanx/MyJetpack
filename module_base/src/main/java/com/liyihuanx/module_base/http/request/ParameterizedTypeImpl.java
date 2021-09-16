package com.liyihuanx.module_base.http.request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @author created by liyihuanx
 * @date 2021/9/16
 * @description: fastjson
 */
public class ParameterizedTypeImpl implements ParameterizedType {
    // 返回Map<String,User>里的String和User，所以这里返回[String.class,User.clas]
    private final Type[] actualTypeArguments;
    // Map<String,User>里的Map,所以返回值是Map.class
    private final Type ownerType;
    // 用于这个泛型上中包含了内部类的情况,一般返回null
    private final Type rawType;

    public ParameterizedTypeImpl(Type[] actualTypeArguments, Type ownerType, Type rawType) {
        this.actualTypeArguments = actualTypeArguments;
        this.ownerType = ownerType;
        this.rawType = rawType;
    }

    public Type[] getActualTypeArguments() {
        return actualTypeArguments;
    }

    public Type getOwnerType() {
        return ownerType;
    }

    public Type getRawType() {
        return rawType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParameterizedTypeImpl that = (ParameterizedTypeImpl) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(actualTypeArguments, that.actualTypeArguments)) return false;
        if (ownerType != null ? !ownerType.equals(that.ownerType) : that.ownerType != null)
            return false;
        return rawType != null ? rawType.equals(that.rawType) : that.rawType == null;

    }

    @Override
    public int hashCode() {
        int result = actualTypeArguments != null ? Arrays.hashCode(actualTypeArguments) : 0;
        result = 31 * result + (ownerType != null ? ownerType.hashCode() : 0);
        result = 31 * result + (rawType != null ? rawType.hashCode() : 0);
        return result;
    }
}
