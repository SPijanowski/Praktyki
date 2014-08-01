package csv_reader;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.Serializable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;



public class CsvChooser extends JPanel
{
	   private JTextField nazwa;
	   private JTextField separator;
	   private JButton okButton;
	   private boolean ok;
	   private JDialog dialog;
	   private JFileChooser csvFileChooser;
	   private JButton wybierz;

	   public CsvChooser()
	   {
	      
		   wybierz = new JButton("Wybierz");
		   wybierz.addActionListener(new fileOpenListener());
		   setLayout(new BorderLayout());

	      // Utworzenie panelu z polami nazwy użytkownika i hasła
	      
	      JPanel panel = new JPanel();
	      panel.setLayout(new GridLayout(3, 2));
	      panel.add(new JLabel("Wybierz Plik:"));
	      panel.add(wybierz);
	      panel.add(new JLabel("Plik Nazwa:"));
	      panel.add(nazwa = new JTextField(""));
	      panel.add(new JLabel("Separator:"));
	      panel.add(separator = new JTextField(""));	      
	      add(panel, BorderLayout.CENTER);

	      // Utworzenie przycisków OK i Anuluj, które zamykają okno dialogowe

	      okButton = new JButton("Ok");
	      okButton.addActionListener(new ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	               ok = true;
	               dialog.setVisible(false);
	            }
	         });

	      JButton cancelButton = new JButton("Cancel");
	      cancelButton.addActionListener(new ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	               dialog.setVisible(false);
	            }
	         });

	      // Dodawanie przycisków w pobliżu południowej krawędzi

	      JPanel buttonPanel = new JPanel();
	      buttonPanel.add(okButton);
	      buttonPanel.add(cancelButton);
	      add(buttonPanel, BorderLayout.SOUTH);
	      
	      //Dodawanie przycisku wyboru pliku
	      csvFileChooser = new JFileChooser();
	      //Akceptowanie wyboru plików .csv
	      FileNameExtensionFilter filter = new FileNameExtensionFilter("Pliki CSV","csv");
	      csvFileChooser.setFileFilter(filter);
	      
	   }

	   /**
	    * Ustawia wartości domyślne okna dialogowego
	    * @param u domyślne informacje użytkownika
	    */
	   public void setUser(Csv_File u)
	   {
	     nazwa.setText(u.getNazwa());
	   }

	   /**
	    * Pobiera dane podane w oknie dialogowym
	    * @return a obiekt typu User, którego stan reprezentuje dane wprowadzone w oknie dialogowym
	    */
	   public Csv_File getCsvFile()
	   {
	      return new Csv_File(nazwa.getText(), separator.getText());
	   }
	   public Csv_File getCsvPath()
	   {
		   return new Csv_File(csvFileChooser.getSelectedFile().getPath());
	   }

	   /**
	    * Wyświetla panel z elementami przyjmującymi dane od użytkownika w oknie dialogowym
	    * @param parent komponent w ramce nadrzędnej lub wartość null
	    * @param title tytuł okna dialogowego
	    */
	   public boolean showDialog(Component parent, String title)
	   {
	      ok = false;

	      // Lokalizacja ramki nadrzędnej

	      Frame owner = null;
	      if (parent instanceof Frame) owner = (Frame) parent;
	      else owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);

	      // Jeśli jest to pierwszy raz lub zmienił się użytkownik, utworzenie nowego okna dialogowego

	      if (dialog == null || dialog.getOwner() != owner)
	      {
	         dialog = new JDialog(owner, true);
	         dialog.add(this);
	         dialog.getRootPane().setDefaultButton(okButton);
	         dialog.pack();
	      }

	      // Ustawienie tytułu i wyświetlenie okna dialogowego

	      dialog.setTitle(title);
	      dialog.setVisible(true);
	      return ok;
	   }
	   private class fileOpenListener implements ActionListener
	   {
		   public void actionPerformed(ActionEvent event)
		   {
			   csvFileChooser.setCurrentDirectory(new File("."));
			   int result = csvFileChooser.showOpenDialog(CsvChooser.this);
			   if(result == JFileChooser.APPROVE_OPTION)
			   {
				   String csvFilePath = csvFileChooser.getSelectedFile().getPath();
				   Csv_File.setCsvFilePath(csvFilePath);
			   }
		   }
	   }
	}