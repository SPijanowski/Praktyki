package csv_reader;
/**
 * Program umożliwijący czytanie plików CSV
 * @author Sylwester Pijanowski
 * @version 1.20
 * @date 04.08.2014
 */
public class Csv_Table {
	public static int row;
	public int minColum;
	public int maxColum;
	
	public Csv_Table(int r, int minC, int maxC){
		row = r;
		minColum = minC;
		maxColum = maxC;
		
	}
		
	public static int getRow(){
		return row;
	}
	public static void setRow(int r){
		row = r;
	}

}

