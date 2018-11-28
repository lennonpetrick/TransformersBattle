package com.test.transformerbattle.presentation;

import com.test.transformerbattle.domain.model.Transformer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BattleTest {

    private List<Transformer> mTransformers;

    @Before
    public void setUp() {
        mTransformers = new ArrayList<>(3);
        mTransformers.add(createTransformer("Soundwave", "D", 8,
                9, 2, 6, 7, 5, 6, 10));
        mTransformers.add(createTransformer("Bluestreak", "A", 6,
                6, 7, 9, 5, 2, 9, 7));
        mTransformers.add(createTransformer("Hubcap", "A", 4,
                4, 4, 4, 4, 4, 4, 4));
    }

    @Test
    public void create() {
        Battle.create(mTransformers)
                .test()
                .assertValue(result -> result.getNumberOfBattles() == 1
                        && result.getWinningTeam().get(0).getName().equals("Soundwave")
                        && result.getSurvivors().get(0).getName().equals("Hubcap"));
    }

    private Transformer createTransformer(String name, String team, int strength,
                                          int intelligence, int speed, int endurance,
                                          int rank, int courage, int firepower, int skill) {
        Transformer transformer = new Transformer();
        transformer.setName(name);
        transformer.setTeam(team);
        transformer.setStrength(strength);
        transformer.setIntelligence(intelligence);
        transformer.setSpeed(speed);
        transformer.setEndurance(endurance);
        transformer.setRank(rank);
        transformer.setCourage(courage);
        transformer.setFirepower(firepower);
        transformer.setSkill(skill);

        return transformer;
    }

}