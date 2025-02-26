package htl.teddy_com.api.students;

import htl.teddy_com.api.klassen.Klasse;

public class Students {
    private String name;
    private int age;
    private int index_number;
    private Klasse klasse;

    public Students(String name, int index_number,int age, Klasse klasse){
        this.name = name;
        this.index_number = index_number;
        this.age = age;
        this.klasse = klasse;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getIndex_number() {
        return index_number;
    }

    public Klasse getKlasse() {
        return klasse;
    }
}
