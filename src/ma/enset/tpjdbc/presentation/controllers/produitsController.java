package ma.enset.tpjdbc.presentation.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import ma.enset.tpjdbc.dao.CategorieDaoImpl;
import ma.enset.tpjdbc.dao.ProduitDaoImpl;
import ma.enset.tpjdbc.dao.entites.Categorie;
import ma.enset.tpjdbc.dao.entites.Produit;
import ma.enset.tpjdbc.services.CatalogueService;
import ma.enset.tpjdbc.services.CatalogueServiceImpl;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class produitsController implements Initializable {
    @FXML
    private TextField textNom;
    @FXML
    private TextArea textDescription;
    @FXML
    private TextField textPrix;
    @FXML
    private TextField textQuantite;
    @FXML
    private TextField textSearch;
    @FXML
    private TableView<Produit> produitTableView;
    @FXML
    private TableColumn<Produit,Integer> colId;
    @FXML
    private TableColumn<Produit,String> colNom;
    @FXML
    private TableColumn<Produit,String> colDescription;
    @FXML
    private TableColumn<Produit,Float> colPrix;
    @FXML
    private TableColumn<Produit,Integer> colQuantite;
    @FXML
    private TableColumn<Produit, Categorie> colCategorie;
    @FXML
    private ComboBox<Categorie> catComboBox;
    @FXML
    private ObservableList<Produit> produitList = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Categorie> categorieObservableList = FXCollections.observableArrayList();

    private CatalogueService produitService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        produitService = new CatalogueServiceImpl(new ProduitDaoImpl(), new CategorieDaoImpl());
        produitTableView.setItems(produitList);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        catComboBox.setItems(categorieObservableList);

        textSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                produitList.clear();
                produitList.addAll(produitService.searchProduitByQuery(newValue));
            }
        });

        produitTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            editProduitCols();
        });

        loadProduits();
        loadCategories();
    }

    @FXML
    public void addProduit() {
        if(textNom.getText().isEmpty() || textDescription.getText().isEmpty() || textPrix.getText().isEmpty() || textQuantite.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez remplir tout les champs !");
            alert.show();
        }else{
            String nom = textNom.getText();
            String description = textDescription.getText();
            float prix = Float.parseFloat(textPrix.getText());
            int quantite = Integer.parseInt(textQuantite.getText());
            Categorie categorie = catComboBox.getSelectionModel().getSelectedItem();
            Produit produit = new Produit();
            produit.setNom(nom);
            produit.setDescription(description);
            produit.setPrix(prix);
            produit.setQuantite(quantite);
            produit.setCategorie(categorie);
            produitService.addProduit(produit);

            textNom.clear();
            textDescription.clear();
            textPrix.clear();
            textQuantite.clear();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Produit a ete ajouter avec succes");
            alert.show();
            loadProduits();
        }
    }

    @FXML
    public void deleteProduit() {
        MultipleSelectionModel<Produit> produit = produitTableView.getSelectionModel();
        if(produit != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Veuillez vous supprimer ce produit ?");
            Optional<ButtonType> optional = alert.showAndWait();
                if (optional.get() == ButtonType.OK) {
                    produitService.deleteProduit(produit.getSelectedItem());
                    //produitList.remove(produit.getSelectedIndex());
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setContentText("Produit a ete supprimer avec succes");
                    alert1.show();
                    loadProduits();
                }
        } else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez selectionner un element ! !");
            alert.show();
        }
    }

    @FXML
    public void updateProduit() {
        Produit produit = produitTableView.getSelectionModel().getSelectedItem();
        if(produit != null){
            produit.setNom(produit.getNom());
            produit.setDescription(produit.getDescription());
            produit.setPrix(produit.getPrix());
            produit.setQuantite(produit.getQuantite());
            produitService.updateProduit(produit);
            loadProduits();
        } else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez selectionner un element ! !");
            alert.show();
        }
    }

    @FXML
    public void clearProduit() {
    }

    private void loadProduits(){
        produitList.clear();
        produitList.addAll(produitService.getAllProduits());
    }
    private void loadCategories(){
        categorieObservableList.clear();
        categorieObservableList.addAll(produitService.getAllCategories());
    }

    public void editProduitCols(){
            colNom.setCellFactory(TextFieldTableCell.forTableColumn());
            colNom.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setNom(e.getNewValue()));
            colDescription.setCellFactory(TextFieldTableCell.forTableColumn());
            colDescription.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setDescription(e.getNewValue()));
            colPrix.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
            colPrix.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setPrix(e.getNewValue()));
            colQuantite.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            colQuantite.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setQuantite(e.getNewValue()));
    }
}
