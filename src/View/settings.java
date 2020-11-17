package View;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import Model.User;

import javax.swing.JButton;

public class settings extends JDialog {
	
	private JTextField NumberOfDaysField;
	private JTextField finerPerDayField;
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	private JButton btnSave;
	private JButton btnCancel;

	public settings() {
		
		setSize(520, 363);
		setResizable(false);
		this.setTitle("Settings");
		makeComponents();
		apearinTheMiddle();
		initDefaultValues();
		activateListeners();
		makeEditable();
		
	
	}


	public void makeComponents() {
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JPanel panelSettings = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panelSettings, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panelSettings, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panelSettings, 234, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panelSettings, 494, SpringLayout.WEST, getContentPane());
		getContentPane().add(panelSettings);
		GridBagLayout gbl_panelSettings = new GridBagLayout();
		gbl_panelSettings.columnWidths = new int[]{0, 0};
		gbl_panelSettings.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelSettings.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelSettings.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelSettings.setLayout(gbl_panelSettings);
		
		JLabel numberOfDaysLbl = new JLabel("Number of days user can keep book without getting fined");
		numberOfDaysLbl.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_numberOfDaysLbl = new GridBagConstraints();
		gbc_numberOfDaysLbl.insets = new Insets(0, 0, 5, 0);
		gbc_numberOfDaysLbl.gridx = 0;
		gbc_numberOfDaysLbl.gridy = 0;
		panelSettings.add(numberOfDaysLbl, gbc_numberOfDaysLbl);
		
		NumberOfDaysField = new JTextField();
		NumberOfDaysField.setHorizontalAlignment(SwingConstants.CENTER);
		NumberOfDaysField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_NumberOfDaysField = new GridBagConstraints();
		gbc_NumberOfDaysField.fill = GridBagConstraints.HORIZONTAL;
		gbc_NumberOfDaysField.insets = new Insets(0, 0, 5, 0);
		gbc_NumberOfDaysField.gridx = 0;
		gbc_NumberOfDaysField.gridy = 1;
		panelSettings.add(NumberOfDaysField, gbc_NumberOfDaysField);
		NumberOfDaysField.setColumns(10);
		
		JLabel finePerDayLbl = new JLabel("Fine per day");
		finePerDayLbl.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_finePerDayLbl = new GridBagConstraints();
		gbc_finePerDayLbl.insets = new Insets(0, 0, 5, 0);
		gbc_finePerDayLbl.gridx = 0;
		gbc_finePerDayLbl.gridy = 2;
		panelSettings.add(finePerDayLbl, gbc_finePerDayLbl);
		
		finerPerDayField = new JTextField();
		finerPerDayField.setHorizontalAlignment(SwingConstants.CENTER);
		finerPerDayField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_finerPerDayField = new GridBagConstraints();
		gbc_finerPerDayField.insets = new Insets(0, 0, 5, 0);
		gbc_finerPerDayField.fill = GridBagConstraints.HORIZONTAL;
		gbc_finerPerDayField.gridx = 0;
		gbc_finerPerDayField.gridy = 3;
		panelSettings.add(finerPerDayField, gbc_finerPerDayField);
		finerPerDayField.setColumns(10);
		
		JLabel usernameLbl = new JLabel("Username");
		usernameLbl.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_usernameLbl = new GridBagConstraints();
		gbc_usernameLbl.insets = new Insets(0, 0, 5, 0);
		gbc_usernameLbl.gridx = 0;
		gbc_usernameLbl.gridy = 4;
		panelSettings.add(usernameLbl, gbc_usernameLbl);
		
		usernameField = new JTextField();
		usernameField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_usernameField = new GridBagConstraints();
		gbc_usernameField.insets = new Insets(0, 0, 5, 0);
		gbc_usernameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_usernameField.gridx = 0;
		gbc_usernameField.gridy = 5;
		panelSettings.add(usernameField, gbc_usernameField);
		usernameField.setColumns(10);
		
		JLabel passwordLbl = new JLabel("Password");
		passwordLbl.setHorizontalAlignment(SwingConstants.CENTER);
		passwordLbl.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_passwordLbl = new GridBagConstraints();
		gbc_passwordLbl.insets = new Insets(0, 0, 5, 0);
		gbc_passwordLbl.gridx = 0;
		gbc_passwordLbl.gridy = 6;
		panelSettings.add(passwordLbl, gbc_passwordLbl);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 0;
		gbc_passwordField.gridy = 7;
		panelSettings.add(passwordField, gbc_passwordField);
		
		JPanel panelSaveCancel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panelSaveCancel, 6, SpringLayout.SOUTH, panelSettings);
		springLayout.putConstraint(SpringLayout.WEST, panelSaveCancel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panelSaveCancel, 80, SpringLayout.SOUTH, panelSettings);
		springLayout.putConstraint(SpringLayout.EAST, panelSaveCancel, 494, SpringLayout.WEST, getContentPane());
		getContentPane().add(panelSaveCancel);
		panelSaveCancel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelSaveCancel.add(btnSave);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelSaveCancel.add(btnCancel);


	}
	
	private void activateListeners() {
		
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				User user = User.getUser();
				user.setNumOfDayswihtoutFine(Integer.parseInt(NumberOfDaysField.getText()));
				user.setFinePerDay(Float.parseFloat(finerPerDayField.getText() ));
				user.setUsername(usernameField.getText());
				user.setPassword(passwordField.getText());
				
				User.writeUserToFile(user);
				
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				
			}
		});
		
	
		
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
				Point p = settings.this.getLocation();
				p.x += e.getXOnScreen() - mx;
				p.y += e.getYOnScreen() - my;
				mx = e.getXOnScreen();
				my = e.getYOnScreen();
				settings.this.setLocation(p);
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
	/**
	 * Metoda koja povlaci podatke iz Gson fila i prikazuje ih kao tekst
	 * na zadana podrucja.
	 */
	
	private void initDefaultValues() {
		
		User user = User.getUser();
		
		NumberOfDaysField.setText(String.valueOf(user.getNumOfDayswihtoutFine()));
		finerPerDayField.setText(String.valueOf(user.getFinePerDay()));
		usernameField.setText(String.valueOf(user.getUsername()));
		passwordField.setText(String.valueOf(user.getPassword()));
		
		
	}
	
	private void makeEditable() {
		
		User user = User.getUser();
		
		if(user.getUsername()=="admin") {
			NumberOfDaysField.setEditable(true);
			finerPerDayField.setEditable(true);
		}else {
			NumberOfDaysField.setEditable(false);
			finerPerDayField.setEditable(false);
		}
	}

}
