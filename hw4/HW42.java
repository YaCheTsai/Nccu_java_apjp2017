package apjp2017.hw4;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * HW42 is used to test your ability to use Java IO stream. <br/>
 * 
 *  
 * HW42 contains 5 nested  static classes: OrderedItems, Person, Man, Woman and Family. <br/>
 * 
 * The class OrderedItems is used to test your ability to use DataOutputStream and DataInputStream.
 * It require you to implement one method: readFromDataInput. <br/>
 * 
 * The class Family is used to test your ability to customize the serialization of object using standard ObjectOutputStream/ObjectInputStream.
 * This class requires that, after studying how a Family object is written using customized writeObject(.) method,  
 * the students implement the method: readObject(.) so that an object serialized previously could be correctly recovered.
 * 
 * The other 3 classes are utility classes used  by the Family class.  
 * 
 * @author chencc
 *
 */
public class HW42 {

	/**
	 * The class is used to practice writing/reading objects to/from IO streams
	 * using DataOutputStream/DataInputStream. NOTE: In this class, you only need to
	 * complete the method: <b style='color:blue'>readFromDataInput</b>. All others
	 * are used for this purpose.
	 * 
	 * @author chencc
	 *
	 */
	public static class OrderedItems {

		String[] productId; // product id of each ordered item
		int[] amount; // amount for each item ordered
		double[] unitPrice; // unit price for each ordered item

		// total number of items ordered. Is is assumed that
		// the length of the above arrays is equal to this field.
		int numberOfItems;

		public OrderedItems(int nItems, String[] pod, int[] amt, double[] uprice) {
			numberOfItems = nItems;
			productId = pod;
			amount = amt;
			unitPrice = uprice;
			
		}

		public boolean equals(Object o) {
			if (!(o instanceof OrderedItems)) {
				return false;
			}
			OrderedItems inv = (OrderedItems) o;
			return numberOfItems == inv.numberOfItems && Arrays.equals(amount, inv.amount)
					&& Arrays.equals(unitPrice, inv.unitPrice) && Arrays.equals(productId, inv.productId);

		}

		public int hashCode() {
			return Arrays.hashCode(amount) ^ Arrays.hashCode(unitPrice) ^ Arrays.hashCode(productId) ^ numberOfItems;
		}
		
		public String toString() {
		  StringBuffer sb = new StringBuffer(); 	
		  sb.append("Number of items:").append(numberOfItems).append("\n");
		  sb.append("    ProdIds:");		  
		  for(String prod : productId) {
			  sb.append(prod).append("|") ;			  
		  }
		  sb.append("\n    amounts:");		  
		  for(double amt : amount) {
			  sb.append(amt).append("|") ;			  
		  }
		  sb.append("\nunit prices:");		  
		  for(double p : unitPrice) {
			  sb.append(p).append("|") ;			  
		  }
		  
		  sb.append("\n");
		  return sb.toString();	  
			
		}

		/**
		 * This method demonstrates how an object of OrderedItems can be written to a
		 * DataOutputStream using primitive write methods provide by DataOutputStream
		 * class.
		 * 
		 * @param orderedItems
		 *            an object to be written out.
		 * @param out
		 *            the destination DataOutputStream
		 * @throws Exception
		 *             an exception indicating that orderedItems cannot be written to
		 *             out for some reason.
		 */
		public static void writeToDataOutput(OrderedItems orderedItems, DataOutputStream out) throws Exception {
			out.writeInt(orderedItems.numberOfItems);
			for (int k = 0; k < orderedItems.numberOfItems; k++) {				
				out.writeUTF(orderedItems.productId[k]);
				out.writeInt(orderedItems.amount[k]);
				out.writeDouble(orderedItems.unitPrice[k]);
			}
			out.close();
		}

		/**
		 * <b style='color:blue'>This method REQURIES the students to implement it.</b>
		 * 
		 * This method is used to to read back an object of OrderedItems from a
		 * DataInputStream. It is supposed that the DataOutputStream was got from a data
		 * source to which an OriteredItems object was written previously using
		 * DataOutputStream. The returned object of this method of course should be
		 * equal to the original object written to the data source.
		 * 
		 * @param in
		 *            a DataInputStream
		 * @return an OrderedItems obejct
		 * @throws Exception
		 *             an exception indicating that an OrderedItems object cannot be
		 *             read back for some reason.
		 * 
		 */
		public static OrderedItems readFromDataInput(DataInputStream in) throws Exception {
			// TO-DO task: remove the line below and put your code here !!
			int item = in.readInt();
			int[] amount = new int[item];
			String[] productId = new String[item];
			double[] unitPrice = new double[item];
			int i = 0;
			
			while(in.available() > 0) {
				productId[i] = in.readUTF();
				amount[i] = in.readInt();
				unitPrice[i] = in.readDouble();
				i++;
			}
			
			OrderedItems result = new OrderedItems(item, productId, amount, unitPrice);
			
			return result;

			

		}

	
	}

	public static class Family implements Serializable {
		
		public String familyName ; 

		transient Set<Person> children;
		transient Man father;
		transient Woman mother;

		public Family(String name, Man f, Woman m, List<Person> cs) {
			children = new LinkedHashSet<>(cs);
			father = f;
			mother = m;
			familyName = name;
			makeLinks();
		}

		 void makeLinks() {
			father.spouse = mother;
			mother.spouse = father;
			father.children = children;
			mother.children = children;
			for (Person c : children) {
				c.father = father;
				c.mother = mother;
			}

		}
		
		boolean isValid() {
			
			return true;
          			
		}

		public boolean equals(Object o) {
			
			if(!(o instanceof Family)) return false ;
			Family f = (Family) o;
			
			return  this.familyName .equals(f.familyName)&&
					this.father.equals(f.father) &&
					this.mother.equals(f.mother) &&
					this.children.equals(f.children) ;			
			
		}
		
		public int hashCode() {
			return familyName.hashCode() ^
				   father.hashCode() ^
				   mother.hashCode() ^
				   children.hashCode();
				   
		} 
		public String toString() {
			return String.format("family_name: %s ** father:%s,mother:%s, children:%s\n", familyName, father, mother, children);
		}

		/**
		 * In order to reduce the used serialization storage, we do not write the whole
		 * Family object directly to the ObjectOutputStream. Instead, we write
		 * names/genders of all family members to the output stream. As the method
		 * indicates, the first/second strings are names of father and mother, and
		 * following strings are names/genders of all children in the family.
		 * 
		 * @param out
		 *            the ObjectOutputStream
		 * @throws Exception
		 *             an exception indicates that the writing is not successfully.
		 */
		private void writeObject(ObjectOutputStream out) throws Exception {

			out.defaultWriteObject();

			// NOTE: we can write a String using either writeObject() or writeUTF().
			// WriteUTF() is usually more efficient. However, remember that in the 'read'
			// part, we should use
			// the corresponding readObject() or readUTF(); otherwise, an error will occurs!
			// Also remember that the return type of ObjectInputStream#readObject() is
			// Object while
			// return type of ObjectInputStream#readObject() is String.
			out.writeObject(father.name);
			out.writeUTF(mother.name);
			for (Person p : children) {
				out.writeUTF(p.name);
				out.writeUTF(p.gender);
			}
			out.close();

		}

		/**
		 * <b style='color:blue'>NOTE: This is the method for the students to implement.
		 * </b><br/>
		 * 
		 * Use this method to recover a Family object (as 'this') which was written to a
		 * data source previously with an ObjectOutputStream corresponding to the input
		 * ObjectInputStream. Note that the object written to the data source is
		 * customized by the above writeObject() method. You should consult the above
		 * method to determine using what method and in what order so that the object
		 * can be correcly recovered.
		 * 
		 * @param in
		 *            the ObejctInptuStream from which 'this' object is to be recovered.
		 * @throws Exception
		 *             an exception indicates that the writing is not successfully.
		 */
		private void readObject(ObjectInputStream in) /*throws Exception*/ {

			// Problems you may be stuck here:1. when to terminate the reading of children?
			// ANS: You should use try-catch-finally statement and keep reading of next
			// child name/gender in the try-block until an java.io.EOFException is thrown,
			// when yon can close the input stream in the finally-clause.
			// 2: Encounter a ClassCastException: String cannot be cast to Family.
			// ==> remember to call in.defautlReadObject() first!
			// TO-DO: put your code here!
			LinkedHashSet<Person> c = new LinkedHashSet<Person>();  
			String name,gender ;
			
			try {
				
				familyName = (String) in.readObject();
				String f = (String) in.readObject();
				String m = in.readUTF();
				while(in.available() > 0) {
					name = in.readUTF();
					gender = in.readUTF();
					if(gender.equals("male")) {
						c.add(new Man(name));
					}
					else {
						c.add(new Woman(name));
					}
					
				}
				father = new Man(f);
				mother = new Woman(m);
				children = c;
				
				makeLinks();

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
		}

		public static Family getAFamily() {

			return new Family("Tan", new Man("m1"), new Woman("w1"),
					Arrays.asList(new Man("c1"), new Woman("c2"), new Man("c3")));
		}

	}

	/**
	 * 
	 * There is no method you need to implement in this class. This us a utility
	 * class used by the Family class. <b style='color:blue'>NOTE: YOU CANNOT REMOVE
	 * the 'transient' modifiers given in the fields of this class. </b>
	 * <b style='color:blue'>NOTE: Since all link fields except name/gender are
	 * transient, these fields will not be written/read using standard
	 * /writeObject method.</b>
	 * 
	 * 
	 * @author chencc
	 *
	 */
	public static class Person implements Serializable {

		private static final long serialVersionUID = 1L;

		public Person(String name, String gender) {
			this.name = name;
			this.gender = gender;
		}

		String name;
		String gender;
		transient Person spouse;
		transient Set<Person> children;
		transient Person father;
		transient Person mother;

		public boolean equals(Object o) {
			if (o instanceof Person) {
				return name.equals(((Person) o).name);
			}
			return false;
		}

		public int hashCode() {
			return name.hashCode();
		}

		public String toString() {
			return name;
		}

	}

	/**
	 * 
	 * There is no method you need to implement in this class. This is a utility
	 * class used by the Family class.
	 * 
	 * <b style='color:blue'> NOTE: Since super class Person implements Serializable
	 * interface, by inheritance, this class is also Serializable. </b>
	 * 
	 * 
	 * @author chencc
	 *
	 */
	public static class Man extends Person {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Man(String name) {
			super(name, "male");
		}
	}

	/**
	 * 
	 * There is no method you need to implement in this class. This is a utility
	 * class used by the Family class.
	 * 
	 * <b style='color:blue'> NOTE: Since super class Person implements Serializable
	 * interface, by inheritance, this class is also Serializable. </b>
	 * 
	 * 
	 * @author chencc
	 *
	 */

	public static class Woman extends Person {
		public Woman(String name) {
			super(name, "female");
		}

	}
	
	static boolean isValidFamily(Family f) {
		if(f.father.spouse != f.mother) return false ;
		if(f.father != f.mother.spouse) return false ;
		if(!(f.father.children.equals(f.children))) return false;
		if(!(f.mother.children.equals(f.children))) return false;
		for(Person p : f.children) {
			if(p.getClass() == Person.class) return false;
			if(p.father != f.father) return false;
			if(p.mother != f.mother) return false;
		}
		return true;		
	}

	public static void testFamily() throws Exception {

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream oout = new ObjectOutputStream(bout);

		Family fam = Family.getAFamily();
		
		System.out.println("Original family:");
		System.out.println(fam);
		System.out.println("Original Family is a valid family?" + isValidFamily(fam));
		System.out.println("========================");
		
		
		oout.writeObject(fam);		

		// *************************************************

		ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(bout.toByteArray()));
			
			
		Family famRead = (Family) oin.readObject();

		System.out.println("Family read back:");
		System.out.println(famRead);

		System.out.println("Original Family == Family read back?" + fam.equals(famRead));
		System.out.println("Family read back is a valid family?" + isValidFamily(famRead));
		
		
		
	}

	
	/**
	 * You OrderedItems class implementation should pass this test and return
	 * 'true'.
	 * 
	 * @return true if the data read back is equal to the original OrderedItems.
	 * @throws Exception
	 *             an exception indicating this method cannot be performed
	 *             successfully.
	 */

	public static void testReadFromDataInput() throws Exception {

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);

		OrderedItems inv = new OrderedItems(5,
				new String[] { "item1", "item2", "item 3", "item 4", "item 5" },
				new int[] { 4, 5, 6, 7, 8 },
				new double[] { 2.3, 10.5, 4.0, 3.2, 6.0 }	);

		System.out.println("Original ordered items:");
		System.out.println(inv);
		
		OrderedItems.writeToDataOutput(inv, dout);
		
		System.out.println("========================");

		// *************************************************

		DataInputStream din = new DataInputStream(new ByteArrayInputStream(bout.toByteArray()));

		OrderedItems invRead = OrderedItems.readFromDataInput(din);
		
		System.out.println("Ordered items read back:");
		System.out.println(invRead);

		System.out.println("OrderedItems readback == Orginal OrderedItem ?" + inv.equals(invRead));
		
	}
	public static void main(String[] args) throws Exception {

		System.out.println("PROBLEM HW42 TEST 1:");
		testReadFromDataInput();
		
		System.out.println("*********************************\n");
		System.out.println("PROBLEM HW42 TEST 2:");
        testFamily();	

		

	}

}