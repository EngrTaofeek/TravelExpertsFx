package com.groupfour.travelexpertsfx.controllers;

/**
 * @Author DarylWxc
 * @Date 2/23/2025
 * @Description Agent Form View Controller
 */

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.groupfour.travelexpertsfx.models.Agency;
import com.groupfour.travelexpertsfx.models.AgencyDB;
import com.groupfour.travelexpertsfx.models.Agent;
import com.groupfour.travelexpertsfx.models.AgentDB;
import com.groupfour.travelexpertsfx.utils.AlertMessage;
import com.groupfour.travelexpertsfx.utils.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AgentFormViewController {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="btnSave"
    private Button btnSave; // Value injected by FXMLLoader

    @FXML // fx:id="cbAgency"
    private ComboBox<Agency> cbAgency; // Value injected by FXMLLoader

    @FXML // fx:id="tfBusPhone"
    private TextField tfBusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfEmail"
    private TextField tfEmail; // Value injected by FXMLLoader

    @FXML // fx:id="tfFirstname"
    private TextField tfFirstname; // Value injected by FXMLLoader

    @FXML // fx:id="tfLastname"
    private TextField tfLastname; // Value injected by FXMLLoader

    @FXML // fx:id="tfMiddleInitial"
    private TextField tfMiddleInitial; // Value injected by FXMLLoader

    @FXML // fx:id="tfPosition"
    private TextField tfPosition; // Value injected by FXMLLoader

    private ObservableList<Agency> options = FXCollections.observableArrayList();

    private int agentId = 0;

    public String mode;

    AlertMessage message = new AlertMessage();

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'agent-from-view.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'agent-from-view.fxml'.";
        assert cbAgency != null : "fx:id=\"cbAgency\" was not injected: check your FXML file 'agent-from-view.fxml'.";
        assert tfBusPhone != null : "fx:id=\"tfBusPhone\" was not injected: check your FXML file 'agent-from-view.fxml'.";
        assert tfEmail != null : "fx:id=\"tfEmail\" was not injected: check your FXML file 'agent-from-view.fxml'.";
        assert tfFirstname != null : "fx:id=\"tfFirstname\" was not injected: check your FXML file 'agent-from-view.fxml'.";
        assert tfLastname != null : "fx:id=\"tfLastname\" was not injected: check your FXML file 'agent-from-view.fxml'.";
        assert tfMiddleInitial != null : "fx:id=\"tfMiddleInitial\" was not injected: check your FXML file 'agent-from-view.fxml'.";
        assert tfPosition != null : "fx:id=\"tfPosition\" was not injected: check your FXML file 'agent-from-view.fxml'.";

        setItemsOfAgency();

        btnCancel.setOnAction((ActionEvent event) -> {
            closeWindow(event);
        });

        btnSave.setOnAction((ActionEvent event) -> {
            saveAgent(event);
        });
    }

    private void setItemsOfAgency() {
        try {
            options = AgencyDB.getAgencies();
            cbAgency.setItems(options);
        } catch (SQLException e) {
            message.alertMessage(Alert.AlertType.ERROR, "Error loading agency options");
        }
    }

    private void saveAgent(ActionEvent event) {
        Agent agent = getAgentFormView();
        int numRows = 0;
        if (agent != null) {
            if (mode.equals("add")) {
                try {
                    numRows = AgentDB.addAgent(agent);
                } catch (SQLException e) {
                    message.alertMessage(Alert.AlertType.ERROR, "An error occurred while Adding the agent\n:" + e.getMessage());
                }
            } else {
                try {
                    numRows = AgentDB.updateAgent(agent);
                } catch (SQLException e) {
                    message.alertMessage(Alert.AlertType.ERROR, "An error occurred while Updating the agent\n:" + e.getMessage());
                }
            }
            if (numRows == 1) {
                message.alertMessage(Alert.AlertType.CONFIRMATION, "Successfully saved the agent");
            } else {
                message.alertMessage(Alert.AlertType.ERROR, mode == "add" ? "Insertion" : "Update" + " failed.");
            }
            closeWindow(event);
        }
    }

    private void closeWindow(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    private Agent getAgentFormView() {
        try {
            Validator.validateName(tfFirstname.getText());
            Validator.validateName(tfLastname.getText());
            Validator.validatePhone(tfBusPhone.getText());
            Validator.validateEmail(tfEmail.getText());
            Validator.validateMiddleName(tfMiddleInitial.getText());
            Validator.isEmpty(tfPosition.getText(), "Please enter a position");
            if (cbAgency.getSelectionModel().getSelectedItem() == null) {
                throw new RuntimeException("Please select an agency");
            }
            ;
            return new Agent(agentId != 0 ? agentId : 0,
                    tfFirstname.getText(),
                    tfMiddleInitial.getText(),
                    tfLastname.getText(),
                    tfBusPhone.getText(),
                    tfEmail.getText(),
                    tfPosition.getText(),
                    cbAgency.getSelectionModel().getSelectedItem().getId(), "");
        } catch (RuntimeException e) {
            message.alertMessage(Alert.AlertType.ERROR, e.getMessage());
        }
        return null;
    }

    public void setAgentFormView(Agent agent) {
        tfFirstname.setText(agent.getAgtfirstname());
        tfMiddleInitial.setText(agent.getAgtmiddleinitial());
        tfLastname.setText(agent.getAgtlastname());
        tfBusPhone.setText(agent.getAgtbusphone());
        tfEmail.setText(agent.getAgtemail());
        tfPosition.setText(agent.getAgtposition());
        int index = 0;
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getId() == agent.getAgencyid()) {
                index = i;
            }
        }
        cbAgency.getSelectionModel().select(index);
    }
}
