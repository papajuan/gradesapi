package com.urfu.controllers;

import com.urfu.objects.studentInfo.StudentDisciplineInfo;
import com.urfu.objects.studentInfo.StudentScoresInfo;
import com.urfu.objects.studentInfo.TechCardInfo;
import com.urfu.services.DisciplineScoresExporter;
import com.urfu.services.TechCardExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author aperminov
 * 28.01.2020
 */
@RestController
public class GradesController {

    @Autowired
    TechCardExporter techCardExporter;

    @Autowired
    DisciplineScoresExporter disciplineScoresExporter;

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
    TechCardInfo getStudentFactors(@RequestParam String studentId,
                                   @RequestParam int eduYear,
                                   @RequestParam String semester) {

        return techCardExporter.getStudentFactorsInformation(studentId, eduYear, semester);
    }

    /**
     * Принимает информацию по дисциплине студента с проставленными баллами по всем КМ
     * Возвращает посчитанные баллы студента по дисциплине (по промежуточной, текущей аттестации и общий балл)
     *
     * @param disciplineInfo
     *         информация о дисциплине студента с проставленными баллами (studentScores)
     *
     * @return информацию о баллах студента по дисциплине
     */
    @PostMapping(path = "/scores", consumes = "application/json", produces = "application/json")
    public @ResponseBody StudentScoresInfo getStudentScores(@RequestBody StudentDisciplineInfo disciplineInfo) throws Exception {

        return disciplineScoresExporter.getScoresInfo(disciplineInfo);
    }
}
