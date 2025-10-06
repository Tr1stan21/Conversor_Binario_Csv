package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import services.Service;
import utils.FileManager;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textRutaArchivo;
	private Service service = new Service();
	private FileManager manager = new FileManager();
	
	//Botones
    private JButton btnCsvToBinario;
    private JButton btnBinarioToCsv;
    private JButton btnOrdenarCsv;
    private JButton btnOrdenarBinario;
    private JButton btnBinarioToCsvOrdenado;
    private JButton bntShowLog;
    JTextArea textAreaLog;
    private LogView logView = new LogView();
    private String textTipoArchivo = "Formato: ";
    private JScrollPane scrollPane;

	public MainView() {
        setTitle("Conversor y Ordenador de Archivos CSV/Binario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(10, 10));
		
		//PANEL SUPERIOR
		JPanel panelSuperior = new JPanel();
		contentPane.add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setLayout(new BorderLayout(5, 5));
		
		textRutaArchivo = new JTextField();
		textRutaArchivo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textRutaArchivo.setBackground(new Color(255, 255, 255));
		textRutaArchivo.setEditable(false);
		textRutaArchivo.setPreferredSize(new Dimension(550,35));
		
		JLabel lblTipoArchivo = new JLabel(textTipoArchivo);
		lblTipoArchivo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblTipoArchivo.setPreferredSize(new Dimension(100, 35));
		panelSuperior.add(lblTipoArchivo, BorderLayout.CENTER);

        ImageIcon openFolderIcon = new ImageIcon("src\\resources\\abrirCarpeta.png");
        JButton btnFileChooser = new JButton("",openFolderIcon);
		btnFileChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(service.chooseFile()) {
					service.showButtons(btnCsvToBinario, btnBinarioToCsv, btnOrdenarCsv, btnOrdenarBinario, btnBinarioToCsvOrdenado);
					textRutaArchivo.setText(service.getFileSelected().getAbsolutePath());
					lblTipoArchivo.setText(textTipoArchivo+service.getFormat());
				}
			}
		});
		btnFileChooser.setPreferredSize(new Dimension(35, 35));
		panelSuperior.add(btnFileChooser, BorderLayout.EAST);
        ImageIcon icono = new ImageIcon("src\\resources\\abrirCarpeta.png");
        Image img = icono.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnFileChooser.setIcon(new ImageIcon(img));
        btnFileChooser.setBorderPainted(false); // Quita el borde
        btnFileChooser.setContentAreaFilled(false); // Quita el fondo



        scrollPane = new JScrollPane();
		panelSuperior.add(scrollPane, BorderLayout.WEST);
		scrollPane.setViewportView(textRutaArchivo);
		

		
		//PANEL CENTRAL
		JPanel panelCentral = new JPanel();
		contentPane.add(panelCentral, BorderLayout.CENTER);
		//(Filas, Columnas, MargenX, MargenY)
		panelCentral.setLayout(new GridLayout(3, 2, 10, 10));
		
		btnCsvToBinario = new JButton("Convertir Csv a Binario");
		btnCsvToBinario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.csvToBinary(service.getFileSelected());
				manager.showExecutedLine(textAreaLog);
			}
		});
		panelCentral.add(btnCsvToBinario);
		
		btnBinarioToCsv = new JButton("Convertir Binario a Csv");
		btnBinarioToCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.binaryToCsv(service.getFileSelected());
				manager.showExecutedLine(textAreaLog);
			}
		});
		panelCentral.add(btnBinarioToCsv);
		
		btnOrdenarCsv = new JButton("Ordenar Csv");
		btnOrdenarCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.orderCsv(service.getFileSelected());
				manager.showExecutedLine(textAreaLog);
			}
		});
		panelCentral.add(btnOrdenarCsv);
		
		btnOrdenarBinario = new JButton("Ordenar Binario");
		btnOrdenarBinario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.orderBinary(service.getFileSelected());
				manager.showExecutedLine(textAreaLog);
			}
		});
		panelCentral.add(btnOrdenarBinario);
		
		btnBinarioToCsvOrdenado = new JButton("Convertir Binario a Csv Ordenado");
		btnBinarioToCsvOrdenado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.orderBinaryToCsv(service.getFileSelected());
				manager.showExecutedLine(textAreaLog);
			}
		});
		panelCentral.add(btnBinarioToCsvOrdenado);
		
		bntShowLog = new JButton("Mostrar Log Completo");
		bntShowLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logView.setVisible(true);
			}
		});
		panelCentral.add(bntShowLog);
		
		/**
		 * Oculta los botones
		 */
        btnCsvToBinario.setEnabled(false);
        btnBinarioToCsv.setEnabled(false);
        btnOrdenarCsv.setEnabled(false);
        btnOrdenarBinario.setEnabled(false);
        btnBinarioToCsvOrdenado.setEnabled(false);

		
		//PANEL INFERIOR
		JPanel panelInferior = new JPanel();
		contentPane.add(panelInferior, BorderLayout.SOUTH);
		panelInferior.setLayout(new BorderLayout(0, 0));
		
		JLabel lblLog = new JLabel("Log de ejecución:");
		lblLog.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelInferior.add(lblLog, BorderLayout.NORTH);
		
		JScrollPane scrollLog = new JScrollPane();
		scrollLog.setPreferredSize(new Dimension(0,200));
		panelInferior.add(scrollLog, BorderLayout.SOUTH);
		
		textAreaLog = new JTextArea();
		textAreaLog.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollLog.setViewportView(textAreaLog);
		textAreaLog.setEditable(false);

		
	}
}