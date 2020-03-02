package com.urfu.entities;

import com.google.common.collect.ImmutableSet;
import com.urfu.utils.ITitled;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

/**
 * @author aperminov
 * 03.02.2020
 */
@AllArgsConstructor
public enum TechnologyCardType implements ITitled {

    lecture("лекции"),
    practice("практические занятия"),
    laboratory("лабораторные занятия"),
    intermediate("промежуточная аттестация"),
    courseWork("курсовая работа"),
    courseProject("курсовой проект"),
    betweenCourseProject("междисциплинарный курсовой проект"),
    additionalPractice("Доп. мероприятия"),
    integratedControl("контроль"),
    integratedMark("контроль"),
    reExamination("переаттестация");

    @Getter
    private final String title;

    /**
     * Виды занятий. Лекции, практики или лабораторные
     */
    public static final Collection<TechnologyCardType> actionType = ImmutableSet.of(
            TechnologyCardType.lecture,
            TechnologyCardType.practice,
            TechnologyCardType.laboratory
    );

    /**
     * Все разновидности курсовых. Курсовые работы, курсовые проекты и междисциплинарные курсовые проекты.
     */
    public static final Collection<TechnologyCardType> courseCardType = ImmutableSet.of(
            TechnologyCardType.courseProject,
            TechnologyCardType.courseWork,
            TechnologyCardType.betweenCourseProject);

    /**
     * необходимость расчета итоговой оценки (StudentEmploymentMarks.intermediate) для тек аттестации
     */
    public boolean canCurrentEmploymentMark() {
        return integratedMark != this;
    }

    /**
     * признак интегромероприятий
     */
    public boolean isIntegrated() {
        return this == integratedControl || this == integratedMark;
    }

    /**
     * указывает на промежуточную аттестацию
     */
    public boolean isIntermediate() {
        return intermediate == this
                || courseProject == this
                || courseWork == this
                || betweenCourseProject == this
                || integratedControl == this
                || integratedMark == this;
    }

    /**
     * Указывает что по виду занятия коэффициенты предустановлены
     */
    public boolean isPredefinedFactors() {
        return TechnologyCardType.integratedControl == this
                || TechnologyCardType.integratedMark == this
                || TechnologyCardType.reExamination == this;
    }

    /**
     * Является одной из разновидностей курсовых (курсовая работа/проект/междисциплинарный проект)
     */
    public boolean isCourse(){
        return courseCardType.contains(this);
    }
}
