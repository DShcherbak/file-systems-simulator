/*
 * $Id: ls.java,v 1.6 2001/10/12 02:14:31 rayo Exp $
 */

/*
 * $Log: ls.java,v $
 * Revision 1.6  2001/10/12 02:14:31  rayo
 * better formatting
 *
 * Revision 1.5  2001/10/07 23:48:55  rayo
 * added author javadoc tag
 *
 */

/**
 * A simple directory listing program for a simulated file system.
 * <p>
 * Usage:
 * <pre>
 *   java ls <i>path-name</i> ...
 * </pre>
 * @author Ray Ontko
 */
public class ln
{
    /**
     * The name of this program.
     * This is the program name that is used
     * when displaying error messages.
     */
    public static String PROGRAM_NAME = "ln" ;

    /**
     * Lists information about named files or directories.
     * @exception java.lang.Exception if an exception is thrown
     * by an underlying operation
     */
    public static void main( String[] args ) throws Exception
    {

        if( args.length < 2 )
        {
            System.err.println( PROGRAM_NAME + ": too few arguments" ) ;
            Kernel.exit( 1 ) ;
        }

        // initialize the file system simulator kernel
        Kernel.initialize();
        int status = Kernel.link(args[0], args[1]);
        if( status < 0 )
        {
            Kernel.exit( 2 ) ;
        }
        Kernel.exit( 0 ) ;
    }
}
