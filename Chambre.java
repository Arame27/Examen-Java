public class Chambre extends Local {
    private int dimension;

    public int getDimension(){
        return dimension;
    }

    public void setDimension(int dimension){
        this.dimension = dimension;
    }

    public Chambre(String ref,int prix,String localisation,int tauxLoc,int dimension){
        super(ref,prix,localisation,tauxLoc);
        this.setDimension(dimension);
        this.type = "Chambre";
    } 

    public Chambre(int dimension){
        super();
        this.setDimension(dimension);
        this.type="Chambre";
    }


    public Chambre(int prix, String localisation, int taux, int dimension) {
        super(prix,localisation,taux);
        this.setDimension(dimension);
        this.type = "Chambre";
    }

    @Override
    public String affiche(){
        return super.affiche()
        +"\n Dimension "+this.getDimension();
    }
}
