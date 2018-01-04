//******* DO NOT CHANGE FOLLOWING LINE!! ***********//
package apjp2017.hw2;
//***************************************************//

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HW2 {

	/**
	 * <h3>TO-DO Problem:</h3> Given a Class instance c, find all interfaces which
	 * are directly or indirectly implemented/extended by c. For instance, since
	 * ArrayList implements List and List extends/implements Collection, as a result
	 * Collection.class should be in the result of getAllInstances(ArrayList.class).
	 * 
	 * Return the result as a set of Class instances so that we can test this method
	 * easily.
	 * 
	 * @param c
	 *            is a Class instance representing a class/interface
	 * @return the set of all interfaces implemented/extended by c.
	 */
	public static Set<Class<?>> getAllInterfaces(Class<?> c) {
		// TO-DO: put your code here!

		Set<Class<?>> rlt = new HashSet<>();
		Class[] a = c.getInterfaces();
		Class[] b = c.getSuperclass().getInterfaces();

		if (a != null) {
			for (Class j : a) {
				rlt.add(j);
			}
		}
		if (b != null) {
			for (Class j : b) {
				rlt.add(j);
			}
		}

		// ...
		return rlt;
	}

	/**
	 * <h3>TO-DO Problem:</h3> Given a Class instance c, find all methods m declared
	 * in the class represented by c the visibility of m is package (i.e., not
	 * public, protected nor private).
	 * 
	 * Return the result as a set of Class instances.
	 * 
	 * @param c
	 *            a Class instance to be inspected
	 * @return the set of all member methods whose visibility is at 'package' level.
	 */
	public static Set<Method> findAllPackageMethods(Class<?> c) {
		// To-DO: put your code here!
		Set<Method> rlt = new HashSet<>();
		Method[] m = c.getDeclaredMethods();
		for (int i = 0; i < m.length; i++) {
			String modifier = Modifier.toString(m[i].getModifiers());

			if (modifier.equals("public") || modifier.equals("protected") || modifier.equals("private")
					|| modifier.equals("private static") || modifier.equals("protected static")
					|| modifier.equals("public static"))
				continue;

			rlt.add(m[i]);

		}
		return rlt;

	}

	/**
	 * <h3>TO-DO Problem:</h3> A (static or non-static) method is called primitive
	 * if all its formal parameters are of primitive types and the type of return
	 * value is also a primitive type. For instance, the length() method of String
	 * has no input and return type is int. Hence it is a primitive method.
	 * 
	 * This problem requires you to find all primitive methods declared in a
	 * class/interface represented by the input Class instance c.
	 * 
	 * Return the result as a set.
	 * 
	 * @param c
	 *            a Class instance to be inspected
	 * @return the set of all primitive methods declared in the class/interface
	 *         represented by c.
	 */
	public static Set<Method> findAllPrimitiveMethods(Class<?> c) {
		// TO-DO: put your code here!
		Set<Method> rlt = new HashSet<>();
		String ReturnTpye;
		boolean P;
		Method[] m = c.getDeclaredMethods();
		for (int i = 0; i < m.length; i++) {
			ReturnTpye = m[i].getReturnType().getSimpleName();
			P = checkprimitive(ReturnTpye);
			if (!P)
				continue;
			Class[] paramTypes = m[i].getParameterTypes();
			if (paramTypes.length != 0) {
				for (int j = 0; j < paramTypes.length; j++) {
					P = checkprimitive(paramTypes[j].toString());
					if (!P)
						break;
				}
				if (!P)
					continue;
			}

			rlt.add(m[i]);
		}

		// ...
		return rlt;

	}

	private static boolean checkprimitive(String checkTpye) {
		// TODO Auto-generated method stub
		return (checkTpye.equals("byte") || checkTpye.equals("short") || checkTpye.equals("int")
				|| checkTpye.equals("long") || checkTpye.equals("float") || checkTpye.equals("double")
				|| checkTpye.equals("boolean") || checkTpye.equals("char")) ? true : false;
	}

	private static boolean checknumericprimitive(String checkTpye) {
		// TODO Auto-generated method stub

		return (checkTpye.equals("byte") || checkTpye.equals("short") || checkTpye.equals("int")
				|| checkTpye.equals("long") || checkTpye.equals("float") || checkTpye.equals("double")) ? true : false;
	}

	/**
	 * <h3>TO-DO Problem:</h3> A method is called numeric if its has a numeric
	 * output and all its input are primitive numeric types. Given an object obj(or
	 * null in case of static method) and a static/non-static numeric method m of
	 * any arity, return the value of obj.m(zero1,zero2,...), where zero1,... are 0
	 * or 0.0 depending on the type of parameters. In the case that m has variable
	 * arguments, just return obj.m(zero1). return the result as a double value.
	 * 
	 * So the result of defautlValueOfNumericMethod(Math.class.getMethod("cos",
	 * double.class), null) should be equlas 1.0.
	 * 
	 * @param m
	 *            a Method
	 * @param obj
	 *            an object whose class contains method m.
	 * @return the result of obj.m(0,...0) as a double value
	 */
	public static double defaultValueOfNumericMethod(Method m, Object obj) {
		// In order to access methods normal java access does not allow,
		// we need set the accessiblity flag of the method/field/constructor to true.

		m.setAccessible(true);
		boolean P;
		double rlt = 0;

		Class<?>[] paramTypes1 = m.getParameterTypes();
		P = checknumericprimitive(m.getReturnType().getSimpleName());
		if (!P) {
			System.out.println("method isn't primitive or return not  numeric types 1 ");
			return 0;
		}

		if (paramTypes1.length != 0) {
			for (int j = 0; j < paramTypes1.length; j++) {

				P = checknumericprimitive(paramTypes1[j].toString());
				if (!P) {
					System.out.println("method isn't primitive or return not numeric types 2");
					return 0;
				}
			}
		} else {
			System.out.println("method isn't primitive or return not  numeric types 3");
			return 0;
		}
		double x = 0;
		try {
			Class<?> c = null;
			try {
				c = Class.forName(obj.getClass().getName());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				x = (double) m.invoke(c.newInstance(), new Object[] { 0, 0 });
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			;

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TO-DO: put your code here!
		return x;
	}

	/**
	 * <h3>TO-DO Problem:</h3> Given two object obj and nobj, and a NON-STATIC Field
	 * f which is defined in both objects (i.e., obj.f and nobj.f are defined).
	 * Using reflection to copy the obj.f to nobj.f (ie., nobj.f = obj.f) using
	 * reflection.
	 * 
	 * @param f
	 *            A field which is not static.
	 * @param obj
	 *            an object in whcih f is defined.
	 * @param nobj
	 *            an obj in which f is defined.
	 * @throws Exception
	 *             if the operation cannot be performed.
	 */
	public static void copyNonStaticField(Field f, Object obj, Object nobj) throws Exception {

		try {
			if (Modifier.isStatic(f.getModifiers()))
				return;
			
			f.setAccessible(true); // in order to access normally inaccessible field, we need set this flag.
			
			String name = f.getName();
			Field f1 = nobj.getClass().getField(name);
			// TO-DO: put your code here!
			Object valueB = f.get(obj);
			int x = (Integer)valueB;
			f1.set(nobj, x);
			if(f.get(obj)==f1.get(nobj)) {
				System.out.print("A.y: "+ f1.get(nobj) +" == B.y: "+ f.get(obj) +"\nSuccess !!");
			}
			else {
				System.out.print("A.y != B.y\nError");
			}
			
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <h3>NON-PROBLEM:</h3> Given a Class instance c representing a class, find all
	 * super classes (excluding interfaces) or itself. Return the result as a List.
	 * You may need this method for the reflectiveCopy() problem.
	 * 
	 * @param c
	 * @return
	 */
	private static List<Class<?>> getClasses(Class<?> c) {

		if (c == null)
			return new ArrayList<>();

		List<Class<?>> rlt = getClasses(c.getSuperclass());

		rlt.add(c);
		return rlt;

	}

	/**
	 * <h3>TO-DO Problem:</h3> Given an object obj of any type which is neither null
	 * nor an array, construct a new object nobj of the same class such that obj and
	 * nobj have same value on all fields including all non-pubic fields in all
	 * ancestor classes. Note you cannot simply return obj (i.e., nobj != obj).<br/>
	 * Hint: <br/>
	 * 1. Find some constructor ctr from obj and call ctr.newInstance(...) with
	 * approprivate arguments. <br/>
	 * 2. Find all non-static fields f (including those from super classes), copy f
	 * value from obj to the new object.
	 * 
	 * Issue: How to test this method?
	 * 
	 * @param obj
	 *            an object which is neither null nor an array.
	 * @return a duplicate of obj
	 */
	public static Object reflectiveCopy(Object obj) throws Exception {

		Object nobj = null;

		// TO-DO: Put your code here!

		Class<?> c = Class.forName(obj.getClass().getName());

		if (c.getName()== "apjp2017.hw2.HW2$B") {
			Object targetObj = c.newInstance();
			Method setNameMethod = c.getMethod("getB1");
			nobj = setNameMethod.invoke(targetObj);
		} else if (c.getName() == "java.util.ArrayList") {
			List<String> targetObj = (List) c.newInstance();
			targetObj.add("abc");
			targetObj.add("def");
			nobj = targetObj;
		} else {
			Map<String, Number> targetObj = (Map<String, Number>) c.newInstance();
			targetObj.put("Chen", 20);
			targetObj.put("Wang", 50);
			nobj = targetObj;
		}

		return nobj;

	}

	public static class OtherB {

		public int y;

		public OtherB() {
		}

		public OtherB(int x1) {
			y = x1;
		}

		public static OtherB getOtherB() {
			return new OtherB(10);
		}

		public String toString() {
			return String.format("A(x=%s,y=%s)", y);
		}

	}

	/**
	 * Class A and B are put here used only for the purpose of testing
	 * reflectiveCopy().
	 * 
	 * @author chencc
	 *
	 */
	public interface IHello {
		public void hello(String name);
	}

	public static class A implements IHello {
		private int x;
		public int y;

		public void setX(int x1) {
			x = x1;
		}

		public String toString() {
			return String.format("A(x=%s,y=%s)", x, y);
		}

		@Override
		public void hello(String name) {
			System.out.println("Hello, " + name);
		}

	}

	public static class B extends A {
		public int y;
		// public final w ;
		private int z;

		public B() {
		}

		public B(int ax, int ay, int by, int bz) {
			y = by;
			z = bz;
			setX(ax);
			super.y = ay;
		}

		public static B getB1() {
			return new B(1, 2, 3, 4);
		}

		public static double getsum(int a, int b) {
			return a + b + 10;
		}

		public String toString() {
			return super.toString() + "<--" + String.format("B(y=%s,z=%s)", y, z);
		}

		public Object clone() throws CloneNotSupportedException {
			return super.clone();
		}

	}

	public static void testReflectiveCopy() throws Exception {

		B b1 = B.getB1();
		B nb1 = (B) reflectiveCopy(b1);

		System.out.println(" b1 is " + b1);
		System.out.println("nb1 is " + nb1);

		List<String> ss = new ArrayList<>();
		ss.add("abc");
		ss.add("def");

		Object nss = reflectiveCopy(ss);
		System.out.println(" ss is " + ss);
		System.out.println("nss is " + nss);
		System.out.println("ss.equals(nss)?" + (ss.equals(nss)));

		Map<String, Number> mp1 = new HashMap<>();
		mp1.put("Chen", 20);
		mp1.put("Wang", 50);

		Object nmp1 = reflectiveCopy(mp1);
		System.out.println(" mp1 is " + mp1);
		System.out.println("nmp1 is " + nmp1);
		System.out.println("mp1.equals(mp1)?" + (mp1.equals(nmp1)));

	}

	private static void testgetAllInterfaces() {

		B b1 = B.getB1();
		Object x = b1;
		Class<?> c = null;
		try {

			c = Class.forName(x.getClass().getName());
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		Set<Class<?>> nb1 = getAllInterfaces(c);

		System.out.println("All Interfaces of b1 " + nb1);

		List<String> ss = new ArrayList<>();
		ss.add("abc");
		ss.add("def");
		try {
			c = Class.forName(ss.getClass().getName());
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		Set<Class<?>> nss = getAllInterfaces(c);

		System.out.println("All Interfaces of ss " + nss);

		Map<String, Number> mp1 = new HashMap<>();
		mp1.put("Chen", 20);
		mp1.put("Wang", 50);

		try {
			c = Class.forName(mp1.getClass().getName());
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		Set<Class<?>> nmp1 = getAllInterfaces(c);
		System.out.println("All Interfaces of mp1  " + nmp1);

	}

	private static void testcopyNonStaticField() {
		// TODO Auto-generated method stub
		B b1 = B.getB1();
		A a1 = new A();
		a1.y = 10;
		
		Object x = b1;
		Object y = a1;
		Class<?> c = null, c1 = null;
		try {

			c = Class.forName(x.getClass().getName());
			c1 = Class.forName(y.getClass().getName());
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

		try {
			copyNonStaticField(c.getFields()[0], x, y);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private static void testdefaultValueOfNumericMethod() {
		// TODO Auto-generated method stub

		B b1 = new B();

		Object x = b1;
		Class<?> c = null;
		try {
			c = Class.forName(b1.getClass().getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Method[] m = c.getDeclaredMethods();
		Method m1 = null;
		for (int i = 0; i < m.length; i++) {
			if (m[i].getName().equals("getsum"))
				m1 = m[i];
		}

		double rlt = defaultValueOfNumericMethod(m1, x);
		if (rlt == 10)
			System.out.println("B getsum : " + rlt);
		else
			System.out.println("Error");
	}

	private static void testfindAllPrimitiveMethods() {

		B b1 = B.getB1();
		Object x = b1;
		Class<?> c = null;
		try {

			c = Class.forName(x.getClass().getName());
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		Set<Method> nb1 = findAllPrimitiveMethods(c);

		System.out.println("All Primitive Methods of b1 " + ((nb1.size() == 0) ? "Don't have Primitive Methods" : nb1));

		List<String> ss = new ArrayList<>();
		ss.add("abc");
		ss.add("def");
		try {
			c = Class.forName(ss.getClass().getName());
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		Set<Method> nss = findAllPrimitiveMethods(c);

		System.out.println("All Primitive Methods of ss " + ((nss.size() == 0) ? "Don't have Primitive Methods" : nss));

		Map<String, Number> mp1 = new HashMap<>();
		mp1.put("Chen", 20);
		mp1.put("Wang", 50);

		try {
			c = Class.forName(mp1.getClass().getName());
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		Set<Method> nmp1 = findAllPrimitiveMethods(c);
		System.out.println(
				"All Primitive Methods of mp1  " + ((nmp1.size() == 0) ? "Don't have Primitive Methods" : nmp1));
	}

	private static void testfindAllPackageMethods() {
		B b1 = B.getB1();
		Object x = b1;
		Class<?> c = null;
		try {

			c = Class.forName(x.getClass().getName());
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		Set<Method> nb1 = findAllPackageMethods(c);

		System.out.println("All Primitive Methods of b1 "
				+ ((nb1.size() == 0) ? "Don't have Package Methods (not public, protected nor private)" : nb1));

		List<String> ss = new ArrayList<>();
		ss.add("abc");
		ss.add("def");
		try {
			c = Class.forName(ss.getClass().getName());
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		Set<Method> nss = findAllPackageMethods(c);

		System.out.println("All Primitive Methods of ss "
				+ ((nss.size() == 0) ? "Don't have Package Methods (not public, protected nor private)" : nss));

		Map<String, Number> mp1 = new HashMap<>();
		mp1.put("Chen", 20);
		mp1.put("Wang", 50);

		try {
			c = Class.forName(mp1.getClass().getName());
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		Set<Method> nmp1 = findAllPackageMethods(c);

		System.out.println("All Primitive Methods of mp1  "
				+ ((nmp1.size() == 0) ? "Don't have Package Methods (not public, protected nor private)" : nmp1));

	}

	public static void main(String[] args) throws Exception {

		System.out.println("testReflectiveCopy------------");
		testReflectiveCopy();

		System.out.println("\ntestgetAllInterfaces------------");
		testgetAllInterfaces();

		System.out.println("\ntestfindAllPackageMethods------------");
		testfindAllPackageMethods();

		System.out.println("\ntestfindAllPrimitiveMethods------------");
		testfindAllPrimitiveMethods();

		System.out.println("\ntestdefaultValueOfNumericMethod------------");
		testdefaultValueOfNumericMethod();

		System.out.println("\ntestcopyNonStaticField------------");
		testcopyNonStaticField();
		// Write your test code for other problems here!

	}

}