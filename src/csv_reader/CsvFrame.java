package csv_reader;

/**
 * Program umożliwijący czytanie plików CSV
 * @author Sylwester Pijanowski
 * @version 1.20
 * @date 04.08.2014
 */

import java.awt.*;
import java.awt.event.*;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.*;

import javax.swing.table.TableColumn;

import com.csvreader.CsvReader;

public class CsvFrame extends JFrame implements Serializable {

	public static final int TEXT_ROWS = 5;
	public static final int TEXT_COLUMNS = 90;
	public static JMenuBar mBar;
	public static JTextArea csvInsert;
	public String[][] compare;

	private CsvChooser dialog = null;
	private CompareCSV compareCsv;
	public static JDesktopPane desktop;

	public static ArrayList<TableColumn> removedColumns;
	private static final long serialVersionUID = 2537576951291269546L;

	public CsvFrame() {
		// Tworzenie menu Wczytaj

		JMenuBar mbar = new JMenuBar();
		setJMenuBar(mbar);
		JMenu insertMenu = new JMenu("Wczytaj Plik");
		mbar.add(insertMenu);

		// Tworzenie elementów menu wczytaj i Zamknij

		JMenuItem insertItem = new JMenuItem("Wczytaj Plik CSV");
		insertItem.addActionListener(new ConnectAction());
		insertMenu.add(insertItem);

		// Tworzenie elemntu do porównywania 2 plików

		JMenuItem compareItem = new JMenuItem("Porównaj Pliki CSV");
		compareItem.addActionListener(new Compare());
		insertMenu.add(compareItem);

		// Opcja Zamknij zamyka program

		JMenuItem exitItem = new JMenuItem("Zamknij");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		insertMenu.add(exitItem);
		// Tworzenie logu oraz puliptu
		desktop = new JDesktopPane();
		desktop.setBackground(new Color(176, 196, 222));
		add(desktop, BorderLayout.CENTER);
		csvInsert = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
		csvInsert.setEditable(false);
		JScrollPane scroll = new JScrollPane(csvInsert);
		// southPanel.add(scroll);
		JSplitPane innerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				desktop, scroll);
		innerPane.setDividerLocation(300);
		innerPane.setContinuousLayout(true);
		innerPane.setOneTouchExpandable(true);
		innerPane.updateUI();
		add(innerPane, BorderLayout.CENTER);
	}

	private class Compare implements ActionListener {
		private JButton generuj;

		public void actionPerformed(ActionEvent arg0) {
			if (compareCsv == null)
				compareCsv = new CompareCSV();
			if (compareCsv.showDialog(CsvFrame.this, "Porównaj dwa pliki CSV")) {
				int wczyt = 2 + CompareCSV.getFirstFileRow()
						+ CompareCSV.getSecondFileRow();
				int line_number1 = 0;
				String l = null;
				String[] test = CompareCSV.getFirstDataArray();
				String[] test2 = CompareCSV.getSecondDataArray();
				int compareSelected = CompareCSV.getSelc2();
				if (compareSelected != -1) {
					String firstFilePath = CompareCSV.getFirstFilePath();
					String secondFilePath = CompareCSV.getSecondFilePath();

					String[][] firstFileTable = Csv_File.readData(
							firstFilePath, test);
					String[][] secondFileTable = Csv_File.readData(
							secondFilePath, test2);
					// Sprawdzanie poprawności według kolumn
					int wybr = CompareCSV.getSelc();
					int t = 0;
					if (wybr != -1) {
						t = wybr;
					}

					int petle1 = 0;
					int pow1 = 0;
					int inne1 = 0;
					int petle2 = 0;
					while (petle1 < firstFileTable.length - 1) {
						petle2 = 0;
						while (petle2 < secondFileTable.length - 1) {
							if (firstFileTable[petle1][t]
									.equals(secondFileTable[petle2][compareSelected])) {
								pow1++;
								break;
							} else {
								if (petle2 == secondFileTable.length - 2) {
									inne1++;
								}
							}
							petle2++;
						}
						petle1++;
					}
					petle2 = 0;
					while (petle2 < secondFileTable.length - 1) {
						if (firstFileTable[firstFileTable.length - 1][t]
								.equals(secondFileTable[petle2][compareSelected])) {
							pow1++;
							break;
						} else {
							if (petle2 == secondFileTable.length - 2) {
								inne1++;
							}
						}
						petle2++;
					}

					final String[][] niepowtarzalne1 = new String[inne1][firstFileTable[0].length];
					final String[][] powtarjace = new String[pow1][firstFileTable[0].length];
					petle1 = 0;
					petle2 = 0;
					int copy = 0;
					int newDate = 0;
					while (petle1 < firstFileTable.length - 1) {
						petle2 = 0;
						while (petle2 < secondFileTable.length - 1) {
							if (firstFileTable[petle1][t]
									.equals(secondFileTable[petle2][compareSelected])) {
								// Usuwa powtarzajaco dana
								for (int i = 0; i < firstFileTable[petle1].length; i++) {
									powtarjace[copy][i] = firstFileTable[petle1][i];
								}
								copy++;
								break;
							} else {
								if (petle2 == secondFileTable.length - 2) {
									for (int i = 0; i < firstFileTable[petle1].length; i++) {
										niepowtarzalne1[newDate][i] = firstFileTable[petle1][i];
									}
									newDate++;
								}
							}
							petle2++;
						}
						petle1++;
					}
					petle2 = 0;
					while (petle2 < secondFileTable.length - 1) {
						if (firstFileTable[firstFileTable.length - 1][t]
								.equals(secondFileTable[petle2][compareSelected])) {
							for (int i = 0; i < firstFileTable[firstFileTable.length - 1].length; i++) {
								powtarjace[copy][i] = firstFileTable[firstFileTable.length - 1][i];
							}
							copy++;
							break;
						} else {
							if (petle2 == secondFileTable.length - 2) {
								for (int i = 0; i < firstFileTable[firstFileTable.length - 1].length; i++) {
									niepowtarzalne1[newDate][i] = firstFileTable[firstFileTable.length - 1][i];
								}
								newDate++;
							}
						}
						petle2++;
					}

					final String[][] niepowtarzalne = Csv_File
							.removeDoubleData(niepowtarzalne1);
					final String[] nameOfColumns = test;
					csvInsert.setForeground(new Color(0, 100, 0));
					csvInsert
							.append("Niepowtarzające: "
									+ inne1
									+ "; Powtarzające się: "
									+ pow1
									+ "; Ilość sprawdzeń: "
									+ petle1
									+ "\r\nUsunięto powtórzenia wewnątrz pliku; Wczytanych danych: "
									+ niepowtarzalne.length + ";\r\n");
					generuj = GenerateButton.addButton("Generuj Tabele",
							niepowtarzalne, powtarjace, nameOfColumns);

					add(generuj, BorderLayout.NORTH);
				} else {
					final String[] nameOfColumns = test;
					String[][] compare = new String[wczyt][test.length];
					try {
						String a = CompareCSV.getFirstSeparator();
						CsvReader tabela;
						if (a != null) {
							char b = a.charAt(0);
							tabela = new CsvReader(
									CompareCSV.getFirstFilePath(), b);
						} else {
							tabela = new CsvReader(
									CompareCSV.getFirstFilePath());
						}
						tabela.readHeaders();
						while (tabela.readRecord()) {
							for (int i = 0; i < test.length; i++) {
								l = tabela.get(test[i]);
								compare[line_number1][i] = (l);
							}
							++line_number1;
						}
						tabela.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						String a = CompareCSV.getSecondSeparator();
						CsvReader tabela;
						if (a != null) {
							char b = a.charAt(0);
							tabela = new CsvReader(
									CompareCSV.getSecondFilePath(), b);
						} else {
							tabela = new CsvReader(
									CompareCSV.getSecondFilePath());
						}

						tabela.readHeaders();
						while (tabela.readRecord()) {
							for (int i = 0; i < test.length; i++) {
								l = tabela.get(test[i]);
								compare[line_number1][i] = (l);
							}
							++line_number1;
						}
						tabela.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					int wybr = CompareCSV.getSelc();
					final int t;
					if (wybr != -1) {
						t = wybr;
					} else {
						t = 0;
					}
					Arrays.sort(compare, new Comparator<String[]>() {
						@Override
						public int compare(final String[] entry1,
								final String[] entry2) {
							final String time1 = entry1[t];
							final String time2 = entry2[t];
							return time1.compareTo(time2);
						}
					});
					int petle = 0;
					int inne = 0;
					int pow = 0;
					while (petle < compare.length - 1) {
						if (compare[petle][t].equals(compare[petle + 1][t])) {
							pow++;
						} else {
							if (petle <= 0) {
								inne++;
							} else {
								if (compare[petle][t]
										.equals(compare[petle - 1][t])) {
									pow++;
								} else {
									inne++;
								}
							}
						}
						petle++;
					}
					int[] nonCopyRow = new int[inne];
					int[] copyRow = new int[pow];
					int nowe = inne;
					int powtarza = pow;
					final String[][] niepowtarzalne = new String[inne][compare[0].length];
					final String[][] powtarjace = new String[pow][compare[0].length];
					petle = 0;
					int nowaDana = 0;
					int powDana = 0;
					while (petle < compare.length - 1) {
						if (petle <= 0) {
							if (compare[petle][t].equals(compare[petle + 1][t])) {
								for (int i = 0; i < compare[petle].length; i++) {
									powtarjace[powDana][i] = compare[petle][i];
								}
								copyRow[powDana] = petle;
								powDana++;
								petle++;
							} else {
								for (int i = 0; i < compare[petle].length; i++) {
									niepowtarzalne[nowaDana][i] = compare[petle][i];
								}
								nonCopyRow[nowaDana] = petle;
								nowaDana++;
								petle++;
							}
						} else {
							if (compare[petle][t].equals(compare[petle - 1][t])) {
								for (int i = 0; i < compare[petle].length; i++) {
									powtarjace[powDana][i] = compare[petle][i];
								}
								copyRow[powDana] = petle;
								powDana++;
								petle++;
							} else {
								if (compare[petle][t]
										.equals(compare[petle + 1][t])) {
									for (int i = 0; i < compare[petle].length; i++) {
										powtarjace[powDana][i] = compare[petle][i];
									}
									copyRow[powDana] = petle;
									powDana++;
									petle++;
								} else {
									for (int i = 0; i < compare[petle].length; i++) {
										niepowtarzalne[nowaDana][i] = compare[petle][i];
									}
									nonCopyRow[nowaDana] = petle;
									nowaDana++;
									petle++;
								}
							}
						}
					}
					generuj = GenerateButton.addButton("Generuj Tabele",
							niepowtarzalne, powtarjace, nameOfColumns);
					csvInsert.setForeground(new Color(0, 100, 0));
					int spr = compare.length - 1;
					csvInsert.append("Porównano pliki \""
							+ Csv_File.csvFileName(CompareCSV
									.getFirstFilePath())
							+ "\" oraz \""
							+ Csv_File.csvFileName(CompareCSV
									.getSecondFilePath())
							+ "\". Ilość sprawdzeń = " + spr + "; Inne = "
							+ nowe + "; Powtarzające = " + powtarza + ";\r\n");
					add(generuj, BorderLayout.NORTH);
				}
			}
		}
	}

	private class ConnectAction implements ActionListener {
		private JButton generuj;

		public void actionPerformed(ActionEvent event) {

			if (dialog == null)
				dialog = new CsvChooser();

			if (dialog.showDialog(CsvFrame.this, "Wybier Plik")) {

				String path = Csv_File.getScvFilePath();
				csvInsert.setForeground(new Color(0, 100, 0));
				int wczyt = Csv_Table.getRow() + 1;
				csvInsert.append("GOTOWE! Wczytano plik \""
						+ Csv_File.csvFileName(path)
						+ "\". Ilość wczytanych danych " + wczyt
						+ " Rekordów;\r\n");
			}
			generuj = GenerateButton.addButton("Generuj");
			add(generuj, BorderLayout.NORTH);

		}

	}
}