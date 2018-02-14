package REST;

import EJB.WriteBeanLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import utilities.LogEntry;

@Path("/datamanager")
public class RestAPI {

    WriteBeanLocal writeBean = lookupWriteBeanLocal();
    
    
    @POST
    public String receiveMessage(){
        return writeBean.write("Ciao");
    }

    private WriteBeanLocal lookupWriteBeanLocal() {
        try {
            Context c = new InitialContext();
            return (WriteBeanLocal) c.lookup("java:global/FisherpriseApplication/FisherpriseApplication-ejb/WriteBean!EJB.WriteBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
