/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzer;

import EJB.DataManagerBeanLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilities.LogEntry;

/**
 *
 * @author gino
 */
public class AnalyzerServlet extends HttpServlet {

    @EJB
    private DataManagerBeanLocal dataManagerBeanLocal;
    
    private ArrayList<LogEntry> lel;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        int nQuery = Integer.parseInt(request.getParameter("query"));
        String getQuery="", printReq="", parEv, parID, parStartTimestamp, parEndTimestamp;
        switch(nQuery){
            case 1:
                printReq = "Eventi ordinati nel tempo per tutte le macchine";
                getQuery = "SELECT * FROM LogEntries ORDER BY timeStamp;";
                break;
            case 2:
                printReq = "Eventi specifici ordinati nel tempo per tutte le macchine";
                parEv = request.getParameter("event");
                getQuery = "SELECT * FROM LogEntries WHERE messageString = '" + parEv + 
                        "' ORDER BY timeStamp;";
                break;
            case 3:
                printReq = "Eventi ordinati nel tempo per singola macchina";
                parID = request.getParameter("machineid");
                getQuery = "SELECT * FROM LogEntries WHERE MachineID = '" + parID + 
                        "' ORDER BY timeStamp;";
                break;
            case 4:
                printReq = "Eventi specifici ordinati nel tempo per singola macchina";
                parEv = request.getParameter("event");
                parID = request.getParameter("machineid");
                getQuery = "SELECT * FROM LogEntries WHERE messageString = '" + parEv + 
                        "' AND MachineID = '" + parID + "' ORDER BY timeStamp;";
                break;
            case 5:
                printReq = "Eventi in una specifica finestra temporale";
                parStartTimestamp = request.getParameter("tsstart");
                parEndTimestamp = request.getParameter("tsend");
                getQuery = "SELECT * FROM LogEntries WHERE timeStamp BETWEEN '"+ 
                        parStartTimestamp + "' AND '" + parEndTimestamp + "' ORDER BY timeStamp;";
                break;

        }
        lel = dataManagerBeanLocal.retrieve(getQuery);
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AnalyzerServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>" + printReq + "</h3>");
            out.println("<h4> Risultati Richiesta: </h4>");
            out.println("</body>");
            out.println("</html>");
            
            for(int i = 0; i< lel.size(); i++)
                out.println("<b> Timestamp: </b>" + lel.get(i).getTimeStamp().substring(0, lel.get(i).getTimeStamp().length() - ".000".length()) +
                            "<b> MachineID: </b>" + lel.get(i).getMachineID() +
                            "<b> Message:  </b>" + lel.get(i).getMessageString() +"<br>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
