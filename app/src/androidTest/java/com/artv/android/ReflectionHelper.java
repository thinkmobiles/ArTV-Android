package com.artv.android;

import android.support.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ZOG1 on 6/12/2015.
 */
public abstract class ReflectionHelper {

	/**
	 * Returns the value of the field on the specified object.
	 * The name parameter is a String specifying the simple name of the desired field.
	 * @param _class class
	 * @param _name name of desired field
	 * @return value of the field
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static final Object getField(final Class _class, final String _name)
			throws NoSuchFieldException, IllegalAccessException {
		final Field field = _class.getDeclaredField(_name);
		field.setAccessible(true);
		return field.get(_class);
	}

	/**
	 * Returns the value of the field on the specified object.
	 * The name parameter is a String specifying the simple name of the desired field.
	 * @param _obj object
	 * @param _name name of desired field
	 * @return value of the field
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static final Object getField(final Object _obj, final String _name)
			throws NoSuchFieldException, IllegalAccessException {
		final Field field = _obj.getClass().getDeclaredField(_name);
		field.setAccessible(true);
		return field.get(_obj);
	}

	/**
	 * Set value to private field.
	 * @param _obj object that contains filed.
	 * @param _name field name.
	 * @param _value needed value.
	 */
	public static final void setField(final Object _obj, final String _name, final Object _value)
			throws NoSuchFieldException, IllegalAccessException {
		final Field field = _obj.getClass().getDeclaredField(_name);
		field.setAccessible(true);
		field.set(_obj, _value);
	}

	public static final Object invoke(final Object _obj, final String _name)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return invoke(_obj, _name, null, null);
	}

	/**
	 * Invokes the method represented by the name value on the specified object with the specified parameters.
	 * @param _obj object
	 * @param _name name of the method to invoke
	 * @param _parameterTypes parameter types
	 * @param _args parameter values
	 * @return value or null if method return void
	 */
	public static final Object invoke(final Object _obj, final String _name,
									  @Nullable final Class[] _parameterTypes,
									  @Nullable final Object[] _args)
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		final Method method = _obj.getClass().getDeclaredMethod(_name, _parameterTypes);
		method.setAccessible(true);
		return method.invoke(_obj, _args);
	}

}
