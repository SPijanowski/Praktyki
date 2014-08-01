package csv_writer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;



public class CsvReaderExample {

	public static void main(String[] args) throws FileNotFoundException {
	/**	try {
			String a = ";";
			char b = a.charAt(0);
			CsvReader tabela = new CsvReader("firmy.csv", b);
					
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
				System.out.println(productID+";"+productName);
			}
	
			tabela.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	*/	BufferedReader CSVFile = 
		        new BufferedReader(new FileReader("firmy.csv"));

		  String dataRow = null;
		try {
			dataRow = CSVFile.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		  while (dataRow != null){
		   String[] dataArray = dataRow.split(",");
		   for (String item:dataArray) { 
		      System.out.print(item + "\t"); 
		   }
		   System.out.println(); // Print the data line.
		   try {
			dataRow = CSVFile.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Read next line of data.
		  }
		  // Close the file once all data has been read.
		  try {
			CSVFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  // End the printout with a blank line.

		
	}
	
}
