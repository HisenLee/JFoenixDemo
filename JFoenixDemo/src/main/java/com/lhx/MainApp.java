package com.lhx;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import com.lhx.controller.MainAppController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 入口界面
 */
public class MainApp extends Application {

    /**
     * dataFX的上下文对象
     */
    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    public static void main( String[] args ) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws Exception {
        new Thread(() -> {
            try {
                SVGGlyphLoader.loadGlyphsFont(MainApp.class.getResourceAsStream("/fonts/icomoon.svg"),
                        "icomoon.svg");
            } catch (IOException ioExc) {
                ioExc.printStackTrace();
            }
        }).start();

        // 创建dataFX的上下文对象,并注册当前stage
        flowContext = new ViewFlowContext();
        flowContext.register("MainStage", mainStage);
        // 创建FlowContainer
        DefaultFlowContainer flowContainer = new DefaultFlowContainer();
        // 创建Flow并绑定Controller, 再启动FlowContainer
        Flow flow = new Flow(MainAppController.class);
        flow.createHandler(flowContext).start(flowContainer);
        // 创建装饰器Decorator[装饰器控制着主框架的操作，最大化最小化边框样式宽度等]
        JFXDecorator decorator = new JFXDecorator(mainStage, flowContainer.getView(), false, true, true);
        // 此处如果不调用setGraphic方法的话，在main_app.css中给装饰器设置的svg图标就会无效
        decorator.setGraphic(new SVGGlyph(""));
        // 设置标题
        mainStage.setTitle("JFoenix Demo");
        Scene scene = new Scene(decorator, 800, 600);
        // 添加样式
        final ObservableList<String> styleSheets = scene.getStylesheets();
        styleSheets.addAll(MainApp.class.getResource("/css/jfoenix-fonts.css").toExternalForm(),
                            MainApp.class.getResource("/css/jfoenix-design.css").toExternalForm(),
                            MainApp.class.getResource("/css/main_app.css").toExternalForm());
        mainStage.setScene(scene);
        mainStage.show();
    }
}
