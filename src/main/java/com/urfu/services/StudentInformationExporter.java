package com.urfu.services;

import com.urfu.entities.*;
import com.urfu.objects.*;
import com.urfu.objects.disciplineEvents.ScoresDisciplineEvent;
import com.urfu.objects.disciplineEvents.TechnologyCardDisciplineEvent;
import com.urfu.objects.disciplines.TechnologyCardDiscipline;
import com.urfu.objects.disciplines.ScoresDiscipline;
import com.urfu.objects.exportAttestations.ScoresAttestation;
import com.urfu.objects.exportAttestations.TechnologyCardAttestation;
import com.urfu.objects.studentInfo.StudentScoresInfo;
import com.urfu.objects.studentInfo.StudentTechCardInfo;
import com.urfu.repositories.*;
import com.urfu.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author aperminov
 * 19.02.2020
 */
@Service
public class StudentInformationExporter {

    @Autowired
    ExamListRepository examListRepository;

    @Autowired
    StudentTotalMarkRepository studentTotalMarkRepository;

    @Autowired
    TechnologyCardFactorsRepository technologyCardFactorsRepository;

    @Autowired
    TechnologyCardRepository technologyCardRepository;

    @Autowired
    TechnologyCardSettingRepository technologyCardSettingRepository;

    @Autowired
    TechGroupRepository techGroupRepository;

    /**
     * Gets student factors information.
     *
     * @param studentId
     *         the student id
     * @param eduYear
     *         the edu year
     * @param semester
     *         the semester
     *
     * @return the student factors information
     */
    public StudentTechCardInfo getStudentFactorsInformation(String studentId, int eduYear, String semester) {
        Set<TechnologyCardDiscipline> technologyCardDisciplines = listFactorsDisciplines(findAgreedDisciplineLoads(studentId, eduYear, semester), studentId);

        return new StudentTechCardInfo(studentId, eduYear, semester, technologyCardDisciplines);
    }

    private Iterable<DisciplineLoad> findAgreedDisciplineLoads(String studentId, int eduYear, String semester) {
        String termType = Util.getSemester(semester);

        Iterable<DisciplineLoad> allDisciplineLoads = studentTotalMarkRepository
                .findAllDisciplineLoadByStudentYearSemesterInOldRegister(studentId, eduYear, termType);

        return technologyCardSettingRepository.findDisciplineLoadsInAgreedTechnologyCards(allDisciplineLoads);
    }

    private List<AttestationControl> listControls(TechnologyCardType type, String studentId,
                                                  DisciplineLoad disciplineLoad, boolean isIntermediate) {
        Iterable<TechnologyCard> cards = studentTotalMarkRepository
                .findTechnologyCardsByTypeStudentAndDisciplineLoad(type.name(), studentId, disciplineLoad, isIntermediate);
        List<AttestationControl> result = new ArrayList<>();

        for(TechnologyCard card : cards)
            result.add(new AttestationControl(card.getControlAction(), card.getMaxValue()));

        return result;
    }

    private Set<TechnologyCardAttestation> listTechnologyCardAttestations(TechnologyCardType technologyCardType, TechnologyCardFactors factors,
                                                            String studentId, DisciplineLoad disciplineLoad) {
        Set<TechnologyCardAttestation> result = new HashSet<>();

        for(TechnologyCardFactorsType factorsType : TechnologyCardFactorsType.factorsTypes) {
            boolean isIntermediate = factorsType == TechnologyCardFactorsType.intermediate;
            BigDecimal factor = isIntermediate
                    ? factors.getIntermediate().setScale(2, RoundingMode.DOWN)
                    : factors.getCurrent().setScale(2, RoundingMode.DOWN);

            List<AttestationControl> currentControls = listControls(technologyCardType, studentId, disciplineLoad, isIntermediate);
            TechnologyCardAttestation currentTechnologyCardAttestation = new TechnologyCardAttestation(factorsType, factor, currentControls);

            result.add(currentTechnologyCardAttestation);
        }

        return result;
    }

    private Set<TechnologyCardDisciplineEvent> listDisciplineEvents(DisciplineLoad disciplineLoad, String studentId) {
        Set<TechnologyCardDisciplineEvent> result = new HashSet<>();

        for(TechnologyCard technologyCard : technologyCardRepository.findAllByDisciplineLoad(disciplineLoad)) {
            TechnologyCardType technologyCardType = TechnologyCardType.valueOf(technologyCard.getTechnologyCardType());
            TechnologyCardFactors factors = technologyCardFactorsRepository
                    .findByTypeAndDisciplineLoad(technologyCardType.name(), technologyCard.getDisciplineLoad()).get();

            BigDecimal totalFactor = factors.getTotal().setScale(2, RoundingMode.DOWN);

            Optional<ExamList> examList = examListRepository.findByStudentAndTechCard(studentId, technologyCard);
            boolean isTestBeforeExam = examList.isPresent() && examList.get().isTestBeforeExam();

            Set<TechnologyCardAttestation> technologyCardAttestations = listTechnologyCardAttestations(technologyCardType, factors, studentId, disciplineLoad);

            TechnologyCardDisciplineEvent technologyCardDisciplineEvent =
                    new TechnologyCardDisciplineEvent(technologyCardType, technologyCardType.getTitle(), totalFactor, isTestBeforeExam, technologyCardAttestations);
            result.add(technologyCardDisciplineEvent);
        }

        return result;
    }

    private Set<TechnologyCardDiscipline> listFactorsDisciplines(Iterable<DisciplineLoad> agreedDisciplineLoads, String studentId) {
        Set<TechnologyCardDiscipline> result = new HashSet<>();

        for(DisciplineLoad disciplineLoad : agreedDisciplineLoads) {
            String disciplineLoadId = disciplineLoad.getDisciplineLoadId();
            String disciplineTitle = disciplineLoad.getDisciplineCatalogItem().getTitle();
            String disciplineId = disciplineLoad.getDisciplineCatalogItem().getDisciplineCatalogItemId();
            Set<TechnologyCardDisciplineEvent> technologyCardDisciplineEvents = listDisciplineEvents(disciplineLoad, studentId);

            if(Util.newRegisterDisciplineTitles.contains(disciplineTitle.toLowerCase()))
                disciplineTitle = techGroupRepository.getTitleByDisciplineLoad(disciplineLoad, studentId).get();

            TechnologyCardDiscipline exportDiscipline = new TechnologyCardDiscipline(disciplineLoadId, disciplineTitle, disciplineId, technologyCardDisciplineEvents);

            result.add(exportDiscipline);
        }

        return result;
    }

    /**
     * @param techCardInfo
     * @param disciplineId
     *
     * @return
     *
     * @throws Exception
     */
    public StudentScoresInfo getScoresInfo(StudentTechCardInfo techCardInfo, String disciplineId) throws Exception {
        for(TechnologyCardDiscipline techCardDiscipline : techCardInfo.getDisciplines()) {
            if(techCardDiscipline.getId().equals(disciplineId)) {
                String studentId = techCardInfo.getUuid();
                String semester = techCardInfo.getSemester();
                int eduYear = techCardInfo.getEduYear();
                Set<ScoresDisciplineEvent> scoresDisciplineEvents =  listScoresDisciplineEvents(techCardDiscipline.getEvents());
                BigDecimal disciplineTotalScore = getDisciplineTotalScore(scoresDisciplineEvents).setScale(2, RoundingMode.DOWN);

                ScoresDiscipline scoresDiscipline = new ScoresDiscipline(disciplineId, techCardDiscipline.getTitle(),
                        disciplineTotalScore, scoresDisciplineEvents, techCardDiscipline.getTitleId());

                return new StudentScoresInfo(studentId, eduYear, semester, scoresDiscipline);
            }
        }

        return new StudentScoresInfo();
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
            Set<ScoresAttestation> scoresAttestations = listScoresAttestations(techCardEvent.getTechnologyCardAttestations());
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
            double studentScore = control.getStudentScore();

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
