package net.realtoner.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public class ReflectUtils {

    public static Method getGetterMethod(Class<?> clazz, Field field) throws NoSuchMethodException{

        String fieldName = field.getName();
        String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

        Method method = clazz.getMethod(methodName);

        if(method == null){
            throw new NoSuchElementException("There is no getter method for '" + fieldName + "'.");
        }

        if(!method.getReturnType().equals(field.getType()))
            throw new NoSuchMethodException("The type of given field is " + field.getType().getName() + " , " +
                    "but getter method's return type is " + method.getReturnType().getName());

        return method;
    }
}
