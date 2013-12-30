package world;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Created by Basil on 30.12.13.
 */
@Entity
public class Planet {

    public Planet() {
    }

    /**
     * Planet generation routine
     */
    public void create() {

    }

    @GeneratedValue
    @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
