package htl.teddy_com.api.students;

import htl.teddy_com.Sanitra;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentManager {
    private final HashMap<Class<? extends Students>, Students> students = new HashMap<>();

    public StudentManager(){
    }

    private void addStudent(final Students student) {
        this.students.put(student.getClass(), student);
    }

    public ArrayList<Students> getStudents() {
        return new ArrayList<>(this.students.values());
    }

    public Students getStudent(final String name) {
        return this.students.values().stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void getStudentss(){
        System.out.println("List of Students:");
        for (Students students : Sanitra.instance.sm.getStudents()){
            System.out.printf("\t%s\n", students.getName());
        }
    }

}
