package csv_reader;
/**
 * Program umożliwijący czytanie plików CSV
 * @author Sylwester Pijanowski
 * @version 1.00
 * @date 01.08.2014
 */

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.*;

import com.csvreader.CsvReader;


public class CsvFrame extends JFrame implements Serializable
{

	private static final long serialVersionUID = 2537576951291269546L;
	public static final int TEXT_ROWS = 20;
	   public static final int TEXT_COLUMNS = 40;
	   private CsvChooser dialog = null;
	   private JTextArea csvInsert;

	   public CsvFrame()
	   {
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
	      exitItem.addActionListener(new ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	               System.exit(0);
	            }
	         });
	      insertMenu.add(exitItem);

	      csvInsert = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
	      add(new JScrollPane(csvInsert), BorderLayout.CENTER);
	      pack();
	   }

	   /**
	    * Akcja Connect wyświetla okno dialogowe z polem hasła
	    */

	   private class ConnectAction implements ActionListener
	   {
	      public void actionPerformed(ActionEvent event)
	      {
	         // Jeśli jest to pierwszy raz, tworzy okno dialogowe

	         if (dialog == null) dialog = new CsvChooser();

	         // Ustawianie wartości domyślnych
	         dialog.setUser(new Csv_File("Nazwa Pliku", ";"));

	         // Wyświetlenie okna dialogowego
	         if (dialog.showDialog(CsvFrame.this, "Connect"))
	         {
	            // Pobranie danych użytkownika w przypadku zatwierdzenia
	        	 try {
	     			String a = Csv_File.getSeparator();
	     			char b = a.charAt(0);
	     			CsvReader tabela = new CsvReader(Csv_File.getScvFilePath(), b);
	     					
	     			tabela.readHeaders();

	     			while (tabela.readRecord())
	     			{
	     				String productID = tabela.get("Nazwa");
	     				String productName = tabela.get("Ulica");
	     				String supplierID = tabela.get("E-mail");
	     				String categoryID = tabela.get("Strona WWW");
	     				String quantityPerUnit = tabela.get("Telefon");
	     				String unitPrice = tabela.get("Nip");
	     								
	     				// perform program logic here
	     				csvInsert.append(productID+";"+productName+"\r\n");
	     			}
	     	
	     			tabela.close();
	     			
	     		} catch (FileNotFoundException e) {
	     			e.printStackTrace();
	     		} catch (IOException e) {
	     			e.printStackTrace();
	     		}
	        	 Csv_File u = dialog.getCsvFile();
	            csvInsert.append("nazwa użytkownika = " + u.getNazwa() + ", hasło = "
	                  + (new String(u.getSeparator())) + "\r\n");
	         }
	      }
	   }
	}