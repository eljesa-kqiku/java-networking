package com.example.javanetworking.AESEncryptor;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;

import java.io.IOException;

public class ClientController {
    Client parent;

    public ClientController(Client parent) {
        this.parent = parent;
    }

    @FXML
    private ToggleButton cryptMode;
    @FXML
    private TextArea textInput;
    @FXML
    private TextArea textOutput;

    @FXML
    public void onToggleMethod() {
        boolean currentMethod = cryptMode.isSelected();  // is selected = Decrypting
        if (currentMethod)
            cryptMode.setText("Decrypting");
        else cryptMode.setText("Encrypting");
    }

    @FXML
    public void onProcess() {
        String inputText = textInput.getText();
        if (inputText.isEmpty())
            return;
        String message = getMethod() + textInput.getText();
        String result = parent.processFromServer(message);
        parent.setResult(result);
        textOutput.setText(result);
    }

    @FXML
    public void onReadFile() {
        System.out.println("Read file is invoked");
        try {
            String fileContent = parent.readFromFile();
            textInput.setText(fileContent);
            textOutput.setText("");
            String result = parent.processFromServer(getMethod() + fileContent);
            parent.setResult(result);
            textOutput.setText(result);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void onSaveFile() {
        String outputText = parent.getResult();
        if (outputText.isEmpty())
            return;
        parent.writeToFile(parent.getResult());
    }

    private String getMethod() {
        // is selected = Decrypting
        return cryptMode.isSelected() ? "decrypt" : "encrypt";
    }
}
