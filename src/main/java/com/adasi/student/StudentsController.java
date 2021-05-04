package com.adasi.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping("students")
public class StudentsController {

    private final StudentService studentService;

    @Autowired
    public StudentsController (StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public List<Student> getAllStudents(){
        return  this.studentService.getAllStudents();
    }

    @PostMapping
    public void addNewStudent(@RequestBody @Valid Student student){
        this.studentService.addNewStudent(student);
    }

    @GetMapping(path = "{studentId}/courses")
    public List<StudentCourse> getAllCoursesForStudent(@PathVariable("studentId") UUID studentId){
        return this.studentService.getAllCoursesForStudent(studentId);
    }



}
