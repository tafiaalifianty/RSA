import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.math.BigInteger;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;

/*
 * Created by Fox on Thu Mar 25 13:46:54 WITA 2021
 */
//666172
public class Runner extends JFrame {
	public Runner() {
		initComponents();
	}
	//68616e7
	private void buttonRandomizeActionPerformed(ActionEvent e) {
		BigInteger[] pq = Generator.generatePrimes();
		p = pq[0];
		q = pq[1];
		textAreaP.setText(pq[0].toString());
		textAreaQ.setText(pq[1].toString());
		labelGenerateNotif.setText("Randomized P & Q");
	}

	private void buttonGoGenerateActionPerformed(ActionEvent e) {
		frameGenerate.setVisible(true);
	}
	
	private void buttonGoEncryptActionPerformed(ActionEvent e) {
		frameEncrypt.setVisible(true);
	}

	private void buttonGoDecryptActionPerformed(ActionEvent e) {
		frameDecrypt.setVisible(true);
	}

	private void buttonGenerateActionPerformed(ActionEvent e) {
		BigInteger[] trinity = RSA.generateTrinity(p, q);
		Generator.generateKeyFile(trinity);
		labelGenerateNotif.setText("Succesfully generated key.pri & key.pub");
	}

	private void buttonChooseFileToEncryptActionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		int r = fc.showOpenDialog(null);

		if (r == JFileChooser.APPROVE_OPTION) {
			fileToEncrypt = fc.getSelectedFile();
			textFieldLocationToEncrypt.setText(fileToEncrypt.getAbsolutePath());

			try {
				String textLine;
				FileReader fr = new FileReader(fileToEncrypt);
				BufferedReader reader = new BufferedReader(fr);
				while((textLine=reader.readLine()) != null){
						textAreaToEnPreview.read(reader, "-");
				}
			}
			catch (IOException ioe) {
					System.err.println(ioe);
					System.exit(1);
			}

		}
	}

	private void buttonChooseFileToPubKeyActionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Public Key (*.pub)", "pub"));
		int r = fc.showOpenDialog(null);
		// if the user selects a file
		if (r == JFileChooser.APPROVE_OPTION) {
			filePubKey = fc.getSelectedFile();
			textFieldLocationToEncryptKey.setText(filePubKey.getAbsolutePath());
		}
	}

	private void buttonEncryptActionPerformed(ActionEvent e) {
		String toDisplay = "";
		String duration = "";
		Instant start = Instant.now();
		try {
			ArrayList<String> hexed = RSA.encryptFile(fileToEncrypt, filePubKey);
			for(String s : hexed){
				toDisplay += s;
			}
		} finally {
			Instant end = Instant.now();
			Duration d = Duration.between(start, end);
			duration = String.format("Encrypt time: %d,%d (seconds)", d.getSeconds(), d.getNano());
		}
		textAreaEncryptedPreview.setText(toDisplay);
		// labelEncryptNotif.setText(duration + ", size: " + toDisplay.getBytes().length);
		labelEncryptNotif.setText(duration);
	}
	// fx
	private void buttonChooseFileToDecryptActionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		int r = fc.showOpenDialog(null);
		if (r == JFileChooser.APPROVE_OPTION) {
			fileToDecrypt = fc.getSelectedFile();
			textFieldLocationToDecrypt.setText(fileToDecrypt.getAbsolutePath());
		}
	}

	private void buttonChooseFileToPrivKeyActionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Private Key (*.pri)", "pri"));
		int r = fc.showOpenDialog(null);
		// if the user selects a file
		if (r == JFileChooser.APPROVE_OPTION) {
			filePrivKey = fc.getSelectedFile();
			textFieldLocationToDecryptKey.setText(filePrivKey.getAbsolutePath());
		}
	}

	private void buttonDecryptActionPerformed(ActionEvent e) {
		String duration = "";
		Instant start = Instant.now();
		try {
			RSA.decryptFile(fileToDecrypt, filePrivKey);
		} finally {
			Instant end = Instant.now();
			Duration d = Duration.between(start, end);
			duration = String.format("Decrypt time: %d,%d (seconds)", d.getSeconds(), d.getNano());
		}
		labelDecryptNotif.setText(duration);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Farhan Ramdhani
		labelMainmenu = new JLabel();
		buttonGoGenerate = new JButton();
		buttonGoEncrypt = new JButton();
		buttonGoDecrypt = new JButton();
		frameGenerate = new JFrame();
		labelP = new JLabel();
		scrollPane1 = new JScrollPane();
		textAreaP = new JTextArea();
		labelQ = new JLabel();
		scrollPane2 = new JScrollPane();
		textAreaQ = new JTextArea();
		buttonRandomize = new JButton();
		buttonGenerate = new JButton();
		labelGenerateNotif = new JLabel();
		frameEncrypt = new JFrame();
		labelToEncrypt = new JLabel();
		textFieldLocationToEncrypt = new JTextField();
		buttonChooseFileToEncrypt = new JButton();
		scrollPane3 = new JScrollPane();
		textAreaToEnPreview = new JTextArea();
		label9 = new JLabel();
		textFieldLocationToEncryptKey = new JTextField();
		buttonChooseFileToPubKey = new JButton();
		buttonEncrypt = new JButton();
		labelHex = new JLabel();
		scrollPane4 = new JScrollPane();
		textAreaEncryptedPreview = new JTextArea();
		labelEncryptNotif = new JLabel();
		frameDecrypt = new JFrame();
		label5 = new JLabel();
		textFieldLocationToDecrypt = new JTextField();
		buttonChooseFileToDecrypt = new JButton();
		label6 = new JLabel();
		textFieldLocationToDecryptKey = new JTextField();
		buttonChooseFileToPrivKey = new JButton();
		buttonDecrypt = new JButton();
		labelDecryptNotif = new JLabel();

		//======== this ========
		setTitle("RSA Menu");
		setMinimumSize(new Dimension(200, 100));
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());

		//---- labelMainmenu ----
		labelMainmenu.setText("Main Menu");
		labelMainmenu.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(labelMainmenu, new GridBagConstraints(0, 1, 7, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//---- buttonGoGenerate ----
		buttonGoGenerate.setText("Generate Keys");
		buttonGoGenerate.addActionListener(e -> buttonGoGenerateActionPerformed(e));
		contentPane.add(buttonGoGenerate, new GridBagConstraints(1, 3, 5, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));

		//---- buttonGoEncrypt ----
		buttonGoEncrypt.setText("Encrypt File");
		buttonGoEncrypt.addActionListener(e -> buttonGoEncryptActionPerformed(e));
		contentPane.add(buttonGoEncrypt, new GridBagConstraints(1, 4, 5, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));

		//---- buttonGoDecrypt ----
		buttonGoDecrypt.setText("Decrypt FIle");
		buttonGoDecrypt.addActionListener(e -> buttonGoDecryptActionPerformed(e));
		contentPane.add(buttonGoDecrypt, new GridBagConstraints(1, 5, 5, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		setSize(235, 245);
		setLocationRelativeTo(getOwner());

		//======== frameGenerate ========
		{
			frameGenerate.setTitle("Generate Key");
			frameGenerate.setMinimumSize(new Dimension(300, 300));
			Container frameGenerateContentPane = frameGenerate.getContentPane();
			frameGenerateContentPane.setLayout(new GridBagLayout());
			((GridBagLayout)frameGenerateContentPane.getLayout()).columnWidths = new int[] {0, 104, 0, 0, 0, 0, 0, 0, 0, 0};
			((GridBagLayout)frameGenerateContentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 16, 0, 11, 0};
			((GridBagLayout)frameGenerateContentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0};

			//---- labelP ----
			labelP.setText("P (prime)");
			frameGenerateContentPane.add(labelP, new GridBagConstraints(1, 1, 9, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//======== scrollPane1 ========
			{

				//---- textAreaP ----
				textAreaP.setLineWrap(true);
				textAreaP.setMinimumSize(new Dimension(400, 70));
				textAreaP.setPreferredSize(new Dimension(400, 70));
				scrollPane1.setViewportView(textAreaP);
			}
			frameGenerateContentPane.add(scrollPane1, new GridBagConstraints(1, 2, 7, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- labelQ ----
			labelQ.setText("Q (prime)");
			frameGenerateContentPane.add(labelQ, new GridBagConstraints(1, 3, 9, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//======== scrollPane2 ========
			{

				//---- textAreaQ ----
				textAreaQ.setLineWrap(true);
				textAreaQ.setMinimumSize(new Dimension(400, 70));
				textAreaQ.setPreferredSize(new Dimension(400, 70));
				scrollPane2.setViewportView(textAreaQ);
			}
			frameGenerateContentPane.add(scrollPane2, new GridBagConstraints(1, 4, 7, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- buttonRandomize ----
			buttonRandomize.setText("Randomize P & Q");
			buttonRandomize.setFont(new Font("Ubuntu", Font.PLAIN, 12));
			buttonRandomize.addActionListener(e -> buttonRandomizeActionPerformed(e));
			frameGenerateContentPane.add(buttonRandomize, new GridBagConstraints(1, 5, 3, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- buttonGenerate ----
			buttonGenerate.setText("Generate");
			buttonGenerate.addActionListener(e -> buttonGenerateActionPerformed(e));
			frameGenerateContentPane.add(buttonGenerate, new GridBagConstraints(1, 7, 7, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- labelGenerateNotif ----
			labelGenerateNotif.setText("Insert p,q then click Generate");
			labelGenerateNotif.setHorizontalAlignment(SwingConstants.LEFT);
			frameGenerateContentPane.add(labelGenerateNotif, new GridBagConstraints(1, 8, 7, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			frameGenerate.pack();
			frameGenerate.setLocationRelativeTo(frameGenerate.getOwner());
		}

		//======== frameEncrypt ========
		{
			frameEncrypt.setTitle("Encrypt File");
			Container frameEncryptContentPane = frameEncrypt.getContentPane();
			frameEncryptContentPane.setLayout(new GridBagLayout());
			((GridBagLayout)frameEncryptContentPane.getLayout()).columnWidths = new int[] {0, 214, 119, 0};
			((GridBagLayout)frameEncryptContentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

			//---- labelToEncrypt ----
			labelToEncrypt.setText("File to encrypt");
			frameEncryptContentPane.add(labelToEncrypt, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- textFieldLocationToEncrypt ----
			textFieldLocationToEncrypt.setFont(new Font("Ubuntu", Font.PLAIN, 12));
			textFieldLocationToEncrypt.setEnabled(false);
			textFieldLocationToEncrypt.setDisabledTextColor(Color.black);
			frameEncryptContentPane.add(textFieldLocationToEncrypt, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- buttonChooseFileToEncrypt ----
			buttonChooseFileToEncrypt.setText("Choose file..");
			buttonChooseFileToEncrypt.addActionListener(e -> buttonChooseFileToEncryptActionPerformed(e));
			frameEncryptContentPane.add(buttonChooseFileToEncrypt, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//======== scrollPane3 ========
			{
				scrollPane3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				scrollPane3.setMinimumSize(new Dimension(370, 70));
				scrollPane3.setPreferredSize(new Dimension(388, 70));

				//---- textAreaToEnPreview ----
				textAreaToEnPreview.setFont(new Font("Ubuntu", Font.PLAIN, 10));
				textAreaToEnPreview.setLineWrap(true);
				textAreaToEnPreview.setMinimumSize(new Dimension(370, 150));
				textAreaToEnPreview.setPreferredSize(new Dimension(370, 150));
				textAreaToEnPreview.setAutoscrolls(false);
				textAreaToEnPreview.setEnabled(false);
				textAreaToEnPreview.setDisabledTextColor(Color.black);
				scrollPane3.setViewportView(textAreaToEnPreview);
			}
			frameEncryptContentPane.add(scrollPane3, new GridBagConstraints(1, 3, 2, 5, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- label9 ----
			label9.setText("File \"key.pub\"");
			frameEncryptContentPane.add(label9, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- textFieldLocationToEncryptKey ----
			textFieldLocationToEncryptKey.setFont(new Font("Ubuntu", Font.PLAIN, 12));
			textFieldLocationToEncryptKey.setEnabled(false);
			textFieldLocationToEncryptKey.setDisabledTextColor(Color.black);
			frameEncryptContentPane.add(textFieldLocationToEncryptKey, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- buttonChooseFileToPubKey ----
			buttonChooseFileToPubKey.setText("Choose file..");
			buttonChooseFileToPubKey.addActionListener(e -> buttonChooseFileToPubKeyActionPerformed(e));
			frameEncryptContentPane.add(buttonChooseFileToPubKey, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- buttonEncrypt ----
			buttonEncrypt.setText("ENCRYPT");
			buttonEncrypt.addActionListener(e -> buttonEncryptActionPerformed(e));
			frameEncryptContentPane.add(buttonEncrypt, new GridBagConstraints(1, 11, 2, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- labelHex ----
			labelHex.setText("Encrypted (in Hex)");
			frameEncryptContentPane.add(labelHex, new GridBagConstraints(1, 13, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//======== scrollPane4 ========
			{
				scrollPane4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				scrollPane4.setMinimumSize(new Dimension(370, 70));
				scrollPane4.setPreferredSize(new Dimension(388, 70));
				scrollPane4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

				//---- textAreaEncryptedPreview ----
				textAreaEncryptedPreview.setMinimumSize(new Dimension(370, 150));
				textAreaEncryptedPreview.setPreferredSize(new Dimension(370, 150));
				textAreaEncryptedPreview.setLineWrap(true);
				textAreaEncryptedPreview.setEnabled(false);
				textAreaEncryptedPreview.setDisabledTextColor(Color.black);
				scrollPane4.setViewportView(textAreaEncryptedPreview);
			}
			frameEncryptContentPane.add(scrollPane4, new GridBagConstraints(1, 14, 2, 5, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- labelEncryptNotif ----
			labelEncryptNotif.setText("Select both files and hit ENCRYPT button");
			frameEncryptContentPane.add(labelEncryptNotif, new GridBagConstraints(1, 19, 3, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));
			frameEncrypt.pack();
			frameEncrypt.setLocationRelativeTo(frameEncrypt.getOwner());
		}

		//======== frameDecrypt ========
		{
			frameDecrypt.setTitle("Decrypt File");
			frameDecrypt.setMinimumSize(new Dimension(300, 100));
			Container frameDecryptContentPane = frameDecrypt.getContentPane();
			frameDecryptContentPane.setLayout(new GridBagLayout());
			((GridBagLayout)frameDecryptContentPane.getLayout()).columnWidths = new int[] {0, 215, 0, 0};
			((GridBagLayout)frameDecryptContentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 28, 0};

			//---- label5 ----
			label5.setText("File to decrypt");
			frameDecryptContentPane.add(label5, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- textFieldLocationToDecrypt ----
			textFieldLocationToDecrypt.setFont(new Font("Ubuntu", Font.PLAIN, 12));
			textFieldLocationToDecrypt.setEnabled(false);
			textFieldLocationToDecrypt.setDisabledTextColor(Color.black);
			frameDecryptContentPane.add(textFieldLocationToDecrypt, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- buttonChooseFileToDecrypt ----
			buttonChooseFileToDecrypt.setText("Choose file..");
			buttonChooseFileToDecrypt.addActionListener(e -> buttonChooseFileToDecryptActionPerformed(e));
			frameDecryptContentPane.add(buttonChooseFileToDecrypt, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- label6 ----
			label6.setText("File \"key.pri\"");
			frameDecryptContentPane.add(label6, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- textFieldLocationToDecryptKey ----
			textFieldLocationToDecryptKey.setFont(new Font("Ubuntu", Font.PLAIN, 12));
			textFieldLocationToDecryptKey.setEnabled(false);
			textFieldLocationToDecryptKey.setDisabledTextColor(Color.black);
			frameDecryptContentPane.add(textFieldLocationToDecryptKey, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- buttonChooseFileToPrivKey ----
			buttonChooseFileToPrivKey.setText("Choose file..");
			buttonChooseFileToPrivKey.addActionListener(e -> buttonChooseFileToPrivKeyActionPerformed(e));
			frameDecryptContentPane.add(buttonChooseFileToPrivKey, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- buttonDecrypt ----
			buttonDecrypt.setText("DECRYPT");
			buttonDecrypt.addActionListener(e -> buttonDecryptActionPerformed(e));
			frameDecryptContentPane.add(buttonDecrypt, new GridBagConstraints(1, 6, 2, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));

			//---- labelDecryptNotif ----
			labelDecryptNotif.setText("Choose both to decrypt and private key");
			frameDecryptContentPane.add(labelDecryptNotif, new GridBagConstraints(1, 7, 2, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			frameDecrypt.pack();
			frameDecrypt.setLocationRelativeTo(frameDecrypt.getOwner());
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	public static void createAndShowGUI() {
		//Create and set up the window.
		Runner frame = new Runner();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Set up the content pane.
		frame.setContentPane(frame.getContentPane());
		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Farhan Ramdhani
	private JLabel labelMainmenu;
	private JButton buttonGoGenerate;
	private JButton buttonGoEncrypt;
	private JButton buttonGoDecrypt;
	private JFrame frameGenerate;
	private JLabel labelP;
	private JScrollPane scrollPane1;
	private JTextArea textAreaP;
	private JLabel labelQ;
	private JScrollPane scrollPane2;
	private JTextArea textAreaQ;
	private JButton buttonRandomize;
	private JButton buttonGenerate;
	private JLabel labelGenerateNotif;
	private JFrame frameEncrypt;
	private JLabel labelToEncrypt;
	private JTextField textFieldLocationToEncrypt;
	private JButton buttonChooseFileToEncrypt;
	private JScrollPane scrollPane3;
	private JTextArea textAreaToEnPreview;
	private JLabel label9;
	private JTextField textFieldLocationToEncryptKey;
	private JButton buttonChooseFileToPubKey;
	private JButton buttonEncrypt;
	private JLabel labelHex;
	private JScrollPane scrollPane4;
	private JTextArea textAreaEncryptedPreview;
	private JLabel labelEncryptNotif;
	private JFrame frameDecrypt;
	private JLabel label5;
	private JTextField textFieldLocationToDecrypt;
	private JButton buttonChooseFileToDecrypt;
	private JLabel label6;
	private JTextField textFieldLocationToDecryptKey;
	private JButton buttonChooseFileToPrivKey;
	private JButton buttonDecrypt;
	private JLabel labelDecryptNotif;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	//2616d64
	private BigInteger p;
	private BigInteger q;

	File fileToEncrypt;
	File filePubKey;

	File fileToDecrypt;
	File filePrivKey;
	//68616e69
}
