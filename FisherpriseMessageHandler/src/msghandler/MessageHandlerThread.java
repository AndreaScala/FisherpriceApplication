package msghandler;

import com.rabbitmq.client.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import utilities.*;

public class MessageHandlerThread implements Runnable {

    private final static String QUEUE_NAME = "hello";
    private static final String EXCHANGE_NAME = "logs";
    private static final String URL = "http://localhost:8080/FisherpriseApplication-war/RestApp/datamanager";
    private static int i = 0;
    private String param;
  
    public MessageHandlerThread (String param) {
        this.param = param;
    }
  
    @Override
    public void run(){
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
            
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    String[] entryParts = message.split(" ");
                    LogEntry le = new LogEntry(entryParts[0] + " " + entryParts[1], entryParts[4], message.substring(message.indexOf(" - ") + 3, message.length()));
                    if (le.getMessageString().indexOf(param)!=-1) 
                        System.out.println(Thread.currentThread().getName() + " [x] Received '" + le + "'");
                    Gson gson = new Gson();
                    gson.toJson(le);
                    
                    Client c = Client.create();
                    WebResource webResourcePost = c.resource(URL);
                    ClientResponse rispostaPost = webResourcePost.post(ClientResponse.class);
                    
                }
            };
            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (IOException ex) {
            Logger.getLogger(MessageHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(MessageHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
