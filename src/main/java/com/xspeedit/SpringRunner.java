package com.xspeedit;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringRunner {
	private final Class<?> mainClass;
	private final AnnotationConfigApplicationContext ctx;

	public SpringRunner(Class<?> mainClass, Class<?> config) {
		this.mainClass = mainClass;
		ctx = new AnnotationConfigApplicationContext(config);
	}

	public void run(String[] args) {
		// AutowireCapableBeanFactory.AUTOWIRE_NO : Constant that indicates no externally defined autowiring
		Object bean = ctx.getBeanFactory().autowire(mainClass, AutowireCapableBeanFactory.AUTOWIRE_NO, true);
		try {
			Method method = mainClass.getMethod("run", String[].class,AnnotationConfigApplicationContext.class);
			method.invoke(bean, new Object[] {args,ctx });
		} catch (SecurityException |NoSuchMethodException | IllegalArgumentException |IllegalAccessException |InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}