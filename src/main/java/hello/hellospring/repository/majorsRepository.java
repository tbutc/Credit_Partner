package hello.hellospring.repository;

import hello.hellospring.domain.Classes;
import hello.hellospring.domain.Credit;
import hello.hellospring.domain.Subject;
import hello.hellospring.domain.MajorDetail;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.resource.jdbc.internal.ResourceRegistryStandardImpl.close;
import static org.springframework.jdbc.datasource.DataSourceUtils.getConnection;


@Repository
public class majorsRepository {

    private final DataSource dataSource;

    public majorsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public MajorDetail findMajor(String major_name) {
        String sql = "SELECT major_detail.major_info, major_detail.kind_of_student, major_detail.class_basic, major_detail.class_course FROM major_detail INNER JOIN major ON major.pid = major_detail.mid WHERE major.학과 = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        MajorDetail majorDetail = null; // 결과를 저장할 MajorDetail 객체 초기화

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, major_name);
            rs = pstmt.executeQuery();

            majorDetail = new MajorDetail();

            if (rs.next()) {
                // 결과가 있는 경우 MajorDetail 객체를 생성하고 데이터를 설정
                majorDetail.setMajor_info(rs.getString("major_info"));
                majorDetail.setKind_of_student(rs.getString("kind_of_student"));
                majorDetail.setClass_basic(rs.getString("class_basic"));
                majorDetail.setClass_course(rs.getString("class_course"));
            }
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace(); // 예외 정보를 출력하거나 다른 처리를 수행할 수 있습니다.
        } finally {
            close(conn, pstmt,rs);
        }
        return majorDetail;
    }

    public String findMajorName(int majorNum) {
        String sql = "select 학과 from major where pid = ?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String major = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, majorNum);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                major = rs.getString("학과");
            }
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace(); // 예외 정보를 출력하거나 다른 처리를 수행할 수 있습니다.
        } finally {
            close(conn, pstmt, rs);
        }
        return major;
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



