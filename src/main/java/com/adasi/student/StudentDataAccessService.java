package com.adasi.student;


import com.adasi.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class StudentDataAccessService {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Student> selectAllStudents() {
        String sql = "SELECT student_id, "+
                     "first_name, "+
                     "last_name, "+
                     "email, "+
                     "gender "+
                     "from student";

        return jdbcTemplate.query(sql, mapStudentFromDb());
    }

    private RowMapper<Student> mapStudentFromDb() {
        return (resultSet, i) -> {
            var student = new Student(UUID.fromString(resultSet.getString("student_id")),
                                      resultSet.getString("first_name"),
                                      resultSet.getString("last_name"),
                                      resultSet.getString("email"),
                                      Student.Gender.valueOf(resultSet.getString("gender")));
           return  student;
        };
    }

    int insertStudent(UUID studentId, Student student) {

        String q = " " +
                    "INSERT INTO student (student_id, first_name, last_name, email, gender)"+
                    "VALUES (?, ?, ?, ?, ?::gender)";

        return jdbcTemplate.update(q, studentId,
                                        student.getFirstName(),
                                        student.getLastName(),
                                        student.getEmail(),
                                        student.getGender().name().toUpperCase());
    }

     boolean isEmailExists(String email){
        String q= "select exists ( select 1 from student where email = ?)";

        return jdbcTemplate.queryForObject(q,new Object[] {email},((resultSet, i) -> resultSet.getBoolean(1)));
    }

    public List<StudentCourse> selectAllStudentCourses(UUID studentId) {
        String sql = "SELECT course_id, "+
                            "student_id, "+
                            "name, "+
                            "description, "+
                            "department, "+
                            "teacher_name, "+
                            "start_date, "+
                            "end_date, "+
                            "grade "+
                            "from student " +
                "join student_course using (student_id) " +
                "join course using (course_id) " +
                "where student_id = ?";

        return jdbcTemplate.query(sql,new Object[] {studentId}, mapStudentCoursesFromDb());
    }


    private RowMapper<StudentCourse> mapStudentCoursesFromDb() {
        return (resultSet, i) -> {
            var studentCourse = new StudentCourse(UUID.fromString(resultSet.getString("student_id")),
                                            UUID.fromString(resultSet.getString("course_id")),
                                            resultSet.getString("name"),
                                            resultSet.getString("department"),
                                            resultSet.getString("description"),
                                            resultSet.getString("teacher_name"),
                                            resultSet.getDate("start_date").toLocalDate(),
                                            resultSet.getDate("end_date").toLocalDate(),
                                            Optional.ofNullable(resultSet.getString("grade"))
                                                    .map(Integer::parseInt)
                                                    .orElse(null));
            return  studentCourse;
        };
    }


}
