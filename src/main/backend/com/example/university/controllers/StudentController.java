package com.example.university.controllers;

import com.example.university.model.Course;
import com.example.university.model.Student;
import com.example.university.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin
public class StudentController {
    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    ResponseEntity<?> getAllStudents() {

        List<Student> students = this.studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PostMapping("/students")
    ResponseEntity<?> createStudent(@RequestBody Student student) {
        String result = this.studentService.createStudent(student);
        if(result.equals("Email already taken")) {
            return ResponseEntity.status(HttpStatus.OK).body("Email already taken");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(student);

    }
    @GetMapping("/students/{id}")

    ResponseEntity<?> getSpecificStudent(@PathVariable Long id) {

            Student student = this.studentService.getStudentById(id);
            if(student == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student with id " + id + " does not exist");
            }

            return ResponseEntity.ok(student);


    }

    @DeleteMapping("/students/{id}")

    ResponseEntity<?> deleteStudentById(@PathVariable Long id) {
        this.studentService.deleteStudentById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Student with id  " + id + " has been deleted");
    }
    @DeleteMapping("/students")

    ResponseEntity<?> deleteStudents() {
        this.studentService.deleteStudents();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
