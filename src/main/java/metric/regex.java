package metric;

import java.util.regex.*;

public class regex {

	public static void main(String[] args) {

		String txt = "public class";

		String re1 = "(public)"; // Word 1
		String re2 = ".*?"; // Non-greedy match on filler
		String re3 = "(class)"; // Word 2

		Pattern p = Pattern.compile(re1 + re2 + re3, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(txt);
		if (m.find()) {
			String word1 = m.group(1);
			String word2 = m.group(2);
			System.out.print("(" + word1.toString() + ")" + "(" + word2.toString() + ")" + "\n");
		}

	}

}
