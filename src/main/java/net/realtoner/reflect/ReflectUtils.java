package net.realtoner.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public class ReflectUtils {

    public static Method getGetterMethod(Class<?> clazz, Field field) throws NoSuchMethodException{

        String fieldName = field.getName();
        String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

        Method method = clazz.getMethod(methodName);

        if(!method.getReturnType().equals(field.getType()))
            throw new NoSuchMethodException("field's return type is " + field.getType().getName() + " , " +
                    "but getter method's return type is " + method.getReturnType().getName());

        return method;
    }
}
