package csv_writer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import com.csvreader.CsvReader;

;

public class ProstaTabela {
	public static void main(String[] args) {
		Tabelka t = new Tabelka();
		t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t.setVisible(true);
	}
}

class Tabelka extends JFrame {

	public Tabelka() {
		setTitle("Prosta tabelka");
		setSize(400, 150);

		JPanel southPanel = new JPanel();
		JButton insertButton = new JButton("Wstaw");
		southPanel.add(insertButton);
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					String a = ";";
					char b = a.charAt(0);
					CsvReader products = new CsvReader("firmy.csv", b);

					products.readHeaders();

					while (products.readRecord()) {
						String productID = products.get("Nazwa");
						String productName = products.get("Ulica");

						Object[][] planety = { { productID, productName } };

						products.close();
						String[] kolumny = { "Nazwa", "Ulica" };
						JTable tabela = new JTable(planety, kolumny);
						JScrollPane pane = new JScrollPane(tabela);
						add(pane);
						
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});	
		add(southPanel,BorderLayout.SOUTH);
	}
}
