package Model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class User {
	
	/**
	 * Klasa user predstavlja korisnika i njegov konfig file za prijavu u sustav. 
	 * kroz sucelje samog programam moguce je mijenjati konfig file.
	 * 
	 */

	public static final String CONFIG_FILE = "config.txt";

	int numOfDayswihtoutFine;
	float finePerDay;
	String username;
	String password;

	public User() {

		numOfDayswihtoutFine = 14;
		finePerDay = 2;
		username = "admin";
		password = "admin";

	}

	public int getNumOfDayswihtoutFine() {
		return numOfDayswihtoutFine;
	}

	public void setNumOfDayswihtoutFine(int numOfDayswihtoutFine) {
		this.numOfDayswihtoutFine = numOfDayswihtoutFine;
	}

	public float getFinePerDay() {
		return finePerDay;
	}

	public void setFinePerDay(float finePerDay) {
		this.finePerDay = finePerDay;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static void initConfig() {

		Writer writer = null;

		try {

			User user = new User();
			Gson gson = new Gson();
			writer = new FileWriter(CONFIG_FILE);
			gson.toJson(user, writer);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e2) {
				// TODO: handle exception
			}
		}

	}

	public static User getUser() {

		Gson gson = new Gson();
		User user = new User();
		try {
			user = gson.fromJson(new FileReader(CONFIG_FILE), User.class);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			initConfig();
			e.printStackTrace();
		}
		return user;

	}

	public static void writeUserToFile(User user) {

		Writer writer = null;

		try {

			Gson gson = new Gson();
			writer = new FileWriter(CONFIG_FILE);
			gson.toJson(user, writer);
			
			JOptionPane.showMessageDialog(null, "Settigns updated", "Success", JOptionPane.INFORMATION_MESSAGE);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(null, "Failed", "Error", JOptionPane.ERROR_MESSAGE);

		} finally {
			try {
				writer.close();
			} catch (IOException e2) {
				// TODO: handle exception
			}
		}

	}

}
