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
import java.util.Arrays;

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
		add(new JScrollPane(csvInsert), BorderLayout.NORTH);
		pack();
	}

	/**
	 * Akcja Connect wyświetla okno dialogowe z polem hasła
	 */

	private class ConnectAction implements ActionListener {
		private JButton generuj;

		public void actionPerformed(ActionEvent event) {
			// Jeśli jest to pierwszy raz, tworzy okno dialogowe

			if (dialog == null)
				dialog = new CsvChooser();

			// Ustawianie wartości domyślnych

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

				csvInsert.append("GOTOWE! Wczytano + " + Csv_Table.getRow());

			}
			generuj = new JButton("Generuj Tabele");
			generuj.addActionListener(new ActionListener() {
				@SuppressWarnings("null")
				public void actionPerformed(ActionEvent event) {
					int line_number = 0;
					int line_number1 = 0;

					String[] dataArray = {};
					BufferedReader CSVFile = null;
					try {
						CSVFile = new BufferedReader(new FileReader(Csv_File.getScvFilePath()));
					} catch (FileNotFoundException e1) {
						
					}

						  String dataRow = "";
						try {
							dataRow = CSVFile.readLine();
						} catch (IOException e) {
						
							
						} 
						
					    

						  while (dataRow != null){
						   line_number++;
						   dataArray = dataRow.split(Csv_File.getSeparator());
						   
						   for (String item:dataArray) { 
							   System.out.print(item+";"); 
						        }
						   System.out.println(); 
						   
						   try {
							dataRow = CSVFile.readLine();
							
						} catch (IOException e) {
							
						} 
						  }
						  
						  try {
							CSVFile.close();
						} catch (IOException e) {
							
						}
						  String[][] tablicaDanych = new String[line_number-1][dataArray.length];
							try {
								CSVFile = new BufferedReader(new FileReader(Csv_File.getScvFilePath()));
							} catch (FileNotFoundException e1) {
								
							}
								String separator = Csv_File.getSeparator();
								  String dataRow1 = "";
								try {
									dataRow1 = CSVFile.readLine();
								} catch (IOException e) {} 
								while (dataRow1 != null){
									  line_number1++;				  
								try {
									dataRow1 = CSVFile.readLine();
									
									if(dataRow1 != null){
									dataArray = dataRow1.split(separator);
									for(int i=0; i<=dataArray.length-1;i++)
									tablicaDanych[line_number1-1][i]=dataArray[i];}
									else {}
								} catch (IOException e) {} 
								  }
								    try {
									CSVFile.close();
								} catch (IOException e) {}
						  

						  
						  
				System.out.println(line_number);
				int nosRows = tablicaDanych[0].length;  
				int nosCols = tablicaDanych.length;   
				System.out.println(nosRows+" "+nosCols);
				String[][] n2 = new String[line_number-1][dataArray.length];
				System.out.println(n2[0].length+" "+n2.length);

				System.out.println(Arrays.deepToString(tablicaDanych));
	
					JTable model = new JTable(tablicaDanych, CsvChooser.getDataArray());
					JScrollPane scrollPane = new JScrollPane(model);
					add(scrollPane, BorderLayout.CENTER);
					SwingUtilities.updateComponentTreeUI(Csv_Reader.frame);
					
					generuj.setVisible(false);

				}
			});
			add(generuj, BorderLayout.SOUTH);
		}
	}
}