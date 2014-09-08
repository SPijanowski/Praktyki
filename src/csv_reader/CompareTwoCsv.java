package csv_reader;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JList;

public class CompareTwoCsv extends JPanel {

	private static final long serialVersionUID = 3695301598444424566L;
	private boolean ok;
	private JDialog dialog;
	private JButton okButton;
	private JButton btnNewButton_1;
	private JButton btnWybierzDrugiPlik;
	private JFileChooser csvFirstFileChooser;
	private JList list;
	private JList list_1;
	public static String firstFilePath;
	
	private static String firstSeparator;
	private static String secondSeparator;
	private static String secondFilePath;
	private static int selc;
	private static int selc2;
	public static String[] firstFileSelected;
	public static String[] secondFileSelected;

	private static String[] firstDataArray;
	private static String[] secondDataArray;
	private static int firstFileRow;
	private static int secondFileRow;
	private static JList list1;

	
	public CompareTwoCsv() {
		// Dodawanie przycisku wyboru pliku
		csvFirstFileChooser = new JFileChooser();
		// Akceptowanie wyboru plików .csv
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Pliki CSV", "csv");
		csvFirstFileChooser.setFileFilter(filter);
		
		final JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ok = true;
				dialog.setVisible(false);
				SwingUtilities.updateComponentTreeUI(Csv_Reader.frame);
			}
		});
		okButton.setEnabled(false);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		
		
		JLabel lblNewLabel = new JLabel("Porównanie plików CSV");
		
		JLabel label = new JLabel("Dane pierwszego pliku");
		
		JLabel label_1 = new JLabel("Dane drugiego pliku");
		@SuppressWarnings("rawtypes")
		JList list_2 = new JList();
		JScrollPane list_1 = new JScrollPane(list_2);
		@SuppressWarnings("rawtypes")
		
		JScrollPane list = new JScrollPane(list1);
		final JButton btnWybierzPierwszyPlik = new JButton("Wybierz pierwszy plik");
		btnWybierzPierwszyPlik.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent event) {
				csvFirstFileChooser.setCurrentDirectory(new File("."));
				int result = csvFirstFileChooser
						.showOpenDialog(CompareTwoCsv.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					firstFilePath = csvFirstFileChooser.getSelectedFile()
							.getPath();
					setFirstFilePath(firstFilePath);
					btnWybierzPierwszyPlik.setEnabled(false);
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
						
						
						@SuppressWarnings({ "unchecked", "rawtypes" })
						@Override
						protected Void doInBackground() throws Exception {
							btnWybierzDrugiPlik.setEnabled(true);
							okButton.setEnabled(false);
							// Wybór separatora
							firstSeparator = Csv_File.separation(firstFilePath);
							// Wczytanie pierwszego wiersza
							setFirstDataArray(Csv_File.firstRow(firstFilePath));
							// Obliczanie wielkości wczytanych danych
							setFirstFileRow(Csv_File.countRow(firstFilePath) - 1);

							String[] dane = firstDataArray;

						final JList list = new JList(dane);
							list.setVisibleRowCount(4);
							list.addListSelectionListener(new ListSelectionListener() {
								@SuppressWarnings("deprecation")
								public void valueChanged(ListSelectionEvent event) {
									setFirstFileSelected(null);
									Object values[] = list.getSelectedValues();
									int lenght = values.length;
									firstFileSelected = new String[lenght];
									System.arraycopy(values, 0, firstFileSelected, 0,
											lenght);
									selc = list.getLeadSelectionIndex();
								}
							}); 
						
							JScrollPane scroll = new JScrollPane(list);
							list1 = list;
							return null;}
						protected void done(){
							
							btnWybierzPierwszyPlik.setEnabled(true);
							Toolkit.getDefaultToolkit().beep();
							btnWybierzDrugiPlik.setEnabled(true);
						}};
					worker.execute();
				}
				}
			});
		
		final JButton btnWybierzDrugiPlik = new JButton("Wybierz drugi plik");
		btnWybierzDrugiPlik.setEnabled(false);
		btnWybierzDrugiPlik.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent event) {
				csvFirstFileChooser.setCurrentDirectory(new File("."));
				int result = csvFirstFileChooser
						.showOpenDialog(CompareTwoCsv.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					secondFilePath = csvFirstFileChooser.getSelectedFile()
							.getPath();
					setSecondFilePath(secondFilePath);
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
						@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
						@Override
						protected Void doInBackground() throws Exception {
							// Wybór separatora
							secondSeparator = Csv_File
									.separation(secondFilePath);
							// Wczytanie pierwszego wiersza
							setSecondDataArray(Csv_File
									.firstRow(secondFilePath));
							// Obliczanie wielkości wczytanych danych
							setSecondFileRow(Csv_File.countRow(secondFilePath) - 1);

							String[] dane = secondDataArray;

							final JList abcd = new JList(dane);
							abcd.setVisibleRowCount(4);
							abcd.addListSelectionListener(new ListSelectionListener() {

								@SuppressWarnings("deprecation")
								public void valueChanged(
										ListSelectionEvent event) {
									setSecondFileSelected(null);
									Object values[] = abcd.getSelectedValues();
									int lenght = values.length;
									secondFileSelected = new String[lenght];
									System.arraycopy(values, 0,
											secondFileSelected, 0, lenght);
									setSelc2(abcd.getLeadSelectionIndex());
								}
							});

							JScrollPane scroll = new JScrollPane(abcd);
							return null;
						}

						@Override
						protected void done() {
							Toolkit.getDefaultToolkit().beep();
							okButton.setEnabled(true);
							btnWybierzDrugiPlik.setEnabled(false);
						}
					};
					worker.execute();
					}
				}
			});
		
		JScrollPane scrollPane = new JScrollPane();
		
		
		
		
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(84)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
					.addGap(85))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
					.addComponent(btnWybierzDrugiPlik)
					.addGap(19))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
					.addComponent(btnWybierzPierwszyPlik)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(40)
					.addComponent(list_1, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(64, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(37)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(list, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
							.addGap(72)
							.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)))
					.addGap(61))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(btnWybierzPierwszyPlik))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(list_1, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(btnWybierzDrugiPlik))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(13)
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNewButton_1)
								.addComponent(okButton)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(37)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(19))
		);
		groupLayout.linkSize(SwingConstants.VERTICAL, new Component[] {btnWybierzPierwszyPlik, btnWybierzDrugiPlik});
		groupLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {okButton, btnNewButton_1});
		setLayout(groupLayout);

	}
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
			dialog.getContentPane().add(this);
			dialog.getRootPane().setDefaultButton(okButton);
			dialog.pack();
		}

		// Ustawienie tytułu i wyświetlenie okna dialogowego
		dialog.setTitle(title);
	
		dialog.setVisible(true);
		return ok;
	}
	public static void setSelc(int i) {
		selc = i;

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

	public static int getFirstFileRow() {
		return firstFileRow;
	}

	public void setSecondFileRow(int r) {
		secondFileRow = r;

	}

	public static int getSecondFileRow() {
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

	public static void setFirstDataArray(String[] afirstDataArray) {
		firstDataArray = afirstDataArray;
	}

	public static void setFirstFileSelected(String[] firstFileSelected) {
		CompareCSV.firstFileSelected = firstFileSelected;
	}

	public static void setSecondDataArray(String[] asecondDataArray) {
		secondDataArray = asecondDataArray;
	}

	public static void setSecondFileSelected(String[] secondFileSelected) {
		CompareCSV.secondFileSelected = secondFileSelected;
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

	public static void setSelc2(int aselc2) {
		selc2 = aselc2;
	}
}
