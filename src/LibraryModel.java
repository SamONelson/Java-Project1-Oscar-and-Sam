import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.*;

/**
 * Name: Sam Nelson
 * Date: Aug 1, 2018
 * Purpose: 
 * Student Number 0785337
 */

/**
 * Program Name: Project 1 Coder: Sam Date: Aug 1, 2018 Purpose:
 */
public class LibraryModel {
	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRslt = null;
	private String sqlQuery = "";
	private ArrayList<String> items;
	private TableModel tableModel;

	public void ListPanelsqlStatement(int decision, String category) {
		String First = "", Last = "";
		if (category.contains(" ")) {
			for (int i = 0; i < category.length(); i++) {
				if (category.charAt(i) == ' ') {
					Last = category.substring(0, i);
					First = category.substring(i + 1);
				}
			}
		}
//		cb_PrepSQL.addItem("All Books In Library");
//		cb_PrepSQL.addItem("Books Out on loan");
//		cb_PrepSQL.addItem("Books on subject");
//		cb_PrepSQL.addItem("Books by Author");
//		cb_PrepSQL.addItem("All Borrowers");
//		cb_PrepSQL.addItem("Overdue books");
		switch (decision) {
		case 0:
			sqlQuery = "SELECT Title, ISBN, Edition_Number AS 'Edition', Subject FROM BOOK;";
			break;
		case 1:
			sqlQuery = "SELECT Title AS 'Title', First_Name AS 'First Name', Last_Name AS 'Last Name' "
					+ "FROM BOOK AS B INNER JOIN Book_Loan AS BL ON B.BookID = BL.BOOK_BOOKID "
					+ "INNER JOIN Borrower AS BO ON BL.Borrower_Borrower_ID = BO.Borrower_ID "
					+ "WHERE B.Available = 0;";
			break;
		case 2:
			sqlQuery = "SELECT Title, ISBN, Edition_Number AS 'Edition', Subject FROM BOOK WHERE SUBJECT = '" + category
					+ "';";
			break;
		case 3:
			sqlQuery = "SELECT Title, ISBN, Edition_Number AS 'Edition', Subject, a.First_Name, a.Last_name from book AS b INNER JOIN book_author AS ba ON b.BookID = ba.Book_BookID "
					+ "INNER JOIN author AS a ON a.AuthorId = ba.Author_AuthorID " + "WHERE a.Last_Name = '" + Last
					+ "' AND a.First_Name = '" + First + "';";
			break;
		case 4:
			sqlQuery = "SELECT First_Name AS 'First Name', Last_Name AS 'Last Name' FROM BORROWER;";
			break;
		case 5:
			sqlQuery = "SELECT Title AS 'Title', First_Name AS 'First Name', Last_Name AS 'Last Name' "
					+ "FROM BOOK AS B INNER JOIN BOOK_LOAN AS BL ON b.BookID = BL.Book_BookID "
					+ "INNER JOIN BORROWER AS BO ON BO.Borrower_ID = BL.BORROWER_BORROWER_ID "
					+ "WHERE CURDATE() > BL.Date_Due AND B.Available = 0;";
			break;

		}
	}

	public LibraryModel() {
		String url = "jdbc:mysql://localhost:3306/info5051_books?useSSL=false&allowPublicKeyRetrieval=true";
		String user = "root";
		String password = "password";
		try {
			myConn = DriverManager.getConnection(url, user, password);
			myStmt = myConn.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}

	}

	// method to return authors in a list;
	public ArrayList<String> getAuthors() {
		sqlQuery = "SELECT CONCAT(Last_Name, ' ' ,First_Name) AS 'Full Name' FROM Author;";
		executeQueryReturnArrayList();
		return items;

	}

	// method to return authors in a list;
	public ArrayList<String> getSubjects() {
		sqlQuery = "SELECT DISTINCT SUBJECT AS 'Subject' FROM BOOK;";
		executeQueryReturnArrayList();
		return items;
	}

	// method to return authors in a list;
	public ArrayList<String> getUsers() {
		sqlQuery = "SELECT CONCAT(Last_Name, ' ' ,First_Name) AS 'Full Name' FROM Borrower;";
		executeQueryReturnArrayList();
		return items;

	}
	
	public ArrayList<String> getUserByName(String fname, String lname) {
		sqlQuery = "SELECT Last_Name, First_Name, Email FROM Borrower WHERE First_Name = '"+ fname +"' AND Last_Name = '"+ lname +"';";
		executeQueryReturnArrayList();
		return items;

	}

	// method to return authors in a list;
	public ArrayList<String> getBooks(boolean OnlyAvailable) {
		if (OnlyAvailable)
			sqlQuery = "SELECT TITLE FROM BOOK WHERE AVAILABLE = 1;";
		else
			sqlQuery = "SELECT TITLE FROM BOOK WHERE AVAILABLE = 0;";
		executeQueryReturnArrayList();
		return items;
	}

	public TableModel getTable() {
		executeQueryReturnTable();
		return tableModel;
	}

	public void AddBook(ArrayList<String> book) {
		executeQueryAddBook(book);
		executeQueryAddAuthor(book);
		executeQueryAddAuthorBook(book);
	}

	private void executeQueryAddAuthorBook(ArrayList<String> book) {
		ArrayList<String> First = new ArrayList<String>();
		ArrayList<String> Last = new ArrayList<String>();
		for (int j = 0; j < book.size(); j++) {
			if (book.get(j).contains(" ")) {
				for (int i = 0; i < book.get(j).length(); i++) {
					if (book.get(j).charAt(i) == ' ') {
						Last.add(book.get(j).substring(0, i));
						First.add(book.get(j).substring(i + 1));
					}
				}
			}
		}

		for (int j = 0; j < Last.size(); j++) {
			try {
				sqlQuery = "INSERT INTO BOOK_AUTHOR(BOOK_BOOKID, AUTHOR_AUTHORID) " + "SELECT "
						+ "(SELECT BOOKID FROM BOOK WHERE ISBN = '" + book.get(1) + "'), "
						+ "(SELECT AUTHORID FROM AUTHOR WHERE LAST_NAME = '" + Last.get(j) + "' AND FIRST_NAME = '"
						+ First.get(j) + "');";
				
				myStmt.executeUpdate(sqlQuery);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void executeQueryAddAuthor(ArrayList<String> book) {
		ArrayList<String> currentAuthors = getAuthors();
		for (int i = 4; i < book.size(); i++) {
			for (int j = 0; j < currentAuthors.size() && i < book.size(); j++) {
				if (currentAuthors.get(j).equals(book.get(i))) {
					book.remove(i);
					j = 0;
				}
			}
		}
		ArrayList<String> First = new ArrayList<String>();
		ArrayList<String> Last = new ArrayList<String>();
		for (int j = 4; j < book.size(); j++) {
			if (book.get(j).contains(" ")) {
				for (int i = 0; i < book.get(j).length(); i++) {
					if (book.get(j).charAt(i) == ' ') {
						Last.add(book.get(j).substring(0, i));
						First.add(book.get(j).substring(i + 1));
					}
				}
			}
		}
		for (int i = 0; i < First.size(); i++) {
			sqlQuery = "INSERT INTO author (Last_Name, First_Name) VALUES ('" + Last.get(i) + "','" + First.get(i)
					+ "')";

			try {
				myStmt.executeUpdate(sqlQuery);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void executeQueryAddBook(ArrayList<String> book) {
		try {
			sqlQuery = "INSERT INTO BOOK(Title, ISBN, Edition_Number, Subject) VALUES ('" + book.get(0) + "', '"
					+ book.get(1) + "', '" + book.get(2) + "', '" + book.get(3).toLowerCase() + "');";
			myStmt.executeUpdate(sqlQuery);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

		}
	}

	private void executeQueryReturnTable() {

		try {
			myRslt = myStmt.executeQuery(sqlQuery);

			tableModel = DBUtils.resultSetToTableModel(myRslt);

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

		}

	}

	private void executeQueryReturnArrayList() {

		try {
			myRslt = myStmt.executeQuery(sqlQuery);

			items = DBUtils.resultSetToArrayList(myRslt);

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

		}

	}
}
