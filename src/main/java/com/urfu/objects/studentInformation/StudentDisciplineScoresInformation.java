package com.urfu.objects.studentInformation;

import com.urfu.objects.exportDisciplines.ExportDiscipline;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author papajuan
 * 19.02.2020
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class StudentDisciplineScoresInformation extends StudentInformation {

    @Getter @Setter
    private ExportDiscipline discipline;

    public StudentDisciplineScoresInformation(String uuid, int eduYear, String semester, ExportDiscipline discipline) {
        super(uuid, eduYear, semester);
        this.discipline = discipline;
    }
}
