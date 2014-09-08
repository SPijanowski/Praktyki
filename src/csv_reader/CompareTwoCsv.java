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

import java.awt.Component;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JList;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JSeparator;
import java.awt.Color;

public class CompareTwoCsv extends JPanel {

	private static final long serialVersionUID = 3695301598444424566L;
	private boolean ok;
	private JDialog dialog;
	private JButton okButton;
	private JButton btnWybierzDrugiPlik;
	private JFileChooser csvFirstFileChooser;
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
		lblNewLabel.setFont(lblNewLabel.getFont().deriveFont(lblNewLabel.getFont().getStyle() | Font.BOLD, lblNewLabel.getFont().getSize() + 5f));
		
		JLabel label = new JLabel("Dane pierwszego pliku");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JComboBox firstFileFirstColumn = new JComboBox();
		
		JComboBox firstFileSecondColumn = new JComboBox();
		
		JComboBox secondFileFirstColumn = new JComboBox();
		
		JComboBox secondFileSecondColumn = new JComboBox();
		
		JLabel label_1 = new JLabel("Dane drugiego pliku");
		label_1.setFont(label_1.getFont().deriveFont(label_1.getFont().getStyle() | Font.BOLD));
		final JButton wybierz1 = new JButton("Wybierz pierwszy plik");
		wybierz1.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent event) {
				csvFirstFileChooser.setCurrentDirectory(new File("."));
				int result = csvFirstFileChooser
						.showOpenDialog(CompareTwoCsv.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					firstFilePath = csvFirstFileChooser.getSelectedFile()
							.getPath();
					setFirstFilePath(firstFilePath);
					wybierz1.setEnabled(false);
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
						
							btnWybierzDrugiPlik.setEnabled(true);
							return null;}
						protected void done(){
							
							wybierz1.setEnabled(true);
							Toolkit.getDefaultToolkit().beep();
							
						}};
					worker.execute();
				}
				}
			});
		
		final JButton wybierz2 = new JButton("Wybierz drugi plik");
		wybierz2.addActionListener(new ActionListener() {
			

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
							wybierz2.setEnabled(false);
						}
					};
					worker.execute();
					}
				}
			});
		
		
		
		JLabel lblPierwszaKolumna = new JLabel("Pierwsza kolumna");
		lblPierwszaKolumna.setFont(new Font("Tahoma", Font.ITALIC, 11));
		
		JLabel label_2 = new JLabel("Pierwsza kolumna");
		label_2.setFont(new Font("Tahoma", Font.ITALIC, 11));
		
		JLabel lblDrugaKolumna = new JLabel("Druga kolumna");
		lblDrugaKolumna.setForeground(Color.RED);
		lblDrugaKolumna.setFont(new Font("Tahoma", Font.ITALIC, 11));
		
		JLabel label_3 = new JLabel("Druga kolumna");
		label_3.setForeground(Color.RED);
		label_3.setFont(new Font("Tahoma", Font.ITALIC, 11));
		
		JSeparator separator = new JSeparator();
		
		
		
		
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
					.addComponent(wybierz2)
					.addGap(19))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(37)
					.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addGap(72)
					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addGap(61))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
						.addComponent(firstFileFirstColumn, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
						.addComponent(secondFileFirstColumn, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPierwszaKolumna)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDrugaKolumna)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(wybierz1)
								.addComponent(firstFileSecondColumn, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
								.addComponent(secondFileSecondColumn, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(54)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(62, Short.MAX_VALUE))
				.addComponent(separator, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(wybierz1))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2)
						.addComponent(label_3))
					.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(firstFileSecondColumn, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(firstFileFirstColumn, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(25)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(wybierz2))
					.addGap(24)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPierwszaKolumna)
						.addComponent(lblDrugaKolumna))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(secondFileFirstColumn, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(secondFileSecondColumn, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_1)
						.addComponent(okButton))
					.addGap(19))
		);
		groupLayout.linkSize(SwingConstants.VERTICAL, new Component[] {wybierz1, wybierz2});
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
