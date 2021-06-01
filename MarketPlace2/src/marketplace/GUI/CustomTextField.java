package marketplace.GUI;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class CustomTextField extends JTextField {

    private String placeholder;

    public void addStyles() {
        setBorder(BorderFactory.createMatteBorder(0,0,4,0, new Color(0,0,0)));
    }

    public CustomTextField() {
        addStyles();
    }

    public CustomTextField(
            final Document pDoc,
            final String pText,
            final int pColumns)
    {
        super(pDoc, pText, pColumns);
        addStyles();
    }

    public CustomTextField(final int pColumns) {
        super(pColumns);
        addStyles();
    }

    public CustomTextField(final String pText) {
        super(pText);
        addStyles();
    }

    public CustomTextField(final String pText, final int pColumns) {
        super(pText, pColumns);
        addStyles();
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        if (placeholder == null || placeholder.length() == 0 || getText().length() > 0) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getDisabledTextColor());
        g.drawString(placeholder, getInsets().left, pG.getFontMetrics()
                .getMaxAscent() + getInsets().top);
    }

    public void setPlaceholder(final String s) {
        placeholder = s;
    }

}