package TicTac;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;




public class TicTacClient extends JFrame 
{
    //client part
    private String HOST_NAME;
    private int PORT_NUMB;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    //GUI part
    private JPanel panel;
    //1st row of buttons
    private JButton button00;
    private JButton button01;
    private JButton button02;
    //2nd row of buttons
    private JButton button10;
    private JButton button11;
    private JButton button12;
    //3rd row of buttons
    private JButton button20;
    private JButton button21;
    private JButton button22;
    
    
    
    
    public TicTacClient()
    {
        clientSocket = null;
        out = null;
        in = null;
        button00 = null;
        PORT_NUMB = 0;
        HOST_NAME = null;
        this.setTitle("TicTacToe");
        initComponents();
    }
    //client part
    public void setPort(int port_number)
    {
        PORT_NUMB = port_number;
    }
    public void setHost(String hostname)
    {
        HOST_NAME = hostname;
    }
    //establishing connection with server
    public void connectToServer()throws IOException
    {
        try
        {
            clientSocket = new Socket(HOST_NAME, PORT_NUMB);
            System.out.println("Connected to server on PORT: " + PORT_NUMB);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
            System.err.println("Exception: " + e.getMessage());
            System.exit(1);
        }
        ////
        //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        Scanner scan = new Scanner(System.in);
      //  System.out.println("Server says: " + in.readLine());
        int userInput;
        boolean listening = true;
        
        while(listening)
        {
            System.out.println("Server says: " + in.readLine());
            //userInput = scan.nextInt();
            //out.println(userInput);
           // System.out.println("Sled iskane");
        }
        /*
        while((userInput = scan.next()) != null)
        {
            out.println(userInput);
            if(userInput.equals("quit"))
            {
                break;
            }
            System.out.println("echo: " + in.readLine());
        }*/
    }
    //reading and writing to the server
    public void readWrite() throws IOException
    {
        try
        {
            Scanner scan = new Scanner(System.in);
            //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            int userInput;
            System.out.println(in.readLine());
            System.out.println(in.read());
            while((userInput = scan.nextInt()) != 1000)
            {
                out.println(userInput);
                System.out.println("Server: " + in.readLine());
            }
        }
        catch(IOException e)
        {
            out.close();
            in.close();
            //scan.close();
            clientSocket.close();
        }
        /*
        Scanner scan = new Scanner(System.in);
        //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        int userInput;
        System.out.println(in.readLine());
        System.out.println(in.read());
        while((userInput = scan.nextInt()) != 1000)
        {
            out.println(userInput);
            System.out.println("Server: " + in.readLine());
        }
        out.close();
        in.close();
        scan.close();
        clientSocket.close();*/
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //gui part
    public void initComponents()
    {
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createPanel();
        add(panel);
        setSize(600, 600);
        setVisible(true);
    }
    private void createPanel()
    {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        createBoard();
    }
    private void createBoard()
    {
        
        
        //1st row
        button00 = new JButton();
        button00.setFocusPainted(false);
        button01 = new JButton();
        button02 = new JButton();
        //2nd row
        button10 = new JButton();
        button11 = new JButton();
        button12 = new JButton();
        //3rd row
        button20 = new JButton();
        button21 = new JButton();
        button22 = new JButton();
        //setting maximum size for the buttons
        //removing focuns 
        button00.setPreferredSize(new Dimension(50, 50));
        button00.setFocusPainted(false);
        button01.setPreferredSize(new Dimension(50, 50));
        button01.setFocusPainted(false);
        button02.setPreferredSize(new Dimension(50, 50));
        button02.setFocusPainted(false);
        
        button10.setPreferredSize(new Dimension(50, 50));
        button10.setFocusPainted(false);
        button11.setPreferredSize(new Dimension(50, 50));
        button11.setFocusPainted(false);
        button12.setPreferredSize(new Dimension(50, 50));
        button12.setFocusPainted(false);
        
        button20.setPreferredSize(new Dimension(50, 50));
        button20.setFocusPainted(false);
        button21.setPreferredSize(new Dimension(50, 50));
        button21.setFocusPainted(false);
        button22.setPreferredSize(new Dimension(50, 50));
        button22.setFocusPainted(false);
        
        GridBagConstraints c = new GridBagConstraints();//gridbagconstraints to set the coordinates for the components 
        c.gridx = 0;//1st column
        c.gridy = 0;//1st row
        c.ipadx = 50;
        c.ipady = 50;
        panel.add(button00, c);//adding button00
        c.gridx = 1;//2nd column
        panel.add(button01, c);//adding button01
        c.gridx = 2;//3rd column
        panel.add(button02, c);//adding button02
        
        c.gridx = 0;//1st column
        c.gridy = 1;//2nd row
        panel.add(button10, c);//adding button10
        c.gridx = 1;//2nd column
        panel.add(button11, c);//adding button11
        c.gridx = 2;//3rd column
        panel.add(button12, c);//adding button12
        
        c.gridx = 0;//1st column
        c.gridy = 2;//3rd row
        panel.add(button20, c);
        c.gridx = 1;//2nd column
        panel.add(button21, c);
        c.gridx = 2;//3rd column
        panel.add(button22, c);
        
        //button00 ActionListener TODO implement the other buttons actionListeners!!!!!!!!!!!!!!!!!!!
        class Button00Listener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Button00 pressed.");
                Icon icn = new ImageIcon("x.png");
                button00.setIcon(icn);
               
            }
        }
        ActionListener button00Listener = new Button00Listener();
        button00.addActionListener(button00Listener);
    }
    public static void main(String[] args) throws IOException
    {
        System.out.println("TicTacClient Start");
        
        TicTacClient client = new TicTacClient();
        client.setHost("localhost");
        client.setPort(2500);
        client.connectToServer();
      
        //client1.readWrite();
       /* 
        java.awt.EventQueue.invokeLater(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        new TicTacClient().setVisible(true);
                    }
                }
        
        );*/
    }
}    
    /*
    public void setPort(int port_number)
    {
        PORT_NUMB = port_number;
    }
    public void setHost(String hostname)
    {
        HOST_NAME = hostname;
    }
    public void initClient()throws IOException
    {
        try
        {
            clientSocket = new Socket(HOST_NAME, PORT_NUMB);
            System.out.println("Connected to server on PORT: " + PORT_NUMB);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
            System.err.println("Exception: " + e.getMessage());
            System.exit(1);
        }
    }
    public void readWrite() throws IOException
    {
        Scanner scan = new Scanner(System.in);
        //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        int userInput;
        System.out.println(in.readLine());
        System.out.println(in.read());
        while((userInput = scan.nextInt()) != 1000)
        {
            out.println(userInput);
            System.out.println("Server: " + in.readLine());
        }
        out.close();
        in.close();
        scan.close();
        clientSocket.close();
    }
    private static void show()
    {
        JFrame frame = new JFrame("TicTacToe");
        GridBagConstraints c = new GridBagConstraints();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        JLabel hostLabel = new JLabel("Enter host name: ");
        JLabel portLabel = new JLabel("Enter port number: ");
        JTextField hostField = new JTextField();
        JTextField portField = new JTextField();
        hostField.setSize(15, 5);
        JButton connectButton = new JButton("Connect");
        c.gridx = 0;//coordinates for the host label
        c.gridy = 0;
        frame.getContentPane().add(hostLabel, c); // adding tyhe host label
        c.gridy = 1;
        frame.add(portLabel, c);//adding the port label
        c.gridx = 1;//coordinates for the host field
        c.gridy = 0;
        c.ipadx = 200;
        frame.add(hostField, c);//host field added
        //coordinates for port field
        c.gridy = 1;
        frame.add(portField, c);//port field
        c.gridx = 3;//coordinates for the connect button
        c.gridy = 0;
        c.ipadx = 50;
        frame.add(connectButton, c);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }
    private void actionButton()
    {
        class AddConnectListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int port_number = Integer.parseInt(portField.getText());
            }
        }
    }
    public static void main(String[] args) throws IOException
    {
        
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run()
         {
             show();
         }
            
        });
        
        
        
        
        try
        {
            clientSocket = new Socket(HOST_NAME, PORT_NUMB);
            System.out.println("Connected to server on PORT: " + PORT_NUMB);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
            System.err.println("Exception: " + e.getMessage());
            System.exit(1);
        }
        
        Scanner scan = new Scanner(System.in);
        //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        int userInput;
        System.out.println(in.readLine());
        System.out.println(in.read());
        while((userInput = scan.nextInt()) != 1000)
        {
            out.println(userInput);
            System.out.println("Server: " + in.readLine());
        }
        out.close();
        in.close();
        scan.close();
        clientSocket.close();
        
        
        
        
    }

}

*/











/*
public class TicTacClient extends JFrame {
    
    private String HOST_NAME;
    private int PORT_NUMB;
    
    Socket clientSocket;
    PrintWriter out;
    BufferedReader in;
    public TicTacClient()
    {
        clientSocket = null;
        out = null;
        in = null;
        
        PORT_NUMB = 0;
        HOST_NAME = null;
        this.setTitle("TicTacToe");
    }
    public void setPort(int port_number)
    {
        PORT_NUMB = port_number;
    }
    public void setHost(String hostname)
    {
        HOST_NAME = hostname;
    }
    public void initClient()throws IOException
    {
        try
        {
            clientSocket = new Socket(HOST_NAME, PORT_NUMB);
            System.out.println("Connected to server on PORT: " + PORT_NUMB);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
            System.err.println("Exception: " + e.getMessage());
            System.exit(1);
        }
    }
    public void readWrite() throws IOException
    {
        Scanner scan = new Scanner(System.in);
        //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        int userInput;
        System.out.println(in.readLine());
        System.out.println(in.read());
        while((userInput = scan.nextInt()) != 1000)
        {
            out.println(userInput);
            System.out.println("Server: " + in.readLine());
        }
        out.close();
        in.close();
        scan.close();
        clientSocket.close();
    }
    private static void show()
    {
        JFrame frame = new JFrame("TicTacToe");
        GridBagConstraints c = new GridBagConstraints();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        JLabel hostLabel = new JLabel("Enter host name: ");
        JLabel portLabel = new JLabel("Enter port number: ");
        JTextField hostField = new JTextField();
        JTextField portField = new JTextField();
        hostField.setSize(15, 5);
        JButton connectButton = new JButton("Connect");
        c.gridx = 0;//coordinates for the host label
        c.gridy = 0;
        frame.getContentPane().add(hostLabel, c); // adding tyhe host label
        c.gridy = 1;
        frame.add(portLabel, c);//adding the port label
        c.gridx = 1;//coordinates for the host field
        c.gridy = 0;
        c.ipadx = 200;
        frame.add(hostField, c);//host field added
        //coordinates for port field
        c.gridy = 1;
        frame.add(portField, c);//port field
        c.gridx = 3;//coordinates for the connect button
        c.gridy = 0;
        c.ipadx = 50;
        frame.add(connectButton, c);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }
    private void actionButton()
    {
        class AddConnectListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int port_number = Integer.parseInt(portField.getText());
            }
        }
    }
    public static void main(String[] args) throws IOException
    {
        
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run()
         {
             show();
         }
            
        });
        
        
        
        
        try
        {
            clientSocket = new Socket(HOST_NAME, PORT_NUMB);
            System.out.println("Connected to server on PORT: " + PORT_NUMB);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
            System.err.println("Exception: " + e.getMessage());
            System.exit(1);
        }
        
        Scanner scan = new Scanner(System.in);
        //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        int userInput;
        System.out.println(in.readLine());
        System.out.println(in.read());
        while((userInput = scan.nextInt()) != 1000)
        {
            out.println(userInput);
            System.out.println("Server: " + in.readLine());
        }
        out.close();
        in.close();
        scan.close();
        clientSocket.close();
        
        
        
        
    }

}
*/
