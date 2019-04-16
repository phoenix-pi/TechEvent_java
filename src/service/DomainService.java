package service;

import entity.Domain;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DataSource;

/**
 *
 * @author Ihèb
 */
public class DomainService implements InterfaceService<Domain> {

    @Override
    public void insert(Domain domain) {
        String req = "insert into domain(name_domain) values(?)";
        try {
            PreparedStatement ps = DataSource.getInstance().getCnx().prepareStatement(req);
            ps.setString(1, domain.getNameDomain());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DomainService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Domain o) {
        try {
            String req = "Delete from domain where id_domain=?  ";
            PreparedStatement ps = DataSource.getInstance().getCnx().prepareStatement(req);
            ps.setInt(1, o.getIdDomain());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DomainService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ObservableList<Domain> DisplayAll() {
        ObservableList<Domain> domains = FXCollections.observableArrayList();
        String req = "SELECT * FROM domain ";
        try {
            PreparedStatement s = DataSource.getInstance().getCnx().prepareStatement(req);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                domains.add(new Domain(rs.getInt("id_domain"), rs.getString("name_domain")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return domains;
    }

    @Override
    public Domain DisplayById(int id) {
        String req = "SELECT * FROM domain where id_domain = ? ";
        Domain domain = new Domain();
        try {
            PreparedStatement s = DataSource.getInstance().getCnx().prepareStatement(req);
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                domain.setIdDomain(rs.getInt(1));
                domain.setNameDomain(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return domain;
    }

    @Override
    public boolean update(Domain os) {
        String req = "update domain set name_domain = ? where id_domain = ?";
        int i = 0;
        try {
            PreparedStatement ps = DataSource.getInstance().getCnx().prepareStatement(req);
            ps.setString(1, os.getNameDomain());
            ps.setInt(2, os.getIdDomain());
            i = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DomainService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i != 0;
    }
    
    
    public ObservableList<Domain> DisplayByName(String name) {
        ObservableList<Domain> domains = FXCollections.observableArrayList();
        String req = "SELECT * FROM domain where name_domain like '%"+name+"%'";
        try {
            PreparedStatement s = DataSource.getInstance().getCnx().prepareStatement(req);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                domains.add(new Domain(rs.getInt("id_domain"), rs.getString("name_domain")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return domains;
    }
    
    public boolean hasArticles (Domain d) {
        int nb = 0;
        String req = "SELECT * FROM article where domain_id = ? ";
        try {
            PreparedStatement s = DataSource.getInstance().getCnx().prepareStatement(req);
            s.setInt(1, d.getIdDomain());
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                nb++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nb!=0;
    }
}
