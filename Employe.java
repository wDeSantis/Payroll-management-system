
package gestiondepaielys;
import java.util.ArrayList;
import java.util.Scanner;

public class Employe {
    // déclaration de variables
    private double tauxHoraire;
    private int id;
    private String nom;
    private Departement departement;
    private int heuresSup;
    private double totalVentes;
    
    // Constructeur et initialisation des variables
     Employe(int id, String nom, Departement departement) {
        this.id = id;
        this.nom = nom;
        this.departement = departement;
    }
    
     // Méthode pour obtenir le ID de l'employé
    public int getID(){
        return id;
    }
    // Méthode pour obtenir le nom de l'employé
    public String getNom(){
        return nom;
    }
    // Méthode pour obtenir le département de l'employé      
    public Departement getDepartement(){
        return departement;
    }
    // Méthode pour initialiser le nb d'heures supp
    public void setHeuresSup(int heuresSup){
        this.heuresSup = heuresSup;
    }
    // Méthohde pour obtenir le nombre d'heures supp
    public int getHeuresSup(){
        return heuresSup;
    }
    // Méthode pour définir le total des ventes brutes
    public void setTotalVentes(double totalVentes){
        this.totalVentes = totalVentes;
    }
    // Méthode pour obtenir le total des ventes brutes
    public double getTotalVentes(){
        return totalVentes;
    }
    // Méthode qui calcule le taux horarie par rapport au département choisit
    public double calculTauxHoraire(){
        double tauxHoraire = 0.00;
        // Association du taux horaire au bon département
        switch (departement){
            case Restaurant:
               tauxHoraire = 8.50;
               break;
            case Maintenance:
                tauxHoraire = 12.50;
                break;
            case Commis_Paysagiste:
                tauxHoraire = 15.75;
                break;
            case Ventes:
                tauxHoraire = 15.00;
                break;
                
        }
        return tauxHoraire;
    }
    // Méthode retournant le taux horaire de l'employé
   public double getTauxHoraire(){
       return calculTauxHoraire();
           
       }
   // Trouver un employé par son Id
    public static Employe trouverEmployeParID(ArrayList<Employe> mesEmployes, int id){
        for (Employe employe : mesEmployes){
            if (employe.getID() == id){
                return employe;
            }
        }
        return null; // Si l'employé n'a pas été trouvé
    }

    
    
}

