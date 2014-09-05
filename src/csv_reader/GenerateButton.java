package csv_reader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.csvreader.CsvWriter;

public class GenerateButton {

	public static JButton addButton(String name, final Csv_File file) {
		final JButton generuj = new JButton(name);
		generuj.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				
				final String[] test;
				String path = file.getCsvFilePath();

				String[] wybraane = CsvChooser.getSelected();
				if (wybraane != null) {
					String[] test1 = Csv_File.removeEmptyField(wybraane);
					test = test1;
				} else {
					String[] test1 = file.getCsvFileFirstRow();
					test = test1;
				}
		
				String[][] daneWczytane = Csv_File.readData(path, test);
				String[][] date = daneWczytane = Csv_File.readData(path, test);
				
				if (CsvChooser.selec) {
					daneWczytane = Csv_File.removeDoubleData(date);
					CsvFrame.csvInsert
							.append("Usunięto powtórzenia wewnątrz pliku; Wczytanych danych: "
									+ daneWczytane.length + ";\r\n");
				} else {
					daneWczytane = date;
				}
				final String[][] tablicaDanych = daneWczytane;

				// Tworzenie modelu tabeli

				JInternalFrame listFrame = new JInternalFrame(file.getCsvFileName(), true, true, true, true);
				final JTable model = new JTable(tablicaDanych, test);
				model.setAutoCreateRowSorter(true);
				model.setDefaultRenderer(Object.class, new ColorCellRenderer());
				JScrollPane scrollPane = new JScrollPane(model);
				scrollPane.setRowHeaderView(new LineNumberTable(model));
				listFrame.setContentPane(scrollPane);
				listFrame.setLocation(0, 0);
				listFrame.setSize(500, 200);
				listFrame.setVisible(true);
				CsvFrame.desktop.add(listFrame, BorderLayout.NORTH);
				SwingUtilities.updateComponentTreeUI(CsvFrame.desktop);
				generuj.setVisible(false);
				CsvFrame.removedColumns = new ArrayList<TableColumn>();
				JPopupMenu popup = new JPopupMenu();
				JMenu selectionMenu = new JMenu("Obszar Zaznaczenia");
				final JCheckBoxMenuItem rowsItem = new JCheckBoxMenuItem(
						"Wiersze");
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
						cellsItem.setSelected(model.getCellSelectionEnabled());
					}
				});
				selectionMenu.add(rowsItem);

				columnsItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						model.clearSelection();
						model.setColumnSelectionAllowed(columnsItem
								.isSelected());
						cellsItem.setSelected(model.getCellSelectionEnabled());
					}
				});
				selectionMenu.add(columnsItem);

				cellsItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						model.clearSelection();
						model.setCellSelectionEnabled(cellsItem.isSelected());
						rowsItem.setSelected(model.getRowSelectionAllowed());
						columnsItem.setSelected(model
								.getColumnSelectionAllowed());
					}
				});
				selectionMenu.add(cellsItem);
				popup.add(selectionMenu);

				model.setComponentPopupMenu(popup);
				CsvFrame.csvInsert.setComponentPopupMenu(popup);
				JMenu tableMenu = new JMenu("Edycja");

				JMenuItem hideColumnsItem = new JMenuItem("Ukryj Kolumny");
				hideColumnsItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						int[] selected = model.getSelectedColumns();
						TableColumnModel columnModel = model.getColumnModel();

						// usuwa kolumny z widoku tabeli, począwszy od
						// najwyższego indeksu, aby nie zmieniać numerów
						// kolumn

						for (int i = selected.length - 1; i >= 0; i--) {
							TableColumn column = columnModel
									.getColumn(selected[i]);
							model.removeColumn(column);

							// przechowuje ukryte kolumny do ponownej
							// prezentacji

							CsvFrame.removedColumns.add(column);
						}
					}
				});
				tableMenu.add(hideColumnsItem);
				JMenuItem showColumnsItem = new JMenuItem(
						"Pokaż Ukryte Kolumny");
				showColumnsItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						// przywraca wszystkie usunięte kolumny
						for (TableColumn tc : CsvFrame.removedColumns)
							model.addColumn(tc);
						CsvFrame.removedColumns.clear();
					}
				});

				tableMenu.add(showColumnsItem);
				JMenuItem saveFile = new JMenuItem("Zapisz");
				saveFile.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						JFileChooser FC = new JFileChooser();
						FileNameExtensionFilter filter = new FileNameExtensionFilter(
								"Pliki .csv", "csv");
						FC.setFileFilter(filter);
						int result = FC.showSaveDialog(Csv_Reader.frame);
						if (result == JFileChooser.APPROVE_OPTION) {
							String filePath = FC.getSelectedFile().getPath();
							String outputFile = (filePath + ".csv");

							// before we open the file check to see if it
							// already exists
							boolean alreadyExists = new File(outputFile)
									.exists();

							try {
								// use FileWriter constructor that specifies
								// open for appending

								char b = ';';
								CsvWriter csvOutput = new CsvWriter(
										new FileWriter(outputFile, true), b);

								// if the file didn't already exist then we need
								// to write out the header line
								if (!alreadyExists) {
									for (String s : test)
										csvOutput.write(s);
									csvOutput.endRecord();
								}
								// else assume that the file already has the
								// correct header line

								// write out a few records
								int pentla = 0;
								while (pentla <= tablicaDanych.length - 1) {
									for (int i = 0; i <= tablicaDanych[0].length - 1; i++)
										csvOutput
												.write(tablicaDanych[pentla][i]);
									csvOutput.endRecord();
									pentla++;
								}

								csvOutput.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
							CsvFrame.csvInsert.setForeground(new Color(0, 100,
									0));
							CsvFrame.csvInsert.append("Zapisano plik \""
									+ outputFile + "\"\r\n");
						}

					}
				});
				popup.add(saveFile);
				popup.add(tableMenu);

				SwingUtilities.updateComponentTreeUI(Csv_Reader.frame);

			}
		});
		
		return generuj;

	}

	public static JButton addButton(String name,
			final String[][] niepowtarzalne, final String[][] powtarjace,
			final String[] nameOfColumns) {
		final JButton generuj = new JButton("Generuj Tabele");
		generuj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JInternalFrame listFrame = new JInternalFrame(
						"Niepowtarzalne z plików \""
								+ Csv_File.csvFileName(CompareCSV
										.getFirstFilePath())
								+ "\" oraz \""
								+ Csv_File.csvFileName(CompareCSV
										.getSecondFilePath()) + "\"", true,
						true, true, true);
				JInternalFrame listFrame2 = new JInternalFrame(
						"Powtazajace sie z plików \""
								+ Csv_File.csvFileName(CompareCSV
										.getFirstFilePath())
								+ "\" oraz \""
								+ Csv_File.csvFileName(CompareCSV
										.getSecondFilePath()) + "\"", true,
						true, true, true);
				final JTable model = new JTable(niepowtarzalne, nameOfColumns);
				model.setAutoCreateRowSorter(true);
				model.setDefaultRenderer(Object.class, new ColorCellRenderer());
				JScrollPane scrollPane = new JScrollPane(model);
				scrollPane.setRowHeaderView(new LineNumberTable(model));
				listFrame.setContentPane(scrollPane);
				listFrame.setLocation(0, 0);
				listFrame.setSize(500, 200);
				listFrame.setVisible(true);
				CsvFrame.desktop.add(listFrame, BorderLayout.NORTH);
				JTable model2 = new JTable(powtarjace, nameOfColumns);
				model2.setAutoCreateRowSorter(true);
				model2.setDefaultRenderer(Object.class, new ColorCellRenderer());
				JScrollPane scrollPane2 = new JScrollPane(model2);
				scrollPane2.setRowHeaderView(new LineNumberTable(model2));
				listFrame2.setContentPane(scrollPane2);
				listFrame2.setLocation(0, 0);
				listFrame2.setSize(500, 200);
				listFrame2.setVisible(true);
				CsvFrame.desktop.add(listFrame2, BorderLayout.CENTER);
				SwingUtilities.updateComponentTreeUI(CsvFrame.desktop);
				generuj.setVisible(false);
				CsvFrame.removedColumns = new ArrayList<TableColumn>();
				JPopupMenu popup = new JPopupMenu();
				JMenu selectionMenu = new JMenu("Obszar Zaznaczenia");
				final JCheckBoxMenuItem rowsItem = new JCheckBoxMenuItem(
						"Wiersze");
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
						cellsItem.setSelected(model.getCellSelectionEnabled());
					}
				});
				selectionMenu.add(rowsItem);

				columnsItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						model.clearSelection();
						model.setColumnSelectionAllowed(columnsItem
								.isSelected());
						cellsItem.setSelected(model.getCellSelectionEnabled());
					}
				});
				selectionMenu.add(columnsItem);

				cellsItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						model.clearSelection();
						model.setCellSelectionEnabled(cellsItem.isSelected());
						rowsItem.setSelected(model.getRowSelectionAllowed());
						columnsItem.setSelected(model
								.getColumnSelectionAllowed());
					}
				});
				selectionMenu.add(cellsItem);
				popup.add(selectionMenu);
				JMenuItem saveFile = new JMenuItem("Zapisz");
				saveFile.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						JFileChooser FC = new JFileChooser();
						FileNameExtensionFilter filter = new FileNameExtensionFilter(
								"Pliki .csv", "csv");
						FC.setFileFilter(filter);
						int result = FC.showSaveDialog(Csv_Reader.frame);
						if (result == JFileChooser.APPROVE_OPTION) {
							String filePath = FC.getSelectedFile().getPath();
							String outputFile = (filePath + ".csv");

							// before we open the file check to see if it
							// already exists
							boolean alreadyExists = new File(outputFile)
									.exists();

							try {
								// use FileWriter constructor that specifies
								// open for appending

								char b = ';';
								CsvWriter csvOutput = new CsvWriter(
										new FileWriter(outputFile, true), b);

								// if the file didn't already exist then we need
								// to write out the header line
								if (!alreadyExists) {
									for (String s : nameOfColumns)
										csvOutput.write(s);
									csvOutput.endRecord();
								}
								// else assume that the file already has the
								// correct header line

								// write out a few records
								int pentla = 0;
								while (pentla <= niepowtarzalne.length - 1) {
									for (int i = 0; i <= niepowtarzalne[0].length - 1; i++)
										csvOutput
												.write(niepowtarzalne[pentla][i]);
									csvOutput.endRecord();
									pentla++;
								}

								csvOutput.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
							CsvFrame.csvInsert.setForeground(new Color(0, 100,
									0));
							CsvFrame.csvInsert.append("Zapisano plik \""
									+ outputFile + "\"\r\n");
						}

					}
				});
				popup.add(saveFile);
				model.setComponentPopupMenu(popup);
				CsvFrame.csvInsert.setComponentPopupMenu(popup);
				JMenu tableMenu = new JMenu("Edycja");

				JMenuItem hideColumnsItem = new JMenuItem("Ukryj Kolumny");
				hideColumnsItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						int[] selected = model.getSelectedColumns();
						TableColumnModel columnModel = model.getColumnModel();

						// usuwa kolumny z widoku tabeli, począwszy od
						// najwyższego indeksu, aby nie zmieniać numerów
						// kolumn

						for (int i = selected.length - 1; i >= 0; i--) {
							TableColumn column = columnModel
									.getColumn(selected[i]);
							model.removeColumn(column);

							// przechowuje ukryte kolumny do ponownej
							// prezentacji

							CsvFrame.removedColumns.add(column);
						}
					}
				});
				tableMenu.add(hideColumnsItem);
				JMenuItem showColumnsItem = new JMenuItem(
						"Pokaż Ukryte Kolumny");
				showColumnsItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						// przywraca wszystkie usunięte kolumny
						for (TableColumn tc : CsvFrame.removedColumns)
							model.addColumn(tc);
						CsvFrame.removedColumns.clear();
					}
				});
				tableMenu.add(showColumnsItem);
				popup.add(tableMenu);

				SwingUtilities.updateComponentTreeUI(Csv_Reader.frame);

			}
		});
		return generuj;
	}

}
