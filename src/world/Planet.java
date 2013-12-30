package world;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class Planet {

    public Planet() {
    }

    /**
     * Planet generation routine
     */
    public void create() {

    }

    private int id;

    private Star sun;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Star getSun() {
        return sun;
    }

    public void setSun(Star sun) {
        this.sun = sun;
    }
}
