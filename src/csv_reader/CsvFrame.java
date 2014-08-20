package csv_reader;

/**
 * Program umożliwijący czytanie plików CSV
 * @author Sylwester Pijanowski
 * @version 1.20
 * @date 04.08.2014
 */

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.csvreader.CsvReader;

public class CsvFrame extends JFrame implements Serializable {

	
	public static final int TEXT_ROWS = 5;
	public static final int TEXT_COLUMNS = 90;
	public static JMenuBar mBar;
	public static JTextArea csvInsert;
	public String[][] compare;
	
	
	private CsvChooser dialog = null;
	private CompareCSV compareCsv;
	private static JDesktopPane desktop;
	private static int nextFrameX;
	private static int nextFrameY;
	private static int frameDistance;
	private ArrayList<TableColumn> removedColumns;
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
		//Tworzenie logu oraz puliptu
		setDesktop(new JDesktopPane());
		desktop.setBackground(new Color(176, 196, 222));
		add(getDesktop(), BorderLayout.CENTER);
		csvInsert = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
		csvInsert.setEditable(false);
		JScrollPane scroll = new JScrollPane(csvInsert);
		//southPanel.add(scroll);		
		JSplitPane innerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, desktop, scroll);
		innerPane.setDividerLocation(300);
		innerPane.setContinuousLayout(true);
		innerPane.setOneTouchExpandable(true);
		innerPane.updateUI();
		add(innerPane, BorderLayout.CENTER);
	}

	public static JDesktopPane getDesktop() {
		return desktop;
	}

	public void setDesktop(JDesktopPane desktop) {
		CsvFrame.desktop = desktop;
	}

	public static int getNextFrameX() {
		return nextFrameX;
	}

	public static void setNextFrameX(int nextFrameX) {
		CsvFrame.nextFrameX = nextFrameX;
	}

	public static int getNextFrameY() {
		return nextFrameY;
	}

	public static void setNextFrameY(int nextFrameY) {
		CsvFrame.nextFrameY = nextFrameY;
	}

	public static int getFrameDistance() {
		return frameDistance;
	}

	public static void setFrameDistance(int frameDistance) {
		CsvFrame.frameDistance = frameDistance;
	}
	private class Compare implements ActionListener {
		private JButton generuj;
		public void actionPerformed(ActionEvent arg0) {
			if (compareCsv == null)
				compareCsv = new CompareCSV();	
			if(compareCsv.showDialog(CsvFrame.this, "Porównaj dwa pliki CSV")){
				int wczyt = 2 + CompareCSV.getFirstFileRow()+CompareCSV.getSecondFileRow();
				int line_number1 = 0;
				String l = null;
				final String[] test = CompareCSV.getFirstDataArray();
				System.out.println(wczyt);
				System.out.println(test);
				String[][] compare = new String[wczyt][test.length];
				try {
					String a = CompareCSV.getFirstSeparator();
					char b = a.charAt(0);
					CsvReader tabela = new CsvReader(CompareCSV.getFirstFilePath(), b);
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
					
					char b = a.charAt(0);
					CsvReader tabela = new CsvReader(CompareCSV.getSecondFilePath(), b);
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
				System.out.println();
				System.out.println(Arrays.deepToString(compare));	
				Arrays.sort(compare, new Comparator<String[]>() {
		            @Override
		            public int compare(final String[] entry1, final String[] entry2) {
		                final String time1 = entry1[0];
		                final String time2 = entry2[0];
		                return time1.compareTo(time2);
		            }
		        });
				System.out.println(Arrays.deepToString(compare));	
				System.out.println(compare.length);
				int petle = 0;
				int inne = 0;
				int pow = 0;
				while(petle < compare.length-1){
					if(compare[petle][0].equals(compare[petle+1][0]))
					{pow++;}
					else
					{inne++;}	
					petle++;
				}
				int nowe = inne;
				int powtarza = pow;
					
				System.out.println("Pętle = "+compare.length+"; inne = "+nowe+"; Powtarzające = "+powtarza);
				final String[][] niepowtarzalne = new String[inne][compare[0].length];
				final String[][] powtarjace = new String[pow][compare[0].length];
				petle = 0;
				int nowaDana = 0;
				int powDana = 0;
				while(petle < compare.length-1){
					if(compare[petle][0].equals(compare[petle+1][0]))
					{	
						for(int i =0; i<compare[petle].length;i++)
						{
						powtarjace[nowaDana][i] = compare[petle][i];}
						nowaDana++;
						petle++;
					}
					else 
					{
						for(int i =0; i<compare[petle].length;i++)
						{niepowtarzalne[powDana][i] = compare[petle][i];}
						powDana++;
						petle++;
					}
				}
				generuj = new JButton("Generuj Tabele");
				generuj.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent event){
						JInternalFrame listFrame = new JInternalFrame("niepowtarzalne", true, true, true, true);
						JInternalFrame listFrame2 = new JInternalFrame("Powtazajace sie", true, true, true, true);
						final JTable model = new JTable(niepowtarzalne, test);
						model.setAutoCreateRowSorter(true);
						model.setDefaultRenderer(Object.class,
								new ColorCellRenderer());
						JScrollPane scrollPane = new JScrollPane(model);
						scrollPane.setRowHeaderView(new LineNumberTable(model));
						    listFrame.setContentPane(scrollPane);
						    listFrame.setLocation(0, 0);
						    listFrame.setSize(500, 200);
						    listFrame.setVisible(true);
						desktop.add(listFrame, BorderLayout.NORTH);
						final JTable model2 = new JTable(powtarjace, test);
						model2.setAutoCreateRowSorter(true);
						model2.setDefaultRenderer(Object.class,
								new ColorCellRenderer());
						JScrollPane scrollPane2 = new JScrollPane(model2);
						scrollPane2.setRowHeaderView(new LineNumberTable(model2));
						    listFrame2.setContentPane(scrollPane2);
						    listFrame2.setLocation(0, 0);
						    listFrame2.setSize(500, 200);
						    listFrame2.setVisible(true);
						desktop.add(listFrame2, BorderLayout.CENTER);
						SwingUtilities.updateComponentTreeUI(desktop);
						generuj.setVisible(false);
						removedColumns = new ArrayList<TableColumn>();
						JPopupMenu popup = new JPopupMenu();
						JMenu selectionMenu = new JMenu("Obszar Zaznaczenia");
						final JCheckBoxMenuItem rowsItem = new JCheckBoxMenuItem("Wiersze");
						final JCheckBoxMenuItem columnsItem = new JCheckBoxMenuItem(
								"Kolumny");
						final JCheckBoxMenuItem cellsItem = new JCheckBoxMenuItem(
								"Komórki");

						rowsItem.setSelected(model.getRowSelectionAllowed());
						columnsItem.setSelected(model.getColumnSelectionAllowed());
						cellsItem.setSelected(model.getCellSelectionEnabled());

						rowsItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent event) {
								model.clearSelection();
								model.setRowSelectionAllowed(rowsItem.isSelected());
								cellsItem.setSelected(model
										.getCellSelectionEnabled());
							}
						});
						selectionMenu.add(rowsItem);

						columnsItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent event) {
								model.clearSelection();
								model.setColumnSelectionAllowed(columnsItem
										.isSelected());
								cellsItem.setSelected(model
										.getCellSelectionEnabled());
							}
						});
						selectionMenu.add(columnsItem);

						cellsItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent event) {
								model.clearSelection();
								model.setCellSelectionEnabled(cellsItem
										.isSelected());
								rowsItem.setSelected(model.getRowSelectionAllowed());
								columnsItem.setSelected(model
										.getColumnSelectionAllowed());
							}
						});
						selectionMenu.add(cellsItem);
						popup.add(selectionMenu);

						model.setComponentPopupMenu(popup);
						csvInsert.setComponentPopupMenu(popup);
						JMenu tableMenu = new JMenu("Edycja");

						JMenuItem hideColumnsItem = new JMenuItem("Ukryj Kolumny");
						hideColumnsItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent event) {
								int[] selected = model.getSelectedColumns();
								TableColumnModel columnModel = model
										.getColumnModel();

								// usuwa kolumny z widoku tabeli, począwszy od
								// najwyższego indeksu, aby nie zmieniać numerów
								// kolumn

								for (int i = selected.length - 1; i >= 0; i--) {
									TableColumn column = columnModel
											.getColumn(selected[i]);
									model.removeColumn(column);

									// przechowuje ukryte kolumny do ponownej
									// prezentacji

									removedColumns.add(column);
								}
							}
						});
						tableMenu.add(hideColumnsItem);
						JMenuItem showColumnsItem = new JMenuItem(
								"Pokaż Ukryte Kolumny");
						showColumnsItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent event) {
								// przywraca wszystkie usunięte kolumny
								for (TableColumn tc : removedColumns)
									model.addColumn(tc);
								removedColumns.clear();
							}
						});
						tableMenu.add(showColumnsItem);
						popup.add(tableMenu);

						SwingUtilities.updateComponentTreeUI(Csv_Reader.frame);

					}});
				csvInsert.setForeground(new Color(0, 100, 0));
				csvInsert.append("Pętle = "+compare.length+"; inne = "+nowe+"; Powtarzające = "+powtarza);
				add(generuj, BorderLayout.NORTH);}
		}
	}

	private class ConnectAction implements ActionListener {
		private JButton generuj;
		

		public void actionPerformed(ActionEvent event) {

			if (dialog == null)
				dialog = new CsvChooser();
			

			if (dialog.showDialog(CsvFrame.this, "Wybier Plik")) {
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
				StringBuilder b = new StringBuilder(Csv_File.getScvFilePath());
				int c = b.lastIndexOf("\\");
				String d = b.substring(c+1);
				Csv_File.setCsvFileName(d);
				csvInsert.setForeground(new Color(0, 100, 0));
				int wczyt = Csv_Table.getRow() + 1;
				csvInsert.append("GOTOWE! Wczytano plik \"" + Csv_File.getCsvFileName() + "\". Ilość wczytanych danych " + wczyt + " Rekordów;\r\n");
			}
			generuj = new JButton("Generuj Tabele");
			generuj.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					int line_number = 0;
					int line_number1 = 0;
					String l = null;
					String[] test = null;
					String a = Csv_File.getSeparator();
					String path = Csv_File.getScvFilePath();
					char sep = a.charAt(0);
					String[] wybraane = CsvChooser.getSelected();
					//Sprawdzenie czy użytkownik wybrał dane do wczytania
					if (wybraane != null) {
						test = wybraane;
					} else {
						test = CsvChooser.getDataArray();
					}
					try {
					//Obiczanie wielkości tablicy
						CsvReader tabela = new CsvReader(path, sep);
						tabela.readHeaders();
						while (tabela.readRecord()) {
							++line_number;
						}
						tabela.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					//Zapisywanie wczytywanych danych w tablicy wielowymiarowej
					String[][] tablicaDanych = new String[line_number][test.length];
					try {
						CsvReader tabela = new CsvReader(path, sep);
						tabela.readHeaders();
						while (tabela.readRecord()) {
							for (int i = 0; i < test.length; i++) {
								l = tabela.get(test[i]);
								tablicaDanych[line_number1][i] = (l);
							}
							++line_number1;
						}
						tabela.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					//Tworzenie modelu tabeli
					JInternalFrame listFrame = new JInternalFrame(Csv_File.getCsvFileName(), true, true, true, true);
					final JTable model = new JTable(tablicaDanych, test);
					model.setAutoCreateRowSorter(true);
					model.setDefaultRenderer(Object.class,
							new ColorCellRenderer());
					JScrollPane scrollPane = new JScrollPane(model);
					scrollPane.setRowHeaderView(new LineNumberTable(model));
					    listFrame.setContentPane(scrollPane);
					    listFrame.setLocation(0, 0);
					    listFrame.setSize(500, 200);
					    listFrame.setVisible(true);
					desktop.add(listFrame, BorderLayout.NORTH);
					SwingUtilities.updateComponentTreeUI(desktop);
					generuj.setVisible(false);
					removedColumns = new ArrayList<TableColumn>();
					JPopupMenu popup = new JPopupMenu();
					JMenu selectionMenu = new JMenu("Obszar Zaznaczenia");
					final JCheckBoxMenuItem rowsItem = new JCheckBoxMenuItem("Wiersze");
					final JCheckBoxMenuItem columnsItem = new JCheckBoxMenuItem(
							"Kolumny");
					final JCheckBoxMenuItem cellsItem = new JCheckBoxMenuItem(
							"Komórki");

					rowsItem.setSelected(model.getRowSelectionAllowed());
					columnsItem.setSelected(model.getColumnSelectionAllowed());
					cellsItem.setSelected(model.getCellSelectionEnabled());

					rowsItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							model.clearSelection();
							model.setRowSelectionAllowed(rowsItem.isSelected());
							cellsItem.setSelected(model
									.getCellSelectionEnabled());
						}
					});
					selectionMenu.add(rowsItem);

					columnsItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							model.clearSelection();
							model.setColumnSelectionAllowed(columnsItem
									.isSelected());
							cellsItem.setSelected(model
									.getCellSelectionEnabled());
						}
					});
					selectionMenu.add(columnsItem);

					cellsItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							model.clearSelection();
							model.setCellSelectionEnabled(cellsItem
									.isSelected());
							rowsItem.setSelected(model.getRowSelectionAllowed());
							columnsItem.setSelected(model
									.getColumnSelectionAllowed());
						}
					});
					selectionMenu.add(cellsItem);
					popup.add(selectionMenu);

					model.setComponentPopupMenu(popup);
					csvInsert.setComponentPopupMenu(popup);
					JMenu tableMenu = new JMenu("Edycja");

					JMenuItem hideColumnsItem = new JMenuItem("Ukryj Kolumny");
					hideColumnsItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							int[] selected = model.getSelectedColumns();
							TableColumnModel columnModel = model
									.getColumnModel();

							// usuwa kolumny z widoku tabeli, począwszy od
							// najwyższego indeksu, aby nie zmieniać numerów
							// kolumn

							for (int i = selected.length - 1; i >= 0; i--) {
								TableColumn column = columnModel
										.getColumn(selected[i]);
								model.removeColumn(column);

								// przechowuje ukryte kolumny do ponownej
								// prezentacji

								removedColumns.add(column);
							}
						}
					});
					tableMenu.add(hideColumnsItem);
					JMenuItem showColumnsItem = new JMenuItem(
							"Pokaż Ukryte Kolumny");
					showColumnsItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							// przywraca wszystkie usunięte kolumny
							for (TableColumn tc : removedColumns)
								model.addColumn(tc);
							removedColumns.clear();
						}
					});
					tableMenu.add(showColumnsItem);
					popup.add(tableMenu);

					SwingUtilities.updateComponentTreeUI(Csv_Reader.frame);

				}
			});
			add(generuj, BorderLayout.NORTH);

		}



	}
}