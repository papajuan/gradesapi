package com.urfu.controllers;

import com.urfu.objects.StudentFactorsInformation;
import com.urfu.services.FactorsExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author urfu
 * 28.01.2020
 */
@RestController
public class GradesController {

    @Autowired
    FactorsExporter factorsExporter;

    @GetMapping(path = "/technologyCards", produces = "application/json")
    public @ResponseBody
    StudentFactorsInformation getStudentFactors(@RequestParam String studentId,
                                                @RequestParam int eduYear,
                                                @RequestParam String semester) {

        return factorsExporter.getStudentFactorsInformation(studentId, eduYear, semester);
    }
}
