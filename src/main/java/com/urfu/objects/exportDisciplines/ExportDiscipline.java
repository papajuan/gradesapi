package com.urfu.objects.exportDisciplines;

import com.urfu.objects.DisciplineEvent;
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
public class ExportDiscipline {

    private String id;
    private String title;
    private String titleId;
    private Set<DisciplineEvent> events;
}