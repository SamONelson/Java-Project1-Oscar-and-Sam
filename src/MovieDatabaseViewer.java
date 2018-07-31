import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * Name: Sam Nelson
 * Date: Jul 25, 2018
 * Purpose: 
 * Student Number 0785337
 */

/**
 * Program Name: Movie Database Viewer
 * Coder: Sam
 * Date: Jul 25, 2018
 * Purpose: 
 */
public class MovieDatabaseViewer extends JFrame {
	public MovieDatabaseViewer(TableModel model) {
		super("Movie Database Viewer");
		
		// boilerplate
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 600);
		this.setLocationRelativeTo(null);
		
		// create the JTable and pass it the TableModel object that is 
		// the parameter of the method
		JTable table = new JTable(model);
		
		// create a JScrollPane so we can see the column names on the table
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane);
		
		// last line
		this.setVisible(true);
	}
}
