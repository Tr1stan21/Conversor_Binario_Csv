package services;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Service {

	private File fileSelected;
	
	public File getFileSelected() {
		return fileSelected;
	}

	/**
	 * Abre un desplegable que permite seleccionar un archivo del sistema
	 */
	public boolean chooseFile() {
		JFileChooser fileChooser = new JFileChooser("C:\\");
        int option = fileChooser.showOpenDialog(null);

        if (option == JFileChooser.APPROVE_OPTION) {
        	fileSelected = fileChooser.getSelectedFile();
        	return true;
        }
        else {
        	return false;
        }
	}
	
	/**
	 * Devuelve el formato del archivo en un String
	 * @return format
	 */
	public String getFormat() {
		return fileSelected.getName().substring(fileSelected.getName().lastIndexOf(".")+1, fileSelected.getName().length());
	}
	
	/**
	 * Muestra los botones usables según el formato del archivo
	 * @param btnCsvToBinario
	 * @param btnBinarioToCsv
	 * @param btnOrdenarCsv
	 * @param btnOrdenarBinario
	 * @param btnBinarioToCsvOrdenado
	 */
	public void showButtons(JButton btnCsvToBinario, JButton btnBinarioToCsv, JButton btnOrdenarCsv, JButton btnOrdenarBinario, JButton btnBinarioToCsvOrdenado) {
		if (fileSelected != null) {
            btnCsvToBinario.setEnabled(false);
            btnBinarioToCsv.setEnabled(false);
            btnOrdenarCsv.setEnabled(false);
            btnOrdenarBinario.setEnabled(false);
            btnBinarioToCsvOrdenado.setEnabled(false);

            switch (getFormat()) {
                case "csv":
                    btnCsvToBinario.setEnabled(true);
                    btnOrdenarCsv.setEnabled(true);
                    break;
                case "dat":
                    btnBinarioToCsv.setEnabled(true);
                    btnOrdenarBinario.setEnabled(true);
                    btnBinarioToCsvOrdenado.setEnabled(true);
                    break;
                case null:
                	break;
                default:
    				JOptionPane.showMessageDialog(null, "Formato del archivo no válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
		}
	}

}
