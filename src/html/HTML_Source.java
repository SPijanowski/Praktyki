package html;

/**
 * Program pobierający kod źródłowy HTML
 * @date 31.07.2014
 * @version 1.02
 * @author Sylwester Pijanowski
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;

public class HTML_Source {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				HTMLFrame frame = new HTMLFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

class HTMLFrame extends JFrame implements Serializable {

	private static final long serialVersionUID = 1854760021729494652L;
	public static final int DEFAULT_WIDTH = 600;
	public static final int DEFAULT_HEIGHT = 500;

	private static final int FONTSIZE = 16;
	private JLabel info1;
	private JLabel info2;

	public HTMLFrame() {
		setTitle("HTML_Source");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		// P�nocny panel
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(2, 1));
		info1 = new JLabel("Wprowadź dane strony internetowej");
		info1.setFont(new Font("Serif", Font.PLAIN, FONTSIZE));
		northPanel.add(info1);

		final JTextField plikNazwa = new JTextField();
		final JTextField urlHtml = new JTextField();

		JPanel northPanel1 = new JPanel();
		northPanel1.setLayout(new GridLayout(2, 2, 0 ,5));
		northPanel1.add(new JLabel("Nazwa Strony: ", SwingConstants.RIGHT));
		northPanel1.add(plikNazwa);
		northPanel1.add(new JLabel("Adres URL: ", SwingConstants.RIGHT));
		northPanel1.add(urlHtml);
		northPanel.add(northPanel1);
		add(northPanel, BorderLayout.NORTH);

		// Srodkowy panel (podgl�d)
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 1, 5 ,0));
		info2 = new JLabel("Log Programu");
		info2.setFont(new Font("Serif", Font.PLAIN, FONTSIZE));
		;
		centerPanel.add(info2);
		final JTextArea view = new JTextArea();
		view.append("Wprowadz adres strony której chcesz uzyskać kod źródłowy\r\n"
				+ "Pole \"Nazwa Strony\" - tak kod źródłowy zostanie zapisany na dysku\r\n"
				+ "Pole \"Adres URL\" - adres strony której źródło chcessz uzyskać np(https://www.google.pl)");
		view.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(view);
		centerPanel.add(scrollPane);
		add(centerPanel, BorderLayout.CENTER);

		// Po�udniowy panel (Przycisk zatwierdzenia)
		JPanel southPanel = new JPanel();
		JButton insertButton = new JButton("Uzyskaj źródło");
		southPanel.add(insertButton);
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// Na podstawie:
				// http://forum.4programmers.net/Java/140242-Pobieranie_zrodla_strony
				String nazwa = plikNazwa.getText();
				boolean walidacja = new File(nazwa + ".txt").exists();

				// Sprawdzanie czy podany plik ju� istnieje
				if (!walidacja) {
					// Pobieranie adresu URL
					String urlString = urlHtml.getText();
					URL url = null;
					try {
						url = new URL(urlString);
					} catch (MalformedURLException e2) {
						e2.printStackTrace();
					}

					InputStream content = null;
					try {
						content = (InputStream) url.getContent();
					} catch (IOException e1) {

						e1.printStackTrace();
					}
					// Wczytywanie danych
					BufferedReader in = new BufferedReader(
							new InputStreamReader(content));
					String line;
					StringBuilder plik = new StringBuilder();

					// Tworzenie nowej zmiennej do zapisywania danych
					try {
						while ((line = in.readLine()) != null) {
							plik.append(line + "\r\n");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					// Zapisywanie pobranego �r�d�a w pliku
					String nowy = plik.toString();
					FileWriter fw = null;
					try {
						fw = new FileWriter(nazwa + ".txt");
					} catch (IOException e) {
						System.out.println(e);
						System.exit(0);
					}
					try {
						fw.write(nowy);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					if (fw != null)
						try {
							fw.close();
						} catch (IOException e) {
							System.out.println(e);
						}
					// Wynik
					view.setForeground(Color.GREEN);
					view.append("\r\nWykonane. Został stworzony plik: " + nazwa
							+ ".txt");
				} else {
					view.setForeground(Color.RED);
					view.append("\r\nPlik "
							+ nazwa
							+ ".txt już istnieje. Prosze wprowadzić nową nazwe.");
				}
			}

		});
		add(southPanel, BorderLayout.SOUTH);
	}
}