/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import entity.Domain;
import entity.Subscriber;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.DomainService;
import service.SubscriberService;
import utils.Session;

/**
 * FXML Controller class
 *
 * @author ihebc_000
 */
public class SubscriberController implements Initializable {

    @FXML
    private Label label_news;
    @FXML
    private Label label_domain;
    @FXML
    private Label label_subscriber;
    @FXML
    private Label label_article;
    @FXML
    private JFXListView<Subscriber> lv_subscriber;
    @FXML
    private JFXButton btn_delete;
    @FXML
    private JFXButton btn_search;
    @FXML
    private JFXTextField txt_search;
    @FXML
    private JFXButton btn_reset;
    @FXML
    private ChoiceBox chDomain;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //navbar
        label_news.setOnMouseClicked((MouseEvent e) -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("/view/Domain.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(DomainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //header
        label_domain.setOnMouseClicked((MouseEvent e) -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("/view/Domain.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(DomainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        label_article.setOnMouseClicked((MouseEvent e) -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("/view/Article.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(DomainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        label_subscriber.setOnMouseClicked((MouseEvent e) -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("/view/Subscriber.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(DomainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btn_delete.setVisible(false);
        SubscriberService ss = new SubscriberService();
        lv_subscriber.getItems().addAll(ss.DisplayAll());
        DomainService ds = new DomainService();
        chDomain.getItems().addAll(ds.DisplayAll());

        //reset
        btn_reset.setOnAction(e -> {
            lv_subscriber.getItems().clear();
            lv_subscriber.getItems().addAll(ss.DisplayAll());
            chDomain.getItems().clear();
            chDomain.getItems().addAll(ds.DisplayAll());
            txt_search.setText("");
            btn_delete.setVisible(false);
        });

        lv_subscriber.setOnMouseClicked((MouseEvent event) -> {
            btn_delete.setVisible(true);
            btn_delete.setOnAction(evt -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("");
                alert.setTitle("Delete ?");
                alert.setContentText("Are you sure you want to delete this?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    ss.delete(lv_subscriber.getSelectionModel().getSelectedItem());
                    Session.selected_article = null;
                    try {
                        Parent page1 = FXMLLoader.load(getClass().getResource("/view/Subscriber.fxml"));
                        Scene scene = new Scene(page1);
                        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(SubscriberController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        });

        btn_search.setOnAction(e -> {
              lv_subscriber.getItems().clear();
            lv_subscriber.getItems().addAll(ss.DisplayByName(txt_search.getText(), (Domain) chDomain.getSelectionModel().getSelectedItem()));
        });

    }

}
