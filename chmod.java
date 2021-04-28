/**
 * @author Denys Shcherbak
 */
public class chmod
{
    /**
     * The name of this program.
     * This is the program name that is used
     * when displaying error messages.
     */
    public static String PROGRAM_NAME = "chmod" ;


    /**
     * Checks if the path exists, and tries to change it's owner
     * @exception java.lang.Exception if an exception is thrown
     * by an underlying operation
     */

    public static void main( String[] args ) throws Exception
    {
        // initialize the file system simulator kernel
        Kernel.initialize() ;

        if(args.length < 2){
            Kernel.perror( PROGRAM_NAME ) ;
            Kernel.exit( 1 );
        }
        short mode = Short.parseShort(args[0]);
        for(int i = 1; i < args.length; i++){
            String name = args[i] ;
            int status = 0 ;

            // stat the name to get information about the file or directory
            Stat stat = new Stat() ;
            status = Kernel.stat( name , stat ) ;
            if( status == -1 )
            {
                Kernel.perror( PROGRAM_NAME ) ;
                Kernel.exit( 1 );
            }
            status = Kernel.chmod(name, mode);
            if(status == -1)
            {
                System.err.println( PROGRAM_NAME +  ": You don't have rights to perform this operation!" ) ;
                Kernel.exit( 1 );
            } else if (status == -2){
                System.err.println( PROGRAM_NAME +  ": Incorrect file" ) ;
                Kernel.exit( 1 );
            } else if (status == -3){
                System.err.println( PROGRAM_NAME +  ": Incorrect mode parameter '" + mode  + "'") ;
                Kernel.exit( 1 );
            }
        }
        // exit with success if we process all the arguments
        Kernel.exit( 0 ) ;
    }
}
