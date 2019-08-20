package metric;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
public class LocClass {
	public static void main(String[] args) {
//		String fileLocation = "C:\\Users\\100914500\\Desktop\\Metricas\\src\\main\\java\\metric\\LocClass.java";
		String fileLocation = "C:\\Users\\Pichau\\Desktop\\metrics-evolucao\\src\\main\\java\\metric\\LocClass.java";
		try {
			System.out.println("Linhas de codigo: "+countLines(fileLocation));
			System.out.println("Número de Classes: "+countClass(fileLocation));
			System.out.println("Número de Metodos: "+countMethods());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static int countLines(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 1;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}
	private static int countClass(String FILE_PATH) {
		int classCount = 0;
		try {				
			String regex = "(public class|private class|protected class).*";
			BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
			while (br.ready()) {
				String linha = br.readLine();

				if(linha.matches(regex)) {
					classCount++;
				}
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return classCount;
	}
	
	private static int countMethods() {
		Class<LocClass> classe = LocClass.class;
		Method[] method = classe.getDeclaredMethods();
		return method.length;
	}
}