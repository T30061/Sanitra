package htl.teddy_com.api.klassen;

import htl.teddy_com.api.klassen.stufen.*;
import java.util.*;

public class KlassenManager {
    private final HashMap<Class<? extends Klasse>, Klasse> Klassen = new HashMap<>();

    public KlassenManager(){
        addKlasse(new BHIT_1());
        addKlasse(new AHIT_1());
    }

    private void addKlasse(final Klasse klassen) {
        this.Klassen.put(klassen.getClass(), klassen);
    }

    public HashMap<Class<? extends Klasse>, Klasse> getKlassen() {
        return Klassen;
    }

    public ArrayList<Klasse> getklasses() {
        return new ArrayList<>(this.Klassen.values());
    }
}
