
package TicTac;
import java.io.*;
import java.util.*;

public class TicTacToe {
    
    private final int BOARD_SIZE = 3;
    private char[][] board;
    
    private char player1Char;
    private char player2Char;
    
    private int player1CoordinateX;
    private int player1CoordinateY;
    
    private int player2CoordinateX;
    private int player2CoordinateY;
    
    private boolean win;
    
    private Scanner player1;
    private Scanner player2;
    
    public TicTacToe()
    {
        player1Char = '-';
        player2Char = '-';
        
        player1CoordinateX = 0;
        player1CoordinateY = 0;
        
        player2CoordinateX = 0;
        player2CoordinateY = 0;
        
        player1 = null;
        player2 = null;
       
        //initBoard();
    }
    
    public boolean setWin(boolean wining)
    {
        win = wining;
        return win;
    }
    public boolean getWin()
    {
        return win;
    }
    
    public void startGame()
    {
        initBoard();
        System.out.print("Player 1, choose a character... ");
        player1 = new Scanner(System.in);
        player1Char = player1.next().charAt(0);
        
        System.out.print("Player 2, choose a character... ");
        player2 = new Scanner(System.in);
        player2Char = player2.next().charAt(0);
        
        
        System.out.println(player1Char);
        System.out.println(player2Char);
    }
    /*The actions that the function performs while gathering input from player 1
     *If the user violate the rules the program will ask him again until he/she provides correct input
     *This is done by catching the ArrayIndexOutOfBoundsException and calling same method until the user provides correct input.
     */
    public void player1Action()
    {
            try
            {
                System.out.println("Player1: " + player1Char +  " enter X axis and Y axis: ");
                player1CoordinateX = player1.nextInt();
                player1CoordinateY = player1.nextInt();
                if(board[player1CoordinateX][player1CoordinateY] == '-')
                {
                    board[player1CoordinateX][player1CoordinateY] = player1Char;
                }
                else
                {
                    System.out.println("Place already taken!\nPlease enter another coordinates...");
                    player1Action();
                }
                printBoard();
                winCheck();
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                System.err.println("You should use numbers from 0 to 2, for the coordinates! " +  e.getMessage() + " is not a valid coordinate.");
                player1Action();
            }
    }
    /*The actions that the function performs while gathering input from player 1
     *If the user violate the rules the program will ask him again until he/she provides correct input.
     *This is done by catching the ArrayIndexOutOfBoundsException and calling same method until the user provides correct input.
     */
    public void player2Action()
    {
       
        try
        {
            System.out.println("Player2: " + player2Char +  " enter X axis and Y axis: ");
            player2CoordinateX = player2.nextInt();
            player2CoordinateY = player2.nextInt();
            if(board[player2CoordinateX][player2CoordinateY] == '-')
            {
                board[player2CoordinateX][player2CoordinateY] = player2Char;
            }
            else
            {
                System.out.println("Place already taken!\nPlease enter another coordinates...");
                player2Action();
            }
            printBoard();
            winCheck();
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.err.println("You should use numbers from 0 to 2, for the coordinates! " +  e.getMessage() + " is not a valid coordinate.");
            player2Action();
        }
    }
    
    /*This function will gather input coordinates for the both players
     * The while loop will loop until a win condition is encountered
     * When a win condition is encountered it will reassign the win boolean to false,
     * and the loop will end, and it will stop asking the user for coordinates.
     */
    public void playGame()
    {
        setWin(true);
        while(win)
        {    
            player1Action();
            player2Action();
        }
    }
    
    public void initBoard()//a.k.a. reset board
    {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++)
        {
            for(int j = 0; j < BOARD_SIZE; j++)
            {
                board[i][j] = '-';
            }
        }
    }
    public void printBoard()
    {
        System.out.println("-------------");
        for(int i = 0; i < BOARD_SIZE; i++)
        {
            System.out.print("| ");
            for(int j = 0; j < BOARD_SIZE; j++)
            {
                System.out.print( board[i][j] + " | ");
            }
            System.out.println();
        }
        System.out.println("-------------");
    }
    public void winCheck()
    {
      
        //1st row
        if(board[0][0] == player1Char &&  board[0][1] == player1Char && board[0][2] == player1Char) 
        {
            setWin(false);
            System.out.println("Player 1 WINS");
            
            System.exit(0);
        }
        else if(board[0][0] == player2Char &&  board[0][1] == player2Char && board[0][2] == player2Char)
        {
            setWin(false);
            System.out.println("Player 2 WINS");
            
            System.exit(0);
        }
        //2nd row
        else if (board[1][0] == player1Char &&  board[1][1] == player1Char && board[1][2] == player1Char)
        {
            setWin(false);
            System.out.println("Player 1 Wins!");
            System.exit(0);
        }
        else if(board[1][0] == player2Char &&  board[1][1] == player2Char && board[1][2] == player2Char)
        {
            setWin(false);
            System.out.println("Player 2 Wins!");
            System.exit(0);
        }
        //3rd row
        else if (board[2][0] == player1Char &&  board[2][1] == player1Char && board[2][2] == player1Char)
        {
            setWin(false);
            System.out.println("Player 1 Wins!");
            System.exit(0);
        }
        else if (board[2][0] == player2Char &&  board[2][1] == player2Char && board[2][2] == player2Char)
        {
            setWin(false);
            System.out.println("Player 2 Wins!");
            System.exit(0);
        }
        //1st column
        else if(board[0][0] == player1Char &&  board[1][0] == player1Char && board[2][0] == player1Char)
        {
            setWin(false);
            System.out.println("Player 1 Wins!");
            System.exit(0);
        }
        else if(board[0][0] == player2Char &&  board[1][0] == player2Char && board[2][0] == player2Char)
        {
            setWin(false);
            System.out.println("Player 2 Wins!");
            System.exit(0);
        }
        //2nd column
        else if(board[0][1] == player1Char &&  board[1][1] == player1Char && board[2][1] == player1Char)
        {
            setWin(false);
            System.out.println("Player 1 Wins!");
            System.exit(0);
        }
        else if(board[0][1] == player2Char &&  board[1][1] == player2Char && board[2][1] == player2Char)
        {
            setWin(false);
            System.out.println("Player 2 Wins!");
            System.exit(0);
        }
        //3rd column
        else if(board[0][2] == player1Char &&  board[1][2] == player1Char && board[2][2] == player1Char)
        {
            setWin(false);
            System.out.println("Player 1 Wins!");
            System.exit(0);
        }
        else if(board[0][2] == player2Char &&  board[1][2] == player2Char && board[2][2] == player2Char)
        {
            setWin(false);
            System.out.println("Player 2 Wins!");
            System.exit(0);
        }
        //backslash \ win
        else if(board[0][0] == player1Char &&  board[1][1] == player1Char && board[2][2] == player1Char)
        {
            setWin(false);
            System.out.println("Player 1 Wins!");
            System.exit(0);
        }
        else if(board[0][0] == player2Char &&  board[1][1] == player2Char && board[2][2] == player2Char)
        {
            setWin(false);
            System.out.println("Player 2 Wins!");
            System.exit(0);
        }
        //forwardslash / win
        else if(board[0][2] == player1Char &&  board[1][1] == player1Char && board[2][0] == player1Char)
        {
            setWin(false);
            System.out.println("Player 1 Wins!");
            System.exit(0);
        }
        else if(board[0][2] == player2Char &&  board[1][1] == player2Char && board[2][0] == player2Char)
        {
            setWin(false);
            System.out.println("Player 2 Wins!");
            System.exit(0);
        }
        else if((board[0][0] != '-' && board[0][1] != '-' && board[0][2] != '-' && board[1][0] != '-' && board[1][1] != '-' && board[1][2] != '-' && board[2][0] != '-' && board[2][1] != '-' && board[2][2] != '-') && getWin() == true)
        {
           setWin(false); 
           System.out.println("Draw!");
           System.exit(0);
        } 
    }
    public static void main(String[] args) {
        
        TicTacToe obj1 = new TicTacToe();
        obj1.startGame();
        obj1.playGame();
        obj1.printBoard();
    }
    
}
