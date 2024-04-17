
package gestiondepaielys;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;
public class Paye {
    // Déclarations de variables
    public static final int Heures_Normales = 44;
    private Employe employe;
    private double salaireBrut;
    private int heuresSup;
    private double commission;
    private double salaireNet;

    
     // Constructeur
    public Paye(Employe employe){
        this.employe = employe;
    }    
   
    // Méthode permettant de créer une Paye et qui la stock dans mesPayes
     public static void creerPaye(ArrayList<Employe> mesEmployes, ArrayList<Paye> mesPayes, Employe employe){
         Paye paie = new Paye(employe);
        Scanner Clavier = new Scanner(System.in);
        // appel de la méthode pour afficher le département
        afficherDepartement(employe);
        // appel de la méthode pour afficher le taux Horaire de l'employé
        double tauxHoraire = employe.getTauxHoraire();
        afficherTauxHoraire(tauxHoraire);
        // appel de la méthode pour demander le numéro de la semaine de la paie
        int numSemaine = demanderNumeroSemaine(Clavier);
        // appel de la méthode pour demander le nombre d'heures que l'employé
        // a fait pour la semaine
        int nbHeures = demanderNombreHeures(Clavier);
        // appel de la méthode pour demander le total des ventes brutes
        // si l'employé est du département vente
        double totalVentes = demanderTotalVentes(Clavier, employe);
        // stockage du résultat du total des ventes brutes pour l'employé
        employe.setTotalVentes(totalVentes);       
        
        double commission = 0.0;
        // appel de la méthode pour calculer les heures supplémentaires
        // et stockage des heuresSUp
        int heuresSup = calculHeuresSup(employe, nbHeures);
        employe.setHeuresSup(heuresSup);       
        
        //calcul de la commission pour le département des ventes
         if (employe.getDepartement() == Departement.Ventes){
            commission = totalVentes * 0.015;
         }
             
        // appel de la méthode pour calculer le salaire Brut de l'employé
        double salaireBrut = calculerSalaireBrut(employe, tauxHoraire, nbHeures, totalVentes);
         // Ajout de l'instance de paye a la liste mesPayes
         paie.setPaye(salaireBrut);
         mesPayes.add(paie);
        // True lorsqu'un employé est créer
        boolean employeAvecPayeCree = true; 
       // appel des méthodes pour calculer la contribution RRC et AE 
       // que l'employé doit contribuer
       double deductionRRC = calculContributionRRC(salaireBrut);
       double deductionAE = calculContributionAE(salaireBrut);
       double deductionTotale = deductionRRC + deductionAE;
       // Affichage des résultats pour le salaire
       afficherResultats(employe, heuresSup, commission, salaireBrut, deductionRRC, deductionAE,numSemaine, nbHeures);
    }
           
    // méthode pour définir le salaire Brut
    public void setPaye(double salaireBrut){
        this.salaireBrut = salaireBrut;
    }
    // méthode pour obtenir le salaire Brut
    public double getPaye(){
        return this.salaireBrut;
    }
    
    // méthode pour obtenir l'employe
    public Employe getEmploye(){
        return employe;
    }

     
     // Méthode pour afficher le département de l'employé
    private static void afficherDepartement(Employe employe) {
        System.out.println("Département de l'employé : " + employe.getDepartement());
    }

    // Méthode pour afficher le taux horaire de l'employé
    private static void afficherTauxHoraire(double tauxHoraire) {
        System.out.println("Taux horaire : " + tauxHoraire);
    }

    // Méthode pour demander le numéro de la semaine
    private static int demanderNumeroSemaine(Scanner Clavier) {
        System.out.print("Numéro de la semaine : ");
        return Clavier.nextInt();
   
    }

    // Méthode pour demander le nombre d'heures que l'employé a fait pour la semaine
    private static int demanderNombreHeures(Scanner Clavier) {
        System.out.print("Nombre d'heures : ");
        return Clavier.nextInt();
    }

    // Méthode qui demande la saisie des ventes brutes hebdomadaires et la retourne
    public static double demanderTotalVentes(Scanner Clavier, Employe employe) {
        if (employe.getDepartement() == Departement.Ventes) {
            System.out.print("Total des ventes brutes hebdomadaires : ");
            return Clavier.nextDouble();
        }
        Clavier.nextLine(); // Consommer la nouvelle ligne
        return 0.0;
    }
    // Méthode pour calculer et retourner le salaire Brut de l'employé
    private static double calculerSalaireBrut(Employe employe, double tauxHoraire, int nbHeures, double totalVentes) {
        double salaireHeuresSup = 0.0;
        double commission = 0.0;
        int heuresSup = employe.getHeuresSup(); // pour obtenir les HeuresSup

        if (employe.getDepartement() == Departement.Ventes) {
            commission = totalVentes * 0.015;
        }

        double tauxModifie = tauxHoraire * 1.5;
        
        if (heuresSup > 0){
            salaireHeuresSup = heuresSup * tauxModifie;
        }
        return ((nbHeures - heuresSup) * tauxHoraire) + salaireHeuresSup + commission;
    }

    // Méthode pour Calculer et retourner les heures supplémentaires pour les 3 départements ayant le droit
    private static int calculHeuresSup(Employe employe, int nbHeures) {
        if (employe.getDepartement() == Departement.Ventes) {
            return 0; // Pas d'heures supplémentaires pour le département des ventes
        } else {
            return Math.max(0, nbHeures - Heures_Normales);
             
        }
    }

    // Méthode pour calculer les contributions RRC que l'employé doit contribuer
    private static double calculContributionRRC(double salaireBrut) {
        return salaireBrut * 0.0495;
    }

    // Méthode pour calculer les contributions AE que l'employé doit contribuer
    private static double calculContributionAE(double salaireBrut) {
        return salaireBrut * 0.0198;
    }

    // Méthode pour afficher le total des contributions RRC
    public static void afficherTotalContributionRRC(ArrayList<Employe> mesEmployes, ArrayList<Paye> mesPayes) {
        DecimalFormat df = new DecimalFormat("#0.00"); // Format pour 2 chiffres apres virgule seulement
    System.out.println("Total des contributions au RRC par employé :");
    for (Employe employe : mesEmployes) {
        double contributionRRC = 0.0;
        for (Paye paie : mesPayes) {
            if (paie.getEmploye() == employe) {
                contributionRRC += paie.calculContributionRRC(paie.getPaye());
            }
        } 
        System.out.println(employe.getNom() + " : " + df.format(contributionRRC) + " $");
    }
}
    // Méthode pour afficher le total des contributions AE par employé
    public static void afficherTotalContributionAE(ArrayList<Employe> mesEmployes, ArrayList<Paye> mesPayes) {
        DecimalFormat df = new DecimalFormat("#0.00"); // Format pour 2 chiffres apres virgule seulement
    System.out.println("Total des contributions à l'assurance emploi (AE) par employé :");
    for (Employe employe : mesEmployes) {
        double contributionAE = 0.0;  
        for (Paye paie : mesPayes) {
            if (paie.getEmploye() == employe) {
                contributionAE += paie.calculContributionAE(paie.getPaye());
            }
        }
        System.out.println(employe.getNom() + " : " + df.format(contributionAE) + " $");
    }
}
    
    // Méthode pour afficher les employés à taux fixe
    public static void afficherEmployesTauxFixe(ArrayList<Employe> mesEmployes) {
    System.out.println("Employés à taux fixe :");

    for (Employe employe : mesEmployes) {
        if (employe.getDepartement() != Departement.Ventes) {
            System.out.println("ID : " + employe.getID() + ", Nom : " + employe.getNom() + 
                                ", Heures supplémentaires : " + employe.getHeuresSup());
        }
    }
}
    
   // Méthode pour afficher les employés à commission
    public static void afficherEmployesCommission(ArrayList<Employe> mesEmployes){
        System.out.println("Employés à commission :");
        
        for (Employe employe : mesEmployes){
            if (employe.getDepartement() == Departement.Ventes){
                System.out.println("ID : " + employe.getID() + ", Nom : " + employe.getNom() +
                                    ", Ventes brutes : " + employe.getTotalVentes() + "$");
            }
        }
    }

    
    // Méthode d'affichage des résultats pour la paie de l'employé
    private static void afficherResultats(Employe employe, int heuresSup, double commission, double salaireBrut, double deductionRRC, double deductionAE, int numSemaine, int nbHeures) {
        DecimalFormat df = new DecimalFormat("#0.00");
    System.out.println("");
    System.out.println("Pour la semaine no : " + numSemaine);
    System.out.println("Résultats pour " + employe.getNom());
    System.out.println("Nombre d'heures travaillées : " + nbHeures);
    System.out.println("Heures supplémentaires : " + heuresSup);
    System.out.println("Commission : " + df.format(commission) + "$");
    System.out.println("Salaire Brut: " + df.format(salaireBrut) + "$");
    System.out.println("Contribution RRC: " + df.format(deductionRRC) + "$");
    System.out.println("Contribution AE: " + df.format(deductionAE) + "$");
    System.out.println();
    System.out.println("Retour au menu principal");
}

  
}

