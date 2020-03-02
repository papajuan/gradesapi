package com.urfu.services;

import com.urfu.entities.TechnologyCardType;
import com.urfu.objects.AttestationControl;
import com.urfu.objects.TechnologyCardFactorsType;
import com.urfu.objects.disciplineEvents.ScoresDisciplineEvent;
import com.urfu.objects.disciplineEvents.TechnologyCardDisciplineEvent;
import com.urfu.objects.disciplines.ScoresDiscipline;
import com.urfu.objects.disciplines.TechnologyCardDiscipline;
import com.urfu.objects.exportAttestations.ScoresAttestation;
import com.urfu.objects.exportAttestations.TechnologyCardAttestation;
import com.urfu.objects.studentInfo.StudentDisciplineInfo;
import com.urfu.objects.studentInfo.StudentScoresInfo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author aperminov
 * 02.03.2020
 */
@Service
public class DisciplineScoresExporter {
    /**
     * @param disciplineInfo
     *
     * @return
     *
     * @throws Exception
     */
    public StudentScoresInfo getScoresInfo(StudentDisciplineInfo disciplineInfo) throws Exception {
        TechnologyCardDiscipline techCardDiscipline = disciplineInfo.getDiscipline();
        String disciplineId = techCardDiscipline.getId();

        String studentId = disciplineInfo.getUuid();
        String semester = disciplineInfo.getSemester();
        int eduYear = disciplineInfo.getEduYear();
        Set<ScoresDisciplineEvent> scoresDisciplineEvents =  listScoresDisciplineEvents(techCardDiscipline.getEvents());
        BigDecimal disciplineTotalScore = getDisciplineTotalScore(scoresDisciplineEvents).setScale(2, RoundingMode.DOWN);

        ScoresDiscipline scoresDiscipline = new ScoresDiscipline(disciplineId, techCardDiscipline.getTitle(),
                disciplineTotalScore, scoresDisciplineEvents, techCardDiscipline.getTitleId());

        return new StudentScoresInfo(studentId, eduYear, semester, scoresDiscipline);
    }

    private BigDecimal getDisciplineTotalScore(Set<ScoresDisciplineEvent> scoresEvents) {
        BigDecimal result = BigDecimal.ZERO;

        for(ScoresDisciplineEvent scoresEvent : scoresEvents)
            result = result.add(scoresEvent.getCalculatedScore());

        return result;
    }

    private Set<ScoresDisciplineEvent> listScoresDisciplineEvents(Set<TechnologyCardDisciplineEvent> techCardEvents) throws Exception {
        Set<ScoresDisciplineEvent> result = new HashSet<>();

        for(TechnologyCardDisciplineEvent techCardEvent : techCardEvents) {
            TechnologyCardType type = techCardEvent.getType();
            String typeTitle = techCardEvent.getTypeTitle();
            BigDecimal totalFactor = techCardEvent.getTotalFactor();
            Set<ScoresAttestation> scoresAttestations = listScoresAttestations(techCardEvent.getAttestations());
            BigDecimal eventCalculatedScore = getCalculatedScore(scoresAttestations, totalFactor);

            ScoresDisciplineEvent scoresDisciplineEvent =
                    new ScoresDisciplineEvent(type, typeTitle, totalFactor, scoresAttestations, eventCalculatedScore);
            result.add(scoresDisciplineEvent);
        }

        return result;
    }

    private Set<ScoresAttestation> listScoresAttestations(Set<TechnologyCardAttestation> techCardAttestations) throws Exception {
        Set<ScoresAttestation> result = new HashSet<>();

        for(TechnologyCardAttestation techCardAttestation : techCardAttestations) {
            TechnologyCardFactorsType type = techCardAttestation.getType();
            BigDecimal factor = techCardAttestation.getFactor();
            BigDecimal calculatedScore = getCalculatedScore(techCardAttestation.getControls(), factor).setScale(2, RoundingMode.DOWN);

            ScoresAttestation scoresAttestation = new ScoresAttestation(type, factor, calculatedScore);
            result.add(scoresAttestation);
        }

        return result;
    }

    private BigDecimal getCalculatedScore(List<AttestationControl> controls, BigDecimal factor) throws Exception {
        BigDecimal result = BigDecimal.ZERO;

        for(AttestationControl control : controls) {
            int maxScore = control.getMaxScore();
            double studentScore = control.getScore();

            if(studentScore <= maxScore)
                result = result.add(BigDecimal.valueOf(studentScore));
            else
                throw new Exception("Введенный балл " + studentScore + " за КМ " + control.getTitle()
                        + " больше " + maxScore);
        }

        return result.multiply(factor);
    }

    private BigDecimal getCalculatedScore(Set<ScoresAttestation> scoresAttestations, BigDecimal totalFactor) throws Exception {
        BigDecimal result = BigDecimal.ZERO;

        for(ScoresAttestation scoresAttestation : scoresAttestations) {
            BigDecimal calculatedAttestationScore = scoresAttestation.getCalculatedScore();
            result = result.add(calculatedAttestationScore);
        }

        return result.multiply(totalFactor);
    }
}
