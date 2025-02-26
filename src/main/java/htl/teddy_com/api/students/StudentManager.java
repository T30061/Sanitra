package htl.teddy_com.api.students;

import htl.teddy_com.Sanitra;
import htl.teddy_com.impl.students._1BHIT.*;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentManager {
    private final HashMap<Class<? extends Students>, Students> students = new HashMap<>();

    public StudentManager(){
        addStudent(new _30061());
        addStudent(new _30065());
    }

    public ArrayList<Students> getStudents() {
        return new ArrayList<>(this.students.values());
    }

    private void addStudent(final Students student) {
        this.students.put(student.getClass(), student);
    }

    public void getStudentss(){
        System.out.println("List of Students:");
        System.out.println("Name | age | Class");
        for (Students students : Sanitra.instance.sm.getStudents()){
            System.out.printf("\t%s | %d | %s\n", students.getName(),students.getAge(),students.getKlasse().getName());
        }
    }
}
