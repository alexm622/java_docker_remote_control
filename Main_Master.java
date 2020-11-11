import java.net.*;
import java.io.*;

class Main{
    final int NUM_ARGS = 5;
    public ServerSocker ss;
    public Socket s;
    public InputStream in;
    //docker run --net=pub_net -d --ip=10.0.0.201 --hostname="test"  --name="remote-desktop" --rm --name="remote-desktop" scottyhardy/docker-remote-desktop
    public static void main(String[] args) {
        // check if length of args array is 
        // greater than 0 
        if (args.length == NUM_ARGS) 
        { 
            System.out.println("The command line arguments are:"); 
            
            //get path
            Path path = new Path(args[0]);
            
            //starting ip
            String startIp = args[1];
            
            try {
            	int hosts =  Integer.parseInt(args[2]);
            }catch(Exception e){
            	System.out.println("the number of hosts needs to be in numerical form");
            	break;
            }
            
            
            //secret token
            String secret = args[3];
            
            //port for server
            try {
	            int port = Integer.parseInt(args[4]);
	            if(port <= 1023) {
	            	System.out.println("you are not allowed to use a port that is between 0-1023");
	            	throw new Error();
	            }else if (port > 65535) {
	            	System.out.println("you cannot use a port that doesn't exist!");
				}
	        }catch(Exception e){
	        	System.out.println("the port needs to be in numerical form");
	        	break;
	        }
	        
            ss = new ServerSocket(port);
            



        } 
        else
        {
            System.out.println("format should be: [server list file path] [starting ip] [number of hosts] [secret token] [port]"); 
            break;
        }


    }
}