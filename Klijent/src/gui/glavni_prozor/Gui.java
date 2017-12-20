package gui.glavni_prozor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import gui.guiKontrolor.GUIKontrolor;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class Gui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private static JMenuItem mntmUpload;
	private JMenuItem mntmDownload;
	private static JMenuItem mntmList;
	private JMenuItem mntmExit;
	private static JMenu mnClient;
	private static JMenu mnClientname;
	private static JMenuItem mntmSignIn;
	private JMenuItem mntmSignOut;
	private JMenuItem mntmLogOut;
	private JButton btnDownload;
	private static JButton btnListFiles;
	private JScrollPane scrollPane;
	private static JTextArea textArea;
	private JScrollPane scrollPane_1;
	private static JTextArea textArea_1;
	private JLabel lblText;
	private JLabel lblListFiles;
	private static JTextField textField;
	private JLabel lblKey;
	private JLabel label;
	private static JButton btnUpload;


	/**
	 * Create the frame.
	 */
	public Gui() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 839, 575);
		setJMenuBar(getMenuBar_1());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnDownload());
		contentPane.add(getBtnListFiles());
		contentPane.add(getScrollPane());
		contentPane.add(getScrollPane_1());
		contentPane.add(getLblText());
		contentPane.add(getLblListFiles());
		contentPane.add(getTextField());
		contentPane.add(getLblKey());
		contentPane.add(getLabel());
		contentPane.add(getBtnUpload());
		provare();
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				update(e);
			}

			public void removeUpdate(DocumentEvent e) {
				update(e);
			}

			public void insertUpdate(DocumentEvent e) {
				update(e);
			}

			public void update(DocumentEvent e) {
				if (textArea.getText().length() > 500) {
					GUIKontrolor.greska("Ne mozete uneti vise od 500 karaktera");
					DocumentEvent.EventType type = e.getType();
					String replace = textArea.getText();
					replace = replace.substring(0, replace.length() - 1);
					final String rep = replace;

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							textArea.setText(rep);
						}
					});

				}
				label.setText(textArea.getText().length() + "/ 500");

			}
		});
	}

	public static void provare() {
		if(GUIKontrolor.register){
			mnClientname.setText(GUIKontrolor.username);
			mnClientname.setVisible(true);
			mnClient.setVisible(false);
			btnUpload.setEnabled(true);
			btnListFiles.setEnabled(true);
			mntmList.setEnabled(true);
			mntmUpload.setEnabled(true);
		}else{
			mnClientname.setVisible(false);
			mnClient.setVisible(true);
			btnUpload.setEnabled(false);
			btnListFiles.setEnabled(false);
			mntmList.setEnabled(false);
			mntmUpload.setEnabled(false);
		}
		
	}

	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnFile());
			menuBar.add(getMnClient());
			menuBar.add(getMnClientname());
		}
		return menuBar;
	}

	private JMenu getMnFile() {
		if (mnFile == null) {
			mnFile = new JMenu("File");
			mnFile.add(getMntmDownload());
			mnFile.add(getMntmUpload());
			mnFile.add(getMntmList());
			mnFile.add(getMntmExit());
		}
		return mnFile;
	}

	private JMenuItem getMntmUpload() {
		if (mntmUpload == null) {
			mntmUpload = new JMenuItem("Upload");
			mntmUpload.setIcon(new ImageIcon(Gui.class.getResource("/upload.png")));
			mntmUpload.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					uploadText();
				}
			});
			mntmUpload.setEnabled(false);
		}
		return mntmUpload;
	}

	private JMenuItem getMntmDownload() {
		if (mntmDownload == null) {
			mntmDownload = new JMenuItem("Download");
			mntmDownload.setIcon(new ImageIcon(Gui.class.getResource("/download1.png")));
			mntmDownload.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					download();
				}
			});
		}
		return mntmDownload;
	}

	private JMenuItem getMntmList() {
		if (mntmList == null) {
			mntmList = new JMenuItem("List");
			mntmList.setIcon(new ImageIcon(Gui.class.getResource("/list.png")));
			mntmList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					list();
				}
			});
			mntmList.setEnabled(false);
		}
		return mntmList;
	}

	private JMenuItem getMntmExit() {
		if (mntmExit == null) {
			mntmExit = new JMenuItem("Exit");
			mntmExit.setIcon(new ImageIcon(Gui.class.getResource("/exit.png")));
			mntmExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					GUIKontrolor.ugasiApp();
				}
			});
		}

		return mntmExit;
	}

	private JMenu getMnClient() {
		if (mnClient == null) {
			mnClient = new JMenu("Client");
			mnClient.add(getMntmSignIn());
			mnClient.add(getMntmSignOut());
		}
		return mnClient;
	}

	private JMenu getMnClientname() {
		if (mnClientname == null) {
			mnClientname = new JMenu("ClientName");
			mnClientname.setVisible(false);
			mnClientname.add(getMntmLogOut());
		}
		return mnClientname;
	}

	private JMenuItem getMntmSignIn() {
		if (mntmSignIn == null) {
			mntmSignIn = new JMenuItem("Sign in");
			mntmSignIn.setIcon(new ImageIcon(Gui.class.getResource("/in.png")));
			mntmSignIn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Singin_up.openFrame(1);
					
				}
			});
		}
		return mntmSignIn;
	}

	private JMenuItem getMntmSignOut() {
		if (mntmSignOut == null) {
			mntmSignOut = new JMenuItem("Sign up");
			mntmSignOut.setIcon(new ImageIcon(Gui.class.getResource("/new.png")));
			mntmSignOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Singin_up.openFrame(2);
					
				}
			});
		}
		return mntmSignOut;
	}

	private JMenuItem getMntmLogOut() {
		if (mntmLogOut == null) {
			mntmLogOut = new JMenuItem("Log out");
			mntmLogOut.setIcon(new ImageIcon(Gui.class.getResource("/logout.png")));
			mntmLogOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					logout();
				}
			});
		}
		return mntmLogOut;
	}

	private JButton getBtnDownload() {
		if (btnDownload == null) {
			btnDownload = new JButton("Download");
			btnDownload.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					download();
					
				}

			});
			btnDownload.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnDownload.setBounds(702, 96, 121, 44);
		}
		return btnDownload;
	}

	private JButton getBtnListFiles() {
		if (btnListFiles == null) {
			btnListFiles = new JButton("List files");
			btnListFiles.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					liste();
				}
			});
			btnListFiles.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnListFiles.setBounds(702, 376, 121, 44);
		}
		return btnListFiles;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 96, 408, 353);
			scrollPane.setViewportView(getTextArea());
		}
		return scrollPane;
	}

	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setWrapStyleWord(true);
			textArea.setLineWrap(true);
		}
		return textArea;
	}

	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(492, 96, 184, 353);
			scrollPane_1.setViewportView(getTextArea_1());
		}
		return scrollPane_1;
	}

	private JTextArea getTextArea_1() {
		if (textArea_1 == null) {
			textArea_1 = new JTextArea();
			textArea_1.setEnabled(false);
			textArea_1.setEditable(false);
		}
		return textArea_1;
	}

	private JLabel getLblText() {
		if (lblText == null) {
			lblText = new JLabel("Text");
			lblText.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblText.setBounds(10, 63, 76, 22);
		}
		return lblText;
	}

	private JLabel getLblListFiles() {
		if (lblListFiles == null) {
			lblListFiles = new JLabel("List files");
			lblListFiles.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblListFiles.setBounds(492, 63, 76, 21);
		}
		return lblListFiles;
	}

	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setBounds(77, 8, 131, 20);
			textField.setColumns(10);
		}
		return textField;
	}

	private JLabel getLblKey() {
		if (lblKey == null) {
			lblKey = new JLabel("Key:");
			lblKey.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblKey.setBounds(10, 11, 57, 17);
		}
		return lblKey;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("0/ 500");
			label.setFont(new Font("Tahoma", Font.PLAIN, 13));
			label.setBounds(428, 434, 54, 15);
		}
		return label;
	}

	private void liste() {
		LinkedList<String> lista = GUIKontrolor.list(GUIKontrolor.username);
		if (lista == null || lista.size() == 0) {
			GUIKontrolor.greska("Nemate nijedan file!");
		} else {
			textArea_1.setText("Vasi fajlovi su:\n");
			for (String string : lista) {
				textArea_1.setText(textArea_1.getText()  + string + "\n");
			}
		}
	}

	private void download() {
		String key = textField.getText();
		
		String putanja = sacuvajFile();
		if(putanja==null || putanja.equals("")) return;
		
		
		String message = GUIKontrolor.download(key,putanja);
		if (message.equals("\\nokey") || message.equals("bad")) {
			GUIKontrolor.greska("Niste uneli odgovarajuci key");
			textField.setFocusable(true);
		} else {
			textArea.setText(message);
		}

	}
	
	private String sacuvajFile(){
		String putanja;
		JFileChooser jf = new JFileChooser();
		int odgovor;
		while(true){
			odgovor = jf.showSaveDialog(this);
			if(odgovor==JFileChooser.APPROVE_OPTION){
				putanja = jf.getSelectedFile().getPath();
				if(!(putanja.equals("") && putanja==null)){
					return putanja;
				}
			}else{
				break;
			}
		}
		return null;
	}

	private void uploadText() {
		String message = textArea.getText();		
		if (message == null || message.equals("")) {
			GUIKontrolor.greska("Moreate uneti tekst u polje!");
		} else {
			String key = GUIKontrolor.upload(message);
			if (key == null || key.equals("")) {
				GUIKontrolor.greska("Doslo je do greske prilikom preuzimanja koda");
			} else {
				textField.setText(key);
			}
		}
	}
	private void logout(){
		GUIKontrolor.register=false;
		GUIKontrolor.username=null;
		provare();
	}
	private JButton getBtnUpload() {
		if (btnUpload == null) {
			btnUpload = new JButton("Upload");
			btnUpload.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					uploadText();
				}
			});
			btnUpload.setBounds(702, 239, 121, 44);
			btnUpload.setFont(new Font("Tahoma", Font.PLAIN, 18));
			
		}
		return btnUpload;
	}
}
