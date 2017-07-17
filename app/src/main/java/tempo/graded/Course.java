package tempo.graded;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Pasoon on 2017-07-12.
 */

public class Course extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;

    private String courseCode;
    private RealmList<Deliverable> assignments;
    private RealmList<Deliverable> labs;
    private RealmList<Deliverable> tests;
    private Deliverable finalExam;
    private double grade;

    public double getGrade() { return grade; }

    public void setGrade(double grade) { this.grade = grade; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getID() {
        return id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public List getAssignments() {
        return assignments;
    }

    public List getLabs() {
        return labs;
    }

    public List getTests() {
        return tests;
    }

    public Deliverable getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(Deliverable finalExam) {
        this.finalExam = finalExam;
    }
}
