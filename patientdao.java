package mentalhealth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class patientdao {

    private patient maprow(ResultSet rs) throws SQLException {
        patient p = new patient();
        p.setid(rs.getString("id"));
        p.setname(rs.getString("name"));
        p.setage(rs.getInt("age"));
        p.setgender(rs.getString("gender"));
        p.setmood(rs.getString("mood"));
        p.setseverity(rs.getString("severity"));
        p.setsymptoms(rs.getString("symptoms"));
        p.setstatus(rs.getString("status"));
        return p;
    }

    // select all, or filter by id / name when a keyword is given
    public List<patient> getall(String keyword) throws SQLException {

        boolean filter = keyword != null && !keyword.trim().isEmpty();
        String sql = "select * from patients"
                + (filter ? " where id like ? or name like ?" : "")
                + " order by id";

        List<patient> list = new ArrayList<>();

        try (Connection con = dbconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (filter) {
                String like = "%" + keyword.trim() + "%";
                ps.setString(1, like);
                ps.setString(2, like);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(maprow(rs));
                }
            }
        }
        return list;
    }

    public patient getbyid(String id) throws SQLException {

        try (Connection con = dbconnection.getconnection();
             PreparedStatement ps = con.prepareStatement("select * from patients where id = ?")) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? maprow(rs) : null;
            }
        }
    }

    public void insert(patient p) throws SQLException {

        String sql = "insert into patients (id, name, age, gender, mood, severity, symptoms, status) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = dbconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getid());
            ps.setString(2, p.getname());
            ps.setInt(3, p.getage());
            ps.setString(4, p.getgender());
            ps.setString(5, p.getmood());
            ps.setString(6, p.getseverity());
            ps.setString(7, p.getsymptoms());
            ps.setString(8, p.getstatus());
            ps.executeUpdate();
        }
    }

    public boolean update(patient p) throws SQLException {

        String sql = "update patients set name = ?, age = ?, gender = ?, mood = ?, "
                + "severity = ?, symptoms = ?, status = ? where id = ?";

        try (Connection con = dbconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getname());
            ps.setInt(2, p.getage());
            ps.setString(3, p.getgender());
            ps.setString(4, p.getmood());
            ps.setString(5, p.getseverity());
            ps.setString(6, p.getsymptoms());
            ps.setString(7, p.getstatus());
            ps.setString(8, p.getid());
            return ps.executeUpdate() > 0;
        }
    }

    public void delete(String id) throws SQLException {

        try (Connection con = dbconnection.getconnection();
             PreparedStatement ps = con.prepareStatement("delete from patients where id = ?")) {

            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
