package com.urfu.utils;

import com.google.common.collect.ImmutableSet;

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
}
