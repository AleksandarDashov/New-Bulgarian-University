

package Homework3;
import java.io.*;
import java.net.*;

public class QuizClient 
{
    
    private static final String HOST_NAME = "localhost";
    private static final int PORT_NUMB = 2600;
    private static final int NUMBER_OF_QUESTIONS = 10;

    public void connectingToServer() throws IOException
    {
        Socket quizClient = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try
        {
           
            quizClient = new Socket(HOST_NAME, PORT_NUMB);
            System.out.println("Connecting to port: " + PORT_NUMB);
            out = new PrintWriter(quizClient.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(quizClient.getInputStream()));
            
        }
        catch(UnknownHostException e)
        {
            System.err.println("Unknown host: " + HOST_NAME);
            System.err.println("Exception: " + e.getMessage());
            System.exit(1);
                    
        }
        catch(IOException e)
        {
            System.err.println("Cannot get I/O for " + HOST_NAME);
            System.err.println("Exception " + e.getMessage());
            System.exit(1);
        }
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        System.out.println(in.readLine());
        for(int i = 0; i < NUMBER_OF_QUESTIONS; i++)
        {
            System.out.println(in.readLine());
            userInput = stdIn.readLine();
            if(userInput.equals("a"))
            {
                
                out.println(userInput);
            }
            else if(userInput.equals("b"))
            {
                out.println(userInput);
            }
            else if(userInput.equals("c"))
            {
                out.println(userInput);
            }
            else if(userInput.equals("d"))
            {
                out.println(userInput);
            }
            else
            {
                System.out.println("Wrong input, answer with a, b, c or d");
                userInput = stdIn.readLine();
                out.println(userInput);
            }
            //out.println(userInput);
            if(userInput.equals("exit"))
            {
                break;
            }
           
        }
        System.out.println(in.readLine());
        System.out.println("Closing connetcion with server!");
        out.close();
        in.close();
        stdIn.close();
        quizClient.close();
    }
    
    public static void main(String[] args) throws IOException
    {
        QuizClient client = new QuizClient();
        client.connectingToServer();
    }
}
