package apjp2017.hw1;


import java.util.EnumSet;
import java.util.Set;
import apjp2017.hw1.HW12.EightQueenPosition;
import static apjp2017.hw1.HW12.EightQueenPosition.*;

public class HW12 {
	
	
	public enum EightQueenPosition	{
		P00, P01, P02, P03, P04, P05, P06, P07,
		P10, P11, P12, P13, P14, P15, P16, P17,
		P20, P21, P22, P23, P24, P25, P26, P27,
		P30, P31, P32, P33, P34, P35, P36, P37,
		P40, P41, P42, P43, P44, P45, P46, P47,
		P50, P51, P52, P53, P54, P55, P56, P57,
		P60, P61, P62, P63, P64, P65, P66, P67,
		P70, P71, P72, P73, P74, P75, P76, P77;
	
	 //Every position is indexed by its row and column value.  
	 int row, col ;	
	 
	 
	 static { // use static initializer to fill in (row, col) for every literal PXX.
		
		EightQueenPosition[] all = EightQueenPosition.values() ;
		int k = 0 ;
	   	for(int r =0; r < 8; r++ ){
	   		for(int c = 0; c < 8; c++){
	   	      all[k].row = r;
	   	      all[k].col = c;
	   	      k++ ;
	   		}
	   	}		 
	 }
	 
	 
	 /** TODO-method:
	  *  Given a set of board positions, return a string displaying
	  *  the layout and content of the board.
	  */
	 public static String displayQBoard(EnumSet<EightQueenPosition> queens) {
		 // replace following code with your implementation
		 return 
		 "-----------------\n" +
		 "|X| | | | | | | |\n" +
		 "-----------------\n" +
		 "| | |X| | | | | |\n" +
		 "...              \n"  ;
		  }
		
		
	/**TODO-method: 
	 * Given a set of board positions, determine if it is a solution of the 8-Queen problem.
	 *  @param board  a set of positions
	 *  @return true if it is a solution of 8 queens.
	 */
	 
	public static boolean isSolution(EnumSet<EightQueenPosition> queens ){
		// replace following code with your implementation
			return false;
	}
	
	
	/** TODO-method: 
	 * Given a set of board positions, determine if we can get a solution of 8 Queens problem by adding additional positions
	 *  to the input board. Return any such extension of the input board if it is possible and return an empty set if
	 *  there is no such solution.  
	 *  @param  board  a set of positions . 
	 *  @return all solution boards which are extensions of the inpout board, or Emptyset.none() 
	 *          if there is no solution.
	 */
	 
	public static Set<EnumSet<EightQueenPosition>> getAllSolutions(EnumSet<EightQueenPosition> queens ){
		// replace following code with your implementation
			return null;
	}
	
	////////////////////////////////////////////////////////////
	////// Add any additional methods/fields  from here      ///
	///////////////////////////////////////////////////////////
	
			
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

	
	public static void mainTest(String... args) {
		
		HW12 hw12 = new HW12();
		
				
		hw12.testDisplayQBoard();
		hw12.testIsSolution();
		hw12.testGetAllSolutions();
	

	}
	
	
	/**
	 * 
	 * @param args
	 */
	public void testDisplayQBoard() {
		// write your test code here!
	}

	/**
	 * 
	 * @param args
	 */
	public  void testIsSolution() {
		// write your test code here!
	}

	/**
	 * 
	 * @param args
	 */
	public  void testGetAllSolutions() {
		// write your test code here!
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