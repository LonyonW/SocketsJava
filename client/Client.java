package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

// /Users/lonyon/Screenshots/Screenshot 2024-02-18 at 4.30.59 PM.png

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
                System.out.print("Put the path of the image: ");
                String pathImage = scanner.nextLine();
                uploadImage(sc, pathImage);
            } else if (myOption == 2) {
                downloadImage(sc, in);
            } else {
                System.out.println("Invalid option");
            }

            

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            sc.close();
        }

    }

    public static void uploadImage(Socket sc, String pathImage) throws IOException {

        FileInputStream inFile = new FileInputStream(pathImage);
        OutputStream outFile = sc.getOutputStream();
        byte[] buffer = new byte[1024];
        int readBytes;
        while ((readBytes = inFile.read(buffer)) != -1) {
            outFile.write(buffer, 0, readBytes);
        }
        inFile.close();

        System.out.println("Uploaded image");
    }

    public static void downloadImage(Socket sc, DataInputStream dataInputStream) throws IOException {
        
        //DataInputStream dataInputStream = new DataInputStream(sc.getInputStream());

        File folder = new File("downloaded_Folder");
        if (!folder.exists()) {
            folder.mkdir();
        }
        int filesCount = dataInputStream.readInt();
        System.out.println(filesCount);
        System.out.println("Receiving " + filesCount + " files");

        for (int i = 0; i < filesCount; i++) {
            String fileName = dataInputStream.readUTF();
            long fileSize = dataInputStream.readLong();

            System.out.println("Descargando archivo: " + fileName);

            FileOutputStream fileOutputStream = new FileOutputStream(new File(folder, fileName));
            byte[] buffer = new byte[1024];
            int bytesRead;
            long totalBytesRead = 0;
            while ((bytesRead = dataInputStream.read(buffer, 0,
                    (int) Math.min(buffer.length, fileSize - totalBytesRead))) != -1 && totalBytesRead < fileSize) {
                fileOutputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
            }
            fileOutputStream.close();

            System.out.println("Archivo descargado: " + fileName);
        }

        System.out.println("Todos los archivos han sido descargados correctamente en la carpeta ");

        System.out.println("Downloaded image");
    }
}
