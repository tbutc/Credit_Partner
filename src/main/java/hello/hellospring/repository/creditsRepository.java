package hello.hellospring.repository;

import hello.hellospring.domain.Classes;
import hello.hellospring.domain.Credit;
import hello.hellospring.domain.Subject;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class creditsRepository {

    private final DataSource dataSource;

    public creditsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void credits_edit(Credit credit_object){
    }

    public List<Credit> find(int semester, String id) {
        String sql = "SELECT class_id, credit FROM class_list WHERE semester = ? AND member_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, semester);
            pstmt.setString(2, id);
            rs = pstmt.executeQuery();
            List<Credit> credits = new ArrayList<>();
            while (rs.next()) {
                Credit credit = new Credit();
                credit.setCid(rs.getInt("class_id"));
                credit.setCredit(rs.getInt("credit"));
                credits.add(credit);
            }
            return credits;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public List<Classes> find_class(int cid) {
        String sql = "SELECT name, credit, sid FROM class WHERE cid = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cid);
            rs = pstmt.executeQuery();
            List<Classes> classes = new ArrayList<>();
            while (rs.next()) {
                Classes classes1 = new Classes();
                classes1.setName(rs.getString("name"));
                classes1.setCredit(rs.getInt("credit"));
                classes1.setSid(rs.getInt("sid"));
                classes.add(classes1);
            }
            return classes;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void credits_delete(int semester, String id){
        String sql = "DELETE FROM class_list WHERE member_id = ? AND SEMESTER = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setInt(2, semester);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    public void credits_edits(int semester, Credit credit_object, String id){
//        String sql = "insert into class_list values(?, 1, 12, ?, ?)";
        String sql = "INSERT INTO class_list (semester, member_id, class_id, credit) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, semester);
            pstmt.setString(2, id);
            pstmt.setInt(3, credit_object.getCid());
            pstmt.setInt(4, credit_object.getCredit());
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public int find_cid_by_name(String name) {
        String sql = "SELECT cid FROM class WHERE name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int cid = rs.getInt("cid");
                return cid;
            } else {
                // 적절한 처리 (데이터가 없을 때의 예외 처리 등)를 수행하세요.
                throw new IllegalStateException("No data found for the given name");
            }

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public int find_sid_by_class(String name) {
        String sql = "SELECT sid FROM class WHERE name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int sid = rs.getInt("sid");
                return sid;
            } else {
                // 적절한 처리 (데이터가 없을 때의 예외 처리 등)를 수행하세요.
                throw new IllegalStateException("No data found for the given name");
            }

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public List<Subject> find_subject(int sid) {
        String sql;
        if(sid == -1){sql = "SELECT name FROM subject";}
        else{sql = "SELECT name FROM subject WHERE sid = ?";}
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            if (sid != -1) {
                pstmt.setInt(1, sid);
            }
            rs = pstmt.executeQuery();
            List<Subject> subjects = new ArrayList<>();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setName(rs.getString("name"));
                subjects.add(subject);
            }
            return subjects;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
