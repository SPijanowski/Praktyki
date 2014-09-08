package csv_reader;

import java.awt.EventQueue;

import javax.swing.JComboBox;

public class ComboBoxChanger implements Runnable {
	
	public ComboBoxChanger(JComboBox aCombo, String[] sd)
	{
		combo = aCombo;
		table = sd;
	}
	
	public void run()
	{
		try 
		{
			
				EventQueue.invokeLater(new Runnable()
				{
				public void run()
				{
						
						combo.removeAllItems();
						for(String i:table)
							combo.addItem(i);
				}
				});
				Thread.sleep(1);
			
		}
		catch (InterruptedException e)
		{}
	}
	
	private JComboBox combo;
	private String[] table;
}
