package com.example.javanetworking.AESEncryptor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception{
        try (ServerSocket serverSocket = new ServerSocket(1025)){
            new Thread(() -> {
                try (Socket clientSocket = serverSocket.accept()){
                    new ClientHandler(clientSocket);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
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
