import Database.DatabaseHandler;
import View.MainFrame;
import View.login;

public class App {

	public static void main(String[] args) {
		
		DatabaseHandler.getInstance();

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				new login().setVisible(true);
			}
		});
	}
}
