package com.test.transformerbattle.domain.model;

import com.test.transformerbattle.data.entity.TransformerEntity;

import java.util.ArrayList;
import java.util.List;

public class TransformerMapper {

    public static List<Transformer> transformToModels(List<TransformerEntity> entities) {
        if (entities == null)
            return null;

        final List<Transformer> models = new ArrayList<>();
        for (TransformerEntity entity : entities) {
            models.add(transformToModel(entity));
        }

        return models;
    }

    public static Transformer transformToModel(TransformerEntity entity) {
        if (entity == null)
            return null;

        final Transformer model = new Transformer();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setTeam(entity.getTeam());
        model.setTeamIconUrl(entity.getTeamIconUrl());
        model.setStrength(entity.getStrength());
        model.setIntelligence(entity.getIntelligence());
        model.setSpeed(entity.getSpeed());
        model.setEndurance(entity.getEndurance());
        model.setRank(entity.getRank());
        model.setCourage(entity.getCourage());
        model.setFirepower(entity.getFirepower());
        model.setSkill(entity.getSkill());
        return model;
    }

    public static TransformerEntity transformToEntity(Transformer model) {
        if (model == null)
            return null;

        final TransformerEntity entity = new TransformerEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setTeam(model.getTeam());
        entity.setTeamIconUrl(model.getTeamIconUrl());
        entity.setStrength(model.getStrength());
        entity.setIntelligence(model.getIntelligence());
        entity.setSpeed(model.getSpeed());
        entity.setEndurance(model.getEndurance());
        entity.setRank(model.getRank());
        entity.setCourage(model.getCourage());
        entity.setFirepower(model.getFirepower());
        entity.setSkill(model.getSkill());
        return entity;
    }

}
