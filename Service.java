import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.*;
import org.json.simple.parser.*;

public class Service {

    protected static List<HashMap<String, Object>> locaux = new ArrayList<HashMap<String, Object>>();
    protected static List<HashMap<String, Object>> clients = new ArrayList<HashMap<String, Object>>();
    protected static List<HashMap<String, Object>> reservations = new ArrayList<HashMap<String, Object>>();

    public HashMap<String, Object> createLoc(Local local, HashMap<String, Object> l) {
        String name = local.getType();
        if (name == "Chambre") {
            l.put("dimension", ((Chambre) local).getDimension());
        } else if (name == "Appartement") {
            List<HashMap<String, Object>> chamb = new ArrayList<HashMap<String, Object>>();
            l.put("nbrePiece", ((Appartement) local).getNbrePiece());
            List<Local> loc = ((Appartement) local).getLocaux();
            if (!loc.isEmpty()) {
                for (Local r : loc) {
                    HashMap<String, Object> c = new HashMap<String, Object>();
                    c.put("type", r.getType());
                    c.put("dimension", ((Chambre) r).getDimension());
                    chamb.add(c);
                    l.put("Pieces", chamb);
                }
            }
        }
        l.put("référence", local.getRef());
        l.put("localisation", local.getLocalisation());
        l.put("prix", local.getPrix());
        l.put("taux", local.getTauxLocal());
        l.put("type", local.getType());
        return l;
    }

    // Ajouter un local
    public void addLocal(Local local) {
        String name = local.getType();
        HashMap<String, Object> l = new HashMap<String, Object>();
        HashMap<String, Object> L = createLoc(local, l);
        HashMap<String, Object> loc = new HashMap<String, Object>();
        loc.put(name, L);
        locaux.add(loc);

        this.saveFile(locaux, "Locaux.json");

    }


    // save in JSON file 
    public void saveFile(List<HashMap<String, Object>> arr, String file) {
        try {
            JSONArray array = new JSONArray(arr);
            FileWriter fil = new FileWriter(file);
            // We can write any JSONArray or JSONObject instance to the file
            fil.append(array.toString());
            fil.flush();
            fil.close();
        } catch (IOException e) {
            System.out.println("");

        }
    }

    // lister local par type  
    @SuppressWarnings("unchecked")
    public void localParType(String type) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("Locaux.json"));
            JSONArray job = (JSONArray) obj;
            for (int i = 0; i < job.size(); i++) {
                HashMap<String, Object> jobj = (HashMap<String, Object>) job.get(i);
                if (jobj.get(type) != null) {
                    HashMap<String, Object> show = (HashMap<String, Object>) jobj.get(type);
                    System.out.println(" type du local: " + show.get("type") + "\n référence: " + show.get("référence")
                            + "\n prix: " + show.get("prix") + "\n localisation: " + show.get("localisation")
                            + "\n taux de location: " + show.get("taux"));
                    if (type.compareTo("Appartement") == 0) {
                        JSONArray piece = (JSONArray) show.get("Pieces");
                        System.out.println(" Il y a " + show.get("nbrePiece") + " nombre de pièces qui sont :");
                        for (int j = 0; j < piece.size(); j++) {
                            HashMap<String, Object> pi = (HashMap<String, Object>) piece.get(j);
                            System.out.println("Chambre " + (j + 1) + "\n dimension:"+pi.get("dimension"));
                        }
                    } else {
                        System.out.println("dimension: " + show.get("dimension"));
                    }
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Aucun local");
        }
    }

    
    @SuppressWarnings("unchecked")
    public Client getClientByNci(String nci) {
        Client client=new Client();
        Reservation reservation;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("Clients.json"));
            JSONArray job = (JSONArray) obj;
            for (int i = 0; i < job.size(); i++) {
                HashMap<String, Object> jobj = (HashMap<String, Object>) job.get(i);
                if (((String)jobj.get("nci")).compareTo(nci)==0) {
                    List<HashMap<String, Object>> show = (List<HashMap<String, Object>>) jobj.get("réservations");
                    client = new Client((String) jobj.get("nci"), (String) jobj.get("nomComplet"),
                            Integer.parseInt(String.valueOf(jobj.get("tel"))), (String) jobj.get("adresse"),
                            (String) jobj.get("email"));
                    for(HashMap<String, Object> r:show){
                        reservation=new Reservation(LocalDate.parse((CharSequence) r.get("date")),Integer.parseInt(String.valueOf(r.get("durée"))),(String) r.get("état"), Integer.parseInt(String.valueOf(r.get("id"))));
                        client.addReservation(reservation);
                    }
                    return client;
                }
            }
            return null;
        } catch (IOException | ParseException e) {
            return null;
        }
    }

    
    public void ReservedLoc(String nci) {
        if (searchClient(nci)) {
            Client client=this.getClientByNci(nci);
            if (!(client.getReservations().isEmpty())) {
                for (Reservation reservation : client.getReservations()) {
                    System.out.println(reservation.affiche());
                }
            } else {
                System.out.println("Aucune réservation effectuée");
            }
        } else {
            System.out.println("client inexistant dans la base");
        }
    }


    @SuppressWarnings("unchecked")
    public Local findLocByRef(String ref) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("Locaux.json"));
            JSONArray job = (JSONArray) obj;
            for (int i = 0; i < job.size(); i++) {
                HashMap<String, Object> jobj = (HashMap<String, Object>) job.get(i);
                if (jobj.get("Chambre") != null) {
                    HashMap<String, Object> show = (HashMap<String, Object>) jobj.get("Chambre");
                    if(((String)show.get("référence")).compareTo(ref)==0){
                        Chambre chambre=new Chambre((String)show.get("référence"),Integer.parseInt(String.valueOf(show.get("prix"))),(String)show.get("localisation"),
                        Integer.parseInt( String.valueOf(show.get("taux"))),Integer.parseInt(String.valueOf(show.get("dimension"))));  
                        return chambre;
                    }
                }else if(jobj.get("Appartement") != null){
                    HashMap<String, Object> show = (HashMap<String, Object>) jobj.get("Appartement");
                    if(((String)show.get("référence")).compareTo(ref)==0){
                        Appartement appartement = new Appartement((String) show.get("référence"),
                                Integer.parseInt(String.valueOf(show.get("prix"))), (String) show.get("localisation"),
                                Integer.parseInt(String.valueOf(show.get("taux"))),
                                Integer.parseInt(String.valueOf(show.get("nbrePiece"))));
                        JSONArray piece = (JSONArray) show.get("Pieces");
                        for (int j = 0; j < piece.size(); j++) {
                            HashMap<String, Object> pi = (HashMap<String, Object>) piece.get(j);
                            Chambre c = new Chambre(Integer.parseInt(String.valueOf(pi.get("dimension"))));
                            appartement.addLocal(c);
                        }
                        return appartement;
                    }
                }
            }
            return null;
        } catch (IOException | ParseException e) {
            return null;
        }
        
    }

    
    public String showLoc(String ref) {
        Local local = findLocByRef(ref);
        if (local != null) {
            return local.affiche();
        }
        return "aucun local du genre existe";
    }

    
    public boolean isReserved(String ref) {
        // comparer les ref
        for (HashMap<String, Object> reser : reservations) {
            if (reser.containsValue(ref)) {
                return true;
            }
        }
        return false;
    }


    public void MakeReserv(Reservation reservation) {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("date", reservation.getDate().toString());
        res.put("duree", reservation.getDuree());
        res.put("etat", reservation.getEtat());
        res.put("id", reservation.getId());
        createLoc(reservation.getLocal(), res);
        createClient(reservation.getClient(), res);

        reservations.add(res);
        this.saveFile(reservations, "Reservations.json");
    }

    
    public String cancelReserv(int id) {
        for (HashMap<String, Object> reser : reservations) {
            int Id = Integer.parseInt((String.valueOf(reser.get("id"))));
            if (Id == id) {
                reservations.remove(reser);
                this.saveFile(reservations, "Reservations.json");
                return "Reservation supprimée";
            }
        }
        return "Aucune reservation n'a été trouvée";

    }

    
    @SuppressWarnings("unchecked")
    public void ListerLocauxDispo() {
        Object type;
        for (HashMap<String, Object> loc :locaux) {
            // recuperer la map du local
            if (loc.containsKey("Chambre")) {
                type = loc.get("Chambre");
            } else {
                type = loc.get("Appartement");
            }
            HashMap<String, Object> Oloc = (HashMap<String, Object>) type;
            if (reservations.isEmpty()) {
                System.out.println("Référence: " + Oloc.get("référence"));
            } else {
                // comparer les ref
                if (!isReserved(Oloc.get("référence").toString())) {
                    System.out.println("Référence: " + Oloc.get("référence"));
                }
            }

        }
    }

    

    public boolean searchClient(String nci) {
        JSONParser parser = new JSONParser(); 
        try { 
            Object obj = parser.parse(new FileReader("Clients.json")); 
            JSONArray job = (JSONArray) obj;
            if(!job.isEmpty()){ 
                for (int i = 0; i < job.size(); i++) { 
                    JSONObject jobj =(JSONObject) job.get(i); 
                    if (jobj.containsValue(nci)) { 
                        return true; 
                    } 
                } 
            } 
        }
        catch (IOException | ParseException e) { 
            return false; 
        } 
        return false;
    }

    // Creer un client  
    public HashMap<String, Object> createClient(Client client, HashMap<String, Object> cli) {
        cli.put("nci", client.getNci());
        cli.put("nomComplet", client.getNomComplet());
        cli.put("tel", client.getTel());
        cli.put("adresse", client.getAdresse());
        cli.put("email", client.getEmail());
        return cli;
    }

    // Ajouter un client 
    public void addClient(Client client) {
        HashMap<String, Object> cli = new HashMap<String, Object>();
        HashMap<String, Object> C = createClient(client, cli);
        List<Reservation> reservation = client.getReservations();
        if (!reservation.isEmpty()) {
            List<HashMap<String, Object>> reser = new ArrayList<HashMap<String, Object>>();
            for (Reservation res : reservation) {
                HashMap<String, Object> r = new HashMap<String, Object>();
                r.put("date", res.getDate().toString());
                r.put("durée", res.getDuree());
                r.put("état", res.getEtat());
                r.put("id", res.getId());
                reser.add(r);
            }
            C.put("réservations", reser);
        }
        clients.add(C);
        this.saveFile(clients, "Clients.json");
    }

    
    @SuppressWarnings("unchecked")
    public void initList(){
        JSONParser parser = new JSONParser();
        JSONParser parser1 = new JSONParser();
        JSONParser parser2 = new JSONParser();
        
        try {
            //recuperer Json en objects
            Object loc = parser.parse(new FileReader("Locaux.json"));
            //transformer en jsonArray
            JSONArray job = (JSONArray) loc;
            
            //remplir locaux
            for (int i = 0; i < job.size(); i++) {
                HashMap<String, Object> local = (HashMap<String, Object>) job.get(i);
                locaux.add(local);
            }
        } catch (IOException | ParseException e) {
            return ;
            //e.printStackTrace();
        }

        try
        {
            // recuperer Json en objects
            Object clt = parser1.parse(new FileReader("Clients.json"));

            // transformer en jsonArray
            JSONArray job1 = (JSONArray) clt;
            
            // remplir client
            for (int j = 0; j < job1.size(); j++) {
                HashMap<String, Object> client = (HashMap<String, Object>) job1.get(j);
                clients.add(client);
            }
        }catch(IOException|
        ParseException e)
        {
            return;
            // e.printStackTrace();
        }

        try {
            //recuperer Json en objects
            Object reserv = parser2.parse(new FileReader("Reservations.json"));
            //transformer en jsonArray
            JSONArray job2 = (JSONArray) reserv;
            //remplir reservations
            for (int k = 0; k < job2.size(); k++) {
                HashMap<String, Object> reservation = (HashMap<String, Object>) job2.get(k);
                reservations.add(reservation);
            }
        } catch (IOException | ParseException e) {
            return ;
            //e.printStackTrace();
        }
    }
}

