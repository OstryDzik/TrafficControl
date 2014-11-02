package Common;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by Filip on 2014-11-02.
 */
public class ReadOnlyCheckBox extends JCheckBox
{
    public ReadOnlyCheckBox (String text, boolean selected) {
        super(text,selected);
    }

    protected void processKeyEvent(KeyEvent e) {
    }

    protected void processMouseEvent(MouseEvent e) {

    }
}
