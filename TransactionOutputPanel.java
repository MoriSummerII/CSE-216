import javax.swing.*;
/**
 * <p>Title: CS656 Project</p>
 *
 * <p>Description: TransactionOutputPanel is 
 * where the output messages
 * are displayed.
 * </p>
 *
 * <p>Copyright: none</p>
 *
 * <p>Company: Lehigh University</p>
 *
 * @author Bill Phillips 
 *
 */
public class TransactionOutputPanel extends JFrame
{

    private int     rows        =           20;
    private int     columns     =           80;
    private boolean emptystring =         true;
    private String  S           = new String();

    private Box box;
    private JTextArea textarea;

    public void setText( String s )
    {
        if ( emptystring )  S  =  s;   
        else                S  =  S + '\n' + s;
        emptystring = false;
        textarea.setText( S );
        repaint();
    }

    public TransactionOutputPanel
                           (String S, int r, int c)
    {
        super(S);
        rows    = r;
        columns = c;
        box = Box.createHorizontalBox();
        textarea = new JTextArea( rows, columns );
        textarea.setEditable( false );
        box.add( new JScrollPane( textarea ) );
        add( box );
    }

}
