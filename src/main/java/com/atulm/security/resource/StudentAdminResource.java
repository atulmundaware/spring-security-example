package com.atulm.security.resource;

import com.atulm.security.vo.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin/student")
public class StudentAdminResource {

    private static final String STUDENT_NOT_FOUND_FOR_GIVEN_ID = "Student not found for given ID";
    public static List<Student> STUDENT_LIST = new ArrayList<>(Arrays.asList(new Student(1,"Test Name")
            , new Student(2,"Test2 Name")
            , new Student(3,"Test3 Name")));

    @GetMapping(path = "{studentId}")
    public Student getStudentById(@PathVariable("studentId") Integer studentId) {
        return STUDENT_LIST.stream().filter(s-> studentId.equals(s.getId()))
                .findFirst()
                .orElseThrow(()->new IllegalStateException(STUDENT_NOT_FOUND_FOR_GIVEN_ID));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
    public List<Student>  getAllStudents() {
        return STUDENT_LIST;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerStudent(@RequestBody Student student) {
        STUDENT_LIST.add(student);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('student:write')")
    public boolean deleteStudent(@PathVariable("studentId") Integer studentId) {
        return STUDENT_LIST.remove(STUDENT_LIST.stream().filter(s-> studentId.equals(s.getId()))
                .findFirst()
                .orElseThrow(()->new IllegalStateException(STUDENT_NOT_FOUND_FOR_GIVEN_ID)));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('student:write')")
    public List<Student> updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        Student requestedStudent = STUDENT_LIST.stream().filter(s-> studentId.equals(s.getId()))
                                    .findFirst()
                                    .orElseThrow(()->new IllegalStateException(STUDENT_NOT_FOUND_FOR_GIVEN_ID));
        requestedStudent.setFullName(student.getFullName());
        return STUDENT_LIST;
    }

}
