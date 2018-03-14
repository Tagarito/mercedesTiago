package com.mercedes.tiago.mercedesproject.dto;

import javafx.util.Pair;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class PolygonDTO {

    private List<PairDTO> points = new ArrayList<>();

    public PolygonDTO() {
    }



    public List<PairDTO> getPoints() {
        return points;
    }

    public void setPoints(List<PairDTO> points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "PolygonDTO{" +
                "points=" + points +
                '}';
    }
}
