

package Homework3;
import java.io.*;
import java.net.*;
public class QuizServer {
    
    private static final int PORT_NUMB = 2600;
    
    private final int NUMBER_OF_QUESTIONS = 10;
    //sockets
    private ServerSocket serverSocket;
    private Socket clientSocket;
    // I/O
    private BufferedReader in;
    private PrintWriter out;
    
    
    
    private String clientAnswers[]; //array for client answers
    private String correctAnswers[];//array for correct answers
    private String questions[];
    
    public int counter;
    
    public QuizServer()
    {
        serverSocket = null;
        clientSocket = null;
        
        in = null;
        out = null;
        
        counter = 0;
        
        initQuestionsAnswers();
        
    }
    public void initQuestionsAnswers()
    {
       questions = new String[NUMBER_OF_QUESTIONS];
       clientAnswers = new String[NUMBER_OF_QUESTIONS];
       correctAnswers = new String[NUMBER_OF_QUESTIONS];//add the correct answers
       correctAnswers[0] = "d";
       correctAnswers[1] = "b";
       correctAnswers[2] = "d";
       correctAnswers[3] = "a";
       correctAnswers[4] = "a";
       correctAnswers[5] = "a";
       correctAnswers[6] = "b";
       correctAnswers[7] = "d";
       correctAnswers[8] = "d";
       correctAnswers[9] = "b";
       
       
       questions[0] = "1. What is the correct syntax for java main method? a) public void main(String[] args) b) public static void main(string[] args) c) public static void main() d) none of the above";//d
       questions[1] = "2. What is a constructor? a) An object that is created when a java program is build. b) A method that is invoked when an object is created. c) An abstract class used as a blueprint. d) none of the above";//b
       questions[2] = "3. What is the size of double variable? a) 8 bit. b) 16 bit. c) 32 bit. d) 64 bit.";//d
       questions[3] = "4. What is local variable? a) Variables defined inside methods, constructors or blocks are called local variables. b) Variables defined outside methods, constructors or blocks are called local variables. c) Static variables defined outside methods, constructors or blocks are called local variables. d) ";//a
       questions[4] = "5. What is an applet? a) An applet is a Java program that runs in a Web browser. b) Applet is a standalone java program. c) Applet is a tool. d) Applet is a run time environment."; //a
       questions[5] = "6. When finally block gets executed? a) Always when try block get executed, no matter exception occured or not. b)  Always when a method get executed, no matter exception occured or not. c) Always when a try block get executed, if exception do not occur. d) Only when exception occurs in try block code.";//a
       questions[6] = "7. What is class variable? a) Class variables are variables defined inside methods, constructors or blocks. b) Class variables are static variables within a class but outside any method. c) Class variables are variables within a class but outside any method. d) None of the above.";//b
       questions[7] = "8. What is true about a final class? a) Class declard final is a final class. b) Final classes are created so the methods implemented by that class cannot be overridden. c) It can't be inherited. d) All of the above.";//d
       questions[8] = "9. Which method must be implemented by all threads? a) wait() b) start() c) stop() d) run()";//d
       questions[9] = "10. In which case, a program is expected to recover? a) If an error occurs. b) If an exception occurs. c) Both of the above. d) None of the above.";//b
       
    }
    public String getQuestionsAt(int index)
    {
        return questions[index];
    }
    public void printClientAnswers()
    {
        System.out.println("Client Answers ");
        for(int i = 0; i < NUMBER_OF_QUESTIONS; i++)
        {
            System.out.println(clientAnswers[i]);
        }
    }
    public int checkAnswers()
    {
        
        for(int i = 0; i < NUMBER_OF_QUESTIONS; i++)
        {
            if(clientAnswers[i].equals(correctAnswers[i]))
            {
                counter++;
            }
        }
        return counter;
    }
    public void establishConnection() throws IOException
    {
        try
        {
            System.out.println("Listening for clients...");
            serverSocket = new ServerSocket(PORT_NUMB);
        }
        catch (IOException excpt)
        {
            System.err.println("Cannot listen on port: " + PORT_NUMB);
            System.err.println("Exception: " + excpt.getMessage());
            System.exit(1);
        }
        
        while (true)
        {
            try
            {
                clientSocket = serverSocket.accept();
            }
            catch (IOException excpt)
            {
                System.err.println("Accept failed.");
                System.err.println("Exception: " + excpt.getMessage());
                System.exit(1);
            }
            
            System.out.println("Accepted connection from client."); 
            try
            {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//BufferedReader
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("Java quiz. Please answer the questions with a,b,c or d.");
                for(int i = 0; i < NUMBER_OF_QUESTIONS; i++)
                {
                    out.println(getQuestionsAt(i));
                    clientAnswers[i] = in.readLine();
                    if(clientAnswers[i] == "exit")
                    {
                        break;
                    }
                    System.out.println("Client says: " + clientAnswers[i]);
                }         
            }
            catch (IOException excpt)
            {
                    System.err.println("Exception: " + excpt.getMessage());
                    System.out.println("Closing connection with client.");
                
                    // free resources
                    out.close();
                    in.close();
                    System.exit(1);
            }
            printClientAnswers();
            out.println("Correct answers: " + checkAnswers());
            
            System.out.println("Closing connection with client.");
                
            out.close();
            in.close();                        
        }
    }
    
    
    public static void main(String[] args) throws IOException 
    {
        QuizServer server = new QuizServer();
        server.establishConnection();
    }
}
