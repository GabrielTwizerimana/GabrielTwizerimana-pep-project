package Service;

import java.util.List;

import org.junit.runners.Parameterized.Parameter;
//import io.javalin.http.Handler;
import DAO.MediaDAO;
import Model.Account;
import Model.Message;
import io.javalin.http.Context;
import net.bytebuddy.asm.Advice.Return;

public class MediaService {
 MediaDAO mediadao;

 public MediaService(){
    this.mediadao=new MediaDAO();
 }
 
 public MediaService(MediaDAO mediadao){
 this.mediadao=mediadao;
 }
 public Account addAccount(Account account){
  if(mediadao.GetAccountById(account.getAccount_id())==null){
     return mediadao.NewAccount(account);
  }
    return null;
 }
 public List<Account> getAllAcc(){
   return mediadao.GetAllAccounts();
 }
 public Message addMessage(Message message){
  //if(message.message_text.length()>255 || message.message_text.isBlank()){
    
  // if(mediadao.GetMessageById(message.message_id)==null){
    return mediadao.NewMessage(message);
  }

 
    public List<Message> getAllMsg(){
     
      return mediadao.GetAllMessages();
    }
   
   /* public List<Message> postAccountAndMessageThenGetMessageService(int message_id){
      return mediadao.postAccountAndMessageThenGetMessage(message_id);
    }*/
  
    public Account loginPass(Account account){
      if(mediadao.GetAccountById(account.getAccount_id())==null){
        return mediadao.login(account);
      }
        
      return null;
      
    }
    public List<Message> GetMessagesByID(){
      return mediadao.GetMessageById();
    }
    
   /* public Message getAllUpdatedMessage(Message message, int message_id){
      if(mediadao.GetMessageById(message_id)!=null){
        return mediadao.GetMessageById(message_id);
      }
       
       return null;
    }*/

    /*public Message patchings (Message message, int message_id){
     if( mediadao.GetMessageById(message_id)!=null){
        return mediadao.GetMessageById(message_id);
     }
     return null;
    }*/
    public Account getAllAccById(Account account){
      return mediadao.GetAccountById(account.getAccount_id());
    }
    
    /*public Message deleleMsg(Message message){
      if(mediadao.GetMessageById(message.getMessage_id())!=null){
        return  mediadao.deleteMessage(message);
      
      }
     return null;
    }*/

  /*  public Message deleteNotExistingMsg (int message_id, Message message){
   if(mediadao.GetMessageById(message_id)!=null){
   return mediadao.GetMessageById(message_id);
   }
   return null;
   }*/
   public List<Message> getAllMsgPosted_By(){
    return mediadao.GetAllMessageByPosted_By();
  }
  }

