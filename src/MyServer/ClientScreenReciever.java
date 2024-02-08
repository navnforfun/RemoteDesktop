/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyServer;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ngocanh
 */
public class ClientScreenReciever extends Thread {

    private ObjectInputStream cObjectInputStream = null;
    private boolean continueLoop = true;
    private JLabel label = null;

    public ClientScreenReciever(ObjectInputStream ois, JLabel label) {
        cObjectInputStream = ois;
        this.label = label;
        //start the thread and thus call the run method
        start();
    }

    public void run() {

        try {
            //Read screenshots of the client then draw them
            while (continueLoop) {
                //Recieve client screenshot and resize it to the current panel size
                ImageIcon imageIcon = (ImageIcon) cObjectInputStream.readObject();
                System.out.println("New image recieved");
                Image image = imageIcon.getImage();
                image = image.getScaledInstance(label.getWidth(), label.getHeight(),
                         Image.SCALE_FAST);
                //Draw the recieved screenshot
                Graphics graphics = label.getGraphics();
                graphics.drawImage(image, 0, 0, label.getWidth(), label.getHeight(), label);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
