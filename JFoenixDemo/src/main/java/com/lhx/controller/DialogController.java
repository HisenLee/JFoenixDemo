package com.lhx.controller;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;

@ViewController(value = "/fxml/Dialog.fxml")
public final class DialogController {

    public static final String CONTENT_PANE = "ContentPane";
    @FXMLViewFlowContext
    private ViewFlowContext context;
    @FXML
    private JFXButton centerButton;
    @FXML
    private JFXButton topButton;
    @FXML
    private JFXButton rightButton;
    @FXML
    private JFXButton bottomButton;
    @FXML
    private JFXButton leftButton;
    @FXML
    private JFXButton acceptButton;
    @FXML
    private JFXButton alertButton;
    @FXML
    private StackPane root;
    @FXML
    private JFXDialog dialog;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {
        root.getChildren().remove(dialog);
        centerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
                // 显示对话框 [显示在MainAppController传递过来的Pane上]
                dialog.show((StackPane) context.getRegisteredObject(CONTENT_PANE));
            }
        });

        topButton.setOnAction(action -> {
            dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
            dialog.show((StackPane) context.getRegisteredObject(CONTENT_PANE));
        });

        rightButton.setOnAction(action -> {
            dialog.setTransitionType(JFXDialog.DialogTransition.RIGHT);
            dialog.show((StackPane) context.getRegisteredObject(CONTENT_PANE));
        });

        bottomButton.setOnAction(action -> {
            dialog.setTransitionType(JFXDialog.DialogTransition.BOTTOM);
            dialog.show((StackPane) context.getRegisteredObject(CONTENT_PANE));
        });

        leftButton.setOnAction(action -> {
            dialog.setTransitionType(JFXDialog.DialogTransition.LEFT);
            dialog.show((StackPane) context.getRegisteredObject(CONTENT_PANE));
        });

        acceptButton.setOnAction(action -> dialog.close());


        // 前边都是默认的在布局中绘制的dialog，只在代码中调用显示
        // 而下边的是在代码中创建JFXAlert并弹出
        alertButton.setOnAction(action -> {
            JFXAlert alert = new JFXAlert((Stage) alertButton.getScene().getWindow());
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setOverlayClose(false);
            JFXDialogLayout layout = new JFXDialogLayout();
            layout.setHeading(new Label("Modal Dialog using JFXAlert"));
            layout.setBody(new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit,"
                    + " sed do eiusmod tempor incididunt ut labore et dolore magna"
                    + " aliqua. Utenim ad minim veniam, quis nostrud exercitation"
                    + " ullamco laboris nisi ut aliquip ex ea commodo consequat " +
                    " JFXAlert With Java Not fxml."));
            JFXButton closeButton = new JFXButton("ACCEPT");
            closeButton.getStyleClass().add("dialog-accept");
            closeButton.setOnAction(event -> alert.hideWithAnimation());
            layout.setActions(closeButton);
            alert.setContent(layout);
            alert.show();
        });
    } // end init


}
