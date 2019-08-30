package metric;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocClass {
	public static void main(String[] args) {
		String fileLocation = "C:\\Users\\Pichau\\Desktop\\metrics-evolucao\\src\\main\\java\\metric\\LocClass.java";
		System.out.println("Linhas de codigo: " + countLines(fileLocation));
		System.out.println("Número de Classes: " + countClass(fileLocation));
		System.out.println("Número de Metodos: " + countMethods(fileLocation));
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

			while (br.ready()) {
				String linha = br.readLine();

				if (linha.matches(regex)) {
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