package csv_reader;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class ColorCellRenderer extends DefaultTableCellRenderer {
 
    @Override
    public Component getTableCellRendererComponent( JTable table, Object val, boolean selected, boolean focused, int row, int col ) {
        Component comp = super.getTableCellRendererComponent( table, val, selected, focused, row, col );
        if( selected == false ) {
            if( ( row % 2 ) == 1 ) {
                comp.setBackground( new Color(176, 196, 222) );
            }
            else {
                comp.setBackground( new Color(255, 250, 250) );
            }
        }
        return comp;
    }
}