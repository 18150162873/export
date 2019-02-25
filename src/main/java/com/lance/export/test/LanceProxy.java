package com.lance.export.test;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LanceProxy implements InvocationHandler,Serializable{

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return 1;
	}

}
