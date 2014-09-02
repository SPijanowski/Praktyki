package csv_reader;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CompareCSV extends JPanel {

	public static String[] firstFileSelected;
	public static String[] secondFileSelected;

	private static String[] firstDataArray;
	private static String[] secondDataArray;
	private static final long serialVersionUID = 7924120448516164063L;
	private JButton okButton;
	private boolean ok2;
	private JDialog compareCsv;
	private JPanel northPanel = new JPanel();
	private JFileChooser csvFirstFileChooser;
	private static String firstSeparator;
	private static String secondSeparator;
	private static String firstFilePath;
	private static String secondFilePath;
	private static int selc;
	private static int selc2;

	private JButton wybierz1;

	private JPanel panel = new JPanel();

	private JButton wybierz2;
	private static int firstFileRow;
	private static int secondFileRow;

	public CompareCSV() {

		setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();

		okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				ok2 = true;
				compareCsv.setVisible(false);
				SwingUtilities.updateComponentTreeUI(Csv_Reader.frame);

			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				compareCsv.setVisible(false);
			}
		});
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel, BorderLayout.SOUTH);

		wybierz1 = new JButton("Wybierz pierwszy plik");
		wybierz1.addActionListener(new ActionListener() {
			

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void actionPerformed(ActionEvent event) {
				northPanel.removeAll();
				northPanel.updateUI();
				setFirstFileSelected(null);
				setFirstDataArray(null);
				csvFirstFileChooser.setCurrentDirectory(new File("."));
				int result = csvFirstFileChooser.showOpenDialog(CompareCSV.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					firstFilePath = csvFirstFileChooser.getSelectedFile().getPath();
					setFirstFilePath(firstFilePath);
					// Wybór separatora
					firstSeparator = Csv_File.separation(firstFilePath);
					//Wczytanie pierwszego wiersza
					setFirstDataArray(Csv_File.firstRow(firstFilePath));
					//Obliczanie wielkości wczytanych danych
					setFirstFileRow(Csv_File.countRow(firstFilePath) - 1);
				
					String[] dane = firstDataArray;
					
					final JList abcd = new JList(dane);
					abcd.setVisibleRowCount(4);
					abcd.addListSelectionListener(new ListSelectionListener() {
						@SuppressWarnings("deprecation")
						public void valueChanged(ListSelectionEvent event) {
							setFirstFileSelected(null);
							Object values[] = abcd.getSelectedValues();
							int lenght = values.length;
							firstFileSelected = new String[lenght];
							System.arraycopy(values, 0, firstFileSelected, 0, lenght);
							selc = abcd.getLeadSelectionIndex();
						}
					});					
					northPanel.add(new JLabel("Dane pierwszego pliku do porównania"));
					JScrollPane scroll = new JScrollPane(abcd);
					northPanel.add(scroll);
					add(northPanel, BorderLayout.NORTH);
					SwingUtilities.updateComponentTreeUI(northPanel);
					SwingUtilities.updateComponentTreeUI(compareCsv);
					wybierz2.setVisible(true);
					
					
				}
			}
		});
		wybierz2 = new JButton("Wybierz drugi plik");
		wybierz2.addActionListener(new ActionListener() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void actionPerformed(ActionEvent event) {
				selc2 = -1;
				northPanel.updateUI();
				csvFirstFileChooser.setCurrentDirectory(new File("."));
				int result = csvFirstFileChooser.showOpenDialog(CompareCSV.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					secondFilePath = csvFirstFileChooser.getSelectedFile().getPath();
					setSecondFilePath(secondFilePath);
					// Wybór separatora
					secondSeparator = Csv_File.separation(secondFilePath);
					//Wczytanie pierwszego wiersza
					setSecondDataArray(Csv_File.firstRow(secondFilePath));
					//Obliczanie wielkości wczytanych danych
					setSecondFileRow(Csv_File.countRow(secondFilePath) - 1);
				
					String[] dane = secondDataArray;
					
					final JList abcd = new JList(dane);
					abcd.setVisibleRowCount(4);
					abcd.addListSelectionListener(new ListSelectionListener() {
			
						@SuppressWarnings("deprecation")
						public void valueChanged(ListSelectionEvent event) {
							setSecondFileSelected(null);
							Object values[] = abcd.getSelectedValues();
							int lenght = values.length;
							secondFileSelected = new String[lenght];
							System.arraycopy(values, 0, secondFileSelected, 0, lenght);
							setSelc2(abcd.getLeadSelectionIndex());
						}
					});
					northPanel.add(new JLabel("Dane pierwszego plik do porównania"));
					JScrollPane scroll = new JScrollPane(abcd);
					northPanel.add(scroll);
					add(northPanel, BorderLayout.NORTH);
					SwingUtilities.updateComponentTreeUI(northPanel);
					SwingUtilities.updateComponentTreeUI(compareCsv);
					wybierz2.setVisible(false);}
				
					}
		});
		panel.setLayout(new GridLayout(2, 2));
		northPanel.setLayout(new GridLayout(2, 2));

		panel.add(new JLabel("Wybierz Pierwszy plik:"));
		panel.add(new JLabel("Wybierz Drugi plik:"));
		panel.add(wybierz1);
		panel.add(wybierz2);
		add(panel, BorderLayout.CENTER);
		// Dodawanie przycisku wyboru pliku
		csvFirstFileChooser = new JFileChooser();
		// Akceptowanie wyboru plików .csv
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Pliki CSV", "csv");
		csvFirstFileChooser.setFileFilter(filter);
	}


	public static int getSelc() {
		return selc;
	}

	public static String[] getSecondDataArray() {
		return Csv_File.removeEmptyField(secondDataArray);
	}


	public void setFirstFileRow(int r) {
		firstFileRow = r;
	}

	public static int getFirstFileRow(){
		return firstFileRow;
	}
	
	public void setSecondFileRow(int r) {
		secondFileRow = r;
		
	}
	
	public static int getSecondFileRow(){
		return secondFileRow;
	}

	public void setFirstFilePath(String f1) {
		firstFilePath = f1;
	}
	
	public void setSecondFilePath(String f2) {
		secondFilePath = f2;
	}
	public static String[] getFirstDataArray() {
		return Csv_File.removeEmptyField(firstDataArray);
	}

	public static void setFirstDataArray(String[] firstDataArray) {
		CompareCSV.firstDataArray = firstDataArray;
	}

	public static void setFirstFileSelected(String[] firstFileSelected) {
		CompareCSV.firstFileSelected = firstFileSelected;
	}
	public static void setSecondDataArray(String[] secondDataArray) {
		CompareCSV.secondDataArray = secondDataArray;
	}

	public static void setSecondFileSelected(String[] secondFileSelected) {
		CompareCSV.secondFileSelected = secondFileSelected;
	}

	public boolean showDialog(Component parent, String title) {
		ok2 = false;
		Frame owner = null;
		if (parent instanceof Frame)
			owner = (Frame) parent;
		else
			owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class,
					parent);
		if (compareCsv == null || compareCsv.getOwner() != owner) {
			compareCsv = new JDialog(owner, true);
			compareCsv.add(this);
			compareCsv.getRootPane().setDefaultButton(okButton);
			compareCsv.pack();
		}
		compareCsv.setTitle(title);
		compareCsv.setSize(500, 300);
		compareCsv.setVisible(true);
		return ok2;
	}


	public static String getFirstFilePath() {
		return firstFilePath;
	}


	public static String getFirstSeparator() {
		return firstSeparator;
	}


	public static String getSecondFilePath() {
		return secondFilePath;
	}


	public static String getSecondSeparator() {
		return secondSeparator;
	}


	public static String[] getFirstFileSelected() {
	
		return firstFileSelected;
	}


	public static String[] getSecondFileSelected() {
		return secondFileSelected;
	}


	public static int getSelc2() {
		return selc2;
	}


	public static void setSelc2(int selc2) {
		CompareCSV.selc2 = selc2;
	}
}
