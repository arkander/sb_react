package com.adasi.student;


import com.adasi.exception.ApiRequestException;
import com.adasi.validation.EmailValidation;
import com.adasi.validation.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentDataAccessService studentDataAccessService;

    private final EmailValidator emailValidator;

    @Autowired
    public StudentService(StudentDataAccessService studentDataAccessService, EmailValidator emailValidator) {
        this.studentDataAccessService = studentDataAccessService;
        this.emailValidator = emailValidator;
    }

    List<Student> getAllStudents(){
        return this.studentDataAccessService.selectAllStudents();
    }

    void addNewStudent(UUID studentId, Student student){
        UUID uuid = Optional.ofNullable(studentId).orElse(UUID.randomUUID());
        if(!emailValidator.test(student.getEmail())){
            throw  new ApiRequestException("O email informado não é valido");
        }
        if(studentDataAccessService.isEmailExists(student.getEmail())){
            throw  new ApiRequestException("O email informado não é valido");
        }
        studentDataAccessService.insertStudent(uuid, student);
    }

    void addNewStudent( Student student){
        addNewStudent(null, student);
    }
}
