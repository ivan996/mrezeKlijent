package gui.glavni_prozor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.guiKontrolor.GUIKontrolor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Singin_up extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUsername;
	private JPasswordField passwordField;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JButton btnSing_in_up;
	private JButton btnCancel;
	private JLabel lblUnesiteKosisnickoIme;
	private int value;

	// true -> in false ->up
	/**
	 * Launch the application.
	 */
	public static void openFrame(int value) {
		Singin_up frame = new Singin_up(value);
		frame.setVisible(true);
		frame.setLocationRelativeTo(GUIKontrolor.glavni);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frame.dispose();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Singin_up(int value) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 338, 249);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getTextFieldUsername());
		contentPane.add(getPasswordField());
		contentPane.add(getLblUsername());
		contentPane.add(getLblPassword());
		contentPane.add(getBtnSing_in_up());
		contentPane.add(getBtnCancel());
		contentPane.add(getLblUnesiteKosisnickoIme());
		if(value==1){
			btnSing_in_up.setText("Sing in");
		}else{
			btnSing_in_up.setText("Sing up");
		}
		this.value = value;
	}

	private JTextField getTextFieldUsername() {
		if (textFieldUsername == null) {
			textFieldUsername = new JTextField();
			textFieldUsername.setBounds(103, 45, 126, 31);
			textFieldUsername.setColumns(10);
		}
		return textFieldUsername;
	}

	private JPasswordField getPasswordField() {
		if (passwordField == null) {
			passwordField = new JPasswordField();
			passwordField.setBounds(103, 106, 126, 31);
		}
		return passwordField;
	}

	private JLabel getLblUsername() {
		if (lblUsername == null) {
			lblUsername = new JLabel("Username");
			lblUsername.setBounds(10, 45, 75, 31);
		}
		return lblUsername;
	}

	private JLabel getLblPassword() {
		if (lblPassword == null) {
			lblPassword = new JLabel("Password");
			lblPassword.setBounds(10, 106, 75, 31);
		}
		return lblPassword;
	}

	private JButton getBtnSing_in_up() {
		if (btnSing_in_up == null) {
			btnSing_in_up = new JButton("");
			
			btnSing_in_up.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sing();
				}
			});
			btnSing_in_up.setBounds(31, 160, 100, 31);
		}
		return btnSing_in_up;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			btnCancel.setBounds(188, 160, 100, 31);
		}
		return btnCancel;
	}

	private JLabel getLblUnesiteKosisnickoIme() {
		if (lblUnesiteKosisnickoIme == null) {
			lblUnesiteKosisnickoIme = new JLabel("Unesite kosisnicko ime i lozinku");
			lblUnesiteKosisnickoIme.setBounds(90, 11, 198, 23);
		}
		return lblUnesiteKosisnickoIme;
	}

	public void sing() {
		if(value==1){
			singin();
		}else{
			singup();
		}
	}
	public void singin(){
		String username = textFieldUsername.getText();
		String pass= new String(passwordField.getPassword());
		if(username.equals("") || username==null || pass.equals("") || pass==null){
			GUIKontrolor.greska("Morate da unesete podatke!");
		}else{
			int provera = GUIKontrolor.singin(username, pass);
			if(provera ==1){
				GUIKontrolor.greska("Uspesno ste se ulogovali!");
				GUIKontrolor.glavni.provare();
				dispose();
			}else if(provera==0){
				GUIKontrolor.greska("Uneli ste pogresne podatke!");
				textFieldUsername.setText("");
				passwordField.setText("");
			}
		}
		
	}
	public void singup(){
		String username = textFieldUsername.getText();
		String pass= new String(passwordField.getPassword());
		if(username.equals("") || username==null || pass.equals("") || pass==null){
			GUIKontrolor.greska("Morate da unesete podatke!");
		}else{
			int provera =  GUIKontrolor.singup(username, pass);
			if(provera==1){
				GUIKontrolor.greska("Uspesno ste se registrovali!");
				GUIKontrolor.glavni.provare();
				dispose();
			}else if(provera==0){
				GUIKontrolor.greska("Uneli ste postojece korisnicko ime!");
				textFieldUsername.setText("");
				passwordField.setText("");
			}
		}
	}
}
