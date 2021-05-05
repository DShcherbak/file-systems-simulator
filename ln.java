
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
