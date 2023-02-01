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
        app.get("/login",this::postAccountHandler);
        //app.post("/login",this::postRegisterThenPostLoginTest);
        app.post("/login",this::postLoginEmpty);
        app.post("/messages",this::postMessageHandlerAdd);
        app.get("/messages",this::getMessageHandlerById);
        app.get("/messages",this::getAllMessageHandler);
        
        
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
        if(addedAccount.username.isEmpty() || addedAccount.equals(account)|| addedAccount.password.length()<4){
          
            context.status(400);
            
        }
      
        else{
            
        context.json(mapper.writeValueAsString(addedAccount));
    }

    }
    private void postRegisterThenPostLoginTest(Context context) throws JsonProcessingException{
        ObjectMapper mapper=new ObjectMapper();
        Account account=mapper.readValue(context.body(),Account.class);
        Account addedAccount=mediaservice.addAccount(account);
        if(addedAccount.username.isEmpty() || addedAccount.equals(account)|| addedAccount.password.length()<4){
          
            context.status(200);
            
        }
      
        else{
        context.json(mapper.writeValueAsString(addedAccount));
    }

    }
    public void postLoginEmpty(Context ctx)throws JsonProcessingException{
    ObjectMapper mapper=new ObjectMapper();
    Account account=mapper.readValue(ctx.body(), Account.class);
    Account logins=mediaservice.getAllAccById(account);
    Account logins1=mediaservice.addAccount(account);
    if(logins==null){
        ctx.status(401);
    }
    else if(logins1.username==account.username && logins1.password==account.password){
    ctx.status(200);
    }else{
        
        ctx.json(mapper.writeValueAsString(logins));
        ctx.json(mapper.writeValueAsString(logins1));
        
    }
    }
    
    public void postRegisterThenPostLogin(Context ctx)throws JsonProcessingException{
        ObjectMapper mapper=new ObjectMapper();
        Account account=mapper.readValue(ctx.body(), Account.class);
        Account postlogins=mediaservice.addAccount(account);
        if(postlogins!=null){
            ctx.status(200);
        }else{
            ctx.json(mapper.writeValueAsString(postlogins));
        }
        }

    private void postMessageHandlerAdd(Context context) throws JsonProcessingException{
        ObjectMapper mapper=new ObjectMapper();
        Message message=mapper.readValue(context.body(),Message.class);
        Message addedMessage=mediaservice.addMessage(message);
        if(addedMessage==null){
            context.status(400);
        }else{
        context.json(mapper.writeValueAsString(addedMessage));
        }
    }
    
    private void getMessageHandlerById(Context context) throws JsonProcessingException{
        ObjectMapper mapper=new ObjectMapper();
        Message message=mapper.readValue(context.body(),Message.class);
        Message messagebyid=mediaservice.getAllMsgById(message);
        if(messagebyid==null){

            context.status(200);
        }
        else{
        context.json(mapper.writeValueAsString(messagebyid));
    }}
    private void getAllMessageHandler(Context context) {
        List<Message> message=mediaservice.getAllMsg();
        context.json(message);
    }}
    
    
    
    