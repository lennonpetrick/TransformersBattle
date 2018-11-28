package com.test.transformerbattle.presentation;

import com.test.transformerbattle.domain.model.Transformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

public class Battle {

    private static final String AUTOBOT_LEADER = "optimus prime",
                                DECEPTICON_LEADER = "predaking";

    private static final int COURAGE_LIMIT = 4,
                             STRENGTH_LIMIT = 3,
                             SKILL_LIMIT = 3;

    private List<Transformer> mAutobotTeam,
                              mWinningAutobots,
                              mTransformersDestroyed,
                              mDecepticonTeam,
                              mWinningDecepticon;

    public static Single<Result> create(List<Transformer> transformers) {
        return Single.create(emitter -> {
            final Result result = new Battle(transformers)
                    .rank()
                    .start();
            emitter.onSuccess(result);
        });
    }

    private Battle(List<Transformer> transformers) {
        mAutobotTeam = new ArrayList<>();
        mWinningAutobots = new ArrayList<>();
        mDecepticonTeam = new ArrayList<>();
        mWinningDecepticon = new ArrayList<>();
        mTransformersDestroyed = new ArrayList<>();

        for (Transformer transformer : transformers) {
            if (transformer.getTeam().equals("A")) {
                mAutobotTeam.add(transformer);
            } else {
                mDecepticonTeam.add(transformer);
            }
        }
    }

    public Result start() {
        final int autobotSize = mAutobotTeam.size();
        final int decepticonSize = mDecepticonTeam.size();
        final int min = Math.min(autobotSize, decepticonSize);

        int battleCount = 0;
        for (int i = 0; i < min; i++) {
            battleCount++;
            if (!fight(mAutobotTeam.get(i), mDecepticonTeam.get(i)))
                break;
        }

        return getResult(battleCount, getSkippedFighters(min, autobotSize, decepticonSize));
    }

    private Battle rank() {
        sortByRank(mAutobotTeam);
        sortByRank(mDecepticonTeam);
        return this;
    }

    private void sortByRank(List<Transformer> transformers) {
        Collections.sort(transformers, (o1, o2) -> Integer.compare(o2.getRank(), o1.getRank()));
    }

    private boolean fight(Transformer autobot, Transformer decepticon) {
        if (autobot.getName().equalsIgnoreCase(AUTOBOT_LEADER)
                && decepticon.getName().equalsIgnoreCase(DECEPTICON_LEADER)) {
            destroyAll();
            return false;
        }

        if (autobot.getName().equalsIgnoreCase(AUTOBOT_LEADER)) {
            autobotWins(autobot, decepticon);
            return true;
        }

        if (decepticon.getName().equalsIgnoreCase(DECEPTICON_LEADER)) {
            decepticonWins(decepticon, autobot);
            return true;
        }

        if ((autobot.getCourage() - COURAGE_LIMIT) >= decepticon.getCourage()
                && (autobot.getStrength() - STRENGTH_LIMIT) >= decepticon.getStrength()) {
            autobotWins(autobot, decepticon);
            return true;
        }

        if ((decepticon.getCourage() - COURAGE_LIMIT) >= autobot.getCourage()
                && (decepticon.getStrength() - STRENGTH_LIMIT) >= autobot.getStrength()) {
            decepticonWins(decepticon, autobot);
            return true;
        }

        if ((autobot.getSkill() - SKILL_LIMIT) >= decepticon.getSkill()) {
            autobotWins(autobot, decepticon);
            return true;
        }

        if ((decepticon.getSkill() - SKILL_LIMIT) >= autobot.getSkill()) {
            decepticonWins(decepticon, autobot);
            return true;
        }

        if (autobot.getOverallRating() > decepticon.getOverallRating()) {
            autobotWins(autobot, decepticon);
            return true;
        }

        if (decepticon.getOverallRating() > autobot.getOverallRating()) {
            decepticonWins(decepticon, autobot);
            return true;
        }

        tie(autobot, decepticon);
        return true;
    }

    private void autobotWins(Transformer winner, Transformer loser) {
        mWinningAutobots.add(winner);
        mTransformersDestroyed.add(loser);
    }

    private void decepticonWins(Transformer winner, Transformer loser) {
        mWinningDecepticon.add(winner);
        mTransformersDestroyed.add(loser);
    }

    private void tie(Transformer autobot, Transformer decepticon) {
        mTransformersDestroyed.add(autobot);
        mTransformersDestroyed.add(decepticon);
    }

    private void destroyAll() {
        mTransformersDestroyed.addAll(mWinningAutobots);
        mTransformersDestroyed.addAll(mWinningDecepticon);
        mWinningAutobots.clear();
        mWinningDecepticon.clear();
    }

    private List<Transformer> getSkippedFighters(int minNumberOfBattles,
                                                           int autobotSize,
                                                           int decepticonSize) {
        if (autobotSize > minNumberOfBattles) {
            return mAutobotTeam.subList(minNumberOfBattles, autobotSize);
        }

        if (decepticonSize > minNumberOfBattles) {
            return mDecepticonTeam.subList(minNumberOfBattles, decepticonSize);
        }

        return null;
    }

    private Result getResult(int battleCount, List<Transformer> skippedFighters) {
        final int autobotSize = mWinningAutobots.size();
        final int decepticonSize = mWinningDecepticon.size();

        final Result result = new Result();
        result.setNumberOfBattles(battleCount);

        if (autobotSize > decepticonSize) {

            if (skippedFighters != null && !skippedFighters.isEmpty()) {
                if (skippedFighters.get(0).getTeam().equals("A")) {
                    mWinningAutobots.addAll(skippedFighters);
                } else {
                    result.setSurvivors(skippedFighters);
                }
            }

            result.setWinningTeam(mWinningAutobots);
        } else if (autobotSize < decepticonSize) {

            if (skippedFighters != null && !skippedFighters.isEmpty()) {
                if (skippedFighters.get(0).getTeam().equals("D")) {
                    mWinningDecepticon.addAll(skippedFighters);
                } else {
                    result.setSurvivors(skippedFighters);
                }
            }

            result.setWinningTeam(mWinningDecepticon);
        } else {
            /*
             * If battle is 0, then there is no winners and it'd be tie, but
             * the truth is there wasn't battle at all.
             *
             * If there was at least one battle, and there is no winners, it means
             * they all were destroyed in the battle.
             *
             * If there was a battle and both were winners, it means a tie.
             */
            result.setTie(battleCount > 0
                    && (autobotSize > 0 || decepticonSize > 0));
        }

        return result;
    }

    public class Result {

        private List<Transformer> mWinningTeam,
                                  mSurvivors;
        private int mNumberOfBattles;
        private boolean mTie;

        public List<Transformer> getWinningTeam() {
            return mWinningTeam;
        }

        public void setWinningTeam(List<Transformer> winningTeam) {
            this.mWinningTeam = winningTeam;
        }

        public List<Transformer> getSurvivors() {
            return mSurvivors;
        }

        public void setSurvivors(List<Transformer> survivors) {
            this.mSurvivors = survivors;
        }

        public int getNumberOfBattles() {
            return mNumberOfBattles;
        }

        public void setNumberOfBattles(int number) {
            this.mNumberOfBattles = number;
        }

        public boolean isTie() {
            return mTie;
        }

        public void setTie(boolean tie) {
            this.mTie = tie;
        }
    }

}
