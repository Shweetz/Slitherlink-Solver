
import java.io.*;

public class Game 
{
//	int size = 1;
//	int[][] numbers = new int[size][size];
//	int[][] walls = new int[size*2 + 1][size*2 + 1];
//	
//	int numbers2[][] = { {4} };
//	char h_walls2[][] = { {'_'}, {'_'} };
//	char v_walls2[][] = { {'|', '|'} };
	
	char tab[][];
/*	static char tab[][] = { {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
							{' ', ' ', ' ', '2', ' ', '2', ' ', '3', ' ', '3', ' ', '3', ' ', ' ', ' '},
							{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
							{' ', '2', ' ', ' ', ' ', '1', ' ', ' ', ' ', '0', ' ', '2', ' ', '2', ' '},
							{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
							{' ', '3', ' ', ' ', ' ', '2', ' ', '3', ' ', ' ', ' ', '3', ' ', '2', ' '},
							{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
							{' ', '2', ' ', '2', ' ', '1', ' ', '1', ' ', '1', ' ', '2', ' ', '3', ' '},
							{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
							{' ', '3', ' ', ' ', ' ', '1', ' ', ' ', ' ', ' ', ' ', '3', ' ', '2', ' '},
							{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
							{' ', ' ', ' ', ' ', ' ', '3', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '3', ' '},
							{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
							{' ', ' ', ' ', '1', ' ', ' ', ' ', '2', ' ', ' ', ' ', ' ', ' ', '2', ' '},
							{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}};
*/	
	boolean number_complete()
	{
		boolean newClue = false;
		
		for (int i = 1; i < tab.length; i+=2)
		{				
			for (int j = 1; j < tab[0].length; j+=2)
			{
				// If number is surrounded with known ways, then don't waste time trying algorithms
				if (tab[i-1][j] == ' ' || tab[i+1][j] == ' ' || tab[i][j-1] == ' ' || tab[i][j+1] == ' ')
				{
					// Test if a number has all its walls
					int numberWalls = 0; 
					
					if (tab[i-1][j] == '_')
						numberWalls++;
					if (tab[i+1][j] == '_')
						numberWalls++;
					if (tab[i][j-1] == '|')
						numberWalls++;
					if (tab[i][j+1] == '|')
						numberWalls++;
					
					if (numberWalls == (int)(tab[i][j] - '0')) // if a number has all its walls, put the x
					{
						newClue = true;
						
						if (tab[i-1][j] != '_')
							tab[i-1][j] = 'x';
						
						if (tab[i+1][j] != '_')
							tab[i+1][j] = 'x';
						
						if (tab[i][j-1] != '|')
							tab[i][j-1] = 'x';
						
						if (tab[i][j+1] != '|')
							tab[i][j+1] = 'x';
					}

					// Test if a number has all its non-walls
					int numberX = 0;
					
					if (tab[i-1][j] == 'x')
						numberX++;
					if (tab[i+1][j] == 'x')
						numberX++;
					if (tab[i][j-1] == 'x')
						numberX++;
					if (tab[i][j+1] == 'x')
						numberX++;
					
					if (numberX == 4 - (int)(tab[i][j] - '0')) // if a number has all its non-walls, put all its walls
					{
						newClue = true;
						
						if (tab[i-1][j] != 'x')
							tab[i-1][j] = '_';
						
						if (tab[i+1][j] != 'x')
							tab[i+1][j] = '_';
						
						if (tab[i][j-1] != 'x')
							tab[i][j-1] = '|';
						
						if (tab[i][j+1] != 'x')
							tab[i][j+1] = '|';
					}						
				}
			}
		}
		
		return newClue;
	}
	
	boolean node_one_way() // if only one way to a node is not known yet
	{
		boolean newClue = false;
		
		for (int i = 0; i < tab.length; i+=2)
		{				
			for (int j = 0; j < tab[0].length; j+=2)
			{
				int numberUnknownWays = 0;
				int numberWalls = 0;
				int unknownWay = 0;
				
				// Count the unknown ways and the walls around the node
				if (i != 0)
				{
					if 		(tab[i-1][j] == '|')
						numberWalls++;
					else if (tab[i-1][j] == ' ')
					{
						numberUnknownWays++;
						unknownWay = 0;
					}
				}
				
				if (i != tab.length - 1)
				{
					if 		(tab[i+1][j] == '|')
						numberWalls++;
					else if (tab[i+1][j] == ' ')
					{
						numberUnknownWays++;
						unknownWay = 1;
					}
				}
				
				if (j != 0)
				{
					if 		(tab[i][j-1] == '_')
						numberWalls++;
					else if (tab[i][j-1] == ' ')
					{
						numberUnknownWays++;
						unknownWay = 2;
					}
				}
				
				if (j != tab[0].length - 1)
				{
					if 		(tab[i][j+1] == '_')
						numberWalls++;
					else if (tab[i][j+1] == ' ')
					{
						numberUnknownWays++;
						unknownWay = 3;
					}
				}
				
				if (numberUnknownWays == 1)
				{
					newClue = true;
					
					if (unknownWay == 0)
					{
						if (numberWalls == 1) // If only one wall is linked to the node
							tab[i-1][j] = '|';
						else
							tab[i-1][j] = 'x';						
					}

					else if (unknownWay == 1)
					{
						if (numberWalls == 1) // If only one wall is linked to the node
							tab[i+1][j] = '|';
						else
							tab[i+1][j] = 'x';						
					}

					else if (unknownWay == 2)
					{
						if (numberWalls == 1) // If only one wall is linked to the node
							tab[i][j-1] = '_';
						else
							tab[i][j-1] = 'x';						
					}

					else if (unknownWay == 3)
					{
						if (numberWalls == 1) // If only one wall is linked to the node
							tab[i][j+1] = '_';
						else
							tab[i][j+1] = 'x';						
					}
				}
			}
		}
	
		return newClue;
	}
	
	boolean node_used() // if node (not on the edge) has a wall in and a wall out, the 2 others are non-walls
	{
		boolean newClue = false;
		
		// Double loop without edges
		for (int i = 2; i < tab.length - 2; i+=2)
		{				
			for (int j = 2; j < tab[0].length - 2; j+=2)
			{
				int numberWalls = 0;
				
				if (tab[i-1][j] == '|')
					numberWalls++;
				if (tab[i+1][j] == '|')
					numberWalls++;
				if (tab[i][j-1] == '_')
					numberWalls++;
				if (tab[i][j+1] == '_')
					numberWalls++;
				
				if (numberWalls == 2)
				{
					if (tab[i-1][j] == ' ')
					{
						newClue = true;
						tab[i-1][j] = 'x';
					}
					if (tab[i+1][j] == ' ')
					{
						newClue = true;
						tab[i+1][j] = 'x';
					}
					if (tab[i][j-1] == ' ')
					{
						newClue = true;
						tab[i][j-1] = 'x';
					}
					if (tab[i][j+1] == ' ')
					{
						newClue = true;
						tab[i][j+1] = 'x';
					}
				}			
			}
		}
		
		return newClue;
	}

	boolean only_one_loop() // a loop not containing all walls satisfying numbers can't close itself
	{
		boolean newClue = false;
		
		// Try to add a wall and see if it creates a loop closing itself even though it shouldn't
		// Horizontal walls
		for (int i = 0; i < tab.length; i+=2)
		{				
			for (int j = 1; j < tab[0].length; j+=2)
			{
							
			}
		}

		// Vertical walls
		for (int i = 1; i < tab.length; i+=2)
		{				
			for (int j = 0; j < tab[0].length; j+=2)
			{
							
			}
		}
		
		return newClue;
	}
	
	boolean is_finished() // Test if the game is won
	{
		boolean newClue = true;
		
		// Check if all numbers have right number of walls
		/*for (int i = 1; i < tab.length; i+=2)
		{				
			for (int j = 1; j < tab[0].length; j+=2)
			{
				if (tab[i][j] != ' ')
				{
					
		*/		
		newClue = false;
		
		return newClue;
	}
	
	void print()
	{
		String str_out = "";
		/*for (int i = 0; i < h_walls2[0].length*2 + 1; ++i)
		{				
			for (int j = 0; j < h_walls2[0].length*2 + 1; ++j)
			{
				if (i%2 == 0)
				{
					if (j%2 == 0)
					{
						str_out += " ";		
					}
					else
					{
						str_out += h_walls2[i/2][(j-1)/2];						
					}
				}
				else
				{
					if (j%2 == 0 )
					{
						str_out += v_walls2[(i-1)/2][j/2];
					}
					else
					{
						str_out += numbers2[(i-1)/2][(j-1)/2];						
					}
				}
			}
			str_out += "\n";
		}*/

		/*for (int i = 0; i < tab.length; ++i)
		{				
			if (i%2 == 1 || i == 0)
			{
				for (int j = 0; j < tab[0].length; ++j)
				{
					str_out += tab[i][j];	
				}
				str_out += "\n";
			}
			else
			{
				int s = i;
				for (int j = 0; j < tab[0].length; ++j)
				{
					i = 2*s - 1 - i;
					str_out += tab[i][j];		
				}
				i = s;
				str_out += "\n";
			}
		}*/
		
		/*for (int i = 0; i < tab.length; ++i)
		{	
			for (int j = 0; j < tab[0].length; ++j)
			{
				str_out += tab[i][j] + " ";	
			}
			str_out += "\n";
			if (i%2 == 0)
				str_out += "\n";
		}*/
		for (int i = 0; i < tab.length - 1; ++i)
		{	
			if (i == 0)
			{
				for (int j = 1; j < tab[0].length; j+=2)
				{
					str_out += " " + tab[i][j] + tab[i][j] + tab[i][j];	
				}
				str_out += "\n";
			}
			else
			{
				for (int j = 0; j < tab[0].length; j+=2) // Vertical 1
				{
					str_out += tab[i][j] + "   ";	
				}
				str_out += "\n";
				for (int j = 0; j < tab[0].length; ++j) // Vertical 2
				{
					str_out += tab[i][j] + " ";	
				}
				str_out += "\n";
				for (int j = 0; j < tab[0].length; ++j)
				{
					if (j%2 == 0)
						str_out += tab[i][j];
					else
					{
						str_out += tab[i+1][j];	
						str_out += tab[i+1][j];	
						str_out += tab[i+1][j];						
					}
				}
				str_out += "\n";
				i++;
			}
		}
		System.out.print(str_out); 
//		System.out.print(str_out.replace('x', ' ')); 
	}
	
	boolean read(String fileName)
	{
		try {
			RandomAccessFile raf = new RandomAccessFile(fileName, "r");
			String ligne;
			
			// First read to count lines (to specify a size for the game table)
			int nbLignes = 0;
			while ((ligne = raf.readLine()) != null) {
				nbLignes++;
			}
			tab = new char[nbLignes][nbLignes];
			
			// Back to beginning of file to fill table
			raf.seek(0);
			for (int i = 0; (ligne = raf.readLine()) != null; i++) {
				for (int j = 0; j < ligne.length(); ++j)
				{
					char c = ligne.charAt(j);
					tab[i][j] = (c == '.') ? ' ' : c; // Put a ' ' when find a '.' in the file
				}
			}
			raf.close();
		}
		catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
		return true;
	}
		
	void solve() 
	{
		System.out.println("Original: ");
		
		print();
		
		boolean cantFindMore = false;
		
		while (is_finished() == false && cantFindMore == false)
		{			
			System.out.println("Number Complete: ");	
			if (number_complete() == false)
			{
				System.out.println("One Way: ");	
				if (node_one_way() == false)
				{
					System.out.println("Node Used: ");
					if (node_used() == false)
					{
						System.out.println("404 Not Found. ");	
						cantFindMore = true;
					}
				}
			}
			
			if (cantFindMore == false)
			{		
				print();
			}
		}
	}

	public static void main(String[] args) 
	{
		String fichier = "C:\\Users\\Romain\\workspace\\Loop the Loop Solver\\game.txt";
		
		Game game = new Game();
		
		if (game.read(fichier))
			game.solve();
		else
			System.out.println("File not found.");
	}
}
