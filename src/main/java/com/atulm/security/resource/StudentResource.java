package com.atulm.security.resource;

import com.atulm.security.vo.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentResource {

    private static final List<Student> STUDENT_LIST = Arrays.asList(new Student(1,"Test Name")
            , new Student(2,"Test2 Name")
            , new Student(3,"Test3 Name"));

    @GetMapping(path = "{studentId}")
    public Student getStudentById(@PathVariable("studentId") Integer studentId) {
        return STUDENT_LIST.stream().filter(s-> studentId.equals(s.getId()))
                                    .findFirst()
                                    .orElseThrow(()->new IllegalStateException("Student not found for given ID"));
    }
}
