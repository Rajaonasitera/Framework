package etu1814.framework.Util;

import java.util.HashMap;

/**
 * ModelView
 */
public class ModelView {
    String view;
    HashMap<String,Object> data= new HashMap<>();

    public HashMap<String, Object> getData() {
        return data;
    }
    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
    
    public String getView() {
        return view;
    }
    public void setView(String view) {
        this.view = view;
    }

    public void addItem(String key,Object value){
        this.data.putIfAbsent(key, value);
    }
    
    
}