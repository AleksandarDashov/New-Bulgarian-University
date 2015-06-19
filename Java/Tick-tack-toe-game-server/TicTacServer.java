

package TicTac;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;
public class TicTacServer 
{
    
    //tic tac toe side
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
    
    //server side
    private static final int PORT_NUMB = 2500;
    
    private ServerSocket serverSocket;
    private Socket clientSocket1;
    private Socket clientSocket2;
    private Scanner inputClient1;
    private Scanner inputClient2;
    
    private PrintWriter outputClient1;
    private PrintWriter outputClient2;
    
    public boolean listeningClient;
    public TicTacServer()
    {
        //tic tac toe side
        player1Char = '-';
        player2Char = '-';
        
        player1CoordinateX = 0;
        player1CoordinateY = 0;
        
        player2CoordinateX = 0;
        player2CoordinateY = 0;
        
        player1 = null;
        player2 = null;
        
        //server side
        serverSocket = null;
        clientSocket1 = null;
        clientSocket2 = null;
        
        inputClient1 = null;//input stream for the first client
        outputClient1 = null;//outputstream for the first client
        
        inputClient2 = null; //input stream for the second client
        outputClient2 = null; //output stream for the second client
        
        listeningClient = true;
    }
    
    //tic tac toe side
    public boolean setWin(boolean wining)
    {
        win = wining;
        return win;
    }
    public  boolean getWin()
    {
        return win;
    }
    //initialize the board game
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
    //method which will initialize the board and it will prompt the user to enter a char
    //IDEA:::::remove char selection!
    public void startGame()
    {
        initBoard();
        System.out.print("Player 1, choose a character... ");
        player1 = new Scanner(System.in);
        player1Char =  'X';//player1.next().charAt(0);
        
        System.out.print("Player 2, choose a character... ");
        player2 = new Scanner(System.in);
        player2Char = 'O'; //player2.next().charAt(0);
        
        
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
    //a method which will call the player1Action method and the player2Action method
    public void playGame()
    {
        setWin(true);
        while(win)
        {    
            player1Action();
            player2Action();
        }
    }
    
    //method that checks for a win, lose, draw situtations
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
    
   /*
     public char[][] printBoard()
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
        return board;
    }
    
    */ 
    
    
    
    //printing the current state of the board
    public char[][] printBoard()
    {/*
        out.println("-------------");
        for(int i = 0; i < BOARD_SIZE; i++)
        {
            out.print("| ");
            for(int j = 0; j < BOARD_SIZE; j++)
            {
                out.print( board[i][j] + " | ");
            }
            out.println();
        }
        out.println("-------------");*/
        return board;
    }
    
    
    public void startPlaying()
    {
        while(listeningClient)
        {
            try
            {
                inputClient1 = new Scanner(clientSocket1.getInputStream()); //initializing the inputstream for the clientSocket1
                inputClient2 = new Scanner(clientSocket2.getInputStream()); //initializing the inputstream for the clientSocket2
                outputClient1 = new PrintWriter(clientSocket1.getOutputStream(), true); //initializing the ouputstream for the clientSocket1
                outputClient2 = new PrintWriter(clientSocket2.getOutputStream(), true); //initializing the ouputstream for the clientSocket2
                
              //  out.println("Please enter integer numbers for X axis and Y axis:");
              //  out.println(printBoard());
                outputClient1.println("Press enter to continue...");
                outputClient2.println("Press enter to continue...");
                int inputBoard = 0;
                while((inputBoard = inputClient1.nextInt()) != 1000)
                {
                    System.out.println("Client1: " + inputBoard);
                    outputClient1.println("OMG");
                }
            }
            catch(IOException e)
            {
                System.err.println("Exception: " + e.getMessage());
                System.out.println("Closing connection with client.");

                // free resources
                outputClient1.close();

                System.exit(1);
            }
            System.out.println("Closing connection with client.");
            outputClient1.close();
            listeningClient = false;
        }
        /*
        try
        {
            Scanner scan = new Scanner(clientSocket.getInputStream());
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Please enter integer numbers for X axis and Y axis:");
            int inputBoard = 0;
            while((inputBoard = scan.nextInt()) != 1000)
            {
                System.out.println(inputBoard);
                out.println("OMG");
            }
        }
        catch(IOException e)
        {
        System.err.println("Exception: " + e.getMessage());
        System.out.println("Closing connection with client.");
                
            // free resources
            out.close();
                  
            System.exit(1);
        }
        System.out.println("Closing connection with client.");
        out.close();*/
    }
    
    
    //server side
    public void initServer()throws IOException
    {
        
        //initing server socket
        try
        {
            
            serverSocket = new ServerSocket(PORT_NUMB);
            System.out.println("Listening for clients on PORT: " + PORT_NUMB);
        }
        catch(IOException e)
        {
            System.err.println("Cannot listen on port: " + PORT_NUMB);
            System.err.println("Exception: " + e.getMessage());
            System.exit(1);
        }
    }
    public void establishConnection() throws IOException
    {
        while(true)
        {
            try
            {
               clientSocket1 = serverSocket.accept();
               System.out.println("Accepted connection from client 1.");
               clientSocket2 = serverSocket.accept();
               System.out.println("Accepted connection from client 2.");
               TicTacServerThread clientPair1 = new TicTacServerThread(clientSocket1, clientSocket2);
               //TicTacServerThread client2 = new TicTacServerThread(clientSocket2);
               clientPair1.start();
               
               
               //startPlaying();
            }
            catch(IOException e)
            {
               System.err.println("Accept failed.");
               System.err.println("Exception: " + e.getMessage());
               System.exit(1);
            }/*
            try
            {
                Scanner scan = new Scanner(clientSocket.getInputStream());
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                //out.println("Please enter integer numbers for X axis and Y axis:");
                out.println("Server is here");
                int inputBoard = 0;
                while((inputBoard = scan.nextInt()) != 1000)
                {
                    System.out.println(inputBoard);
                    out.println("OMG");
                }
            }
            catch(IOException e)
            {
                System.err.println("Exception: " + e.getMessage());
                System.out.println("Closing connection with client.");
                
                // free resources
                out.close();
                  
                System.exit(1);
            }
            System.out.println("Closing connection with client.");
            out.close();*/
        }
        //serverSocket.close();
    }
    
    public static void main(String[] args) throws IOException
    {
        System.out.println("OMG");
        TicTacServer obj1 = new TicTacServer();
        obj1.initServer();
        obj1.establishConnection();
       // obj1.startPlaying();
    
    
    
    
    
    
        /*
        try
        {
            serverSocket = new ServerSocket(PORT_NUMB);
        }
        catch(IOException e)
        {
            System.err.println("Cannot listen on port: " +PORT_NUMB);
            System.err.println("Exception: " + e.getMessage());
            System.exit(1);
        }
        while(true)
        {
            try
            {
                clientSocket = serverSocket.accept();
            }
            catch(IOException e)
            {
                System.err.println("Accept failed.");
                System.err.println("Exception: " + e.getMessage());
                System.exit(1);
            }
            
            System.out.println("Accepted connection from client.");
            try
            {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                Scanner scan = new Scanner(clientSocket.getInputStream());
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                int inputBoard = 0;
                while((inputBoard = scan.nextInt()) != 1000)
                {
                    System.out.println(inputBoard);
                    out.println("OMG");
                }
            }
            catch(IOException e)
            {
                 System.err.println("Exception: " + e.getMessage());
                    System.out.println("Closing connection with client.");
                
                    // free resources
                    out.close();
                  
                    System.exit(1);
            }
            System.out.println("Closing connection with client.");
            out.close();
            
        }
        
        
    }*/

    }
}
