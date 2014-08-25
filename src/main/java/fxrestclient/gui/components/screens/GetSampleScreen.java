package fxrestclient.gui.components.screens;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Created by ton on 8/25/14.
 */
public class GetSampleScreen extends Parent {

    public Group start() throws Exception {

        Group group = new Group();
        group.prefWidth(700);
        group.prefHeight(500);

        HBox box = new HBox();
        Label label = new Label("Mensagem");

        box.getChildren().add(label);

        group.getChildren().add(box);
        return group;
    }
}
