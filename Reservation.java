
import java.time.LocalDate;

public class Reservation extends Local {
    private LocalDate date;
    private int duree;
    private String etat;
    private int id;
    private static int increment;

    //Attribut naviguationnel (relation many to one donc : un objet de client )
    private Client client = new Client();

    //Attribut naviguationnel (relation many to one donc : un objet de local )
    private Local local;

    //Constructeur
    public Reservation(){
        id= ++increment;
        setId(id);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        this.client.addReservation(this);
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
        this.local.addReservation(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Reservation(int duree,String etat){
        id = ++increment;
        setId(id);
        setEtat(etat);
        setDuree(duree);
    }

    public Reservation(LocalDate date,int duree,String etat,int id){
        setId(id);
        setEtat(etat);
        setDate(date);
        setDuree(duree);
    }

    public Reservation(String etat,int duree,LocalDate date,Client client){
        id = ++increment;
        setId(id);
        setEtat(etat);
        setDate(date);
        setDuree(duree);
        setClient(client);
    }

    public Reservation(String etat,int duree,LocalDate date,Client client,Local local){
        id = ++increment;
        setId(id);
        setEtat(etat);
        setDate(date);
        setDuree(duree);
        setClient(client);
        setLocal(local);
    }

    public Reservation(LocalDate now, int duree, String etat, Local local, Client cli) {
        id = ++increment;
        setId(id);
        setDate(now);
        setDuree(duree);
        setEtat(etat);
        setLocal(local);
        setClient(cli);
    }

    @Override
    public String affiche(){
        return"\n id "+this.id
        +"\n date: "+this.date
        +"\n durée : "+this.duree
        +"\n état : "+this.etat;
    }
    
}
