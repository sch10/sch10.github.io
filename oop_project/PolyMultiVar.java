/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interpolation;

/**
 *
 * @author Santosh
 */
public class PolyMultiVar {
    // polynomial in two variables
    // poly = cc[0][0] + cc[0][1]*r + cc[1][0]*s + cc[1][1]*r*s + cc[0][2]*r^2 + cc[2][0]*s^2 + cc[2][2]*r^2*s^2 .....
    private double[][] cc = null; // Polynomial coefficients 
    
//==========================================================================
    public PolyMultiVar(double[][] cc) {
        this.cc = cc;
    } // constructor
    
//==========================================================================
    public double[][] getCoefficients() {
        PolyMultiVar newp = this.clone();
        double [][] cba = newp.cc;
      //  cba[0] = newp.cc;
      //  cba[1] = newp.bb;
      //  cba[2] = newp.aa;
        return cba;
    } // getCoefficients
    
    //==========================================================================
    // Computes polynomial as:
    // p(r,s) = Sum_ii(Sum_jj(cc[ii][jj]*s^ii*r^jj))
    /**
     * Computes the polynomial at the given coordinates
     * @param rr - value of parameter
     * @param ss
     * @return - value of the polynomial at evaluated at rr
     */
    public double evaluate(double rr, double ss) {
        double ri = 1.0;
        double si = 1.0;
        double val = cc[0][0];
        if (rr == 0.0 && ss == 0.0) {
            return val;
        } else if (rr == 1.0 && ss == 1.0) {
            for(int ii = 0; ii < cc.length; ii++){
                for (int jj = 0; jj < cc[ii].length; jj++) {
                    val += cc[ii][jj];
                }
            }
            return val;
        }
        
        for(int ii = 1; ii < cc[ii].length; ii++){
        ri *= rr;
        val += cc[0][ii] * ri;
        }

        for (int jj = 1; jj < (cc.length); jj++){
        si *= ss;
        val += cc[jj][0] * si;   
        }

        for(int ii = 1; ii < cc.length; ii++){
            si *= ss;           
            for (int jj = 1; jj < (cc[ii].length); jj++){
            ri *= rr;
            val += cc[ii][jj] * ri * si;
            }
        }
        return val;
    } // evaluate

    //==========================================================================
    public PolyMultiVar add(PolyMultiVar poly){
    double[][] cnew = new double[Math.max(cc.length,poly.cc.length)][Math.max(cc[0].length,poly.cc[0].length)];
    cnew = null;
    for(int ii = 0; ii < cc.length; ii++){
        for(int jj = 0; jj < cc[0].length; jj++){
            cnew[ii][jj] += cc[ii][jj];
        }
    }
    for(int ii = 0; ii < poly.cc.length; ii++){
        for(int jj = 0; jj < poly.cc[0].length; jj++){
            cnew[ii][jj] += poly.cc[ii][jj];
        }
    }
    return new PolyMultiVar(cnew);
    } // add

    //==========================================================================
    public PolyMultiVar scale(double scale) {
        double[][] cn = new double[cc.length][cc[0].length];
        for (int ii = 0; ii < cc.length; ii++) {
            for(int jj = 0; jj < cc[0].length; jj++){
                cn[ii][jj] = scale * cc[ii][jj];
            }
        }
        return new PolyMultiVar(cn);
    } // scale

    //==========================================================================
    // NEEDs to be expanded to work for aa!=null
    public PolyMultiVar multiply(PolyMultiVar poly) {
          return multiply1(poly);
    } // multiply

    //==========================================================================
    private PolyMultiVar multiply1(PolyMultiVar poly) {
        double[][] cn = new double[Math.max(cc.length,poly.cc.length)][Math.max(cc.length,poly.cc.length)];
        cn = null;
        int tt = 0;
        for(int ii = 0; ii < cc.length; ii++){
            while(tt < cc[0].length){
                for(int jj = 0; jj < poly.cc.length; jj++){
                    for(int kk = 0; kk < poly.cc[0].length; kk++){
                        cn[ii + tt][jj + kk] = cc[ii][tt] * poly.cc[jj][kk];
                    }
                tt += 1;
                }
            }
        }
        return new PolyMultiVar(cn);
    } // multiply

    //==========================================================================
    /**
     * Changes the variable r = poly(p
     * Note: Does not work for polynomials with negative powers for r
     * @param poly1
     * @param poly2
     * @return cc[0][0] + cc[0][1]*poly(p) + cc[0][2]*poly(p)^2 + ...+ cc[n][n]*(poly(p1)^n)*poly(p2)^n
     */
    // WORKS ONLY for bb = null and aa = null
    public PolyMultiVar changeVariable(PolyMultiVar poly1, PolyMultiVar poly2) {
        
        double[][] ncc = new double[1][1];
        ncc[0][0] = cc[0][0];
        if (cc.length == 1 && cc[0].length == 1){
            return new PolyMultiVar(ncc);
        }
        PolyMultiVar nPoly = new PolyMultiVar(ncc);
        PolyMultiVar spolyPowerI = new PolyMultiVar(poly1.cc);
        PolyMultiVar rpolyPowerI = new PolyMultiVar(poly2.cc);
///////////================================================================
        for(int ii = 0; ii < 2; ii++){   
            for(int jj = 0; jj < cc.length; jj++){
                if(jj > 1 && ii == 0){
                rpolyPowerI = rpolyPowerI.multiply(poly2);
                nPoly = nPoly.add(rpolyPowerI.scale(cc[ii][jj]));
                }
            }
        }
        for(int ii = 0; ii < 2; ii++){   
            for(int jj = 0; jj < cc.length; jj++){
                if(jj > 1 && ii == 1){
                rpolyPowerI = rpolyPowerI.multiply(poly2);
                nPoly = nPoly.add((spolyPowerI.multiply(rpolyPowerI)).scale(cc[ii][jj]));
                }
            }
        }
        for(int ii = 0; ii < 2; ii++){   
            for(int jj = 0; jj < cc.length; jj++){
                 if(ii == 1 && jj > 1){
                spolyPowerI.cc = poly1.cc;
                spolyPowerI = spolyPowerI.multiply(poly1);
                nPoly = nPoly.add((rpolyPowerI.multiply(spolyPowerI)).scale(cc[jj][ii]));
            }
            }
        }
        for(int ii = 0; ii < 2; ii++){   
            for(int jj = 0; jj < cc.length; jj++){
                if(ii == 0 && jj > 1){
                spolyPowerI = spolyPowerI.multiply(poly1);
                nPoly = nPoly.add(spolyPowerI.scale(cc[ii][jj]));
                }
            }
        }
//////////=============================================== for rows and coloumns more than 2 
        for(int ii = 2; ii < cc.length; ii++){
            spolyPowerI = spolyPowerI.multiply(poly1);
            for(int jj = 2; jj < cc[0].length; jj++){
            rpolyPowerI = rpolyPowerI.multiply(poly2);
            //polyPowerI.scale(cc[ii][jj])    
            PolyMultiVar polyPowerI = spolyPowerI.multiply(rpolyPowerI);
            polyPowerI = polyPowerI.scale(cc[ii][jj]);
            nPoly = nPoly.add(polyPowerI);
            }
        }
        return nPoly;
    } // changeVariable


//==========================================================================
    /**
     * Computes the derivative of the polynomial with respect to r
     * @return -  derivative as a polynomial
     */
    public PolyMultiVar computeDerivativeWRTr() { 
        double[][] dd = cc;
        for(int ii = 0; ii < dd.length; ii++){
            for(int jj = 0; jj < dd[0].length; jj++){

                dd[ii][jj] = (dd[ii + 1][jj]) * (ii + 1);
                if(jj == dd.length - 1){
                    dd[ii][jj] = 0.0;
                }
            }
        }
    return new PolyMultiVar(dd);
    } // computeDerivativeWRTr


    //==========================================================================
    /**
     * Computes the derivative of the polynomial with respect to s
     * @return -  derivative as a polynomial
     */
    public PolyMultiVar computeDerivativeWRTs() {
        double[][] dd = cc;
        for(int ii = 0; ii < dd.length; ii++){
            for(int jj = 0; jj < dd[0].length; jj++){

                dd[jj][ii] = (dd[jj + 1][ii]) * (jj + 1);
                if(jj == dd[0].length - 1){
                    dd[jj][ii] = 0.0;
                }
            }
        }
    return new PolyMultiVar(dd);
        } // computeDerivativeWRTs

    //==========================================================================
    /**
     * Computes the integral of the polynomial WRT dr
     * @return -  integral as a polynomial
     */
    public PolyMultiVar computeIntegralWRTdr() {
     double[][] dd = new double[cc.length][cc[0].length + 1];
        for(int ii = dd.length - 1; ii >= 0; ii--){
            for(int jj = dd[dd.length].length - 1; jj >= 0; jj--){
                dd[ii][jj] = (cc[ii][jj - 1]) / (jj - 1);
                if(jj == 0){
                    dd[ii][jj] = 0.0;
                }
            }
        }
    return new PolyMultiVar(dd);

    } // computeIntegralWRTdr



    //==========================================================================
    /**
     * Computes the integral of the polynomial WRT ds
     * @return -  integral as a polynomial
     */
    public PolyMultiVar computeIntegralWRTds() {
    double[][] dd = new double[cc.length + 1][cc[0].length];
        for(int ii = dd.length - 1; ii >= 0; ii--){
            for(int jj = dd[dd.length].length - 1; jj > 0; jj--){
                dd[ii][jj] = (cc[ii - 1][jj]) / (ii - 1);
                if(ii == 0){
                    dd[ii][jj] = 0.0;
                }
            }
        }
    return new PolyMultiVar(dd);
    } // computeIntegralWRTds

    //==========================================================================
    @Override
    public PolyMultiVar clone() {
        double[][] cn = new double[cc.length][cc.length];
        for(int ii = 0; ii < cc.length; ii++){
            for(int jj = 0; jj < cc[0].length; jj++){
                cn[ii][jj] = cc[ii][jj];
            }
        }
        return new PolyMultiVar(cn);
    } // clone

    //==========================================================================
    public void print(String rr, String ss) {
        System.out.print(cc[0][0]);
        if (cc[0].length > 1) {
            if (cc[0][1] > 0.0) {
                System.out.print(" + " + cc[0][1] + rr);
            } else if (cc[0][1] < 0.0) {
                System.out.print(" - " + -cc[0][1] + rr);
            }
        }

        if (cc.length > 1) {
            if (cc[1][0] > 0.0) {
                System.out.print(" + " + cc[1][0] + ss);
            } else if (cc[1][0] < 0.0) {
                System.out.print(" - " + -cc[1][0] + ss);
            }
        }

        for (int ii = 2; ii < cc.length; ii++) {
            for(int jj = 0; jj < cc[0].length; jj++){

                if (cc[ii][jj] > 0.0) {
                    System.out.print(" + " + cc[ii][jj] + ss + "^" + ii + rr + "^" + jj);
                } else if (cc[ii][jj] < 0.0) {
                System.out.print(" - " + -cc[ii][jj] + ss + "^" + ii + rr + "^" + jj);
                }
            }
        }
    } // print

    //==========================================================================
    public static void main(String[] args) {
        /*
        double[][] cc = {2.0, 3.0, 5.0; 4.0, 5.0, 9.0; 2.0, 3.0, 1.0};
        
        PolyMultiVar p1 = new PolyMultiVar(cc);
        System.out.println("p1(0.5, 0.92) = " + p1.evaluate(0.5, 0.92));
        Polynomial p2 = p1.computeIntegral();
        Polynomial p3 = p1.computeDerivative();
        p1.print("rr", "ss");
        p2.print("rr", "tt");
        p3.print("ss", "tt");
        p2.computeIntegral().print("rr");
        */
        
/*
        p3 = p2.computeIntegral().computeIntegral(); p3.print("rr");
        p3.computeDerivative().print("rr");
        p3.computeDerivative().computeDerivative().print("rr");
        p3.computeDerivative().computeDerivative().computeDerivative().print("rr");
*/
    }
}
