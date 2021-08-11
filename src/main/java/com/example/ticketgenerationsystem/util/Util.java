package com.example.ticketgenerationsystem.util;

public class Util {
    public static double calcDistance(double entAx, double entAy, double entBx, double entBy){
        return Math.sqrt((entAx - entBx)*(entAx - entBx) + (entAy - entBy)*(entAy - entBy));
    }
}
