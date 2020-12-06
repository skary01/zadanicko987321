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
        Socket s = new Socket("localhost",3333);
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader stdin = new BufferedReader(isr);
        String s1="",s2="";
        System.out.println("'s' pre studenta 'r' pre refeerenta");  
        while(!s1.equals("stop"))
        {         
            s2=stdin.readLine();
            dos.writeUTF(s2);
            dos.flush();
            s1=dis.readUTF();
            System.out.println("Server: "+s1);
        }
        dos.close();
        s.close();
    }
}