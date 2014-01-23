package util;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import world.Planet;
import world.Star;

public class StarArray extends ArrayList<Star> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8039273398434330805L;

	/**
	 * Find nearest star to given coordinate
	 * 
	 * @param coordinate Coordinate
	 * @return int
	 */
	public int getNearest(Coordinate coordinate) {
		int nearest = -1;
		double nearestDistance = 1E20;
		
		for (int i=0;i<this.size();i++) {
			double currDistance = get(i).getDistance(coordinate);
			
			if (currDistance < nearestDistance) {
				nearest = i;
				nearestDistance = currDistance;
			}
		}
		
		return nearest;
	}

	/**
	 * Find distance from given coordinate to nearest star  
	 * 
	 * @param coordinate Coordinate
	 * @return double
	 */
	public double getNearestDistance(Coordinate coordinate) {
		return get(getNearest(coordinate)).getDistance(coordinate);
	}
	
	/**
	 * Find and return star by name
	 * 
	 * @param name String
	 * @return Star
	 */
	public Star getByName(String name) {
		for (Star star: this) {
			if (star.getName().equals(name))
				return star;
		}
		
		return null;
	}
	
	public Rectangle getBoundsRectangle() {
        int maxX = 0, minX = 0, maxY = 0, minY = 0;
        for (Star star: this) {
			int x = (int)star.getX();
            int y = (int)star.getY();

            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
		}

		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}

    public void save() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        for ( Star star: this ) {
            session.save(star);
            for ( Planet planet: star.getPlanets() ) {
                session.save(planet);
            }
        }
        session.getTransaction().commit();
    }

    public void load() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Star> result = session.createQuery("FROM Star").list();
        for (Star star: result) {
            this.add(star);
        }
        session.getTransaction().commit();
    }
}
