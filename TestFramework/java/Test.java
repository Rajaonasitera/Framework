package model;

import etu1814.framework.Util.Url;
import etu1814.framework.Util.ModelView;

/**
 * test1
 */
public class Test {

    public Test(){

    }
    
    @Url(url = "/ajout")
    public ModelView Ajout(){
        ModelView mv = new ModelView();
        mv.setView("Ajout.jsp");
        return mv;
    }

    public void huhu(){

    }


}
