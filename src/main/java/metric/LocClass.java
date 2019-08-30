package metric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocClass {
	private static int somaLinhasCodigo = 0;
	private static int somaClasses = 0;
	private static int somaMethods = 0;
	
	public static void main(String[] args) {
		File pasta = new File("C:\\Users\\Pichau\\Desktop\\Dataset");
		navegatorPasta(pasta);
		
//		String fileLocation = "C:\\Users\\Pichau\\Desktop\\metrics-evolucao\\src\\main\\java\\metric\\LocClass.java";
		System.out.println("-------------------------------------Somatorio total do projeto -------------------------------------");
		System.out.println("Linhas de codigo: " + somaLinhasCodigo);
		System.out.println("Número de Classes: " + somaClasses);
		System.out.println("Número de Metodos: " + somaMethods);
	}

	private static void navegatorPasta(File pasta) {
		File[] listaArquivos = pasta.listFiles();
		for (int i = 0; i < listaArquivos.length; i++) {
			if (listaArquivos[i].isDirectory()) {
				navegatorPasta(listaArquivos[i]);
			} else{
				String fileLocation = listaArquivos[i].getPath();
				somaLinhasCodigo += countLines(fileLocation);
				somaClasses += countClass(fileLocation);
				somaMethods += countMethods(fileLocation);
				System.out.println("Diretório do arquivo: " + listaArquivos[i].getPath());
				System.out.println("Linhas de codigo: " + countLines(fileLocation));
				System.out.println("Número de Classes: " + countClass(fileLocation));
				System.out.println("Número de Metodos: " + countMethods(fileLocation));
			}
		}
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
		Pattern p = Pattern.compile(regexMethod);
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileLocation));
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