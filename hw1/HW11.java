package apjp2017.hw1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import apjp2017.hw1.HW11.BasicColor;
import static apjp2017.hw1.HW11.BasicColor.*;


public class HW11 {
	
	
	
	/** It is well known that a color may be represented as a composition of 3
	 * primary colors: (red, green,blue). Suppose each primary color component
	 * is represented as a value between 0 and 255. So, for example, the white
	 * color is composed of (red,green,blue)=(255,255,255) and represented
	 * compactly as an int (red << 24 + green << 16 + blue << 8), and CYAN is
	 * composed of (red,green, blue) = (0, 255, 255) or as a compact int (0<<24
	 * + 255<< 16 + 255<< 8).
	 * 
	 * This homework requires you to define an enumeration type called
	 * BasicColor for the eight combinations of primary colors: BLACK, RED,
	 * BLUE, GREEN, CYAN, MAGENTA, YELLOW, and WHITE, each represented
	 * internally compactly as an int. In addition, you are required to
	 * implement also miscellaneous methods documented in the body.
	 *
	 * 
	 */

	public enum BasicColor {
		// BLACK(?), RED(?), BLUE(), GREEN(), CYAN(), MAGENTA(), YELLOW(),
		// WHITE(),;
		// replace the following code with yours!
		BLACK(0, 0, 0), BLUE(0, 0, 255), GREEN(0, 255, 0), CYAN(0, 255, 255), RED(255, 0, 0), MAGENTA(255, 0, 255), YELLOW(255, 255, 0), WHITE(255, 255, 255);

		// rgb = r<<24 + g << 16 + b << 8 where r,g and b are an integer in [0,
		// 255]
		// note: since we use the left 8 bits of an int to represent the red
		// value, the
		// internal color value would be negative if the basic color contains
		// red(=255) color.
		private int rgb;

		private static final int NO_BASIC_COLORS = 8;

		private BasicColor(int r, int g, int b) {
			// Replace the following code by yours!
			
			this.rgb = (r << 16) + (g << 8) + b;
					
			// Don't change following code!
			if (r > 255 || r < 0 || g > 255 || g < 0 || b > 255 || b < 0) {
				throw new IllegalArgumentException();
			}

			// Don't change following code!
			if (this.ordinal() >= NO_BASIC_COLORS) {
				throw new RuntimeException("Could not create duplicated BasicColor!");
			}

		}

		/**
		 * Find the red component of this color. The result is either 0 or 255.
		 */
		public int getRed() {
			// replace the following code by yours!
			int rgb = getRGB();
			int R = rgb>>>16;
			return R;
		}

		/**
		 * Find the green component of this color. The result is either 0 or
		 * 255.
		 */
		public int getGreen() {
			// replace the following code by yours!
			int rgb = getRGB();
			int G = rgb >>> 8;
			int R = rgb >>> 16;
			R = R << 8;
			G = G - R;
			return G;
		}

		/**
		 * Find the blue component of this color. The result is either 0 or 255.
		 */
		public int getBlue() {
			// replace the following code by yours!
			int rgb = getRGB();
			int B = rgb >>> 8 ;
			 B = B << 8;
			 B = rgb - B;
			return B;
		}

		/**
		 * get the internal rgb int of this color for the purpose of test.
		 * 
		 * @return
		 */
		public int getRGB() {
			return rgb;
		}

		/**
		 * Get the complement color of this color. For instance,
		 * WHITE.complement() == BLACK.
		 * 
		 * @return the complement color of this color.
		 */
		public BasicColor complement() {
			// replace the following code by yours!
			int r = getRed();
			int g = getGreen();
			int b = getBlue();
			r = (r + 255) % 510;
			g = (g + 255) % 510;
			b = (b + 255) % 510;
			
			return getColor(r,g,b);
		}

		/**
		 * Find the resulting BasicColor consisting of primary colors common to
		 * both this and input color c.
		 * 
		 * @param c
		 *            another BasicColor
		 * @return
		 */
		public BasicColor common(BasicColor c) {
			// replace follow code by yours.
			int r = getRed();
			int g = getGreen();
			int b = getBlue();
			int Cr = c.getRed();
			int Cg = c.getGreen();
			int Cb = c.getBlue();
			int R,G,B;
			R = (r + Cr > 255)? 255 : 0;
			G = (g + Cg > 255)? 255 : 0;
			B = (b + Cb > 255)? 255 : 0;
			return getColor(R,G,B);

		}

		/**
		 * Find the resulting BasicColor consisting of primary colors contained
		 * in one and only one of both colors.
		 * 
		 * @param c
		 *            another BasicColor
		 * @return
		 */
		public BasicColor exclusiveOr(BasicColor c) {
			// replace follow code by yours.
			int r = getRed();
			int g = getGreen();
			int b = getBlue();
			int Cr = c.getRed();
			int Cg = c.getGreen();
			int Cb = c.getBlue();
			int R,G,B;
			R = (r + Cr > 255)? 0 : r + Cr;
			G = (g + Cg > 255)? 0 : g + Cg;
			B = (b + Cb > 255)? 0 : b + Cb;
			return getColor(R,G,B);
		}

		/**
		 * What will a color become if it is mixed with another color ?
		 * 
		 * @param c
		 *            a BasicColor
		 * @return
		 */
		public BasicColor mixedWith(BasicColor c) {
			// replace follow code by yours.
			int r = getRed();
			int g = getGreen();
			int b = getBlue();
			int Cr = c.getRed();
			int Cg = c.getGreen();
			int Cb = c.getBlue();
			int R,G,B;
			R = (r + Cr > 255)? 255 : r + Cr;
			G = (g + Cg > 255)? 255 : g + Cg;
			B = (b + Cb > 255)? 255 : b + Cb;
			return getColor(R,G,B);

		}

		/**
		 * Find the resulting BasicColor by combining all BasicColors given in
		 * the argument list. Return BLACK if there is no argument in the
		 * argument list.
		 * 
		 * @param colors
		 *            a BasicColor argument list
		 * @return
		 */
		public static BasicColor combine(BasicColor... colors) {
			// replace follow code by yours.
			int R = colors[0].getRed()+colors[1].getRed()+colors[2].getRed();
			int G = colors[0].getGreen()+colors[1].getGreen()+colors[2].getGreen();
			int B = colors[0].getBlue()+colors[1].getBlue()+colors[2].getBlue();
			R = (R > 255)? 255 : R;
			G = (G > 255)? 255 : G;
			B = (B > 255)? 255 : B;
			return getColor(R,G,B) == null ? BLACK : getColor(R,G,B) ;

		}
		
		
		/**
		   * Find a BasicColor whose primary color composition is as the given input arguments.
		   * Return null if there is no BasicColor with the given composition.
		   * @param r
		   * @param g
		   * @param b
		   * @return 
		   */
		  public static BasicColor getColor(int r, int g, int b){
				// replace follow code by yours.
			  if(r == 0 && g == 0 && b == 0) return BLACK;
			  else if(r == 0 && g == 0 && b == 255) return BLUE;
			  else if(r == 0 && g == 255 && b == 0) return GREEN;
			  else if(r == 0 && g == 255 && b == 255) return CYAN;
			  else if(r == 255 && g == 0 && b == 0) return RED;
			  else if(r == 255 && g == 0 && b == 255) return MAGENTA;
			  else if(r == 255 && g == 255 && b == 0) return YELLOW;
			  else if(r == 255 && g == 255 && b == 255) return WHITE;
			  else return null;
			 
		  }

		/**
		 * Return an array containing all BasicColors sorted in ascending order
		 * according to the priority of composing primary colors as specified in
		 * the input. For example, if invoking sort(BLUE, RED, GREEN), then the
		 * resulting array would have the following color priority: (BLUE first
		 * , then Red and finally GREEN) and the result would be:
		 * (red=0,green=0, blue=0), (0,255,0), (255,0,0), ... (255,255,255).
		 * 
		 * @param p1,p2,p3
		 *            is a permutation of RED, GREEN and BLUE.
		 * @return
		 */
		public static BasicColor[] sort(BasicColor p1, BasicColor p2, BasicColor p3) {
			if (!EnumSet.of(p1, p2, p3).equals(EnumSet.of(RED, GREEN, BLUE))) {
				throw new IllegalArgumentException();
			}

			// Replace following code by yours
			int s1,s2,s3;
			if(p1 == RED && p2 == GREEN && p3 == BLUE) { 
				s1 = 16;
				s2 = 8;
				s3 = 0;
				}
			else if(p1 == RED && p2 == BLUE && p3 == GREEN) { 
				s1 = 16;
				s2 = 0;
				s3 = 8;
				}
			else if(p1 == GREEN && p2 == RED && p3 == BLUE) { 
				s1 = 8;
				s2 = 16;
				s3 = 0;
				}
			else if(p1 == GREEN && p2 == BLUE && p3 == RED) { 
				s1 = 0;
				s2 = 16;
				s3 = 8;
				}
			else if(p1 == BLUE && p2 == GREEN && p3 == RED) { 
				s1 = 0;
				s2 = 8;
				s3 = 16;
				}
			else if(p1 == BLUE && p2 == RED && p3 == GREEN) { 
				s1 = 8;
				s2 = 0;
				s3 = 16;
				}
			else throw new IllegalArgumentException();
			int x[] = new int [8]; 
			BasicColor result[] = new BasicColor [8]; 
			BasicColor[] cs = BasicColor.values();
			for (int k = 0; k < BasicColor.values().length; k++) {
				
				
				x[k]= (cs[k].getRed() << s1) + (cs[k].getGreen() << s2) + (cs[k].getBlue() << s3);
				
			}
			 Arrays.sort(x);
			 int R,G,B,o;
			 for (int k = 0; k < BasicColor.values().length; k++) {
				 	int rgb = x[k];
				 	R = rgb>>>16;
				 	rgb = x[k];
					G = rgb >>> 8;
					o = rgb >>> 16;
					o = o << 8;
					G = G - o;
				 	rgb = x[k];
				 	B = rgb >>> 8 ;
				 	B = B << 8;
				 	B = rgb - B;
				 	if(p1 == RED && p2 == GREEN && p3 == BLUE) result[k] = getColor(R,G,B);	
					else if(p1 == RED && p2 == BLUE && p3 == GREEN) result[k] = getColor(R,B,G);
					else if(p1 == GREEN && p2 == RED && p3 == BLUE) result[k] = getColor(G,R,B);
					else if(p1 == GREEN && p2 == BLUE && p3 == RED) result[k] = getColor(B,R,G);
					else if(p1 == BLUE && p2 == GREEN && p3 == RED) result[k] = getColor(B,G,R);
					else if(p1 == BLUE && p2 == RED && p3 == GREEN) result[k] = getColor(G,B,R);
					else throw new IllegalArgumentException();
					
				}
            return result ;

		}

		/**
		 * Represent a color array as a string.
		 * 
		 * @param colors
		 * @return
		 */
		public static String toString(BasicColor... colors) {
			if (colors == null) return "null";
			StringBuffer sb = new StringBuffer();
			for (int k = 0; k < colors.length; k++) {
				if (k > 0)
					sb.append(",");
				sb.append(colors[k]);
			}
			return sb.toString();
		}
		
		/**
		 * Represent a color array as a rgb.
		 * 
		 * @param colors
		 * @return
		 */
		public static String toRGB(BasicColor... colors) {
			if (colors == null) return "null";
			StringBuffer sb = new StringBuffer();
			for (int k = 0; k < colors.length; k++) {
				if (k > 0)
					sb.append(",");
				sb.append(String.format("(%s,%s,%s)", colors[k].getRed(), colors[k].getGreen(), colors[k].getBlue()));
			}
			return sb.toString();
		}
		
		

	}

	

	public static void mainTest(String... args) {
		
		HW11 hw11 = new HW11();		
		System.out.println(BasicColor.toString(BasicColor.values()));
		
		hw11.testGetRed();
		hw11.testGetGreen();
		hw11.testGetBlue();
		hw11.testComplement();		 
	    hw11.testCommon();
		hw11.testExclusiveOr();
		hw11.testMixedWith() ;
		hw11.testCombine();
		hw11.testGetColor();
		hw11.testSort1();
		hw11.testSort2();

	}
	

	static int nTests = 0;   // each test may produce multiple errors!
	static int nErrors = 0;
	static int nTestErrors = 0;
	static boolean newTest = true;

	static void error() {
		error("");
	}

	static void error(String s) {
		nErrors++;
		if(newTest){
			nTestErrors ++ ;
			newTest = false;
			System.out.println(">>> new TestError! <<< ");
		}
		System.out.println("*** error " + nErrors + "*** :" + s);
	}
	
	static void error(String s, Object ... args){
		error( String.format(s, args));
	}

	static void test() {
		nTests++;
		newTest = true ;
	}
	
//	static void test(int k) {
//		nTests += k ;
//	}

	static String summary() {

		StringBuilder sb = new StringBuilder();
		sb.append("There are totally " + nTests + " full tests! \n");
		sb.append("There are " + nTestErrors + " test errors! \n");
		sb.append("There are " + nErrors + " detailed errors! \n");
		sb.append(" The passing rate is " + (nTests - nTestErrors) + "/" + nTests + "="
				+ ((nTests - nTestErrors) * 100 / nTests) + "!\n");

		int score = (nTests - nTestErrors) * 50 / nTests + 50 ;

		sb.append("The score is " + score + " provided your source passes compilation!");

		return sb.toString();

	}

	
	public final void testGetRed() {

		test();
		int[] rlt = new int[] { 0, 0, 0, 0, 255, 255, 255, 255 };
		BasicColor[] cs = BasicColor.values();
		for (int k = 0; k < BasicColor.values().length; k++) {
			if (rlt[k] != cs[k].getRed()) {
				error("the red value: " + cs[k].getRed() + " of " + cs[k] + "is not correct!");
			}
		}
	}

	
	public final void testGetGreen() {
		test();
		int[] rlt = new int[] { 0, 0, 255, 255, 0, 0, 255, 255 };
		BasicColor[] cs = BasicColor.values();
		for (int k = 0; k < BasicColor.values().length; k++) {
			if (rlt[k] != cs[k].getGreen()) {
				error("the green value: " + cs[k].getGreen() + " of " + cs[k] + "is not correct!");
			}
		}
	}

	
	public final void testGetBlue() {
		test();
		int[] rlt = new int[] { 0, 255, 0, 255, 0, 255, 0, 255 };
		BasicColor[] cs = BasicColor.values();
		for (int k = 0; k < BasicColor.values().length; k++) {
			if (rlt[k] != cs[k].getBlue()) {
				error("the blue value: " + cs[k].getBlue() + " of " + cs[k] + "is not correct!");
			}
		}
	}

	
	public final void testComplement() {
		test();
		BasicColor[] rlt = BasicColor.values();
		// reverse array!
		for (int l = 0, r = rlt.length - 1; l < r; l++, r--) {
			BasicColor tmp = rlt[l];
			rlt[l] = rlt[r];
			rlt[r] = tmp;
		}

		BasicColor[] cs = BasicColor.values(); // get another array
		for (int k = 0; k < BasicColor.values().length; k++) {
			if (rlt[k] != cs[k].complement()) {
				error("the complement Color: " + cs[k].complement() + " of " + cs[k] + "is not correct!");
			}
		}
	}

	
	public final void testCommon() {
		
		BasicColor[][] answer = new BasicColor[][]{			
			{ BLACK,BLACK,BLACK,BLACK,BLACK,BLACK,BLACK,BLACK},
			{ BLACK,BLUE,BLACK,BLUE,BLACK,BLUE,BLACK,BLUE},
			{ BLACK,BLACK,GREEN,GREEN,BLACK,BLACK,GREEN,GREEN},
			{ BLACK,BLUE,GREEN,CYAN,BLACK,BLUE,GREEN,CYAN},
			{ BLACK,BLACK,BLACK,BLACK,RED,RED,RED,RED},
			{ BLACK,BLUE,BLACK,BLUE,RED,MAGENTA,RED,MAGENTA},
			{ BLACK,BLACK,GREEN,GREEN,RED,RED,YELLOW,YELLOW},
			{ BLACK,BLUE,GREEN,CYAN,RED,MAGENTA,YELLOW,WHITE}			
		};
		
		test();
		BasicColor[] colors = BasicColor.values();
		BasicColor[][] rlt = new BasicColor[8][8];
			
		
		for (int k = 0; k < 8; k++) {
			for (int j = 0; j < 8; j++) {
				rlt[k][j] = colors[k].common(colors[j]);
				if(rlt[k][j]!= answer[k][j]){
					error("The return value of " + colors[k] + ".common(" + colors[j] +") = " + rlt[k][j] + " is not the expected "+
				     answer[k][j]) ;
					
				}
			}
		}
		
	}

	

	
	public final void testExclusiveOr() {
		BasicColor[][] answer = 
		{
		{BLACK,BLUE,GREEN,CYAN,RED,MAGENTA,YELLOW,WHITE},
		{BLUE,BLACK,CYAN,GREEN,MAGENTA,RED,WHITE,YELLOW},
		{GREEN,CYAN,BLACK,BLUE,YELLOW,WHITE,RED,MAGENTA},
		{CYAN,GREEN,BLUE,BLACK,WHITE,YELLOW,MAGENTA,RED},
		{RED,MAGENTA,YELLOW,WHITE,BLACK,BLUE,GREEN,CYAN},
		{MAGENTA,RED,WHITE,YELLOW,BLUE,BLACK,CYAN,GREEN},
		{YELLOW,WHITE,RED,MAGENTA,GREEN,CYAN,BLACK,BLUE},
		{WHITE,YELLOW,MAGENTA,RED,CYAN,GREEN,BLUE,BLACK} } ;
		
		test();
		BasicColor[] colors = BasicColor.values();
		BasicColor[][] rlt = new BasicColor[8][8];
			
		
		for (int k = 0; k < 8; k++) {
			for (int j = 0; j < 8; j++) {
				rlt[k][j] = colors[k].exclusiveOr(colors[j]);
				if(rlt[k][j]!= answer[k][j]){
					error("The return value of " + colors[k] +".exclusiveOr(" + colors[j] +") = " + rlt[k][j] + " is not the expected "+
				    answer[k][j]) ;
					
				}
			}
		}	
		
	}

	
	public final void testMixedWith() {
		BasicColor[][] answer = {		
		{BLACK,BLUE,GREEN,CYAN,RED,MAGENTA,YELLOW,WHITE},
		{BLUE,BLUE,CYAN,CYAN,MAGENTA,MAGENTA,WHITE,WHITE},
		{GREEN,CYAN,GREEN,CYAN,YELLOW,WHITE,YELLOW,WHITE},
		{CYAN,CYAN,CYAN,CYAN,WHITE,WHITE,WHITE,WHITE},
		{RED,MAGENTA,YELLOW,WHITE,RED,MAGENTA,YELLOW,WHITE},
		{MAGENTA,MAGENTA,WHITE,WHITE,MAGENTA,MAGENTA,WHITE,WHITE},
		{YELLOW,WHITE,YELLOW,WHITE,YELLOW,WHITE,YELLOW,WHITE},
		{WHITE,WHITE,WHITE,WHITE,WHITE,WHITE,WHITE,WHITE}} ;
		
		test();
		BasicColor[] colors = BasicColor.values();
		BasicColor[][] rlt = new BasicColor[8][8];
			
		
		for (int k = 0; k < 8; k++) {
			for (int j = 0; j < 8; j++) {
				rlt[k][j] = colors[k].mixedWith(colors[j]);
				if(rlt[k][j]!= answer[k][j]){
					error("The return value of " + colors[k] +".mixedWith(" + colors[j] +") = " + rlt[k][j] + " is not the expected "+
				    answer[k][j]) ;
					
				}
			}
		}
		
		
		
	}

	
	public final void testCombine() {
       test() ;
       
       if(combine(RED, BLUE, GREEN) != WHITE) {
    	   error("Error on testing combine()!1") ;
       }       
       
       if(combine(RED, BLACK, MAGENTA) != MAGENTA) {
    	   error("Error on testing combine()!2") ;
       }
       
       if(combine(BLUE,CYAN, GREEN) != CYAN) {
    	   error("Error on testing combine()!3") ;
       }
		

	}
	
	public final void testGetColor() {
		
		test();
		
		BasicColor[] answer = {BLACK, BLUE, GREEN, CYAN,  RED, MAGENTA, YELLOW, WHITE };
		int[][] input = new int[][]{
			{0,0,0}, {0,0,255}, {0,255,0}, {0,255,255}, 
			{255,0,0},{255,0,255},{255,255,0}, {255,255,255} 
		} ;
	
		for(int c = 0; c< 8; c++){
			BasicColor rlt = getColor(input[c][0], input[c][1], input[c][2]) ;
			if( rlt != answer[c]){
				error("Fail to pass testing of getColor(%s, %s, %s): result is %s, expecting %s", input[c][0], input[c][1], input[c][2], rlt, answer[c] ) ;
			}
		}
		
	}

	
	public final void testSort1() {
		
	 test() ;
	 
	 BasicColor[] answer = {BLACK, BLUE, GREEN, CYAN,  RED, MAGENTA, YELLOW, WHITE };
	 
	 BasicColor[] rlt = BasicColor.sort(RED,  GREEN,  BLUE) ;
	 
	 if(! Arrays.equals(answer, rlt) ){
		 error("Fail to pass test of testSort1!"); 
		 System.out.println(BasicColor.toString(rlt)) ;
		 System.out.println(BasicColor.toRGB(rlt)) ;
	 }
	}
	 
	 public final void testSort2() {
			
		 test() ;
		 
		 int[][] ans = new int[][]{
				{0,0,0}, {255,0,0}, {0,0,255}, {255,0,255}, 
				{0,255,0}, {255,255,0}, {0,255,255}, {255,255,255}
			} ;
			BasicColor[] answer = new BasicColor[8] ;
			for(int k = 0; k < 8; k++){
				answer[k] = getColor(ans[k][0], ans[k][1], ans[k][2]) ;
			}
			
			answer = new BasicColor[] { BLACK,RED,BLUE,MAGENTA,GREEN,YELLOW,CYAN,WHITE } ;
			
			System.out.println(BasicColor.toString(answer)) ;
		 
		 
		 BasicColor[] rlt = BasicColor.sort(GREEN,  BLUE, RED) ;
		
			 
		 
		 if(! Arrays.equals(answer, rlt)){
			 error("Fail to pass test of testSort2!") ;
			 System.out.println(BasicColor.toString(rlt)) ;
			 System.out.println(BasicColor.toRGB(rlt)) ;
		 } 
		
	}		
	
	
	 public static void main(String... args) {

			// write your own test here!
			
			
			 
			System.out.println("=====Begin of Test======");
			// test with tests given by the instructor!
			mainTest(args);
			System.out.println("=====End of Test=========");
			System.out.println(summary());
		}
	
	

}