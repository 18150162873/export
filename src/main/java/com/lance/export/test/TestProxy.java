package com.lance.export.test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Optional;

public class TestProxy {

	public static void main(String[] args) throws Exception {
//		LanceProxy lanceProxy = new LanceProxy();
//		Object proInstent = Proxy.newProxyInstance(T1.class.getClassLoader(), new Class[] {T1.class}, lanceProxy);
//		Method mothod = proInstent.getClass().getMethod("test", new Class[] {});
//		System.out.println((int)mothod.invoke(proInstent, new Class[] {}));
		System.out.println(Optional.of(null).get());
	}
}
