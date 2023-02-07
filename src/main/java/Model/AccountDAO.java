package AccountDAO;

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

public class AccountDAO {
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

}