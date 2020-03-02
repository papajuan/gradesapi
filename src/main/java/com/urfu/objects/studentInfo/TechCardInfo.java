package com.urfu.objects.studentInfo;

import com.urfu.objects.disciplines.TechCardDiscipline;
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
public class TechCardInfo {

    private String uuid;
    private int eduYear;
    private String semester;
    Set<TechCardDiscipline> disciplines;
}
