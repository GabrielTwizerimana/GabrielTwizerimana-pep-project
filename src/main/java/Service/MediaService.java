package Service;

import java.util.List;

import org.junit.runners.Parameterized.Parameter;
//import io.javalin.http.Handler;
import DAO.MediaDAO;
import Model.Account;
import Model.Message;

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
   return mediadao.NewMessage(message);
    }
    public List<Message> getAllMsg(){
      return mediadao.GetAllMessages();
    }
    public Message getAllMsgById(Message message){
      return mediadao.GetMessageById(message.getMessage_id());
    }
    public Message getAllMsgByMsgText(Message message){
      if(mediadao.GetMessageByMsgposted(message.getMessage_text()==null)){
       return mediadao.NewMessage(message);
      }
      return null;
    }
    public Account getAllAccById(Account account){
      return mediadao.GetAccountById(account.getAccount_id());
    }
    
    public Message deleleMsg(int msgid,Message message){
   return mediadao.deleteMessage(msgid);
    }
   
}
