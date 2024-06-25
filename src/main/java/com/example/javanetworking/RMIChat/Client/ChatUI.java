package com.example.javanetworking.RMIChat.Client;

import com.example.javanetworking.HelloApplication;
import com.example.javanetworking.RMIChat.Model.FriendInviteStatus;
import com.example.javanetworking.RMIChat.Model.Message;
import com.example.javanetworking.RMIChat.Model.RoundedImage;
import com.example.javanetworking.RMIChat.Model.User;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class ChatUI extends HBox {
    VBox sidePanel = new VBox();
    HBox userDataContainer = new HBox();
    VBox friendsListContainer = new VBox();
    VBox chatContainer = new VBox();
    HBox activeChatDetails = new HBox();
    ScrollPane messageContainer = new ScrollPane();
    HBox newMessageContainer = new HBox();
    TextField messageBox = new TextField();
    Button sendButton = new Button("Send");
    VBox messages = new VBox();
    ClientUIController controller;
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

    public ChatUI(ClientUIController controller) {
        super();
        this.controller = controller;
        this.buildChatUI();
    }

    private void buildChatUI() {
        // region UI Skeleton
        this.getChildren().add(sidePanel);
        this.getChildren().add(chatContainer);
        sidePanel.getChildren().add(userDataContainer);
        sidePanel.getChildren().add(friendsListContainer);
        chatContainer.getChildren().add(activeChatDetails);
        chatContainer.getChildren().add(messageContainer);
        chatContainer.getChildren().add(newMessageContainer);
        // endregion

        // region Side Panel
        sidePanel.setMaxWidth(300);
        sidePanel.setPrefWidth(300);
        //endregion

        // region Chat Container
        chatContainer.setPrefWidth(900);
        chatContainer.setPrefHeight(900);

        // region Active Chat Details
        activeChatDetails.setStyle(BOX_STYLE);
        activeChatDetails.setPrefHeight(100);
        clearActiveChatDetails();
        // endregion

        // region Message Container
        messageContainer.setPrefHeight(750);
        messageContainer.setPrefWidth(900);
        messageContainer.setStyle("-fx-padding: 15px;");
        messageContainer.setContent(messages);
        messageContainer.vvalueProperty().bind(messages.heightProperty());

        messages.setSpacing(15);
        // endregion

        // region New Message Container
        messageBox.setPromptText("Type your message here");
        messageBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendMessage();
            }
        });
        sendButton.setStyle("-fx-background-color: #e2f2ef");
        sendButton.setOnAction(_ -> {
            sendMessage();
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
    }
    private void sendMessage(){
        String msg = messageBox.getText();
        controller.sendMessage(msg);
        messageBox.setText("");
        messageBox.requestFocus();
    }

    public void showMyData(User me) {
        userDataContainer.setPrefHeight(100);
        userDataContainer.setStyle(BOX_STYLE);

        RoundedImage userAvatar = new RoundedImage(new Image(HelloApplication.class.getResource("chat/avatars/" + me.getAvatar() + ".png").toString(), 70, 70, false, false));
        userDataContainer.getChildren().add(userAvatar);
        Label userDisplayName = new Label(me.getDisplayName() + " (Me) ");
        userDisplayName.setPrefWidth(200);
        userDisplayName.setStyle(
                "-fx-alignment: center"
        );
        userDataContainer.getChildren().add(userDisplayName);
    }

    public void showFriends(List<User> friends) {
        if (friends.isEmpty())
            return;
        Platform.runLater(() -> {
            friendsListContainer.getChildren().clear();
            for (User friend : friends) {
                friendsListContainer.setStyle(NO_SPACING_BOX_STYLE);
                HBox friendCardContainer = new HBox();
                friendCardContainer.setPrefHeight(100);
                friendCardContainer.setStyle(NO_SPACING_BOX_STYLE +
                        "-fx-padding: 15px;" +
                        "-fx-spacing: 15px;" +
                        "-fx-background-color: #ebeae5;");


                RoundedImage itemAvatar = new RoundedImage(new Image(HelloApplication.class.getResource("chat/avatars/" + friend.getAvatar() + ".png").toString(), 70, 70, false, false));
                friendCardContainer.getChildren().add(itemAvatar);


                Label friendNameLabel = new Label(friend.getDisplayName());
                friendNameLabel.setPrefWidth(100);
                friendNameLabel.setStyle(
                        "-fx-alignment: center"
                );
                friendCardContainer.getChildren().add(friendNameLabel);
                FriendInviteStatus status = controller.getInvitationStatus(friend.getDisplayName());
                String buttonName = status == FriendInviteStatus.NONE ? "Invite" :
                        status == FriendInviteStatus.SENT_PENDING ? "Busy (Invitation sent)" :
                                status == FriendInviteStatus.RECEIVED_PENDING ? "Accept" :
                                        status == FriendInviteStatus.ACCEPTED ? "Current" :
                                                "Disabled";
                Text buttonText = new Text(buttonName);
                buttonText.setWrappingWidth(100);
                buttonText.maxWidth(100);
                Button invitationButton = new Button();
                invitationButton.setGraphic(buttonText);
                invitationButton.setPrefWidth(100);
                invitationButton.setOnAction(ActionEvent -> {
                    if (status == FriendInviteStatus.NONE) {
                        controller.sendInvitation(friend.getDisplayName());
                    } else if (status == FriendInviteStatus.RECEIVED_PENDING) {
                        controller.acceptInvitation(friend.getDisplayName());
                    }
                    clearActiveChatDetails();
                });
                friendCardContainer.getChildren().add(invitationButton);
                friendsListContainer.getChildren().add(friendCardContainer);
            }
        });
    }

    public void setActiveChatDetails(User usr) {
        Platform.runLater(() -> {
            activeChatDetails.getChildren().clear();
            System.out.println(usr.getAvatar() + " is the avatar ");
            RoundedImage activeChatAvatar = new RoundedImage(new Image(HelloApplication.class.getResource("chat/avatars/" + usr.getAvatar() + ".png").toString(), 70, 70, false, false));
            activeChatDetails.getChildren().add(activeChatAvatar);
            Label activeChatFriend = new Label(usr.getDisplayName());
            activeChatFriend.setPrefWidth(800);
            activeChatFriend.setStyle(
                    "-fx-alignment: center"
            );
            activeChatDetails.getChildren().add(activeChatFriend);
            messageBox.setDisable(false);
            sendButton.setDisable(false);
        });
    }

    public void clearActiveChatDetails() {
        Label activeChatFriend = new Label("Select a friend to begin the chat");
        activeChatFriend.setPrefWidth(800);
        activeChatFriend.setStyle(
                "-fx-alignment: center"
        );
        activeChatDetails.getChildren().clear();
        activeChatDetails.getChildren().add(activeChatFriend);
        messageBox.setDisable(true);
        sendButton.setDisable(true);
        clearMessages();
    }

    public void addMessage(Message msg) {
        Platform.runLater(() -> {
            VBox msgBox = new VBox();
            msgBox.setStyle("-fx-background-color: #ebeae5;" +
                    "-fx-border-radius: 20px;" +
                    "-fx-padding: 10px;" +
                    "-fx-spacing: 5px;");

            Label msgSenderLabel = new Label(msg.getSenderName());
            msgSenderLabel.setStyle("-fx-font-weight: bold");
            msgBox.getChildren().add(msgSenderLabel);

            Label msgContent = new Label(msg.getContent());
            msgContent.setPrefWidth(800);
            msgContent.setWrapText(true);
            msgBox.getChildren().add(msgContent);

            Label msgTimeStampLabel = new Label(msg.getTimestamp());
            msgTimeStampLabel.setPrefWidth(800);
            msgTimeStampLabel.setStyle("-fx-alignment: center-right;");
            msgBox.getChildren().add(msgTimeStampLabel);
            messages.getChildren().add(msgBox);
        });
    }

    public void clearMessages() {
        messages.getChildren().clear();
    }
}
