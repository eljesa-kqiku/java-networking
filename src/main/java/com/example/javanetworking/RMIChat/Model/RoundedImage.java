package com.example.javanetworking.RMIChat.Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class RoundedImage extends ImageView {
    public RoundedImage(Image img){
        super(img);

        // Set the size of the ImageView
        this.setFitWidth(70);
        this.setFitHeight(70);

        // Create a circle with the desired radius
        Circle clip = new Circle(35, 35, 35);
        this.setClip(clip);

    }
}
