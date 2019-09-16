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

	public static void writeLine(Writer w, List<String> valuesMeses,List<String> valuesLoc,List<String> valuesClasses,List<String> valuesMetodos) throws IOException {

		boolean first = true;
		char separators = DEFAULT_SEPARATOR;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < valuesMeses.size() ; i++) {

//		for (String value : valuesMeses) {
			if (!first) {
				sb.append(separators);
			}
				sb.append(followCVSformat(valuesMeses.get(i))).append(',');
				sb.append(followCVSformat(valuesLoc.get(i))).append(',');
				sb.append(followCVSformat(valuesClasses.get(i))).append(',');
				sb.append(followCVSformat(valuesMetodos.get(i))).append(',');

			first = false;
//		}
		}
		sb.append(",");
		w.append(sb.toString());

	}

}
