package com.urfu.objects.studentInfo;

import com.urfu.objects.disciplines.TechnologyCardDiscipline;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author aperminov
 * 31.01.2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentTechCardInfo {

    private String uuid;
    private int eduYear;
    private String semester;
    Set<TechnologyCardDiscipline> disciplines;
}
