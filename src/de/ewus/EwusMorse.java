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
    public static long dih = 100, dah = 3 * dih;
    
    
    private void addButtons(char start, int count, Container container) {
        char c = start;
        Button b;
        for (int ic = 0; ic < count; ic++) {
            b = new Button(new Command("Morse " + String.valueOf(c)));
            b.setAlignment(Label.CENTER);
            b.addActionListener(this);
            b.setText(String.valueOf(c));
            container.addComponent(b);
            c = (char)(c + 1);   
        }
    }
    
    public void startApp() {
        Display.init(this);

        morseCodeDisplay = new Label(".  . - -  . . -  . . .");
        morseCodeDisplay.setAlignment(Label.CENTER);
        
        Container buttons = new Container(new GridLayout(6, 6));
        buttons.setScrollableY(true);
        addButtons('A', 26, buttons);
        addButtons('0', 10, buttons);
        
        Form f = new Form("EWUS MorseCode");
        f.setLayout(new BorderLayout());
        
        f.addComponent(BorderLayout.NORTH, morseCodeDisplay);
        f.addComponent(BorderLayout.CENTER, buttons);
        
        f.show();

        Command exitCommand = new Command("Exit");
        f.addCommand(exitCommand);
        
        Command vibrCommand = new Command("Vibrate");
        f.addCommand(vibrCommand);
        
        Command blinkCommand = new Command("Blink");
        f.addCommand(blinkCommand);
        f.setCommandListener(this);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    protected void sleep(long millisecs) {
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e) {};
    }
    
    public void actionPerformed(ActionEvent ae) {
        Command c = ae.getCommand();
        if (c.getCommandName().equals("Exit")) {
            notifyDestroyed();
        }
        if (c.getCommandName().startsWith("Morse ")) {
            morseCodeDisplay.setText(MorseCodeTable.getMorse(c.getCommandName().charAt(6)));
        }
        if (c.getCommandName().equals("Vibrate") || c.getCommandName().equals("Blink")) {
            boolean vibrate = c.getCommandName().equals("Vibrate");
            boolean lastCharWasBlank = false;
            String text = morseCodeDisplay.getText();
            
            for (int i = 0; i < text.length(); i++) {
                char ch = text.charAt(i);
                if (ch == ' ' && lastCharWasBlank) {
                    // long pause
                    sleep(dah);
                } else if (ch == ' ') {
                    // short pause
                    lastCharWasBlank = true;
                    sleep(dih);
                } else {
                    lastCharWasBlank = false;
                    if (ch == '.') {
                        // dot = short
                        if (vibrate) {
                            javax.microedition.lcdui.Display.getDisplay(this).vibrate((int)dih);
                        } else {
                            // blink
                        }
                    } else if (ch == '-') {
                        // dash = long
                        if (vibrate) {
                            javax.microedition.lcdui.Display.getDisplay(this).vibrate((int)dah);
                        } else {
                            // blink
                        }
                    }
                }
            }
            
            
        }
    }
}
