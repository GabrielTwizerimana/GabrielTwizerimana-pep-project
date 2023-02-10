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
 
 public Message addMessage(Message message){

    return mediadao.NewMessage(message);

  }
 
    public List<Message> getAllMsg(){
     
      return mediadao.GetAllMessages();
    }
   
  
    public Account loginPass(Account account){
      if(mediadao.GetAccountById(account.getAccount_id())==null){
        return mediadao.login(account);
      }
        
      return null;
      
    }
    public Message GetMessagesByID(int id){
  
      if(mediadao.GetMessageById(id)!=null){
        return mediadao.GetMessageById(id);
      }
      return null;
      }

      
    public Message patchings (int message_id,Message message){
     if( mediadao.GetMessageById(message_id)!=null){
        return mediadao.patchingMessage(message_id,message);
     }
     return null;
    }
   
    
    public Message deleleMsg(int message_id){
      
      Message message=mediadao.GetMessageById(message_id);
      mediadao.deleteMessage(message_id);
      if(message==null){
        return null;
       }
      return  message;
    }

   
   public List<Message> getAllMsgPosted_By(int id){
    return mediadao.GetAllMessagesByAccountID(id);
  }
  
  }

