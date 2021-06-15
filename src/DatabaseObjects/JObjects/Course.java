package DatabaseObjects.JObjects;

import java.util.Objects;

public class Course {
    private int id;
    private String courseName;
    private String courseTime;
    private String courseLectureHall;
    private String semesterType;
    private String courseType;
    private String programName;

    public Course(int id, String courseName, String courseTime, String courseLectureHall, String semesterType, String courseType, String programName) {
        this.id = id;
        this.courseName = courseName;
        this.courseTime = courseTime;
        this.courseLectureHall = courseLectureHall;
        this.semesterType = semesterType;
        this.courseType = courseType;
        this.programName = programName;
    }

    public Course(String courseName, String courseTime, String courseLectureHall, String semesterType, String courseType, String programName) {
        this.courseName = courseName;
        this.courseTime = courseTime;
        this.courseLectureHall = courseLectureHall;
        this.semesterType = semesterType;
        this.courseType = courseType;
        this.programName = programName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseLectureHall() {
        return courseLectureHall;
    }

    public void setCourseLectureHall(String courseLectureHall) {
        this.courseLectureHall = courseLectureHall;
    }

    public String getSemesterType() {
        return semesterType;
    }

    public void setSemesterType(String semesterType) {
        this.semesterType = semesterType;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return  Objects.equals(courseName, course.courseName) &&
                Objects.equals(courseTime, course.courseTime) &&
                Objects.equals(courseLectureHall, course.courseLectureHall) &&
                Objects.equals(semesterType, course.semesterType) &&
                Objects.equals(courseType, course.courseType) &&
                Objects.equals(programName, course.programName);
    }

}
