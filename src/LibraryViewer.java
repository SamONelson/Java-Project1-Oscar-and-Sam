import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;
import javax.swing.table.*;

public class LibraryViewer extends JFrame {
	private static final long serialVersionUID = 1L;

	// CardLayout cardLayout;
	private Color bgColor = new Color(102, 51, 153);
	private JTable t_Table;
	private JScrollPane sp_ScrollPane;
	private TableModel tm_TableModel;
	private JButton b_Go, b_Add, b_Clear;
	private JLabel l_PossibleErrors, l_Title, l_ISBN, l_Edition, l_Subject, l_Author, l_FirstName, l_LastName, l_Email,
			l_Date, l_DueDate, l_Book, l_Borrower;
	private JPanel p_AddBook, p_AddUpdateBorrower, p_Checkout, p_Return, p_List;
	private JTextField tf_Author, tf_Title, tf_ISBN, tf_Edition, tf_Subject, tf_FirstName, tf_LastName, tf_Email;

	private JComboBox<String> cb_PrepSQL, cb_Type, cb_User, cb_Books, cb_AuthorList;
	private JTabbedPane tp_Tabs;

	private class tabs {
		static final int LIST = 0;
		static final int ADDBOOK = 1;
		static final int ADDUPDATEBROWSER = 2;
		static final int CHECKOUT = 3;
		static final int RETURN = 4;
//		LIST, ADDBOOK, ADDUPDATEBROWSER, CHECKOUT, RETURN;
	}

	public static void main(String[] args) {
		new LibraryViewer(/* TableModel */);
	}

	public LibraryViewer(/* TableModel tm */) {
		super("Library Management V1.0 Oscar & Sam");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 250);
		this.setLocationRelativeTo(null);
//		this.setResizable(false);

		tp_Tabs = new JTabbedPane();
		p_AddBook = new JPanel();
		p_AddUpdateBorrower = new JPanel();
		p_Checkout = new JPanel();
		p_Return = new JPanel();
		p_List = new JPanel();

		setupList();
		setupAddBook();
		setupAddUpdateBorrower();
		setupCheckout();
		setupReturn();

		tp_Tabs.add("List", p_List);
		tp_Tabs.add("Add Book", p_AddBook);
		tp_Tabs.add("Add/Update Borrower", p_AddUpdateBorrower);
		tp_Tabs.add("Checkout", p_Checkout);
		tp_Tabs.add("Return", p_Return);
		p_AddBook.setBackground(bgColor);
		p_AddUpdateBorrower.setBackground(bgColor);
		p_Checkout.setBackground(bgColor);
		p_Return.setBackground(bgColor);
		p_List.setBackground(bgColor);
		this.add(tp_Tabs);

		this.setVisible(true);
	}

	void setupAddBook() {
		l_Author = new JLabel("Author:");
		l_Title = new JLabel("Title:");
		l_ISBN = new JLabel("ISBN:");
		l_Edition = new JLabel("Edition:");
		l_Subject = new JLabel("Subject:");
		l_PossibleErrors = new JLabel("No Errors YEt!");
		l_PossibleErrors.setForeground(Color.BLUE);

		tf_Author = new JTextField(20);
		tf_Title = new JTextField(10);
		tf_ISBN = new JTextField(10);
		tf_Edition = new JTextField(10);
		tf_Subject = new JTextField(10);

		b_Add = new JButton("Add Author");
		b_Clear = new JButton("Clear Author");
		b_Go = new JButton("Add Book");

		cb_AuthorList = new JComboBox<String>();
		cb_AuthorList.addItem("Authors...");

		p_AddBook.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.05;
		c.weighty = 1;
		c.insets = new Insets(5, 5, 5, 5);

		c.gridx = 0; // aligned with button 2
		c.gridy = 0; // third row
		p_AddBook.add(l_Title, c);
		c.gridx = 0; // aligned with button 2
		c.gridy = 1; // third row
		p_AddBook.add(l_ISBN, c);
		c.gridx = 2; // aligned with button 2
		c.gridy = 0; // third row
		p_AddBook.add(l_Author, c);
		c.gridx = 0; // aligned with button 2
		c.gridwidth = 1; // 2 columns wide
		c.gridy = 2; // third row
		p_AddBook.add(l_Edition, c);
		c.gridx = 0; // aligned with button 2
		c.gridwidth = 1; // 2 columns wide
		c.gridy = 3; // third row
		p_AddBook.add(l_Subject, c);

		c.weightx = 2.0;
		c.gridx = 1; // aligned with button 2
		c.gridy = 0; // third row
		p_AddBook.add(tf_Title, c);
		c.gridx = 3; // aligned with button 2
		c.gridy = 0; // third row
		p_AddBook.add(tf_Author, c);
		c.gridx = 1; // aligned with button 2
		c.gridy = 1; // third row
		p_AddBook.add(tf_ISBN, c);
		c.gridx = 1; // aligned with button 2
		c.gridy = 3; // third row
		p_AddBook.add(tf_Subject, c);
		c.gridx = 1; // aligned with button 2
		c.gridy = 2; // third row
		p_AddBook.add(tf_Edition, c);
		c.gridx = 4; // aligned with button 2
		c.gridy = 0; // third row
		p_AddBook.add(b_Add, c);

		c.gridx = 4; // aligned with button 2
		c.gridwidth = 1; // 2 columns wide
		c.gridy = 1; // third row
		p_AddBook.add(b_Clear, c);

		c.gridwidth = 2; // 2 columns wide
		c.gridx = 2; // aligned with button 2
		c.gridy = 1; // third row
		p_AddBook.add(cb_AuthorList, c);

		c.gridx = 0; // aligned with button 2
		c.gridy = 4; // third row
		p_AddBook.add(b_Go, c);

		c.gridx = 2; // aligned with button 2
		c.gridy = 4; // third row
		p_AddBook.add(l_PossibleErrors, c);

		ListenerForButtons listener = new ListenerForButtons();
		b_Go.addActionListener(listener);
		b_Add.addActionListener(listener);
		b_Clear.addActionListener(listener);

	}

	void setupAddUpdateBorrower() {
		cb_User = new JComboBox<String>();
		l_FirstName = new JLabel("First Name:");
		l_LastName = new JLabel("Last Name:");
		l_Email = new JLabel("Email:");
		l_PossibleErrors = new JLabel("No Errors Yet!");
		tf_FirstName = new JTextField(20);
		tf_LastName = new JTextField(20);
		tf_Email = new JTextField(20);
		b_Go = new JButton("Add User");

		cb_User.addItem("Add New User...");

		JPanel p_Top = new JPanel();
		JPanel p_Mid = new JPanel();
		JPanel p_Bottom = new JPanel();

		p_Top.setBackground(bgColor);
		p_Mid.setBackground(bgColor);
		p_Bottom.setBackground(bgColor);

		p_Top.add(cb_User);
		p_Mid.setLayout(new GridLayout(3, 2, 10, 10));
		p_Mid.add(l_FirstName);
		p_Mid.add(tf_FirstName);
		p_Mid.add(l_LastName);
		p_Mid.add(tf_LastName);
		p_Mid.add(l_Email);
		p_Mid.add(tf_Email);
		p_Bottom.add(b_Go);
		p_Bottom.add(l_PossibleErrors);

		p_AddUpdateBorrower.setLayout(new BorderLayout());
		p_AddUpdateBorrower.add(p_Top, BorderLayout.NORTH);
		p_AddUpdateBorrower.add(p_Mid, BorderLayout.CENTER);
		p_AddUpdateBorrower.add(p_Bottom, BorderLayout.SOUTH);
	}

	void setupCheckout() {
		l_Book = new JLabel("Book:");
		l_Borrower = new JLabel("Borrower:");
		l_Date = new JLabel("Date:");
		l_DueDate = new JLabel("Due Date:");
		cb_Books = new JComboBox<String>();
		cb_User = new JComboBox<String>();
		l_PossibleErrors = new JLabel("No Errors Yet");
		b_Go = new JButton("Checkout");

		cb_Books.addItem("Any Book That is not currently checked out");
		cb_User.addItem("Any User!");

		p_Checkout.setLayout(new GridLayout(5, 2, 10, 10));
		p_Checkout.add(l_Book);
		p_Checkout.add(cb_Books);
		p_Checkout.add(l_Borrower);
		p_Checkout.add(cb_User);
		p_Checkout.add(l_Date);
		p_Checkout.add(new JLabel());
		p_Checkout.add(l_DueDate);
		p_Checkout.add(new JLabel());
		p_Checkout.add(b_Go);
		p_Checkout.add(l_PossibleErrors);
	}

	void setupReturn() {
		l_Book = new JLabel("Book:");
		l_Borrower = new JLabel("Borrower:");
		l_Date = new JLabel("Date:");
		l_DueDate = new JLabel("Date Due:");
		cb_Books = new JComboBox<String>();
		cb_User = new JComboBox<String>();
		l_PossibleErrors = new JLabel("No Errors Yet");
		b_Go = new JButton("Return");

		cb_Books.addItem("Any Book That is currently checked out");

		p_Return.setLayout(new GridLayout(5, 2, 10, 10));
		p_Return.add(l_Book);
		p_Return.add(cb_Books);
		p_Return.add(l_Borrower);
		p_Return.add(new JLabel());
		p_Return.add(l_Date);
		p_Return.add(new JLabel());
		p_Return.add(l_DueDate);
		p_Return.add(new JLabel());
		p_Return.add(b_Go);
		p_Return.add(l_PossibleErrors);

	}

	void setupList() {
		cb_PrepSQL = new JComboBox<String>();
		cb_PrepSQL.addItem("All Books In Library");
		cb_PrepSQL.addItem("Books Out on loan");
		cb_PrepSQL.addItem("Books on subject");
		cb_PrepSQL.addItem("Books by Author");
		cb_PrepSQL.addItem("All Borrowers");
		cb_PrepSQL.addItem("Overdue books");
		cb_Type = new JComboBox<String>();
		// make a method that fills the combo box depending on context
		cb_Type.addItem("DEPENDS ON SQL STATEMENT!");
		l_PossibleErrors = new JLabel("No Errors YEt!");
		l_PossibleErrors.setForeground(Color.RED);

		b_Go = new JButton("GO!");
		// tm_TableModel = tm;
		t_Table = new JTable(tm_TableModel);
		sp_ScrollPane = new JScrollPane(t_Table);

		JPanel p_Top = new JPanel();
		JPanel p_Middle = new JPanel();
		JPanel p_Bottom = new JPanel();
		p_Top.setBackground(bgColor);
		p_Middle.setBackground(bgColor);
		p_Bottom.setBackground(bgColor);

		p_Top.add(cb_PrepSQL);
		p_Top.add(cb_Type);

		p_Middle.add(sp_ScrollPane);

		p_Bottom.add(b_Go);
		p_Bottom.add(l_PossibleErrors);

		p_List.setLayout(new BorderLayout());

		p_List.add(p_Top, BorderLayout.NORTH);
		p_List.add(p_Middle, BorderLayout.CENTER);
		p_List.add(p_Bottom, BorderLayout.SOUTH);
		
		ListenerForButtons listener = new ListenerForButtons();
		b_Go.addActionListener(listener);
	}

	private class ListenerForButtons implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(tp_Tabs.getSelectedIndex() + " " + e.getActionCommand().toString());
			if (tp_Tabs.getSelectedIndex() == tabs.LIST) {
				if (e.getActionCommand().equals("GO!")) {
					int selected = cb_PrepSQL.getSelectedIndex();
				}

			} else if (tp_Tabs.getSelectedIndex() == tabs.ADDBOOK) {

			} else if (tp_Tabs.getSelectedIndex() == tabs.ADDUPDATEBROWSER) {

			} else if (tp_Tabs.getSelectedIndex() == tabs.CHECKOUT) {

			} else if (tp_Tabs.getSelectedIndex() == tabs.RETURN) {

			}
		}

	}

	private class ListenerForComboBox implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (tp_Tabs.getSelectedIndex() == tabs.LIST) {

			} else if (tp_Tabs.getSelectedIndex() == tabs.ADDBOOK) {

			} else if (tp_Tabs.getSelectedIndex() == tabs.ADDUPDATEBROWSER) {

			} else if (tp_Tabs.getSelectedIndex() == tabs.CHECKOUT) {

			} else if (tp_Tabs.getSelectedIndex() == tabs.RETURN) {

			}
		}

	}
}
