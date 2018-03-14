package com.mercedes.tiago.mercedesproject.dto;

public class PairDTO {

    private Double key;

    private Double value;

    public PairDTO() {
    }

    public Double getKey() {
        return key;
    }

    public void setKey(Double key) {
        this.key = key;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PairDTO{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
