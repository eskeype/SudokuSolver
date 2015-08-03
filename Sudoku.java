/*
EXPLAIN CODE
DO I/O
WRITE SHIT ABOUT 'LEAST POSSIBLE' HEURISTIC
MAKE PRINT BOARD LOOK BETTER -done
*/

public class Sudoku
{
	private static final int BLANK = 9;
	private static final int BOARDLEN = 9;
	private static final int BOARDELEMENTS = 81;
	private static final int SQUARELEN = 3;
	
	private int[] board;//81 elements in classic
	//private boolean[][] possible;//81 by 9 in classic
	//if possible[x][y] = true, then board[x] = y doesn't pose an immediate contradiction
	
	
	public Sudoku(int[] board)//CONSTRUCTOR - takes 1d array, values are 0 to 8, and 9 represents blank
	{
		this.board = board;
		//possible = new boolean[BOARDELEMENTS][BOARDLEN];
		/*use algorithm to determine possible*/
	}
	
	
	//COORDNIATE TRANSFORMS
	//three coordinate systems - row col, square subsquare, and index
	//*****************************************************************
	//2d to 1d transforms
	private static int rowCol2Ind(int row, int col)
	{
		return (row*BOARDLEN+col);
	}
	
	private static int sqSub2Ind(int square, int subsquare)
	{	
		return rowCol2Ind(SQUARELEN*(square/SQUARELEN)+subsquare/SQUARELEN,SQUARELEN*(square%SQUARELEN)+subsquare%SQUARELEN);
	}
	
	//1d to 2d transforms
	private static int ind2Row(int ind)
	{
		return ind/BOARDLEN;
	}
	private static int ind2Col(int ind)
	{
		return ind%BOARDLEN;
	}
	
	private static int ind2Square(int ind)
	{
		int row = ind2Row(ind)/SQUARELEN;
		int col = ind2Col(ind)/SQUARELEN;
		return row*SQUARELEN+col;
	}
	private static int ind2Subsquare(int ind)
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
	
	private boolean isPossible(int space,int guess)
	{
		//row contradiction
		int row = ind2Row(space);
		for(int i = 0; i<BOARDLEN; i++)
		{
			if(guess==board[rowCol2Ind(row,i)])
				return false;
		}
		//col contradiction
		int col = ind2Col(space);
		for(int i = 0; i<BOARDLEN; i++)
		{
			if(guess==board[rowCol2Ind(i,col)])
				return false;
		}
		//square contradiction
		int square = ind2Square(space);
		for(int i = 0; i<BOARDLEN; i++)
		{
			if(guess==board[sqSub2Ind(square,i)])
				return false;
		}
		
		return true;
	}
	public static void printSolution(Sudoku current)
	{
		if(current.isSolved())
		{
			current.printBoard();
			System.out.println();
			return;
		}
		
		//try and make a move on the first blank
		/*
		int blankSpace = 0; 
		while(current.board[blankSpace]!=BLANK)
		{
			blankSpace++;
		}
		*/
		
		//using least possible heuristic - find the space with the least possible moves
		int minMoves = 10;
		int blankSpace = 0;
		for(int i = 0; i<BOARDELEMENTS; i++)
		{
			if((current.board[i]==BLANK)&&(current.moveCount(i)<minMoves))
			{
				minMoves = current.moveCount(i);
				blankSpace = i;
			}
		}
		
		//make every move
		for(int moveTry = 0; moveTry<BOARDLEN; moveTry++)
		{
			if(current.isPossible(blankSpace,moveTry))
			{
				Sudoku newSudoku = current.copySudoku();
				newSudoku.board[blankSpace] = moveTry;
				printSolution(newSudoku);
				
			}
		}
	}
	
	public static Sudoku SudokuSolver(Sudoku current)
	{
		if(current.isSolved())
		{
			//current.printBoard();
			return current;
		}
		
		//try and make a move on the first blank
		/*
		int blankSpace = 0; 
		while(current.board[blankSpace]!=BLANK)
		{
			blankSpace++;
		}
		*/
		
		//using least possible heuristic - find the space with the least possible moves
		int minMoves = 10;
		int blankSpace = 0;
		for(int i = 0; i<BOARDELEMENTS; i++)
		{
			if((current.board[i]==BLANK)&&(current.moveCount(i)<minMoves))
			{
				minMoves = current.moveCount(i);
				blankSpace = i;
			}
		}
		
		//make every move
		for(int moveTry = 0; moveTry<BOARDLEN; moveTry++)
		{
			if(current.isPossible(blankSpace,moveTry))
			{
				Sudoku newSudoku = current.copySudoku();
				newSudoku.board[blankSpace] = moveTry;
				Sudoku value = SudokuSolver(newSudoku);
				if(value!=null)
					return value;
				
			}
		}
		
		return null;
	}
	
	private int moveCount(int space)
	{
		int possibleMoves = 0;
		for(int i = 0; i<BOARDLEN; i++)
		{
			if(isPossible(space,i))
				possibleMoves++;
		}
		return possibleMoves;
	}
	
	private Sudoku copySudoku()
	{
		int[] newBoard = new int[BOARDELEMENTS];
		for(int i = 0; i<BOARDELEMENTS; i++)
		{
			newBoard[i] = board[i];
		}
		return new Sudoku(newBoard);
	}
	private boolean isSolved()
	{
		for(int i = 0; i<BOARDELEMENTS; i++)
		{
			if(board[i]==BLANK)
			{
				return false;
			}
			else
			{
				
				int row = ind2Row(i);
				int col = ind2Col(i);
				int square = ind2Square(i);
				int sub = ind2Subsquare(i);
				for(int j = 0; j<BOARDLEN; j++)
				{
					//rowcheck
					if((j!=col)&&(board[rowCol2Ind(row,j)]==board[i]))
						return false;
					//colcheck
					if((j!=row)&&(board[rowCol2Ind(j,col)]==board[i]))
						return false;
					//squarecheck
					if((j!=sub)&&(board[sqSub2Ind(square,j)]==board[i]))
						return false;
				}
			}
		}
		return true;
	}

	//INPUT OUTPUT
	//***********
	public void printBoard()
	{


		for(int i = 0; i<9; i++)
		{
			if(i%SQUARELEN==0)
				System.out.println("--------------------");
			for(int j = 0; j<9; j++)
			{
				if(j%SQUARELEN==0)
					System.out.print("|");
				
				if(board[9*i+j]!=9)
					System.out.print((1+board[9*i+j])%10+" ");
				else
					System.out.print(". ");
				
			}
			System.out.println();
			
			
		}
	}
	public static Sudoku str2Sudoku(String puzzle)
	{
		int[] puzzleboard = new int[81];
		for(int i = 0; i<puzzle.length(); i++)
		{
			if(puzzle.charAt(i)=='.')
				puzzleboard[i] = 9;
			else
				puzzleboard[i] = puzzle.charAt(i) - '0'-1; 
				
		}

		return new Sudoku(puzzleboard);
	}

	public static void main(String [] args)
	{
		//String puzzle = ".................................................................................";
		String puzzle = ".4..5..67...1...4....2.....1..8..3........2...6...........4..5.3.....8..2........";
		Sudoku sudPuzz = str2Sudoku(puzzle);
		
		sudPuzz.printBoard();
		
		System.out.println();
		
		/*Sudoku Answer = */printSolution(sudPuzz);
		
		//Answer.printBoard();

		

		
	}
	
}
