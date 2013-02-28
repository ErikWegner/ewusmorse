package de.ewus;

import javax.microedition.midlet.*;

import com.sun.lwuit.*;
import com.sun.lwuit.animations.*;
import com.sun.lwuit.events.*;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.GridLayout;

/**
 * @author Erik Wegner
 */
public class EwusMorse extends MIDlet implements ActionListener {

    Form main, form1, form0;
    
    Label morseCodeDisplay;
    public final static long dih = 200, dah = 5 * dih / 2;
    public final static int transOutDelay = 100;
    
    
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
        main = f;
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
        
        Command clearCommand = new Command("Clear");
        f.addCommand(clearCommand);
        f.addCommandListener(this);
        f.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 150));
        f.setTransitionInAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, 150));
        
        form0 = new Form(); // Light off
        form0.getStyle().setBgColor(0x000000);
        form1 = new Form(); // Light on
        form1.getStyle().setBgColor(0xffffff);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    boolean vibrate = false;
    
    private class BlinkThread extends Thread {
        
        protected javax.microedition.lcdui.Display display;
        
        public void setDiplay(javax.microedition.lcdui.Display display) {
            this.display = display;
        }
        
        protected void msleep(long millisecs) {
            try {
                Thread.sleep(millisecs);
            } catch (InterruptedException e) {};
        }
        
        public void run() {
            boolean lastCharWasBlank = false;
            String text = morseCodeDisplay.getText();
            
            if (!vibrate) {
                form0.show();
                msleep(transOutDelay); // wait for the main screen to transition out
                msleep(750); // wait a moment with a blank screen
            }
            
            for (int i = 0; i < text.length(); i++) {
                char ch = text.charAt(i);
                
                if (ch == ' ' && lastCharWasBlank) {
                    // long pause
                    msleep(dah);
                } else if (ch == ' ') {
                    // short pause
                    lastCharWasBlank = true;
                    msleep(dih);
                } else {
                    lastCharWasBlank = false;
                    if (ch == '.') {
                        // dot = short
                        if (vibrate) {
                            display.vibrate((int)dih*3);
                        } else {
                            // blink
                            form1.show();
                            msleep(dih);
                            form0.show();
                        }
                    } else if (ch == '-') {
                        // dash = long
                        if (vibrate) {
                            display.vibrate((int)dah*3);
                        } else {
                            // blink
                            form1.show();
                            msleep(dah);
                            form0.show();
                        }
                    }
                }
            }
            if (!vibrate) {
                msleep(dah);
                main.show();
            }
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
        Command c = ae.getCommand();
        if (c.getCommandName().equals("Exit")) {
            notifyDestroyed();
        }
        if (c.getCommandName().equals("Clear")) {
            morseCodeDisplay.setText("");
        }
        if (c.getCommandName().startsWith("Morse ")) {
            String t = morseCodeDisplay.getText();
            if (t.length() > 0) {
                t = t + "  ";
            } else {
                //Label disappears in emulator
                main.repaint();
            }
            t = t + MorseCodeTable.getMorse(c.getCommandName().charAt(6));
            morseCodeDisplay.setText(t);
            ae.consume();
        }
        if (c.getCommandName().equals("Vibrate") || c.getCommandName().equals("Blink")) {
            vibrate = c.getCommandName().equals("Vibrate");
            BlinkThread bt = new BlinkThread();
            bt.setDiplay(javax.microedition.lcdui.Display.getDisplay(this));
            bt.start();
        }
    }
}
