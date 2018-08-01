//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.util.Vector;
//
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableModel;
//
///**
//* Program Name:	DBUtils.java
//* Purpose:  methods used for database utilities
//* Coder:	Sam And Oscar
//* Date:     July 11, 2018   
//*/
//
//public class DBUtils {
//	
//	/**
//	* Method Name:	resultSetToTableModel
//	* Purpose:		converts a ResultSet object to a TableModel object
//	* 				for use in a JTable object	
//	* Accepts:	    ResultSet from SQL query
//	* Returns:	    TableModel object which can be used as an argument
//	* 				in the constructor method of JTable to construct
//	* 				a JTable to display data
//	*/
//	public static TableModel resultSetToTableModel(ResultSet rs) {
//		
//		// get the metadata for number of columns and column names
//		try {
//			ResultSetMetaData metaData = rs.getMetaData();
//			
//			int numberOfColumns = metaData.getColumnCount();
//			
//			// instantiate a vector of strings to hold columns names
//			Vector<String> columnNames = new Vector<String>();
//			
//			// get column names and store in the vector
//			for(int column = 1; column <= numberOfColumns; ++column) {
//				columnNames.addElement(metaData.getColumnName(column));
//			}
//			
///*            //new version as of July 12, 2018
//            Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
//
//           while (rs.next())
//           {
//                Vector<Object> newRow = new Vector<Object>();
//
//               for (int i = 1; i <= numberOfColumns; i++)
//               {
//                    newRow.addElement(rs.getObject(i));
//               }//end for
//
//                rows.addElement(newRow);
//            }//end while
//
//           //return the DefaultTableModel object to the line that called it		
//            return new DefaultTableModel(rows, columnNames);*/
//			
//			// create a DefaultTableModel
//			DefaultTableModel model = new DefaultTableModel(columnNames, 0);
//			
//			// get rows of data and store in vector
//			while(rs.next()) {
//				Vector<String> newRow = new Vector<String>();
//				
//				for(int i = 1; i <= numberOfColumns; ++i) {
//					// store column data in the vector
//					newRow.add(rs.getString(i));
//				}
//				
//				model.addRow(newRow);
//			}
//			
//			return model;
//	
//			
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
//		return null;	
//	}
//}
