package champollion;

import java.util.*;

public class Intervention {
    protected Date debut;
    protected int duree;
    protected Salle salleReservee;
    protected boolean annulee = false;
    protected TypeIntervention type;
    protected UE ue;

    public Intervention(Salle salle, UE ue, Date debut, int duree, TypeIntervention type) {
        this.salleReservee = salle;
        this.debut = debut;
        this.duree = duree;
        this.type = type;
        this.ue = ue;
    }

    public String toString() {
        return this.duree + "";
    }
}
