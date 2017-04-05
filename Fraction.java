import edu.princeton.cs.algs4.StdOut; 

public class Fraction 
{
    int num; // numerator
    int den; // denominator
    
    // initialize fractions to zero 
    public Fraction()
    {
        num = 0; 
        den = 1; 
    }
    
    // assign fraction with given values 
    public Fraction(int numerator, int denominator)
    {
        num = numerator; 
        den = denominator; 
        
        this.simplify(); 
    }
    
    // computes GCD using Euclid's algorithm 
    public int GCD(int a, int b)
    {
        if (b == 0)
            return a; 
        return GCD(b, a%b); 
    }
    
    // reduces fraction to simplest form 
    public void simplify()
    {
        int gcd = GCD(this.num, this.den); 
        if (gcd != 0 && gcd != 1)
        {
            num = num / gcd; 
            den = den / gcd; 
        }
    }
    
    // returns sum of this fraction and that fraction
    public Fraction add(Fraction that)
    {
        Fraction sum = new Fraction(this.num * that.den + that.num * this.den, 
                                    this.den * that.den); 
        sum.simplify();  
        return sum; 
    }
    
    // returns difference of this fraction minus that fraction 
    public Fraction subtract(Fraction that)
    {
        Fraction diff = new Fraction(this.num * that.den - that.num * this.den,
                                     this.den * that.den); 
        diff.simplify(); 
        return diff;  
    }
    // returns product of this fraction with that fraction
    public Fraction multiply(Fraction that)
    {
        Fraction product = new Fraction(this.num * that.num, this.den * that.den); 
        product.simplify(); 
        return product;
    }
    
    // returns product of this fraction with an integer
    public Fraction multiply(int that)
    {
        Fraction product = new Fraction(this.num * that, this.den); 
        product.simplify(); 
        return product; 
    }
    
    // changes Fraction into integer, provided denominator equals one
    public int intoInt()
    {
        if (this.den != 1)
            throw new IllegalArgumentException("Not an integer"); 
        return this.num; 
    }
    
    // returns quotient of this fraction divided by that fraction
    public Fraction divide(Fraction that)
    {
        Fraction quotient = new Fraction(this.num * that.den, this.den * that.num); 
        quotient.simplify(); 
        return quotient; 
    }
    
    public String toString()
    {
        return this.num + "/" + this.den; 
    }
    
    
    // unit testing 
    public static void main(String[] args)
    {
        Fraction test0 = new Fraction(3, 7); 
        Fraction test1 = new Fraction(2, 3); 
        Fraction test2 = new Fraction(3, 9); 

        test0.simplify(); 
        test1.simplify(); 
        test2.simplify(); 
        StdOut.println(test0); 
        StdOut.println(test1); 
        StdOut.println(test2); 
        StdOut.println(); 
        
        Fraction sum = new Fraction(); 
        sum = sum.add(test1); 
        StdOut.println(sum); 
        sum = sum.add(test2); 
        StdOut.println(sum);
        StdOut.println(); 
        
        Fraction product = new Fraction();  
        product = test1.multiply(test2); 
        StdOut.println(product); 
    }
        
    
}
