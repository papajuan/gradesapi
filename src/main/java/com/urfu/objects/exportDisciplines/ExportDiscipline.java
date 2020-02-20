package com.urfu.objects.exportDisciplines;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.urfu.objects.DisciplineEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author aperminov
 * 31.01.2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExportDiscipline {

    private String id;
    private String title;

    @JsonIgnore
    private String titleId;

    private Set<DisciplineEvent> events;
}
