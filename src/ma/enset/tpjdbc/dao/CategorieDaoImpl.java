package ma.enset.tpjdbc.dao;

import ma.enset.tpjdbc.dao.entites.Categorie;
import ma.enset.tpjdbc.dao.entites.Produit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieDaoImpl implements CategorieDao{
    @Override
    public List<Categorie> findAll() {
        Connection connection = SingletonConnexionDB.getConnection();
        List<Categorie> categories = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM CATEGORIES");
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                Categorie categorie = new Categorie();
                categorie.setId(rs.getInt(1));
                categorie.setNom(rs.getString(2));
                categories.add(categorie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    @Override
    public Categorie findOne(int id) {
        Connection connection = SingletonConnexionDB.getConnection();
        Categorie c = new Categorie();
        try {
            PreparedStatement pstm = connection.prepareStatement("select * from CATEGORIES where ID=?");
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                c.setId(rs.getInt(1));
                c.setNom(rs.getString(2));
            }
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return c;
    }

    @Override
    public Categorie save(Categorie o) {
        Connection connection = SingletonConnexionDB.getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO categories(NOM) VALUES (?)");
            pstm.setString(1, o.getNom());
            pstm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return o;

    }

    @Override
    public boolean delete(Categorie o) {
        return false;
    }

    @Override
    public Categorie update(Categorie o) {
        return null;
    }
}
