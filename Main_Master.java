import java.net.*;
import java.util.ArrayList;
import java.io.*;

class Master{
    final static int NUM_ARGS = 5;
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
                try{
                    ArrayList<String> hosts = readFromFile(f);
                }catch(Exception e){
                    System.out.println("something is wrong with the file");
                    throw new Error();
                }
                



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

    private static ArrayList<String> readFromFile(File f) throws IOException {
        ArrayList<String> contents;
        try(BufferedReader r = new BufferedReader(new FileReader(f))){
            contents = new ArrayList<>();

            
            String line = r.readLine();
            while(line != null){
                contents.add(line);

                line = r.readLine();
            }
        }catch(IOException e){
            throw e;
        }

        return contents;
    }

}

class Connect{
    private String ip, token, startingIp;
    private int port, count;
    private Socket s;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Connect(String ip, int port, String token, String startingIp, int count){
        this.ip = ip;
        this.port = port;
        this.token = token;
        this.startingIp = startingIp;
        this.count = count;
        
        start();
    }

    private void start(){
        try{
            s = new Socket(ip, port);
            in = new ObjectInputStream(s.getInputStream());
            out = new ObjectOutputStream(s.getOutputStream());

        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}

class Message implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 123645;
    
}