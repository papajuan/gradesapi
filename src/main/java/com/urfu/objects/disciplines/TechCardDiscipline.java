package com.urfu.objects.disciplines;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.urfu.objects.disciplineEvents.TechCardDisciplineEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author aperminov
 * 19.02.2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechCardDiscipline {

    private String id;
    private String title;

    @JsonIgnore
    private String titleId;

    private Set<TechCardDisciplineEvent> events;
}
