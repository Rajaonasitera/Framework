package model;

import etu1814.framework.Util.Url;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import etu1814.framework.FileUpload;
import etu1814.framework.Util.ModelView;

/**
 * test1
 */
public class Emp {
    String nom;
    int numero;

    public Emp(String nom, int numero) {
        this.nom = nom;
        this.numero = numero;
    }

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

    @Url(url = "/saveEmp")
    public ModelView save(){
        ModelView mv = new ModelView();
        mv.setView("Emp.jsp");
        mv.addItem("emp",this);
        return mv;
    }

    @Url(url = "/insert", parameters = "nom/numero" )
    public ModelView insert(String nom,int numero){
        ModelView mv = new ModelView();
        mv.setView("InsertEmp.jsp");
        mv.addItem("nom", nom);
        mv.addItem("numero", numero);
        return mv;

    }

    @Url(url = "/upload", parameters = "file")
    public ModelView uploadCv(FileUpload file){
        String filePath = file.getPath();
        String fileName = file.getName();
        byte[] fileData = file.getBytearray();
        try (FileOutputStream fos = new FileOutputStream(filePath + File.separator + fileName)) {
            fos.write(fileData);
            System.out.println("Le fichier a été sauvegardé avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ModelView mv = new ModelView();
        mv.setView("File.jsp");
        return mv;
    }

    public void huhu(){

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }


}
