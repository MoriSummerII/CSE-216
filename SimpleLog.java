import java.io.*;
import java.text.*;
import java.util.*;
/**
 * <p>Title: Simple Log</p>
 *
 * <p>Description:
 *  Simple logger found at 
 *      http://www.rgagnon.com/javadetails/
 *                                  java-0063.html
 * </p>
 *
 * <p>Copyright: none</p>
 *
 * <p>Company: Real's Java How-to...</p>
 *
 * @author R Gagnon
 * @version 1.0
 */

/**
 * Utilities log
 */
public class SimpleLog
{

    private final static DateFormat 
            df = new SimpleDateFormat
                        ("yyyy.MM.dd  hh:mm:ss ");

    synchronized public static void write
            ( final String file, final String msg)
    {
        try
        {
            Date now = new Date();
            String currentTime = 
                         SimpleLog.df.format(now);
            FileWriter aWriter = 
                       new FileWriter(file, true);
            aWriter.write(currentTime + " " + msg
                    + System.getProperty
                              ("line.separator"));
            aWriter.flush();
            aWriter.close();
        }
        catch (Exception e)
        {
            System.out.println
                  ("Could not write to log file"); 
            System.exit(-1);
        }
    }

}
