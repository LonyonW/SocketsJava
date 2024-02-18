package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args){

        ServerSocket server = null;
        Socket sc = null;

        DataInputStream in;
        DataOutputStream out;

        final int PORT = 55551;

        try {
            server = new ServerSocket(PORT);
            System.out.println("Server iniciated");

            while(true) {
                sc = server.accept();

                System.out.println("Client conected");

                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());

                String myMessage = in.readUTF();
                System.out.println(myMessage);

                out.writeUTF("Hi from server");

                sc.close(); // close the client NOT the server

                System.out.println("Client disconected");

            }





        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }




}

