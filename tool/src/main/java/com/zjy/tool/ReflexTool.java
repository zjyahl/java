package com.zjy.tool;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;



import static java.lang.System.out;

import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface TagTest {
	String name() default "ddfdf";
	int age() default 12;
}

@Repeatable(Mytags.class)
@Retention(RetentionPolicy.RUNTIME)
 @interface Mytag {
	String name() default "hello";
	int age() default 12;
}

@Retention(RetentionPolicy.RUNTIME)
 @interface Mytags {
	Mytag[] value();
}
public class ReflexTool {

	private Class<?> cls;
	
	public ReflexTool(Class<?> cls) {
		this.cls = cls;
	}
	
	public void printConstructors() {
		Constructor<?>[] constructors = cls.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			Parameter[] parameters = constructor.getParameters();
			for (Parameter parameter : parameters) {
				parameter.getModifiers();
				parameter.isVarArgs();
				parameter.getName();
 				parameter.getParameterizedType();
			}
		}
		
		
	}
	public void printFields() {
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			out.print("field name ->" + field.getName());
			out.print("field class ->" + field.getType());
			
			field.isAnnotationPresent(TagTest.class);
			field.getAnnotation(TagTest.class);
			field.getAnnotations();	
			field.getDeclaredAnnotations();
			field.getDeclaredAnnotation(TagTest.class);		
			field.getAnnotationsByType(TagTest.class);
			field.getDeclaredAnnotationsByType(TagTest.class);
			
			Type gType = field.getGenericType();
			if(gType instanceof ParameterizedType) {
				printGenerics((ParameterizedType)gType);
			}
			out.println("modifier -> " + Modifier.toString(field.getModifiers()));
		}
	}
	
	public void printFuns() {
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			Type gType = method.getGenericReturnType();
			if(gType instanceof ParameterizedType) {
				printGenerics((ParameterizedType)gType);
			}
			out.println("modifier -> " + Modifier.toString(method.getModifiers()));
			Parameter[] parameters = method.getParameters();
			for (Parameter parameter : parameters) {
				parameter.getModifiers();
				parameter.isVarArgs();
				parameter.getName();
				parameter.getParameterizedType();
			}
		}
	}
	
	private void printAnnotations(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			out.println("annotation -> " + annotation);
			if (annotation instanceof TagTest) {
				TagTest a = (TagTest) annotation;
			}
		}
	}
	
	private void printGenerics(ParameterizedType pType) {
			Type[] types = pType.getActualTypeArguments();
			for (Type type : types) {
				out.println("generic type -> " + type);
			}
	}
	
	public static String[] add() {
		return null;
	}
	
	public String[] aStrings;
	public static void main( String[] args ) throws Exception
    {
		Work work = new WorkImpl();
		InvocationHandler handler = new MyInvocationHandler(work);
		Work workProxy =(Work)Proxy.newProxyInstance(Work.class.getClassLoader(), work.getClass().getInterfaces(), handler);
		workProxy.gather();
		out.println(ReflexTool.class.getDeclaredField("aStrings").getGenericType().getTypeName());
    }
}



@Retention(RetentionPolicy.RUNTIME)
 @interface Persisen {
	String table();
}

@Retention(RetentionPolicy.RUNTIME)
 @interface Fiel {
	String name();
	String type();
}
class Test{
	
	@Mytag
	@Mytag
	private  int maptyu;
	
	
}
class MyProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> arg0, RoundEnvironment arg1) {
		for (Element el : arg1.getElementsAnnotatedWith(Persisen.class)) {
			el.getSimpleName();
			for (Element f : el.getEnclosedElements()) {
				if (f.getKind() == ElementKind.FIELD) {
					f.getAnnotation(Fiel.class);
				}
			}
		}
		return false;
	}
	
}
class MyInvocationHandler implements InvocationHandler {
	private Object orgObject;
	
	public MyInvocationHandler(Object orgObject) {
		this.orgObject = orgObject;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		 Object rst = method.invoke(orgObject, args);
		out.println(rst==null);
		 return rst;
	}
	
}

interface Work {
	public void gather();
	
}
class WorkImpl implements Work {

	@Override
	public void gather() {
		out.println("fun");
	}
	
}
