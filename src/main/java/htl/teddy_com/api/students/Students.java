package htl.teddy_com.api.students;

public class Students {
    private String vorname, nachname;
    private int note, age;
    private boolean isAbsent;

    public Students(String Vorname, String Nachname, int note, int age){
        this.vorname = Vorname;
        this.nachname = Nachname;
        this.note = note;
        this.age = age;
    }

    public Students(String Vorname, String Nachname,boolean isAbsent){
        this.vorname = Vorname;
        this.nachname = Nachname;
        this.isAbsent = isAbsent;
    }

    public String getName(){
        return vorname + " " + nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }
}
