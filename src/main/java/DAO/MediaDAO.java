package DAO;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import javafx.scene.media.Media;
public class MediaDAO {
    Account account;
    Message message;
   
    public Account NewAccount(Account account){
        Connection conn=ConnectionUtil.getConnection();
        try {
            String sql="INSERT INTO account (username,password) VALUES (?,?);";
            PreparedStatement prep=conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            prep.setString(1, account.getUsername());
            prep.setString(2, account.getPassword());
            prep.executeUpdate();
            ResultSet pkResultSet=prep.getGeneratedKeys();
            while(pkResultSet.next()){
              
                int generatedAcc_id=(int) pkResultSet.getLong(1);
                return new Account(generatedAcc_id,account.getUsername(),account.getPassword());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return account;
    }
    
    
    public Message patchingMessage(int id,Message message){
        Connection conn=ConnectionUtil.getConnection();
        try {
            String sql="update message set message_text=? where message_id=?;";
            PreparedStatement prep=conn.prepareStatement(sql);
           prep.setString(1,message.getMessage_text());
            prep.setInt(2,id);
            prep.executeUpdate();
            return GetMessageById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public Message NewMessage(Message message){
        Connection conn=ConnectionUtil.getConnection();
        try {
            String sql="INSERT INTO message (posted_by,message_text,time_posted_epoch) VALUES (?,?,?);";
            PreparedStatement prep=conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            prep.setInt(1, message.getPosted_by());
            prep.setString(2, message.getMessage_text());
            prep.setLong(3, message.getTime_posted_epoch());
            prep.executeUpdate();
            ResultSet pkResultSet1=prep.getGeneratedKeys();
            while(pkResultSet1.next()){
                int generatedMess_id=(int) pkResultSet1.getInt(1);
                return new Message(generatedMess_id,message.getPosted_by(),message.getMessage_text(),
                message.getTime_posted_epoch());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return message;
    }


    public List<Message> GetAllMessages(){
        Connection conn=ConnectionUtil.getConnection();
       
        try {
            String sql="SELECT * FROM message;";
            PreparedStatement prep=conn.prepareStatement(sql);
            prep.executeQuery();
            ResultSet rs=prep.executeQuery();
            List<Message> messages=new ArrayList<>();
           while(rs.next()){
        
           Message message=new Message(rs.getInt("message_id"),
           rs.getInt("posted_by"),
           rs.getString("message_text"),
           rs.getLong("time_posted_epoch"));
           messages.add(message);
           }
           return messages;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
       return null;
    }
    public List<Message> GetAllMessagesByAccountID(int id){
        Connection conn=ConnectionUtil.getConnection();
       
        try {
            String sql="SELECT * FROM message where posted_by=?;";
            PreparedStatement prep=conn.prepareStatement(sql);
            prep.setInt(1, id);
            prep.executeQuery();
            ResultSet rs=prep.executeQuery();
            List<Message> messages=new ArrayList<>();
           while(rs.next()){
        
           Message message=new Message(rs.getInt("message_id"),
           rs.getInt("posted_by"),
           rs.getString("message_text"),
           rs.getLong("time_posted_epoch"));
           messages.add(message);
           }
           return messages;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
       return null;
    }


    public Message GetMessageById(int id){
        Connection conn=ConnectionUtil.getConnection();
        
        try {
            String sql="select * from message where message_id=?;";
            PreparedStatement prep=conn.prepareStatement(sql);
            prep.setInt(1,id);
            prep.executeQuery();
            ResultSet rs=prep.executeQuery();
           while(rs.next()){
           Message hello=new Message(rs.getInt("message_id"),
           rs.getInt("posted_by"),
           rs.getString("message_text"),
           rs.getLong("time_posted_epoch"));
           return hello;
           }
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public Account GetAccountById(int id){
        Connection conn=ConnectionUtil.getConnection();
        
        try {
            String sql="SELECT * FROM account WHERE account_id=?;";
            PreparedStatement prep=conn.prepareStatement(sql);
            prep.setInt(1, id);
            ResultSet rs=prep.executeQuery();
            while(rs.next()){
            Account account=new Account(rs.getInt("account_id"),
            rs.getString("username"),
            rs.getString("password"));
            return account;
            }
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }
        return null;
    }

    
    public Message GetMessageByMsgposted(String message_text){
        Connection conn=ConnectionUtil.getConnection();
        try {
            String sql="SELECT * FROM message WHERE message_text=?;";
            PreparedStatement prep=conn.prepareStatement(sql);
            prep.setString(1, message_text);
            ResultSet rs=prep.executeQuery();
            while(rs.next()){
            Message message=new Message(rs.getInt("message_id"),
            rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
            return message;
            }
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }
        return null;
    }


    public Message deleteMessage(int message_id){
        Connection conn=ConnectionUtil.getConnection();
    try {
        String sql="delete from message where message_id=?;";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setInt(1, message_id);
        prep.executeUpdate();
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    return null;
}


public Account login(Account account){
    Connection conn=ConnectionUtil.getConnection();
try {
    
    String sql="select * from account WHERE username=? and password=?;";
    PreparedStatement prep=conn.prepareStatement(sql);
    prep.setString(1,account.getUsername());
    prep.setString(2,account.getPassword());
    ResultSet rs=prep.executeQuery();
    
    while(rs.next()){
        account=new Account(rs.getInt("account_id"),rs.getString("username"),
       rs.getString("password"));
        return account;
        }
       
} catch (Exception e) {
    System.out.println(e.getMessage());
}
return null;
}


public List<Message> GetAllMessageByPosted_By(){
    Connection conn=ConnectionUtil.getConnection();
    List<Message> messages=new ArrayList<>();
    try {
        String sql="SELECT * FROM message WHERE posted_by=?;";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setInt(1,message.getPosted_by());
        ResultSet rs=prep.executeQuery();
        while(rs.next()){
        Message message=new Message(rs.getInt("message_id"),
        rs.getInt("posted_by"),
        rs.getString("message_text"),rs.getLong("time_posted_epoch"));
        messages.add(message);
        }
    } catch (Exception e) {
       System.out.println(e.getMessage());
    }
    return messages;
}

}

