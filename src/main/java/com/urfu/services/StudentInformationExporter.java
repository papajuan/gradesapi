package com.urfu.services;

import com.urfu.entities.*;
import com.urfu.objects.*;
import com.urfu.objects.exportAttestations.Attestation;
import com.urfu.objects.exportDisciplines.ExportDiscipline;
import com.urfu.objects.studentInformation.StudentDisciplineScoresInformation;
import com.urfu.objects.studentInformation.StudentFactorsInformation;
import com.urfu.objects.studentInformation.StudentInformation;
import com.urfu.repositories.*;
import com.urfu.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public StudentFactorsInformation getStudentFactorsInformation(String studentId, int eduYear, String semester) {
        Set<ExportDiscipline> exportDisciplines = listExportDisciplines(findAgreedDisciplineLoads(studentId, eduYear, semester), studentId);

        return new StudentFactorsInformation(studentId, eduYear, semester, exportDisciplines);
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

    private Set<Attestation> listAttestations(TechnologyCardType technologyCardType, TechnologyCardFactors factors,
                                              String studentId, DisciplineLoad disciplineLoad) {
        Set<Attestation> result = new HashSet<>();

        for(TechnologyCardFactorsType factorsType : TechnologyCardFactorsType.factorsTypes) {
            boolean isIntermediate = factorsType == TechnologyCardFactorsType.intermediate;
            double factor = isIntermediate
                    ? factors.getIntermediate().setScale(2, RoundingMode.CEILING).doubleValue()
                    : factors.getCurrent().setScale(2, RoundingMode.CEILING).doubleValue();

            List<AttestationControl> currentControls = listControls(technologyCardType, studentId, disciplineLoad, isIntermediate);
            Attestation currentAttestation = new Attestation(factorsType, factor, currentControls);

            result.add(currentAttestation);
        }

        return result;
    }

    private Set<DisciplineEvent> listDisciplineEvents(DisciplineLoad disciplineLoad, String studentId) {
        Set<DisciplineEvent> result = new HashSet<>();

        for(TechnologyCard technologyCard : technologyCardRepository.findAllByDisciplineLoad(disciplineLoad)) {
            TechnologyCardType technologyCardType = TechnologyCardType.valueOf(technologyCard.getTechnologyCardType());
            TechnologyCardFactors factors = technologyCardFactorsRepository
                    .findByTypeAndDisciplineLoad(technologyCardType.name(), technologyCard.getDisciplineLoad()).get();

            double totalFactor = factors.getTotal().setScale(2, RoundingMode.CEILING).doubleValue();

            Optional<ExamList> examList = examListRepository.findByStudentAndTechCard(studentId, technologyCard);
            boolean isTestBeforeExam = examList.isPresent() && examList.get().isTestBeforeExam();

            Set<Attestation> attestations = listAttestations(technologyCardType, factors, studentId, disciplineLoad);

            DisciplineEvent disciplineEvent =
                    new DisciplineEvent(technologyCardType, technologyCardType.getTitle(), totalFactor, isTestBeforeExam, attestations);
            result.add(disciplineEvent);
        }

        return result;
    }

    private Set<ExportDiscipline> listExportDisciplines(Iterable<DisciplineLoad> agreedDisciplineLoads, String studentId) {
        Set<ExportDiscipline> result = new HashSet<>();

        for(DisciplineLoad disciplineLoad : agreedDisciplineLoads) {
            String disciplineLoadId = disciplineLoad.getDisciplineLoadId();
            String disciplineTitle = disciplineLoad.getDisciplineCatalogItem().getTitle();
            String disciplineId = disciplineLoad.getDisciplineCatalogItem().getDisciplineCatalogItemId();
            Set<DisciplineEvent> disciplineEvents = listDisciplineEvents(disciplineLoad, studentId);

            if(Util.newRegisterDisciplineTitles.contains(disciplineTitle.toLowerCase()))
                disciplineTitle = techGroupRepository.getTitleByDisciplineLoad(disciplineLoad, studentId).get();

            ExportDiscipline exportDiscipline = new ExportDiscipline(disciplineLoadId, disciplineTitle, disciplineId, disciplineEvents);
            result.add(exportDiscipline);
        }

        return result;
    }

    /**
     * Gets scores information.
     *
     * @param factorsInformation
     *         the factors information
     * @param disciplineId
     *         the discipline id
     *
     * @return the scores information
     */
    public StudentDisciplineScoresInformation getScoresInformation(StudentFactorsInformation factorsInformation, String disciplineId) {
        String studentId = factorsInformation.getUuid();
        int eduYear = factorsInformation.getEduYear();
        String semester = factorsInformation.getSemester();

        ExportDiscipline discipline = listExportDisciplines(findAgreedDisciplineLoads(studentId, eduYear, semester), studentId)
                .stream().filter(exportDiscipline -> exportDiscipline.getId().equals(disciplineId)).findFirst().get();

        return new StudentDisciplineScoresInformation(studentId, eduYear, semester, discipline);
    }
}
