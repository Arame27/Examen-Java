import java.util.List;
import java.util.ArrayList;


public class Client  {
    private String nci;
    private String nomComplet;
    private int tel;
    private String adresse;
    private String email;

    //Attribut naviguationnel (relation one to many donc : tableau de réservation )
    List<Reservation> reservations = new ArrayList<Reservation>();


    public Client(){

    }


    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getAdresse() {
        return adresse;
    }


    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    public int getTel() {
        return tel;
    }


    public void setTel(int tel) {
        this.tel = tel;
    }


    public String getNomComplet() {
        return nomComplet;
    }


    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }


    public String getNci() {
        return nci;
    }


    public void setNci(String nci) {
        this.nci = nci;
    }

    public Client(String email,String nomComplet,String adresse){
        setEmail(email);
        setNomComplet(nomComplet);
        setAdresse(adresse);
    }

    public Client(String nci,String nomComplet,int tel,String adresse,String email){
        setEmail(email);
        setNomComplet(nomComplet);
        setAdresse(adresse);
        setNci(nci);
        setTel(tel);
    }



    
}
