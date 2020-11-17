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
 * @author Bajlo 
 * 
 * 		   Klasa addMember zadužena je za pohranjivanje novih clanova u bazu
 *         podataka. Sadrži nekoliko osnovnih TextFieldova koji ce opisivati
 *         Clana. (ime, email, broj mobitela, clanski ID)
 * 
 *         addMember treba funkcionirati kao popup prozor stoga ga koristimo kao
 *         JWindow umjesto JFramea jer nezelimo da se može ugasiti na itijedan
 *         nacin osim na pritisak saveBtn ili closeBt
 * 
 *         Posto JWindow nema implemenitranu mogucnost da ga pomicemo po ekranu
 *         niti se kotnrolira gdje ce se pojaviti taj problem rijesavamo sami
 * @see moveWindow()
 * @see apearinTheMiddle()
 * 
 * Klasa je jako slicna kao addBook iz jednostavnog razloga joj je zadatak pohraniti druge podatke. Funkcionalnost je jednaka.
 */

public class addMember extends JDialog {

	private JTextField nameField;
	private JTextField memberIdField;
	private JTextField eMailField;
	private JTextField passwordField;

	private JButton saveBtn;
	private JButton cancelBtn;
	private DatabaseHandler databaseHandler;

	public addMember() {

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

		nameField = new JTextField();
		nameField.setToolTipText("");
		nameField.setBounds(10, 50, 480, 35);
		getContentPane().add(nameField);
		nameField.setColumns(10);
		nameField.setEditable(true);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setLabelFor(nameField);
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nameLabel.setBounds(10, 11, 480, 35);
		getContentPane().add(nameLabel);

		memberIdField = new JTextField();
		memberIdField.setToolTipText("");
		memberIdField.setColumns(10);
		memberIdField.setBounds(10, 135, 480, 35);
		getContentPane().add(memberIdField);
		memberIdField.setEditable(true);

		JLabel memberIdLabel = new JLabel("Member ID: ");
		memberIdLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		memberIdLabel.setBounds(10, 96, 480, 35);
		getContentPane().add(memberIdLabel);

		eMailField = new JTextField();
		eMailField.setToolTipText("");
		eMailField.setColumns(10);
		eMailField.setBounds(10, 220, 480, 35);
		getContentPane().add(eMailField);
		eMailField.setEditable(true);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		emailLabel.setBounds(10, 181, 480, 35);
		getContentPane().add(emailLabel);

		passwordField = new JTextField();
		passwordField.setToolTipText("");
		passwordField.setColumns(10);
		passwordField.setBounds(10, 315, 480, 35);
		getContentPane().add(passwordField);
		passwordField.setEditable(true);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordLabel.setBounds(10, 276, 480, 35);
		getContentPane().add(passwordLabel);

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
				Point p = addMember.this.getLocation();
				p.x += e.getXOnScreen() - mx;
				p.y += e.getYOnScreen() - my;
				mx = e.getXOnScreen();
				my = e.getYOnScreen();
				addMember.this.setLocation(p);
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

				new addMember().setVisible(true);
			}
		});
	}

	/**
	 * Dodaje action listenere na 2 JButtona koja smo kreirali.
	 * 
	 */

	public void addActionListeners() {

		/**
		 * SaveBtn pohranjuje clanove u bazu podataka
		 */

		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String memberId = memberIdField.getText();
				String eMail = eMailField.getText();
				String memberName = nameField.getText();
				String password = passwordField.getText();

				if (memberId.isEmpty() || eMail.isEmpty() || memberName.isEmpty() || password.isEmpty()) {

					JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}


				String st = "INSERT INTO MEMBER VALUES (" 
						+ "'" + memberId + "'," 
						+ "'" + memberName + "'," 
						+ "'" + password + "'," 
						+ "'" + eMail + "',"
						+ "'" + "user" + "'"
						+ ")";
				
				System.out.println(st);

				if (databaseHandler.execAction(st)) {

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

		String qu = "SELECT name FROM MEMBER";
		ResultSet rs = databaseHandler.execQuery(qu);

		try {
			while (rs.next()) {
				String titlex = rs.getString("name");
				System.out.println(titlex);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
