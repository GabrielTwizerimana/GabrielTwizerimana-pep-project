package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import Util.ConnectionUtil;

public class MediaDAO {
    public List<Media> GetAllMessages(){
        Connection conn=ConnectionUtil.getConnection();
        try {
            String sql="SELECT * FROM message;";
            PreparedStatement preps=conn.prepareStatement(sql);
            ResultSet rs=preps.executeQuery();
            while(rs.next()){
                

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
