package com.groupfour.travelexpertsfx.controllers;




import com.groupfour.travelexpertsfx.models.Product;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.groupfour.travelexpertsfx.models.ProductDB;
import com.groupfour.travelexpertsfx.models.ProductSupplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;


public class ProductsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="colProductId"
    private TableColumn<ProductSupplier, Integer> colProductId; // Value injected by FXMLLoader

    @FXML // fx:id="colProductName"
    private TableColumn<ProductSupplier, String> colProductName; // Value injected by FXMLLoader

    @FXML // fx:id="colProductId"
    private TableColumn<ProductSupplier, Integer> colSupplierId; // Value injected by FXMLLoader

    @FXML // fx:id="colProductName"
    private TableColumn<ProductSupplier, String> colSupplierName; // Value injected by FXMLLoader

    @FXML // fx:id="tvProduct"
    private TableView<ProductSupplier> tvProduct; // Value injected by FXMLLoader

    @FXML
    private Button btnAdd, btnEdit, btnDelete, btnSaveChanges;

    @FXML
    private HBox HBoxEdit;

    @FXML
    private TextField tfProdName;



    //private ObservableList<Product> productData = FXCollections.observableArrayList();
    private ObservableList<ProductSupplier> productData = FXCollections.observableArrayList();


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'Products.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'Products.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'Products.fxml'.";
        assert colProductId != null : "fx:id=\"colProductId\" was not injected: check your FXML file 'Products.fxml'.";
        assert colProductName != null : "fx:id=\"colProductName\" was not injected: check your FXML file 'Products.fxml'.";
        assert colSupplierId != null : "fx:id=\"colSupplierId\" was not injected: check your FXML file 'Products.fxml'.";
        assert colSupplierName != null : "fx:id=\"colSupplierName\" was not injected: check your FXML file 'Products.fxml'.";
        assert btnSaveChanges != null : "fx:id=\"btnSaveChanges\" was not injected: check your FXML file 'Products.fxml'.";
        assert HBoxEdit != null : "fx:id=\"HBoxEdit\" was not injected: check your FXML file 'Products.fxml'.";
        assert tfProdName != null : "fx:id=\"tfProdName\" was not injected: check your FXML file 'Products.fxml'.";
        assert tvProduct != null : "fx:id=\"tvProduct\" was not injected: check your FXML file 'Products.fxml'.";
        HBoxEdit.setVisible(false);

        setupProductTable();
        displayProduct();

    }

    private void setupProductTable() {
        colProductId.setCellValueFactory(new PropertyValueFactory<ProductSupplier,Integer>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<ProductSupplier,String>("productName"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<ProductSupplier, Integer>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<ProductSupplier, String>("supplierName"));
    }


    public void displayProduct(){
        productData.clear();
        try {
            productData = ProductDB.getProductsSuppliers();
        } catch (SQLException e) {
            throw new RuntimeException("Fail to load Products table", e);
        }
        tvProduct.setItems(productData);
    }

    @FXML
    void addProduct(MouseEvent event) {

        HBoxEdit.setVisible(true);



    }

    public void addProductComplete(MouseEvent mouseEvent) {
        int numRows=0;
        try {
            numRows = ProductDB.addProduct(tfProdName.getText());
            Product product = ProductDB.getProductsByName(tfProdName.getText());
            alertUser(Alert.AlertType.CONFIRMATION, "Product added successfully.\n" +
                    "Details: \n" +
                    "Product ID: "+product.getProductId()+"\n"+
                    "Product Name: "+product.getProductName());
            HBoxEdit.setVisible(false);
            displayProduct();
        } catch (SQLException e) {
            alertUser(Alert.AlertType.ERROR, "An error occurred while adding New Product\n" + e.getMessage());
        }


    }

    public static void alertUser(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.toString());
        alert.setHeaderText("STATUS: " + alertType.toString());
        alert.setContentText(message);
        alert.showAndWait();
    }



}

