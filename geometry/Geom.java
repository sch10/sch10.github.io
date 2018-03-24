/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import java.io.BufferedReader;

/**
 *
 * @author Santosh
 */
public abstract class Geom implements Relatable {

    public abstract Object read(BufferedReader br);

    public abstract double computeArea();

    public abstract double computeVolume();

    public void printDescription() {
        String className = this.getClass().getName();
        System.out.println("The object belongs to class " + className);
        System.out.println("The volume is " + computeVolume());
    }

    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public abstract int isLargerThan(Relatable obj);

}
