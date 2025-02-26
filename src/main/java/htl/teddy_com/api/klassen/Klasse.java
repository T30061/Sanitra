package htl.teddy_com.api.klassen;

public class Klasse {
    private String name;
    private int students;

    public Klasse(String name, int Students){
        this.name = name;
        this.students = Students;
    }

    public String getName() {
        return name;
    }

    public int getStudents() {
        return students;
    }

}
