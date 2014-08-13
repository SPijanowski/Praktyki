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

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.csvreader.CsvReader;

public class CsvFrame extends JFrame implements Serializable {

	private static final long serialVersionUID = 2537576951291269546L;
	public static final int TEXT_ROWS = 5;
	public static final int TEXT_COLUMNS = 90;
	public static JMenuBar mBar;
	private CsvChooser dialog = null;
	static JTextArea csvInsert;
	private JPanel northPanel = new JPanel();
	 private ArrayList<TableColumn> removedColumns;

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
		csvInsert.setEditable(false);
		JScrollPane scroll = new JScrollPane(csvInsert);
		northPanel.add(scroll);
		add(northPanel, BorderLayout.SOUTH);
		
	}



	private class ConnectAction implements ActionListener {
		private JButton generuj;
		private JTable model;


		public void actionPerformed(ActionEvent event) {
			
			if (dialog == null)
				dialog = new CsvChooser();
				dialog.setSize(400, 400);

			if (dialog.showDialog(CsvFrame.this, "Wybier Plik")) {
				
				String a = Csv_File.getSeparator();
				
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
				csvInsert.setForeground(new Color(0,100,0));
				int wczyt = Csv_Table.getRow() +1;
				csvInsert.append("GOTOWE! Wczytano " + wczyt+" Rekordów\r\n");

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
					if( wybraane !=null)
					{
						test = wybraane;
					}
					else{
						test = CsvChooser.getDataArray();
					}
					try {
						
						
						CsvReader tabela = new CsvReader(path, sep);
						tabela.readHeaders();
						while (tabela.readRecord()){
							++line_number;
						}
						tabela.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					String[][] tablicaDanych = new String[line_number][test.length];
					try {
						CsvReader tabela = new CsvReader(path, sep);
						tabela.readHeaders();
						while (tabela.readRecord()){
							for(int i =0; i<test.length; i++)
						{	
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
					
					
					
					
					
					final JTable model = new JTable(tablicaDanych, test);
					model.setDefaultRenderer( Object.class, new ColorCellRenderer() );
					JScrollPane scrollPane = new JScrollPane(model);
					scrollPane.setRowHeaderView( new LineNumberTable( model ) );
					add(scrollPane, BorderLayout.CENTER);
					generuj.setVisible(false);
					/**JToolBar bar = new JToolBar();
					String[] b = test;
					
					
					
					int i =0;
					for(i = 0; i<b.length; i++)	{				
					JCheckBox kolumna = new JCheckBox(b[i], true);
					bar.add(kolumna);}
					add(bar, BorderLayout.NORTH);*/
					removedColumns = new ArrayList<TableColumn>();
					 JPopupMenu popup = new JPopupMenu();

				      JMenu selectionMenu = new JMenu("Obszar Zaznaczenia");
				      

				      final JCheckBoxMenuItem rowsItem = new JCheckBoxMenuItem("Wiersze");
				      final JCheckBoxMenuItem columnsItem = new JCheckBoxMenuItem("Kolumny");
				      final JCheckBoxMenuItem cellsItem = new JCheckBoxMenuItem("Komórki");

				      rowsItem.setSelected(model.getRowSelectionAllowed());
				      columnsItem.setSelected(model.getColumnSelectionAllowed());
				      cellsItem.setSelected(model.getCellSelectionEnabled());

				      rowsItem.addActionListener(new ActionListener()
				      {
				         public void actionPerformed(ActionEvent event)
				         {
				            model.clearSelection();
				            model.setRowSelectionAllowed(rowsItem.isSelected());
				            cellsItem.setSelected(model.getCellSelectionEnabled());
				         }
				      });
				      selectionMenu.add(rowsItem);
				     
				      columnsItem.addActionListener(new ActionListener()
				      {
				         public void actionPerformed(ActionEvent event)
				         {
				            model.clearSelection();
				            model.setColumnSelectionAllowed(columnsItem.isSelected());
				            cellsItem.setSelected(model.getCellSelectionEnabled());
				         }
				      });
				      selectionMenu.add(columnsItem);
				      
				      cellsItem.addActionListener(new ActionListener()
				      {
				         public void actionPerformed(ActionEvent event)
				         {
				            model.clearSelection();
				            model.setCellSelectionEnabled(cellsItem.isSelected());
				            rowsItem.setSelected(model.getRowSelectionAllowed());
				            columnsItem.setSelected(model.getColumnSelectionAllowed());
				         }
				      });
				      selectionMenu.add(cellsItem);
				      popup.add(selectionMenu);
				      
				      model.setComponentPopupMenu(popup);
				      csvInsert.setComponentPopupMenu(popup);
				      JMenu tableMenu = new JMenu("Edycja");
				      

				      JMenuItem hideColumnsItem = new JMenuItem("Ukryj Kolumny");
				      hideColumnsItem.addActionListener(new ActionListener()
				      {
				         public void actionPerformed(ActionEvent event)
				         {
				            int[] selected = model.getSelectedColumns();
				            TableColumnModel columnModel = model.getColumnModel();

				            // usuwa kolumny z widoku tabeli, począwszy od
				            // najwyższego indeksu, aby nie zmieniać numerów kolumn

				            for (int i = selected.length - 1; i >= 0; i--)
				            {
				               TableColumn column = columnModel.getColumn(selected[i]);
				               model.removeColumn(column);

				               // przechowuje ukryte kolumny do ponownej prezentacji

				               removedColumns.add(column);
				            }
				         }
				      });
				      tableMenu.add(hideColumnsItem);

				      JMenuItem showColumnsItem = new JMenuItem("Pokaż Ukryte Kolumny");
				      showColumnsItem.addActionListener(new ActionListener()
				      {
				         public void actionPerformed(ActionEvent event)
				         {
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