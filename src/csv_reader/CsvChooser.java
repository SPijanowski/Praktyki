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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.csvreader.CsvReader;

public class CsvChooser extends JPanel implements Serializable {

	private static final long serialVersionUID = 5054426769966312408L;
	private JButton okButton;
	private boolean ok;
	private JDialog dialog;
	private JFileChooser csvFileChooser;
	private JButton wybierz;
	private static String[] dataArray = { "" };
	private int countRow = 0;
	private JPanel panel = new JPanel();
	private JPanel northPanel = new JPanel();
	public static String[] selected;
	public static JCheckBox duplicate;
	public static boolean selec;
	

	public CsvChooser() {
		setSize(100, 600);
		wybierz = new JButton("Wybierz plik");
		wybierz.addActionListener(new fileOpenListener());

		setLayout(new BorderLayout());

		// Utworzenie panelu z polami nazwy użytkownika i hasła
		panel.setLayout(new GridLayout(2, 1));
		JPanel panel2 = new JPanel();
		panel2.add(new JLabel("Wybierz Plik:"));
		panel2.add(wybierz);
		panel2.setLayout(new GridLayout(1,2));
		panel.add(panel2);
		JPanel buttonPanel2 = new JPanel();
		buttonPanel2.setLayout(new GridLayout(1,2));
		final JCheckBox duplicate = new JCheckBox("Usuń duplikaty");
		duplicate.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent event){
				selec = duplicate.isSelected();
			}
		});
		buttonPanel2.add(duplicate);
		
		panel.add(buttonPanel2);
		add(panel, BorderLayout.CENTER);
		System.out.println(duplicate.isSelected());
		

		// Utworzenie przycisków OK i Anuluj, które zamykają okno dialogowe
		okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ok = true;
				dialog.setVisible(false);
				SwingUtilities.updateComponentTreeUI(Csv_Reader.frame);

			}
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dialog.setVisible(false);
			}
		});

		// Dodawanie przycisków w pobliżu południowej krawędzi
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel, BorderLayout.SOUTH);

		// Dodawanie przycisku wyboru pliku
		csvFileChooser = new JFileChooser();
		
		// Akceptowanie wyboru plików .csv
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Pliki CSV", "csv");
		csvFileChooser.setFileFilter(filter);
	}

	/**
	 * Wyświetla panel z elementami przyjmującymi dane od użytkownika w oknie
	 * dialogowym	 
	 * @param parent
	 * komponent w ramce nadrzędnej lub wartość null
	 * @param title
	 * tytuł okna dialogowego
	 */
	public boolean showDialog(Component parent, String title) {
		ok = false;
		
		// Lokalizacja ramki nadrzędnej
		Frame owner = null;
		if (parent instanceof Frame)
			owner = (Frame) parent;
		else
			owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class,
					parent);
		
		// Jeśli jest to pierwszy raz lub zmienił się użytkownik, utworzenie
		// nowego okna dialogowego
		if (dialog == null || dialog.getOwner() != owner) {
			dialog = new JDialog(owner, true);
			dialog.add(this);
			dialog.getRootPane().setDefaultButton(okButton);
			dialog.pack();
		}

		// Ustawienie tytułu i wyświetlenie okna dialogowego
		dialog.setTitle(title);
		dialog.setSize(200, 300);
		dialog.setVisible(true);
		return ok;
	}

	private class fileOpenListener implements ActionListener {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void actionPerformed(ActionEvent event) {
			northPanel.removeAll();
			northPanel.updateUI();
			setSelected(null);
			setDataArray(null);
			countRow = 0;
			csvFileChooser.setCurrentDirectory(new File("."));
			int result = csvFileChooser.showOpenDialog(CsvChooser.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				String csvFilePath = csvFileChooser.getSelectedFile().getPath();
				Csv_File.setCsvFilePath(csvFilePath);
				
				// Wybór separatora
				Csv_File.setSeparator(Csv_File.separation(csvFilePath));

				BufferedReader CSVFile = null;
				try {
					CSVFile = new BufferedReader(new FileReader(csvFilePath));
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				}
				String dataRow = null;
				try {
					dataRow = CSVFile.readLine();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(Csv_File.separation(csvFilePath)!= null){if(Csv_File.separation(csvFilePath)!= null)
				{setDataArray(dataRow.split(Csv_File.separation(csvFilePath)));}
				else{dataArray[0] = dataRow;}
				}
									
				{
					try {
						CSVFile.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					CsvReader tabela;
					String a = Csv_File.getSeparator();
					if(a!=null){
					char b = a.charAt(0);
					tabela = new CsvReader(csvFilePath, b);}
					else{				
					tabela = new CsvReader(csvFilePath);}
					
					tabela.readHeaders();
					while (tabela.readRecord()) {
						countRow++;
					}
					tabela.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Csv_Table.setRow(countRow - 1);
				String[] tmp = getDataArray();
				
				String[] dane = getDataArray();
				if(tmp != null){dane = Csv_File.removeEmptyField(getDataArray());}
				else{BufferedReader CSVFile2 = null;
				try {
					CSVFile2 = new BufferedReader(new FileReader(csvFilePath));
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				}
				String dataRow2 = null;
				try {
					dataRow2 = CSVFile2.readLine();
				} catch (IOException e1) {
					e1.printStackTrace();
					
				}
				String[] absc = {dataRow2};
				dane = absc;
				setDataArray(absc);
				}
				
				final JList abcd = new JList(getDataArray());
				abcd.setVisibleRowCount(4);
				abcd.addListSelectionListener(new ListSelectionListener() {
					@SuppressWarnings("deprecation")
					public void valueChanged(ListSelectionEvent event) {
						setSelected(null);
						Object values[] = abcd.getSelectedValues();
						int lenght = values.length;
						selected = new String[lenght];
						System.arraycopy(values, 0, selected, 0, lenght);
					}
				});
				JScrollPane scroll = new JScrollPane(abcd);
				northPanel.add(scroll);
				add(northPanel, BorderLayout.NORTH);
				SwingUtilities.updateComponentTreeUI(northPanel);
				SwingUtilities.updateComponentTreeUI(dialog);
				
			}
			
		}
	}

	public static String[] getDataArray() {
		return dataArray;
	}

	public static void setDataArray(String[] split) {
		dataArray = split;
	}

	public static String[] getSelected() {
		return selected;
	}

	public void setSelected(String[] s) {
		selected = s;
	}
}
