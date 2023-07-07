package etu1814.framework.Util;

import java.beans.PropertyEditorManager;
import java.beans.PropertyEditorSupport;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import javax.servlet.http.*;

public class Util {

    // public static Vector<String> getAllArgument(HttpServletRequest req,){
        
    // }

    public static String getUrlString(String url){
        String[] all = url.split("/");
        return "/"+all[1];
    }

    public static <T> T conversion(String value, Class<T> type){
        PropertyEditorSupport editor = (PropertyEditorSupport) PropertyEditorManager.findEditor(type);
        editor.setAsText(value);
        return (T) editor.getValue();
    }

    public static String[] getParameters(Enumeration<String> values, HttpServletRequest req){
        ArrayList<String> list = new ArrayList<>();
        while (values.hasMoreElements()) {
            list.add(values.nextElement());
        }
        String[] paramName = new String[list.size()];
        paramName = list.toArray(paramName);
        String[] rep = new String[list.size()];
        for (int i = 0; i < rep.length; i++) {
            rep[i] = req.getParameter(paramName[i]);
        }
        return rep;
    }

    public static ArrayList<String>getAllCLassName(File f,ArrayList<String> tab,String pack){
        File[] files = f.listFiles();
        for(int i=0;i<files.length;i++){
            if (files[i].isFile()){
                String nomfile= files[i].getName().split("\\.")[0];
                tab.add(pack.concat(nomfile));
            }else{
                String dossier = pack+files[i].getName()+".";
                getAllCLassName(files[i],tab,dossier);
            }
        }
        return tab;
    }

    public static ArrayList<Field> getAllAnnotedAttribut(Class classe){
        Field[] fields = classe.getDeclaredFields();
        ArrayList<Field> rep = new ArrayList<Field>();
        for (Field field : fields) {
            if (field.getAnnotations().length!=0){
                rep.add(field);
            }
        }
        return rep;
    }

    public static ArrayList<Method> getAllMethodAnnoted(Class classe){
        Method[] methods= classe.getDeclaredMethods();
        ArrayList<Method> rep = new ArrayList<Method>();
        for (Method method : methods){
            if (method.getAnnotations().length>0){
                rep.add(method);
            }
        }
        return rep;
    }

    public static ArrayList<Class> getAllAnnotedClasse(File f)throws Exception{
        ArrayList<Class> allclass= new ArrayList<Class>();
        ArrayList<String> allClasseName = Util.getAllCLassName(f,new ArrayList<String>(),"");
        for (String s : allClasseName) {
            Class c = Class.forName(s);
                allclass.add(c);
        }
        return allclass;
    }
}
