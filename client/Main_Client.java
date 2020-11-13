import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Pattern;

class Main {
    final static int NUM_ARGS = 2;

    //docker run --net=pub_net -d --ip=10.0.0.201 --hostname="test"  --name="remote-desktop" --rm --name="remote-desktop" scottyhardy/docker-remote-desktop
    //docker container stop $(docker container ls -q --filter name=remote*)
    public static void main(String[] args) {
        // check if length of args array is
        // greater than 0
        try {
            if (args.length == NUM_ARGS) {
                System.out.println("The command line arguments are:");

                // iterating the args array and printing
                // the command line arguments
                // secret token
                String secret = args[0];
                System.out.println("token is " + secret);

                int port = 3066;

                // port for server
                try {
                    port = Integer.parseInt(args[1]);
                    if (port == 0) { // using default
                        port = 3066;
                        System.out.println("using port 3066");
                    } else if (port <= 1023) {
                        System.out.println("you are not allowed to use a port that is between 0-1023");
                        throw new Error();
                    } else if (port > 65535) {
                        System.out.println("you cannot use a port that doesn't exist!");
                        throw new Error();
                    }
                } catch (Exception e) {
                    System.out.println("the port needs to be in numerical form");
                    throw new Error();
                }

                Server s = new Server(port, secret);
                s.start();

            } else {
                System.out.println("format should be: [secret token] [port]");
                throw new Error();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

class Server {
    private int port;
    private String token;
    final String create = "docker run --net=pub_net -d --ip=10.0.0.201 --hostname=\"$1\"  --name=\"remote-$1\" --rm scottyhardy/docker-remote-desktop";
    final String destroy = "docker container stop $(docker container ls -q --filter name=remote*)";
    public Server(int port, String token) {
        this.port = port;
        this.token = token;
    }

    public void start() {
        try(ServerSocket ss = new ServerSocket(this.port)){

            System.out.print("waiting for input:");
            while(!ss.isClosed()){
                Socket client = ss.accept();
                System.out.println("accepted connection");
                ObjectInputStream oi = new ObjectInputStream(client.getInputStream());
                ObjectOutputStream oo = new ObjectOutputStream(client.getOutputStream());

                Message m = (Message) oi.readObject();
                if(m.o != Operation.LOGIN || !m.args[0].equals(token)){
                    oo.writeObject(new Message(Operation.FAIL, new String[]{"invalid token"}));
                    client.close();
                }else{
                    oo.writeObject(new Message(Operation.SUCCESS, new String[]{"proper token entered"}));
                }

                while(!client.isClosed()){
                    m = (Message) oi.readObject();
                    switch (m.o) {
                        case CREATE:
                            System.out.println("attempting to create");
                            if(create(m)) {oo.writeObject(new Message(Operation.SUCCESS, new String[]{"created successfully"}));}
                            else{oo.writeObject(new Message(Operation.FAIL, new String[]{"invalid token"}));}
                            break;
                        case DISCONNECT:
                            System.out.println("disconnecting");
                            client.close();
                            break;
                        case STATUS:
                            status(m);
                            break;
                        default:
                            break;
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean create(Message m) throws IOException {
        String ip = m.args[0];

        boolean ret = true;

        String cmd = create.replaceAll(Pattern.quote("$1"), ip);
        Process process = Runtime.getRuntime().exec(cmd);
        System.out.println(cmd);
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
            String line ="";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }   
        }catch(Exception e){
            ret = false;
        }
        
        return ret;
    }

    private boolean status(Message m){
        throw new UnsupportedOperationException();
    }
}

class Message implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 123645;
    public Operation o;
    public String[] args;
    public Message(Operation o, String[] args){
        this.o = o;
        this.args = args;
    }

}

enum Operation{
    LOGIN,
    CREATE,
    SUCCESS,
    FAIL,
    DISCONNECT,
    STATUS;
}