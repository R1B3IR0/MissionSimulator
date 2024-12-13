package Game.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tools {

    /**
     * This method returns a string that is read from the keyboard.
     *
     * @return The string that was read from the keyboard.
     * @throws IOException If the string is null, empty or blank.
     */
    public static String getString() throws IOException {

        BufferedReader stringIn = new BufferedReader(new
                InputStreamReader(System.in));

        return stringIn.readLine();

    }

    /**
     * This method returns an integer that is read from the keyboard.
     *
     * @return The integer that was read from the keyboard.
     * @throws IOException If the input is not an integer.
     */
    public static int getInt() throws IOException {

        String aux = getString();

        return Integer.parseInt(aux);

    }
}
