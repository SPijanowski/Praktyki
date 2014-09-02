package csv_reader;

import javax.swing.*;
import javax.swing.table.TableColumn;


public class LineNumberTable extends JTable {

	private static final long serialVersionUID = -2347482843961067504L;
	private JTable mainTable;
 
    public LineNumberTable( JTable table ) {
        super();
        mainTable = table;
        setAutoCreateColumnsFromModel( false );
        setModel( mainTable.getModel() );
        setSelectionModel( mainTable.getSelectionModel() );
        setAutoscrolls( false );
 
        addColumn( new TableColumn() );
        getColumnModel().getColumn( 0 ).setCellRenderer( mainTable.getTableHeader().getDefaultRenderer() );
        getColumnModel().getColumn( 0 ).setPreferredWidth( 60 );
        setPreferredScrollableViewportSize( getPreferredSize() );
    }
 
    @Override
    public boolean isCellEditable( int row, int column ) {
        return false;
    }
 
    @Override
    public Object getValueAt( int row, int column ) {
        return new Integer( row + 1 );
    }
 
    @Override
    public int getRowHeight( int row ) {
        return mainTable.getRowHeight();
    }
}