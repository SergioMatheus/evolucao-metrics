package metric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocClass {

	private static String csvFile = System.getProperty("user.dir") + "/metrics-evolucao.csv";
	private static List<String> meses = new ArrayList<String>();
	private static List<String> loc = new ArrayList<String>();
	private static List<String> classes = new ArrayList<String>();
	private static List<String> metodos = new ArrayList<String>();
	private static int somaLinhasCodigo = 0;
	private static int somaClasses = 0;
	private static int somaMethods = 0;

	public static void main(String[] args) {
		File pasta = new File("C:\\Users\\Pichau\\Desktop\\Dataset");

		navegatorPasta(pasta);

		// mountCsvMês(pasta);
		// mountCsvLoc(pasta);
	}

	private static void printConsole(int somaLinhasCodigo, int somaClasses, int somaMethods) {
		System.out.println("___________________________________________________");
		System.out.println("Linhas de codigo: " + somaLinhasCodigo);
		System.out.println("Número de Classes: " + somaClasses);
		System.out.println("Número de Metodos: " + somaMethods);
	}

	private static void navegatorPasta(File pasta) {
		int somaLinhasCodigo = 0;
		int somaClasses = 0;
		int somaMethods = 0;
		File[] listaArquivos = pasta.listFiles();

		for (int i = 0; i < listaArquivos.length; i++) {
			if (listaArquivos[i].isDirectory()) {
				navegatorPasta(listaArquivos[i]);
			} else {
				String fileLocation = listaArquivos[i].getPath();
				somaLinhasCodigo += countLines(fileLocation);
				somaClasses += countClass(fileLocation);
				somaMethods += countMethods(fileLocation);

				if (!meses.contains(listaArquivos[i].getParentFile().getName())) {
					meses.add(listaArquivos[i].getParentFile().getName());
				}

				if (i+1 == listaArquivos.length){
					loc.add(Integer.toString(somaLinhasCodigo));
					classes.add(Integer.toString(somaClasses));
					metodos.add(Integer.toString(somaMethods));
				}

				System.out.println("Pasta do arquivo: " + listaArquivos[i].getParentFile().getName());
				printConsole(somaLinhasCodigo, somaClasses, somaMethods);
				try {
					mountCsv(meses, loc, classes, metodos);
				} catch (IOException e) {
					e.printStackTrace();
				}

//				printConsole(countLines(fileLocation), countClass(fileLocation), countMethods(fileLocation));
			}
		}
	}

	private static void mountCsv(List<String> meses, List<String> loc, List<String> classes, List<String> metodos)
			throws IOException {

		FileWriter writer = new FileWriter(csvFile);
		writer.append("MÊS");
		writer.append(" ");
		writer.append("\n");
		CSVUtil.writeLine(writer, meses);
		writer.append("LOC");
		writer.append(" ");
		writer.append("\n");
		CSVUtil.writeLine(writer, loc);
		writer.append("CLASSES");
		writer.append(" ");
		writer.append("\n");
		CSVUtil.writeLine(writer, classes);
		writer.append("MÉTODOS");
		writer.append(" ");
		writer.append("\n");
		CSVUtil.writeLine(writer, metodos);
		// for (String rowData : dados) {
		// writer.append(rowData);
		// writer.append("\n");
		// writer.flush();
		// }
		writer.close();

	}

	public static int countLines(String fileLocation) {
		int LinesCount = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileLocation));
			String regexLinha = ".*(\\S)";
			while (br.ready()) {
				String linha = br.readLine();

				if (linha.matches(regexLinha)) {
					LinesCount++;
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return LinesCount;
	}

	private static int countClass(String fileLocation) {
		int classCount = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileLocation));
			final String regex = "(public|private|protected).*(class).*(\\()*(\\{)";
			Pattern p = Pattern.compile(regex);
			while (br.ready()) {
				String linha = br.readLine();
				Matcher m = p.matcher(linha);
				if (m.find()) {
					classCount++;
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return classCount;
	}

	private static int countMethods(String fileLocation) {
		int methodCount = 0;
		final String regexMethod = "(public|private|protected).*(static|void|String|int|long|float|boolean|double|char|Bitmap|BigDecimal|Double|Long|Float).*(\\()*(\\{)";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileLocation));
			Pattern p = Pattern.compile(regexMethod);
			while (br.ready()) {
				String linhaAtual;
				linhaAtual = br.readLine();

				Matcher m = p.matcher(linhaAtual);
				if (m.find()) {
					methodCount++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return methodCount;

	}
}