package mz.mzlib.util.math;

import java.util.Objects;

public class Quaternion
{
    public final double a, b, c, d;
    
    public Quaternion(double a, double b, double c, double d)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    public Quaternion(double a)
    {
        this(a, 0, 0, 0);
    }
    public Quaternion(Complex complex)
    {
        this(complex.getReal(), complex.getImag(), 0, 0);
    }
    
    public double getA()
    {
        return this.a;
    }
    
    public double getB()
    {
        return this.b;
    }
    public double getC()
    {
        return this.c;
    }
    
    public double getD()
    {
        return this.d;
    }
    
    public double getScalar()
    {
        return this.getA();
    }
    public Quaternion getVector()
    {
        return new Quaternion(0, this.getB(), this.getC(), this.getD());
    }
    
    public Quaternion negate()
    {
        return new Quaternion(-this.a, -this.b, -this.c, -this.d);
    }
    
    public Quaternion conjugate()
    {
        return new Quaternion(this.a, -this.b, -this.c, -this.d);
    }
    
    public Quaternion scale(double scalar)
    {
        return new Quaternion(this.a*scalar, this.b*scalar, this.c*scalar, this.d*scalar);
    }
    
    public double normSquared()
    {
        return this.a*this.a+this.b*this.b+this.c*this.c+this.d*this.d;
    }
    
    public double norm()
    {
        return Math.sqrt(this.normSquared());
    }
    
    public Quaternion sgn()
    {
        return this.scale(1./this.norm());
    }
    
    public Quaternion inverse()
    {
        return this.conjugate().scale(1./this.normSquared());
    }
    
    public Quaternion square()
    {
        return this.multiply(this);
    }
    
    public Quaternion cube()
    {
        return this.multiply(this.square());
    }
    
    public Quaternion exp()
    {
        Quaternion vector = this.getVector();
        double vectorNorm = vector.norm();
        return new Quaternion(Math.cos(vectorNorm)).add(vector.scale(Math.sin(vectorNorm))).scale(Math.exp(this.getScalar()));
    }
    
    public Quaternion log()
    {
        return this.getVector().sgn().scale(this.arg()).add(new Quaternion(Math.log(this.norm())));
    }
    
    public Quaternion pow(Quaternion exponent)
    {
        return exponent.multiply(this.log()).exp();
    }
    
    public double arg()
    {
        return Math.acos(this.a/this.norm());
    }
    
    public Quaternion add(Quaternion other)
    {
        return new Quaternion(this.a+other.a, this.b+other.b, this.c+other.c, this.d+other.d);
    }
    
    public Quaternion multiply(Quaternion other)
    {
        return new Quaternion( //
                this.a*other.a-this.b*other.b-this.c*other.c-this.d*other.d, //
                this.a*other.b+this.b*other.a+this.c*other.d-this.d*other.c, //
                this.a*other.c-this.b*other.d+this.c*other.a+this.d*other.b, //
                this.a*other.d+this.b*other.c-this.c*other.b+this.d*other.a);
    }
    
    public Complex toComplex()
    {
        return new Complex(this.a, this.b+this.c+this.d);
    }
    
    public Quaternion fromAxisAngle(Quaternion axis, double angle)
    {
        double halfAngle = angle/2.;
        double s = Math.sin(halfAngle);
        return new Quaternion(Math.cos(halfAngle), axis.b*s, axis.c*s, axis.d*s);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(this.a, this.b, this.c, this.d);
    }
    @Override
    public boolean equals(Object obj)
    {
        if(this==obj)
            return true;
        if(!(obj instanceof Quaternion))
            return false;
        Quaternion other = (Quaternion)obj;
        return this.a==other.a && this.b==other.b && this.c==other.c && this.d==other.d;
    }
    @Override
    public String toString()
    {
        return "("+this.a+"+"+this.b+"i"+"+"+this.c+"j"+"+"+this.d+"k)";
    }
}
