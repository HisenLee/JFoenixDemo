package com.lhx.controller;

import com.jfoenix.controls.JFXComboBox;
import io.datafx.controller.ViewController;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

import javax.annotation.PostConstruct;

@ViewController(value = "/fxml/ComboBox.fxml")
public final class ComboBoxController {

    @FXML
    private JFXComboBox<Label> jfxComboBox;
    @FXML
    private JFXComboBox<Label> jfxEditableComboBox;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {

        jfxComboBox.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                jfxComboBox.validate();
            }
        });

        ChangeListener<? super Boolean> comboBoxFocus = (o, oldVal, newVal) -> {
            if (!newVal) {
                jfxEditableComboBox.validate();
            }
        };
        jfxEditableComboBox.focusedProperty().addListener(comboBoxFocus);
        jfxEditableComboBox.getEditor().focusedProperty().addListener(comboBoxFocus);
        // 如果不设置的话，编辑jfxEditableComboBox选中之后会显示label的hashcode值，所以需要重写toString
        jfxEditableComboBox.setConverter(new StringConverter<Label>() {
            @Override
            public String toString(Label object) {
                return object==null? "" : object.getText();
            }
            @Override
            public Label fromString(String string) {
                return string == null || string.isEmpty() ? null : new Label(string);
            }
        });
    }
}
