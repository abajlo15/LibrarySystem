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
 * Klasa memberList je zasebni prozor koji uz pomoc vanjskog JAR-a
 * prikazuje podatke o Clanovima iz baze na JTable.
 * @author Bajlo
 *
 */

public class memberList extends JDialog {
	
	

	private JTable memberListTable;
	DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

	public memberList() {

		setSize(700, 500);
		setResizable(false);
		getContentPane().setLayout(null);
		apearInTheMiddle();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 664, 439);
		getContentPane().add(scrollPane);

		memberListTable = new JTable();
		scrollPane.setViewportView(memberListTable);
		memberListTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Name", "Member ID", "Mobile", "Email" }));

		try {

			String query = "select * from MEMBER";
			PreparedStatement pst = databaseHandler.conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			memberListTable.setModel(DbUtils.resultSetToTableModel(rs));

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

				new memberList().setVisible(true);
			}
		});

		class Member {

			private SimpleStringProperty name;
			private SimpleStringProperty id;
			private SimpleStringProperty phoneNumber;
			private SimpleStringProperty email;
			

			public Member(String name, String id, String phoneNumber, String email) {

				this.name = new SimpleStringProperty(name);
				this.id = new SimpleStringProperty(id);
				this.phoneNumber = new SimpleStringProperty(phoneNumber);
				this.email = new SimpleStringProperty(email);
		

			}


			public String getName() {
				return name.get();
			}


			public String getId() {
				return id.get();
			}


			public String getPhoneNumber() {
				return phoneNumber.get();
			}


			public String getEmail() {
				return email.get();
			}

		

		}

	}
}