package dao;

import model.Student;

import java.util.List;

public interface StudentDAO {
    void addStudent(Student s);
    List<Student> getAllStudents();
    Student getStudentById(int id);
    boolean updateStudent(int id, String s);
    boolean updateStudent(int id, int marks);
    boolean deleteStudent(int id);
    int getTotalStudents();
}
