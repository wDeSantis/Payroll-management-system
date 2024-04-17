package gestiondepaielys;
import java.util.Scanner;
import java.util.ArrayList;
import gestiondepaielys.Paye;
import gestiondepaielys.Employe;
import java.util.HashSet;
import java.util.regex.Pattern;

public class GestionPaie {
    // stock les ID pour voir si deja créer ou non
    private static HashSet<Integer> idsEmployesCrees = new HashSet<>();
    // initialise les heures payés a taux normales jusqua 44 heures
    public static final int Heures_Normales = 44;
    private static boolean employeAvecPayeCree = false;
    // Méthode principale 
    public static void main(String[] args) {
        // Création de la liste des employés
        ArrayList<Employe> mesEmployes = new ArrayList<>();
        // Création de la liste des payes
        ArrayList<Paye> mesPayes = new ArrayList<>();
        Scanner Clavier = new Scanner(System.in);
        int choix;
        do{
            // Initialise a faux 
           employeAvecPayeCree = false;
           // appel de la méthode pour afficher le menu
           afficherMenu();
            choix = Clavier.nextInt();
           switch (choix){
               case 1:
                   // appel de la méthode creerEmploye
                   Employe nouvelEmploye = creerEmploye();
                   mesEmployes.add(nouvelEmploye);
                   Paye paie = new Paye(nouvelEmploye);
                   mesPayes.add(paie);
                   break;
               case 2:
                   System.out.println();
                   System.out.print("Id de l'employé pour la paye : ");
                   int idEmploye = Clavier.nextInt();
                   // Appel de la méhode  trouverEmployeParID
                   Employe employe = Employe.trouverEmployeParID(mesEmployes, idEmploye);
                   // if qui s'assure que le ID est connu (créer)
                   if (employe != null){
                       Clavier.nextLine();
                    // Si oui appel de la méthode creer Paye
                       Paye.creerPaye(mesEmployes, mesPayes, employe);
                    
                   } else {
                    // Si non 
                       System.out.println("L'employé avec l'ID " + idEmploye + " n'a pas été trouvé.");
                   }   
                   break;
               case 3: // ok seulement si une paye existe pour un employé
                   if (employeAvecPayeCree = true){
                       // appel de la méthode pour afficher le total des contributions RRC
                   Paye.afficherTotalContributionRRC(mesEmployes, mesPayes);
                   } else {
                       System.out.println("Aucun employé avec une paye n'a été crée.");
                   }  
                   break;
               case 4: // ok seulement si une paye existe pour un employé
                   if (employeAvecPayeCree = true){
                       // appel de la méthode pour afficher le total des contributions AE
                   Paye.afficherTotalContributionAE(mesEmployes, mesPayes);        
                   } else {
                       System.out.println("Aucun employé avec une paye n'a été crée.");
                   }
                   break;
               case 5:
                   if (employeAvecPayeCree = true){
                       // Appel de la méthode pour afficher les employés a taux fixe
                   Paye.afficherEmployesTauxFixe(mesEmployes);    
                   } else {
                       System.out.println("Aucun employé avec une paye n'a été crée.");
                   }
                   break;
               case 6:
                   if (employeAvecPayeCree = true){
                       // appel de la méthode pour afficher les employés a commission
                       Paye.afficherEmployesCommission(mesEmployes);
                   } else {
                       System.out.println("Aucun employé avec une paye n'a été crée.");
                   }
                   break;
                case 7:
                    System.out.println("Au revoir!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Option invalide!");
           }
        } while (choix != 7);
        
    }   
    
    private static void  afficherMenu(){ // Méthode d'affichage du menu
            System.out.println("");
            System.out.println("******************************************");
            System.out.println("*        Menu de Gestion de Paie         *");
            System.out.println("******************************************");
            System.out.println("* 1- Ajouter un employé                  *");
            System.out.println("* 2- Ajouter une paye                    *");
            System.out.println("* 3- Afficher le total des contributions *"+
                                "\n*   au Régime de Retraite du Canada      *");
            System.out.println("* 4- Afficher le total des contributions *" +
                               "\n*    à l'assurance emploi                *");
            System.out.println("* 5- Liste des employés à taux fixe      *");
            System.out.println("* 6- Liste des employés à commission     *");
            System.out.println("* 7- Quitter                             *");
            System.out.println("******************************************");
              System.out.print("Votre choix :");
            
    }
         
    private static Employe creerEmploye(){ // Méthode pour ajouter un employé 
        Scanner Clavier = new Scanner(System.in);
        Employe nouvelEmploye;
        int choixDep, id;
        String prénom;
        String nomFam;
        String nomComplet;     
       
        // Demande du id et 
        // do while pour assurer que le ID est valide
        do{
        System.out.print("\nID de l'employé : ");
        while (!Clavier.hasNextInt()){
            System.out.println("Veuillez entrer un ID valide (chiffres seulement).");
            Clavier.next();
        }
        id = Clavier.nextInt();
        if (id <= 0){
            System.out.println("L'ID doit être supérieur à 0");
        } else if (idsEmployesCrees.contains(id)){
            System.out.println("Cet ID est déjà attribué à un employé.");
        }
        } while (id <= 0 || idsEmployesCrees.contains(id)); // Assure que le id n'est pas négatif ni deja assigné
         // Ajout de l'id a la liste d'id si id ok
         idsEmployesCrees.add(id);
        Pattern pattern = Pattern.compile("^[a-zA-Z\\s]+$");
        
        // Demande du prénom avec boucle do while 
        // pour assurer que le prénom est valide
        do {
        Clavier.nextLine();
        System.out.print("Prénom de l'employé : ");
        prénom = Clavier.nextLine();
        if (prénom.isEmpty()) {
            System.out.println("Le prénom ne peut être vide");
        } else if (!prénom.matches("^[a-zA-Z]+$")){
            System.out.println("Le prénom ne peut contenir que des lettres.");
        }
        } while (prénom.isEmpty() || !pattern.matcher(prénom).matches());
        
        // Demande du nom avec boucle do while
        // pour assurer que le nom est valide
        do{
        System.out.print("Nom de l'employé : ");
        nomFam = Clavier.nextLine();
        if (nomFam.isEmpty()){            
            System.out.println("Le nom ne peut être vide");
        } else if (!nomFam.matches("^[a-zA-Z]+$")){
            System.out.println("Le nom ne peut contenir que des lettres.");
        }
        } while (nomFam.isEmpty() || !pattern.matcher(nomFam).matches());
       
        // affichage et choix du département
        System.out.println("\nChoix de départements");
        for (Departement dep : Departement.values()){
            System.out.println(dep.ordinal()+1+". "+ dep.name());
        }
        do{
        System.out.print("Votre choix : ");
        choixDep = Clavier.nextInt();
        if (choixDep < 1 || choixDep > Departement.values().length){
            System.out.println("Choix de département invalide," +
                               " veuillez choisir entre 1 et 4.");
        }
        } while (choixDep < 1 || choixDep > Departement.values().length);
        
        // Association du nom complet de l'employé et affichage du résulat
        nomComplet = prénom+" "+nomFam;
        System.out.println("Vous avez ajouté "+nomComplet+
                           " comme employé, du département: "+choixDep);
        Departement departement = Departement.values()[choixDep-1];
         nouvelEmploye = new Employe(id, nomComplet, departement);
        return nouvelEmploye;   
    }
      
    }
        

