package com.adasi.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
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


}
