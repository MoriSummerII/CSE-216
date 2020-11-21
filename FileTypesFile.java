import java.util.*;
import java.io.*;
/* <p>Title: CS656 Project</p>
 *
 * <p>Description: This class retrieves file type data
 .                 stores it and makes it available.
 * </p>
 *
 * <p>Copyright: none</p>
 *
 * <p>Company: NJIT Fall 2009</p>
 *
 * @author Bill Phillips (214-36-930)
 * @version 1.0
 */
public class FileTypesFile
{
    private static FileTypesFile ft     =          null;
    private final  String FILETYPESFILE = "files.types";
    //
    // The Singleton Design Pattern.
    //
    public static FileTypesFile Instance()
    {
        if ( ft == null ) ft = new FileTypesFile();
        return ft;
    }

    private FileTypesFile()
    {
        //
        // Read the config file.
        //
        if (this.readFileTypesFile(FILETYPESFILE) == 0)
        {
            System.out.println
                ("\nProblem reading file types file: " +
                 FILETYPESFILE +
                 "*****");
            System.exit( -1);
        }
    }

    class FileTypeRecord
    {
        private String filetype;
        private int          id;

        public FileTypeRecord(String FT, int ID)
        {
            filetype  = FT;
            id        = ID;
        }
        public String getFT() { return filetype; }
        public int    getId()        { return id; }

    }

    private final ArrayList
            <FileTypeRecord> fList = new ArrayList<FileTypeRecord>();

    //
    // Load all read file types into the
    // arraylist.
    //
    public final void LoadAllFileTypes(ArrayList <String> AL)
    {
        for ( FileTypeRecord f : fList )  AL.add(f.getFT());
    }

    //
    // Read all file type records from file
    // fileName into the internal store.
    //
    private final int readFileTypesFile( final String fileName )
    {
        Scanner input = null;
        try
        {
            input = new Scanner(new File(fileName));
            while (input.hasNext())
                fList.add(new FileTypeRecord(input.next(),input.nextInt()));
        }
        catch (Exception e) {                    fList.clear(); }
        finally             { if (input != null) input.close(); }
        return fList.size();
    }

    synchronized public final int getID( final String FT )
    {
        for( FileTypeRecord f : fList )
            if (f.getFT().equalsIgnoreCase(FT)) {return f.getId();}

        return -1;
    }



}
