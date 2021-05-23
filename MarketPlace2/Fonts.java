import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class Fonts {

    public Fonts() {
        try {
            Font heading;
            // load custom font in project folder
            heading = Font.createFont(Font.TRUETYPE_FONT, new File("DelaGothicOne-Regular.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(heading);
        } catch (IOException | FontFormatException e) {

        }
    }
}
