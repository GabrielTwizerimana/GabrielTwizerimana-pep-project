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
        app.post("/messages/1",this::postAccountAndMessageThenGetMessageHandler);
        app.delete("/messages/{message_id}",this::postDeletedMessagesHandler);
        app.get("/accounts/messages",this::getAllAccountHandler);
        app.get("/accounts/1/messages",this::getMessagesByAccountIdEmpty);
        app.get("/users/1/messages",this::postManyAccountsAndMessagesThenGetMessagesByAccountIdHandler);
        app.patch("/messages", this::updateMessageHandler);
        app.patch("/messages/{message_id}", this::postMessageHandlerAdd);
        //app.delete("/messages/{message_id}", this::messageDeleteNonExistance1);

        
        
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
    private void postManyAccountsAndMessagesThenGetMessagesByAccountIdHandler(Context ctx) throws JsonProcessingException{
            ctx.json(mediaservice.postManyAccountsAndMessagesThenGetMessagesByAccountIdService());
             }
    

    
    public void postRegisterThenPostLogin(Context ctx)throws JsonProcessingException{
        ObjectMapper mapper=new ObjectMapper();
        Account account=mapper.readValue(ctx.body(), Account.class);
        Account postlogins=mediaservice.loginPass(account, null);
        if(postlogins==null){
            ctx.status(401); 
        }else{
          
            ctx.json(mapper.writeValueAsString(postlogins));
        }
        }
        private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
       int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage= mediaservice.patchings(message, message_id);
         System.out.println(updatedMessage);
        if(updatedMessage == null || updatedMessage.message_text.length()>255 || updatedMessage.message_text.isBlank()){
        ctx.status(400);
         }else{
            ctx.json(mapper.writeValueAsString(updatedMessage));
            }
            }
            private void messageDeleteNonExistance1(Context ctx) throws JsonProcessingException {
                ObjectMapper mapper = new ObjectMapper();
                Message message = mapper.readValue(ctx.body(), Message.class);
               int message_id= Integer.parseInt(ctx.pathParam("message_id"));
                Message deletedMessage= mediaservice.deleteNotExistingMsg(message_id, message);
                 System.out.println(deletedMessage);
                if(deletedMessage == null || deletedMessage.message_text.length()>255 || deletedMessage.message_text.isEmpty()){
                ctx.status(400);
                 }else{
                    ctx.json(mapper.writeValueAsString(deletedMessage));
                    }
                    }
            private void patchings(Context ctx) throws JsonProcessingException {
                ObjectMapper mapper = new ObjectMapper();
                Message message = mapper.readValue(ctx.body(), Message.class);
               int message_id = Integer.parseInt(ctx.pathParam("message_id"));
                Message updatedMessage= mediaservice.patching(message, message_id);
                 System.out.println(updatedMessage);
                if(updatedMessage == null || updatedMessage.posted_by==0 || updatedMessage.message_id==0){
                ctx.status(400);
                 }else{
                    ctx.json(mapper.writeValueAsString(updatedMessage));
                    }
                }
        
public void postDeletedMessagesHandler(Context ctx)throws JsonProcessingException{
    ObjectMapper mapper=new ObjectMapper();
    Message message=mapper.readValue(ctx.body(), Message.class);
    int message_id=Integer.parseInt(ctx.pathParam("message_id"));
    Message DeletedMsg_id=mediaservice.deleleMsg(message_id,message);

    if(DeletedMsg_id==null){
        ctx.status(400);
    }
   else{ 
    ctx.json(mapper.writeValueAsString(DeletedMsg_id));
    
    
    }}

    private void postMessageHandlerAdd(Context context) throws JsonProcessingException{
        ObjectMapper mapper=new ObjectMapper();
        Message message=mapper.readValue(context.body(),Message.class);
        Message addedMessage=mediaservice.addMessage(message);
        if(addedMessage==null || addedMessage.posted_by==0 ||addedMessage.message_text.length()>255 ||
         addedMessage.message_text.isBlank() || addedMessage.message_id==0){
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
    private void postAccountAndMessageThenGetMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        Message message=mapper.readValue(context.body(),Message.class);
        Message addedMessage=mediaservice.addMessage(message);
        if(addedMessage==null || addedMessage.message_text.length()>255){
            context.status(400);
        }
        else{
        context.json(mapper.writeValueAsString(addedMessage));
        }
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
        List<Message> message=mediaservice.getAllMsg();
        List<Account> account=mediaservice.getAllAcc();
        if(account==null || message==null)
        context.json(message);
        context.json(account);
    }
 
}
    
    
    
    