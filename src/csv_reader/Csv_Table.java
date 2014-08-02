package csv_reader;

import javax.swing.table.AbstractTableModel;

public class Csv_Table extends AbstractTableModel{
	private static final StringBuilder csvInsert = null;
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
		
	@Override
	public int getColumnCount() {
		
		return maxColum - minColum;
	}
	@Override
	public int getRowCount() {
		
		return row;
	}
	@Override
	public Object getValueAt(int r, int c) {
		String[] a = CsvChooser.getDataArray();
		StringBuilder b = new StringBuilder();
		for(Object nowy : a)
			return b.append(nowy);
		
		return b;
	}	
}

