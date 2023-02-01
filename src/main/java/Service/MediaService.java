package Service;

import org.junit.runners.Parameterized.Parameter;

import DAO.MediaDAO;

public class MediaService {
 MediaDAO mediadao;

 public MediaService(){
    this.mediadao=new MediaDAO();
 }
 
 public MediaService(MediaDAO mediadao){
 this.mediadao=mediadao;
 }
 
}
