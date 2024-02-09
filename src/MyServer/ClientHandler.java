/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyServer;

import java.awt.Rectangle;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JLabel;

/**
 *
 * @author ngocanh
 */
public class ClientHandler extends Thread {

    private Socket cSocket = null;
    private JLabel lable = null;

    public ClientHandler(Socket socket, JLabel lable) {
        this.cSocket = socket;
        this.lable = lable;
        start();
    }

    public void run() {
        Rectangle clientScreenDim = null;
        //Used to read screenshots and client screen dimension
        ObjectInputStream ois = null;
        //start drawing GUI
        try {
            //Read client screen dimension
            ois = new ObjectInputStream(cSocket.getInputStream());
            System.out.println("Get rectangle");
            clientScreenDim = (Rectangle) ois.readObject();
            System.out.println(clientScreenDim.toString());
            new ClientScreenReciever(ois, lable);
            new ClientCommandsSender(cSocket, lable, clientScreenDim);
//             DataInputStream dIn = new DataInputStream(cSocket.getInputStream());

//            String  message = dIn.readUTF();
//            System.out.println("Client handle read message: " + message);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

}
