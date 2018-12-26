import java.util.*;
public class defines
{
    protected static HashMap<String, String> name_hash;
    protected static HashMap<String, String> title_hash;
    
    protected String raum = "N004";
    public void init()
    {
        name_hash = new HashMap<String, String>();
        name_hash.put("Freitag","Hr. Freitag");
        name_hash.put("Hoff","Prof. Hoff");
        name_hash.put("Wark","Herr Warkentin");
        name_hash.put("Sibler","Dr. Sibler");
        name_hash.put("Kibler","Prof. Kibler");
        name_hash.put("Albrecht","Hr. Albrecht");
        name_hash.put("Leicht","Hr. Leicht");
        name_hash.put("Möckel","Hr. Möckel");
        name_hash.put("Herpel","Hr. Herpel");
        name_hash.put("Schmidgal","Hr. Schmidgal");
        name_hash.put("Pinnow","Hr. Pinnow");
        
        title_hash = new HashMap<String, String>();
        title_hash.put("V-motor","Verbrennungsmotoren");
        title_hash.put("RT","Regelungstechnik");
        title_hash.put("Sig Sys","Signale und Systeme");
        title_hash.put("PM","Projektmanagement");
        title_hash.put("Simtec","Simulationstechnik");
        title_hash.put("MC","Microcontroller");
        title_hash.put("MT2","Messtechnik");
    }
}
