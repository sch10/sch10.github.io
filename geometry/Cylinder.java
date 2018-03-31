/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Santosh
 */
public class Cylinder extends Geom3D {

    private double radius;
    private double height;

    public Cylinder(double m, double n) {
        radius = m;
        height = n;
    }

    /////// computing surface area of a cube of side length 'a'
    @Override
    public double computeArea() {
        return (2 * Math.PI * Math.pow(radius, 2) + 2 * Math.PI * radius * height);
    }

    //////// computing volume of the cube
    @Override
    public double computeVolume() {
        return (Math.PI * Math.pow(radius, 2) * height);
    }

    
////////////////////////////////////////////////toString method overriding

    @Override
    public String toString() {

        return "the object is a cylinder of radius " + radius + " & height " + height;

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
        Cylinder c = (Cylinder) obj;
        return this.computeVolume() == (c.computeVolume());

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
            this.height = doubleArray[1];

        } catch (IOException ex) {
            Logger.getLogger(Sphere.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
}
