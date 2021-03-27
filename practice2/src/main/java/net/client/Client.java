package net.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author : Alex
 * @created : 27.03.2021, суббота
 **/
public class Client {
    public static void main(String[] args) {
        String server = "132.145.228.39";
        int port = 8080;
        try {
            Socket socket = new Socket(server, port);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            dos.writeUTF("SETNAME");
            dos.writeUTF("Alex");
            boolean ret = dis.readBoolean();

            dos.writeUTF("CHANGECOLOR");
            dos.writeUTF("green");
            ret = dis.readBoolean();

            dos.writeUTF("DOWN");
            dos.writeUTF("5");
            ret = dis.readBoolean();
            Thread.currentThread().sleep(10000);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
