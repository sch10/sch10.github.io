/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import java.io.IOException;
import java.io.BufferedReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Santosh
 */
public class Sphere extends Geom3D {

    private double radius;

    public Sphere(double m) {
        radius = m;
    }

    ////////////////////////////////////////// computeArea method
    @Override
    public double computeArea() {
        return (4 * Math.PI * Math.pow(radius, 2));
    }

    ///////////////////////////////////////// computeVolume method
    @Override
    public double computeVolume() {
        return ((4 * Math.PI * Math.pow(radius, 3)) / 3);
    }

////////////////////////////////////////////////toString method overriding
    @Override
    public String toString() {
        return "the object is a sphere of radius " + radius;
    }
//////////////////////////////////////////////// equals method overriding
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Sphere s = (Sphere) obj;
        return this.computeVolume() == (s.computeVolume());
    }
    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }
////////////////////////////////////////////////// isLargerThan method from Relatable
    @Override
    public int isLargerThan(Relatable obj) {

        Geom other = (Geom) obj;
        if (this.computeVolume() > other.computeVolume()) {
            return 1;
        }
        if (this.computeVolume() < other.computeVolume()) {
            return -1;
        } else {
            return 0;
        }
    }
//////////////////////////////////////////////////// read method
    @Override
    public Object read(BufferedReader br) {

        try {
            String line = br.readLine();
            String[] stringArray = line.split("\\s");
            Double[] doubleArray = new Double[stringArray.length];

            for (int i = 0; i < stringArray.length; i++) {
                doubleArray[i] = Double.parseDouble(stringArray[i]);
            }
            this.radius = doubleArray[0];

        } catch (IOException ex) {
            Logger.getLogger(Sphere.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

}
