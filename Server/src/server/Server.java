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
 * 
 * @author Ondirko, Karabinos
 *
 */

public class Server 
{   
    public static void main(String args[]) throws Exception, SQLException 
    {    
        Server myWrapper = new Server() ;

        int x=0;
        ServerSocket serverSocket = new ServerSocket(3333);
        Socket socket = serverSocket.accept();
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(inputStreamReader);
        
        String socket1="",socket2="",socket3="",rola="",student="student",referent="referent",maria="maria",robert="robert",vypis="vypis",helpID="",helpName="";
        while(!socket1.equals("stop"))
        {
            socket1=dataInputStream.readUTF();
            if(x == 0)
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    PreparedStatement preparedStatement = connect.prepareStatement("SELECT TYPE_OF_CLIENT FROM CLIENTS WHERE ID=?");)
                {
                    preparedStatement.setString(1, socket1);
                    try(ResultSet result = preparedStatement.executeQuery())
                    {
                        result.next();
                        rola = result.getString("TYPE_OF_CLIENT");
                        helpID = socket1;
                        x=1;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }  
            
            
            
            
            
            System.out.println("Client: "+socket1);
            if((x == 1)&&(rola.equals(student)))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    PreparedStatement preparedStatement = connect.prepareStatement("SELECT NAME FROM CLIENTS WHERE ID=?");)
                {
                    preparedStatement.setString(1, socket1);
                    try(ResultSet result = preparedStatement.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        x=2;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            if((x == 2)&&(rola.equals(student)))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                PreparedStatement preparedStatement = connect.prepareStatement("SELECT NAME,ID FROM CLIENTS WHERE PASSWORD=?");)
                {
                    preparedStatement.setString(1, socket1);
                    try(ResultSet result = preparedStatement.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        
                        x=69;
                        socket2="Ak chcete sa zapisat zadajte 1, Ak chcete vypisat kde vase schodzky zadajte 2";
                        dataOutputStream.writeUTF(socket2);
                        dataOutputStream.flush();
                        socket1=dataInputStream.readUTF();
                        
                        
                        if(socket1.equals("1"))
                                {  
                                    socket2="Ak chcete sa zapisat pri marii zadajte 7 , Ak chcete sa zapisat pri robertovi zadajte 8";
                                    dataOutputStream.writeUTF(socket2);
                                    dataOutputStream.flush();
                                    socket1=dataInputStream.readUTF();
                                    
                                    if(socket1.equals("7"))
                                {   x=7;
                                    
                                
                                }   
                                    if(socket1.equals("8"))
                                {   x=8;
                                    
                        
                                 }
                                
                        
                                   }
                        if(socket1.equals("2"))
                                {   x=4;
                        
                                   }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            }
            if(x == 7)
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                PreparedStatement preparedStatement = connect.prepareStatement("SELECT NAME,SURNAME,ID FROM CLIENTS WHERE ID=?");)
                {
                    preparedStatement.setString(1, helpID);
                    try(ResultSet result = preparedStatement.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        String surname = result.getString("SURNAME");
                        String id = result.getString("ID");
                        Statement statement = connect.createStatement();
                        statement.executeUpdate("insert into TEST.MARIA(name,surname,id) values('"+name+"','"+surname+"','"+id+"')");
                        statement.executeUpdate("UPDATE CLIENTS SET EXAM_Maria='Zapisany u Marii' WHERE ID='"+id+"' ");
                        System.out.println("Data was inserted ");
                        x=0;
                        
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            if(x == 8)
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                PreparedStatement preparedStatement = connect.prepareStatement("SELECT NAME,SURNAME,ID FROM CLIENTS WHERE ID=?");)
                {
                    preparedStatement.setString(1, helpID);
                    try(ResultSet result = preparedStatement.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        String surname = result.getString("SURNAME");
                        String id = result.getString("ID");
                        Statement statement = connect.createStatement();
                        statement.executeUpdate("insert into TEST.ROBERT(name,surname,id) values('"+name+"','"+surname+"','"+id+"')");
                        statement.executeUpdate("UPDATE CLIENTS SET EXAM_ROBERT='Zapisany u Roba' WHERE ID='"+id+"' ");
                        System.out.println("Data was inserted ");
                        x=0;                    
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            
            
            
            if((x == 1)&&(rola.equals(referent)))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    PreparedStatement preparedStatement = connect.prepareStatement("SELECT NAME FROM CLIENTS WHERE ID=?");)
                {
                    preparedStatement.setString(1, socket1);
                    try(ResultSet result = preparedStatement.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        helpName=name;
                        x=2;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            if((x == 2)&&(rola.equals(referent))) 
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    PreparedStatement preparedStatement = connect.prepareStatement("SELECT NAME,ID FROM CLIENTS WHERE PASSWORD=?");)
                {
                    preparedStatement.setString(1, socket1);
                    try(ResultSet result = preparedStatement.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");   
                        x=69;
                        socket2="Ak chcete sa vypisat prihlasenych studentov zadajte 1, Ak chcete posunut 2";
                        
                        dataOutputStream.writeUTF(socket2);
                        dataOutputStream.flush();
                        
                        socket1=dataInputStream.readUTF();
                        if(socket1.equals("1"))
                                {   x=10;
                        
                                   }
                        if(socket1.equals("2"))
                                {   x=20;
                        
                                   }
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            if((x == 10)&&(rola.equals(referent)))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    PreparedStatement preparedStatement = connect.prepareStatement("SELECT NAME,SURNAME,ID FROM FROM '"+helpName+"' ");)
                {
                    preparedStatement.setString(1, helpName);
                    try(ResultSet result = preparedStatement.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        socket2=name;
                        dataOutputStream.writeUTF(socket2);
                        
                        String surname = result.getString("SURNAME");
                        socket2=surname;
                        dataOutputStream.writeUTF(socket2);
                        
                        String id = result.getString("ID");
                        socket2=id;
                        dataOutputStream.writeUTF(socket2); 
                        
                        
                        x=5;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
                
            
            
            if((x == 4)&&(rola.equals(student)))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    PreparedStatement preparedStatement = connect.prepareStatement("SELECT EXAM_MARIA, EXAM_ROBERT FROM CLIENTS WHERE ID=?");)
                {
                    preparedStatement.setString(1, helpID);
                    try(ResultSet result = preparedStatement.executeQuery())
                    {
                        result.next();
                        String examMaria = result.getString("EXAM_MARIA");
                        String examRobert = result.getString("EXAM_ROBERT");
                        socket2=examMaria;
                        dataOutputStream.writeUTF(socket2);
                        socket2=examRobert;
                        dataOutputStream.writeUTF(socket2); 
                        
                        
                        x=5;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            
            if((x == 21)&&(rola.equals(referent)))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    PreparedStatement preparedStatement = connect.prepareStatement("ERASE NAME,SURNAME,ID FROM '"+helpName+"' ");)
                {
                    preparedStatement.setString(1, helpName);
                    try(ResultSet result = preparedStatement.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        socket2=name;
                        dataOutputStream.writeUTF(socket2);
                        
                        String surname = result.getString("SURNAME");
                        socket2=surname;
                        dataOutputStream.writeUTF(socket2);
                        
                        String id = result.getString("ID");
                        socket2=id;
                        dataOutputStream.writeUTF(socket2); 
                        
                        
                        x=5;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            
            if(x == 0)
            {
                socket2="Enter ID"; 
            }
            if(x == 2)
            {
                socket2="Enter password";
            }     
            if(x == 69)
            {
                
                socket2="Nechcel vypisat";
                
            }
            if(x == 5)
            {
                socket2="Vypisane";
                x=0;
            } 
  
            
            dataOutputStream.writeUTF(socket2);
            dataOutputStream.flush();
            
        }
        dataInputStream.close();
        socket.close();
        serverSocket.close();
    }
}