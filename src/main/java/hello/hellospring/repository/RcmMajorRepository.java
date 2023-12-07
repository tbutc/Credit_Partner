package hello.hellospring.repository;

import hello.hellospring.domain.Classes;
import hello.hellospring.domain.Credit;
import hello.hellospring.domain.RcmMajor;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.jdbc.datasource.DataSourceUtils;
import java.util.List;

@Repository
public class RcmMajorRepository {

    private final DataSource dataSource;

    public RcmMajorRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int findMajorId(String major){
        String sql = "SELECT pid FROM major WHERE 학과 = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, major);
            rs = pstmt.executeQuery();
            int mid = 0;
            while (rs.next()) {
                mid = rs.getInt("pid");
            }
            return mid;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void RcmMajorEdit(RcmMajor major){
        String sql = "INSERT INTO recommended_major (mid1, mid2, mid3, sid) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1, 1);
//            pstmt.setString(2, "12");
//            pstmt.setLong(1, credit_object.getPid());
            pstmt.setInt(1, major.getMid1());
            pstmt.setInt(2, major.getMid1());
            pstmt.setInt(3, major.getMid1());
            pstmt.setString(4, major.getSid());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void RcmMajorEdit(int rcmMajorId1, int rcmMajorId2, int rcmMajorId3, String sid) {
        String sql = "INSERT INTO recommended_major (mid1, mid2, mid3, sid) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1, 1);
//            pstmt.setString(2, "12");
//            pstmt.setLong(1, credit_object.getPid());
            pstmt.setInt(1, rcmMajorId1);
            pstmt.setInt(2, rcmMajorId2);
            pstmt.setInt(3, rcmMajorId3);
            pstmt.setString(4, sid);
            pstmt.executeUpdate();
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

    public void deleteRcm(String id) {
        String sql = "DELETE FROM recommended_major WHERE sid = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
}
