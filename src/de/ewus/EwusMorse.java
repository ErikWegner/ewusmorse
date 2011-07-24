package de.ewus;

import javax.microedition.midlet.*;

import com.sun.lwuit.*;
import com.sun.lwuit.events.*;

/**
 * @author Erik Wegner
 */
public class EwusMorse extends MIDlet implements ActionListener {

    public void startApp() {
        Display.init(this);

        Form f = new Form("Hello, LWUIT!");
        f.show();

        Command exitCommand = new Command("Exit");
        f.addCommand(exitCommand);
        f.setCommandListener(this);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void actionPerformed(ActionEvent ae) {
        notifyDestroyed();
    }
}
