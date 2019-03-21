package com.lhx.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import io.datafx.controller.ViewController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import javax.annotation.PostConstruct;

@ViewController(value = "/fxml/ListView.fxml")
public final class ListViewController {
    @FXML
    private JFXListView<?> list1;
    @FXML
    private JFXListView<?> list2;
    @FXML
    private JFXListView<?> subList;
    @FXML
    private JFXButton button3D;
    @FXML
    private JFXButton collapse;
    @FXML
    private JFXButton expand;

    private int counter = 0;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {
        // 实现三维效果
        button3D.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int val = ++counter % 2;
                list1.depthProperty().set(val);
                list2.depthProperty().set(val);
            }
        });
        // 设置展开效果
        expand.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                list2.expandedProperty().set(true);
            }
        });
        // 设置合并效果
        collapse.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                list2.expandedProperty().set(false);
            }
        });


    }


}
