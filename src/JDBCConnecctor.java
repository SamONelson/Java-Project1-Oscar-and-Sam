//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import javax.swing.table.TableModel;
//
///**
// * Name: Sam Nelson
// * Date: Jul 25, 2018
// * Purpose: 
// * Student Number 0785337
// */
//
///**
// * Program Name: Movie Database Viewer
// * Coder: Oscar and Sam
// * Date: Jul 25, 2018
// * Purpose: 
// */
//public class JDBCConnecctor {
//	public static void main(String [] args) {
//		// connect to world database jdbc:mysql://localhost:3306/world?allowPublicKeyRetrieval=true&useSSL=false
//		String url = "jdbc:mysql://localhost:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true";
//		String user = "root";
//		String password = "password";
//		
//		Connection myConn = null;
//		Statement myStmt = null;
//		ResultSet myRslt = null;
//		
//		String last_name = "WAHLBERG";
//		
//		String sqlQuery = "SELECT A.last_name, A.first_name, C.title " +
//				"FROM Actor AS A INNER JOIN Film_Actor AS B ON A.actor_id = B.actor_id " +
//				"INNER JOIN Film AS C on B.film_id = C.film_id " +
//				"WHERE last_name = '" + last_name + "';";
//		
//		
//		try {
//			myConn = DriverManager.getConnection(url, user, password);
//			
//			myStmt = myConn.createStatement();
//			
//			myRslt = myStmt.executeQuery(sqlQuery);
//			
//			TableModel model = DBUtils.resultSetToTableModel(myRslt);
//			
//			// create the JTableViewer
//			MovieDatabaseViewer viewer = new MovieDatabaseViewer(model);
//			
//			
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		}
//	}
//}
