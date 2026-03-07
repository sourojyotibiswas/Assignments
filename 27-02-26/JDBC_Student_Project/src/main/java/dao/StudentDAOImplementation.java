/*
* The DAO (Data Access Object) pattern in JDBC (Java Database Connectivity) is a software design pattern
* that separates the data access logic (how you connect to the database, execute SQL, and handle ResultSet objects)
* from the business logic (what your application actually does with the data).
* */
package dao;

import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static dao.DatabaseUtil.myGetConnection;

public class StudentDAOImplementation implements StudentDAO {

    @Override
    public void addStudent(Student s) {
        String insertSql = "INSERT INTO students (sid, sname, marks) VALUES (?, ?, ?)";

        try (Connection con = myGetConnection();
             PreparedStatement ps = con.prepareStatement(insertSql)) {

            ps.setInt(1, s.getSid());
            ps.setString(2, s.getSname());
            ps.setInt(3, s.getMarks());

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " row inserted successfully.");

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // unique_violation in PostgreSQL
                System.out.println("Student with SID already exists!");
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> ls = new ArrayList<>();
        String selectAll = "SELECT * FROM students";

        try (Connection con = myGetConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(selectAll)) {

            while (rs.next()) {
                int sid = rs.getInt(1);
                String sname = rs.getString(2);
                int marks = rs.getInt(3);

                Student std = new Student();
                std.setSid(sid);
                std.setSname(sname);
                std.setMarks(marks);

                ls.add(std);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ls;
    }

//    @Override
//    public Student getStudentById(int id) {
//        Student s = new Student();
//        String selectById = "SELECT * FROM students WHERE sid = ?";
//
//        try (Connection con = myGetConnection();
//             PreparedStatement ps = con.prepareStatement(selectById)) {
//
//                ps.setInt(1, id);
//
//                try (ResultSet rs = ps.executeQuery()) {
//                    if (rs.next()) {
//                        System.out.println("Found student:");
//                        int sid = rs.getInt("sid");
//                        String sname = rs.getString("sname");
//                        int marks = rs.getInt("marks");
//
//                        s.setSid(sid);
//                        s.setSname(sname);
//                        s.setMarks(marks);
//                    } else {
//                        System.out.println("Student with ID " + id + " not found.");
//                    }
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return s;
//    }

    @Override
    public boolean updateStudent(int id, String s) {
        String updateSname;
        try(Connection con = myGetConnection()){
            updateSname = "UPDATE students SET sname = ? WHERE sid = ?";
            PreparedStatement ps = con.prepareStatement(updateSname);
            ps.setString(1, s);
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " row updated successfully.");
            return rowsAffected > 0; // returns true only if there exists any row
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateStudent(int id, int marks) {
        String updateSname;
        try(Connection con = myGetConnection()){
            updateSname = "UPDATE students SET marks = ? WHERE sid = ?";
            PreparedStatement ps = con.prepareStatement(updateSname);
            ps.setInt(1, marks);
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " row updated successfully.");
            return rowsAffected > 0;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteStudent(int id) {
        String deleteStd = "DELETE FROM students WHERE sid = ?";
        try(Connection con = myGetConnection()){
            PreparedStatement ps = con.prepareStatement(deleteStd);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " row deleted successfully.");
            return rowsAffected > 0;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public int getTotalStudents() {
//        String countSql = "SELECT COUNT(*) FROM students";
//
//        try (Connection con = myGetConnection();
//             PreparedStatement ps = con.prepareStatement(countSql);
//             ResultSet rs = ps.executeQuery()) {
//
//            if (rs.next()) {
//                return rs.getInt(1);  // COUNT(*) result
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return 0; // fallback (should not happen)
//    }

}
