package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * A class that reads text from a file and converts it to a usable form.
 * 
 * @author malinocr
 *
 */
public class TextReader {
	/**
	 * Gets the text of a file at a given path as a array of strings.
	 * 
	 * @param file
	 *            The file
	 * @return The text of the file
	 */
	public static String[] getText(File file) {
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedText = new BufferedReader(fileReader);

			String line = "";
			int numLines = 0;

			while (line != null) {
				line = bufferedText.readLine();
				if (line != null) {
					numLines++;
				}
			}
			bufferedText.close();

			FileReader fileReader2 = new FileReader(file);
			BufferedReader bufferedText2 = new BufferedReader(fileReader2);
			String[] text = new String[numLines];

			for (int i = 0; i < numLines; i++) {
				text[i] = bufferedText2.readLine();
			}
			bufferedText2.close();
			return text;

		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
