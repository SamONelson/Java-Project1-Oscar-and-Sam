
//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
<<<<<<< HEAD
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.border.Border;
import javax.swing.table.TableModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

=======
>>>>>>> c2d9164afbb9dc5df51d587cab9735de616436da

public class LibraryViewer extends JFrame {
//	CardLayout cardLayout;
	//	CardLayout cardLayout;
	JTable t_Table;
	JScrollPane sp_ScrollPane;
	TableModel tm_TableModel;
	JButton b_Go, b_Add, b_Clear;
	JLabel l_PossibleErrors, l_Title, l_ISBN, l_Edition, l_Subject, l_Author;
	JPanel /* p_Cards, p_Tabs, */ p_AddBook, p_AddUpdateBorrower, p_Checkout, p_Return, p_List;
	JTextField tf_Author, tf_Title, tf_ISBN, tf_Edition, tf_Subject;
	
	JComboBox<String> cb_PrepSQL, cb_Type;
//	JButton b_AddBook, b_AddUpdateBorrower, b_Checkout, b_Return, b_List; 
	JTabbedPane tp_Tabs;

	public static void main(String[] args) {
		new LibraryViewer(/*TableModel*/);
	}

	public LibraryViewer(/*TableModel tm*/) {
		super("Library Management V1.0 Oscar & Sam");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700,550);
		this.setLocationRelativeTo(null);
//		this.setResizable(false);
//		this.setLayout(new BorderLayout());
//		cardLayout = new CardLayout();

		tp_Tabs = new JTabbedPane();
		p_AddBook = new JPanel();
		p_AddUpdateBorrower = new JPanel();
		p_Checkout = new JPanel();
		p_Return = new JPanel();
		p_List = new JPanel();
		
		setupList();
		setupAddBook();
		tp_Tabs.add("List", p_List);
		tp_Tabs.add("Add Book", p_AddBook);
		tp_Tabs.add("Add/Update Borrower", p_AddUpdateBorrower);
		tp_Tabs.add("Checkout", p_Checkout);
		tp_Tabs.add("Return", p_Return);
		p_AddBook.setBackground(Color.RED);
		p_AddUpdateBorrower.setBackground(Color.GREEN);
		p_Checkout.setBackground(Color.BLUE);
		p_Return.setBackground(Color.PINK);
		p_List.setBackground(new Color(102, 51, 153));
		this.add(tp_Tabs);
		this.setVisible(true);
	}

	void setupAddBook() {	// HOLY FUCK I HATE THIS
		l_Author = new JLabel("Author:");
		l_Title = new JLabel("Title:");
		l_ISBN = new JLabel("ISBN:");
		l_Edition = new JLabel("Edition:");
		l_Subject = new JLabel("Subject:");
		l_PossibleErrors = new JLabel("No Errors YEt!");
		l_PossibleErrors.setForeground(Color.BLUE);;
		
		tf_Author = new JTextField(10);
		tf_Title = new JTextField(10);
		tf_ISBN = new JTextField(10);
		tf_Edition = new JTextField(10);
		tf_Subject = new JTextField(10);
<<<<<<< HEAD
		
		
		b_Go = new JButton("Add Book");
		b_Add = new JButton("Add Author");
		b_Clear = new JButton("Clear Authors");
		
		t_Table = new JTable(tm_TableModel);
		sp_ScrollPane = new JScrollPane(t_Table);
		//p_AddBook.setLayout(new BoxLayout(p_AddBook, BoxLayout.X_AXIS));
		p_AddBook.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(5, 5, 5, 5);
		
		
		c.gridx = 0;       //aligned with button 2
		//c.gridwidth = 1;   //2 columns wide
		c.gridy = 0;       //third row
		p_AddBook.add(l_Title, c);
		
		
		c.gridx = 1;       //aligned with button 2
		//c.gridwidth = 1;   //2 columns wide
		c.gridy = 0;       //third row
		p_AddBook.add(tf_Title, c);
		
		
		c.gridx = 2;       //aligned with button 2
		//c.gridwidth = 1;   //2 columns wide
		c.gridy = 0;       //third row
		p_AddBook.add(l_Author, c);
		
		c.gridx = 3;       //aligned with button 2
		//c.gridwidth = 1;   //2 columns wide
		c.gridy = 0;       //third row
		p_AddBook.add(tf_Author, c);
		
		c.gridx = 4;       //aligned with button 2
		//c.gridwidth = 1;   //2 columns wide
		c.gridy = 0;       //third row
		p_AddBook.add(b_Add, c);
		
		c.gridx = 0;       //aligned with button 2
		//c.gridwidth = 1;   //2 columns wide
		c.gridy = 1;       //third row
		p_AddBook.add(l_ISBN, c);
		
		c.gridx = 1;       //aligned with button 2
		//c.gridwidth = 1;   //2 columns wide
		c.gridy = 1;       //third row
		p_AddBook.add(tf_ISBN, c);
		
		c.gridx = 2;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridheight = 2;
		c.gridy = 1;       //third row
		p_AddBook.add(sp_ScrollPane, c);
		
		c.gridx = 4;       //aligned with button 2
		//c.gridwidth = 1;   //2 columns wide
		c.gridy = 1;       //third row
		p_AddBook.add(b_Clear, c);
		
		c.gridx = 0;       //aligned with button 2
		c.gridwidth = 1;   //2 columns wide
		c.gridy = 2;       //third row
		p_AddBook.add(l_Edition, c);
		
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 1;   //2 columns wide
		c.gridy = 2;       //third row
		p_AddBook.add(tf_Edition, c);
		
		c.gridx = 0;       //aligned with button 2
		c.gridwidth = 1;   //2 columns wide
		c.gridy = 3;       //third row
		p_AddBook.add(l_Subject, c);
		
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 1;   //2 columns wide
		c.gridy = 3;       //third row
		p_AddBook.add(tf_Subject, c);
		
		c.gridx = 0;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 4;       //third row
		p_AddBook.add(b_Go, c);
		
		c.gridx = 2;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 4;       //third row
		p_AddBook.add(l_PossibleErrors, c);
		
//		p_AddBook.add(sp_ScrollPane);
=======
>>>>>>> c2d9164afbb9dc5df51d587cab9735de616436da
	}

	void setupAddUpdateBorrower() {

	}

	void setupCheckout() {

	}

	void setupReturn() {

	}

	void setupList() {
		cb_PrepSQL = new JComboBox<String>();
		cb_PrepSQL.addItem("PREPARE SOME SQL STATEMENTS IN THE CONTROLLER LOL");
		cb_Type = new JComboBox<String>();
		cb_Type.addItem("DEPENDS ON SQL STATEMENT!");
		l_PossibleErrors = new JLabel("No Errors YEt!");
		l_PossibleErrors.setForeground(Color.RED);;
		;
		b_Go = new JButton("GO!");
		//tm_TableModel = tm;
		t_Table = new JTable(tm_TableModel);
		sp_ScrollPane = new JScrollPane(t_Table);
		
//		JPanel p_Top = new JPanel();
//		p_Top.add(cb_PrepSQL);
//		p_Top.add(cb_Type);
//		
//		JPanel p_Left = new JPanel();
//		p_Left.add(sp_ScrollPane);
//		
//		JPanel p_Right = new JPanel();
//		p_Right.add(b_Go);
//		p_Right.add(l_PossibleErrors);
//		
//		p_List.add(p_Top);
//		p_List.add(p_Left);
//		p_List.add(p_Right);
		
		p_List.add(cb_PrepSQL);
		p_List.add(cb_Type);
		p_List.add(sp_ScrollPane);
		p_List.add(b_Go);
		p_List.add(l_PossibleErrors);
	}
}
//		p_Cards = new JPanel();
//		p_Tabs = new JPanel();

// b_AddBook = new JButton("Book");
//		b_AddUpdateBorrower = new JButton("Borrowers");
//		b_Checkout = new JButton("Checkout");
//		b_Return = new JButton("Return");
//		b_List = new JButton("List");

//		MaryPoppins nanny = new MaryPoppins();

//		b_AddBook.addActionListener(nanny);
//		b_AddUpdateBorrower.addActionListener(nanny);
//		b_Checkout.addActionListener(nanny);
//		b_Return.addActionListener(nanny);
//		b_List.addActionListener(nanny);

//		p_Tabs.add(b_AddBook);
//		p_Tabs.add(b_AddUpdateBorrower);
//		p_Tabs.add(b_Checkout);
//		p_Tabs.add(b_Return);
//		p_Tabs.add(b_List);

//		p_Cards.setLayout(cardLayout);
//		cardLayout.show(p_Cards, "List");

//		p_Cards.add(p_AddBook, "AddBook");
//		p_Cards.add(p_AddUpdateBorrower, "AddUpdateBorrow");
//		p_Cards.add(p_Checkout, "Checkout");
//		p_Cards.add(p_Return, "Return");
//		p_Cards.add(p_List, "List");
//		this.add(p_Cards, BorderLayout.CENTER);

//	class MaryPoppins implements ActionListener {
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			if(e.getSource() == b_AddBook)
//				cardLayout.show(p_Cards, "AddBook");
//			else if(e.getSource() == b_AddUpdateBorrower)
//				cardLayout.show(p_Cards, "AddUpdateBorrow");
//			else if(e.getSource() == b_Checkout)
//				cardLayout.show(p_Cards, "Checkout");
//			else if(e.getSource() == b_Return)
//				cardLayout.show(p_Cards, "Return");
//			else if(e.getSource() == b_List)
//				cardLayout.show(p_Cards, "List");
//		}
//		
//	}
