package etu1814.framework.servlet;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.*; 
import javax.servlet.http.*;
import etu1814.framework.*; 

public class FrontServlet extends HttpServlet { 
    HashMap<String,Mapping> MappingUrls=new HashMap<>();

    @Override
    public void init() throws ServletException{
        super.init();
        try {
        ClassLoader loader = getServletContext().getClassLoader();
        URI uri =loader.getResource("").toURI();
        System.out.println(uri);
        File f = new File(uri);
            System.out.println(f.exists());
            ArrayList<Class> tab= etu1814.framework.Util.Util.getAllAnnotedClasse(f);
            for (Class allClass : tab){
                ArrayList<Method> methods = etu1814.framework.Util.Util.getAllMethodAnnoted(allClass);
                for (Method method : methods) {
                    if (method.getAnnotations()[0] instanceof etu1814.framework.Util.Url){
                        System.out.println("dhhjs");
                        Mapping m = new Mapping(allClass.getName(),method.getName());
                        String key = ((etu1814.framework.Util.Url)(method.getAnnotations()[0])).url();
                        MappingUrls.putIfAbsent(key, m);
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
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
        for (Map.Entry<String,Mapping> i : MappingUrls.entrySet()) {
            out.println(i.getKey());
            out.println(i.getValue());
        }
    } 
}