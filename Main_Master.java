import java.net.*;
import java.io.*;

class Main{
    final int NUM_ARGS = 5;
    //docker run --net=pub_net -d --ip=10.0.0.201 --hostname="test"  --name="remote-desktop" --rm --name="remote-desktop" scottyhardy/docker-remote-desktop
    public static void main(String[] args) {
        // check if length of args array is 
        // greater than 0 
        if (args.length == NUM_ARGS) 
        { 
            System.out.println("The command line arguments are:"); 
  
            Path path = new Path(args[0]);
            String startIp = args[1];
            int hosts =  Integer.parseInt(args[2]);



        } 
        else
        {
            System.out.println("format should be: [server list file path] [starting ip] [number of hosts] [secret token] [port]"); 
            break;
        }


    }
}