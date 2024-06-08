package com.example.javanetworking.AESEncryptor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Client extends Application {
    TextField textContent = new TextField();
    ToggleButton toggleButton = new ToggleButton();
    Button fileButton = new Button("Choose a file");
    Button send = new Button("Send");
    Button savaAsFile = new Button("Save File");
    FileChooser fileChooser = new FileChooser();
    String textToManipulate = "";
    String result;
    TextArea resultText = new TextArea();
    Socket socket;
    DataInputStream input;
    DataOutputStream output;

    @Override
    public void start(Stage stage) {
        try {
            socket = new Socket("localhost", 5000);
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e);
        }
        VBox box = new VBox();

        box.getChildren().add(toggleButton);
        box.getChildren().add(fileButton);
        box.getChildren().add(textContent);
        box.getChildren().add(send);
        box.getChildren().add(resultText);
        box.getChildren().add(savaAsFile);

        savaAsFile.setOnAction((ActionEvent _) -> {
            try {
                this.writeToFile(this.result);
            } catch (Exception err) {
                System.out.println(err);
            }
        });

        fileButton.setOnAction((ActionEvent _) -> {
            try {
                textToManipulate = readFromFile();
                System.out.println(textToManipulate);
                this.askServer();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        send.setOnAction((ActionEvent _) -> {
            this.textToManipulate = this.textContent.getText();
            this.askServer();
        });

        Scene scene = new Scene(box);
        stage.setScene(scene);
        stage.show();
    }

    private void askServer() {
        String message;
        if (toggleButton.isSelected())
            message = STR."decrypt\{textToManipulate}";
        else
            message = STR."encrypt\{textToManipulate}";

        try {
            this.output.writeUTF(message);
            this.output.flush();
            this.result = input.readUTF();
            this.resultText.setText(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readFromFile() throws IOException {
        Stage secondStage = new Stage();
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

    private void writeToFile(String data) {
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
}
