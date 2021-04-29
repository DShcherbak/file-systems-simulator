<<<<<<< HEAD:find.java
=======
import java.util.ArrayList;
>>>>>>> 02942c5135df22526f5eac364f70e5bd9837380c:src/find.java
/**
 * @author Ivan Ramyk
 */
public class find
{
    /**
     * The name of this program.
     * This is the program name that is used
     * when displaying error messages.
     */
    public static String PROGRAM_NAME = "find" ;

    /**
     * Lists that path name and all files in all directories.
     * @exception java.lang.Exception if an exception is thrown
     * by an underlying operation
     */

    private static void printAllInPathRec(String path) throws Exception {
        int fd = Kernel.open( path , Kernel.O_RDONLY ) ;
        if( fd < 0 )
        {
            return;
        }

        // print a heading for this directory
        System.out.println(path) ;

        // create a directory entry structure to hold data as we read
        DirectoryEntry directoryEntry = new DirectoryEntry() ;

        // while we can read, print the information on each entry
        while( true )
        {
            // read an entry; quit loop if error or nothing read

            int status = Kernel.readdir( fd , directoryEntry ) ;
            if( status <= 0 )
                break ;

            // get the name from the entry
            String entryName = directoryEntry.getName() ;

            // call stat() to get info about the file
            Stat stat = new Stat();
            status = Kernel.stat( path + "/" + entryName , stat ) ;
            if( status < 0 )
            {
                Kernel.perror( PROGRAM_NAME ) ;
                Kernel.exit( 1 ) ;
            }
            short type = (short)( stat.getMode() & Kernel.S_IFMT ) ;

            if( type == Kernel.S_IFDIR )
            {
                if (!entryName.equals(".") && !entryName.equals("..")) {
                    if (path.charAt(path.length() - 1) == '/')
                        printAllInPathRec(path  + entryName);
                    else
                        printAllInPathRec(path + '/' + entryName);
                }
            } else {
                if (path.charAt(path.length() - 1) == '/')
                    System.out.println(path + entryName);
                else
                    System.out.println(path + "/" + entryName);
            }
        }
        Kernel.close( fd ) ;
    }

    private static void printAllInPathQueue(String rootPath) throws Exception {

        ArrayList<String> queue = new ArrayList<>();
        queue.add(rootPath);
        int currentPathIndex = 0;
        while (queue.size() > currentPathIndex) {
            String path = queue.get(currentPathIndex);
            currentPathIndex++;
            //check if file is a dir
            int status = 0 ;
            // stat the name to get information about the file or directory
            Stat stat = new Stat() ;
            status = Kernel.stat( path , stat ) ;
            if( status < 0 )
            {
                Kernel.perror( PROGRAM_NAME ) ;
                Kernel.exit( 1 ) ;
            }

            // mask the file type from the mode
            short type = (short)( stat.getMode() & Kernel.S_IFMT ) ;

            // if name is a regular file, just print path
            if (type != Kernel.S_IFDIR)
            {
                System.out.println(path) ;
                continue;
            }

            int fd = Kernel.open( path , Kernel.O_RDONLY ) ;
            if( fd < 0 )
            {
                return;
            }

            System.out.println(path) ;

            // create a directory entry structure to hold data as we read
            DirectoryEntry directoryEntry = new DirectoryEntry() ;

            // while we can read, print the information on each entry
            while( true )
            {
                // read an entry; quit loop if error or nothing read
                status = Kernel.readdir( fd , directoryEntry ) ;
                if( status <= 0 )
                    break ;
                // get the name from the entry
                String entryName = directoryEntry.getName() ;
                if (!entryName.equals(".") && !entryName.equals("..")) {
                    if (path.charAt(path.length() - 1) == '/')
                        queue.add(path  + entryName);
                    else
                        queue.add(path + '/' + entryName);
                }
            }
            Kernel.close( fd ) ;
        }
    }


    /**
     * Checks if the path exists, and lists that path name and all files in all directories.
     * @exception java.lang.Exception if an exception is thrown
     * by an underlying operation
     */

    public static void main( String[] args ) throws Exception
    {
        // initialize the file system simulator kernel
        Kernel.initialize() ;

        boolean isRec = false;

        // for each path-name given
        for( int i = 0 ; i < args.length ; i ++ )
        {
            String name = args[i];
            if (args[i].equals("-r")) {
                isRec = true;
                continue;
            }
            int status = 0 ;

            // stat the name to get information about the file or directory
            Stat stat = new Stat() ;
            status = Kernel.stat( name , stat ) ;
            if( status < 0 )
            {
                Kernel.perror( PROGRAM_NAME ) ;
                Kernel.exit( 1 ) ;
            }

            // mask the file type from the mode
            short type = (short)( stat.getMode() & Kernel.S_IFMT ) ;

            // if name is a regular file, print the info
            if( type == Kernel.S_IFREG )
            {
                System.out.println(name) ;
            }

            // if name is a directory open it and read the contents
            else if( type == Kernel.S_IFDIR )
            {
                if (isRec)
                    printAllInPathRec(name);
                else
                    printAllInPathQueue(name);
            }
        }

        // exit with success if we process all the arguments
        Kernel.exit( 0 ) ;
    }
}
