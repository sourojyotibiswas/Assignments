* ========= Student Management =========
1. Add Student // DONE
2. View All Students // DONE
3. Search by ID // DONE
4. Update Student // DONE
5. Delete Student // DONE
6. Count Students // DONE
7. Bulk Insert (Transaction) // Pending
8. Exit // DONE


```
-- You pass a list of Student objects.
-- JDBC sends all inserts together (batch).
-- If all succeed → COMMIT.
-- If any fails → ROLLBACK (nothing gets inserted).
```

```java
public static boolean bulkInsert(List<Student> students) {
    String insertSql = "INSERT INTO students (sid, sname, marks) VALUES (?, ?, ?)";
    Connection con = null;

    try {
        con = myGetConnection();
        con.setAutoCommit(false); // start transaction

        try (PreparedStatement ps = con.prepareStatement(insertSql)) {

            for (Student s : students) {
                ps.setInt(1, s.getSid());
                ps.setString(2, s.getSname());
                ps.setInt(3, s.getMarks());
                ps.addBatch(); // add to batch
            }

            int[] counts = ps.executeBatch(); // run all inserts together

            con.commit(); // all good → commit

            System.out.println("Bulk insert completed. Rows inserted: " + counts.length);
            return true;
        }

    } catch (SQLException e) {
        // something failed → rollback
        if (con != null) {
            try {
                con.rollback();
                System.out.println("Bulk insert failed. Transaction rolled back.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        throw new RuntimeException(e);
    } finally {
        if (con != null) {
            try {
                con.setAutoCommit(true); // reset (good practice)
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```

```java
case 7:
    System.out.print("How many students to insert? ");
    int count = Integer.parseInt(br.readLine());

    List<Student> bulkList = new ArrayList<>();

    for (int i = 0; i < count; i++) {
        System.out.println("Student " + (i + 1) + ":");
        System.out.print("Enter sid: ");
        int sidB = Integer.parseInt(br.readLine());
        System.out.print("Enter sname: ");
        String nameB = br.readLine();
        System.out.print("Enter marks: ");
        int marksB = Integer.parseInt(br.readLine());

        Student st = new Student();
        st.setSid(sidB);
        st.setSname(nameB);
        st.setMarks(marksB);

        bulkList.add(st);
    }

    boolean ok = bulkInsert(bulkList);
    System.out.println("Bulk insert success: " + ok);
    break;
```
