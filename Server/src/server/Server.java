package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import java.sql.SQLException;

/**
 * This class implements java Socket server
 * @author Ondo
 *
 */

public class Server {

    //static ServerSocket variable
    private static ServerSocket serverSocket;
    //socket server port on which it will listen
    private static int portKlient = 9876;
    //private static int portDatabaza = 1527;
    
    public static void main(String args[]) throws IOException, SQLException, ClassNotFoundException, Exception{
        
       
        
        //create the socket server object
        serverSocket = new ServerSocket(portKlient);
//        server = new ServerSocket(portDatabaza);
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            int a=0;
            System.out.println("Cakanie na poziadavky klienta");
            //creating socket and waiting for client connection
            Socket socket = serverSocket.accept();
            //read from socket to ObjectInputStream object
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            //convert ObjectInputStream object to String
            String message = (String) objectInputStream.readObject();
            System.out.println("Poziadavka klienta: " + message);
            
             try (Connection spojenie = DriverManager.getConnection("jdbc:derby://localhost:1527/Server", "test", "test");
                    PreparedStatement dotaz = spojenie.prepareStatement("SELECT MENO FROM REFERENTI WHERE CAST(ID AS VARCHAR (128))= 'massage'"); )
            {
               
               
                try(ResultSet vypis = dotaz.executeQuery())
                {
                
                    while (vypis.next()) 
                    {   
                        String id = vypis.getString("ID");;
                        System.out.println("ID: " + id);
                    }
                }
            }
            catch(Exception e){System.out.println(e);}
            
            
            
            //create ObjectOutputStream object
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            //write object to Socket
           
                objectOutputStream.writeObject(" "+message);
            
            
            //close resources
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
            //terminate the server if client sends exit request
            if(message.equalsIgnoreCase("exit")) break;
        }
        System.out.println("Vpnutie servera");
        //close the ServerSocket object
        serverSocket.close();
    }
}