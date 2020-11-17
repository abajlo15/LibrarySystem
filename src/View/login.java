package View;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Database.DatabaseHandler;
import Model.User;


public class login extends JDialog {
	
	/**
	 * Login je prozor koji se otvara prvi prilikom startra aplikacije te nam nudi mogucnost prijave
	 * unesene podatke usporeduje s konfig fileom od Usera te ako su isti prosljeduje nas dalje u program
	 * ili ako su krivi tj razliciti javlja nam gresku i tu nas zaustavlja.
	 */
	
	private JTextField usernameTextField;
	private JPasswordField passwordField;
	private JButton loginBtn;
	private JButton cancelBtn;
	
	public static String username;
	public String password;
	
	 public User user;
	
	public login() {
		
		user = User.getUser();
		createComponents();
		setSize(460,320);
		setResizable(false);
		setVisible(true);
		apearinTheMiddle();
		activateListeners();
		
	}
	
	private void createComponents(){
		
		setTitle("Library Management Login");
		getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(10, 11, 426, 37);
		getContentPane().add(lblUsername);
		
		usernameTextField = new JTextField();
		usernameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		usernameTextField.setBounds(10, 59, 426, 37);
		getContentPane().add(usernameTextField);
		usernameTextField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPassword.setBounds(10, 107, 426, 37);
		getContentPane().add(lblPassword);
		
		loginBtn = new JButton("Login");
		loginBtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		loginBtn.setBounds(83, 218, 126, 51);
		getContentPane().add(loginBtn);
		
		cancelBtn = new JButton("Cancel");
		cancelBtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		cancelBtn.setBounds(238, 218, 126, 51);
		getContentPane().add(cancelBtn);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordField.setBounds(10, 155, 426, 37);
		getContentPane().add(passwordField);
		
		
	}
	
	private void activateListeners() {
		
		cancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
				
			}
		});
		
		loginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int counter = 0;
				
				username = usernameTextField.getText();
				password = passwordField.getText();
				
				if(username.equals(user.getUsername()) && password.equals(user.getPassword())) {
					setVisible(false);
					counter = 0;
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {

							new MainFrame().setVisible(true);
							
						
						}
					});
					
				}else {
					counter++;
				}
				
				DatabaseHandler databaseHandler = new DatabaseHandler();
				try {
				String qu = "SELECT * FROM MEMBER WHERE name=? and password=? and usertype=?";
				PreparedStatement pst = databaseHandler.conn.prepareStatement(qu);
				pst.setString(1, username);
				pst.setString(2, password);
				pst.setString(3, "user");
				
				ResultSet rs = pst.executeQuery();
				
				if(rs.next()) {
					
					counter = 0;
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {

							new userFrame().setVisible(true);
							
						
						}
					});
					
					
				}else {
					
					counter++;
				}
				
				if(counter >= 2) {
					
					JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Failed", JOptionPane.ERROR_MESSAGE);

				}
			
				
			
				} catch (SQLException es) {
					es.printStackTrace();
				}
				
			

			}
		});
		
	}
	
	public void apearinTheMiddle() {

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	public static String getUsername() {
		return username;
	}
	
	
}
