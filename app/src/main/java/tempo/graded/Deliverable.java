package tempo.graded;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Pasoon on 2017-07-12.
 */

public class Deliverable extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;

    private String type;
    private double weight;
    private double grade;

    public Deliverable(String name, double weight, String type) {
        this.name = name;
        this.weight = weight;
        this.type = type;
        grade = 0;
    }

    public Deliverable(){
        grade = 0;
    }

    public long getID() {
        return id;
    }

    public void setID(long id){ this.id = id; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName(){ return name; }

    public void setName(String name){ this.name=name; }

    public double getWeight(){ return weight; }

    public void setWeight(Double weight){ this.weight = weight; }

    public double getGrade() { return grade; }

    public void setGrade(double grade) { this.grade = grade; }
}
