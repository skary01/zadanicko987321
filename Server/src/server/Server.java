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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
/**
 * 
 * @author Ondirko, Karabinos
 *
 */

public class Server 
{   private final Connection mConnection;
    
    Server() throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        mConnection = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase","test","test");
        
  }
    
    private void printResultSet(ResultSet pResultSet) throws SQLException {
    ResultSetMetaData rsmd = pResultSet.getMetaData();
    int columnsNumber = rsmd.getColumnCount();
    while (pResultSet.next()) {
        for (int i = 1; i <= columnsNumber; i++) {
            if (i > 1) System.out.print(",  ");
            String columnValue = pResultSet.getString(i);
            System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
        }
        System.out.println("");
    }
  }
    
    private void logSimpleQuery(String pCommand) throws SQLException {
        Statement myStatement = mConnection.createStatement();
      
        Timestamp myTs = new Timestamp(System.currentTimeMillis());
        myStatement.executeUpdate("INSERT INTO TEST.MARIA (NAME, SURNAME) VALUES ('"+myTs+"','called: myQuery')");
    }
    
     private void logFullQuery(String pCommand) throws SQLException {
         
        Statement myStatement = mConnection.createStatement();
        String mySqlString = "INSERT INTO TEST.MARIA ( NAME, SURNAME,ID) VALUES ( ?, ?,?)" ;
        PreparedStatement ps = mConnection.prepareStatement( mySqlString );
        ps.setObject( 1,pCommand);
        ps.setString( 2, pCommand ) ;
        ps.setString( 3, pCommand) ;
        ps.executeUpdate();
    }
    
     
     
  public void myQuery(String pCommand) throws SQLException {
        Statement myStatement = mConnection.createStatement();
        ResultSet myResultSet = myStatement.executeQuery(pCommand);
        logSimpleQuery(pCommand);
       logFullQuery(pCommand);
       printResultSet (myResultSet);
    }  
    
    
    
    
    public static void main(String args[]) throws Exception, SQLException 
    {    Server myWrapper = new Server() ;
    
    
    
        int x=0;
        ServerSocket ss = new ServerSocket(3333);
        Socket s = ss.accept();
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader stdin = new BufferedReader(isr);
        String s1="",s2="",rola="",student="student",referent="referent";
        while(!s1.equals("stop"))
        {
            s1=dis.readUTF();
            if(x == 0)
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    PreparedStatement dotaz = connect.prepareStatement("SELECT TYPE_OF_CLIENT FROM CLIENTS WHERE ID=?");)
                {
                    dotaz.setString(1, s1);
                    try(ResultSet result = dotaz.executeQuery())
                    {
                        result.next();
                        rola = result.getString("TYPE_OF_CLIENT");
                        x=x+1;
                        
                
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
                
            }  
            System.out.println("Client: "+s1);
            if((x == 1)&&(rola.equals(student)))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    PreparedStatement dotaz = connect.prepareStatement("SELECT NAME FROM CLIENTS WHERE ID=?");)
                {
                    dotaz.setString(1, s1);
                    try(ResultSet result = dotaz.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        x=x+1;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            if((x == 2)&&(rola.equals(student)))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                PreparedStatement dotaz = connect.prepareStatement("SELECT NAME,SURNAME,ID FROM CLIENTS WHERE PASSWORD=?");)
                {
                    dotaz.setString(1, s1);
                    try(ResultSet result = dotaz.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        String surname = result.getString("SURNAME");
                        String id = result.getString("ID");
                        
                        x=x+1;
                        if((x == 3)&&(rola.equals(student)))
            {   
              myWrapper.myQuery("SELECT * FROM MARIA");
                
                
                
            }
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            if((x == 1)&&(rola.equals(referent)))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    PreparedStatement dotaz = connect.prepareStatement("SELECT NAME FROM CLIENTS WHERE ID=?");)
                {
                    dotaz.setString(1, s1);
                    try(ResultSet result = dotaz.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        x=x+1;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            if((x == 2)&&(rola.equals(referent)))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                PreparedStatement dotaz = connect.prepareStatement("SELECT NAME,SURNAME FROM CLIENTS WHERE PASSWORD=?");)
                {
                    dotaz.setString(1, s1);
                    try(ResultSet result = dotaz.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        String surname = result.getString("SURNAME");
                        x=x+1;
                        
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            
            
            
            
            
            if(x == 0)
            {
                s2="Enter ID"; 
            }
            if(x == 1)
            {
                s2="Enter ID";
            }
            if(x == 2)
            {
                s2="Enter Password";
            }
            
            if(x == 3)
            {
                s2="You have been successfully added";
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