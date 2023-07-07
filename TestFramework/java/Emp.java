package model;

import etu1814.framework.Util.Url;
import etu1814.framework.Util.ModelView;

/**
 * test1
 */
public class Emp {

    public Emp(){

    }
    
    @Url(url = "/getAll")
    public ModelView getAll(){
        ModelView mv = new ModelView();
        String[] nom= new String[3];
        nom[0]="Tatiana";
        nom[1]="Gustave";
        nom[2]="Nivo";
        mv.setView("AllEmp.jsp");
        mv.addItem("all",nom);
        return mv;
    }

    public void huhu(){

    }


}
