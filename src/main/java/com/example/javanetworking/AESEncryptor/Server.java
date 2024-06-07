package com.example.javanetworking.AESEncryptor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception{
        try {
            new Thread(() -> {
                try {
                    ServerSocket serverSocket = new ServerSocket(5000);
                    Socket clientSocket = serverSocket.accept();
                    new ClientHandler(clientSocket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    static class ClientHandler{
        public ClientHandler(Socket socket){
            try{
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                new Thread(() -> {
                    while (true){
                        try{
                            String inputFromClient = input.readUTF();

                            String method = inputFromClient.substring(0, 7);
                            String content = inputFromClient.substring(7);

                            String outputForClient = method.equals("encrypt") ? AESEncrypt.encrypt(content) : AESEncrypt.decrypt(content);

                            output.writeUTF(outputForClient);
                        }catch (Exception e){
                            System.out.println(e);
                        }
                    }
                }).start();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
}
