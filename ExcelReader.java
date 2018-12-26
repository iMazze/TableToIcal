import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import java.util.ArrayList;

public class ExcelReader {
    public static final String SAMPLE_XLSX_FILE_PATH = "/Users/imazze/Downloads/TableToIcal/table_new.xls";

    public static void main(String[] args) throws IOException, InvalidFormatException {

        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));
        
        // Retrieving the number of sheets in the Workbook
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        /*
        =============================================================
        Iterating over all the sheets in the workbook (Multiple ways)
        =============================================================
         */

        // 2. Or you can use a for-each loop
        System.out.println("Retrieving Sheets using for-each loop");
        for(Sheet sheet: workbook) {
            System.out.println("=> " + sheet.getSheetName());
        }


        /*
        ==================================================================
        Iterating over all the rows and columns in a Sheet (Multiple ways)
        ==================================================================
         */

        // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        // 2. Or you can use a for-each loop to iterate over the rows and columns
        System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
        for (Row row: sheet) {
            for(Cell cell: row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                //System.out.print(cellValue + "\t");
            }
            //System.out.println();
        }

        ArrayList<Vorlesung> events = new ArrayList<Vorlesung>();
        
        for(int c_row = 0; c_row < sheet.getPhysicalNumberOfRows(); c_row++)
        {
            Row row = sheet.getRow(c_row);
            // Datumsspalte
            if(c_row == 2 || c_row == 18 || c_row == 34){
                for(int c_cell = 0; c_cell < row.getPhysicalNumberOfCells(); c_cell++)
                {
                    Cell cell = row.getCell(c_cell);
                    if(cell!=null)
                    {
                        if(cell.getCellType() == CellType.FORMULA || cell.getCellType() == CellType.NUMERIC)
                        {
                            //System.out.println(cell.getDateCellValue() + "\t");
                            events.addAll(getDaysEvents(sheet, c_row+1, c_cell, cell.getDateCellValue()));
                        } 

                    }

                }
            }
            //System.out.println("");
        }

        // Closing the workbook
        workbook.close();
        
        for(Vorlesung v : events)
        {
            System.out.println(v.toString());
        }
    }
    
    static ArrayList<Vorlesung> getFromExcel(String path) throws IOException
    {
        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(path));


        // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);


        ArrayList<Vorlesung> events = new ArrayList<Vorlesung>();
        
        for(int c_row = 0; c_row < sheet.getPhysicalNumberOfRows(); c_row++)
        {
            Row row = sheet.getRow(c_row);
            // Datumsspalte
            if(c_row == 2 || c_row == 18 || c_row == 34){
                for(int c_cell = 0; c_cell < row.getPhysicalNumberOfCells(); c_cell++)
                {
                    Cell cell = row.getCell(c_cell);
                    if(cell!=null)
                    {
                        if(cell.getCellType() == CellType.FORMULA || cell.getCellType() == CellType.NUMERIC)
                        {
                            events.addAll(getDaysEvents(sheet, c_row+1, c_cell, cell.getDateCellValue()));
                        } 

                    }

                }
            }
        }

        // Closing the workbook
        workbook.close();
        
        return events;
    }

    static public boolean isMergedCell(Sheet sheet, int row, int column) {
        for (CellRangeAddress range : sheet.getMergedRegions()) {
            if (range.isInRange(row, column)) {
                return true;
            }
        }
        return false;
    }

    static ArrayList<Vorlesung> getDaysEvents(Sheet s, int c_row, int c_cell, java.util.Date day)
    {
        ArrayList<Vorlesung> events = new ArrayList<Vorlesung>();
        //System.out.print("Events for row/cell " + c_row + "/" + c_cell);
        
        for(int i = 0; i < 12; i++)
        {
            if(i != 6)
            {
                Row row = s.getRow(c_row + i);
                Cell cell = row.getCell(c_cell);
                if(cell!=null)
                {
                    if(cell.getCellType() == CellType.BLANK){
                        if(isMergedCell(s, c_row + i, c_cell))
                        {
                            System.out.println( i + ":" + "MERGED" + "\t");
                            if(events.size() != 0)
                            {
                                events.get(events.size()-1).setEndTime(getTimeLikeNumber(i, true));
                            } else // An diesem Tag sind keine Vorlesungen
                            {
                                break;
                            }
                            
                        }
                        else
                        {
                            //System.out.println( i + ":" + "blank" + "\t");
                        }

                    } else if(cell.getCellType() == CellType._NONE){
                        //System.out.println( i + ":" + "none" + "\t");
                    } else if(cell.getCellType() == CellType.STRING)
                    {
                        System.out.println( i + ":" + cell + "\t");
                        events.add(new Vorlesung(cell + "", day));
                        events.get(events.size()-1).setStartTime(getTimeLikeNumber(i, false));
                    }

                }
            }else
                {
                    if(isInfoBox(s, c_row+7, c_cell))
                    {
                        System.out.println("Infobox");
                        // Dont go further
                        break;
                    }
                }

        }
        return events;
    }

    static boolean isInfoBox(Sheet sheet, int row, int column) {
        for (CellRangeAddress range : sheet.getMergedRegions()) {
            if (range.isInRange(row, column) && range.isInRange(row, column+1)) {
                return true;
            }
        }
        return false;
    }
    static String getTimeLikeNumber(int i, boolean end)
    {
        if(!end)
        {
            switch(i)
            {
                case 0: return "8:00";
                case 1: return "8:45";
                case 2: return "9:45";
                case 3: return "10:30";
                case 4: return "11:30";
                case 5: return "12:15";
                case 7: return "14:00";
                case 8: return "14:45";
                case 9: return "15:45";
                case 10: return "16:30";
                case 11: return "17:30";
                case 12: return "18:15";
                default: return "00:00";
            } 
        }
        else
        {
            switch(i)
            {
                case 0: return "8:45";
                case 1: return "9:30";
                case 2: return "10:30";
                case 3: return "11:15";
                case 4: return "12:15";
                case 5: return "13:00";
                case 7: return "14:45";
                case 8: return "15:30";
                case 9: return "16:30";
                case 10: return "17:15";
                case 11: return "18:15";
                case 12: return "19:00";
                default: return "00:00";
            } 
        }

    }
}