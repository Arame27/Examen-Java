import java.time.LocalDate;
import java.util.Scanner;


public class Main{
    public static Scanner scanner = new Scanner(System.in);
    static Service s = new Service();
    static Validator v = new Validator();
    public static void main(String[] args){
        
        s.initList();
        int choix;
        do {
            choix = menu();
            Object p; 
            //Object t; //taux
            //Object c; //choix du local
            //Object d; //dimension
            //Object n; //nombre de piéces 
            Chambre chambre;
            Appartement appartement;
            switch (choix) {
                
                case 1 : 
                    System.out.println("Entrer la localisation du local : ");
                    String localisation = scanner.nextLine();
                    do {
                        System.out.println("Entrer le prix du local : ");
                        p = scanner.nextLine();
                    } while (!v.isPositive(p));
                    int prix=Integer.parseInt((String) p);

                    do {
                        do {
                            System.out.println("Entrer le taux du local : ");
                            p = scanner.nextLine();
                        } while (!v.isPositive(p));
                    } while (!v.isTaux(Integer.parseInt((String) p)));
                    int taux=Integer.parseInt((String) p);

                    do {
                        do {
                            System.out.println("Choisir le type du local : \n 1-Chambre  \n 2-Appartement");
                            p = scanner.nextLine();
                        } while (!v.isPositive(p));
                    } while (Integer.parseInt((String) p)!=1 && Integer.parseInt((String) p)!=2);
                    int choice = Integer.parseInt((String) p);
                    if (choice==1){
                        do {
                            System.out.println("Entrer la dimension de la chambre");
                            p = scanner.nextLine();
                        } while (!v.isPositive(p));
                        int  dimension= Integer.parseInt((String) p);
                        chambre = new Chambre(prix,localisation,taux,dimension);
                        s.addLocal(chambre);
                    }else{
                        do {
                            do {
                                System.out.println("Saisir le nombre de piéces (minimum 3 )");
                                p = scanner.nextLine();
                            } while (!v.isPositive(p));
                        } while (Integer.parseInt((String) p)<3);
                        int nbrePiece = Integer.parseInt((String) p);
                        appartement=new Appartement(prix,localisation,taux,nbrePiece); 
                        
                        Object di; //dimension
                        for(int i = 1 ;i<=nbrePiece;i++){
                            do {
                                System.out.println("Entrer la dimension de la chambre");
                                di = scanner.nextLine();
                            } while (!v.isPositive(di));
                            int  dimen= Integer.parseInt((String) di);
                            Chambre ch = new Chambre(dimen);
                            appartement.addLocal(ch);
                        }
                        s.addLocal(appartement);
                        
                    }
                    System.out.println("Local ajouté avec succés");
                    break;
                case 2 :
                    do {
                        do {
                            System.out.println("Choisir le type du local : \n 1-Chambre  \n 2-Appartement");
                            p = scanner.nextLine();
                        } while (!v.isPositive(p));
                    } while (Integer.parseInt((String) p)!=1 && Integer.parseInt((String) p)!=2);
                    int choose = Integer.parseInt((String) p);
                    if (choose == 1){
                        s.localParType("Chambre");
                    }else{
                        s.localParType("Appartement");
                    }
                    
                    break;
                    //Object nc; //nci
                case 3 : 
                
                    do {
                        System.out.println("Entrer le nci du client : ");
                        p = scanner.nextLine();
                    } while (!v.isNci((String)p));
                    String nci = (String) p ; 
                    s.ReservedLoc(nci);
                    break;
                
                case 4 : 
                    String ref;
                        System.out.println("Entrer la référence du local : ");
                        p = scanner.nextLine();
                    ref = (String) p ;
                    System.out.println(s.showLoc(ref));
                    break;
                case 5 : 
                    Object d; 
                    String email; 
                    String Nci; 
                    String etat; 
                    String Ref; 
                    Client cli; 
                    do { 
                        System.out.println("Veuillez saisir le nci:"); 
                        Nci =scanner.nextLine(); 
                    } while (!v.isNci(Nci)); 
                    if(s.searchClient(Nci)){
                        cli=s.getClientByNci(Nci); 
                    }else{
                        System.out.println("Veuillez saisir le nom complet:"); 
                        String nomC =scanner.nextLine(); 
                        do { 
                            do {
                                System.out.println("Veuillez saisir le numero de telephone:"); 
                                d = scanner.nextLine();
                            } while (!v.isPositive(d)); 
                        } while(!v.isTel(Integer.parseInt((String) d))); 
                        int numero=Integer.parseInt((String) d);
                        System.out.println("Veuillez saisir l'adresse:"); 
                        String address =scanner.nextLine(); 
                        do { 
                            System.out.println("Veuillez saisir le mail:");
                            email = scanner.nextLine(); 
                        } while (!v.isMail(email)); 
                        cli=new Client(Nci,nomC,numero,address,email); 
                    }
                        do {
                            System.out.println("Veuillez saisir la durée de réservation (en nombre de mois): "); 
                            d= scanner.nextLine(); 
                        } while (!v.isPositive(d)); 
                        int duree =Integer.parseInt((String) d); 
                        do { 
                            do { 
                                System.out.println("Veuillez choisir l'état de la réservation: \n1-validé \n2-En cours"); 
                                d = scanner.nextLine(); 
                            } while (!v.isPositive(d)); 
                        } while(Integer.parseInt((String) d) != 1 && Integer.parseInt((String) d) != 2);
                        if (Integer.parseInt((String) d) == 1) { 
                            etat = "validé"; 
                        } else {  
                            etat ="en cours"; 
                        } 
                        System.out.println("Liste de locaux disonibles:");
                        s.ListerLocauxDispo(); 
                        do {
                            System.out.println("Veuillez saisir la référence d'un local à réserver"); 
                            Ref= scanner.nextLine();
                        } while (s.findLocByRef(Ref)==null);
                        //Client cltt= s.getClientByNci(Nci); 
                        Local local=s.findLocByRef(Ref); 
                        Reservation reservation = new Reservation(LocalDate.now(), duree, etat, local,cli); //save reservation
                        s.addClient(cli); 
                        s.MakeReserv(reservation); 
                        System.out.println("Local réservé");
                    

                        break;
                case 6 : 
                Object id; 
                    do {
                        System.out.println("Veuillez saisir l'id de la réservation a supprimé:"); 
                        id= scanner.nextLine(); 
                    } while (!v.isPositive(id)); 
                    int Id=Integer.parseInt((String) id); 
                    System.out.println(s.cancelReserv(Id)); 
                    break;
                case 7 : 
                    s.ListerLocauxDispo();

                    break;
                case 8 : 
                
                System.out.println("A trés bientot");
                    break;
                default: System.out.println("Mauvais choix");
                    break;
            }

        } while ( choix != 8);
    }
    public static int menu()
    {
        Object co;//choix
        System.out.println("Menu"
        + "\n  1 - Ajouter un local"
        + "\n  2 - Lister les locaux par type"
        + "\n  3 - Lister les locaux réservé par client"
        + "\n  4 - Voir les détails d'un local"
        + "\n  5 - Faire une réservation"
        + "\n  6 - Annuler une réservation"
        + "\n  7 - Lister les locaux disponibles"
        + "\n  8 - Quitter"
        );
        do {
            System.out.println("Faites votre choix");
            co = scanner.nextLine();
        } while (!v.isPositive(co));
        int choix=Integer.parseInt((String) co);
        return choix;
    }
}



