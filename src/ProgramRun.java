import UI.StudentsCommentsForm;
import UI.MainMenuForm;
import UI.CommentsForm;
import UI.AllStudentsForm;
import UI.FriendsForm;
import UI.CoursesForAddingCommentsForm;
import UI.MyCoursesForm;
import UI.LoginForm;
import UI.FriendCourseForm;
import UI.EditProfileForm;
import UI.AllCoursesForm;
import UI.CourseAttendeesForm;
import Validations.ProgramValidations;
import Validations.CoursesValidations;
import Validations.CourseListValidations;
import Validations.StudentValidations;
import Validations.FriendsValidations;
import DatabaseObjects.JObjects.Course;
import DatabaseObjects.JObjects.CourseListItem;
import DatabaseObjects.JObjects.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProgramRun {
    public static String theUsername;
    private static List<Course> studentCourses;

    // forms
    private static LoginForm loginForm;
    private static MainMenuForm dashboardForm;
    private static AllCoursesForm coursesForm;
    private static EditProfileForm editProfileForm;
    private static MyCoursesForm myCoursesForm;
    private static CourseAttendeesForm courseAttendeesForm;
    private static CoursesForAddingCommentsForm coursesForAddingCommentsForm;
    private static CommentsForm commentsForm;
    private static StudentsCommentsForm studentsCommentsForm;
    private static AllStudentsForm allStudentsForm;
    private static FriendsForm friendsForm;
    private static FriendCourseForm friendCourseForm;

    // business logic classes
    private static StudentValidations studentValidations;
    private static CoursesValidations coursesValidations;
    private static CourseListValidations courseListValidations;
    private static ProgramValidations programValidations;
    private static FriendsValidations friendsValidations;

    public static void main(String[] args) {
        initialize();
        setProperties();

        loginForm.setVisible(true);

        loginForm.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginForm.getUsernameTextfield().getText().trim();
                String password = new String(loginForm.getPasswordField().getPassword());

                if (studentValidations.validate(username, password)) {
                    dashboardForm.setVisible(true);
                    loginForm.setVisible(false);

                    theUsername = username;

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials");
                }

            }
        });

        dashboardForm.getLogOutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?",
                        "CONFIRM",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    // yes option
                    loginForm.setVisible(true);
                    dashboardForm.setVisible(false);

                    resetLoginForm();

                }

            }
        });

        dashboardForm.getAvailableCoursesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCoursesToTable();
                coursesForm.setVisible(true);
                dashboardForm.setVisible(false);

            }
        });

        dashboardForm.getEditAccountButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProfileForm.setVisible(true);
                dashboardForm.setVisible(false);

            }
        });

        dashboardForm.getMyCoursesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMyCourses();
                addMyCoursesToTable();

                myCoursesForm.setVisible(true);
                dashboardForm.setVisible(false);

            }
        });

        dashboardForm.getAddCommentsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCoursesToCommentsTable();
                coursesForAddingCommentsForm.setVisible(true);
                dashboardForm.setVisible(false);

            }
        });

        dashboardForm.getAllStudentsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAllStudents();
                allStudentsForm.setVisible(true);
                dashboardForm.setVisible(false);

            }
        });

        dashboardForm.getFriendsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAllFriends();
                friendsForm.setVisible(true);
                dashboardForm.setVisible(false);

            }
        });

        coursesForm.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashboardForm.setVisible(true);
                coursesForm.setVisible(false);

            }
        });

        coursesForm.getAddCourseToCourseListButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (coursesForm.getCoursesTable().getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a course");
                } else {
                    int row = coursesForm.getCoursesTable().getSelectedRow();
                    int courseID = (Integer) coursesForm.getCoursesTable().getValueAt(row, 0);

                    Course selectedCourse = coursesValidations.getCourseByID(courseID);

                    addCourseToStudentCourses(selectedCourse);
                }

            }
        });

        coursesForm.getViewAttendeesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (coursesForm.getCoursesTable().getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a course");
                } else {
                    int row = coursesForm.getCoursesTable().getSelectedRow();
                    String courseName = (String) coursesForm.getCoursesTable().getValueAt(row, 1);

                    Course course = coursesValidations.getCourseByCourseName(courseName);

                    List<Integer> attendees = courseListValidations.getAllAttendees(course.getId());

                    loadAllCourseAttendees(attendees);

                    courseAttendeesForm.source = "All Courses";

                    courseAttendeesForm.setVisible(true);
                    coursesForm.setVisible(false);
                }
            }
        });

        editProfileForm.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = editProfileForm.getFirstNameTextField().getText().trim();
                String lastName = editProfileForm.getLastNameTextField().getText().trim();
                int age = Integer.parseInt(editProfileForm.getAgeTextField().getText().trim());

                Student student = new Student();
                student.setUsername(theUsername);
                student.setFirstName(firstName);
                student.setLastName(lastName);
                student.setAge(age);

                if (studentValidations.validateAndUpdateStudent(student)) {
                    // student data updated successfully

                    JOptionPane.showMessageDialog(null, "Profile updated successfully");
                    resetEditProfileForm();
                    dashboardForm.setVisible(true);
                    editProfileForm.setVisible(false);

                }

            }
        });

        editProfileForm.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashboardForm.setVisible(true);
                editProfileForm.setVisible(false);

            }
        });

        myCoursesForm.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashboardForm.setVisible(true);
                myCoursesForm.setVisible(false);

            }
        });

        myCoursesForm.getDropCourseButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (myCoursesForm.getMyCoursesTable().getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a course");
                } else {
                    int row = myCoursesForm.getMyCoursesTable().getSelectedRow();
                    String courseName = (String) myCoursesForm.getMyCoursesTable().getValueAt(row, 1);

                    Course course = coursesValidations.getCourseByCourseName(courseName);

                    CourseListItem item = new CourseListItem();
                    Student student = studentValidations.getStudentByUsername(theUsername);

                    int studentID = student.getId();

                    item.setStudentID(studentID);
                    item.setCourseID(course.getId());

                    if (courseListValidations.dropACourseForStudent(item)) {
                        JOptionPane.showMessageDialog(null, "Course dropped successfully");
                        loadMyCourses();
                        addMyCoursesToTable();

                    }
                }
            }
        });

        myCoursesForm.getCourseAttendeesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (myCoursesForm.getMyCoursesTable().getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a course");
                } else {
                    int row = myCoursesForm.getMyCoursesTable().getSelectedRow();
                    String courseName = (String) myCoursesForm.getMyCoursesTable().getValueAt(row, 1);

                    Course course = coursesValidations.getCourseByCourseName(courseName);

                    List<Integer> attendees = courseListValidations.getAllAttendees(course.getId());

                    loadAllCourseAttendees(attendees);

                    courseAttendeesForm.source = "My Courses";

                    courseAttendeesForm.setVisible(true);
                    myCoursesForm.setVisible(false);
                }
            }
        });

        courseAttendeesForm.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (courseAttendeesForm.source.equals("My Courses")) {
                    myCoursesForm.setVisible(true);
                } else {
                    coursesForm.setVisible(true);
                }

                courseAttendeesForm.setVisible(false);
            }
        });


        coursesForAddingCommentsForm.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashboardForm.setVisible(true);
                coursesForAddingCommentsForm.setVisible(false);

            }
        });

        coursesForAddingCommentsForm.getAddCommentsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (coursesForAddingCommentsForm.getCoursesTable().getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a course");
                } else {
                    int row = coursesForAddingCommentsForm.getCoursesTable().getSelectedRow();
                    String courseName = (String) coursesForAddingCommentsForm.getCoursesTable().getValueAt(row, 1);

                    commentsForm.getCourseNameTextField().setText(courseName);
                    commentsForm.getCourseNameTextField().setEnabled(false);

                    commentsForm.getCommentsTextArea().setText("");

                    commentsForm.setVisible(true);
                    coursesForAddingCommentsForm.setVisible(false);
                }
            }
        });

        coursesForAddingCommentsForm.getViewCommentsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (coursesForAddingCommentsForm.getCoursesTable().getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a course");
                } else {
                    int row = coursesForAddingCommentsForm.getCoursesTable().getSelectedRow();
                    String courseName = (String) coursesForAddingCommentsForm.getCoursesTable().getValueAt(row, 1);

                    Course course = coursesValidations.getCourseByCourseName(courseName);

                    studentsCommentsForm.getCourseNameTextField().setText(courseName);
                    studentsCommentsForm.getCourseNameTextField().setEnabled(false);

                    loadAllCommentsIntoTable(course.getId());

                    studentsCommentsForm.setVisible(true);
                    coursesForAddingCommentsForm.setVisible(false);
                }
            }
        });

        studentsCommentsForm.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coursesForAddingCommentsForm.setVisible(true);
                studentsCommentsForm.setVisible(false);
            }
        });

        commentsForm.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coursesForAddingCommentsForm.setVisible(true);
                commentsForm.setVisible(false);

            }
        });

        commentsForm.getSaveCommentsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = commentsForm.getCourseNameTextField().getText().trim();
                String courseComments = commentsForm.getCommentsTextArea().getText().trim();

                Course course = coursesValidations.getCourseByCourseName(courseName);
                Student student = studentValidations.getStudentByUsername(theUsername);

                CourseListItem courseListItem = new CourseListItem();
                courseListItem.setCourseID(course.getId());
                courseListItem.setStudentID(student.getId());
                courseListItem.setComments(courseComments);

                if (courseListValidations.addCommentsToACourse(courseListItem)) {
                    JOptionPane.showMessageDialog(null, "Comments saved for this course successfully");
                    coursesForAddingCommentsForm.setVisible(true);
                    commentsForm.setVisible(false);

                }

            }
        });

        allStudentsForm.getAddFriendButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (allStudentsForm.getStudentsTable().getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a student");
                } else {
                    int row = allStudentsForm.getStudentsTable().getSelectedRow();
                    int studentID = (Integer) allStudentsForm.getStudentsTable().getValueAt(row, 0);

                    Student myself = studentValidations.getStudentByUsername(theUsername);

                    if (friendsValidations.addStudentsAsFriends(myself.getId(), studentID)) {
                        JOptionPane.showMessageDialog(null, "Student added as your friend successfully");

                    }
                }

            }
        });

        allStudentsForm.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashboardForm.setVisible(true);
                allStudentsForm.setVisible(false);

            }
        });

        friendsForm.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashboardForm.setVisible(true);
                friendsForm.setVisible(false);
            }
        });

        friendsForm.getSeeFriendCoursesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (friendsForm.getFriendsTable().getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a friend");
                } else {
                    int row = friendsForm.getFriendsTable().getSelectedRow();
                    int studentID = (Integer) friendsForm.getFriendsTable().getValueAt(row, 0);

                    List<Course> friendCourses = coursesValidations.getAllStudentCourses(studentID);

                    loadFriendsCoursesIntoTable(friendCourses);

                    friendCourseForm.setVisible(true);
                    friendsForm.setVisible(false);
                }
            }
        });

        friendCourseForm.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                friendsForm.setVisible(true);
                friendCourseForm.setVisible(false);

            }
        });
    }

    private static void addCourseToStudentCourses(Course selectedCourse) {
        CourseListItem item = new CourseListItem();
        Student student = studentValidations.getStudentByUsername(theUsername);
        int studentID = student.getId();

        item.setStudentID(studentID);
        item.setCourseID(selectedCourse.getId());
        item.setComments("");

        courseListValidations.assignCourseToStudent(item, student.getProgramID());
    }

    private static void loadFriendsCoursesIntoTable(List<Course> friendCourses) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Timings");
        model.addColumn("Semester");
        model.addColumn("Type");
        model.addColumn("Program");

        for (Course course : friendCourses) {
            model.addRow(new Object[]{course.getId(), course.getCourseName(), course.getCourseTime(),
                    course.getSemesterType(), course.getCourseType(), course.getProgramName()});

        }

        friendCourseForm.getFriendCoursesTable().setModel(model);
    }

    private static void loadAllFriends() {
        int userID = studentValidations.getStudentIDByUsername(theUsername);
        List<Integer> friendsID = friendsValidations.getAllFriends(userID);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Age");
        model.addColumn("Program");

        for (Integer integer : friendsID) {
            Student friend = studentValidations.getStudentByID(integer);
            String programName = programValidations.getProgramNameByID(friend.getProgramID());

            model.addRow(new Object[]{friend.getId(), friend.getFirstName() + " " + friend.getLastName(),
                    friend.getAge(), programName});

        }

        friendsForm.getFriendsTable().setModel(model);
    }

    private static void addAllStudents() {
        List<Student> students = studentValidations.getAllStudents(theUsername);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Student Name");
        model.addColumn("Age");
        model.addColumn("Program");

        for (Student student : students) {
            String programName = programValidations.getProgramNameByID(student.getProgramID());

            model.addRow(new Object[]{student.getId(), student.getFirstName() + " " + student.getLastName(),
                    student.getAge(), programName});

        }

        allStudentsForm.getStudentsTable().setModel(model);

    }

    private static void loadAllCommentsIntoTable(int id) {
        List<CourseListItem> items = courseListValidations.getAllCommentsOfCourse(id);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Student Name");
        model.addColumn("Comment");

        for (CourseListItem item : items) {
            Student student = studentValidations.getStudentByID(item.getStudentID());

            model.addRow(new Object[]{student.getFirstName() + " " + student.getLastName(), item.getComments()});

        }

        studentsCommentsForm.getCommentsTable().setModel(model);
    }

    private static void setCoursesToCommentsTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Timings");
        model.addColumn("Semester");
        model.addColumn("Type");
        model.addColumn("Program");

        loadMyCourses();

        for (Course course : studentCourses) {
            model.addRow(new Object[]{course.getId(), course.getCourseName(), course.getCourseTime(),
                    course.getSemesterType(), course.getCourseType(), course.getProgramName()});

        }

        coursesForAddingCommentsForm.getCoursesTable().setModel(model);
    }

    private static void loadAllCourseAttendees(List<Integer> attendees) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Student ID");
        model.addColumn("Student Name");

        for (Integer studentID : attendees) {
            Student student = studentValidations.getStudentByID(studentID);

            model.addRow(new Object[]{student.getId(), student.getFirstName() + " " + student.getLastName()});

        }

        courseAttendeesForm.getAttendeesTable().setModel(model);

    }

    private static void loadMyCourses() {
        int studentID = studentValidations.getStudentIDByUsername(theUsername);
        studentCourses = coursesValidations.getAllStudentCourses(studentID);

    }

    private static void addMyCoursesToTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Timings");
        model.addColumn("Type");
        model.addColumn("Location");
        model.addColumn("Program");

        for (Course course : studentCourses) {
            model.addRow(new Object[]{course.getId(), course.getCourseName(), course.getCourseTime(),
                    course.getCourseType(), course.getCourseLectureHall(), course.getProgramName()});

        }

        myCoursesForm.getMyCoursesTable().setModel(model);
    }

    private static void resetEditProfileForm() {
        editProfileForm.getAgeTextField().setText("");
        editProfileForm.getFirstNameTextField().setText("");
        editProfileForm.getLastNameTextField().setText("");

    }

    private static void setCoursesToTable() {
        List<Course> courses = ProgramRun.coursesValidations.getAllCourses();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Timings");
        model.addColumn("Location");
        model.addColumn("Type");
        model.addColumn("Program");

        for (Course course : courses) {
            model.addRow(new Object[]{course.getId(), course.getCourseName(), course.getCourseTime(),
                    course.getCourseLectureHall(), course.getCourseType(), course.getProgramName()});

        }

        coursesForm.getCoursesTable().setModel(model);

    }

    private static void resetLoginForm() {
        loginForm.getPasswordField().setText("");
        loginForm.getUsernameTextfield().setText("");

    }

    private static void initialize() {
        loginForm = new LoginForm();
        dashboardForm = new MainMenuForm();
        coursesForm = new AllCoursesForm();
        editProfileForm = new EditProfileForm();
        myCoursesForm = new MyCoursesForm();
        courseAttendeesForm = new CourseAttendeesForm();
        coursesForAddingCommentsForm = new CoursesForAddingCommentsForm();
        commentsForm = new CommentsForm();
        studentsCommentsForm = new StudentsCommentsForm();
        allStudentsForm = new AllStudentsForm();
        friendsForm = new FriendsForm();
        friendCourseForm = new FriendCourseForm();

        studentValidations = new StudentValidations();
        coursesValidations = new CoursesValidations();
        courseListValidations = new CourseListValidations();
        programValidations = new ProgramValidations();
        friendsValidations = new FriendsValidations();

    }

    private static void setProperties() {
        loginForm.setResizable(false);
        loginForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        dashboardForm.setResizable(false);
        dashboardForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        coursesForm.setResizable(false);
        coursesForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        editProfileForm.setResizable(false);
        editProfileForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        myCoursesForm.setResizable(false);
        myCoursesForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        courseAttendeesForm.setResizable(false);
        courseAttendeesForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        coursesForAddingCommentsForm.setResizable(false);
        coursesForAddingCommentsForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        commentsForm.setResizable(false);
        commentsForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        studentsCommentsForm.setResizable(false);
        studentsCommentsForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        allStudentsForm.setResizable(false);
        allStudentsForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        friendsForm.setResizable(false);
        friendsForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        friendCourseForm.setResizable(false);
        friendCourseForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
