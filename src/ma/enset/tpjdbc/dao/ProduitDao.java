package ma.enset.tpjdbc.dao;

import ma.enset.tpjdbc.dao.entites.Produit;

import java.util.List;

public interface ProduitDao extends Dao<Produit>{
    List<Produit> findByMotCle(String mc);
}
