package com.example.javanetworking.AESEncryptor;

import com.example.javanetworking.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Client extends Application {
    Socket socket;
    DataInputStream input;
    DataOutputStream output;
    private String result;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("aes-encrypt.fxml"));
        loader.setControllerFactory(controllerClass -> {
            try {
                return controllerClass.getConstructor(Client.class).newInstance(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Scene scene = new Scene(loader.load(), 600, 400);
        try {
            socket = new Socket("localhost", 5000);
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e);
        }
        stage.setScene(scene);
        stage.show();
    }

    public String processFromServer(String message) {
        try {
            this.output.writeUTF(message);
            this.output.flush();
            return input.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readFromFile() throws IOException {
        Stage secondStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(secondStage);
        fileChooser.setTitle("Select a File");
        String filePath;
        if (file != null) {
            filePath = file.getAbsolutePath();
            secondStage.hide();
        } else {
            return "";
        }
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder message = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null)
            message.append(line);
        return message.toString();
    }

    public void writeToFile(String data) {
        Stage secondStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showSaveDialog(secondStage);

        if (file != null) {
            secondStage.hide();
            try (FileOutputStream fos = new FileOutputStream(file);
                 BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                byte[] bytes = data.getBytes();
                bos.write(bytes);
                bos.close();
                fos.close();
                System.out.print("Data written to Output File successfully.");
            } catch (IOException err) {
                System.out.println(err);
            }
        } else {
            System.out.println("No file selected");
        }
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
