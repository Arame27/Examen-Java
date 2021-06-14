import java.util.ArrayList;
import java.util.List;

public class Appartement extends Local {
    private int nbrePiece;

    //Attribut naviguationnel (relation one to many donc : tableau de local )
   List<Local> locaux = new ArrayList<>();



    public List<Local> getLocaux() {
        return locaux;
    }

    public void addLocal(Local local) {
        locaux.add(local);
    }


    public  int getNbrePiece(){
        return nbrePiece;
    }

    public void setNbrePiece( int nbrePiece){
        this.nbrePiece = nbrePiece;
    }

    public Appartement(int nbrePiece){
        super();
        type = "Appartement";
        setNbrePiece(nbrePiece);

    }    

    public Appartement(int nbrePiece,Local local){
        super();
        type = "Appartement";
        this.setNbrePiece(nbrePiece);
        addLocal(local);
    }  

    public Appartement(String ref, int prix, String localisation, int taux, int nbrePiece) {
        super(ref, prix, localisation, taux);
        this.setNbrePiece(nbrePiece);
        type = "Appartement";
    }

    public Appartement(int prix, String localisation, int taux, int nbrePiece) {
        super(prix, localisation, taux);
        this.setNbrePiece(nbrePiece);
        type = "Appartement";
    }

    @Override
    public String affiche(){
        return super.affiche()
        +"\n nombre de piéces "+this.getNbrePiece();
    }
}
