package com.urfu.services;

import com.urfu.entities.*;
import com.urfu.objects.AttestationControl;
import com.urfu.objects.TechCardFactorsType;
import com.urfu.objects.disciplineEvents.TechCardDisciplineEvent;
import com.urfu.objects.disciplines.TechCardDiscipline;
import com.urfu.objects.exportAttestations.TechCardAttestation;
import com.urfu.objects.studentInfo.TechCardInfo;
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
public class TechCardExporter {

    @Autowired
    private ExamListRepository examListRepository;

    @Autowired
    private StudentTotalMarkRepository studentTotalMarkRepository;

    @Autowired
    private TechnologyCardFactorsRepository technologyCardFactorsRepository;

    @Autowired
    private TechnologyCardRepository technologyCardRepository;

    @Autowired
    private TechnologyCardSettingRepository technologyCardSettingRepository;

    @Autowired
    private TechGroupRepository techGroupRepository;

    /**
     * Gets student factors information.
     *
     * @param studentId
     *         the student id
     * @param eduYear
     *         the eduyear
     * @param semester
     *         the semester
     *
     * @return the student factors information
     */
    public TechCardInfo getStudentFactorsInformation(String studentId, int eduYear, String semester) {
        Set<TechCardDiscipline> techCardDisciplines =
                listFactorsDisciplines(findAgreedDisciplineLoads(studentId, eduYear, semester), studentId);

        return new TechCardInfo(studentId, eduYear, semester, techCardDisciplines);
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

    private Set<TechCardAttestation> listTechnologyCardAttestations(TechnologyCardType technologyCardType, TechnologyCardFactors factors,
                                                                    String studentId, DisciplineLoad disciplineLoad) {
        Set<TechCardAttestation> result = new HashSet<>();

        for(TechCardFactorsType factorsType : TechCardFactorsType.factorsTypes) {
            boolean isIntermediate = factorsType == TechCardFactorsType.intermediate;
            BigDecimal factor = isIntermediate
                    ? factors.getIntermediate().setScale(2, RoundingMode.DOWN)
                    : factors.getCurrent().setScale(2, RoundingMode.DOWN);

            List<AttestationControl> currentControls = listControls(technologyCardType, studentId, disciplineLoad, isIntermediate);
            TechCardAttestation currentTechCardAttestation = new TechCardAttestation(factorsType, factor, currentControls);

            result.add(currentTechCardAttestation);
        }

        return result;
    }

    private Set<TechCardDisciplineEvent> listDisciplineEvents(DisciplineLoad disciplineLoad, String studentId) {
        Set<TechCardDisciplineEvent> result = new HashSet<>();

        for(TechnologyCard technologyCard : technologyCardRepository.findAllByDisciplineLoad(disciplineLoad)) {
            TechnologyCardType technologyCardType = TechnologyCardType.valueOf(technologyCard.getTechnologyCardType());
            TechnologyCardFactors factors = technologyCardFactorsRepository
                    .findByTypeAndDisciplineLoad(technologyCardType.name(), technologyCard.getDisciplineLoad()).get();

            BigDecimal totalFactor = factors.getTotal().setScale(2, RoundingMode.DOWN);

            Optional<ExamList> examList = examListRepository.findByStudentAndTechCard(studentId, technologyCard);
            boolean isTestBeforeExam = examList.isPresent() && examList.get().isTestBeforeExam();

            Set<TechCardAttestation> techCardAttestations = listTechnologyCardAttestations(technologyCardType, factors, studentId, disciplineLoad);

            TechCardDisciplineEvent techCardDisciplineEvent =
                    new TechCardDisciplineEvent(technologyCardType, technologyCardType.getTitle(), totalFactor, isTestBeforeExam, techCardAttestations);
            result.add(techCardDisciplineEvent);
        }

        return result;
    }

    private Set<TechCardDiscipline> listFactorsDisciplines(Iterable<DisciplineLoad> agreedDisciplineLoads, String studentId) {
        Set<TechCardDiscipline> result = new HashSet<>();

        for(DisciplineLoad disciplineLoad : agreedDisciplineLoads) {
            String disciplineLoadId = disciplineLoad.getDisciplineLoadId();
            String disciplineTitle = disciplineLoad.getDisciplineCatalogItem().getTitle();
            String disciplineId = disciplineLoad.getDisciplineCatalogItem().getDisciplineCatalogItemId();
            Set<TechCardDisciplineEvent> techCardDisciplineEvents = listDisciplineEvents(disciplineLoad, studentId);

            if(Util.newRegisterDisciplineTitles.contains(disciplineTitle.toLowerCase()))
                disciplineTitle = techGroupRepository.getTitleByDisciplineLoad(disciplineLoad, studentId).get();

            TechCardDiscipline exportDiscipline = new TechCardDiscipline(disciplineLoadId, disciplineTitle, disciplineId, techCardDisciplineEvents);

            result.add(exportDiscipline);
        }

        return result;
    }
}
