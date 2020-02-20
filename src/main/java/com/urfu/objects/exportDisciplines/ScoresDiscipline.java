package com.urfu.objects.exportDisciplines;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author aperminov
 * 19.02.2020
 */
@Data
@NoArgsConstructor
public class ScoresDiscipline extends ExportDiscipline {

    private BigDecimal disciplineTotalScore;

    public ScoresDiscipline(ExportDiscipline discipline) {
        super(discipline.getId(), discipline.getTitle(), discipline.getTitleId(), discipline.getEvents());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScoresDiscipline)) return false;
        ScoresDiscipline that = (ScoresDiscipline) o;
        return getId().equals(that.getId()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getTitleId(), that.getTitleId()) &&
                Objects.equals(getDisciplineTotalScore(), that.getDisciplineTotalScore()) &&
                Objects.equals(getEvents(), that.getEvents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getTitleId(), getEvents(), getDisciplineTotalScore());
    }
}
