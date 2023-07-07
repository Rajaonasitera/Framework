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
import etu1814.framework.Util.ModelView; 
import java.util.Map.Entry;

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
        try {
            PrintWriter out=res.getWriter();
            String url=req.getServletPath();
            ModelView mv=new ModelView();
            out.println(url);
            if (MappingUrls.containsKey(url)) {
                Mapping ma=MappingUrls.get(url);
                Class cl = Class.forName(ma.getClassName());
                Object obj = cl.getConstructor().newInstance();
                Method[] met = cl.getDeclaredMethods();
                for (int i = 0; i < met.length; i++) {
                    if (met[i].getName().equals(ma.getMethod())) {
                        mv = (ModelView)met[i].invoke(obj);
                    }
                }
            }
            HashMap<String,Object> mp= mv.getData();
            for ( Entry e : mp.entrySet()) {
                req.setAttribute((String)e.getKey(),e.getValue());
            }
            // for (Map.Entry<String,Mapping> i : MappingUrls.entrySet()) {
            //     out.println(i.getKey());
            //     out.println(i.getValue());
            // }
            RequestDispatcher dispatch = req.getRequestDispatcher(mv.getView());
            dispatch.forward(req,res);
        } catch (Exception e) {
            // TODO: handle exception
        }

    } 
}