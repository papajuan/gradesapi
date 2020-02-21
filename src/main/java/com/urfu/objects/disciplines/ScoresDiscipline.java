package com.urfu.objects.disciplines;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.urfu.objects.disciplineEvents.ScoresDisciplineEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author aperminov
 * 19.02.2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoresDiscipline {

    private String id;
    private String title;
    private BigDecimal totalCalculatedScore;
    Set<ScoresDisciplineEvent> events;

    @JsonIgnore
    private String titleId;
}
