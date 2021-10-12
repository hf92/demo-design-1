import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class TextFileUI extends UI {
	PrintWriter out;
	UI ui;
	
	public TextFileUI(String filename, UI ui) {
		try {
			out = new PrintWriter(filename);
		} catch (FileNotFoundException e) {
			ui.display("Cannot create file");
		}
	}
	
	public void closeFile() {
		out.close();
	}
	
	@Override
	public void display(String output) {
		out.print(output);
	}

	@Override
	public String captureString() {
		return "";
	}

	@Override
	public int captureInt() {
		return 0;
	}

	@Override
	public int captureLetter(int max) {
		return 0;
	}
}
