package msghandler;

import com.rabbitmq.client.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import utilities.*;

public class MessageHandler implements Runnable {

  private final static String QUEUE_NAME = "hello";
  private String param;
  
  public MessageHandler (String param) {
      this.param = param;
  }
  
  @Override
  public void run(){
      try {
          ConnectionFactory factory = new ConnectionFactory();
          factory.setHost("localhost");
          Connection connection = factory.newConnection();
          Channel channel = connection.createChannel();
          
          channel.queueDeclare(QUEUE_NAME, false, false, false, null);
          System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
          
          Consumer consumer = new DefaultConsumer(channel) {
              @Override
              public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                      throws IOException {
                  String message = new String(body, "UTF-8");
                  String[] entryParts = message.split(" ");
                  LogEntry le = new LogEntry(entryParts[0] + " " + entryParts[1], entryParts[4], message.substring(message.indexOf(" - ") + 3, message.length()));
                  System.out.println(" [x] Received '" + le + "'");
                  Gson gson = new Gson();
                  gson.toJson(le);
                  
                  //POST
                  
                  /* Il codice sottostante non funziona ancora (Connection Refused), ma dovrebbe essere la chiamata*/
                  /*
                  URL url = new URL("http://localhost/datamanager/put");
                  URLConnection con = url.openConnection();
                  HttpURLConnection http = (HttpURLConnection)con;
                  http.setRequestMethod("PUT");
                  http.getResponseCode();
                  */
              }
          };
          channel.basicConsume(QUEUE_NAME, true, consumer);
      } catch (IOException ex) {
          Logger.getLogger(MessageHandler.class.getName()).log(Level.SEVERE, null, ex);
      } catch (TimeoutException ex) {
          Logger.getLogger(MessageHandler.class.getName()).log(Level.SEVERE, null, ex);
      }
  }

}
