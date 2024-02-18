package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        
        final String HOST = "192.168.80.17";
        final int PORT = 55551;

        DataInputStream in;
        DataOutputStream out;

        try {
            Socket sc = new Socket(HOST, PORT);

            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());

            out.writeUTF("Hi from client");

            String myMessage = in.readUTF();

            System.out.println(myMessage);

            sc.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
