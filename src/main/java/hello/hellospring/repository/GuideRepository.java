package hello.hellospring.repository;

import hello.hellospring.domain.*;
import hello.hellospring.dto.AllClassDTO;
import hello.hellospring.dto.CompleteDTO;
import hello.hellospring.dto.GuideDTO;
import hello.hellospring.dto.SubjectDataDTO;
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
public class GuideRepository {

    private final DataSource dataSource;

    public GuideRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<String> getMajor(String id) {
        String sql = "select m.학과 from recommended_major r, major m where (r.mid1=m.pid or r.mid2=m.pid or r.mid3=m.pid) and r.sid = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            List<String> major_list = new ArrayList<>();
            while (rs.next()) {
                major_list.add(rs.getString("학과"));
            }
            return major_list;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }


    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
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

    public List<AllClassDTO> getAllClass(String major) {
        String sql = "select c.name as class_name, c.credit as credit, s.name as subject_name from class c, subject s where c.sid = s.sid";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<AllClassDTO> class_list = new ArrayList<>();
            while (rs.next()) {
                AllClassDTO class_element = new AllClassDTO();
                class_element.setClass_name(rs.getString("class_name"));
                class_element.setCredit(rs.getInt("credit"));
                class_element.setSubject_name(rs.getString("subject_name"));
                class_list.add(class_element);
            }
            return class_list;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }


    public GuideDTO getAllTotalguide(String id, String major) {
        String sql = "select * from total_guide where sid = ? and major = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, major);
            rs = pstmt.executeQuery();

            List<SubjectDataDTO> subjectDataDTOList = new ArrayList<>();

            while (rs.next()) {

                // SubjectDataDTO를 생성하고 설정
                SubjectDataDTO subjectData = new SubjectDataDTO(
                        rs.getString("category"),
                        rs.getString("subject"),
                        rs.getString("class"),
                        rs.getInt("credit"),
                        rs.getString("course"),
                        rs.getInt("complete"),
                        rs.getBoolean("recommend"),
                        rs.getBoolean("chosen")
                );
                subjectDataDTOList.add(subjectData);
            }
            GuideDTO guide_list = new GuideDTO(major, subjectDataDTOList);
            return guide_list;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public String getSID(String id, String major) {
        String sql = "select distinct t.sid as sid from total_guide t where t.sid = ? and t.major = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sid = "";
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, major);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                sid = rs.getString("sid");
            }

        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace(); // 예외 정보를 출력하거나 다른 처리를 수행할 수 있습니다.
        } finally {
            close(conn, pstmt, rs);
        }
        return sid;
    }

    public List<String> getSubjectList(String major) {
        String sql = "SELECT class_basic as '학과1', class_course as '학과2' FROM major_detail WHERE pid in (select pid from major where 학과 = ?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, major);
            rs = pstmt.executeQuery();

            List<String> subject_list = new ArrayList<>();
            while (rs.next()) {
                subject_list.add(rs.getString("학과1"));
                subject_list.add(rs.getString("학과2"));
            }
            return subject_list;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }


    public List<CompleteDTO> getCompleteClass(String id) {
        String sql = "select c.name as 'class_name', cl.credit as 'credit', s.name as 'subject', cl.semester as 'semester' from class_list cl, class c, subject s where cl.member_id = ? and cl.class_id = c.cid and c.sid = s.sid;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CompleteDTO> class_list = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CompleteDTO class_element = new CompleteDTO();
                class_element.setClass_name(rs.getString("class_name"));
                class_element.setCredit(rs.getInt("credit"));
                class_element.setSubject_name(rs.getString("subject"));
                class_element.setSemester(rs.getInt("semester"));
                class_list.add(class_element);
            }
            return class_list;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }


    public void insertTemporaryGuide(String major, String id, List<SubjectDataDTO> subjectDataDTOList) {

        for (int i = 0; i < subjectDataDTOList.size(); i++) {
            String sql = "INSERT INTO total_guide (sid, major, category, subject, class, credit, course, complete, recommend, chosen) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, id);
                pstmt.setString(2, major);
                pstmt.setString(3, subjectDataDTOList.get(i).getCategory());
                pstmt.setString(4, subjectDataDTOList.get(i).getSubject());
                pstmt.setString(5, subjectDataDTOList.get(i).getClasses());
                pstmt.setInt(6, subjectDataDTOList.get(i).getCredit());
                pstmt.setString(7, subjectDataDTOList.get(i).getCourse());
                pstmt.setInt(8, subjectDataDTOList.get(i).getComplete());
                pstmt.setBoolean(9, subjectDataDTOList.get(i).isRecommend());
                pstmt.setBoolean(10, subjectDataDTOList.get(i).isChosen());
                pstmt.executeUpdate();

            } catch (Exception e) {
                throw new IllegalStateException(e);
            } finally {
                close(conn, pstmt, rs);
            }
        }
    }

    public void deleteTemporaryGuide(String major, String id) {
        String sql = "DELETE FROM total_guide WHERE major = ? and sid = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, major);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void deleteGuide(String id) {
        String sql = "DELETE FROM total_guide where sid = ?";

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

    public void complete_create(List<CompleteDTO> completeList) {
        for (int i = 0; i < completeList.size(); i++) {
            String sql = "INSERT INTO tmp_CompleteList VALUES (?, ?, ?, ?)";
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, completeList.get(i).getClass_name());
                pstmt.setInt(2, completeList.get(i).getCredit());
                pstmt.setString(3, completeList.get(i).getSubject_name());
                pstmt.setInt(4, completeList.get(i).getSemester());
                pstmt.executeUpdate();

            } catch (Exception e) {
                throw new IllegalStateException(e);
            } finally {
                close(conn, pstmt, rs);
            }
        }
    }

    public void complete_delete() {
        String sql = "DELETE FROM tmp_CompleteList";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void complete_check(String major, String id) {
        String sql = "UPDATE total_guide tg JOIN tmp_CompleteList tc ON tg.class = tc.class_name SET tg.complete = tc.semester, tg.chosen = true, tg.credit = tc.credit WHERE tg.sid = ? AND tg.major = ?;";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, major);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    //채윤코드
    //추천 받은 과목들 가져와서 테이블에 넣기
    public void insertSubjects(List<String> recommendSubjects) {
        for (int i = 0; i < recommendSubjects.size(); i++) {
            String sql = "INSERT INTO tmp_RecommendList VALUES (?)";
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, recommendSubjects.get(i));
                pstmt.executeUpdate();

            } catch (Exception e) {
                throw new IllegalStateException(e);
            } finally {
                close(conn, pstmt, rs);
            }
        }
    }

    public void recommend_check(String major, String id) {
        String sql = "UPDATE total_guide tg JOIN tmp_RecommendList tr on tr.class_name = tg.class set tg.recommend = true, tg. chosen = true where tg.sid = ? and tg.major=?;";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, major);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

    }

    public void recommend_delete(){
        String sql = "DELETE FROM tmp_RecommendList";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

    }

    public void insertGuide(String major, String id, List<SubjectDataDTO> subjectDataDTOList) {

        for (int i = 0; i < subjectDataDTOList.size(); i++) {
            String sql = "INSERT INTO total_guide (sid, major, category, subject, class, credit, course, complete, recommend, chosen) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, id);
                pstmt.setString(2, major);
                pstmt.setString(3, subjectDataDTOList.get(i).getCategory());
                pstmt.setString(4, subjectDataDTOList.get(i).getSubject());
                pstmt.setString(5, subjectDataDTOList.get(i).getClasses());
                pstmt.setInt(6, subjectDataDTOList.get(i).getCredit());
                pstmt.setString(7, subjectDataDTOList.get(i).getCourse());
                pstmt.setInt(8, subjectDataDTOList.get(i).getComplete());
                pstmt.setBoolean(9, subjectDataDTOList.get(i).isRecommend());
                pstmt.setBoolean(10, subjectDataDTOList.get(i).isChosen());
                pstmt.executeUpdate();

            } catch (Exception e) {
                throw new IllegalStateException(e);
            } finally {
                close(conn, pstmt, rs);
            }
        }

    }
}








