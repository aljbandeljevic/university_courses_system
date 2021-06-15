package DatabaseObjects.JObjects;

public class CourseListItem {
    private int id;
    private int studentID;
    private int courseID;
    private String comments;

    public CourseListItem() {}

    public CourseListItem(int studentID, int courseID, String comments) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
