package Service;

import java.util.List;

import org.junit.runners.Parameterized.Parameter;
//import io.javalin.http.Handler;
import DAO.MediaDAO;
import Model.Account;
import Model.Message;
import io.javalin.http.Context;

public class MediaService {
 MediaDAO mediadao;

 public MediaService(){
    this.mediadao=new MediaDAO();
 }
 
 public MediaService(MediaDAO mediadao){
 this.mediadao=mediadao;
 }
 public Account addAccount(Account account){
    return mediadao.NewAccount(account);
 }
 public List<Account> getAllAcc(){
   return mediadao.GetAllAccounts();
 }
 public Message addMessage(Message message){
  if(message.message_text.length()>255 || message.message_text.isBlank()){
    return null;
  }
   else{
    return mediadao.NewMessage(message);
   }
 }
    public List<Message> getAllMsg(){
     
      return mediadao.GetAllMessages();
    }
   
    public List<Message> postManyAccountsAndMessagesThenGetMessagesByAccountIdService(){
      return mediadao.postManyAccountsAndMessagesThenGetMessagesByAccountIdDAO();
    }
  
    public Account loginPass(Account account,String username){
      account=null;
      if(account==null && !account.getPassword().equalsIgnoreCase("password")){
        return null;
      }
    return mediadao.login(account, username);
      
    }
    public Message getAllMsgById(Message message){
      return mediadao.GetMessageById(message.getMessage_id());
    }
    public Message getAllUpdatedMessage(Message message, int message_id){
      if(mediadao.GetMessageById(message_id)!=null){
        return mediadao.GetMessageById(message_id);
      }
       
       return null;
    }
    public Message patchings (Message message, int message_id){
     if( mediadao.GetMessageById(message_id)!=null){
        return mediadao.GetMessageById(message_id);
     }
     return null;
    }
    public Account getAllAccById(Account account){
      return mediadao.GetAccountById(account.getAccount_id());
    }
    
    public Message deleleMsg(int message_id,Message message){
      if(mediadao.GetMessageById(message_id)!=null){
      return mediadao.GetMessageById(message_id);
      //return mediadao.deleteMessage(message_id, message);
      }
      return null;
    }
   public Message deleteNotExistingMsg (int message_id, Message message){
   if(mediadao.GetMessageById(message_id)!=null){
   return mediadao.GetMessageById(message_id);
   }
   return null;
   }
  }
