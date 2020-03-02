package com.urfu.objects.studentInfo;

import com.urfu.objects.disciplines.TechCardDiscipline;
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
public class DisciplineInfo {

    private String uuid;
    private int eduYear;
    private String semester;
    private TechCardDiscipline discipline;
}
