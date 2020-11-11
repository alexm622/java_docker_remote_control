class Main{
    final int NUM_ARGS = 1;
    //docker run --net=pub_net -d --ip=10.0.0.201 --hostname="test"  --name="remote-desktop" --rm --name="remote-desktop" scottyhardy/docker-remote-desktop
    public static void main(String[] args) {
        // check if length of args array is 
        // greater than 0 
        if (args.length = NUM_ARGS) 
        { 
            System.out.println("The command line arguments are:"); 
  
            // iterating the args array and printing 
            // the command line arguments
            //secret token
            String secret = args[0];
            
            //port for server
            try {
	            int port = Integer.parseInt(args[1]);
	            if(port <= 1023) {
	            	System.out.println("you are not allowed to use a port that is between 0-1023");
	            	break;
	            }else if (port > 65535) {
	            	System.out.println("you cannot use a port that doesn't exist!");
	            	break;
				}
	        }catch(Exception e){
	        	System.out.println("the port needs to be in numerical form");
	        	break;
	        }
            
            
            
        } 
        else
        {
            System.out.println("format should be: [secret token] [port]"); 
            break;
        }


    }
}