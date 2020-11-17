package View;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Database.DatabaseHandler;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import net.proteanit.sql.DbUtils;

/**
 * Klasa bookList je zasebni prozor koji uz pomoc vanjskog JAR-a prikazuje
 * podatke o knjigama iz baze na JTable.
 * 
 * @author Bajlo
 *
 */

public class bookList extends JDialog {

	private JTable bookListTable;
	DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

	public bookList() {

		setSize(700, 500);
		setResizable(false);
		getContentPane().setLayout(null);
		apearInTheMiddle();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 664, 439);
		getContentPane().add(scrollPane);

		bookListTable = new JTable();
		scrollPane.setViewportView(bookListTable);
		bookListTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Title", "BookID", "Author", "Publisher", "Avaiability" }));

		try {

			String query = "select * from BOOK";
			PreparedStatement pst = databaseHandler.conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			bookListTable.setModel(DbUtils.resultSetToTableModel(rs));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void apearInTheMiddle() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				new bookList().setVisible(true);
			}
		});

		class Book {

			private SimpleStringProperty title;
			private SimpleStringProperty id;
			private SimpleStringProperty author;
			private SimpleStringProperty publisher;
			private SimpleBooleanProperty avaiability;

			public Book(String title, String id, String author, String publisher, Boolean avaiability) {

				this.title = new SimpleStringProperty(title);
				this.id = new SimpleStringProperty(id);
				this.author = new SimpleStringProperty(author);
				this.publisher = new SimpleStringProperty(publisher);
				this.avaiability = new SimpleBooleanProperty(avaiability);

			}

			public String getTitle() {
				return title.get();
			}

			public String getId() {
				return id.get();
			}

			public String getAuthor() {
				return author.get();
			}

			public String getPublisher() {
				return publisher.get();
			}

			public Boolean getAvaiability() {
				return avaiability.get();
			}

		}

	}
}
