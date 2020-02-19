package com.urfu.objects.studentInformation;

import com.urfu.objects.exportDisciplines.ExportDiscipline;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author urfu
 * 31.01.2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentInformation {

    private String uuid;
    private int eduYear;
    private String semester;
}
