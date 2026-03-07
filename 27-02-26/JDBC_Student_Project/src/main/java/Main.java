import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import dao.StudentDAO;
import dao.StudentDAOImplementation;
import model.Student;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Simple Student Management System");
        boolean running = true;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StudentDAO studentDAO = new StudentDAOImplementation();
        while(running){
            System.out.println("Menu");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Count Students");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            int n = Integer.parseInt(br.readLine());
            switch(n){
                case 1:
                    System.out.print("Enter sid: ");
                    int sid = Integer.parseInt(br.readLine());
                    System.out.print("Enter sname: ");
                    String sname = br.readLine();
                    System.out.print("Enter marks: ");
                    int marks = Integer.parseInt(br.readLine());
                    Student std = new Student();
                    std.setSid(sid);
                    std.setSname(sname);
                    std.setMarks(marks);
                    studentDAO.addStudent(std);
                    break;
                case 2:
                    List<Student> ls = studentDAO.getAllStudents();
                    System.out.println("sid " + "sname " + "marks ");
                    for(Student s : ls){
                        System.out.print(s.getSid() + " ");
                        System.out.print(s.getSname() + " ");
                        System.out.print(s.getMarks());
                        System.out.println();
                    }
                    break;
                case 3:
                    System.out.print("Enter sid: ");
                    int id = Integer.parseInt(br.readLine());
                    Student studinfo = studentDAO.getStudentById(id);
                    if(studinfo.getSid() != 0) {
                        System.out.println("sid  " + "sname  " + "marks  ");
                        System.out.println(studinfo.getSid() + " " + studinfo.getSname() + " " + studinfo.getMarks());
                    }
                    break;
                case 4:
                    System.out.print("Enter the sid you want to update: ");
                    int s_id = Integer.parseInt(br.readLine());
                    System.out.print("Enter 1 for name and 2 for marks: ");
                    int opt = Integer.parseInt(br.readLine());
                    boolean b = false;
                    if(opt == 1){
                        System.out.print("Enter sname: ");
                        String s = br.readLine();
                        b = studentDAO.updateStudent(s_id, s);
                    }else if(opt == 2){
                        System.out.print("Enter marks: ");
                        int mar = Integer.parseInt(br.readLine());
                        b = studentDAO.updateStudent(s_id, mar);
                    }
                    else{
                        System.out.println("Invalid Input!");
                    }
                    System.out.println("Updated: " + b);
                    break;
                case 5:
                    System.out.print("Enter the sid you want to delete: ");
                    int ss_id = Integer.parseInt(br.readLine());
                    boolean bs = studentDAO.deleteStudent(ss_id);
                    System.out.println("Deleted: " + bs);
                    break;
                case 6:
                    System.out.println("Number of Students: " + studentDAO.getTotalStudents());
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid Input Try Again!");
                    break;
            }
        }
        br.close();
    }
}
