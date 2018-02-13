package REST;

import EJB.WriteBeanLocal;
import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import utilities.LogEntry;

@Path("/datamanager")
public class RestAPI {
    
    @EJB
    private static WriteBeanLocal wbl;
    
    @POST
    public String receiveMessage(){
        return wbl.write("Ciao");
    }
}
