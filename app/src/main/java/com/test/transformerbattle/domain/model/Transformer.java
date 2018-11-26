package com.test.transformerbattle.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Transformer implements Parcelable {

    private String id,
                   name,
                   team,
                   teamIconUrl;

    private int strength,
                intelligence,
                speed,
                endurance,
                rank,
                courage,
                firepower,
                skill,
                overallRating;

    public Transformer() { }

    private Transformer(Parcel in) {
        id = in.readString();
        name = in.readString();
        team = in.readString();
        teamIconUrl = in.readString();
        strength = in.readInt();
        intelligence = in.readInt();
        speed = in.readInt();
        endurance = in.readInt();
        rank = in.readInt();
        courage = in.readInt();
        firepower = in.readInt();
        skill = in.readInt();
        overallRating = in.readInt();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTeamIconUrl() {
        return teamIconUrl;
    }

    public void setTeamIconUrl(String teamIconUrl) {
        this.teamIconUrl = teamIconUrl;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getCourage() {
        return courage;
    }

    public void setCourage(int courage) {
        this.courage = courage;
    }

    public int getFirepower() {
        return firepower;
    }

    public void setFirepower(int firepower) {
        this.firepower = firepower;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public int getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(int overallRating) {
        this.overallRating = overallRating;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(team);
        dest.writeString(teamIconUrl);
        dest.writeInt(strength);
        dest.writeInt(intelligence);
        dest.writeInt(speed);
        dest.writeInt(endurance);
        dest.writeInt(rank);
        dest.writeInt(courage);
        dest.writeInt(firepower);
        dest.writeInt(skill);
        dest.writeInt(overallRating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Transformer> CREATOR = new Creator<Transformer>() {
        @Override
        public Transformer createFromParcel(Parcel in) {
            return new Transformer(in);
        }

        @Override
        public Transformer[] newArray(int size) {
            return new Transformer[size];
        }
    };
}
