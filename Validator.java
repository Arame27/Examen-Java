public class Validator {

    private final int FORMAT = 4;
    private static int increment;


    public boolean isMail(String email){
        return email.matches(".+@.+\\..+");
    }
    
    public boolean isNci(String nci){
        //ecrit le code la //taille = 17  chiffre 1 ou 2 
        //String Nci=String.valueOf(nci);
        if(nci.length()==17 && (nci.charAt(0)=='1'|| nci.charAt(0) == '2')){
            return true;
        }
        return false;
    }

    public boolean isPositive(int val){
        return val>0;
    }

    public boolean isTaux(int taux){
        return taux<100 && taux>=0;
    }

    public boolean isTel(int tel){
        // numéro de tel taille = 9 et doit commencer soit par 77 ou 76 ou 70 ou 75 ou 33

        //String tele=String.valueOf(tel);
        String T= String.valueOf(tel);
        if(T.length()==9 &&  (T.charAt(0)=='7'|| T.substring(0, 1) == "33")){
            return true;
        }
        return false;
        //return tele.length()==9 && (tele.substring(0,1)=="77" || tele.substring(0, 1)=="76" || tele.substring(0, 1)=="70" || tele.substring(0, 1)=="33" || tele.substring(0, 1)=="75");
    }

    public String generateRef(){
        String nbCompte= String.valueOf(++increment);
        String nbreZero="";
        for(int i=1; i<=(FORMAT-nbCompte.length());i++){
            nbreZero += "0";
        }
        return "Ref" + nbreZero + String.valueOf(nbCompte);
    }

    public boolean isPositive(Object value){
        try{
            boolean v = value instanceof Integer;
            int i = Integer.parseInt((String) value);
            return v || i >=0;
        }catch(Exception e){
            return false;
        }
    }

    public boolean isNci(Object p) {
        return false;
    }
}
