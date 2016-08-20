package info.softex.dictionary.core.utils;

import java.lang.reflect.Method;

/**
 *
 * @since version 4.7, 04/01/2015
 *
 * @author Dmitry Viktorov
 *
 */
public class ReflectionUtils {

    /**
     * Quietly calls a method.
     */
    public static Object callQuietly(Object obj, String methodName, Class<?>[] parameterTypes, Object[] params) {

        try {
        	
			Method method = obj.getClass().getMethod(methodName, parameterTypes);
			
			if (method.getReturnType().equals(Void.TYPE)) {
				method.invoke(obj, params);
			} else {
				return method.invoke(obj, params);
			}
			
		} catch (Exception e) {}
        
        return null;

    }

    public static Object callQuietly(Object obj, String methodName, Class<?> parameterType, Object param) {
        return callQuietly(obj, methodName, new Class<?>[] {parameterType}, new Object[] {param});
    }

}
