package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static ServerSocket server;
    public static Socket sc;
    public static DataInputStream in;
    public static DataOutputStream out;
    private static Scanner scanner;
    final static int PORT = 55551;

    public Server() throws IOException {
        server = new ServerSocket(PORT);

        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) throws IOException {
        Server myServer = new Server();

        try {

            System.out.println("Server iniciated");

            while (true) {

                sc = server.accept();
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());

                System.out.println("Client conected");

                int myOption = Integer.parseInt(in.readUTF());

                if (myOption == 1) {
                    uploadImage(sc);
                } else if (myOption == 2) {
                    downloadImage(sc);
                } else {
                    System.out.println("Invalid option");
                }

                sc.close(); // close the client NOT the server

                System.out.println("Client disconected");

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            server.close();
        }

    }

    public static void uploadImage(Socket sc) throws IOException {
        // Obtain image path

        InputStream inImage = sc.getInputStream();
        FileOutputStream outFile = new FileOutputStream("imagenes/received_img" + System.currentTimeMillis() + ".jpg");
        byte[] buffer = new byte[1024];
        int readBytes;
        while ((readBytes = inImage.read(buffer)) != -1) {
            outFile.write(buffer, 0, readBytes);
        }
        outFile.close();

        System.out.println("Uploaded image");
    }

    public static void downloadImage(Socket sc) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(sc.getOutputStream());
        File folder = new File("imagenes");
        File[] files = folder.listFiles();
        if (files != null) {
            dataOutputStream.writeInt(files.length);
            

            for (File file : files) {
                if (file.isFile()) {
                    dataOutputStream.writeUTF(file.getName());
                    dataOutputStream.writeLong(file.length());

                    FileInputStream fileInputStream = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        sc.getOutputStream().write(buffer, 0, bytesRead);
                        //out.write(buffer, 0, bytesRead);
                    }
                    fileInputStream.close();

                }
            }

        }

        System.out.println("Downloaded images");
    }

}
