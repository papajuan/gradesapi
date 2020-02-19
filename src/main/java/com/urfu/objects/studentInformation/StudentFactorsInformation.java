package com.urfu.objects.studentInformation;

import com.urfu.objects.exportDisciplines.ExportDiscipline;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * @author papajuan
 * 19.02.2020
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class StudentFactorsInformation extends StudentInformation{

    @Getter @Setter
    Set<ExportDiscipline> disciplines;

    public StudentFactorsInformation(String uuid, int eduYear, String semester, Set<ExportDiscipline> disciplines) {
        super(uuid, eduYear, semester);
        this.disciplines = disciplines;
    }
}
