import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Write a description of class Vorlesung here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Vorlesung extends defines
{
    private String titel;
    private java.util.Date datum;
    private String anfang = "00:00";
    private String ende = "00:00";
    
    public Vorlesung()
    {
        init();
    }

    public Vorlesung(String pTitel, String pDatum)
    {
        titel = pTitel;
        //datum = pDatum;
        init();
    }

    public Vorlesung(String pTitel, java.util.Date pDatum)
    {
        titel = pTitel;
        datum = pDatum;
        init();
    }
    
    public String getRoom()
    {
        return raum;
    }

    public void setEndTime(String time)
    {
        ende = time;
    }

    public void setStartTime(String time)
    {
        anfang = time;
    }

    public String getTitle()
    {
        return replaceTitle(titel);
    }

    public String getStartDate()
    {
        String start = getYear() + "-" + getMonth() + "-" + getDayOfMonth() + " " + anfang;
        return start;
    }

    public String getEndDate()
    {
        String end = getYear() + "-" + getMonth() + "-" + getDayOfMonth() + " " + ende;
        return end;
    }

    private String getDayOfMonth()
    {
        return datum.getDate() + "";
    }

    private int getYear()
    {
        return 2019;
    }

    private int getMonth()
    {
        return datum.getMonth() + 1;
    }

    public String toString()
    {
        return getTitle() + ": " + getStartDate() + " - " + getEndDate();
    }

    private String replaceTitle(String title)
    {   
        // Replace Name
        Iterator<Map.Entry<String, String>> it = name_hash.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            
            if(title.contains((String)entry.getKey()))
            {
                title = title.replace((String)entry.getKey(), "[" + entry.getValue() + "]");
                break;
            }
            
        } 
        
        // Replace Title
        Iterator<Map.Entry<String, String>> it2 = title_hash.entrySet().iterator();

        while (it2.hasNext()) {
            Map.Entry entry = (Map.Entry)it2.next();
            
            if(title.contains((String)entry.getKey()))
            {
                title = title.replace((String)entry.getKey(), (String)entry.getValue());
                break;
            }
            
        } 


        // Replace Raum
        Pattern p = Pattern.compile("[HNE]\\d\\d\\d");   // the pattern to search for
        Matcher m = p.matcher(title);
        
        // now try to find at least one match
        while (m.find()) {
            // Replace Room
            titel = titel.replace(" " + m.group(), "");
            raum = m.group();
        }   
    
        return title;
    }
}
