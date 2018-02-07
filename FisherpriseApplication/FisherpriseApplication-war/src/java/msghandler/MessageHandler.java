package msghandler;

public class MessageHandler {
    public static void main (String[] args){
        Thread t1 = new Thread(new MessageHandlerThread("Error receiving object from"));
        Thread t2 = new Thread(new MessageHandlerThread("Network proxy error on port"));
        t1.start();
        t2.start();
    }
}
