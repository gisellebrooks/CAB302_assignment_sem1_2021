package marketplace.GUI;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.Document;

/**
 * <h1>Component for the Password Field </h1>
 * * A custom password field for use within our app.
 * * Adds relevant styles to match our apps design system
 * *
 * * @author Ali
 */
@SuppressWarnings("serial")
public class CustomPasswordField extends JPasswordField {
    public void addStyles() {
        setBorder(BorderFactory.createMatteBorder(0,0,4,0, new Color(0,0,0)));
    }

    public CustomPasswordField() {
        addStyles();
    }

    public CustomPasswordField(
            final Document pDoc,
            final String pText,
            final int pColumns)
    {
        super(pDoc, pText, pColumns);
        addStyles();
    }

    public CustomPasswordField(final int pColumns) {
        super(pColumns);
        addStyles();
    }

    public CustomPasswordField(final String pText) {
        super(pText);
        addStyles();
    }

    public CustomPasswordField(final String pText, final int pColumns) {
        super(pText, pColumns);
        addStyles();
    }
}