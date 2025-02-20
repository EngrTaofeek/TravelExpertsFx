package com.groupfour.travelexpertsfx.controllers;




import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.groupfour.travelexpertsfx.models.Product;
import com.groupfour.travelexpertsfx.models.ProductDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="colProductId"
    private TableColumn<Product, Integer> colProductId; // Value injected by FXMLLoader

    @FXML // fx:id="colProductName"
    private TableColumn<Product, String> colProductName; // Value injected by FXMLLoader

    @FXML // fx:id="tvProduct"
    private TableView<Product> tvProduct; // Value injected by FXMLLoader

    private ObservableList<Product> productData = FXCollections.observableArrayList();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert colProductId != null : "fx:id=\"colProductId\" was not injected: check your FXML file 'Products.fxml'.";
        assert colProductName != null : "fx:id=\"colProductName\" was not injected: check your FXML file 'Products.fxml'.";
        assert tvProduct != null : "fx:id=\"tvProduct\" was not injected: check your FXML file 'Products.fxml'.";

        setupProductTable();
        displayProduct();

    }

    private void setupProductTable() {
        colProductId.setCellValueFactory(new PropertyValueFactory<Product,Integer>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<Product,String>("productName"));
    }


    public void displayProduct(){
        productData.clear();
        try {
            productData = ProductDB.getProducts();
        } catch (SQLException e) {
            throw new RuntimeException("Fail to load Products table", e);
        }
        tvProduct.setItems(productData);
    }

}

