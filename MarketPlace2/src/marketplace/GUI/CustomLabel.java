package marketplace.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * <h1>Component for Label </h1>
 * * A custom Label for use within our app.
 * * Adds relevant styles to match our apps design system
 * *
 * * @author Ali
 */
public class CustomLabel extends JLabel {

    /**
     *
     * @param text the text you want to display
     * @param font the font to use (See Util/Fonts)
     * @param alignLeft whether this is aligned left or centered
     */
    public CustomLabel(String text, Font font, boolean alignLeft) {
        super(text);
        this.setFont(font);
        if (alignLeft) {
            this.setAlignmentX( Component.LEFT_ALIGNMENT );
        }
    }

}
