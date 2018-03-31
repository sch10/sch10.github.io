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
public class Rectangle extends Geom2D {

    private double length;
    private double breadth;
    private double thickness;

    public Rectangle(double k, double m, double n) {
        length = k;
        breadth = m;
        thickness = n;
    }

    //////////// computing area of a square slab of side 'a' and thickness 'h'
    @Override
    public double computeArea() {
        return (2 * length * breadth + 2 * length * thickness + +2 * breadth * thickness);
    }

    //////////// computing volume of the square slab
    @Override
    public double computeVolume() {
        return (length * breadth * thickness);
    }

////////////////////////////////////////////////toString method overriding

    @Override
    public String toString() {

        return "the object is a rectangle of length " + length + " , with breadth " + breadth + "and thickness " + thickness;

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
        Rectangle s = (Rectangle) obj;
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
            this.length = doubleArray[0];
            this.breadth = doubleArray[1];
            this.thickness = doubleArray[2];
        } catch (IOException ex) {
            Logger.getLogger(Sphere.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

}
