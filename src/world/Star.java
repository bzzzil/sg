package world;

import util.Coordinate;
import java.awt.*;
import java.util.*;

public class Star {

    private int star_id;

    private int temperature;

    private String name;

    private double x;

    private double y;

    private Set<Planet> planets;

    public int getStar_id() {
        return star_id;
    }
    public void setStar_id(int id) {
        this.star_id = id;
    }

    public int getTemperature() {
        return temperature;
    }
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }

    public Set<Planet> getPlanets() {
        if (planets == null) {
            planets = new HashSet<Planet>();
        }
        return planets;
    }
    public void setPlanets(Set<Planet> planets) {
        this.planets = planets;
    }

    /**
     * Calculate Cartesian distance between current star and give coordinate
     *
     * @param anotherCoordinate Coordinate
     * @return float
     */
    public double getDistance(Coordinate anotherCoordinate)
    {
        return getDistance(anotherCoordinate.getX(), anotherCoordinate.getY());
    }

    /**
     * Calculate Cartesian distance between current star and give coordinate
     *
     * @param x double
     * @param y double
     * @return float
     */
    public double getDistance(double x, double y)
    {
        return Math.sqrt(
                Math.pow(this.getX() - x, 2) +
                        Math.pow(this.getY() - y, 2)
        );
    }

    /**
     * Get star temperature in visual specter for drawing
     *
     * {@link} http://en.wikipedia.org/wiki/Stellar_classification
     *
     * @return int - temperature in Kelvins in visual specter
     */
    public int getTemperatureAsRGB() {
        int temperature = this.getTemperature();
        if (temperature<600)
        {
            // Y class
            // TODO correct color
            return 0xbf2901;	// Brown
        }
        else if (temperature<1300)
        {
            // T class
            // TODO correct color
            return 0xff673e;	// Brown
        }
        else if (temperature<2000)
        {
            // L class
            // TODO correct color
            return 0xfd945b;	// Brown
        }
        else if (temperature<3700)
        {
            // M class
            return 0xffcc6f;	// Orange red
        }
        else if (temperature<5200)
        {
            // K class
            return 0xffd2a1;	// Yellow orange
        }
        else if (temperature<6000)
        {
            // G class
            return 0xfff4ea;	// Yellowish white
        }
        else if (temperature<7500)
        {
            // F class
            return 0xf8f7ff;	// White
        }
        else if (temperature<10000)
        {
            // A class
            return 0xcad7ff;	// White to blue white
        }
        else if (temperature<30000)
        {
            // B class
            return 0xaabfff;	// Blue white
        }
        else
        {
            // O class
            return 0x9bb0ff;	// Blue
        }
    }

    /**
     * Star generation routine
     */
    public void create() {
        // Create planets
        int planetsCount = Galaxy.getInstance().generator.nextInt(10);
        for (int i=0;i<planetsCount;i++)
        {
            Planet currentPlanet = new Planet();

            currentPlanet.create();
            currentPlanet.setId(i);
            currentPlanet.setSun(this);

            this.getPlanets().add(currentPlanet);
        }
    }

    public String toString() {
        return "Star #" + getStar_id()
                + " (" + getX() + ", " + getY() + "), "
                + getName() + ", " + getTemperature() + "K";
    }

    /**
     * draw star into given graphics object
     *
     * @param g2 graphic object draw to
     * @param scale scale level
     * @param x position of star
     * @param y position of star
     * @param showName true if name of star should be displayed near
     * @param showId true if id of star should be displayed near
     * @param isHover true if display mouse over event
     */
    public void draw(Graphics2D g2, double scale, int x, int y, boolean showName, boolean showId, boolean isHover)
    {
        int diameter = (int)scale + 5;
        if (diameter < 1) {
            diameter = 1;
        }

        int star_x = (int)(x + scale * this.getX() - diameter/2);
        int star_y = (int)(y + scale * this.getY() - diameter/2);

        g2.setColor(new Color(getTemperatureAsRGB()));

        g2.fillOval(star_x, star_y, diameter, diameter);

        if (isHover) {
            g2.setColor(Color.white);
        } else {
            g2.setColor(Color.gray);
        }

        int textOffset = 0;
        if (showId)
        {
            textOffset+=12;
            g2.drawString("#" + getStar_id(), star_x + diameter,
                    star_y + diameter + textOffset);
        }

        if (showName || isHover)
        {
            textOffset+=12;
            g2.drawString(getName(), star_x + diameter,
                    star_y + diameter + textOffset);
        }

        if (isHover) {
            g2.setColor(Color.white);
            g2.drawOval(star_x - 2, star_y - 2, diameter + 4,
                    diameter + 4);
        }
    }
}
