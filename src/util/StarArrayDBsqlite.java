package util;

import java.sql.*;
import world.Star;

class StarArrayDBsqlite {
	private Connection connection;
	
	private StarArray stars;
	
	public StarArrayDBsqlite(StarArray stars) throws ClassNotFoundException, SQLException 
	{
		this.stars = stars;
		Class.forName("org.sqlite.JDBC");
		this.connection = DriverManager.getConnection("jdbc:sqlite:galaxy.db");
		connection.setAutoCommit(false);
	}

	public void load() throws ClassNotFoundException, SQLException
	{
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery("SELECT * FROM stars;");
	    while (rs.next()) {
	    	Star star = new Star();
	    	star.setId(rs.getInt("id"));
	    	star.getLocation().setX(rs.getInt("x"));
	    	star.getLocation().setY(rs.getInt("y"));
	    	star.setTemperature(rs.getInt("temperature"));
	    	star.setName(rs.getString("name"));
	    	star.setState(DBObject.stateTypes.UNCHANGED);
	    	
	    	stars.add(star);
	    }
	    rs.close();
	}
	
	public void save() throws ClassNotFoundException, SQLException
	{
		Statement stat = connection.createStatement();
		stat.executeUpdate("CREATE TABLE IF NOT EXISTS stars (id, x, y, temperature, name);");

		PreparedStatement prepInsert = connection.prepareStatement("INSERT INTO stars VALUES (?, ?, ?, ?, ?);");
		PreparedStatement prepUpdate = connection.prepareStatement("UPDATE stars SET x=?, y=?, temperature=?, name=? WHERE id=?;");
		for (Star star: stars)
		{
			if (star.getState()==DBObject.stateTypes.NEW)
			{
				prepInsert.setInt(1, star.getId());
				prepInsert.setInt(2, star.getLocation().getX());
				prepInsert.setInt(3, star.getLocation().getY());
				prepInsert.setInt(4, star.getTemperature());
				prepInsert.setString(5, star.getName());
				prepInsert.addBatch();
			}
			else if (star.getState()==DBObject.stateTypes.MODIFIED)
			{
				prepUpdate.setInt(1, star.getLocation().getX());
				prepUpdate.setInt(2, star.getLocation().getY());
				prepUpdate.setInt(3, star.getTemperature());
				prepUpdate.setString(4, star.getName());
				prepUpdate.setInt(5, star.getId());
				prepUpdate.addBatch();
			}
		}
		
		prepInsert.executeBatch();
		prepUpdate.executeBatch();
		connection.commit();
	}
	
	public void clear() throws ClassNotFoundException, SQLException
	{
		Statement stat = connection.createStatement();
		stat.execute("DELETE FROM stars;");
	}
}
