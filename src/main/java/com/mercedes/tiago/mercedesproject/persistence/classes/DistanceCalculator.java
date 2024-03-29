package com.mercedes.tiago.mercedesproject.persistence.classes;

import com.mercedes.tiago.mercedesproject.exception.InvalidCoordinatesException;

import java.util.*;
import java.lang.*;
import java.io.*;


//https://www.geodatasource.com/developers/java (SOURCE)
public class DistanceCalculator
{

    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) throws InvalidCoordinatesException {
        if(lat1 >90 || lat1< -90 || lat2 >90 || lat2< -90 ||
                lon1 >180 || lon1< -180 || lon2 >180 || lon2< -180){
            throw new InvalidCoordinatesException();
        }
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}