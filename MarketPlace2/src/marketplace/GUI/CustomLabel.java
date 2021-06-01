package marketplace.GUI;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel {

    public CustomLabel(String text, Font font, boolean alignLeft) {
        super(text);
        this.setFont(font);
        if (alignLeft) {
            this.setAlignmentX( Component.LEFT_ALIGNMENT );
        }
    }

}
