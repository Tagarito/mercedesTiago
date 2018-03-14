package com.mercedes.tiago.mercedesproject.dto;

public class GetDealersInsidePolygonDto {

    private PolygonDTO polygonDTO;

    private String model;

    private String fuel;

    private String transmission;

    public GetDealersInsidePolygonDto() {
    }

    public PolygonDTO getPolygonDTO() {
        return polygonDTO;
    }

    public void setPolygonDTO(PolygonDTO polygonDTO) {
        this.polygonDTO = polygonDTO;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    @Override
    public String toString() {
        return "GetDealersInsidePolygonDto{" +
                "polygonDTO=" + polygonDTO +
                ", model='" + model + '\'' +
                ", fuel='" + fuel + '\'' +
                ", transmission='" + transmission + '\'' +
                '}';
    }
}
