package util;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import world.Galaxy;
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
	 * @param coord
	 * @return int
	 */
	public int getNearest(Coordinate coord) {
		int nearest = -1;
		double nearestDistance = 1E20;
		
		for (int i=0;i<this.size();i++) {
			double currDistance = get(i).getDistance(coord);
			
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
	 * @param coord
	 * @return double
	 */
	public double getNearestDistance(Coordinate coord) {
		return get(getNearest(coord)).getDistance(coord);
	}
	
	/**
	 * Find and return star by name
	 * 
	 * @param name
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
		Rectangle boundsRect = new Rectangle();

        for (Star star: this) {
			double x = star.getX();
            double y = star.getY();
			
			if ( x < boundsRect.x ) {
				boundsRect.x = (int)x;
            }
			if ( x > boundsRect.x+boundsRect.width ) {
				boundsRect.width = (int)(x-boundsRect.x);
            }
			if ( y < boundsRect.y ) {
				boundsRect.y = (int)y;
            }
			if ( y > boundsRect.y+boundsRect.height ) {
				boundsRect.height = (int)(y-boundsRect.y);
            }
		}

		return boundsRect;
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
        for (Star star: result)
        {
            this.add(star);
        }
        session.getTransaction().commit();
    }
}
