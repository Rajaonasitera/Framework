package etu1814.framework.servlet;
import java.io.*; 
import javax.servlet.*; 
import javax.servlet.http.*; 
public class FrontServlet extends HttpServlet { 
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
        processRequest(req, res);
    } 
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
        processRequest(req, res);
    } 
    public void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out=res.getWriter();
        String url=req.getServletPath();
        out.println(url);
    }
}
