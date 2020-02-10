package com.urfu.objects;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @author urfu
 * 31.01.2020
 */
@Data
@AllArgsConstructor
public class ExportDiscipline {

    private String id;
    private String title;
    private String titleId;
    private Set<DisciplineEvent> events;
}
