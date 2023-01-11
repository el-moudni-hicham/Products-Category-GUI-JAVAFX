package ma.enset.tpjdbc.services;

import ma.enset.tpjdbc.dao.CategorieDao;
import ma.enset.tpjdbc.dao.ProduitDao;
import ma.enset.tpjdbc.dao.entites.Categorie;
import ma.enset.tpjdbc.dao.entites.Produit;

import java.util.List;

public class CatalogueServiceImpl implements CatalogueService {
    ProduitDao pdao;
    CategorieDao cdao;

    public CatalogueServiceImpl(ProduitDao pdao, CategorieDao cdao) {
        this.pdao = pdao;
        this.cdao = cdao;
    }

    @Override
    public void addProduit(Produit p) {
        pdao.save(p);
    }

    @Override
    public void deleteProduit(Produit p) {
        pdao.delete(p);
    }

    @Override
    public List<Produit> searchProduitByQuery(String mc) {
        return pdao.findByMotCle(mc);
    }

    @Override
    public Produit updateProduit(Produit p) {
        pdao.update(p);
        return p;
    }

    @Override
    public List<Produit> getProduitParMC(String mc) {
        return pdao.findByMotCle(mc);
    }

    @Override
    public List<Produit> getAllProduits() {
        return pdao.findAll();
    }

    //CATEGORIES

    @Override
    public void addCategorie(Categorie c) {
        cdao.save(c);
    }

    @Override
    public void deleteCategorie(Categorie p) {

    }

    @Override
    public List<Categorie> searchCategorieByQuery(String mc) {
        return null;
    }

    @Override
    public Categorie updateCategorie(Categorie p) {
        return null;
    }

    @Override
    public List<Categorie> getCategorieParMC(String mc) {
        return null;
    }

    @Override
    public List<Categorie> getAllCategories() {
        return cdao.findAll();
    }

}
