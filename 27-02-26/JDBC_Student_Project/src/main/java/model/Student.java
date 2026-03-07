package model;

public class Student {
    private int sid;
    private String sname;
    private int marks;

    public int getMarks() {
        return marks;
    }
    public int getSid() {
        return sid;
    }
    public String getSname() {
        return sname;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
    public void setSid(int sid) {
        this.sid = sid;
    }
    public void setSname(String sname) {
        this.sname = sname;
    }
}
