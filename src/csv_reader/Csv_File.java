package csv_reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.csvreader.CsvReader;

/**
 * Program umożliwijący czytanie plików CSV
 * 
 * @author Sylwester Pijanowski
 * @version 1.20
 * @date 04.08.2014
 */

class Csv_File {

	private static String separator;
	private static String csvFilePath;
	private static String csvFileName;

	public Csv_File(String name, String separation, String path) {
		csvFilePath = path;
	}

	public Csv_File(String f) {
		csvFilePath = f;
	}

	public String getNazwa() {
		return csvFileName;
	}

	public static String getSeparator() {
		return separator;
	}

	public static String getScvFilePath() {
		return csvFilePath;
	}

	public static void setSeparator(String s) {
		separator = s;
	}

	public static void setCsvFilePath(String f) {
		csvFilePath = f;
	}
	
	public static String getCsvFileName() {
		return csvFileName;
	}

	public static void setCsvFileName(String csvFileName) {
		Csv_File.csvFileName = csvFileName;
	}

	public static String separation(String path) {
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
			dataArray = dataRow.split("");
			for (String item : dataArray) {
				budowa.append(item);
			}
			try {
				dataRow = CSVFile.readLine();

			} catch (IOException e) {
			}
		}
		try {
			CSVFile.close();
		} catch (IOException e) {
		}
		double spr = budowa.indexOf(";");
		double spr1 = budowa.indexOf(",");
		double spr2 = budowa.indexOf("\"");
		if (spr == -1) {
			if (spr1 == -1) {
				if (spr2 == -1) {
					{
						a = null;
					}
				} else {
					a = "\"";
				}
			} else {
				a = ",";
			}
		} else {
			a = ";";
		}
		return a;
	}

	public static String csvFileName(String path) {
		StringBuilder b = new StringBuilder(path);
		int c = b.lastIndexOf("\\");
		String d = b.substring(c + 1);
		return d;
	}

	public static String[] removeEmptyField(String[] empty) {
		String[] a = {};
		List<String> list = new ArrayList<String>();
		for (String s : empty) {
			if (s != null && s.length() > 0) {
				list.add(s);
			}
		}
		a = list.toArray(new String[list.size()]);
		return a;
	}
	
	public static String[][] readData(String path, String[] firstRow){
		String l = null;
		int danetablica; 
		if(firstRow != null){ danetablica = firstRow.length;}else{danetablica = 1; }
		int line_numberFirstFile = countRow(path);
		int line_number = 0;
	
		String[][] tablicaDanych = new String[line_numberFirstFile][danetablica];
		try {
			String a = Csv_File.separation(path);
			CsvReader tabela;
			if(a!=null){
			char b = a.charAt(0);
			tabela = new CsvReader(path, b);}
			else{tabela = new CsvReader(path);}
			tabela.readHeaders();
			while (tabela.readRecord()) {
				for (int i = 0; i < danetablica; i++) {
					l = tabela.get(firstRow[i]);
					tablicaDanych[line_number][i] = (l);
				}
				++line_number;
			}
			tabela.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tablicaDanych;		
	}
	
	public static int countRow(String path)
	{	int countRow = 0;
		try {
			String a = Csv_File.separation(path);
			CsvReader tabela;
			if(a!=null){
			char b = a.charAt(0);
			tabela = new CsvReader(path, b);}
			else{tabela = new CsvReader(path);}
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
		return countRow;
	}
	

	@SuppressWarnings("resource")
	public static String[] firstRow(String csvFilePath){
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
		if(Csv_File.separation(csvFilePath)!= null)
		{String fileRow[] = dataRow.split(Csv_File.separation(csvFilePath)); return fileRow;}
		else {String fileRow[] = {dataRow}; return fileRow;}
	}
	
	public static String[][] removeDoubleData(String[][] tablicaDanych){
		Arrays.sort(tablicaDanych, new Comparator<String[]>() {
			@Override
			public int compare(final String[] entry1, final String[] entry2) {
				final String time1 = entry1[0];
				final String time2 = entry2[0];
				return time1.compareTo(time2);
			}
		});

		int petle = 0;
		int inne = 0;
		int pow = 0;
		
		while (petle < tablicaDanych.length - 1) {
			if (tablicaDanych[petle][0].equals(tablicaDanych[petle + 1][0])) {
				pow++;
			} else {
				inne++;
			}
			petle++;
		}
		String[][] niepowtarzalne = new String[inne][tablicaDanych[0].length];
		String[][] powtarjace = new String[pow][tablicaDanych[0].length];
		petle = 0;
		int nowaDana = 0;
		int powDana = 0;
		while (petle < tablicaDanych.length - 1) {
			if (tablicaDanych[petle][0].equals(tablicaDanych[petle + 1][0])) {
				for (int i = 0; i < tablicaDanych[petle].length; i++) {
					powtarjace[nowaDana][i] = tablicaDanych[petle][i];
				}
				nowaDana++;
				petle++;
			} else {
				for (int i = 0; i < tablicaDanych[petle].length; i++) {
					niepowtarzalne[powDana][i] = tablicaDanych[petle][i];
				}
				powDana++;
				petle++;
			}
		}
		return niepowtarzalne;
	}
	
}
