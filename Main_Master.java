import java.net.*;
import java.util.ArrayList;
import java.io.*;
import java.io.file.Path;

class Main{
    final static int NUM_ARGS = 5;
    public Socket s;
    public InputStream in;
    public OutputStream out;
    //docker run --net=pub_net -d --ip=10.0.0.201 --hostname="test"  --name="remote-desktop" --rm --name="remote-desktop" scottyhardy/docker-remote-desktop
    public static void main(String[] args) {
        // check if length of args array is 
        // greater than 0 
        try{
            if (args.length == NUM_ARGS) 
            { 
                System.out.println("The command line arguments are:"); 
                
                //get path
                File f;
                int containers;
                int port;
                try {
                    f = new File(args[0]);
                }catch(Exception e){
                    System.out.println("invalid path");
                    throw new Error();
                }
                
                //starting ip
                String startIp = args[1];
                
                try {
                    containers =  Integer.parseInt(args[2]);
                }catch(Exception e){
                    System.out.println("the number of containers needs to be in numerical form");
                    throw new Error();
                }
                
                
                
                //secret token
                String secret = args[3];
                
                //port for server
                try {
                    port = Integer.parseInt(args[4]);
                    if(port <= 1023) {
                        System.out.println("you are not allowed to use a port that is between 0-1023");
                        throw new Error();
                    }else if (port > 65535) {
                        System.out.println("you cannot use a port that doesn't exist!");
                        throw new Error();
                    }
                }catch(Exception e){
                    System.out.println("the port needs to be in numerical form");
                    throw new Error();
                }
                
                ArrayList<String> hosts = new ArrayList<>();



            } 
            else
            {
                System.out.println("format should be: [server list file path] [starting ip] [number of containers] [secret token] [port]"); 
                throw new Error();
            }
        }finally{
            //dop nothing
        }


    }
}