package tempo.graded;

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
    private RealmList<Deliverable> assignments = new RealmList<>();
    private RealmList<Deliverable> labs = new RealmList<>();
    private RealmList<Deliverable> tests = new RealmList<>();
    private double assignmentsgrade;
    private double labsgrade;
    private double testsgrade;
    private double totalweight;
    private double grade;



    public void calculateGrade(){

        assignmentsgrade = 0;
        labsgrade = 0;
        testsgrade = 0;
        totalweight = 0;
        grade = 0;
        for (Deliverable deliverable : assignments) {
            if(deliverable.getGrade() != 0){
                assignmentsgrade = assignmentsgrade + (deliverable.getGrade()/100 * deliverable.getWeight()/100);
                totalweight = totalweight + deliverable.getWeight()/100;
            }
        }

        for (Deliverable deliverable : labs) {
            if(deliverable.getGrade() != 0){
                labsgrade = labsgrade + (deliverable.getGrade()/100 * deliverable.getWeight()/100);
                totalweight = totalweight + deliverable.getWeight()/100;
            }
        }

        for (Deliverable deliverable : tests) {
            if(deliverable.getGrade() != 0){
                testsgrade = testsgrade + (deliverable.getGrade()/100 * deliverable.getWeight()/100);
                totalweight = totalweight + deliverable.getWeight()/100;
            }
        }

        grade = (assignmentsgrade + labsgrade + testsgrade)/totalweight;
        grade = grade * 100;
        this.setGrade(grade);
    }

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

    public void setCourseCode(String courseCode){ this.courseCode = courseCode; }

    public RealmList<Deliverable> getAssignments(){
        return assignments;
    }

    public RealmList<Deliverable> getLabs(){
        return labs;
    }

    public RealmList<Deliverable> getTests(){
        return tests;
    }

}
