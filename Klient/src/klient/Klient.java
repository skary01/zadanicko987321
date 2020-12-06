package klient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
/**
 * This class implements java socket client
 * @author Ondo
 *
 */
public class Klient {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        
        Socket socket;
        ObjectOutputStream objectOutputStream ;
        ObjectInputStream objectInputStream ;
        
        for(int i=1; i<6;i++){
            socket = new Socket(host.getHostName(), 9876);
            //establish socket connection to server
            
            //write to socket using ObjectOutputStream
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            if(i==5)objectOutputStream.writeObject("exit");
            else{
                if(i%2!=0)
                {
                    System.out.println("Zadaj Id");
                    Scanner sc = new Scanner( System.in );
                    String id = sc.next();
                    objectOutputStream.writeObject(""+id);
                }
                if(i%2==0)
                {
                    System.out.println("Zadaj heslo");
                    Scanner sc = new Scanner( System.in );
                    String heslo = sc.next();
                    objectOutputStream.writeObject(""+heslo);
                }
            }
            //read the server response message
          objectInputStream = new ObjectInputStream(socket.getInputStream());
          String message = (String) objectInputStream.readObject();
            System.out.println("Prijata sprava: " + message);
            //close resources
            
            Thread.sleep(1000);
            objectInputStream.close();
            objectOutputStream.close();
        }
        
    }
}