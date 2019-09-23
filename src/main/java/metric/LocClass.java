package metric;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocClass {

	private static String csvFile = System.getProperty("user.dir") + "/metrics-evolucao.csv";
	private static List<String> meses = new ArrayList<String>();
	private static List<String> loc = new ArrayList<String>();
	private static List<String> classes = new ArrayList<String>();
	private static List<String> classesGods = new ArrayList<String>();
	private static List<String> metodos = new ArrayList<String>();
	private static List<String> metodosGods = new ArrayList<String>();
	private static List<Pasta> listaCsv = new ArrayList<Pasta>();
	private static String regexLinha = ".*(\\S)";

	public static void main(String[] args) {
		File pasta = new File("C:\\Users\\Pichau\\Desktop\\Dataset");
		// File pasta = new
		// File("C:\\Users\\Pichau\\Desktop\\Dataset\\1\\FileLoader.java");

		try {
			navegatorPasta(pasta);
			// mountCsv(meses, loc, classes, classesGods, metodos, metodosGods);
			ordenarPeloMes(listaCsv);
			mountCsv(listaCsv);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void ordenarPeloMes(List<Pasta> pasta) {
		Collections.sort(pasta, new Comparator<Pasta>() {
			public int compare(Pasta objeto1, Pasta objeto2) {
				return objeto1.getMes().compareTo(objeto2.getMes());
			}
		});
	}

	private static void navegatorPasta(File pasta) throws IOException {
		int somaLinhasCodigo = 0;
		int somaClasses = 0;
		int somaMethods = 0;
		int somaMethodsGods = 0;
		int somaClassGods = 0;
		Pasta csvLinha = new Pasta();
		File[] listaArquivos = pasta.listFiles();

		for (int i = 0; i < listaArquivos.length; i++) {
			if (listaArquivos[i].isDirectory()) {
				navegatorPasta(listaArquivos[i]);
			} else {
				String fileLocation = listaArquivos[i].getPath();
				somaLinhasCodigo += countLines(fileLocation);
				somaClasses += countClass(fileLocation);
				somaMethods += countMethods(fileLocation);
				somaMethodsGods += contMetGod(listaArquivos[i]);
				somaClassGods += contClassGod(listaArquivos[i]);

				if (!meses.contains(listaArquivos[i].getParentFile().getName())) {
					meses.add(listaArquivos[i].getParentFile().getName());
					csvLinha.setMes(Integer.valueOf(listaArquivos[i].getParentFile().getName()));
				}

				if (i + 1 == listaArquivos.length) {
					loc.add(Integer.toString(somaLinhasCodigo));
					classes.add(Integer.toString(somaClasses));
					classesGods.add(Integer.toString(somaClassGods));
					metodos.add(Integer.toString(somaMethods));
					metodosGods.add(Integer.toString(somaMethodsGods));
					csvLinha.setLoc(somaLinhasCodigo);
					csvLinha.setClasses(somaClasses);
					csvLinha.setMethod(somaMethods);
					csvLinha.setClassGod(somaClassGods);
					csvLinha.setMethodGod(somaMethodsGods);
					listaCsv.add(csvLinha);
				}

				// System.out.println("Pasta do arquivo: " +
				// listaArquivos[i].getParentFile().getName());
				// printConsole(somaLinhasCodigo, somaClasses, somaClassGods,
				// somaMethods, somaMethodsGods);

			}
		}
	}

	// private static void mountCsv(List<String> meses, List<String> loc,
	// List<String> classes, List<String> classesGods,
	// List<String> metodos, List<String> metodosGods) throws IOException {
	//
	// FileWriter writer = new FileWriter(csvFile);
	// writer.append("MÊS,LOC,CLASSES,MÉTODOS,CLASSES DEUS,MÉTODOS DEUS\n");
	// CSVUtil.writeLine(writer, meses, loc, classes, classesGods, metodos,
	// metodosGods);
	// writer.close();
	//
	// }

	private static void mountCsv(List<Pasta> file) throws IOException {

		FileWriter writer = new FileWriter(csvFile);
		writer.append("MÊS,LOC,CLASSES,MÉTODOS,CLASSES DEUS,MÉTODOS DEUS\n");
		CSVUtil.writeLine(writer, file);
		writer.close();

	}

	public static boolean comment(String line) {
		line = line.trim();
		return line.startsWith("//") || line.startsWith("/*") || line.startsWith("/**");
	}

	public static int countLines(String fileLocation) {
		int LinesCount = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileLocation));
			while (br.ready()) {
				String linha = br.readLine();

				if (!comment(linha) && linha.matches(regexLinha)) {
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
			// final String regex = "class \\w+";
			Pattern p = Pattern.compile(regex);
			while (br.ready()) {
				String linha = br.readLine();
				Matcher m = p.matcher(linha);
				if (!comment(linha) && m.find()) {
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
		// final String regexMethod =
		// "(public|private|protected).*(static|void|String|int|long|float|boolean|double|char|Bitmap|BigDecimal|Double|Long|Float).*(\\()*(\\{)";
		final String regexMethod = "\\w+ \\w+\\(.*?\\)";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileLocation));
			Pattern p = Pattern.compile(regexMethod);
			while (br.ready()) {
				String linhaAtual;
				linhaAtual = br.readLine();

				Matcher m = p.matcher(linhaAtual);
				if (!comment(linhaAtual) && m.find() && !linhaAtual.contains("new ")
						&& !linhaAtual.contains("return")) {
					// if (!comment(linhaAtual) && m.find()) {
					methodCount++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return methodCount;

	}

	public static int contClassGod(File file) throws IOException {
		int contClassGod = 0;
		List<Contador> contadores = new ArrayList<Contador>();
		Contador contador = null;

		File arquivoLeitura = file;
		FileInputStream fs = new FileInputStream(arquivoLeitura);
		new DataInputStream(fs);

		FileReader arqv = new FileReader(arquivoLeitura);
		BufferedReader leitura = new BufferedReader(arqv);

		String line = leitura.readLine();

		while (line != null) {

			if (!contadores.isEmpty() || contadores != null) {
				for (Contador cont : contadores) {

					if (cont.getCont() != 0) {
						cont.setLinha(cont.getLinha() + 1);
					}
				}

				if (line.equals("")) {
					for (Contador cont : contadores) {

						if (cont.getCont() != 0) {
							cont.setLinha(cont.getLinha() - 1);
						}
					}
				}
			}

			if (line.contains("{")) {

				if (!contadores.isEmpty() || contadores != null) {
					for (Contador cont : contadores) {

						if (cont.getCont() != 0) {
							cont.setCont(cont.getCont() + 1);
						}
					}
				}

				if (line.contains("public") && line.contains("class") && !line.contains("/*")
						|| line.contains("private") && line.contains("class") && !line.contains("/*")
						|| line.contains("protected") && line.contains("class") && !line.contains("/*")) {
					contador = new Contador();
					contador.setCont(contador.getCont() + 1);
					contadores.add(contador);
				}
			}

			if (line.contains("}")) {
				if (!contadores.isEmpty() || contadores != null) {
					for (Contador cont : contadores) {

						if (cont.getCont() != 0) {
							cont.setCont(cont.getCont() - 1);
						}
					}

				}
			}

			if (!contadores.isEmpty() || contadores != null) {
				for (Contador cont : contadores) {
					if (cont.getCont() == 0) {
						if (cont.getLinha() > 800) {
							contClassGod++;
							cont.setLinha(0);
						} else {
							cont.setLinha(0);
						}
					}
				}

			}
			line = leitura.readLine();
		}

		return contClassGod;
	}

	public static int contMetGod(File file) throws IOException {
		int contMetGod = 0;
		List<Contador> contadores = new ArrayList<Contador>();
		Contador contador = null;

		File arquivoLeitura = file;
		FileInputStream fs = new FileInputStream(arquivoLeitura);
		new DataInputStream(fs);

		FileReader arqv = new FileReader(arquivoLeitura);
		BufferedReader leitura = new BufferedReader(arqv);

		String line = leitura.readLine();
		while (line != null) {

			if (!contadores.isEmpty() || contadores != null) {
				for (Contador cont : contadores) {
					if (cont.getCont() != 0) {
						cont.setLinha(cont.getLinha() + 1);
					}
				}

				if (line.equals("")) {

					for (Contador cont : contadores) {
						if (cont.getCont() != 0) {
							cont.setLinha(cont.getLinha() - 1);
						}
					}
				}
			}

			if (line.contains("{")) {

				if (!contadores.isEmpty() || contadores != null) {
					for (Contador cont : contadores) {
						if (cont.getCont() != 0) {
							cont.setCont(cont.getCont() + 1);
						}
					}
				}

				if (line.contains("public") && line.contains("(") && line.contains("{") && !line.contains("/*")
						|| line.contains("private") && line.contains("{") && line.contains("(") && !line.contains("/*")
						|| line.contains("protected") && line.contains("{") && line.contains("(")
								&& !line.contains("/*")) {
					contador = new Contador();
					contador.setCont(contador.getCont() + 1);
					contadores.add(contador);

				}

			}
			if (line.contains("}")) {

				if (!contadores.isEmpty() || contadores != null) {
					for (Contador cont : contadores) {
						if (cont.getCont() != 0) {
							cont.setCont(cont.getCont() - 1);
						}
					}
				}
			}

			if (!contadores.isEmpty() || contadores != null) {
				for (Contador cont : contadores) {
					if (cont.getCont() == 0) {
						if (cont.getLinha() > 127) {
							contMetGod++;
							cont.setLinha(0);
						} else {
							cont.setLinha(0);
						}
					}
				}
			}

			line = leitura.readLine();

		}
		return contMetGod;
	}
}
