package com.urfu.utils;

import com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author aperminov
 * 03.02.2020
 */
public class Util {

    public static final Set<String> newRegisterDisciplineTitles = ImmutableSet.of(
            "прикладная физическая культура",
            "физическая культура",
            "иностранный язык");

    public static final String AUTUMN = "Осенний";
    public static final String SPRING = "Весенний";

    public static String getSemester(String semester) {
        return semester.toLowerCase().equals("autumn") ? Util.AUTUMN : Util.SPRING;
    }

    public static String getMarkFromScore(BigDecimal score) {

        if(score.compareTo(BigDecimal.valueOf(Long.valueOf(80))) >= 0)
            return "отл.";
        if(score.compareTo(BigDecimal.valueOf(Long.valueOf(60))) >= 0 && score.compareTo(BigDecimal.valueOf(Long.valueOf(80))) < 0)
            return "хор.";
        if(score.compareTo(BigDecimal.valueOf(Long.valueOf(40))) >= 0 && score.compareTo(BigDecimal.valueOf(Long.valueOf(60))) < 0)
            return "уд.";

        return "неуд.";
    }
}
