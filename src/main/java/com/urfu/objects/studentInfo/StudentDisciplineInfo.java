package com.urfu.objects.studentInfo;

import com.urfu.objects.disciplines.TechnologyCardDiscipline;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author aperminov
 * 02.03.2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDisciplineInfo {

    private String uuid;
    private int eduYear;
    private String semester;
    private TechnologyCardDiscipline discipline;
}
