package Database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


/**
 * Klasa DatabaseHandler zaduzena je za stvarnje baze i odrzavanje iste
 * Koristim Derby bazu koja se kreira samo lokalno.
 * 
 * @author Bajlo
 *
 */

public class DatabaseHandler {
	
	
	
	private static DatabaseHandler handler = null;
	
	private static final String DB_URL = "jdbc:derby:database;create=true" ;
	public static Connection conn = null;
	private static Statement stmt = null;
	
	public DatabaseHandler() {
		
		createConnection();
		setupBookTable();
		setUpMemberTable();
		setUpIssueTable();
		
	}
	
	/**
	 * Stvara konekciju s bazom provjerava dali vec postoji a ako ne postoji baza onda ju kreira
	 * 
	 */
	 private static void createConnection() {
	        try {
	            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
	            conn = DriverManager.getConnection(DB_URL);
	        }
	        catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
	            System.exit(0);
	        }
	    }
	 
	 public static DatabaseHandler getInstance() {
		 
		 if(handler == null) {
			 
			 handler = new DatabaseHandler();
		 }
		 return handler;
	 }
	 
	 /**
	  * Kreiramo bazu podataka za pohranu informacija o knjigama.
	  * 
	  */
	 
	 void setupBookTable() {
		 
		 String TABLE_NAME = "BOOK";	
		 try {
			stmt = conn.createStatement();
			
			DatabaseMetaData dmb = conn.getMetaData();
			ResultSet tables = dmb.getTables(null,null, TABLE_NAME.toUpperCase(), null);
			
			if(tables.next()) {
				System.out.println("Table "+ TABLE_NAME + " alredy exist. Ready!");
			}else {
				stmt.execute("CREATE TABLE " + TABLE_NAME +"("
						+"		id varchar(200) primary key,\n"
						+"		title varchar(200),\n"
						+"		author varchar(200),\n"
						+"		publisher varchar(200),\n"
						+"		isAvail boolean default true" 
						+")");	
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage()+" --- setupDatabase");
			
		}finally {
			
		}
		 
		 
		 
	 }
	 
	 /**
	  * Kreiramo bazu podataka za pohranu informacija o clanovima
	  */
	 
	 void setUpMemberTable() {
		 
		 String TABLE_NAME = "MEMBER";	
		 try {
			stmt = conn.createStatement();
			
			DatabaseMetaData dmb = conn.getMetaData();
			ResultSet tables = dmb.getTables(null,null, TABLE_NAME.toUpperCase(), null);
			
			if(tables.next()) {
				System.out.println("Table "+ TABLE_NAME + " alredy exist. Ready!");
			}else {
				stmt.execute("CREATE TABLE " + TABLE_NAME +"("
						+"		id varchar(200) primary key,\n"
						+"		name varchar(200),\n"
						+"		password varchar(200),\n"
						+"		email varchar(200)"
						+"      usertype varchar(200)"	
						+")");	
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage()+" --- setupDatabase");
			
		}finally {
			
		}
		  
	 }
	 
	 
	 /**
	  * kreiramo bazu podataka za pohranu informacija o posudbi knjige
	  */
	 void setUpIssueTable() {
		 
		 String TABLE_NAME = "ISSUE";	
		 try {
			stmt = conn.createStatement();
			
			DatabaseMetaData dmb = conn.getMetaData();
			ResultSet tables = dmb.getTables(null,null, TABLE_NAME.toUpperCase(), null);
			
			if(tables.next()) {
				System.out.println("Table "+ TABLE_NAME + " alredy exist. Ready!");
			}else {
				stmt.execute("CREATE TABLE " + TABLE_NAME +"("
						+"		bookID varchar(200) primary key,\n"
						+"		memberID varchar(200),\n"
						+"		issueTime timestamp default CURRENT_TIMESTAMP,\n"
						+"		renew_count integer default 0,\n"
						+"		FOREIGN KEY (bookID) REFERENCES BOOK(id),\n"
						+"		FOREIGN KEY (memberID) REFERENCES MEMBER(id)"
						+")");	
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage()+" --- setupDatabase");
			
		}finally {
			
		}
		  
		 
	 }
	 
	 /**
	  * Metoda koja izvlaci podatke iz baze podataka.
	  * 
	  * @param query
	  * @return
	  */
	 
	  public ResultSet execQuery(String query) {
	        ResultSet result;
	        try {
	            stmt = conn.createStatement();
	            result = stmt.executeQuery(query);
	        }
	        catch (SQLException ex) {
	            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
	            return null;
	        }
	        finally {
	        }
	        return result;
	    }
	  
	  /**
	   * Metoda koja sprema podatke u bazu podataka.
	   * 
	   * @param qu
	   * @return
	   */
	   public boolean execAction(String qu) {
	        try {
	            stmt = conn.createStatement();
	            stmt.execute(qu);
	            return true;
	        }
	        catch (SQLException ex) {
	            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
	            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
	            return false;
	        }
	        finally {
	        }
	    }



}
