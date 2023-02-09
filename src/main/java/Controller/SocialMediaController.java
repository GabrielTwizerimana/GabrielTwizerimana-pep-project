package Controller;
import java.security.Provider.Service;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import DAO.MediaDAO;
import Model.Account;
import Model.Message;
import Service.MediaService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.security.Provider.Service;
import io.javalin.http.Handler;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController{
    MediaService mediaservice;
    public SocialMediaController(){
        this.mediaservice=new MediaService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
       app.post("/register",this::postAccountHandler);
       app.post("/login",this::postRegisterThenPostLogin);
       app.post("/messages",this::postMessageHandlerAdd);
       app.get("/messages",this::getAllMessageHandler);
      // app.patch("/messages/{message_id}", this::postMessageHandlerAdd);
       app.get("/accounts/{account_id}/messages",this::getMessagesByAccountIdEmpty);
       app.get("messages/{message_id}",this::GetAllMessagesById);
       app.delete("/messages/{message_id}",this::deleteMessage);
       app.patch("/messages/{message_id}", this::updateMessageHandler);
        //app.delete("/messages/1",this::deletingExistingMessage);
        //app.post("/accounts/messages",this::getAllAccountHandler);
        //app.get("/accounts/{account_id}/messages",this::GetAllMessagesByUserHandler);
        //app.get("/messages/1",this::postAccountAndMessageThenGetMessageHandle);
        //app.patch("/messages", this::updateMessageHandler);
        
        
        
        return app;
    }
   
    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper=new ObjectMapper();
        Account account=mapper.readValue(context.body(),Account.class);
        Account addedAccount=mediaservice.addAccount(account);
        if(addedAccount.username.isEmpty() || addedAccount.equals(account) ||
         addedAccount.username.isBlank()|| addedAccount.password.length()<4){
            context.status(400);   
        }
      
        else{
            
        context.json(mapper.writeValueAsString(addedAccount));
    }
    }
    private void postAccountAndMessageThenGetMessageHandle(Context context) throws JsonProcessingException{
        ObjectMapper mapper=new ObjectMapper();
        Message message=mapper.readValue(context.body(),Message.class);
        Message addedMessage=mediaservice.addMessage(message);
        if(addedMessage==null){
            context.status(400);
        }
        else{
        context.json(mapper.writeValueAsString(addedMessage));
        }
    }
    
    public void postRegisterThenPostLogin(Context ctx)throws JsonProcessingException{
        ObjectMapper mapper=new ObjectMapper();
        Account account=mapper.readValue(ctx.body(), Account.class);
        Account postlogins=mediaservice.loginPass(account);
        if(postlogins!=null) {
            ctx.json(mapper.writeValueAsString(postlogins));         
        }else{
          
            ctx.status(401);
        }
        }
        private void updateMessageHandler(Context ctx) throws JsonProcessingException {
            ObjectMapper mapper=new ObjectMapper();
        Message message=mapper.readValue(ctx.body(),Message.class);
       int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage= mediaservice.patchings( message_id,message);
        if(updatedMessage == null || updatedMessage.message_text.isBlank() ){
            ctx.status(400);
         }else{
           
            ctx.json(updatedMessage);
            }
            }
           
    private void postMessageHandlerAdd(Context context) throws JsonProcessingException{
        ObjectMapper mapper=new ObjectMapper();
        Message message=mapper.readValue(context.body(),Message.class);
        Message addedMessage=mediaservice.addMessage(message);
        if(addedMessage.posted_by==0 ||addedMessage.message_text.length()>255
         || addedMessage.message_text.isBlank() || addedMessage.message_id==0){
            context.status(400);
         }
         
        else{
        context.json(mapper.writeValueAsString(addedMessage));
        }
    }
    
   
    private void getAllMessageHandler(Context context) {
        List<Message> message=mediaservice.getAllMsg();
        context.json(message);
    }
    
    private void getAllAccountHandler(Context context) {
        List<Account> account=mediaservice.getAllAcc();
        List<Message> message=mediaservice.getAllMsg();
        if(account!=null && message!=null){
            context.json(message);
            context.json(account);
        }else{
        context.status(400);
        }
        
    }
    private void getMessagesByAccountIdEmpty(Context context) {
        String id=context.pathParam("account_id");
        int accid=Integer.parseInt(id);
        List<Message> message=mediaservice.getAllMsgPosted_By(accid);
        context.json(message);
        
    }
 /**
 * @param ctx
 */
private void GetAllMessagesById(Context ctx) throws JsonProcessingException{
    //Object mapper=new ObjectMapper();
    //Message message= ((ObjectMapper) mapper).readValue(ctx.body(),Message.class);
    int message_id=Integer.parseInt(ctx.pathParam("message_id"));
    Message retreivedmessage=mediaservice.GetMessagesByID(message_id);
    if(retreivedmessage!=null){
    ctx.json(retreivedmessage);
      }
    }

/*private void GetAllMessagesByUserHandler(Context ctx) throws JsonProcessingException{
    //Object mapper=new ObjectMapper();
    //Message message= ((ObjectMapper) mapper).readValue(ctx.body(),Message.class);
    int posted_by=Integer.parseInt(ctx.pathParam("posted_by"));
    Message messageuser=mediaservice.GetMessagesByUSER(posted_by);
    if(messageuser!=null){
    ctx.json(messageuser);
      }
}*/
/*
private void GetAllMessagesByUserHandler(Context ctx){
    List<Message> messages=mediaservice.getAllMsgPosted_By();
    ctx.json(messages);
}*/


private void deleteMessage(Context ctx) throws JsonProcessingException{
    //Object mapper=new ObjectMapper();
    //Message message= ((ObjectMapper) mapper).readValue(ctx.body(),Message.class);
    int message_id=Integer.parseInt(ctx.pathParam("message_id"));
    Message deletedmessage=mediaservice.deleleMsg(message_id);
    if(deletedmessage!=null){
        ctx.json(deletedmessage);
      }
      ctx.status(200);
}
private void deletingExistingMessage(Context context) throws JsonProcessingException{
    ObjectMapper mapper=new ObjectMapper();
    Message message=mapper.readValue(context.body(),Message.class);
    Message addedMessage=mediaservice.deletingExistingMessage(message);
    if(addedMessage!=null){
        
        context.json(mapper.writeValueAsString(addedMessage));
     }
     
    else{
        context.status(400);
    }
    
}}
    

