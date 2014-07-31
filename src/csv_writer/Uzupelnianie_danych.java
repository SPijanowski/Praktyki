package csv_writer;

/**
 * Program umożliwijacy wprowadzanie danych do pliku CSV
 * @date 31.07.2014
 * @version 1.10
 * @author Sylwester Pijanowski
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.Serializable;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import com.csvreader.CsvWriter;

public class Uzupelnianie_danych {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				CsvFrame frame = new CsvFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

class CsvFrame extends JFrame implements Serializable {
	String outputFile = "firmy.csv";

	public static final int DEFAULT_WIDTH = 600;
	public static final int DEFAULT_HEIGHT = 500;

	private static final long serialVersionUID = -4481173070381424799L;
	private static final int FONTSIZE = 16;
	private JLabel info1;
	private JLabel info2;

	public CsvFrame() {
		setTitle("CsvFrame");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		// Północny panel
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(2, 1));
		info1 = new JLabel("Wprowadź dane");
		info1.setFont(new Font("Serif", Font.PLAIN, FONTSIZE));
		northPanel.add(info1);

		final JTextField nazwa = new JTextField();
		final JTextField ulica = new JTextField();
		final JTextField email = new JTextField();
		final JTextField url = new JTextField();
		final JTextField tel = new JTextField();
		final JTextField nip = new JTextField();

		JPanel northPanel1 = new JPanel();
		northPanel1.setLayout(new GridLayout(3, 2, 1, 5));
		northPanel1.add(new JLabel("Nazwa: ", SwingConstants.RIGHT));
		northPanel1.add(nazwa);
		northPanel1.add(new JLabel("Ulica: ", SwingConstants.RIGHT));
		northPanel1.add(ulica);
		northPanel1.add(new JLabel("Email: ", SwingConstants.RIGHT));
		northPanel1.add(email);
		northPanel1.add(new JLabel("Adres www: ", SwingConstants.RIGHT));
		northPanel1.add(url);
		northPanel1.add(new JLabel("Nr telefonu: ", SwingConstants.RIGHT));
		northPanel1.add(tel);
		northPanel1.add(new JLabel("nip: ", SwingConstants.RIGHT));
		northPanel1.add(nip);
		
		northPanel.add(northPanel1);

		add(northPanel, BorderLayout.NORTH);

		// Srodkowy panel (podgl�d)
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 1));
		info2 = new JLabel("Podgląd wprowadzanych danych");
		info2.setFont(new Font("Serif", Font.PLAIN, FONTSIZE));
		;
		centerPanel.add(info2);
		final JTextArea view = new JTextArea();
		view.append("nazwa;ulica;email;WWW;Telefon;NIP");
		view.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(view);
		centerPanel.add(scrollPane);
		add(centerPanel, BorderLayout.CENTER);

		// Południowy panel (Przycisk zatwierdzenia)
		JPanel southPanel = new JPanel();
		JButton insertButton = new JButton("Wstaw");
		southPanel.add(insertButton);
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				StringBuilder aUrl = new StringBuilder(url.getText());
				String tmp = aUrl.substring(0,7);
				String adresWWW;
				if (tmp.equals("http://"))
				{
					adresWWW = aUrl.toString();
				}
				else
				{
					StringBuilder bUrl = new StringBuilder("http://");
					bUrl.append(aUrl);
					adresWWW = bUrl.toString();
				}
				String wprowadzanyMail = email.getText();
				// źródło:
				// http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
				Pattern p = Pattern
						.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
								+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
				Matcher m = p.matcher(wprowadzanyMail);

				boolean walidacja = m.matches();
				// �r�d�o: http://www.csvreader.com/java_csv_samples.php
				if (walidacja) {
					boolean alreadyExists = new File(outputFile).exists();
					try {
						CsvWriter csvOutput = new CsvWriter(new FileWriter(
								outputFile, true), ';');

						if (!alreadyExists) {
							csvOutput.write("Nazwa");
							csvOutput.write("Ulica");
							csvOutput.write("E-mail");
							csvOutput.write("Strona WWW");
							csvOutput.write("Telefon");
							csvOutput.write("Nip");
							csvOutput.endRecord();
						}
						csvOutput.write(nazwa.getText());
						csvOutput.write(ulica.getText());
						csvOutput.write(email.getText());
						csvOutput.write(adresWWW);
						csvOutput.write(tel.getText());
						csvOutput.write(nip.getText());
						csvOutput.endRecord();
						csvOutput.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					view.setForeground(Color.GREEN);
					view.append("\r\n" + nazwa.getText() + ";"
							+ ulica.getText() + ";" + email.getText()+";"+adresWWW+";"+tel.getText()+nip.getText());
				} else {
					view.setForeground(Color.RED);
					view.append("\r\nZły adres email. Poprawna forma np.(przyklad@email.com)");
				}
			}
		});
		add(southPanel, BorderLayout.SOUTH);
	}
}