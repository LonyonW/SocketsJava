package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        
        final String HOST = "192.168.80.17";
        final int PORT = 55551;

        DataInputStream in;
        DataOutputStream out;
        Scanner scanner = new Scanner(System.in);

        try {
            Socket sc = new Socket(HOST, PORT);

            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());

            System.out.println("Hi what do you want? 1. Upload or 2. Download?");

            out.writeUTF(scanner.nextLine());


            sc.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
