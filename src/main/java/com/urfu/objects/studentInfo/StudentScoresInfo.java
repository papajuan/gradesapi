package com.urfu.objects.studentInfo;

import com.urfu.objects.disciplines.ScoresDiscipline;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author aperminov
 * 21.02.2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentScoresInfo {

    private String uuid;
    private int eduYear;
    private String semester;
    ScoresDiscipline discipline;
}
