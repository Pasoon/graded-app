package tempo.graded;

import io.realm.RealmObject;

/**
 * Created by Pasoon on 2017-07-12.
 */

public class Deliverable extends RealmObject {

    private String name;
    private double weight;
    private double grade;

    public Deliverable(String name, double weight){
        this.name = name;
        this.weight = weight;
    }

    public Deliverable(){

    }

    public String getName(){ return name; }

    public void setName(String name){ this.name=name; }

    public double getWeight(){ return weight; }

    public void setWeight(Double weight){ this.weight = weight; }

    public double getGrade() { return grade; }

    public void setGrade(double grade) { this.grade = grade; }
}
