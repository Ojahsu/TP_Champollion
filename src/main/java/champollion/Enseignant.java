package champollion;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Un enseignant est caractérisé par les informations suivantes : son nom, son adresse email, et son service prévu,
 * et son emploi du temps.
 */
public class Enseignant extends Personne {

    public Map<UE, ServicePrevu> listeCours;
    public Map<UE, LinkedList<Intervention>> interventions;

    public Enseignant(String nom, String email) {
        super(nom, email);
        this.listeCours = new HashMap<>();
        this.interventions = new HashMap<>();
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant en "heures équivalent TD" Pour le calcul : 1 heure
     * de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut 0,75h
     * "équivalent TD"
     *
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevues() {
        long total = 0;
        for (ServicePrevu service : listeCours.values()) {
            total += service.volumeCM * 1.5 + service.volumeTD + service.volumeTP * 0.75;
        }
        return Math.round(total);
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant dans l'UE spécifiée en "heures équivalent TD" Pour
     * le calcul : 1 heure de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure
     * de TP vaut 0,75h "équivalent TD"
     *
     * @param ue l'UE concernée
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevuesPourUE(UE ue) {
        if (this.listeCours.containsKey(ue)){
            return (int) Math.round(listeCours.get(ue).volumeCM * 1.5 + listeCours.get(ue).volumeTD + listeCours.get(ue).volumeTP * 0.75);
        }
        return 0;
    }


    /**
     * Ajoute un enseignement au service prévu pour cet enseignant
     *
     * @param ue l'UE concernée
     * @param volumeCM le volume d'heures de cours magistral
     * @param volumeTD le volume d'heures de TD
     * @param volumeTP le volume d'heures de TP
     */
    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        if (!this.listeCours.containsKey(ue)) {
            this.listeCours.put(ue, new ServicePrevu(volumeCM, volumeTD, volumeTP));
        } else {
            this.listeCours.get(ue).volumeCM += volumeCM;
            this.listeCours.get(ue).volumeTD += volumeTD;
            this.listeCours.get(ue).volumeTP += volumeTP;
        }
    }


    public void ajouteIntervention(Intervention i) throws ExceptionServiceDepasse {
        if(!this.interventions.containsKey(i.ue)){
            this.interventions.put(i.ue, new LinkedList<>());
            this.interventions.get(i.ue).add(i);
        }
        else {
            this.interventions.get(i.ue).add(i);
            float total = 0;
            for (Intervention intervention : this.interventions.get(i.ue)){
                switch (i.type){
                    case CM:
                        total += intervention.duree * 1.5;
                        break;
                    case TD:
                        total += intervention.duree;
                        break;
                    case TP:
                        total += intervention.duree * 0.75;
                        break;
                }
            }
            if (total > this.heuresPrevuesPourUE(i.ue)){
                this.interventions.get(i.ue).remove(this.interventions.get(i.ue).getLast());
                throw new ExceptionServiceDepasse(i.ue);
            }
        }
    }

}
