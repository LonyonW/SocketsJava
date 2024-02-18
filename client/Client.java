package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static Socket sc;
    private static DataInputStream in;
    private static DataOutputStream out;
    private static Scanner scanner;
    final int PORT = 55551;
    final String HOST = "192.168.80.17";

    public Client() throws IOException {
        sc = new Socket(HOST, PORT);
        in = new DataInputStream(sc.getInputStream());
        out = new DataOutputStream(sc.getOutputStream());
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();


        try {
            System.out.println("Hi what do you want? 1. Upload or 2. Download?");

            String option = scanner.nextLine();

            out.writeUTF(option);

            int myOption = Integer.parseInt(option);

            if (myOption == 1) {
                uploadImage();
            } else if (myOption == 2) {
                downloadImage();
            } else {
                System.out.println("Invalid option");
            }

            sc.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void uploadImage() {
        System.out.println("Uploaded image");
    }

    public static void downloadImage() {
        System.out.println("Downloaded image");
    }
}
