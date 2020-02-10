package com.urfu.objects;

import com.google.common.collect.ImmutableSet;
import com.urfu.utils.ITitled;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
public enum TechnologyCardFactorsType implements ITitled {

    current("текущая"), intermediate("промежуточная"), total("итоговая");

    @Getter
    private final String title;

    public static Set<TechnologyCardFactorsType> factorsTypes = ImmutableSet.of(
            current, intermediate
    );
}
