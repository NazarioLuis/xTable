package py.com.cs.xtable.test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import py.com.cs.xtable.component.AwesomeTable;

public class MyTestView extends JFrame {

	private static final long serialVersionUID = -853026106748477267L;
	private JPanel contentPane;
	private AwesomeTable table;

	/**
	 * Create the frame.
	 */
	public MyTestView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 574, 346);
		contentPane.add(scrollPane);
		
		table = new AwesomeTable();
		scrollPane.setViewportView(table);
	}
	
	public AwesomeTable getTable() {
		return table;
	}
}
