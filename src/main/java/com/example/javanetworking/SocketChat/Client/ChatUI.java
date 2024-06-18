package com.example.javanetworking.SocketChat.Client;

import com.example.javanetworking.HelloApplication;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ChatUI extends Application {
    HBox root = new HBox();
    VBox sidePanel = new VBox();
    HBox userDataContainer = new HBox();
    VBox friendsListContainer = new VBox();
    VBox chatContainer = new VBox();
    HBox activeChatDetails = new HBox();
    ScrollPane messageContainer = new ScrollPane();
    HBox newMessageContainer = new HBox();
    private final String NO_SPACING_BOX_STYLE =
            "-fx-border-color: black; " +
            "-fx-border-width: 1px;" +
            "-fx-border-style: solid;" +
            "-fx-alignment: center;";
    private final String BOX_STYLE =
            NO_SPACING_BOX_STYLE +
            "-fx-padding: 15px;" +
            "-fx-spacing: 15px;" +
                    "-fx-background-color: #e2f2ef;";
    @Override
    public void start(Stage stage) throws Exception {
        buildChatUI(stage);
    }

    private void buildChatUI(Stage stage){
        // region UI Skeleton
            root.getChildren().add(sidePanel);
            root.getChildren().add(chatContainer);
            sidePanel.getChildren().add(userDataContainer);
            sidePanel.getChildren().add(friendsListContainer);
            chatContainer.getChildren().add(activeChatDetails);
            chatContainer.getChildren().add(messageContainer);
            chatContainer.getChildren().add(newMessageContainer);
        // endregion

        // region Side Panel
            sidePanel.setMaxWidth(300);
            sidePanel.setPrefWidth(300);
            // region User Details
                userDataContainer.setPrefHeight(100);
                userDataContainer.setStyle(BOX_STYLE);

                ImageView userAvatar = new ImageView(new Image(HelloApplication.class.getResource("chat/avatars/"+3+".png").toString(), 70, 70, false, false));
                userDataContainer.getChildren().add(userAvatar);
                Label userDisplayName = new Label("Sarah Hamad (me)");
                userDisplayName.setPrefWidth(200);
                userDisplayName.setStyle(
                        "-fx-alignment: center"
                );
                userDataContainer.getChildren().add(userDisplayName);
            // endregion

            // region Friends List
                friendsListContainer.setStyle(NO_SPACING_BOX_STYLE);

                String[] friends = {"John Doe", "Melissa Adams"};

                for (String name : friends){
                    HBox friendCardContainer = new HBox();
                    friendCardContainer.setPrefHeight(100);
                    friendCardContainer.setStyle(NO_SPACING_BOX_STYLE +
                            "-fx-padding: 15px;" +
                            "-fx-spacing: 15px;" +
                            "-fx-background-color: #ebeae5;");
                    ImageView itemAvatar = new ImageView(new Image(HelloApplication.class.getResource("chat/avatars/"+7+".png").toString(), 70, 70, false, false));
                    friendCardContainer.getChildren().add(itemAvatar);
                    Label friendNameLabel = new Label(name);
                    friendNameLabel.setPrefWidth(200);
                    friendNameLabel.setStyle(
                            "-fx-alignment: center"
                    );
                    friendCardContainer.getChildren().add(friendNameLabel);
                    friendsListContainer.getChildren().add(friendCardContainer);
                }

            // endregion
        //endregion

        // region Chat Container
            chatContainer.setPrefWidth(900);
            chatContainer.setPrefHeight(900);

            // region Active Chat Details
                activeChatDetails.setStyle(BOX_STYLE);
                activeChatDetails.setPrefHeight(100);
                ImageView activeChatAvatar = new ImageView(new Image(HelloApplication.class.getResource("chat/avatars/"+15+".png").toString(), 70, 70, false, false));
                activeChatDetails.getChildren().add(activeChatAvatar);
                Label activeChatFriend = new Label("Fatima Saleem");
                activeChatFriend.setPrefWidth(800);
                activeChatFriend.setStyle(
                        "-fx-alignment: center"
                );
                activeChatDetails.getChildren().add(activeChatFriend);
            // endregion

            // region Message Container
                messageContainer.setPrefHeight(750);
                messageContainer.setPrefWidth(900);
                messageContainer.setStyle("-fx-padding: 15px;");
                VBox messages = new VBox();
                messageContainer.setContent(messages);
                messages.setSpacing(15);

                for (int i = 0; i < 10; i++) {
                    VBox msgBox = new VBox();
                    messages.getChildren().add(msgBox);
                    msgBox.setStyle("-fx-background-color: #ebeae5;" +
                            "-fx-border-radius: 20px;" +
                            "-fx-padding: 10px;" +
                            "-fx-spacing: 5px;");

                    Label msgSenderLabel = new Label("Sarah Hamad");
                    msgSenderLabel.setStyle("-fx-font-weight: bold");
                    msgBox.getChildren().add(msgSenderLabel);

                    Label msgContent = new Label("new Label(\"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
                    msgContent.setPrefWidth(800);
                    msgContent.setWrapText(true);
                    msgBox.getChildren().add(msgContent);

                    Label msgTimeStampLabel = new Label("1:36");
                    msgTimeStampLabel.setPrefWidth(800);
                    msgTimeStampLabel.setStyle("-fx-alignment: center-right;");
                    msgBox.getChildren().add(msgTimeStampLabel);
                }



            // endregion

            // region New Message Container
                TextField messageBox = new TextField();
                Button sendButton = new Button("Send");
                messageBox.setPromptText("Type your message here");
                sendButton.setStyle("-fx-background-color: #e2f2ef");
                sendButton.setOnAction(_ -> {
                    String msg = messageBox.getText();
                    System.out.println(msg); // do something with the message here
                    messageBox.setText("");
                });
                newMessageContainer.getChildren().add(messageBox);
                newMessageContainer.getChildren().add(sendButton);
                newMessageContainer.setPrefHeight(50);
                newMessageContainer.setPrefWidth(900);
                messageBox.setPrefHeight(50);
                messageBox.setPrefWidth(800);
                sendButton.setPrefWidth(100);
                sendButton.setPrefHeight(50);
                newMessageContainer.setStyle(NO_SPACING_BOX_STYLE);
            // endregion
        // endregion

        stage.setScene(new Scene(root, 1200, 900));
        stage.setTitle("Socket Chat");
        stage.show();
    }
}
