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
public abstract class Geom2D extends Geom {

    @Override
    public abstract Object read(BufferedReader br);

    @Override
    public abstract double computeArea();

    @Override
    public abstract double computeVolume();

    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public abstract int isLargerThan(Relatable obj);

}
