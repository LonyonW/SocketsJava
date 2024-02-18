package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private static ServerSocket server;
    private static Scanner scanner;
    final static int PORT = 55551;

    public Server() throws IOException {
        server = new ServerSocket(PORT);
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        try {
            Server myServer = new Server();
            System.out.println("Server initiated");

            while (true) {
                Socket clientSocket = server.accept();
                Thread clientThread = new ClientHandler(clientSocket);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

                int myOption = Integer.parseInt(in.readUTF());

                if (myOption == 1) {
                    uploadImage(clientSocket);
                } else if (myOption == 2) {
                    downloadImage(clientSocket);
                } else {
                    System.out.println("Invalid option");
                }

                clientSocket.close();
                System.out.println("Client disconnected: " + clientSocket.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
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
}


