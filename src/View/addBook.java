package View;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Database.DatabaseHandler;

/**
 * 
 * @author Bajlo Klasa addBook zadužena je za pohranjivanje novih knjiga u bazu
 *         podataka. Sadrži nekoliko osnovnih TextFieldova koji ce opisivati
 *         knjigu. (Autor knjige, Ime knjige, Izdavacka kuca)
 * 
 *         addBook treba funkcionirati kao popup prozor stoga ga koristimo kao
 *         JWindow umjesto JFramea jer nezelimo da se može ugasiti na itijedan
 *         nacin osim na pritisak saveBtn ili closeBt
 * 
 *         Posto JWindow nema implemenitranu mogucnost da ga pomicemo po ekranu
 *         niti se kotnrolira gdje ce se pojaviti taj problem rijesavamo sami
 * @see moveWindow()
 * @see apearinTheMiddle()
 */

public class addBook extends JDialog {

	private JTextField bookTitleField;
	private JTextField bookIdField;
	private JTextField bookAuthorField;
	private JTextField publisherField;

	private JButton saveBtn;
	private JButton cancelBtn;
	private DatabaseHandler databaseHandler;

	public addBook() {

		getContentPane().setLayout(null);
		setSize(520, 500);
		setResizable(false);
		makeComponents();
		moveWindow();
		apearinTheMiddle();
		initializeDBS();
		addActionListeners();
		checkData();

	}

	/**
	 * Metoda makeComponents kreira komponente prozora, to cinimo koristeci window
	 * builder.
	 * 
	 */

	public void makeComponents() {

		bookTitleField = new JTextField();
		bookTitleField.setToolTipText("");
		bookTitleField.setBounds(10, 50, 480, 35);
		getContentPane().add(bookTitleField);
		bookTitleField.setColumns(10);
		bookTitleField.setEditable(true);

		JLabel bookTitleLabel = new JLabel("Book Title: ");
		bookTitleLabel.setLabelFor(bookTitleField);
		bookTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		bookTitleLabel.setBounds(10, 11, 480, 35);
		getContentPane().add(bookTitleLabel);

		bookIdField = new JTextField();
		bookIdField.setToolTipText("");
		bookIdField.setColumns(10);
		bookIdField.setBounds(10, 135, 480, 35);
		getContentPane().add(bookIdField);
		bookIdField.setEditable(true);

		JLabel bookIdLabel = new JLabel("Book ID: ");
		bookIdLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		bookIdLabel.setBounds(10, 96, 480, 35);
		getContentPane().add(bookIdLabel);

		bookAuthorField = new JTextField();
		bookAuthorField.setToolTipText("");
		bookAuthorField.setColumns(10);
		bookAuthorField.setBounds(10, 220, 480, 35);
		getContentPane().add(bookAuthorField);
		bookAuthorField.setEditable(true);

		JLabel bookAuthorLabel = new JLabel("Book Author: ");
		bookAuthorLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		bookAuthorLabel.setBounds(10, 181, 480, 35);
		getContentPane().add(bookAuthorLabel);

		publisherField = new JTextField();
		publisherField.setToolTipText("");
		publisherField.setColumns(10);
		publisherField.setBounds(10, 315, 480, 35);
		getContentPane().add(publisherField);
		publisherField.setEditable(true);

		JLabel publisherLabel = new JLabel("Publisher: ");
		publisherLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		publisherLabel.setBounds(10, 276, 480, 35);
		getContentPane().add(publisherLabel);

		ImageIcon saveImg = new ImageIcon("pictures/save.png");
		saveBtn = new JButton(saveImg);
		saveBtn.setBounds(48, 380, 150, 57);
		getContentPane().add(saveBtn);

		ImageIcon cancelImg = new ImageIcon("pictures/cancel.png");
		cancelBtn = new JButton(cancelImg);
		cancelBtn.setBounds(299, 380, 150, 57);
		getContentPane().add(cancelBtn);

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
				Point p = addBook.this.getLocation();
				p.x += e.getXOnScreen() - mx;
				p.y += e.getYOnScreen() - my;
				mx = e.getXOnScreen();
				my = e.getYOnScreen();
				addBook.this.setLocation(p);
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

	public void initializeDBS() {

		databaseHandler = DatabaseHandler.getInstance();
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				new addBook().setVisible(true);
			}
		});
	}

	/**
	 * Dodaje action listenere na 2 JButtona koja smo kreirali.
	 * 
	 */

	public void addActionListeners() {

		/**
		 * SaveBtn pohranjuje knjige u bazu podataka
		 */

		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String bookID = bookIdField.getText();
				String bookAuthor = bookAuthorField.getText();
				String bookName = bookTitleField.getText();
				String bookPublisher = publisherField.getText();

				if (bookID.isEmpty() || bookAuthor.isEmpty() || bookName.isEmpty() || bookPublisher.isEmpty()) {

					JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

//				stmt.execute("CREATE TABLE " + TABLE_NAME +"("
//						+"		id varchar(200) primary key,\n"
//						+"		title varchar(200),\n"
//						+"		author varchar(200),\n"
//						+"		publisher varchar(200),\n"
//						+"		isAvail boolean default true" 
//						+")");	

				String qu = "INSERT INTO BOOK VALUES (" + "'" + bookID + "'," + "'" + bookName + "'," + "'" + bookAuthor
						+ "'," + "'" + bookPublisher + "'," + "" + true + "" + ")";

				if (databaseHandler.execAction(qu)) {

					JOptionPane.showMessageDialog(null, "Succes");

				} else {
					JOptionPane.showMessageDialog(null, "Failed", "Error", JOptionPane.ERROR_MESSAGE);

				}

			}
		});

		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				

			}
		});

	}

	private void checkData() {
		
		String qu = "SELECT title FROM BOOK";
		ResultSet rs = databaseHandler.execQuery(qu);

		try {
			while (rs.next()) {
				String titlex = rs.getString("title");
				System.out.println(titlex);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
