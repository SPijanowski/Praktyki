package csv_reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		csvFileName = name;
		separator = separation;
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
			dataArray = dataRow.split(",");
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

	public static String getCsvFileName() {
		return csvFileName;
	}

	public static void setCsvFileName(String csvFileName) {
		Csv_File.csvFileName = csvFileName;
	}

	public static String csvFileName(String path) {
		StringBuilder b = new StringBuilder(path);
		int c = b.lastIndexOf("\\");
		String d = b.substring(c + 1);
		return d;
	}

	public static String[] removeNull(String[] a) {
		ArrayList<String> removed = new ArrayList<String>();
		for (String str : a)
			if (str != null)
				removed.add(str);
		return removed.toArray(new String[0]);
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

}
