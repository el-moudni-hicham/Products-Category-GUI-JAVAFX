package ma.enset.tpjdbc.dao;

import ma.enset.tpjdbc.dao.entites.Categorie;
import ma.enset.tpjdbc.dao.entites.Produit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitDaoImpl implements ProduitDao{
    CategorieDao cdao = new CategorieDaoImpl();
    @Override
    public List<Produit> findAll() {
        Connection connection = SingletonConnexionDB.getConnection();
        List<Produit> produits = new ArrayList();
        try {
            PreparedStatement pstm = connection.prepareStatement("select * from PRODUITS");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Produit p = new Produit();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString(2));
                p.setDescription(rs.getString(3));
                p.setPrix(rs.getFloat(4));
                p.setQuantite(rs.getInt(5));
                p.setCategorie(cdao.findOne(rs.getInt(6)));
                produits.add(p);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return produits;
    }

    @Override
    public Produit findOne(int id) {
        Connection connection = SingletonConnexionDB.getConnection();
        Produit p = new Produit();
        try {
            PreparedStatement pstm = connection.prepareStatement("select * from PRODUITS where ID=?");
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                p.setId(rs.getInt("ID"));
                p.setNom(rs.getString(2));
                p.setDescription(rs.getString(3));
                p.setPrix(rs.getFloat(4));
                p.setQuantite(rs.getInt(5));
            }
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return p;
    }

    @Override
    public Produit save(Produit o) {
        Connection connection = SingletonConnexionDB.getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("insert into PRODUITS(NOM,DESCRIPTION,PRIX,QUANTITE,CAT_ID) values (?,?,?,?,?)");
            pstm.setString(1, o.getNom());
            pstm.setString(2, o.getDescription());
            pstm.setFloat(3, o.getPrix());
            pstm.setInt(4, o.getQuantite());
            pstm.setInt(5,o.getCategorie().getId());
            pstm.executeUpdate();

            //get the last elt in table PRODUITS
            /***
             * pstm = connection.prepareStatement("SELECT * FROM PRODUITS ORDER BY ID DESC LIMIT 1 ");
             * ResultSet rs = pstm.executeQuery();
             *         Produit p = null;
             *         if(rs.next()) {
             *             p.setId(rs.getInt(1));
             *             p.setNom(rs.getString(2));
             *             p.setDescription(rs.getString(3));
             *             p.setPrix(rs.getFloat(4));
             *             p.setQuantite(rs.getInt(4));
             *         }
             *         return p;
             */
        } catch (SQLException e){
            e.printStackTrace();
        }
        return o;

    }

    @Override
    public boolean delete(Produit o) {
        try {
            Connection connection = SingletonConnexionDB.getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM PRODUITS WHERE ID=?");
            pstm.setInt(1,o.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public Produit update(Produit o) {
        Connection connection = SingletonConnexionDB.getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("UPDATE PRODUITS SET NOM=?,DESCRIPTION=?,PRIX=?,QUANTITE=? WHERE ID=?");
            pstm.setString(1, o.getNom());
            pstm.setString(2, o.getDescription());
            pstm.setFloat(3, o.getPrix());
            pstm.setInt(4, o.getQuantite());
            pstm.setInt(5, o.getId());
            pstm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return o;
    }


    @Override
    public List<Produit> findByMotCle(String mc) {
        Connection connection = SingletonConnexionDB.getConnection();
        List<Produit> produits = new ArrayList();
        try {
            PreparedStatement pstm = connection.prepareStatement("select * from PRODUITS where NOM LIKE ?");
            pstm.setString(1, "%" + mc + "%");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Produit p = new Produit();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString(2));
                p.setDescription(rs.getString(3));
                p.setPrix(rs.getFloat(4));
                p.setQuantite(rs.getInt(5));
                produits.add(p);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return produits;
    }
}
