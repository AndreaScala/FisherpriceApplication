package sender;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.*;

public class Sender {

  private final static String QUEUE_NAME = "hello";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    
    BufferedReader in;
    String message;
    String dir = System.getProperty("user.dir");
    
    in = new BufferedReader(new FileReader(dir + "/Files/ERROR_WARN.log"));
    
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    message = in.readLine();
    while (message != null){
        if(message.indexOf("WARN") != -1){
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
        message = in.readLine();
    }
    
    in.close();
    channel.close();
    connection.close();
  }
}
