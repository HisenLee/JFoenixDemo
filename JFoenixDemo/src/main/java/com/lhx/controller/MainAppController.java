package com.lhx.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDrawerEvent;
import com.lhx.datafx.ExtendedAnimatedFlowContainer;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.container.ContainerAnimations;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import javax.annotation.PostConstruct;

import static io.datafx.controller.flow.container.ContainerAnimations.SWIPE_LEFT;

/**
 *  入口界面MainApp对应的Controller
 */

@ViewController(value = "/fxml/main_app.fxml")  // 此处的Title是JFXToolbar的标题，不是主标题
public final class MainAppController {

    /**
     * ViewFlowContext inner flow and content
     */
    @FXMLViewFlowContext
    private ViewFlowContext context;
    /**
     * 左上角的drawerBurger按钮[控制抽屉闭合状态]
     * 变量名需要对应main_app.fxml中的fx:id
     */
    @FXML
    private JFXHamburger drawerBurger;
    /**
     * 中间核心区域的抽屉控件
     * 变量名需要对应main_app.fxml中的fx:id
     */
    @FXML
    private JFXDrawer mainDrawer;

    /**
     * 右上角的optionsBurger按钮
     * 变量名需要对应main_app.fxml中的fx:id
     */
    @FXML
    private JFXHamburger optionsBurger;
    /**
     * 点击optionsBurger弹出的Popup框
     */
    private JFXPopup toolbarPopup;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() throws Exception {
        // 设置抽屉打开和关闭时候的监听
        mainDrawer.setOnDrawerOpening(new EventHandler<JFXDrawerEvent>() {
            @Override
            public void handle(JFXDrawerEvent event) {
                final Transition animation = drawerBurger.getAnimation();
                animation.setRate(1);
                animation.play();
            }
        });
        mainDrawer.setOnDrawerClosing(new EventHandler<JFXDrawerEvent>() {
            @Override
            public void handle(JFXDrawerEvent event) {
                final Transition animation = drawerBurger.getAnimation();
                animation.setRate(-1);
                animation.play();
            }
        });
        // 设置drawerBurger的点击事件处理
        drawerBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (mainDrawer.isClosed() || mainDrawer.isClosing()) {
                    mainDrawer.open();
                } else {
                    mainDrawer.close();
                }
            }
        });

        // 加载popup的布局文件
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_app_option_popup.fxml"));
        // 设置popup布局文件对应的Controller
        loader.setController(new OptionPopupController());
        toolbarPopup = new JFXPopup((Region) loader.load());
        // 点击optionsBurger 弹出popup
        optionsBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toolbarPopup.show(optionsBurger, JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT, -15, 15);
            }
        });

        // create the inner flow and content
        context = new ViewFlowContext();
        // 创建Flow，并默认设置ButtonController
        Flow innerFlow = new Flow(ButtonController.class);
        // 创建flowHandler
        final FlowHandler flowHandler = innerFlow.createHandler(context);
        context.register("ContentFlowHandler", flowHandler);
        context.register("ContentFlow", innerFlow);
        // 设置抽屉展开和关闭动画
        final Duration containerAnimationDuration = Duration.millis(320);
        // Drawer默认给的内容是BtnHandler的内容
        mainDrawer.setContent(flowHandler.start(new ExtendedAnimatedFlowContainer(
                containerAnimationDuration, SWIPE_LEFT)));
        context.register("ContentPane", mainDrawer.getContent().get(0));

        // 创建SideMenuFlow，并设置SideMenuController
        Flow sideMenuFlow = new Flow(SideMenuController.class);
        // 创建sideMenuFlowHandler并绑定sideMenuFlow[把Context传递给SideMenuController]
        final FlowHandler sideMenuFlowHandler = sideMenuFlow.createHandler(context);
        mainDrawer.setSidePane(sideMenuFlowHandler.start(new ExtendedAnimatedFlowContainer(
                containerAnimationDuration, SWIPE_LEFT)));
    }


    /**
     * main_app_option_popup.fxml 布局文件对应的Controller
     */
    public static final class OptionPopupController {
        @FXML
        private JFXListView<?> toolbarPopupList;

        /**
         * 添加监听 此处submit方法名对应main_app_option_popup.fxml中
         *  的JFXListView的onMouseClicked="#submit"
         */
        @FXML
        private void submit() {
            if (toolbarPopupList.getSelectionModel().getSelectedIndex() == 1) {
                // close application
                Platform.exit();
            }
        }
    }

}
