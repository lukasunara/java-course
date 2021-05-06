package hr.fer.oprpp1.custom.collections.demo;

import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;
import hr.fer.oprpp1.custom.collections.List;
import hr.fer.oprpp1.custom.collections.Tester;

/**
 * Class Demo is used to demonstrate how does the whole project in package
 * hr.fer.oprpp1.custom.collections work.
 * 
 * @author lukasunara
 *
 */
public class Demo {

	static class EvenIntegerTester implements Tester {
		
		public boolean test(Object obj) {
			if(!(obj instanceof Integer)) return false;
			
			Integer i = (Integer)obj;
			
			return i % 2 == 0;
		}
	
	}

	public static void main(String[] args) {
		try {
			getterExample1();
		} catch(NoSuchElementException ex) {
			System.out.println(ex);
		}
		System.out.println();

		try {
			getterExample2();
		} catch(NoSuchElementException ex) {
			System.out.println(ex);
		}
		System.out.println();
		
		try {
			getterExample3();
		} catch(NoSuchElementException ex) {
			System.out.println(ex);
		}
		System.out.println();
		
		try {
			getterExample4();
		} catch(NoSuchElementException ex) {
			System.out.println(ex);
		}
		System.out.println();

		try {
			getterExample5();
		} catch(NoSuchElementException ex) {
			System.out.println(ex);
		}
		System.out.println();
		
		try {
			arrayCollectionAltered();
		} catch(NoSuchElementException | ConcurrentModificationException ex) {
			System.out.println(ex);
		}
		System.out.println();
		
		try {
			listCollectionAltered();
		} catch(NoSuchElementException | ConcurrentModificationException ex) {
			System.out.println(ex);
		}
		System.out.println();
		
		try {
			processorArrayExample();
		} catch(NoSuchElementException | ConcurrentModificationException ex) {
			System.out.println(ex);
		}
		System.out.println();
		
		try {
			processorListExample();
		} catch(NoSuchElementException | ConcurrentModificationException ex) {
			System.out.println(ex);
		}
		System.out.println();
		
		testerExample1();
		System.out.println();
		
		testerExample2();
		System.out.println();
		
		listInterfaceExample();
		System.out.println();
	}
	
	private static void getterExample1() {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();

		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
	}
	
	private static void getterExample2() {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();

		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
	}

	private static void getterExample3() {
		Collection col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
	}
	
	private static void getterExample4() {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		
		ElementsGetter getter1 = col.createElementsGetter();
		ElementsGetter getter2 = col.createElementsGetter();
		
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
	}
	
	private static void getterExample5() {
		Collection col1 = new ArrayIndexedCollection();
		Collection col2 = new LinkedListIndexedCollection();
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		col2.add("Jasmina");
		col2.add("Štefanija");
		col2.add("Karmela");
		
		ElementsGetter getter1 = col1.createElementsGetter();
		ElementsGetter getter2 = col1.createElementsGetter();
		ElementsGetter getter3 = col2.createElementsGetter();
		
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
	}
	
	private static void arrayCollectionAltered() {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		
		col.clear();
		System.out.println("Jedan element: " + getter.getNextElement());
	}
	
	private static void listCollectionAltered() {
		Collection col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		
		col.clear();
		System.out.println("Jedan element: " + getter.getNextElement());
	}
	
	private static void processorArrayExample() {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		
		getter.getNextElement();
		getter.processRemaining(System.out::println);
	}

	private static void processorListExample() {
		Collection col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		
		getter.getNextElement();
		getter.processRemaining(System.out::println);
	}
	
	private static void testerExample1() {
		Tester t = new EvenIntegerTester();
		
		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));
	}
	
	private static void testerExample2() {
		Collection col1 = new LinkedListIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
		
		col1.add(2);
		col1.add(3);
		col1.add(4);
		col1.add(5);
		col1.add(6);
		
		col2.add(12);
		col2.addAllSatisfying(col1, new EvenIntegerTester());
		
		col2.forEach(System.out::println);
	}
	
	private static void listInterfaceExample() {
		List col1 = new ArrayIndexedCollection();
		List col2 = new LinkedListIndexedCollection();
		
		col1.add("Ivana");
		col2.add("Jasna");
		
		Collection col3 = col1;
		Collection col4 = col2;
		
		col1.get(0);
		col2.get(0);
//		col3.get(0); // undefined for type Collection
//		col4.get(0); // undefined for type Collection
		
		col1.forEach(System.out::println); // Ivana
		col2.forEach(System.out::println); // Jasna
		col3.forEach(System.out::println); // Ivana
		col4.forEach(System.out::println); // Jasna
	}
	
}
