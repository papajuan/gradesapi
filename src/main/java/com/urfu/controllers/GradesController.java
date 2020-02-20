package com.urfu.controllers;

import com.urfu.objects.StudentInformation;
import com.urfu.services.StudentInformationExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author aperminov
 * 28.01.2020
 */
@RestController
public class GradesController {

    @Autowired
    StudentInformationExporter studentInformationExporter;

    /**
     * Возвращает информацию по всем тех.картам студента согласно дисциплинам.
     *
     * @param studentId
     *         uuid студента
     * @param eduYear
     *         учебный год
     * @param semester
     *         семестр на английском
     *
     * @return информацию по всем тех.картам студента
     */
    @GetMapping(path = "/technologyCards", produces = "application/json")
    public @ResponseBody
    StudentInformation getStudentFactors(@RequestParam String studentId,
                                         @RequestParam int eduYear,
                                         @RequestParam String semester) {

        return studentInformationExporter.getStudentFactorsInformation(studentId, eduYear, semester);
    }

    /**
     * Принимает информацию по тех.картам студента с проставленными баллами по всем КМ и id дисциплины
     * Возвращает посчитанные баллы студента по дисциплине (по промежуточной, текущей аттестации и общий балл)
     *
     * @param factorsInformation
     *         информация о всех тех.картах студента с проставленными баллами (studentScores)
     * @param disciplineId
     *         uuid дисциплины, по которой нужны посчитанные баллы
     *
     * @return информацию о баллах студента по дисциплинам
     */
    @PostMapping(path = "/scores", consumes = "application/json", produces = "application/json")
    public @ResponseBody StudentInformation getStudentScores(@RequestBody StudentInformation factorsInformation,
                                                                             @RequestParam String disciplineId) {

        return studentInformationExporter.getScoresInformation(factorsInformation, disciplineId);
    }
}
