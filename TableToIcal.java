import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Write a description of class main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TableToIcal
{
    public static ArrayList<Vorlesung> liste_vorlesungen;

    public static void run(String path){
        //Read liste
        ExcelReader reader = new ExcelReader();

        try
        {
            liste_vorlesungen = reader.getFromExcel(path);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        System.out.println("Found " + liste_vorlesungen.size() + " events");

        iCalendar ical = new iCalendar();
        try
        {
            String calFile;
            calFile = path.replace(path.substring(path.lastIndexOf(".")), "") + ".ics";
            ical.makeIcalFromList(liste_vorlesungen, calFile);

            JOptionPane.showMessageDialog(null, "File created: " + calFile, "Ready2Go", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }
    
    public static void runUpdate(String path){
        //Read liste
        ExcelReader reader = new ExcelReader();

        try
        {
            liste_vorlesungen = reader.getFromExcel(path);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        System.out.println("Found " + liste_vorlesungen.size() + " events");

        iCalendar ical = new iCalendar();
        try
        {
            String calFile;
            calFile = path.replace(path.substring(path.lastIndexOf(".")), "") + ".ics";
            ical.makeIcalFromList(liste_vorlesungen, calFile);

            JOptionPane.showMessageDialog(null, "File created: " + calFile, "Ready2Go", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }

}
