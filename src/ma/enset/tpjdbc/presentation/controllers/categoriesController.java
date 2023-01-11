package ma.enset.tpjdbc.presentation.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.enset.tpjdbc.dao.CategorieDaoImpl;
import ma.enset.tpjdbc.dao.ProduitDaoImpl;
import ma.enset.tpjdbc.dao.entites.Categorie;
import ma.enset.tpjdbc.dao.entites.Produit;
import ma.enset.tpjdbc.services.CatalogueService;
import ma.enset.tpjdbc.services.CatalogueServiceImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class categoriesController implements Initializable {
    @FXML
    private TextField textNom;

    @FXML
    private Button addCategorie;

    @FXML
    private Button deleteCatogrie;

    @FXML
    private Button updateCategorie;

    @FXML
    private TextField textSearch;

    @FXML
    private TableView<Categorie> categorieTableView;

    @FXML
    private TableColumn<Categorie, Integer> colId;

    @FXML
    private TableColumn<Categorie, String> colNom;
    ObservableList<Categorie> categorieObservableList = FXCollections.observableArrayList();
    CatalogueService categorieService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categorieService = new CatalogueServiceImpl(new ProduitDaoImpl(), new CategorieDaoImpl());
        categorieTableView.setItems(categorieObservableList);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        loadCategories();
    }

    @FXML
    void addCategorie(ActionEvent event) {
        if(textNom.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez remplir tout les champs !");
            alert.show();
        }else{
            String nom = textNom.getText();
            Categorie categorie = new Categorie();
            categorie.setNom(nom);
            categorieService.addCategorie(categorie);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Categorie a ete ajouter avec succes");
            alert.show();
            loadCategories();
        }
    }

    @FXML
    void deleteCategorie(ActionEvent event) {

    }

    @FXML
    void updateCategorie(ActionEvent event) {

    }

    private void loadCategories(){
        categorieObservableList.clear();
        categorieObservableList.addAll(categorieService.getAllCategories());
    }
}
