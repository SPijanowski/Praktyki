package csv_reader;

/**
 * Program umożliwijący czytanie plików CSV
 * @author Sylwester Pijanowski
 * @version 1.00
 * @date 01.08.2014
 */
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Csv_Reader {
	 public static void main(String[] args)
	   {
	      EventQueue.invokeLater(new Runnable()
	         {
	            public void run()
	            {
	               JFrame frame = new CsvFrame();
	               frame.setTitle("Csv_Reader version 1.00");      
	               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	               frame.setVisible(true);
	            }
	         });
	   }

}
