package klient;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This class implements java socket client
 * @author Ondirko, Karabinos
 *
 */
public class Klient {

    public static void main(String[] args) throws Exception
    { 
        Socket socket = new Socket("localhost",3333);
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(inputStreamReader);
        
        String socket1="",socket2="";
        System.out.println("Enter ID");  
        while(!socket1.equals("stop"))
        {         
            socket2=buffer.readLine();
            dataOutputStream.writeUTF(socket2);
            dataOutputStream.flush();
            socket1=dataInputStream.readUTF();
            System.out.println("Server: "+socket1);
        }
        dataOutputStream.close();
        socket.close();
    }
}