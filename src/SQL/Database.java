package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Main.Game;

public class Database {
	private Game game;
	static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/gamejava?serverTimezone=UTC";
	static final String USER = "root";
	static final String PASS = "";
	public Database(Game game) {
		this.game=game;
	}
	public void setDataForGame() throws SQLException, ClassNotFoundException {
			Connection conn = null;
			Statement stmt = null;
			try {
			System.out.println("STEP 1: Register JDBC driver");
			Class.forName(DRIVER_CLASS);
		
			System.out.println("STEP 2: Open a connection");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		
			System.out.println("STEP 3: Execute a query");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM object ORDER BY id DESC LIMIT 1");
			System.out.println("STEP 4: Extract data from result set");
			if (rs.next()) {
			int id=rs.getInt("id");
			float x=rs.getFloat("lastX");
			float y=rs.getFloat("lastY");
			game.getContinue().getPlayer().updateHitBox(x, y);
			game.getContinue().getPlayer().setCurrentHealth(rs.getInt("lastCurrentHealth"));
			game.getContinue().getPlayer().setStatusIndex(rs.getInt("lastStatus"));
			game.getContinue().getLevelManager().setLevelIndex(rs.getInt("lastLeveIndex"));
			game.getContinue().getLevelManager().updateLvel();
			}
			rs.close();
			}catch (SQLException e) {
				throw e;
			} finally {
			System.out.println("STEP 5: Close connection");
			if (stmt != null)
			stmt.close();
			if (conn != null)
			conn.close();
			} 
			System.out.println("Done!");
	}
	public void collectDataForGame() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		Statement stmt = null;
		try {
		System.out.println("STEP 1: Register JDBC driver");
		Class.forName(DRIVER_CLASS);
	
		System.out.println("STEP 2: Open a connection");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
		System.out.println("STEP 3: Execute a query");
		stmt = conn.createStatement();
		float lastX=game.getContinue().getPlayer().getHitBox().x;
		float lastY=game.getContinue().getPlayer().getHitBox().y;
		int lastCurrentHealth= game.getContinue().getPlayer().getCurrentHealth();
		
		int lastStatus= game.getContinue().getPlayer().getStatus();
		int lastLevelIndex= game.getContinue().getLevelManager().getLevelIndex();
		String sql = "INSERT INTO object (lastX, lastY, lastCurrentHealth, lastStatus, lastLeveIndex) VALUES ('" + lastX + "','" + lastY + "'," + lastCurrentHealth + "," + lastStatus + "," + lastLevelIndex + ")";
        stmt.executeUpdate(sql);
        System.out.println("Data inserted successfully");
		}catch (SQLException e) {
			throw e;
		} finally {
		System.out.println("STEP 5: Close connection");
		if (stmt != null)
		stmt.close();
		if (conn != null)
		conn.close();
		} 
		System.out.println("Done!");
}
}
