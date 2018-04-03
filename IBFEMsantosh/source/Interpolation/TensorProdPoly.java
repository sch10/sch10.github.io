/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interpolation;
import javax.vecmath.Point3d;
/**
 *
 * @author Santosh
 */
public class TensorProdPoly {
    //
    // TensorProdPoly = Sum_ii{ prst[ii][0]*prst[ii][1]*prst[ii][2] }
    // prst[ii][0] = poly(r), prst[ii][1] = poly(s), prst[ii][2] = poly(t), 
    // Array of prst is needed to support addition of tensor prod polynomials
    //
    private PolyMultiVar[][] prst = null; // r,s,t polynomials
    private int dim = 3;
    //==========================================================================
    // Constructor1
    public TensorProdPoly(PolyMultiVar[] pxyz) {
        prst = new PolyMultiVar[1][];
        prst[0] = pxyz;
        dim = pxyz.length;
        if (dim == 3 && pxyz[2] == null) {
            dim = 2;
        }
    } // TensorProdPoly
    //==========================================================================
    // Constructor2
    public TensorProdPoly(int dim) {
        this.dim = dim;
        prst = null;
    } // TensorProdPoly
    //==========================================================================
    // Constructor3
    public TensorProdPoly(PolyMultiVar[][] pxyz) {
        prst = pxyz;
        dim = pxyz[0].length;
    } // TensorProdPoly
    //==========================================================================
    /**
     * Get a copy of the Polynomials in this TensorProdPoly
     *
     * @return PolyMultiVar array
     */
    public PolyMultiVar[][] getPolynomials() {
        PolyMultiVar[][] newprst = new PolyMultiVar[prst.length][dim];
        for (int ii = 0; ii < prst.length; ii++) {
            for (int jj = 0; jj < dim; jj++) {
                newprst[ii][jj] = prst[ii][jj].clone();
            }
        }
        return newprst;
    } // getPolynomials

    //==========================================================================
    // Computes polynomial as:
    // p(r,s,t) = scalarMult*Sum_jj(cc[0][jj]*r^jj)*Sum_jj(cc[1][jj]*s^jj)*Sum_jj(cc[2][jj]*t^jj)
    /**
     * Computes the polynomial at the given coordinates
     *
     * @param pCoords - coordinates r, s, t
     * @return - value of the polynomial at evaluated at pCoords
     */
    public double evaluate(double[] pCoordsrr, double[] pCoordsss) {
        double value = 0.0;
        for (int ii = 0; ii < prst.length; ii++) {
            double val = 1.0;
            for (int jj = 0; jj < dim; jj++) {
                val *= prst[ii][jj].evaluate(pCoordsrr[jj], pCoordsss[jj]);
            }
            value += val;
        }
        return value;
    } // evaluate

    //==========================================================================
    /**
     * Computes partial derivative of the polynomial with respect to the ith
     * variable
     *
     * @param ith - spatial component with respect to which derivative is
     * desired
     * @return - partial derivative as a tensor product polynomial
     */
    public TensorProdPoly computePartialDer(int ith) {
        if (ith + 1 > dim) {
            System.out.println("Error: ith > TensorProdPoly dimension");
            return null;
        }
        PolyMultiVar[][] pd = new PolyMultiVar[prst.length][dim];
        for (int ii = 0; ii < prst.length; ii++) {
            for (int jj = 0; jj < dim; jj++) {
                pd[ii][jj] = prst[ii][jj];
            }
            pd[ii][ith] = prst[ii][ith].computeDerivative();
        }
        TensorProdPoly derivative = new TensorProdPoly(pd);
        return derivative;
    } // computePartialDer

    //==========================================================================
    /**
     * Computes integral with respect to the ith variable
     *
     * @param ith - spatial component with respect to which integral is desired
     * @return - integral as a tensor product polynomial
     */
    public TensorProdPoly integrateWRTxi(int ith) {
        if (ith > dim) {
            System.out.println("Error: ith > TensorProdPoly dimension");
            return null;
        }
        PolyMultiVar[][] pd = new PolyMultiVar[prst.length][dim];
        for (int ii = 0; ii < prst.length; ii++) {
            for (int jj = 0; jj < dim; jj++) {
                pd[ii][jj] = prst[ii][jj].clone();
            }
            pd[ii][ith] = pd[ii][ith].computeIntegral();
        }
        TensorProdPoly integral = new TensorProdPoly(pd);
        return integral;
    } // integrateWRTxi

    //==========================================================================
    /**
     * Computes the sum of a given TensorProdPoly and this one
     *
     * @param tpp - a tensor prod polynomial
     * @return - sum of given TensorProdPoly and this one
     */
    public TensorProdPoly add(TensorProdPoly tpp) {
        if (tpp == null) {
            return this;
        } else if (tpp.prst == null) {
            return this;
        } else if (prst == null) {
            return tpp;
        }
        int size = prst.length + tpp.prst.length;
        PolyMultiVar[][] npxyz = new PolyMultiVar[size][dim];
        for (int ii = 0; ii < prst.length; ii++) {
            for (int jj = 0; jj < dim; jj++) {
                npxyz[ii][jj] = prst[ii][jj];
            }
        }
        for (int ii = 0; ii < tpp.prst.length; ii++) {
            int kk = prst.length + ii;
            for (int jj = 0; jj < dim; jj++) {
                npxyz[kk][jj] = tpp.prst[ii][jj];
            }
        }
        return new TensorProdPoly(npxyz);
    } // add 

//==========================================================================
    /**
     * Computes a scaled tensor product polynomial
     *
     * @param tpp - a tensor prod polynomial
     * @return - sum of given TensorProdPoly and this one
     */
    public TensorProdPoly scale(double scale) {
        if (scale == 0) {
            return null;
        }
        PolyMultiVar[][] npxyz = new PolyMultiVar[prst.length][dim];
        for (int ii = 0; ii < prst.length; ii++) {
            npxyz[ii][0] = prst[ii][0].scale(scale);
            npxyz[ii][1] = prst[ii][1].clone();
            if (dim == 3) {
                npxyz[ii][2] = prst[ii][2].clone();
            }
        }
        return new TensorProdPoly(npxyz);
    } // scale

    //==========================================================================
    /**
     * Computes the product of two TensorProdPoly
     *
     * @param tpp - TensorProdPoly to multiply with this TensorProdPoly
     * @return - TensorProdPoly that is the product of tpp and this
     * TensorProdPoly
     */
    public TensorProdPoly multiply(TensorProdPoly tpp) {
        if ((prst == null) || (tpp.prst == null)) {
            return null;
        }
        int size = prst.length * tpp.prst.length;
        PolyMultiVar[][] nprst = new PolyMultiVar[size][dim];
        int cntr = 0;
        for (int ii = 0; ii < prst.length; ii++) {
            for (int jj = 0; jj < tpp.prst.length; jj++) {
                for (int kk = 0; kk < dim; kk++) {
                    nprst[cntr][kk] = prst[ii][kk].multiply(tpp.prst[jj][kk]);
                }
                cntr++;
            }
        }
        return new TensorProdPoly(nprst);
    } // multiply   /// completed

    public PolyMultiVar integrateOverTriangle(Point3d[] verts) {
        double[][] ccx = {{verts[0].x, (verts[1].x - verts[0].x)},
        {(verts[2].x - verts[0].x), 0.0}};
        double[][] ccy = {{verts[0].y, (verts[1].y - verts[0].y)},
        {(verts[2].y - verts[0].y), 0.0}};
        double[][] ccz = {{verts[0].z, (verts[1].z - verts[0].z)},
        {(verts[2].z - verts[0].z), 0.0}};
        PolyMultiVar xPoly = new PolyMultiVar(ccx);
        PolyMultiVar yPoly = new PolyMultiVar(ccy);
        PolyMultiVar zPoly = new PolyMultiVar(ccz);
        PolyMultiVar[] xyzPolys = {xPoly, yPoly, zPoly};
        PolyMultiVar result = null;

        for (int ii = 0; ii < prst.length; ii++) {
            PolyMultiVar polyProduct = prst[ii][0].changeVariable(xyzPolys[0], xyzPolys[1]);
            for (int jj = 1; jj < prst[0].length; jj++) {
                if (jj == prst[0].length - 1) {
                    polyProduct = polyProduct.multiply(prst[ii][jj].changeVariable(xyzPolys[jj], xyzPolys[0]));
                } else {
                    polyProduct = polyProduct.multiply(prst[ii][jj].changeVariable(xyzPolys[jj], xyzPolys[jj + 1]));
                }
            }
            if (result == null) {
                result = polyProduct;
            } else {
                result = result.add(polyProduct);
            }
        }
        return result;
    }

    //==========================================================================
    public void print() {
        if (prst == null) {
            System.out.println("prst is null => 0.0");
            return;
        }
        String[] rr = {"r", "s", "t"};
        for (int ii = 0; ii < prst.length; ii++) {
            System.out.print(" + ");
            for (int jj = 0; jj < prst[ii].length; jj++) {
                System.out.print("(");
                prst[ii][jj].print(rr[jj], rr[jj + 1]);
                System.out.print(")");
                if (jj == prst.length - 1) {
                    prst[ii][jj].print(rr[jj], rr[0]);
                    System.out.print(")");
                }
                System.out.println();
            }
        } // print
    }
    //==========================================================================

    /**
     *
     * @param mat
     */
    public static void printMatrix(TensorProdPoly[][] mat) {
        for (int ii = 0; ii < mat.length; ii++) {
            for (int jj = 0; jj < mat[ii].length; jj++) {
                System.out.println("(" + ii + "," + jj + ") ");
                if (mat == null) {
                    System.out.println("Mat is null => 0.0");
                } else {
                    if (mat[ii][jj] == null) {
                        System.out.println("null");
                    } else {
                        mat[ii][jj].print();
                    }
                }
            }
        }
    } // printMatrix

    public static void main(String[] args) {

        double[][] cc = {{9.0, 3.0, 5.0}, {4.0, 5.0, 9.0}, {12.0, 3.0, 2.0}};
        double[][] dd = {{9.0, 4.0, 8.0}, {3.0, 5.0, 6.0}, {2.0, 6.0, 1.0}};
        double[][] ee = {{3.0, 5.0, 8.0}, {3.0, 7.0, 6.0}, {1.0, 4.0, 6.0}};
        PolyMultiVar[] poly = {new PolyMultiVar(cc), new PolyMultiVar(dd), new PolyMultiVar(ee)};
        TensorProdPoly tpp = new TensorProdPoly(poly);
        double[] cc1 = {9.0, 3.0, 5.0};
        double[] dd1 = {6.0, 7.0, 3.0};
        double[] ee1 = {2.0, 9.0, 4.0};

        Point3d[] verts = {new Point3d(cc1), new Point3d(dd1), new Point3d(ee1)};
        // tpp.print();
        System.out.println("\n");
        PolyMultiVar tppot = tpp.integrateOverTriangle(verts);
        //System.out.println(tppot);
        tppot.print("rr", "ss");
        System.out.println("\n");
        tppot.computeIntegralWRTdr().print("rr", "ss");
    }

}
