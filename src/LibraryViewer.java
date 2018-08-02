import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;

public class LibraryViewer extends JFrame {
	private static final long serialVersionUID = 1L;
	private static LibraryModel model = new LibraryModel();
	// CardLayout cardLayout;
	private Color bgColor = new Color(102, 51, 153);
	private JTable t_Table;
	private JScrollPane sp_ScrollPane;
	private TableModel tm_TableModel;
	private JButton b_Go, b_Add, b_Clear;
	private JLabel l_PossibleErrors, l_Title, l_ISBN, l_Edition, l_Subject, l_Author, l_FirstName, l_LastName, l_Email,
			l_Date, l_DueDate, l_Book, l_Borrower;
	private JPanel p_AddBook, p_AddUpdateBorrower, p_Checkout, p_Return, p_List, p_Top, p_Mid, p_Bottom;
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
		
		t_Table = new JTable();
		sp_ScrollPane = new JScrollPane();
		b_Go = new JButton();
		b_Add = new JButton();
		b_Clear = new JButton();
		l_PossibleErrors = new JLabel();
		l_Title = new JLabel();
		l_ISBN = new JLabel();
		l_Edition = new JLabel();
		l_Subject = new JLabel();
		l_Author = new JLabel();
		l_FirstName = new JLabel();
		l_LastName = new JLabel();
		l_Email = new JLabel();
		l_Date = new JLabel();
		l_DueDate = new JLabel();
		l_Book = new JLabel();
		l_Borrower = new JLabel();
		
		tf_Author = new JTextField(20);
		tf_Title = new JTextField(20);
		tf_ISBN = new JTextField(20);
		tf_Edition = new JTextField(20);
		tf_Subject = new JTextField(20);
		tf_FirstName = new JTextField(20);
		tf_LastName = new JTextField(20);
		tf_Email = new JTextField(20);

		cb_PrepSQL = new JComboBox<String>();
		cb_Type = new JComboBox<String>();
		cb_User = new JComboBox<String>(); 
		cb_Books = new JComboBox<String>();
		cb_AuthorList = new JComboBox<String>();
		
		p_Top = new JPanel();
		p_Mid = new JPanel();
		p_Bottom = new JPanel();

		setupList();
		

		ListenerForButtons listener = new ListenerForButtons();
		b_Go.addActionListener(listener);
		b_Add.addActionListener(listener);
		b_Clear.addActionListener(listener);
		
		
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

		tp_Tabs.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (tp_Tabs.getSelectedIndex() == tabs.LIST) {
					setupList();
					cb_PrepSQL.setSelectedIndex(0);
				} else if (tp_Tabs.getSelectedIndex() == tabs.ADDBOOK) {
					setupAddBook();
				} else if (tp_Tabs.getSelectedIndex() == tabs.ADDUPDATEBROWSER) {
					setupAddUpdateBorrower();
				} else if (tp_Tabs.getSelectedIndex() == tabs.CHECKOUT) {
					setupCheckout();
				} else if (tp_Tabs.getSelectedIndex() == tabs.RETURN) {
					setupReturn();
				}
				revalidate();
			}
		});

		this.add(tp_Tabs);
		this.setVisible(true);
	}

	void setupAddBook() {
		p_AddBook.removeAll();
		
		l_Author.setText("Author:");
		l_Title.setText("Title:");
		l_ISBN.setText("ISBN:");
		l_Edition.setText("Edition:");
		l_Subject.setText("Subject:");
		l_PossibleErrors.setText("Author's Go (LastName FirstName)");
		l_PossibleErrors.setForeground(Color.GREEN);

		b_Add.setText("Add Author");
		b_Clear.setText("Clear Author");
		b_Go.setText("Add Book");

		cb_AuthorList.removeAllItems();

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
	}

	void setupAddUpdateBorrower() {
		p_AddUpdateBorrower.removeAll();
		cb_User.removeAllItems();
		
		l_FirstName.setText("First Name:");
		l_LastName.setText("Last Name:");
		l_Email.setText("Email:");
		l_PossibleErrors.setText("Last Name:");
		b_Go.setText("Add Borrower");

		cb_User.addItem("Add New User");
		ArrayList<String> temp = model.getUsers();
		for (int i = 0; i < temp.size(); i++) {
			cb_User.addItem(temp.get(i));
		}

		p_Top.removeAll();
		p_Mid.removeAll();
		p_Bottom.removeAll();

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
		
		ListenerForComboBox cb_Listener = new ListenerForComboBox();
		cb_User.addItemListener(cb_Listener);
	}

	void setupCheckout() {
		p_Checkout.removeAll();
		cb_Books.removeAllItems();
		cb_User.removeAllItems();
		
		l_Book.setText("Book:");
		l_Borrower.setText("Borrower:");
		l_Date.setText("Date Current:");
		l_DueDate.setText("Date Due:");
		l_PossibleErrors.setText("Checkout a book!");
		b_Go.setText("Check Out:");

		
		// Populate Books
		ArrayList<String> books = model.getBooks(true);
		ArrayList<String> users = model.getUsers();
		for (int i = 0; i < books.size(); i++) {
			cb_Books.addItem(books.get(i));
		}
		for (int i = 0; i < users.size(); i++) {
			cb_User.addItem(users.get(i));
		}

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
		p_Return.removeAll();
		cb_Books.removeAllItems();
		
		l_Book.setText("Book:");
		l_Borrower.setText("Borrower:");
		l_Date.setText("Date Current:");
		l_DueDate.setText("Date Due:");
		l_PossibleErrors.setText("Return Thy book!");
		b_Go.setText("Return Book");

		ArrayList<String> books = model.getBooks(false);
		for (int i = 0; i < books.size(); i++) {
			cb_Books.addItem(books.get(i));
		}
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
		p_List.removeAll();
		cb_PrepSQL.removeAllItems();
		sp_ScrollPane.removeAll();
		p_Top.removeAll();
		p_Mid.removeAll();
		p_Bottom.removeAll();
		
		
		cb_PrepSQL.addItem("All Books In Library");
		cb_PrepSQL.addItem("Books Out on loan");
		cb_PrepSQL.addItem("Books on subject");
		cb_PrepSQL.addItem("Books by Author");
		cb_PrepSQL.addItem("All Borrowers");
		cb_PrepSQL.addItem("Overdue books");

		cb_Type.setVisible(false);
		sp_ScrollPane.setVisible(false);
		
		l_PossibleErrors.setText("Select a Query!");
		l_PossibleErrors.setForeground(Color.RED);

		b_Go.setText("Go!");
		t_Table = new JTable(tm_TableModel);
		sp_ScrollPane.add(t_Table);
		
		p_Top.setBackground(bgColor);
		p_Mid.setBackground(bgColor);
		p_Bottom.setBackground(bgColor);

		p_Top.add(cb_PrepSQL);
		p_Top.add(cb_Type);

		p_Mid.add(sp_ScrollPane);

		p_Bottom.add(b_Go);
		p_Bottom.add(l_PossibleErrors);

		p_List.setLayout(new BorderLayout());

		p_List.add(p_Top, BorderLayout.NORTH);
		p_List.add(p_Mid, BorderLayout.CENTER);
		p_List.add(p_Bottom, BorderLayout.SOUTH);

		ListenerForButtons b_Listener = new ListenerForButtons();
		b_Go.addActionListener(b_Listener);

		ListenerForComboBox cb_Listener = new ListenerForComboBox();
		cb_PrepSQL.addItemListener(cb_Listener);
	}

	private class ListenerForButtons implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (tp_Tabs.getSelectedIndex() == tabs.LIST) {
					if (e.getSource().equals(b_Go)) {
						int decision = cb_PrepSQL.getSelectedIndex();
						model.ListPanelsqlStatement(decision,
								cb_Type.getItemCount() > 0 ? cb_Type.getSelectedItem().toString() : "");
						tm_TableModel = model.getTable();
						t_Table = new JTable(tm_TableModel);
						t_Table.setCellSelectionEnabled(false);

						JScrollPane sp_Temp = new JScrollPane(t_Table);
						JPanel temp = (JPanel) p_List.getComponent(1);

						
						
						temp.removeAll();
						temp.add(sp_Temp);
						revalidate();
					}

				} else if (tp_Tabs.getSelectedIndex() == tabs.ADDBOOK) {
					if (e.getSource().equals(b_Add)) {
						cb_AuthorList.addItem(tf_Author.getText());
					} else if (e.getSource().equals(b_Clear)) {
						cb_AuthorList.removeItem(cb_AuthorList.getSelectedItem());
						if (cb_AuthorList.getItemCount() == 0) { // Fixes problem with box not clearing itself
							cb_AuthorList.removeAllItems();
						}
					} else if (e.getSource().equals(b_Go)) {
						ArrayList<String> book = new ArrayList<String>();
						boolean noEmptySlots = true;
						book.add(tf_Title.getText());
						book.add(tf_ISBN.getText());
						book.add(tf_Edition.getText());
						book.add(tf_Subject.getText());
						for (int i = 0; i < cb_AuthorList.getItemCount(); i++) {
							book.add(cb_AuthorList.getItemAt(i).toString());
						}
						for (int i = 0; i < book.size(); i++)
							if (book.get(i).toString().equals(""))
								noEmptySlots = false;
						if (noEmptySlots) {
							model.AddBook(book);
							l_PossibleErrors.setText("Added Successfully! ♥");
						}
						else
							l_PossibleErrors.setText("One or More Fields were left Empty! >:|");
					}
				} else if (tp_Tabs.getSelectedIndex() == tabs.ADDUPDATEBROWSER) {
					if (e.getActionCommand().equals("Update Borrower")) {
						
					}

				} else if (tp_Tabs.getSelectedIndex() == tabs.CHECKOUT) {
						if(e.getSource().equals(b_Go)) {
							String title = cb_Books.getSelectedItem().toString(), borrower = cb_User.getSelectedItem().toString();
							model.CheckOutBook(title, borrower);
							setupCheckout();
							l_PossibleErrors.setText("Checkout Successful");
						}
				} else if (tp_Tabs.getSelectedIndex() == tabs.RETURN) {

				}
			} catch (Exception e1) {
				l_PossibleErrors.setText(e1.getMessage());
			}
		}

	}

	private class ListenerForComboBox implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			try {
				if (tp_Tabs.getSelectedIndex() == tabs.LIST) {
					if (e.getSource().equals(cb_PrepSQL) && e.getStateChange() == ItemEvent.SELECTED) {
						if (cb_PrepSQL.getSelectedIndex() == 0) {
							cb_Type.setVisible(false);
						}
						if (cb_PrepSQL.getSelectedIndex() == 1) {
							cb_Type.setVisible(false);
						}
						if (cb_PrepSQL.getSelectedIndex() == 2) {
							ArrayList<String> temp = model.getSubjects();
							cb_Type.removeAllItems();
							for (int i = 0; i < temp.size(); i++) {
								cb_Type.addItem(temp.get(i));
							}
							cb_Type.setVisible(true);
						}
						if (cb_PrepSQL.getSelectedIndex() == 3) {
							ArrayList<String> temp = model.getAuthors();
							cb_Type.removeAllItems();
							for (int i = 0; i < temp.size(); i++) {
								cb_Type.addItem(temp.get(i));
							}
							cb_Type.setVisible(true);
						}
						if (cb_PrepSQL.getSelectedIndex() == 4) {
							cb_Type.setVisible(false);
						}
						if (cb_PrepSQL.getSelectedIndex() == 5) {
							cb_Type.setVisible(false);
						}
						if (cb_PrepSQL.getSelectedIndex() == 6) {
							cb_Type.setVisible(false);
						}
					}

				} else if (tp_Tabs.getSelectedIndex() == tabs.ADDBOOK) {

					
					
				} else if (tp_Tabs.getSelectedIndex() == tabs.ADDUPDATEBROWSER) {					
					if (e.getSource().equals(cb_User) && e.getStateChange() == ItemEvent.SELECTED) {
						if (cb_User.getSelectedIndex() == 0) {
							b_Go.setText("Add Borrower");

						}
						else if(cb_User.getSelectedIndex() != 0){
							b_Go.setText("Update Borrower");
							String fullname = cb_User.getSelectedItem().toString();
//							String last = model.seperateSpace(fullname, false);
//							String first = model.seperateSpace(fullname, true);
							String first = "Mary";
							String last = "Smith";
							ArrayList<String> temp = model.getUserByName(first, last);
							for(int i =0; i < temp.size(); i++)
							{
								System.out.println(temp.get(i));
							}
							
//							tf_FirstName.setText(temp.get(1));
//							tf_LastName.setText(temp.get(2));
//							tf_Email.setText(temp.get(3));
						}
					}

				} else if (tp_Tabs.getSelectedIndex() == tabs.CHECKOUT) {

				} else if (tp_Tabs.getSelectedIndex() == tabs.RETURN) {

				}
			} catch (Exception e1) {
				l_PossibleErrors.setText(e1.getMessage());
			}
		}

	}
}
