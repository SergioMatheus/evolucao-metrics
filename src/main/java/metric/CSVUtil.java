package metric;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CSVUtil {

	private static final char DEFAULT_SEPARATOR = '\n';

	private static String followCVSformat(String value) {

		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;

	}


	// public static void writeLine(Writer w, List<String> valuesMeses,
	// List<String> valuesLoc, List<String> valuesClasses,
	// List<String> valuesClassesGods, List<String> valuesMetodos, List<String>
	// valuesMetodosGods)
	// throws IOException {
	//
	// boolean first = true;
	// char separators = DEFAULT_SEPARATOR;
	//
	// StringBuilder sb = new StringBuilder();
	// for (int i = 0; i < valuesMeses.size(); i++) {
	//
	// // for (String value : valuesMeses) {
	// if (!first) {
	// sb.append(separators);
	// }
	// sb.append(followCVSformat(valuesMeses.get(i))).append(',');
	// sb.append(followCVSformat(valuesLoc.get(i))).append(',');
	// sb.append(followCVSformat(valuesClasses.get(i))).append(',');
	// sb.append(followCVSformat(valuesMetodos.get(i))).append(',');
	// sb.append(followCVSformat(valuesClassesGods.get(i))).append(',');
	// sb.append(followCVSformat(valuesMetodosGods.get(i))).append(',');
	//
	// first = false;
	// // }
	// }
	// sb.append(",");
	// w.append(sb.toString());
	//
	// }

	public static void writeLine(Writer w, List<Pasta> arquivo) throws IOException {

		boolean first = true;
		char separators = DEFAULT_SEPARATOR;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arquivo.size(); i++) {

			if (!first) {
				sb.append(separators);
			}
			sb.append(followCVSformat(arquivo.get(i).getMes() + ","));
			sb.append(followCVSformat(arquivo.get(i).getLoc() + ","));
			sb.append(followCVSformat(arquivo.get(i).getClasses() + ","));
			sb.append(followCVSformat(arquivo.get(i).getMethod() + ","));
			sb.append(followCVSformat(arquivo.get(i).getForWhile() + ","));
			sb.append(followCVSformat(arquivo.get(i).getIfs() + ","));
//			sb.append(followCVSformat(arquivo.get(i).getClassGod() + ""));
//			sb.append(followCVSformat(arquivo.get(i).getMethodGod() + ""));

			first = false;
		}
		w.append(sb.toString());

	}

}
