package mz.mzlib.util;

import java.util.Objects;

public class Complex
{
    public double real;
    public double imag;
    
    public Complex(double real, double imag)
    {
        this.real = real;
        this.imag = imag;
    }
    
    public Complex(double real)
    {
        this(real, 0);
    }
    
    public double getReal()
    {
        return this.real;
    }
    public double getImag()
    {
        return this.imag;
    }
    
    public Complex negate()
    {
        return new Complex(-this.real, -this.imag);
    }
    
    public Complex conjugate()
    {
        return new Complex(this.real, -this.imag);
    }
    
    public Complex scale(double scalar)
    {
        return new Complex(this.real*scalar, this.imag*scalar);
    }
    
    public double normSquared()
    {
        return this.real*this.real+this.imag*this.imag;
    }
    
    public Complex sgn()
    {
        return this.scale(1./this.norm());
    }
    
    public double norm()
    {
        return Math.sqrt(this.normSquared());
    }
    
    public Complex reciprocal()
    {
        return this.conjugate().scale(1./this.normSquared());
    }
    
    public Complex square()
    {
        return this.multiply(this);
    }
    
    public Complex cube()
    {
        return this.multiply(this.square());
    }
    
    public Complex sqrt()
    {
        double r = Math.sqrt(this.norm());
        double theta = Math.atan2(this.imag, this.real);
        return new Complex(r*Math.cos(theta/2), r*Math.sin(theta/2));
    }
    
    public double arg()
    {
        return Math.atan2(this.imag, this.real);
    }
    
    public Complex add(Complex other)
    {
        return new Complex(this.real+other.real, this.imag+other.imag);
    }
    
    public Complex subtract(Complex other)
    {
        return this.add(other.negate());
    }
    
    public Complex multiply(Complex other)
    {
        return new Complex(this.real*other.real-this.imag*other.imag, //
                this.real*other.imag+this.imag*other.real);
    }
    
    public Complex divide(Complex other)
    {
        return this.multiply(other.reciprocal());
    }
    
    public Complex exp()
    {
        double r = Math.exp(this.real);
        double theta = this.imag;
        return new Complex(r*Math.cos(theta), r*Math.sin(theta));
    }
    
    public Complex log()
    {
        double r = Math.sqrt(this.real*this.real+this.imag*this.imag);
        double theta = Math.atan2(this.imag, this.real);
        return new Complex(Math.log(r), theta);
    }
    
    public Complex pow(Complex exponent)
    {
        return this.log().multiply(exponent).exp();
    }
    
    public Complex pow(double exponent)
    {
        return this.pow(new Complex(exponent));
    }
    
    public Complex sin()
    {
        return new Complex(Math.sin(this.real)*Math.cosh(this.imag), Math.cos(this.real)*Math.sinh(this.imag));
    }
    
    public Complex cos()
    {
        return new Complex(Math.cos(this.real)*Math.cosh(this.imag), -Math.sin(this.real)*Math.sinh(this.imag));
    }
    
    public Complex tan()
    {
        return this.sin().divide(this.cos());
    }
    
    public Complex asin()
    {
        return new Complex(Math.asin(this.real)*Math.cosh(this.imag), Math.PI/2-Math.atan2(this.imag, this.real));
    }
    
    public Complex acos()
    {
        return new Complex(Math.acos(this.real)*Math.cosh(this.imag), -Math.PI/2-Math.atan2(this.imag, this.real));
    }
    
    public Complex atan()
    {
        return new Complex(Math.atan(this.real)*Math.cosh(this.imag), Math.atan(this.imag)*Math.sinh(this.imag));
    }
    
    public Complex sinh()
    {
        return new Complex(Math.sinh(this.real)*Math.cos(this.imag), Math.cosh(this.real)*Math.sin(this.imag));
    }
    
    public Complex cosh()
    {
        return new Complex(Math.cosh(this.real)*Math.cos(this.imag), Math.sinh(this.real)*Math.sin(this.imag));
    }
    
    public Complex tanh()
    {
        return this.sinh().divide(this.cosh());
    }
    
    public Quaternion toQuaternion()
    {
        return new Quaternion(this);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(this.real, this.imag);
    }
    @Override
    public boolean equals(Object obj)
    {
        if(obj==this)
            return true;
        if(!(obj instanceof Complex))
            return false;
        Complex other = (Complex)obj;
        return this.real==other.real && this.imag==other.imag;
    }
    @Override
    public String toString()
    {
        return "("+this.real+"+"+this.imag+"i)";
    }
}
