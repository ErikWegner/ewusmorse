package de.ewus;

import javax.microedition.midlet.*;

import com.sun.lwuit.*;
import com.sun.lwuit.events.*;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.GridLayout;

/**
 * @author Erik Wegner
 */
public class EwusMorse extends MIDlet implements ActionListener {

    Label morseCodeDisplay;
    
    public void startApp() {
        Display.init(this);

        morseCodeDisplay = new Label(". .-- ..- ...");
        
        Container buttons = new Container(new GridLayout(6, 6));
        char c = 'A';
        for (int ic = 0; ic < 26; ic++) {
            buttons.addComponent(new Button(String.valueOf(c)));
            c = (char)(c + 1);   
        }
        c = '0';
        for (int ic = 0; ic < 10; ic++) {
            buttons.addComponent(new Button(String.valueOf(c)));
            c = (char)(c + 1);            
        }
        
        Form f = new Form("EWUS MorseCode");
        f.setLayout(new BorderLayout());
        
        f.addComponent(BorderLayout.NORTH, morseCodeDisplay);
        f.addComponent(BorderLayout.CENTER, buttons);
        
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
