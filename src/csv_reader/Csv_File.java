package csv_reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Program umożliwijący czytanie plików CSV
 * @author Sylwester Pijanowski
 * @version 1.20
 * @date 04.08.2014
 */

class Csv_File
{
   private String nazwa;
   private static String separator = ";";
   private static String csvFilePath;

   public Csv_File(String n, String s)
   {
      nazwa = n;
      separator = s;
   }
   public Csv_File(String f)
   {
	  csvFilePath = f;
   }
   public String getNazwa()
   {
      return nazwa;
   }

   public static String getSeparator()
   {
      return separator;
   }
   public static String getScvFilePath()
   {
	   return csvFilePath;
   }
   public void setNazwa(String n)
   {
      nazwa = n;
   }

   public static void setSeparator(String s)
   {
      separator = s;
   }
   public static void setCsvFilePath(String f)
   {
	   csvFilePath = f;
   }
   public static void separation(String path) {
		String dataArray[];
		String a = null;
		StringBuilder budowa = new StringBuilder();
		BufferedReader CSVFile = null;
		try {
			CSVFile = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e1) {
		}
		String dataRow = "";
		try {
			dataRow = CSVFile.readLine();
		} catch (IOException e) {
		}
		while (dataRow != null) {
			dataArray = dataRow.split(",");
			for (String item : dataArray) {
				budowa.append(item);
			}
			try {
				dataRow = CSVFile.readLine();

			} catch (IOException e) {}
		}
		try {
			CSVFile.close();
		} catch (IOException e) {
		}
		double spr = budowa.indexOf(";");
		if (spr == -1) {
			a = ",";
		} else {
			a = ";";
		}
		setSeparator(a);
	}
}
