package REST;

import EJB.SendToDBManagerLocal;
import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import utilities.LogEntry;

@Path("/datamanager")
public class RestAPI {

    SendToDBManagerLocal sendToDBManager = lookupSendToDBManagerLocal();
    
    
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public void receiveMessage(String logEntryJson){
        LogEntry le = new Gson().fromJson(logEntryJson, LogEntry.class);
        sendToDBManager.add(le);
    }

    private SendToDBManagerLocal lookupSendToDBManagerLocal() {
        try {
            Context c = new InitialContext();
            return (SendToDBManagerLocal) c.lookup("java:global/FisherpriseApplication/FisherpriseApplication-ejb/SendToDBManager!EJB.SendToDBManagerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
