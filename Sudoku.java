public class Sudoku
{
	private static final int BLANK = 9;
	private static final int BOARDLEN = 9;
	private static final int BOARDELEMENTS = 81;
	private static final int SQUARELEN = 3;
	
	public int[] board;//81 elements in classic
	private boolean[][] possible;//81 by 9 in classic
	//if possible[x][y] = true, then board[x] = y doesn't pose an immediate contradiction
	
	
	public Sudoku(int[] board)//CONSTRUCTOR - takes 1d array, values are 0 to 8, and 9 represents blank
	{
		this.board = board;
		possible = new boolean[BOARDELEMENTS][BOARDLEN];
		/*use algorithm to determine possible*/
	}
	
	
	//COORDNIATE TRANSFORMS
	//three coordinate systems - row col, square subsquare, and index
	//*****************************************************************
	//2d to 1d transforms
	public static int rowCol2Ind(int row, int col)
	{
		return (row*BOARDLEN+col);
	}
	
	public static int sqSub2Ind(int square, int subsquare)
	{	
		return rowCol2Ind(SQUARELEN*(square/SQUARELEN)+subsquare/SQUARELEN,3*(square%SQUARELEN)+subsquare%SQUARELEN);
	}
	
	//1d to 2d transforms
	public static int ind2Row(int ind)
	{
		return ind/BOARDLEN;
	}
	public static int ind2Col(int ind)
	{
		return ind%BOARDLEN;
	}
	
	public static int ind2Square(int ind)
	{
		int row = ind2Row(ind)/SQUARELEN;
		int col = ind2Col(ind)/SQUARELEN;
		return row*SQUARELEN+col;
	}
	public static int ind2Subsquare(int ind)
	{
		int row = ind2Row(ind)%SQUARELEN;
		int col = ind2Col(ind)%SQUARELEN;
		return row*SQUARELEN+col;
	}
	//END COORDINATE TRANSFORMS
	//**********************************************************************
	
	//possibility checks
	// no row contradiction
	// no col contradiction
	// no square contradiction
	// ***no ADVANCED contradiction - later heuristic
	/*
	private boolean isPossible(int space,int guess)
	{
		//row contradiction
		int row = ind2Row(space);
		for(int i = 0; i<9; i++)
		{
			if(guess==board[rowCol2Ind(row,i)])
				return false;
		}
		//col contradiction
		int col = ind2Col(space);
		for(int i = 0; i<9; i++)
		{
			if(guess==board[rowCol2Ind(i,col)])
				return false;
		}
		//square contradiction
		//blah blah blah needs tested
	}
	*/
	private void genPossible()
	{
		//step 1: add in given values
		for(int i = 0; i<BOARDELEMENTS; i++)//loop through each element
		{
			if(board[i]!=BLANK)//
			{
				possible[i][board[i]] = true;
			}
		}
	}

	//INPUT OUTPUT
	//***********
	public void printBoard()
	{

		for(int i = 0; i<9; i++)
		{
			for(int j = 0; j<9; j++)
			{
				System.out.print((1+board[9*i+j])%10+" ");
			}
			System.out.println();
		}
	}

	public static void main(String [] args)
	{
		String puzzle = ".94...13..............76..2.8..1.....32.........2...6.....5.4.......8..7..63.4..8";
		
		System.out.println("Puzzle length is "+puzzle.length());
		
		int[] puzzleboard = new int[81];
		for(int i = 0; i<puzzle.length(); i++)
		{
			if(puzzle.charAt(i)=='.')
				puzzleboard[i] = 9;
			else
				puzzleboard[i] = puzzle.charAt(i) - '0'-1; 
				
		}

		Sudoku sPuzzle = new Sudoku(puzzleboard);
		sPuzzle.printBoard();

		System.out.println();
		for(int i = 0; i<9; i++)
		{
			System.out.print(sPuzzle.board[sqSub2Ind(3,i)]+" ");
		}

		System.out.println();

		for(int i = 0; i<81; i++)
		{
			System.out.print(sqSub2Ind(ind2Square(i),ind2Subsquare(i))+" ");
		}

		
	}
	
}
