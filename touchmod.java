
/**
 * Reads standard input and writes to standard output
 * and the named file.
 * A simple tee program for a simulated file system.
 * <p>
 * Usage:
 * <pre>
 *   java tee <i>output-file</i>
 * </pre>
 * @author Ray Ontko
 */
public class touchmod
{
    /**
     * The name of this program.
     * This is the program name that is used
     * when displaying error messages.
     */
    public static final String PROGRAM_NAME = "touchmod" ;

    /**
     * Copies standard input to standard output and to a file.
     * @exception java.lang.Exception if an exception is thrown
     * by an underlying operation
     */
    public static void main( String[] argv ) throws Exception
    {
        // initialize the file system simulator kernel
        Kernel.initialize() ;

        String modeBuffer = System.getProperty( "mode" ) ;
        short mode = 0666;

        if ( modeBuffer == null )
            modeBuffer = "0666" ;
        try
        {
            mode = Short.parseShort(modeBuffer, 8) ;
        }
        catch ( NumberFormatException e )
        {
            System.err.println( PROGRAM_NAME +
                    ": invalid number for property mode" ) ;
            System.exit( 1 ) ;
        }


        // print a helpful message if the number of arguments is not correct
        if( argv.length != 1 )
        {
            System.err.println( PROGRAM_NAME + ": usage: java " + PROGRAM_NAME +
                    " output-file" ) ;
            Kernel.exit( 1 ) ;
        }

        // give the command line argument a better name
        String name = argv[0] ;

        // create the output file
        int out_fd = Kernel.creat( name , mode ) ;
        if( out_fd < 0 )
        {
            Kernel.perror( PROGRAM_NAME ) ;
            System.err.println( PROGRAM_NAME + ": unable to open output file \"" +
                    name + "\"" ) ;
            Kernel.exit( 2 ) ;
        }

        // close the output file
        Kernel.close( out_fd ) ;

        // exit with success
        Kernel.exit( 0 ) ;
    }

}
