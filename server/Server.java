package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static ServerSocket server;
    public static Socket sc;
    public static DataInputStream in;
    public static DataOutputStream out;
    final static int PORT = 55551;

    public static void main(String[] args) {

        try {
            server = new ServerSocket(PORT);
            System.out.println("Server iniciated");

            while (true) {
                sc = server.accept();

                System.out.println("Client conected");

                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());

                int myOption = Integer.parseInt(in.readUTF());

                if (myOption == 1) {
                    uploadImage();
                } else if (myOption == 2) {
                    downloadImage();
                } else {
                    System.out.println("Invalid option");
                }

                sc.close(); // close the client NOT the server

                System.out.println("Client disconected");

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void uploadImage() {
        // Obtain image path

        System.out.println("Uploaded image");
    }

    public static void downloadImage() {
        System.out.println("Downloaded image");
    }

}
