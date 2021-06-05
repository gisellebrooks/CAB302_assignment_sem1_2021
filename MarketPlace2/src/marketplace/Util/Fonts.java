package marketplace.Util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

public class Fonts {
    public Font body;
    public Font heading;
    public Font smallHeading;
    public Font inputLabel;
    public Font small;



    public Fonts() {
        try {
            java.io.InputStream openSansStream = getClass().getClassLoader().getResourceAsStream("OpenSans-Bold.ttf");
            java.io.InputStream delaGothicStream = getClass().getClassLoader().getResourceAsStream("DelaGothicOne-Regular.ttf");
            Font openSans = Font.createFont(Font.TRUETYPE_FONT, openSansStream);
            Font delaGothic = Font.createFont(Font.TRUETYPE_FONT, delaGothicStream);
            body = openSans.deriveFont(16f);
            inputLabel = openSans.deriveFont(10f);
            small = openSans.deriveFont(14f);
            smallHeading = delaGothic.deriveFont(20f);
            heading = delaGothic.deriveFont(28f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(openSans);
            ge.registerFont(delaGothic);
        } catch (IOException | FontFormatException e) {
            System.out.println("Caught an error setting fonts");
            System.out.println(e.getMessage());
        }
    }
}
