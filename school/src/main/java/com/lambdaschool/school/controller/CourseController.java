package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.service.CourseService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "returns all courses", response = Course.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "integer",
                    paramType = "query", value = "Sorting criteria in the format: property(,asc|desc)." +
                    "Default sort order is ascending" + "Multiple sort criteria are supported)")})
    // http://localhost:2019/courses/course/?page=0&size=5
    @GetMapping(value = "/courses", produces = {"application/json"})
    public ResponseEntity<?> listAllCourses(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        ArrayList<Course> myCourses = courseService.findAll(pageable);
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }


    @ApiOperation(value = "returns all courses and their students", response = Course.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "integer",
                    paramType = "query", value = "Sorting criteria in the format: property(,asc|desc)." +
                    "Default sort order is ascending" + "Multiple sort criteria are supported)")})
    @GetMapping(value = "/studcount", produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        return new ResponseEntity<>(courseService.getCountStudentsInCourse(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Deletes a course with the course id", response = Course.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Course Found", response = Course.class),
            @ApiResponse(code = 404, message = "Course Not Found", response = ErrorDetail.class)
    })
    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(@ApiParam(value = "Course Id", required = true, example = "1")
                                              @PathVariable long courseid) {
        courseService.delete(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
