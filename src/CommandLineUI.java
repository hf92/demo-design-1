import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CommandLineUI extends UI {
    private BufferedReader in;
    private Scanner input;

    public CommandLineUI() {
        input = new Scanner(System.in);
        InputStreamReader converter = new InputStreamReader(System.in);
        in = new BufferedReader(converter);
    }

    @Override
    public void display(String output) {
        System.out.print(output);
    }

    @Override
    public String captureString() {
        String inputString = "";
        try {
            inputString = in.readLine();
        } catch (IOException e) {
            display("ERROR: failed to read input\n");
        }
        return inputString;
    }

    @Override
    public int captureInt() {
        while (!input.hasNextInt()) {
            display("Invalid choice. Try again: ");
            input.next();
        }
        return input.nextInt();
    }

    @Override
    public int captureLetter(int max) {
        String s = input.next();

        for (int n = 0; n < 26; n++) {
            if (String.valueOf((char) ('a' + n)).equals(s.toLowerCase())) {
                if (n < max) {
                    return n;
                }
            }
        }

        display("Invalid choice.  Try again: ");
        return captureLetter(max);
    }
}
