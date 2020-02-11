package com.urfu.controllers;

import com.urfu.entities.*;
import com.urfu.objects.*;
import com.urfu.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.urfu.repositories.*;

import java.math.RoundingMode;
import java.util.*;

/**
 * @author urfu
 * 28.01.2020
 */
@RestController
public class GradesController {

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

    @GetMapping(path = "/technologyCards", produces = "application/json")
    public @ResponseBody
    StudentFactorsInformation getStudentFactors(@RequestParam String studentId,
                                                @RequestParam int eduYear,
                                                @RequestParam String semester) {

        String termType = semester.equals("autumn") ? Util.AUTUMN : Util.SPRING;

        Iterable<DisciplineLoad> allDisciplineLoads = studentTotalMarkRepository
                .findAllDisciplineLoadByStudentYearSemesterInOldRegister(studentId, eduYear, termType);

        Iterable<DisciplineLoad> agreedDisciplineLoads = technologyCardSettingRepository
                .findDisciplineLoadsInAgreedTechnologyCards(allDisciplineLoads);

        Set<ExportDiscipline> exportDisciplines = listExportDisciplines(agreedDisciplineLoads, studentId);

        return new StudentFactorsInformation(studentId, eduYear, semester, exportDisciplines);
    }

    private List<AttestationControl> listControls(TechnologyCardType type, TechnologyCardFactorsType factorsType, String studentId, DisciplineLoad disciplineLoad, boolean isIntermediate) {
        Iterable<TechnologyCard> cards = studentTotalMarkRepository.findTechnologyCardsByTypeStudentAndDisciplineLoad(type.name(), studentId, disciplineLoad, isIntermediate);
        List<AttestationControl> result = new ArrayList<>();

        for(TechnologyCard card : cards)
            result.add(new AttestationControl(card.getControlAction(), card.getMaxValue()));

        return result;
    }

    private Set<Attestation> listAttestations(TechnologyCardType technologyCardType, TechnologyCardFactors factors, String studentId, DisciplineLoad disciplineLoad) {
        Set<Attestation> result = new HashSet<>();

        for(TechnologyCardFactorsType factorsType : TechnologyCardFactorsType.factorsTypes) {
            boolean isIntermediate = factorsType == TechnologyCardFactorsType.intermediate;
            double factor = isIntermediate
                    ? factors.getIntermediate().setScale(2, RoundingMode.CEILING).doubleValue()
                    : factors.getCurrent().setScale(2, RoundingMode.CEILING).doubleValue();

            List<AttestationControl> currentControls = listControls(technologyCardType, factorsType, studentId, disciplineLoad, isIntermediate);
            Attestation currentAttestation = new Attestation(factorsType, factor, currentControls);

            result.add(currentAttestation);
        }

        return result;
    }

    private Set<DisciplineEvent> listDisciplineEvents(DisciplineLoad disciplineLoad, String studentId) {
        Set<DisciplineEvent> result = new HashSet<>();

        for(TechnologyCard technologyCard : technologyCardRepository.findAllByDisciplineLoad(disciplineLoad)) {
            TechnologyCardType technologyCardType = TechnologyCardType.valueOf(technologyCard.getTechnologyCardType());
            TechnologyCardFactors factors = technologyCardFactorsRepository.findByTypeAndDisciplineLoad(technologyCardType.name(), technologyCard.getDisciplineLoad()).get();

            double totalFactor = factors.getTotal().setScale(2, RoundingMode.CEILING).doubleValue();

            Optional<ExamList> examList = examListRepository.findByStudentAndTechCard(studentId, technologyCard);
            boolean isTestBeforeExam = examList.isEmpty() ? false : examList.get().isTestBeforeExam();

            Set<Attestation> attestations = listAttestations(technologyCardType, factors, studentId, disciplineLoad);

            DisciplineEvent disciplineEvent = new DisciplineEvent(technologyCardType, technologyCardType.getTitle(), totalFactor, isTestBeforeExam, attestations);
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
}
