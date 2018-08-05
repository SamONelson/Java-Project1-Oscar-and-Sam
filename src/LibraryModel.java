import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	PreparedStatement myStmt = null;
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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}

	}

	// method to return authors in a list;
	public ArrayList<String> getAuthors() {
		try {
			sqlQuery = "SELECT CONCAT(Last_Name, ' ' ,First_Name) AS 'Full Name' FROM Author;";
			myStmt = myConn.prepareStatement(sqlQuery);
			executeQueryReturnArrayList();
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return null;

	}

	// method to return authors in a list;
	public ArrayList<String> getSubjects() {
		try {
			sqlQuery = "SELECT DISTINCT SUBJECT AS 'Subject' FROM BOOK;";
			myStmt = myConn.prepareStatement(sqlQuery);
			executeQueryReturnArrayList();
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}

	// method to return users in a list;
	public ArrayList<String> getUsers() {
		try {
			sqlQuery = "SELECT CONCAT(Last_Name, ' ' ,First_Name) AS 'Full Name' FROM Borrower;";
			myStmt = myConn.prepareStatement(sqlQuery);
			executeQueryReturnArrayList();
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}

	public ArrayList<String> getUserByName(String fname, String lname) {
		try {
			sqlQuery = "SELECT * FROM Borrower WHERE First_Name = ? AND Last_Name = ?";
			myStmt = myConn.prepareStatement(sqlQuery);
			myStmt.setString(1, fname);
			myStmt.setString(2, lname);
			executeQueryReturnArrayList();
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}

		return null;
	}

	public void updateUser(String fname, String lname, String email, int ID) {
		try {
			sqlQuery = "UPDATE Borrower SET First_Name = ?, Last_Name = ?, Borrower_email = ? WHERE Borrower_ID = ?;";
			myStmt = myConn.prepareStatement(sqlQuery);
			myStmt.setString(1, fname);
			myStmt.setString(2, lname);
			myStmt.setString(3, email);
			myStmt.setInt(4, ID);
			myStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
	}

	public ArrayList<String> getBookLoanStatus(String title) {
		try {
			sqlQuery = "SELECT CONCAT(Last_Name, ' ' ,First_Name) AS 'Full Name',BL.Date_Out as 'Date Out', BL.Date_Due as 'Date Due' FROM BORROWER AS BO INNER JOIN BOOK_LOAN AS BL ON "
					+ "BO.Borrower_ID = BL.Borrower_Borrower_ID INNER JOIN Book AS B ON B.BookID = BL.Book_BookID "
					+ "WHERE b.Title = ? ORDER BY BL.Date_Due DESC";
			myStmt = myConn.prepareStatement(sqlQuery);
			myStmt.setString(1, title);
			executeQueryReturnArrayList();

			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addUser(String fname, String lname, String email) {
		try {
			sqlQuery = "INSERT INTO Borrower (First_Name, Last_Name, Borrower_email) VALUES(?, ?, ?);";
			myStmt = myConn.prepareStatement(sqlQuery);
			myStmt.setString(1, fname);
			myStmt.setString(2, lname);
			myStmt.setString(3, email);
			myStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
	}

	// method to return authors in a list;
	public ArrayList<String> getBooks(boolean OnlyAvailable) {
		try {
			if (OnlyAvailable)
				sqlQuery = "SELECT TITLE FROM BOOK WHERE AVAILABLE = 1;";
			else
				sqlQuery = "SELECT TITLE FROM BOOK WHERE AVAILABLE = 0;";
			myStmt = myConn.prepareStatement(sqlQuery);
			executeQueryReturnArrayList();
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}

	public TableModel getTable() {
		try {
			myStmt = myConn.prepareStatement(sqlQuery);
			executeQueryReturnTable();
			return tableModel;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}

	public void AddBook(ArrayList<String> book) {
		executeQueryAddBook(book);
		executeQueryAddAuthor(book);
		executeQueryAddAuthorBook(book);
	}

	public void CheckOutBook(String title, String borrower, int weeks) {
		executeQueryAddBookLoan(title, borrower, weeks);
	}

	public void ReturnBook(String title) {
		executeQueryEndBookLoan(title);
	}

	private void executeQueryGetBookID(String title) {
		try {
			sqlQuery = "SELECT BOOKID FROM BOOK WHERE TITLE = ?";
			myStmt = myConn.prepareStatement(sqlQuery);
			myStmt.setString(1, title);
			executeQueryReturnArrayList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void executeQueryEndBookLoan(String title) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			executeQueryGetBookID(title);
			String bookID = items.get(0);
			String Name = getBookLoanStatus(title).get(0);
			String BorrowerID = getUserByName(seperateSpace(Name, true), seperateSpace(Name, false)).get(0);

			sqlQuery = "UPDATE BOOK_LOAN SET DATE_RETURNED = ? WHERE Book_BookID = ? AND Borrower_Borrower_ID = ?";
			myStmt = myConn.prepareStatement(sqlQuery);
			myStmt.setString(1, dtf.format(now));
			myStmt.setString(2, bookID);
			myStmt.setString(3, BorrowerID);
			myStmt.executeUpdate();

			sqlQuery = "UPDATE BOOK SET Available = 1 WHERE BOOKID = ?";
			myStmt = myConn.prepareStatement(sqlQuery);
			myStmt.setString(1, bookID);
			myStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void executeQueryAddAuthorBook(ArrayList<String> book) {
		ArrayList<String> First = new ArrayList<String>();
		ArrayList<String> Last = new ArrayList<String>();
		for (int j = 4; j < book.size(); j++) {
			Last.add(seperateSpace(book.get(j), false));
			First.add(seperateSpace(book.get(j), true));
		}

		for (int j = 0; j < Last.size(); j++) {
			try {
				sqlQuery = "INSERT INTO BOOK_AUTHOR(BOOK_BOOKID, AUTHOR_AUTHORID) " + "SELECT "
						+ "(SELECT BOOKID FROM BOOK WHERE ISBN = '" + book.get(1) + "'), "
						+ "(SELECT AUTHORID FROM AUTHOR WHERE LAST_NAME = '" + Last.get(j) + "' AND FIRST_NAME = '"
						+ First.get(j) + "');";
				myStmt = myConn.prepareStatement(sqlQuery);
				myStmt.executeUpdate();
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
			Last.add(seperateSpace(book.get(j), false));
			First.add(seperateSpace(book.get(j), true));
		}
		for (int i = 0; i < First.size(); i++) {
			sqlQuery = "INSERT INTO author (Last_Name, First_Name) VALUES ('" + Last.get(i) + "','" + First.get(i)
					+ "')";

			try {
				myStmt = myConn.prepareStatement(sqlQuery);
				myStmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void executeQueryAddBook(ArrayList<String> book) {
		try {
			sqlQuery = "INSERT INTO BOOK(Title, ISBN, Edition_Number, Subject) VALUES (?, ?, ?, ?)";
			myStmt = myConn.prepareStatement(sqlQuery);
			myStmt.setString(1, book.get(0));
			myStmt.setString(2, book.get(1));
			myStmt.setString(3, book.get(2));
			myStmt.setString(4, book.get(3));
			myStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
	}

	private void executeQueryAddBookLoan(String title, String borrower, int weeks) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			sqlQuery = "SELECT BOOKID FROM BOOK WHERE TITLE = ?";
			myStmt = myConn.prepareStatement(sqlQuery);
			myStmt.setString(1, title);
			executeQueryReturnArrayList();
			int bookId = Integer.parseInt(items.get(0));

			sqlQuery = "SELECT BORROWER_ID FROM BORROWER WHERE Last_Name = '" + seperateSpace(borrower, false)
					+ "' AND First_Name = '" + seperateSpace(borrower, true) + "';";
			myStmt = myConn.prepareStatement(sqlQuery);
			executeQueryReturnArrayList();
			int borrowerId = Integer.parseInt(items.get(0));

			sqlQuery = "INSERT INTO BOOK_LOAN (BOOK_BOOKID, BORROWER_BORROWER_ID, COMMENT, DATE_OUT, DATE_DUE) "
					+ "VALUES ('" + bookId + "','" + borrowerId + "', '" + "Borrowed On " + now.getMonth() + " "
					+ now.getDayOfWeek() + " " + now.getDayOfMonth() + "', '" + dtf.format(now) + "', '"
					+ dtf.format(now.plusDays(7 * weeks)) + "')";
			myStmt = myConn.prepareStatement(sqlQuery);
			myStmt.executeUpdate();

			sqlQuery = "UPDATE BOOK SET AVAILABLE = 0 WHERE BOOKID = " + bookId;
			myStmt = myConn.prepareStatement(sqlQuery);
			myStmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

		}
	}

	private void executeQueryReturnTable() {

		try {
			myRslt = myStmt.executeQuery();

			tableModel = DBUtils.resultSetToTableModel(myRslt);

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

		}

	}

	private void executeQueryReturnArrayList() {

		try {
			myRslt = myStmt.executeQuery();

			items = DBUtils.resultSetToArrayList(myRslt);

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

		}
	}

	public String seperateSpace(String toSeperate, boolean rightSide) {
		String last = "";
		String first = "";

		if (toSeperate.contains(" ")) {
			for (int i = 0; i < toSeperate.length(); i++) {
				if (toSeperate.charAt(i) == ' ') {
					first = toSeperate.substring(0, i);
					last = toSeperate.substring(i + 1);
				}
			}
		} else {
			return toSeperate;
		}
		if (rightSide)
			return last;
		return first;
	}
}
