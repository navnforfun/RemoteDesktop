/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyClient;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.ImageIcon;

/**
 *
 * @author ngocanh
 */
public class ClientScreen extends Thread {

    Socket socket = null;
    Robot robot = null; // Used to capture screen
    Rectangle rectangle = null; //Used to represent screen dimensions
    boolean continueLoop = true; //Used to exit the program

    public ClientScreen(Socket socket, Robot robot, Rectangle rect) {
        this.socket = socket;
        this.robot = robot;
        rectangle = rect;
        start();
    }

    public void run() {
        ObjectOutputStream oos = null; //Used to write an object to the streem

        try {
            //Prepare ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());
            /*
             * Send screen size to the server in order to calculate correct mouse
             * location on the server's panel
             */
            oos.writeObject(rectangle);
            System.out.println("Write rectangle");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        while (continueLoop) {
            //Capture screen
            BufferedImage image = robot.createScreenCapture(rectangle);
            /* I have to wrap BufferedImage with ImageIcon because BufferedImage class
             * does not implement Serializable interface
             */
            ImageIcon imageIcon = new ImageIcon(image);

            //Send captured screen to the server
            try {
//                System.out.println("before sending image");
                System.out.println("write image");
                oos.writeObject(imageIcon);
                oos.reset(); //Clear ObjectOutputStream cache
//                System.out.println("New screenshot sent");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            //wait for 100ms to r educe network traffic
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
