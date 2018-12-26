/* ICalendarExample.java */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.data.*;
import java.util.ArrayList;
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter; 
import java.time.format.FormatStyle; 
import java.time.LocalDate;  

public class iCalendar {

    
    
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void makeIcalFromList(ArrayList<Vorlesung> vorlesungen, String path) throws IOException, ValidationException, java.text.ParseException
    {
        //Creating a new calendar
        net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
        calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        // Getting actual Date for Comment
        LocalDateTime now = LocalDateTime.now(); 
        DateTimeFormatter df; 
        df = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");     // 31.01.2016 20:07
            
        for(Vorlesung v : vorlesungen){
            //Creating an event
            //
            DateTime start = new DateTime(v.getStartDate(), "yyyy-MM-dd HH:mm", false);
            DateTime end = new DateTime(v.getEndDate(), "yyyy-MM-dd HH:mm", false);
            VEvent meeting = new VEvent(start, end, v.getTitle());

            // generate unique identifier..
            UidGenerator ug = new UidGenerator("uidGen");
            //meeting.getProperties().add(ug.generateUid());
            meeting.getProperties().add(new Location(v.getRoom()));
            meeting.getProperties().add(new Description("Updated at " + now.format(df)));
            
            // Add the event and print
            calendar.getComponents().add(meeting);
        }

        //Saving an iCalendar file
        FileOutputStream fout = new FileOutputStream(path);
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.setValidating(false);
        outputter.output(calendar, fout);
    }
    
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void updateIcalFromList(ArrayList<Vorlesung> vorlesungen, String path) throws IOException, ValidationException, java.text.ParseException, net.fortuna.ical4j.data.ParserException
    {
        FileInputStream fin = new FileInputStream(path);

        CalendarBuilder builder = new CalendarBuilder();

        net.fortuna.ical4j.model.Calendar calendar = builder.build(fin);
        
        System.out.println();
    }

}