package com.urfu.services;

import com.urfu.entities.ControlAction;
import com.urfu.entities.MarkScale;
import com.urfu.repositories.ControlActionRepository;
import com.urfu.repositories.MarkScaleRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author aperminov
 * 02.03.2020
 */
@Service
public class MarkService {

    @Autowired
    private MarkScaleRepository markScaleRepository;

    @Autowired
    private ControlActionRepository controlActionRepository;

    public String getMarkFromScore(BigDecimal score, ControlAction controlAction) {
        boolean test = IterableUtils.toList(controlActionRepository.getTestControlActions()).contains(controlAction);
        MarkScale scale = markScaleRepository.getMarkScaleByScoreAndControl(score.intValue(), test).iterator().next();

        String result = scale.getMark().getTitle();

        return result;
    }
}
