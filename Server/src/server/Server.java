package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * This class implements java Socket server
 * @author pankaj
 *
 */

public class Server 
{
    public static void main(String args[]) throws Exception, SQLException 
    {
        int x=0;
        ServerSocket ss = new ServerSocket(3333);
        Socket s = ss.accept();
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader stdin = new BufferedReader(isr);
        String s1="",s2="",rola="",student="s",referent="r";
        while(!s1.equals("stop"))
        {
            s1=dis.readUTF();
            if((x == 0)&&((s1.equals(student))||(s1.equals(referent))))
            {
                rola=s1;
                x=x+1;
            }  
            System.out.println("Client: "+s1);
            if((x == 1)&&(rola.equals(student)))
            {
                try (Connection spojenie = DriverManager.getConnection("jdbc:derby://localhost:1527/Server", "test", "test");
                    PreparedStatement dotaz = spojenie.prepareStatement("SELECT MENO FROM STUDENTI WHERE ID=?");)
                {
                    dotaz.setString(1, s1);
                    try(ResultSet vysledky = dotaz.executeQuery())
                    {
                        vysledky.next();
                        String meno = vysledky.getString("MENO");
                        x=x+1;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Chyba při komunikaci s databází");
                }
            }
            if((x == 2)&&(rola.equals(student)))
            {
                try (Connection spojenie = DriverManager.getConnection("jdbc:derby://localhost:1527/Server", "test", "test");
                PreparedStatement dotaz = spojenie.prepareStatement("SELECT MENO,PRIEZVISKO,ID FROM STUDENTI WHERE HESLO=?");)
                {
                    dotaz.setString(1, s1);
                    try(ResultSet vysledky = dotaz.executeQuery())
                    {
                        vysledky.next();
                        String meno = vysledky.getString("MENO");
                        String priezvisko = vysledky.getString("PRIEZVISKO");
                        String id = vysledky.getString("ID");
                        

                        x=x+1;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Chyba při komunikaci s databází");
                }
            }
            if((x == 1)&&(rola.equals(referent)))
            {
                try (Connection spojenie = DriverManager.getConnection("jdbc:derby://localhost:1527/Server", "test", "test");
                    PreparedStatement dotaz = spojenie.prepareStatement("SELECT MENO FROM REFERENTI WHERE ID=?");)
                {
                    dotaz.setString(1, s1);
                    try(ResultSet vysledky = dotaz.executeQuery())
                    {
                        vysledky.next();
                        String meno = vysledky.getString("MENO");
                        x=x+1;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Chyba při komunikaci s databází");
                }
            }
            if((x == 2)&&(rola.equals(referent)))
            {
                try (Connection spojenie = DriverManager.getConnection("jdbc:derby://localhost:1527/Server", "test", "test");
                PreparedStatement dotaz = spojenie.prepareStatement("SELECT MENO,PRIEZVISKO FROM REFERENTI WHERE HESLO=?");)
                {
                    dotaz.setString(1, s1);
                    try(ResultSet vysledky = dotaz.executeQuery())
                    {
                        vysledky.next();
                        String meno = vysledky.getString("MENO");
                        String priezvisko = vysledky.getString("PRIEZVISKO");
                        x=x+1;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Chyba při komunikaci s databází");
                }
            }
            if(x == 0)
            {
                s2="Pre studenta zadajte 's' pre referenta 'r'";
            }
            if(x == 1)
            {
                s2="Zadaj Id";
            }
            if(x == 2)
            {
                s2="Zadaj heslo";
            }
            if(x == 3)
            {
                s2="Boli ste uspesna zapisany";
                x = 0;
                
            }
            dos.writeUTF(s2);
            dos.flush();
        }
        dis.close();
        s.close();
        ss.close();
    }
}