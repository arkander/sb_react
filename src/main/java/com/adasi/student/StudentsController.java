package com.adasi.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import java.util.UUID;

@RestController
@RequestMapping("students")
public class StudentsController {

    @GetMapping()
    public List<Student> getAllStudents(){
        return List.of(new Student(UUID.randomUUID(),"Adauto","Fabiano e silva","adasdi@gmail.com", Student.Gender.MALE),
                new Student(UUID.randomUUID(),"Maria","Aparecida e silva","Maria@gmail.com", Student.Gender.FEMALE),
                new Student(UUID.randomUUID(),"João","Victor e silva","JV@gmail.com", Student.Gender.MALE));

//        List<Student> l = new ArrayList<>();
//        l.add( );
//        l.add( );
//        return l;



    }


}
