package com.spring.read_service.controllers;

import com.spring.read_service.dtos.StudentDTO;
import com.spring.read_service.dtos.SubjectDTO;
import com.spring.read_service.services.ReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class ReadController {

    @Autowired
    ReadService readService;

    @GetMapping("/name/{name}")
    public List<StudentDTO> getStudentByName(@PathVariable String name) {
        return readService.getStudentByName(name);
    }
    // Get students by age
    @GetMapping("/age/{age}")
    public List<StudentDTO> getStudentByAge(@PathVariable int age) {
        return readService.getStudentByAge(age);
    }
    // Get students by gender
    @GetMapping("/gender/{gender}")
    public List<StudentDTO> getStudentByGender(@PathVariable String gender) {
        return readService.getStudentByGender(gender);
    }
    // Get all students with pagination
    @GetMapping("/all")
    public Page<StudentDTO> getAllStudents(@RequestParam int page, @RequestParam int size) {
        return readService.getAllStudents(page, size);
    }

    //find student by id
    @GetMapping("/id/{id}")
    public StudentDTO getStudentById(@PathVariable int id){
        return readService.getStudentById(id);
    }


    // Get subjects by name
    @GetMapping("/subjects/{name}")
    public List<SubjectDTO> getSubjectsByName(@PathVariable String name) {
        return readService.findSubjectsByName(name);
    }
}
