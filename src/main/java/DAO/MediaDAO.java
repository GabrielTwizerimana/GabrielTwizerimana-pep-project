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
    public Message NewMessage(Message message){
        Connection conn=ConnectionUtil.getConnection();
        try {
            String sql="INSERT INTO message (posted_id,message_text,time_posted_epoch) VALUES (?,?,?);";
            PreparedStatement prep=conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            prep.setInt(1, message.getPosted_by());
            prep.setString(2, message.getMessage_text());
            prep.setLong(3, message.getTime_posted_epoch());
            prep.executeUpdate();
            ResultSet pkResultSet1=prep.getGeneratedKeys();
            while(pkResultSet1.next()){
                int generatedMess_id=(int) pkResultSet1.getLong(1);
                return new Message(generatedMess_id,message.getPosted_by(),message.getMessage_text(),
                message.getTime_posted_epoch());

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return message;
    }
    public List<Account> GetAllAccounts(){
        Connection conn=ConnectionUtil.getConnection();
        List<Account> accounts=new ArrayList<>();
        try {
            String sql="SELECT * FROM account;";
            PreparedStatement prep=conn.prepareStatement(sql);
            prep.executeQuery();
            ResultSet rs=prep.executeQuery();
           while(rs.next()){
           Account account=new Account(rs.getInt("account_id"),rs.getString("username"),
           rs.getString("password"));
           accounts.add(account);
           }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }
    public List<Message> GetAllMessages(){
        Connection conn=ConnectionUtil.getConnection();
        List<Message> messages=new ArrayList<>();
        try {
            String sql="SELECT * FROM message;";
            PreparedStatement prep=conn.prepareStatement(sql);
            prep.executeQuery();
            ResultSet rs=prep.executeQuery();
           while(rs.next()){
           Message message=new Message(rs.getInt("posted_id"),rs.getString("message_text"),
           rs.getLong("time_posted_epoch"));
           messages.add(message);
           }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
    public Account GetAccountById(int id){
        Connection conn=ConnectionUtil.getConnection();
        try {
            String sql="SELECT * FROM account WHERE account_id=?;";
            PreparedStatement prep=conn.prepareStatement(sql);
            prep.setInt(1, id);
            ResultSet rs=prep.executeQuery();
            while(rs.next()){
            Account account=new Account(rs.getInt("account_id"),rs.getString("username"),rs.getString("password"));
            return account;
            }
        } catch (Exception e) {
           System.out.println(e.getMessage());

        }
        return null;
    }
    public Message GetMessageById(int id){
        Connection conn=ConnectionUtil.getConnection();
        try {
            String sql="SELECT * FROM message WHERE message_id=?;";
            PreparedStatement prep=conn.prepareStatement(sql);
            prep.setInt(1, id);
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
        String sql="DELETE FROM message WHERE message_id=?;";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setInt(1, message_id);
        prep.executeUpdate();
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    return null;
}}
