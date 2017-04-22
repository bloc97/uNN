/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customnn;

/**
 *
 * @author bowen
 */
public abstract class ActivationFunctions {
    public enum ActivationType {
        LINEAR, ARCTAN, SOFTSIGN, RELU, LRELU, PRELU, ELU
    }
    public static double nonLinear(double d, ActivationType type) {
        switch (type) {
            case LINEAR:
                return d;
            case ARCTAN:
                return arcTan(d);
            case SOFTSIGN:
                return softSign(d);
            case RELU:
                return reLU(d);
            case LRELU:
                return d;
            case PRELU:
                return d;
            case ELU:
                return eLU(d, 0.1);
            default:
                throw new IllegalStateException("Illegal Activation Function");
        }
    }
    public static double nonLinearDerivative(double d, ActivationType type) {
        switch (type) {
            case LINEAR:
                return 1;
            case ARCTAN:
                return arcTanDerivative(d);
            case SOFTSIGN:
                return softSignDerivative(d);
            case RELU:
                return reLUDerivative(d);
            case LRELU:
                return 1;
            case PRELU:
                return 1;
            case ELU:
                return eLUDerivative(d, 0.1);
            default:
                throw new IllegalStateException("Illegal Activation Function");
        }
    }
    public static double nonLinear(double d, ActivationType type, double a) {
        switch (type) {
            case LRELU:
                return d;
            case PRELU:
                return d;
            case ELU:
                return eLU(d, a);
            default:
                throw new IllegalStateException("Illegal Activation Function");
        }
    }
    public static double nonLinearDerivative(double d, ActivationType type, double a) {
        switch (type) {
            case LRELU:
                return 1;
            case PRELU:
                return 1;
            case ELU:
                return eLUDerivative(d, a);
            default:
                throw new IllegalStateException("Illegal Activation Function");
        }
    }
    public static double arcTan(double d) {
        return Math.atan(d);
    }
    public static double arcTanDerivative(double d) {
        return 1/(d*d + 1);
    }
    public static double softSign(double d) {
        return d / (1 + Math.abs(d));
    }
    public static double softSignDerivative(double d) {
        double ds = (1 + Math.abs(d));
        return 1 / (ds * ds);
    }
    public static double reLU(double d) {
        if (d >= 0) {
            return d;
        } else {
            return 0;
        }
    }
    public static double reLUDerivative(double d) {
        if (d >= 0) {
            return 1;
        } else {
            return 0;
        }
    }
    public static double eLU(double d, double a) {
        if (d >= 0) {
            return d;
        } else {
            return a*(Math.pow(Math.E, d) - 1);
        }
    }
    public static double eLUDerivative(double d, double a) {
        if (d >= 0) {
            return 1;
        } else {
            return eLU(d, a) + a;
        }
    }
}
