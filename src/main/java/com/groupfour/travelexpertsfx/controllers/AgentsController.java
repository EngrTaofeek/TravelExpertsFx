package com.groupfour.travelexpertsfx.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.groupfour.travelexpertsfx.models.Agency;
import com.groupfour.travelexpertsfx.models.AgencyDB;
import com.groupfour.travelexpertsfx.models.Agent;
import com.groupfour.travelexpertsfx.models.AgentDB;
import com.groupfour.travelexpertsfx.utils.AlertMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AgentsController {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdd"
    private Button btnAdd; // Value injected by FXMLLoader

    @FXML // fx:id="btnClear"
    private Button btnClear; // Value injected by FXMLLoader

    @FXML // fx:id="btnDelete"
    private Button btnDelete; // Value injected by FXMLLoader

    @FXML // fx:id="btnEdit"
    private Button btnEdit; // Value injected by FXMLLoader

    @FXML // fx:id="btnSearch"
    private Button btnSearch; // Value injected by FXMLLoader

    @FXML // fx:id="cbAgency"
    private ComboBox<Agency> cbAgency; // Value injected by FXMLLoader

    @FXML // fx:id="tcAgency"
    private TableColumn<Agent, String> tcAgency; // Value injected by FXMLLoader

    @FXML // fx:id="tcEmail"
    private TableColumn<Agent, String> tcEmail; // Value injected by FXMLLoader

    @FXML // fx:id="tcFirstName"
    private TableColumn<Agent, String> tcFirstName; // Value injected by FXMLLoader

    @FXML // fx:id="tcLastName"
    private TableColumn<Agent, String> tcLastName; // Value injected by FXMLLoader

    @FXML // fx:id="tcMiddleInitial"
    private TableColumn<Agent, String> tcMiddleInitial; // Value injected by FXMLLoader

    @FXML // fx:id="tcPhone"
    private TableColumn<Agent, String> tcPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tcPosition"
    private TableColumn<Agent, String> tcPosition; // Value injected by FXMLLoader

    @FXML // fx:id="tfSearchField"
    private TextField tfSearchField; // Value injected by FXMLLoader

    @FXML // fx:id="tvAgent"
    private TableView<Agent> tvAgent; // Value injected by FXMLLoader

    private ObservableList<Agent> data = FXCollections.observableArrayList();

    String mode;

    Agent agent;

    AlertMessage message = new AlertMessage();

    private ObservableList<Agency> options = FXCollections.observableArrayList();

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'Agents.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Agents.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'Agents.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'Agents.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'Agents.fxml'.";
        assert cbAgency != null : "fx:id=\"cbAgency\" was not injected: check your FXML file 'Agents.fxml'.";
        assert tcAgency != null : "fx:id=\"tcAgency\" was not injected: check your FXML file 'Agents.fxml'.";
        assert tcEmail != null : "fx:id=\"tcEmail\" was not injected: check your FXML file 'Agents.fxml'.";
        assert tcFirstName != null : "fx:id=\"tcFirstName\" was not injected: check your FXML file 'Agents.fxml'.";
        assert tcLastName != null : "fx:id=\"tcLastName\" was not injected: check your FXML file 'Agents.fxml'.";
        assert tcMiddleInitial != null : "fx:id=\"tcMiddleInitial\" was not injected: check your FXML file 'Agents.fxml'.";
        assert tcPhone != null : "fx:id=\"tcPhone\" was not injected: check your FXML file 'Agents.fxml'.";
        assert tcPosition != null : "fx:id=\"tcPosition\" was not injected: check your FXML file 'Agents.fxml'.";
        assert tvAgent != null : "fx:id=\"tvAgent\" was not injected: check your FXML file 'Agents.fxml'.";
        assert tfSearchField != null : "fx:id=\"tfSearchField\" was not injected: check your FXML file 'Agents.fxml'.";

        setItemsOfAgency();
        setupAgentTable();
        displayAgents();

        tvAgent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                agent = newValue;
            }
        });

        btnAdd.setOnAction(event -> {
            mode = "add";
            CreateForm(null);
        });

        btnEdit.setOnAction(event -> {
            mode = "edit";
            if (agent != null) {
                CreateForm(agent);
            } else {
                message.alertMessage(Alert.AlertType.ERROR, "Please select a agent first");
            }
        });

        btnDelete.setOnAction(event -> {
            if (agent != null) {
                deleteAgent(agent);
            } else {
                message.alertMessage(Alert.AlertType.ERROR, "Please select a agent first");
            }
        });

        btnClear.setOnAction(event -> {
            clearSearchForm();
        });

        btnSearch.setOnAction(event -> {
            searchAgents();
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

    private void setupAgentTable() {
        tcFirstName.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtfirstname"));
        tcMiddleInitial.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtmiddleinitial"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtlastname"));
        tcPhone.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtbusphone"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtemail"));
        tcPosition.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtposition"));
        tcAgency.setCellValueFactory(new PropertyValueFactory<Agent, String>("agency"));
    }

    public void displayAgents() {
        data.clear();
        try {
            data = AgentDB.getAgents();
        } catch (SQLException e) {
            throw new RuntimeException("Fail to load agents table", e);
        }
        tvAgent.setItems(data);
    }

    private void CreateForm(Agent agent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/groupfour/travelexpertsfx/views/agent-from-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AgentFormViewController controller = fxmlLoader.getController();
        controller.setMode(mode);
        if (agent != null) {
            controller.setAgentFormView(agent);
            controller.setAgentId(agent.getId());
        }
        closeWindow(scene);
        displayAgents();
    }

    private void closeWindow(Scene scene) {
        Stage stage = new Stage();
        stage.setTitle("Agent" + mode);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void deleteAgent(Agent agent) {
        int numRows = 0;
        int agentId = agent.getId();

        try {
            numRows = AgentDB.deleteAgent(agentId);
            if (numRows == 1) {
                message.alertMessage(Alert.AlertType.CONFIRMATION, "The agent has been deleted successfully.");
            } else {
                message.alertMessage(Alert.AlertType.ERROR, "Delete agent failed.");
            }
        } catch (SQLException e) {
            message.alertMessage(Alert.AlertType.ERROR, "An error occurred while deleting the agent\n" + e.getMessage());
        }
        displayAgents();
    }

    private void clearSearchForm() {
        cbAgency.getSelectionModel().clearSelection();
        tfSearchField.clear();
        displayAgents();
    }

    private void searchAgents() {
        data.clear();
        Agency agency = cbAgency.getSelectionModel().getSelectedItem();
        String searchWord = tfSearchField.getText();
        int agencyId = 0;
        if (agency != null) {
            agencyId = agency.getId();
        }
        try {
            data = AgentDB.searchAgents(searchWord, agencyId == 0 ? null : agencyId);
        } catch (SQLException e) {
            throw new RuntimeException("Fail to load agency table", e);
        }
        tvAgent.setItems(data);
    }
}
