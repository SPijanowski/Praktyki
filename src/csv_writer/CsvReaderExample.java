package csv_writer;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.csvreader.CsvReader;



public class CsvReaderExample {

	public static void main(String[] args) {
		try {
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
		
	}

}
