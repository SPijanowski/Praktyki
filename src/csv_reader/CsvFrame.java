package csv_reader;

/**
 * Program umożliwijący czytanie plików CSV
 * @author Sylwester Pijanowski
 * @version 1.10
 * @date 01.08.2014
 */

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.*;

import com.csvreader.CsvReader;

public class CsvFrame extends JFrame implements Serializable {

	private static final long serialVersionUID = 2537576951291269546L;
	public static final int TEXT_ROWS = 20;
	public static final int TEXT_COLUMNS = 40;
	private CsvChooser dialog = null;
	private JTextArea csvInsert;

	public CsvFrame() {
		// Tworzenie menu Plik

		JMenuBar mbar = new JMenuBar();
		setJMenuBar(mbar);
		JMenu insertMenu = new JMenu("Wczytaj Plik");
		mbar.add(insertMenu);

		// Tworzenie elementów menu Połącz i Zamknij

		JMenuItem insertItem = new JMenuItem("Wczytaj Plik");
		insertItem.addActionListener(new ConnectAction());
		insertMenu.add(insertItem);

		// Opcja Zamknij zamyka program

		JMenuItem exitItem = new JMenuItem("Zamknij");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		insertMenu.add(exitItem);

		csvInsert = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
		add(new JScrollPane(csvInsert), BorderLayout.CENTER);
		pack();
	}

	/**
	 * Akcja Connect wyświetla okno dialogowe z polem hasła
	 */

	private class ConnectAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// Jeśli jest to pierwszy raz, tworzy okno dialogowe

			if (dialog == null)
				dialog = new CsvChooser();

			// Ustawianie wartości domyślnych
			dialog.setCsvFile(new Csv_File("Nazwa Pliku", ";"));

			// Wyświetlenie okna dialogowego
			if (dialog.showDialog(CsvFrame.this, "Wybier Plik")) {
				// Pobranie danych użytkownika w przypadku zatwierdzenia
				String a = Csv_File.getSeparator();
				char b = a.charAt(0);
				try {
					CsvReader tabela = new CsvReader(Csv_File.getScvFilePath(),
							b);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				BufferedReader CSVFile = null;
				try {
					CSVFile = new BufferedReader(new FileReader(
							Csv_File.getScvFilePath()));
				} catch (FileNotFoundException e1) {

					e1.printStackTrace();
				}

				String dataRow = null;
				try {
					dataRow = CSVFile.readLine();
				} catch (IOException e) {

					e.printStackTrace();
				}

				while (dataRow != null) {
					String[] dataArray = dataRow.split(a);
					for (String item : dataArray) {
						csvInsert.append(item + ";");
						
					}
					csvInsert.append("\r\n");
					try {
						dataRow = CSVFile.readLine();
					} catch (IOException e) {

						e.printStackTrace();
					}
				}

				try {
					CSVFile.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
				Csv_File u = dialog.getCsvFile();
				csvInsert.append("GOTOWE!");
			}
		}
	}
}