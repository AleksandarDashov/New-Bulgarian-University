

package TicTac;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.in;
import static java.lang.System.out;
import java.net.*;
import java.util.Scanner;


public class TicTacServerThread extends Thread
{
    
    private Socket client1 = null;
    private Socket client2 = null;
    //private BufferedReader in = null;
    private PrintWriter outputClient1 = null;
    private PrintWriter outputClient2 = null;
    private Scanner inputClient1 = null;
    private Scanner inputClient2 = null;
    
    
    
    public TicTacServerThread(Socket clientSocket1, Socket clientSocket2) throws IOException
    {
        this.client1 = clientSocket1;
        this.client2 = clientSocket2;
       // PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        //BufferedReader    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));*/
    }
    public void ConnectClient()
    {
        System.out.println("Hello from ClientPair Thread");
        try
        {
            inputClient1 = new Scanner(client1.getInputStream()); //inputstream for the first client
            outputClient1 = new PrintWriter(client1.getOutputStream(), true); //outputstream for the first client
            
            inputClient2 = new Scanner(client2.getInputStream()); //inputstream for the second client
            outputClient2 = new PrintWriter(client2.getOutputStream(), true);
            
            //outputClient1.println("Hello, Client1, the server says hi");
            //outputClient2.println("Hello, Client2, the server says hi");
            outputClient1.println("It's your move...");
            outputClient2.println("It's Player 1 move");
            //out.println("Please enter integer numbers for X axis and Y axis:");
            //out.println("Server is here");
            int inputBoard = 0;
            boolean gnomec = true;
            
            while(gnomec)
            {
                System.out.println("VATRE V LOOPA");
                inputBoard = inputClient1.nextInt();
                System.out.println("Client1 says: " + inputBoard);
                outputClient2.println("Player 1 move was: " + inputBoard);
            }
            /*
            while((inputBoard = inputClient1.nextInt()) != 1000)
            {
                System.out.println("Ses se" + inputBoard);
                out.println("OMG");
            }*/
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
    @Override
    public void run()
    {
        ConnectClient();
        /*System.out.println("Hello fomr TIcTacServerThread");
        try
        {
            Scanner scan = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
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
    

}
