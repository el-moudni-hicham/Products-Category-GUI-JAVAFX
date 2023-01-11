package ma.enset.tpjdbc.services;

import ma.enset.tpjdbc.dao.entites.Categorie;
import ma.enset.tpjdbc.dao.entites.Produit;

import java.util.List;

public interface CatalogueService {
    void addProduit(Produit p);
    void deleteProduit(Produit p);
    List<Produit> searchProduitByQuery(String mc);
    Produit updateProduit(Produit p);
    List<Produit> getProduitParMC(String mc);

    List<Produit> getAllProduits();

    //CATEGORIES
    void addCategorie(Categorie c);
    void deleteCategorie(Categorie p);
    List<Categorie> searchCategorieByQuery(String mc);
    Categorie updateCategorie(Categorie p);
    List<Categorie> getCategorieParMC(String mc);

    List<Categorie> getAllCategories();
}
