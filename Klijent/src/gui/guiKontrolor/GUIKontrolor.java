package gui.guiKontrolor;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import gui.glavni_prozor.Gui;

public class GUIKontrolor {
	public static Gui glavni = new Gui();
	public static Socket communicationSocket = null;
	public static BufferedReader serverInput = null;
	public static PrintStream serverOutput = null;
	public static InputStream fileInput = null;
	public static boolean register = false;
	public static String username = null;

	public static void main(String[] args) {

		try {
			communicationSocket = new Socket("localhost", 9001);
			serverInput = new BufferedReader(new InputStreamReader(communicationSocket.getInputStream()));
			serverOutput = new PrintStream(communicationSocket.getOutputStream());
			fileInput = communicationSocket.getInputStream();
			String mess = serverInput.readLine();
			if (mess.equals("\\connectionok")) {
				greska("Uspesna konekcija sa serverom!");
			} else {
				greska("Doslo je do greske u konekciji sa serverom!");
			}
		} catch (UnknownHostException e1) {
			greska("UNKNOWN HOST");
			System.exit(0);
		} catch (IOException e1) {
			greska("SERVER IS DOWN");
			System.exit(0);
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					glavni = new Gui();
					glavni.setVisible(true);
					glavni.setLocationRelativeTo(null);
					glavni.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(java.awt.event.WindowEvent e) {
							GUIKontrolor.ugasiApp();
						}
					});

				} catch (Exception e) {
					greska("Doslo je do greske prilikom otvaranja programa.");
				}
			}
		});
	}

	public static void ugasiApp() {
		int odg = JOptionPane.showConfirmDialog(glavni, "Da li zelite da izadjete", "Izlaz", JOptionPane.YES_NO_OPTION);

		if (odg == JOptionPane.YES_OPTION) {
			try {
				serverOutput.println("-1");
				communicationSocket.close();
				System.exit(0);
			} catch (IOException e) {
				greska("Doslo je do greske sa serverom");
				System.exit(0);
			}

		}
	}

	public static void greska(String message) {
		JOptionPane.showConfirmDialog(glavni, message, "Obavestenje", JOptionPane.OK_CANCEL_OPTION);

	}

	public static String download(String key, String putanja) {
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(putanja, "rw");
			int n;
			byte[] bafer = new byte[1024];
			serverOutput.println("1");
			serverOutput.println(key);
			String mes = serverInput.readLine();
			if (mes == null || mes.equals("")) {
				return "bad";
			}
			if (mes.equals("\\nokey")) {
				return "\\nokey";
			} else {
				while(true){
					n = fileInput.read(bafer);
					if(n==-1){
						break;
					}
					randomAccessFile.write(bafer,0,n);
				}
				randomAccessFile.close();
				greska("File je uspesno sacuvan u file sistem!");
				BufferedReader fileIn = new BufferedReader(new FileReader(putanja));
				String mess=fileIn.readLine();
				fileIn.close();
				return mess;
				
			}
			
			
			
		} catch (IOException e) {
			greska("Doslo je do greske na serveru!");
			return null;
		}
	}

	public static String upload(String message) {
		try {
			serverOutput.println("0");
			serverOutput.println(message);
			String key = serverInput.readLine();
			return key.substring(5);
		} catch (IOException e) {
			greska("Doslo je do greske na serveru!");
			return null;
		}

	}

	public static LinkedList<String> list(String username) {
		String mes="";
		LinkedList<String> list = new LinkedList<>();
		serverOutput.println("2");
		
		try {
			mes=serverInput.readLine();
			while (!mes.equals("\\endlist")){
				list.add(mes);
				mes = serverInput.readLine();
			} 
			return list;
		} catch (IOException e) {
			greska("Doslo je do greske prilikom preuzimanja liste fajlova");
			return null;
		}
	}

	public static boolean singin(String usernam, String pass) {
		try {
			serverOutput.println("3");
			serverOutput.println(usernam);
			serverOutput.println(pass);
			String mes = serverInput.readLine();
			if (mes.equals("\\clientok")) {
				username = usernam;
				register = true;
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			greska("Doslo je do greske na serveru!");
			return false;
		}
	}

	public static boolean singup(String usernam, String pass) {
		try {
			serverOutput.println("4");
			serverOutput.println(usernam);
			serverOutput.println(pass);
			String mes = serverInput.readLine();
			if (mes.equals("\\okusername")) {
				username = usernam;
				register = true;
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			greska("Doslo je do greske na serveru!");
			return false;
		}
	}

}
