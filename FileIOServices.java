import java.io.*;
import java.net.*;
/**
  * <p>Title: CS656 Project</p>
 *
 * <p>Description: These are the services used to transfer
 * files back and forth between the client and server.
 * </p>
 *
 * <p>Copyright: none</p>
 *
 * <p>Company: NJIT Fall 2009</p>
 *
 * @author Bill Phillips (214-36-930)
 * @version 1.0
 */
public class FileIOServices
{

    private String  filename;
    private String  filetype;
    private String  dirstring;
    private boolean isClient;
    private String  pathname;
    private File    sendFile;

    private FileInputStream         fisS;
    private BufferedInputStream     bisS;
    private OutputStream             osS;

    private InputStream              isG;
    private FileOutputStream        fosG;
    private BufferedOutputStream    bosG;


    private final int BUFSIZE = 32768;  // 2 ** 15

    public FileIOServices( final boolean cL )
    {
        if (cL) dirstring = "ClientFiles-";
        else    dirstring = "ServerFiles-";
        try
        {
            dirstring += InetAddress.getLocalHost().getHostName();
        }
        catch (Exception e){}
    }

    public FileIOServices( final String fN,
                           final String fT,
                           final boolean cL)
    {

        filename = fN;
        filetype = fT;
        isClient = cL;

        if (cL) dirstring = "ClientFiles-";
        else    dirstring = "ServerFiles-";
        try
        {
            dirstring += InetAddress.getLocalHost().getHostName();
        }
        catch (Exception e){}

        if (isClient) pathname = filename;
        else          pathname = dirstring +
                                 File.separator +
                                 filetype +
                                 File.separator +
                                 filename;
        sendFile = new File(pathname);

    }

    public final void CleanFiles()
    {
        deleteFiles(new File(dirstring));
    }

    private final boolean deleteFiles( final File dir )
    {
        if (dir.isDirectory())
        {
            final String[] children = dir.list();
            for (int i = 0; i < children.length; ++i)
            {
                final boolean success = deleteFiles(new File(dir, children[i]));
                if (!success) return false;
            }
        }
        return dir.delete();
    }

    public final String[] getFilesInDir()
    {
        return new File(dirstring + File.separator + filetype).list();
    }

    public final String[] getFilesInTDir()
    {
        return new File(dirstring).list();
    }

    public final boolean CreateMainDirectory()
    {
        boolean success = false;
        try
        {
            success = new File(dirstring).mkdir();
        }
        catch (Exception e){ success = false; }
        return success;
    }

    public final boolean DoesMainDirExist()
    {
        return new File(dirstring).exists();
    }


    public final boolean DoesSubdirExist()
    {
        return new File(dirstring + File.separator + filetype).exists();
    }

    public final boolean CreateSubDirectory()
    {
        boolean success = false;

        try
        {
            success = (new File(dirstring, filetype).mkdirs());
        }
        catch (Exception e) {}
        return success;
    }

    //
    // The function to kill the server
    // connection is broken out so the
    // sender can wait for acknowlegment
    // before killing it.  The receiver
    // can kill his immediately after
    // sending the file.
    //
    public final boolean CloseSendStreams()
    {
        boolean status = true;
        try
        {
            osS.close();
            bisS.close();
            fisS.close();
        }
        catch(Exception e)
        {
            status = false;
        }
        return status;
    }

    public final boolean CloseGetStreams()
    {
        boolean status = true;
        try
        {
            bosG.flush();
            fosG.flush();
            bosG.close();
            fosG.close();
            isG.close();
        }
        catch(Exception e)
        {
            status = false;
        }

        return status;

    }

    public final boolean Send( final Socket S )
    {

        boolean status            =              true;
        final byte [] mybytearray = new byte[BUFSIZE];
        try
        {
            fisS     = new FileInputStream(sendFile);
            bisS     = new BufferedInputStream(fisS);
            osS      =     S.getOutputStream();

            long btr       = this.GetLength();
            int  bytesread =                0;
            int  b         =          BUFSIZE;
            do
            {
                bytesread = bisS.read(mybytearray, 0, b);
                osS.write(mybytearray, 0, bytesread);
                btr -= bytesread;
                if (btr < BUFSIZE) b = (int)btr;
            } while (btr > 0);

        }
        catch(Exception e)
        {
            status = false;
        }

        return status;
    }

    public final long    GetLength(){ return sendFile.length(); }

    public final boolean Exists()   { return sendFile.exists(); }

    public final boolean Get( final long filesize, final Socket S)
    {
        boolean status      = true;

        String getpathname = dirstring +
                             File.separator +
                             filetype +
                             File.separator +
                             filename;

        final byte [] mybytearray = new byte[BUFSIZE];
        try
        {

            isG =                  S.getInputStream();
            fosG = new  FileOutputStream(getpathname);
            bosG = new     BufferedOutputStream(fosG);

            long btr       = filesize;
            int bytesread  =        0;
            int  b         =  BUFSIZE;
            do
            {
                bytesread = isG.read(mybytearray, 0,         b);
                bosG.write(mybytearray,           0, bytesread);
                btr -= bytesread;
                if (btr < BUFSIZE) b = (int)btr;
            } while (btr > 0);

            CloseGetStreams();

        }
        catch(Exception e) { status = false; }
        return status;
    }
}
