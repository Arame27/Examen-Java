import java.util.List;
import java.util.ArrayList;

public abstract class Local {
    protected String ref;
    protected String localisation;
    protected int prix;
    protected int tauxLoc;
    protected String type;  
    Validator v = new Validator();

    //Attribut naviguationnel (relation many to one donc : un objet de Appartement )
    private Appartement appartement;

    //Attribut naviguationnel (relation one to many donc : tableau de réservation )
    List<Reservation> reservations = new ArrayList<Reservation>();

    // Constructeur
    public Local(){
        
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public Appartement getAppartement() {
        return appartement;
    }

    public void setAppartement(Appartement appartement) {
        this.appartement = appartement;
        this.appartement.addLocal(this);
    }

    //Getters
    public String getRef(){
        return ref;
    }

    public String getLocalisation(){
        return localisation;
    }

    public int getPrix(){
        return prix;
    }

    public int getTauxLocal(){
        return tauxLoc;
    }

    public String getType(){
        return type;
    }
    

    //Setters

    //public void setRef(String ref){
        //this.ref = ref;  }            ne pas utiliser car générer
    

    public void setLocalisation(String localisation){
        this.localisation = localisation;
    }

    public void setPrix(int prix){
        this.prix = prix;
    }

    public void setTauxLocal(int tauxLoc){
        this.tauxLoc=tauxLoc;
    }

    //Constructeurs surchargés

    public Local(int prix,int tauxLoc){
        this.ref = v.generateRef();
        setPrix(prix);
        setTauxLocal(tauxLoc);
    }

    public Local(int prix,int tauxLoc,String localisation){
        this.ref = v.generateRef();
        setPrix(prix);
        setTauxLocal(tauxLoc);
        setLocalisation(localisation);
    }

    public Local(int prix,int tauxLoc,String localisation,Appartement appartement){
        this.ref = v.generateRef();
        setPrix(prix);
        setTauxLocal(tauxLoc);
        setLocalisation(localisation);
        setAppartement(appartement);
    }

    public Local(int prix,int tauxLoc,String localisation,Appartement appartement,Reservation reservation){
        this.ref = v.generateRef();
        setPrix(prix);
        setTauxLocal(tauxLoc);
        setLocalisation(localisation);
        setAppartement(appartement);
        addReservation(reservation);
    }

    public Local(String ref, int prix, String localisation, int tauxLoc) {
        this.ref = v.generateRef();
        setPrix(prix);
        setTauxLocal(tauxLoc);
        setLocalisation(localisation);
        setRef(ref);
    }

    public Local(int prix, String localisation, int taux) {
        this.ref = v.generateRef();
        setPrix(prix);
        setTauxLocal(taux);
        setLocalisation(localisation);
    }

    private void setRef(String ref) {
        this.ref=ref;
    }

    public String affiche(){
        return"\n ref "+this.ref
        +"\n localisation: "+this.localisation
        +"\n Prix : "+this.prix
        +"\n TauxLoc : "+this.tauxLoc;
    }

    public Float cout(){
        return (float) (this.getPrix() + this.getTauxLocal() * this.getPrix());
    }


}