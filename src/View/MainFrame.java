package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import Database.DatabaseHandler;
import javax.swing.JScrollPane;
import javax.swing.JMenuItem;

/**
 * Glavni prozor aplikacije. Sastoji se od 3 dijela: 1. Menu bar:
 * 
 * 
 * 
 * 
 * 2. JTabbedPanel:
 * 
 * 
 * 
 * 
 * 3. Panel sa JButtonima:
 * 
 * 
 * 
 * 
 * @author Bajlo
 *
 */

public class MainFrame extends JFrame {

	private Panel panel;

	private JButton addMemberButton;
	private JButton addBookButton;
	private JButton viewMembersButton;
	private JButton viewBooksButton;
	private JButton settingsBottun;

	private JTabbedPane tabbedPane;
	private Panel bookIsuePanel;
	private JLayeredPane bookPanel;
	private JTextField enterBookIDField;
	private JLayeredPane memberPanel;
	private JLabel enterMemberIdLbl;
	private JTextField enterMemberIdField;
	private JLabel memberNameLbl;
	private JLabel contactMemberLbl;
	private Panel renewSubmisionPanel;

	private JButton issueButton;
	private JButton renewButton;
	private JButton submissionButton;

	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu editMenu;
	private JTextField enterBookIdRenewField;
	private JButton enterBookIdRenewButton;

	private memberList memberLst;
	private bookList bookLst;
	private addBook bookAdd;
	private addMember memberAdd;
	private JButton showBookButton;
	private JButton showMemberBtn;

	private JLabel bookNameLbl;
	private JLabel bookAuthorLbl;
	private JLabel avaiabilityLbl;

	private JMenuItem mntmClose = new JMenuItem("Close");
	JMenuItem mntmAddMember = new JMenuItem("Add Member");
	JMenuItem mntmAddBook = new JMenuItem("Add Book");
	JMenuItem mntmViewMember = new JMenuItem("View Members");
	JMenuItem mntmViewBooks = new JMenuItem("View Books");

	DefaultListModel<String> model;
	private JList<String> bookInformationList;

	private DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
	private JScrollPane scrollPane;

	private Boolean isReadyForSubmission = null;

	public MainFrame() {

		setSize(780, 530);
		setResizable(false);
		createComponents();
		apearInTheMiddle();
		activateListeners();

	}

	private void apearInTheMiddle() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	private void createComponents() {

		setTitle("Library Management System Projekt NOP");
		getContentPane().setLayout(new BorderLayout(0, 0));

		panel = new Panel();
		getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		ImageIcon addMemberImg = new ImageIcon("pictures/addMember.png");
		addMemberButton = new JButton("Add Member", addMemberImg);
		addMemberButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		addMemberButton.setHorizontalTextPosition(SwingConstants.CENTER);
		panel.add(addMemberButton);

		ImageIcon addBookImg = new ImageIcon("pictures/addBook.png");
		addBookButton = new JButton("Add Book", addBookImg);
		addBookButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		addBookButton.setHorizontalTextPosition(SwingConstants.CENTER);
		panel.add(addBookButton);

		ImageIcon viewMembersImg = new ImageIcon("pictures/viewMembers.png");
		viewMembersButton = new JButton("View Members", viewMembersImg);
		viewMembersButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		viewMembersButton.setHorizontalTextPosition(SwingConstants.CENTER);
		panel.add(viewMembersButton);

		ImageIcon viewBooksImg = new ImageIcon("pictures/viewBooks.png");
		viewBooksButton = new JButton("View Books", viewBooksImg);
		viewBooksButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		viewBooksButton.setHorizontalTextPosition(SwingConstants.CENTER);
		panel.add(viewBooksButton);

		ImageIcon settingsImg = new ImageIcon("pictures/settings.png");
		settingsBottun = new JButton("Settings", settingsImg);
		settingsBottun.setVerticalTextPosition(SwingConstants.BOTTOM);
		settingsBottun.setHorizontalTextPosition(SwingConstants.CENTER);
		panel.add(settingsBottun);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		bookIsuePanel = new Panel();
		tabbedPane.addTab("Book Issue", null, bookIsuePanel, null);
		bookIsuePanel.setLayout(new GridLayout(0, 1, 0, 0));

		bookPanel = new JLayeredPane();
		bookPanel.setForeground(Color.LIGHT_GRAY);
		bookPanel.setBackground(Color.LIGHT_GRAY);
		bookPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		bookIsuePanel.add(bookPanel);
		bookPanel.setLayout(null);

		JLabel enterBookIdLbl = new JLabel("Enter Book ID:");
		enterBookIdLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		enterBookIdLbl.setBounds(28, 38, 144, 47);
		bookPanel.add(enterBookIdLbl);

		enterBookIDField = new JTextField();
		enterBookIDField.setBounds(10, 83, 144, 36);
		bookPanel.add(enterBookIDField);
		enterBookIDField.setColumns(10);

		bookNameLbl = new JLabel("Book Name");
		bookNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		bookNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		bookNameLbl.setBounds(261, 38, 347, 28);
		bookPanel.add(bookNameLbl);

		bookAuthorLbl = new JLabel("Author");
		bookAuthorLbl.setHorizontalAlignment(SwingConstants.CENTER);
		bookAuthorLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		bookAuthorLbl.setBounds(261, 94, 347, 25);
		bookPanel.add(bookAuthorLbl);

		showBookButton = new JButton("Show Book");
		showBookButton.setBounds(20, 130, 117, 23);
		bookPanel.add(showBookButton);

		avaiabilityLbl = new JLabel("Avaiability");
		avaiabilityLbl.setHorizontalAlignment(SwingConstants.CENTER);
		avaiabilityLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		avaiabilityLbl.setBounds(261, 153, 347, 25);
		bookPanel.add(avaiabilityLbl);

		memberPanel = new JLayeredPane();
		memberPanel.setForeground(Color.LIGHT_GRAY);
		memberPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		memberPanel.setBackground(Color.LIGHT_GRAY);
		bookIsuePanel.add(memberPanel);
		memberPanel.setLayout(null);

		enterMemberIdLbl = new JLabel("Enter Member ID:");
		enterMemberIdLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		enterMemberIdLbl.setBounds(21, 36, 144, 47);
		memberPanel.add(enterMemberIdLbl);

		enterMemberIdField = new JTextField();
		enterMemberIdField.setColumns(10);
		enterMemberIdField.setBounds(10, 83, 144, 36);
		memberPanel.add(enterMemberIdField);

		contactMemberLbl = new JLabel("Password");
		contactMemberLbl.setHorizontalAlignment(SwingConstants.CENTER);
		contactMemberLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contactMemberLbl.setBounds(261, 94, 347, 25);
		memberPanel.add(contactMemberLbl);

		ImageIcon issueImg = new ImageIcon("pictures/issueBook.png");
		issueButton = new JButton("Issue Book", issueImg);
		issueButton.setBounds(248, 167, 166, 42);
		memberPanel.add(issueButton);

		showMemberBtn = new JButton("Show Member");
		showMemberBtn.setBounds(21, 130, 117, 23);
		memberPanel.add(showMemberBtn);

		memberNameLbl = new JLabel("Member Name");
		memberNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		memberNameLbl.setBounds(261, 45, 347, 28);
		memberPanel.add(memberNameLbl);
		memberNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));

		renewSubmisionPanel = new Panel();
		renewSubmisionPanel.setForeground(Color.WHITE);
		tabbedPane.addTab("Renew / Submission", null, renewSubmisionPanel, null);
		renewSubmisionPanel.setLayout(null);

		JLabel enterBookIdRenewLbl = new JLabel("Enter Book ID:");
		enterBookIdRenewLbl.setHorizontalAlignment(SwingConstants.CENTER);
		enterBookIdRenewLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		enterBookIdRenewLbl.setBounds(140, 0, 380, 35);
		renewSubmisionPanel.add(enterBookIdRenewLbl);

		enterBookIdRenewField = new JTextField();
		enterBookIdRenewField.setBounds(210, 45, 128, 35);
		renewSubmisionPanel.add(enterBookIdRenewField);
		enterBookIdRenewField.setColumns(10);

		ImageIcon goImg = new ImageIcon("pictures/go.png");
		enterBookIdRenewButton = new JButton(goImg);
		enterBookIdRenewButton.setBounds(354, 45, 93, 35);
		renewSubmisionPanel.add(enterBookIdRenewButton);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 85, 626, 308);
		renewSubmisionPanel.add(scrollPane);

		bookInformationList = new JList<>();
		scrollPane.setViewportView(bookInformationList);

		ImageIcon renewImg = new ImageIcon("pictures/renewBook.png");
		renewButton = new JButton("Renew", renewImg);
		renewButton.setBounds(140, 404, 149, 26);
		renewSubmisionPanel.add(renewButton);

		ImageIcon submissionImg = new ImageIcon("pictures/submission.png");
		submissionButton = new JButton("Submission", submissionImg);
		submissionButton.setBounds(371, 404, 149, 26);
		renewSubmisionPanel.add(submissionButton);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		fileMenu.add(mntmClose);

		editMenu = new JMenu("Edit");
		menuBar.add(editMenu);

		editMenu.add(mntmAddMember);

		editMenu.add(mntmAddBook);

		editMenu.add(mntmViewMember);

		editMenu.add(mntmViewBooks);

	}

	private void clearBookCache() {

		bookNameLbl.setText("");
		bookAuthorLbl.setText("");
		avaiabilityLbl.setText("");

	}

	private void clearMemberCache() {

		memberNameLbl.setText("");
		contactMemberLbl.setText("");

	}

	private void activateListeners() {

		viewMembersButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {

						new memberList().setVisible(true);
					}
				});

			}
		});

		viewBooksButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {

						new bookList().setVisible(true);
					}
				});

			}
		});

		addMemberButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {

						memberAdd = new addMember();
						memberAdd.setTitle("Add New Member");
						memberAdd.setVisible(true);
					}
				});
			}
		});

		addBookButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {

						bookAdd = new addBook();
						bookAdd.setTitle("Add New Book");
						bookAdd.setVisible(true);
					}
				});
			}
		});

		settingsBottun.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {

						new settings().setVisible(true);

					}
				});

			}
		});

		showMemberBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				clearMemberCache();

				String id = enterMemberIdField.getText();
				String qu = "SELECT * FROM MEMBER WHERE id = '" + id + "'";
				ResultSet rs = databaseHandler.execQuery(qu);
				Boolean flag = false;

				try {

					while (rs.next()) {

						String memberName = rs.getString("name");
						String memberMobile = rs.getString("password");

						memberNameLbl.setText(memberName);
						contactMemberLbl.setText(memberMobile);

						flag = true;

					}

					if (!flag) {

						memberNameLbl.setText("No Such Member Available");
					}

				} catch (SQLException ex) {

					ex.printStackTrace();
				}

			}
		});

		showBookButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				clearBookCache();

				String id = enterBookIDField.getText();
				String qu = "SELECT * FROM BOOK WHERE id = '" + id + "'";
				ResultSet rs = databaseHandler.execQuery(qu);
				Boolean flag = false;

				try {

					while (rs.next()) {

						String bookName = rs.getString("title");
						String bookAuthor = rs.getString("author");
						String bookStatus = rs.getString("isAvail");

						bookNameLbl.setText(bookName);
						bookAuthorLbl.setText(bookAuthor);

						String status = null;
						if (bookStatus == "true") {
							status = "Available";
						} else {
							status = "Not Available";
						}
						avaiabilityLbl.setText(status);

						flag = true;

					}

					if (!flag) {

						bookNameLbl.setText("No Such Book Available");
					}

				} catch (SQLException ex) {

					ex.printStackTrace();
				}
			}
		});

		issueButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String memberID = enterMemberIdField.getText();
				String bookID = enterBookIDField.getText();

				int issueButton = JOptionPane.YES_NO_OPTION;
				int issueResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to issue the book "
						+ bookNameLbl.getText() + "\n to " + memberNameLbl.getText(), "Confirmation", issueButton);
				if (issueResult == JOptionPane.YES_OPTION) {

					String str = "INSERT INTO ISSUE(memberID,bookID) VALUES (" + "'" + memberID + "'," + "'" + bookID
							+ "')";

					String str2 = "UPDATE BOOK SET isAvail = false WHERE id = '" + bookID + "'";

					System.out.println(str + "\n" + str2);

					if (databaseHandler.execAction(str) && databaseHandler.execAction(str2)) {

						JOptionPane.showMessageDialog(null, "Book issue Complete");
					} else {
						JOptionPane.showMessageDialog(null, "Issue Operation Failed", "Error",
								JOptionPane.ERROR_MESSAGE);

					}

				} else {
					JOptionPane.showMessageDialog(null, "Issue Operation canceled by user");
				}

			}
		});

		enterBookIdRenewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isReadyForSubmission = false;
				model = new DefaultListModel<>();
				model.removeAllElements();
				bookInformationList.setModel(model);

				String id = enterBookIdRenewField.getText();
				String qu = "SELECT * FROM ISSUE WHERE bookID ='" + id + "'";
				ResultSet rs = databaseHandler.execQuery(qu);

				try {
					while (rs.next()) {
						String mBookId = id;
						String mMemberId = rs.getString("memberID");
						Timestamp mIssueTime = rs.getTimestamp("issueTime");
						int mRenewCount = rs.getInt("renew_count");

						model.addElement("Issue Date and Time: " + mIssueTime.toGMTString());
						model.addElement("Renew Count: " + mRenewCount);
						model.addElement("");
						model.addElement("Book Information: ");

						qu = "SELECT * FROM BOOK WHERE ID = '" + mBookId + "'";
						ResultSet rs1 = databaseHandler.execQuery(qu);
						while (rs1.next()) {
							model.addElement("\tBook Name: " + rs1.getString("title"));
							model.addElement("\tBook Author: " + rs1.getString("author"));
							model.addElement("\tBook ID: " + rs1.getString("id"));
							model.addElement("\tBook Publisher: " + rs1.getString("publisher"));
						}
						model.addElement("");
						model.addElement("Member Information: ");
						qu = "SELECT * FROM MEMBER WHERE ID = '" + mMemberId + "'";
						rs1 = databaseHandler.execQuery(qu);
						while (rs1.next()) {

							model.addElement("\tName: " + rs1.getString("name"));
							model.addElement("\tMobile: " + rs1.getString("mobile"));
							model.addElement("\tEmail: " + rs1.getString("email"));

						}

						isReadyForSubmission = true;
						bookInformationList.setModel(model);

					}

				} catch (SQLException ex) {
					ex.printStackTrace();
				}

			}
		});

		submissionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!isReadyForSubmission) {
					JOptionPane.showMessageDialog(null, "Please select a Book to submit", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				int submissionButton = JOptionPane.YES_NO_OPTION;
				int submissionResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to return the book?",
						"Confirmation", submissionButton);

				if (submissionResult == JOptionPane.YES_OPTION) {
					String id = enterBookIdRenewField.getText();
					String ac1 = "DELETE FROM ISSUE WHERE bookID = '" + id + "'";
					String ac2 = "UPDATE BOOK SET isAvail = TRUE WHERE ID = '" + id + "'";

					if (databaseHandler.execAction(ac1) && databaseHandler.execAction(ac2)) {

						JOptionPane.showMessageDialog(null, "Book Has Been Submitted");
					} else {
						JOptionPane.showMessageDialog(null, "Submission Has Failed", "Error",
								JOptionPane.ERROR_MESSAGE);

					}
				} else {
					JOptionPane.showMessageDialog(null, "Submission Has Been Cancelled by User", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		renewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!isReadyForSubmission) {
					JOptionPane.showMessageDialog(null, "Please select a Book to submit", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				int renewButton = JOptionPane.YES_NO_OPTION;
				int renewResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to renew the book?",
						"Confirmation", renewButton);

				if (renewResult == JOptionPane.YES_OPTION) {

					String ac1 = "UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP, renew_count = renew_count+1 WHERE bookID = '"
							+ enterBookIdRenewField.getText() + "'";

					System.out.println(ac1);

					if (databaseHandler.execAction(ac1)) {
						JOptionPane.showMessageDialog(null, "Book Has Been Renew");
					} else {
						JOptionPane.showMessageDialog(null, "Book Renew Has Failed", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(null, "Renew Has Been Cancelled by User", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		mntmClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.exit(0);

			}
		});

		mntmAddBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				addBookButton.doClick();
			}
		});

		mntmAddMember.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				addMemberButton.doClick();
			}
		});
		mntmViewBooks.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				viewBooksButton.doClick();
			}
		});

		mntmViewMember.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				viewMembersButton.doClick();
			}
		});

	}
}
