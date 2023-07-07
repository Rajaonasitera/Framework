package etu1814.framework.servlet;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.*; 
import javax.servlet.http.*;
import etu1814.framework.*;
import etu1814.framework.Util.ModelView;
import etu1814.framework.Util.Util;
// import org.apache.commons.fileupload.*;

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

            if (MappingUrls.containsKey(url)) {
                Mapping ma=MappingUrls.get(url);
                Class cl = Class.forName(ma.getClassName());
                Object obj = cl.getConstructor().newInstance();
                Field[] attribut = cl.getDeclaredFields();

                for (int i = 0; i < attribut.length; i++) {
                    if (req.getParameter(attribut[i].getName())!=null) {
                        Field f = cl.getDeclaredField(attribut[i].getName());
                        f.setAccessible(true);
                        String value = req.getParameter(attribut[i].getName());
                        f.set(obj, Util.conversion(value,f.getType()));
                    }
                }
                String contenttype = req.getContentType();
                if(contenttype != null && contenttype.toLowerCase().startsWith("multipart/form-data")) {
                    System.out.println("mandalo sprint 9");

                    FileUpload file = new FileUpload();
                    for(Part part : req.getParts()) {
                        try {
                            file.setName(Paths.get(part.getSubmittedFileName()).getFileName().toString());
                            InputStream in = part.getInputStream();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            byte[] buffer = new byte[4096];
                            int byteread;
                            while((byteread = in.read()) != -1) {
                                baos.write(buffer, 0, byteread);
                            }
                            file.setBytearray(baos.toByteArray());
                            out.println("taile byte :"+file.getBytearray().length);
                            mv = new ModelView();
                            HashMap<String,Object> mas = new HashMap<String,Object>();
                            mas.put("taillefichier", file.getBytearray().length);
                            mas.put("nomfichier", file.getName());

                            mv.setData(mas);
                            mv.setView("index.jsp");
                            } catch (Exception e) {
                                out.println(e.getMessage()+"cause"+e.getCause());
                            }
                        }
                }
            }

                Method[] met = cl.getDeclaredMethods();
                
                for (int i = 0; i < met.length; i++) {
                    if (met[i].getName().equals(ma.getMethod())) {
                        Parameter[] parameters = met[i].getParameters();

                        if (parameters.length!=0) {
                            Object[] arg = new Object[parameters.length];
                            Vector<String> allarg = new Vector<>();
                            String[] argname = (((etu1814.framework.Util.Url)met[i].getAnnotations()[0]).parameters()).split("/");

                                for (int j = 0; j < parameters.length; j++) {
                                    arg[j] = Util.conversion(req.getParameter(argname[j]), parameters[j].getType());
                                }
                                mv = (ModelView)met[i].invoke(obj, arg);
                        }else{

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
            System.out.println(e);
            // TODO: handle exception
        }

    } 
}