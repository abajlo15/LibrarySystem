package View;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Database.DatabaseHandler;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JList;

public class userFrame extends JDialog {
	
	private DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
	String username = login.getUsername();
	String id = null;
	
	DefaultListModel<String> model;
	private JList<String> issuedBooksList;
	
	JLabel usernameLbl = new JLabel("New label");
	
	public userFrame() {
		
		getContentPane().setLayout(null);
		setSize(700, 500);
		setResizable(false);
		makeComponents();
		apearinTheMiddle();
		moveWindow();
		setID();
		showIssuedBooks();	
	
		
	}
	
	private void setID() {
		String qu = "SELECT id FROM MEMBER where name=" + "'" + login.getUsername() + "'";
		ResultSet rs = databaseHandler.execQuery(qu);

		try {
			while (rs.next()) {
				String titlex = rs.getString("id");
				id = titlex;
				System.out.println(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	private void showIssuedBooks() {
		
		model = new DefaultListModel<>();
		model.removeAllElements();
		issuedBooksList.setModel(model);
		
		String qu = "SELECT * FROM ISSUE WHERE memberID ='" + id + "'";
		ResultSet rs = databaseHandler.execQuery(qu);
		
		try {
			
			while(rs.next()) {
				
				String mBookID = rs.getString("bookID");
				
				qu = "SELECT * FROM BOOK WHERE ID = '" + mBookID + "'";
				ResultSet rs1 = databaseHandler.execQuery(qu);
				
				while(rs1.next()) {
					model.addElement(rs1.getString("title"));
				}
				
				issuedBooksList.setModel(model);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public void makeComponents() {
		
		usernameLbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		usernameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLbl.setBounds(10, 11, 650, 27);
		getContentPane().add(usernameLbl);
		usernameLbl.setText("Welcome to your library " + login.getUsername());
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 61, 331, 389);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel issuedBooksLbl = new JLabel("New label");
		issuedBooksLbl.setHorizontalAlignment(SwingConstants.CENTER);
		issuedBooksLbl.setBounds(10, 11, 311, 22);
		panel.add(issuedBooksLbl);
		issuedBooksLbl.setText("Your issued Books");
		
		issuedBooksList = new JList();
		issuedBooksList.setFont(new Font("Tahoma", Font.PLAIN, 13));
		issuedBooksList.setBounds(10, 47, 311, 331);
		panel.add(issuedBooksList);
		
		
		
	}
	
	
	/**
	 * Metoda moveWidnow zaduzena je za pomicanje prozora.
	 * 
	 */
	public void moveWindow() {

		this.addMouseMotionListener(new MouseMotionListener() {
			private int mx, my;

			@Override
			public void mouseMoved(MouseEvent e) {
				mx = e.getXOnScreen();
				my = e.getYOnScreen();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = userFrame.this.getLocation();
				p.x += e.getXOnScreen() - mx;
				p.y += e.getYOnScreen() - my;
				mx = e.getXOnScreen();
				my = e.getYOnScreen();
				userFrame.this.setLocation(p);
			}
		});
	}

	/**
	 * Metoda apearInTheMiddle zaduzena je da se prozor uvjek pojavi na sredini
	 * ekrana bez obzira na kojem racunalu se otvori.
	 * 
	 * 
	 */

	public void apearinTheMiddle() {

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}
}
