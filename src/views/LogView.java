package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.FileManager;

import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;

public class LogView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public LogView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 720, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textAreaLog = new JTextArea();
		textAreaLog.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane.setViewportView(textAreaLog);
		textAreaLog.setEditable(false);
		
		JLabel lblLog = new JLabel("Log completo:");
		lblLog.setFont(new Font("Verdana", Font.BOLD, 16));
		contentPane.add(lblLog, BorderLayout.NORTH);
		
		FileManager manager = new FileManager();
		manager.showCompleteLogInTxtArea(textAreaLog);
	}
}
