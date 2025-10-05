package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class FileManager {		
	private static ArrayList<String> temporalLogs = new ArrayList<>();
	
	/**
	 * Lee un archivo csv y devuelve una lista con cada linea
	 * @param csvFile
	 * @return linesCsv
	 */
	private ArrayList<String> readAllCsv(File csvFile) {
		ArrayList<String> linesCsv = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(new FileReader(csvFile))){
			String line;
			while((line = reader.readLine()) != null) {
				linesCsv.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return linesCsv;
	}
	
	/**
	 * Lee un archivo binario y devuelve una lista con cada linea
	 * @param binaryFile
	 * @return linesBinary
	 */
	private ArrayList<String> readAllBinary(File binaryFile) {
		ArrayList<String> linesBinary = new ArrayList<>();
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(binaryFile));){
            while (true) {
                try {
                	linesBinary.add(String.valueOf(reader.readObject()));
                }
                catch (EOFException  e) {
                    break;
                }
            }
        }
        catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
		return linesBinary;
	}
	
	/**
	 * Crea un archivo con formato .dat o.csv
	 * @param file
	 * @param isOrdered
	 * @param extension
	 * @return newFile
	 */
	private File createFile(File file, boolean isOrdered, String extension) {
		String fileName;
		if (isOrdered){
			fileName = "src\\resources\\"+ file.getName().substring(0, file.getName().lastIndexOf(".")) +"_ord"+ extension;
		}
		else {
			fileName = "src\\resources\\"+ file.getName().substring(0, file.getName().lastIndexOf(".")) + extension;
		}
		File newFile = new File(fileName);
		try {
			if(newFile.createNewFile()) {
				return newFile;
			}
			else {
				JOptionPane.showMessageDialog(null, "El archivo \""+ fileName +"\" ya existe", "Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Convierte un archivo de tipo csv en un archivo de tipo dat
	 * @param csvFile
	 */
	public void csvToBinary(File csvFile) {
		File binaryFile = createFile(csvFile, false, ".dat");
		if(binaryFile != null) {
			binaryWriter(binaryFile, readAllCsv(csvFile), null);
			registerLog("csvToBinary", csvFile.getName(), binaryFile.getName());
		}
	}
	
	/**
	 * Convierte un archivo de tipo dat en un archivo de tipo csv
	 * @param binaryFile
	 */
	public void binaryToCsv(File binaryFile) {
		File csvFile = createFile(binaryFile, false, ".csv");
		if(csvFile != null) {
			csvWriter(csvFile, readAllBinary(binaryFile), null);
			registerLog("binaryToCsv", binaryFile.getName(), csvFile.getName());
		}
	}

	/**
	 * Escribe en un archivo binario
	 * @param binaryFile
	 * @param lines
	 * @param header
	 */
	private void binaryWriter(File binaryFile, ArrayList<String> lines, String header) {
		try(ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(binaryFile))) {
			if (header != null) {
				writer.writeObject(header);
			}
			for(String line : lines) {
				writer.writeObject(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escribe en un archivo csv
	 * @param csvFile
	 * @param lines
	 * @param header
	 */
	private void csvWriter(File csvFile, ArrayList<String> lines, String header) {
			try(BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
				if (header != null) {
					writer.write(header+"\n");
				}
				for(String line : lines) {
					writer.write(line+"\n");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Ordena un archivo csv creando otro fichero csv ordenado
	 * @param csvFile
	 */
	public void orderCsv(File csvFile) {
		File orederedCsvFile = createFile(csvFile, true, ".csv");
		if(orederedCsvFile != null) {
			ArrayList<String> ordenado = readAllCsv(csvFile);
			String cabecera = ordenado.get(0);
			ordenado.remove(0);
			Collections.sort(ordenado);
			csvWriter(orederedCsvFile, ordenado, cabecera);
			registerLog("orderCsv", csvFile.getName(), orederedCsvFile.getName());
		}
	}
	
	/**
	 * Ordena un archivo binario creando otro fichero binario ordenado
	 * @param binaryFile
	 */
	public void orderBinary(File binaryFile) {
		File orederedBinaryFile = createFile(binaryFile, true, ".dat");
		if(orederedBinaryFile != null) {
			ArrayList<String> ordenado = readAllBinary(binaryFile);
			String cabecera = ordenado.get(0);
			ordenado.remove(0);
			Collections.sort(ordenado);
			binaryWriter(orederedBinaryFile, ordenado, cabecera);
			registerLog("orderBinary", binaryFile.getName(), orederedBinaryFile.getName());
		}
	}

	/**
	 * Ordena un archivo binario creando un fichero csv ordenado
	 * @param binaryFile
	 */
	public void orderBinaryToCsv(File binaryFile) {
		File orederedCsvFile = createFile(binaryFile, true, ".csv");
		if(orederedCsvFile != null) {
			ArrayList<String> ordenado = readAllBinary(binaryFile);
			String cabecera = ordenado.get(0);
			ordenado.remove(0);
			Collections.sort(ordenado);
			csvWriter(orederedCsvFile, ordenado, cabecera);
			registerLog("orderBinaryToCsv", binaryFile.getName(), orederedCsvFile.getName());
		}
	}
	
	/**
	 * Escribe un registro de los métodos usados, fecha y hora de ejecución, fichero de entrada y fichero de salida.
	 * Guarda en una lista el log de la sesión
	 * @param usedMethod
	 * @param inFile
	 * @param outFile
	 */
	private void registerLog(String usedMethod, String inFile, String outFile) {
		String logLine = usedMethod + ",[" + getDateTime() + "]," + inFile + "," + outFile+"\n";
		try(BufferedWriter writer = new BufferedWriter(new FileWriter("log_csv.csv", true))) {
			writer.write(logLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 temporalLogs.add(logLine);
	}
	
	/**
	 * Devuelve la fecha y hora actual
	 * @return
	 */
	private String getDateTime() {
	    LocalDateTime executionDateTime = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	    return executionDateTime.format(formatter);
	}
	
	/**
	 * Muestra los logs de la sesión
	 * @param textAreaLog
	 */
	public void showExecutedLine(JTextArea textAreaLog) {
		StringBuilder builder = new StringBuilder();
		builder.append("MétodoUsado,FechaYhora,ArchivoDeEntrada,ArchivoDeSalida"+"\n");
	    for (String line : temporalLogs) {
	        builder.append(line);
	    }
	    textAreaLog.setText(builder.toString());
	}
	
	/**
	 * Muestra el archivo log_csv completo
	 * @param textAreaLog
	 */
	public void showCompleteLogInTxtArea(JTextArea textAreaLog) {
		File logCsv = new File("log_csv.csv");		
	    try {
	        StringBuilder builder = new StringBuilder();
	        for (String line : readAllCsv(logCsv)) {
	            builder.append(line).append("\n");
	        }
	        textAreaLog.setText(builder.toString());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
