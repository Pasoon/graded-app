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
    private String letterGrade;
    private RealmList<Deliverable> assignments = new RealmList<>();
    private RealmList<Deliverable> labs = new RealmList<>();
    private RealmList<Deliverable> tests = new RealmList<>();
    private double assignmentsgrade;
    private double labsgrade;
    private double testsgrade;
    private double totalweight;
    private double grade;



    public double getTotalWeight(){
        return totalweight*100;
    }
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
        this.setLetterGrade();
    }

    public void setLetterGrade(){
        if(grade > 0 && grade < 50){
            letterGrade = "F";
        }

        else if(grade >= 50 && grade < 60){
            if(grade >= 50 && grade <= 54){
                letterGrade = "D";
            }
            else{
                letterGrade = "D+";
            }
        }

        else if(grade >= 60 && grade < 70){
            if(grade >= 60 && grade <= 64){
                letterGrade = "C";
            }
            else{
                letterGrade = "C+";
            }
        }

        else if(grade >= 70 && grade < 80){
            if(grade >= 70 && grade <= 74){
                letterGrade = "B";
            }
            else{
                letterGrade = "B+";
            }
        }

        else if(grade >= 80 && grade <=100){
            if(grade >= 80 && grade <= 84){
                letterGrade = "A-";
            }
            else if(grade >= 85 && grade <= 89){
                letterGrade = "A";
            }
            else{
                letterGrade = "A+";
            }
        }

    }

    public String getLetterGrade(){
        return letterGrade;
    }

    public String getGradeColor(){
        if(grade > 0 && grade < 50){
            return "grade_f";
        }

        else if(grade >= 50 && grade < 60){
            return "grade_d";
        }

        else if(grade >= 60 && grade < 70){
            return "grade_c";
        }

        else if(grade >= 70 && grade < 80){
            return "grade_b";
        }

        else if(grade >= 80 && grade <=100){
            return "grade_a";
            }

        return "";
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
