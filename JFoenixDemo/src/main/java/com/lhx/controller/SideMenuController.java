package com.lhx.controller;

import com.jfoenix.controls.JFXListView;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

import javax.annotation.PostConstruct;
import java.util.Objects;

@ViewController(value = "/fxml/SideMenu.fxml")
public final class SideMenuController {

    @FXMLViewFlowContext
    private ViewFlowContext context;
    @FXML
    @ActionTrigger("buttons")
    private Label button;
    @FXML
    @ActionTrigger("checkbox")
    private Label checkbox;
    @FXML
    @ActionTrigger("combobox")
    private Label combobox;
    @FXML
    @ActionTrigger("dialogs")
    private Label dialogs;
    @FXML
    @ActionTrigger("icons")
    private Label icons;
    @FXML
    @ActionTrigger("listview")
    private Label listview;
    @FXML
    @ActionTrigger("treetableview")
    private Label treetableview;
    @FXML
    @ActionTrigger("progressbar")
    private Label progressbar;
    @FXML
    @ActionTrigger("radiobutton")
    private Label radiobutton;
    @FXML
    @ActionTrigger("slider")
    private Label slider;
    @FXML
    @ActionTrigger("spinner")
    private Label spinner;
    @FXML
    @ActionTrigger("textfield")
    private Label textfield;
    @FXML
    @ActionTrigger("togglebutton")
    private Label togglebutton;
    @FXML
    @ActionTrigger("popup")
    private Label popup;
    @FXML
    @ActionTrigger("svgLoader")
    private Label svgLoader;
    @FXML
    @ActionTrigger("pickers")
    private Label pickers;
    @FXML
    @ActionTrigger("masonry")
    private Label masonry;
    @FXML
    @ActionTrigger("scrollpane")
    private Label scrollpane;
    @FXML
    @ActionTrigger("chipview")
    private Label chipview;
    @FXML
    @ActionTrigger("nodeslist")
    private Label nodesList;
    @FXML
    @ActionTrigger("highlighter")
    private Label highlighter;
    @FXML
    private JFXListView<Label> sideListView;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {
        Objects.requireNonNull(context, "context");
        // 从父层[MainAppController]中取出传递过来的ContentFlowHandler对象
        FlowHandler contentFlowHandler = (FlowHandler) context.getRegisteredObject("ContentFlowHandler");
        // 获取到父层[MainAppController]注册过的Flow对象
        Flow contentFlow = (Flow)context.getRegisteredObject("ContentFlow");
        // 把sideListView的鼠标事件传递到父层
        sideListView.propagateMouseEventsToParent();

        sideListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Label>() {
            @Override
            public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
                new Thread(()->{
                    Platform.runLater(()->{
                        if (newValue != null) {
                            try {
                                contentFlowHandler.handle(newValue.getId());
                            } catch (VetoException exc) {
                                exc.printStackTrace();
                            } catch (FlowException exc) {
                                exc.printStackTrace();
                            }
                        }
                    });
                }).start();

            } // end changed
        });
        /**
         *  把控件绑定到Controller中
         */
        //  JFXButton： 常规平面Button和三维效果Button
        bindNodeToController(button, ButtonController.class, contentFlow, contentFlowHandler);

        // JFXCheckBox: 复选框[默认效果和自定义css样式]
        bindNodeToController(checkbox, CheckboxController.class, contentFlow, contentFlowHandler);

        // JFXComboBox: 可编辑的组合框[下拉列表框]
        bindNodeToController(combobox, ComboBoxController.class, contentFlow, contentFlowHandler);

        /**
         * 实现2中Dialog
         * JFXDialog:在fxml中绘制dialog,在Controller中控制显示即可
         * JFXAlert: 在Controller直接代码绘制Alert对话框并弹出
         */
        bindNodeToController(dialogs, DialogController.class, contentFlow, contentFlowHandler);

        /**
         * 实现3中Icon效果
         * Font Awesome Icons : FontAwesomeIconView
         * Animated Icons : JFXHamburger + Hamburger***Transition
         * Badge Icons : JFXBadge + FontAwesomeIconView
         */
        bindNodeToController(icons, IconsController.class, contentFlow, contentFlowHandler);

        // JFXScrollPane: 实现滑动布局，并且顶部可以平滑缩放
        bindNodeToController(scrollpane, ScrollPaneController.class, contentFlow, contentFlowHandler);

        // JFXListView[用label填充数据]: 实现JFXListView的嵌套，三维效果，展开等效果
        bindNodeToController(listview, ListViewController.class, contentFlow, contentFlowHandler);

        // 重点使用对象JFXTreeTableView: 实现表格效果，支持搜索，排序和编辑
        // 【构建对象用了javafxBean实体以及其他面向对象的概念】
        bindNodeToController(treetableview, TreeTableViewController.class, contentFlow, contentFlowHandler);

        // JFXProgressBar: 实现各种样式的进度条
        bindNodeToController(progressbar, ProgressBarController.class, contentFlow, contentFlowHandler);

        // JFXRadioButton: 实现各种样式的单选按钮
        bindNodeToController(radiobutton, RadioButtonController.class, contentFlow, contentFlowHandler);

        // JFXSlider: 实现各种滑动条的效果[类似于音量控制条]
        bindNodeToController(slider, SliderController.class, contentFlow, contentFlowHandler);

        // JFXSpinner: 加载圈，旋转进度圈
        bindNodeToController(spinner, SpinnerController.class, contentFlow, contentFlowHandler);

        // JFXTextField/JFXPasswordField/JFXTextArea: 实现各种输入框
        bindNodeToController(textfield, TextFieldController.class, contentFlow, contentFlowHandler);

        // JFXHighlighter 实现高亮显示文本
        bindNodeToController(highlighter, HighlighterController.class, contentFlow, contentFlowHandler);

        // JFXChipView: 实现标签组控件的效果，类似于多搜索记录的多文本标签效果
        // 并且每个标签可以嵌入icomoon.svg的图片，当记录某个关键词之后会自动显示图片[功能在Controller中实现]
        bindNodeToController(chipview, ChipViewController.class, contentFlow, contentFlowHandler);

        // JFXToggleButton/JFXToggleNode 实现开关按钮的各种效果
        bindNodeToController(togglebutton, ToggleButtonController.class, contentFlow, contentFlowHandler);

        // JFXRippler+JFXHamburger+JFXListView实现下拉列表菜单
        bindNodeToController(popup, PopupController.class, contentFlow, contentFlowHandler);

        // JFXColorPicker JFXDatePicker JFXTimePicker 颜色选择器，日期选择器，时间选择器
        bindNodeToController(pickers, PickersController.class, contentFlow, contentFlowHandler);

        // 在Controller中实现SVGGlyph的各种图标加载和样式[颜色大小等]修改，比较复杂
        bindNodeToController(svgLoader, SVGLoaderController.class, contentFlow, contentFlowHandler);

        // JFXNodesList: 实现三维的Node节点效果
        bindNodeToController(nodesList, NodesListController.class, contentFlow, contentFlowHandler);

        // 在Controller中代码创建StackPane以及相关子控件，并添加到JFXMasonryPane中，实现复杂效果
        bindNodeToController(masonry, MasonryPaneController.class, contentFlow, contentFlowHandler);


    } // end init

    /**
     * 把控件绑定到Controller
     * @param node
     * @param controllerClass
     * @param flow
     * @param flowHandler
     */
    private void bindNodeToController(Node node, Class<?> controllerClass, Flow flow, FlowHandler flowHandler) {
        flow.withGlobalLink(node.getId(), controllerClass);
    }



}
