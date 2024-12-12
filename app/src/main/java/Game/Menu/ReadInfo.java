package Game.Menu;

import java.io.IOException;

public class ReadInfo {

    /**
     * This method returns a name that is read from the keyboard.
     *
     * @return The string that was read from the keyboard.
     * @throws IOException If the string is null, empty or blank.
     */
    public static String readName() throws IOException {
        System.out.println("\nName :\t");
        return Tools.getString();
    }
}
