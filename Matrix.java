import edu.princeton.cs.algs4.StdOut; 

public class Matrix
{
    public Fraction[][] array; // 2D array to store values 
    public final int rowSize; // number of rows
    public final int colSize; // number of columns 
    
    public Matrix(Fraction[][] array) // constructor 
    {
        this.array = array; 
        rowSize = array.length; 
        colSize = array[0].length; // assumes all rows have same length 
    }
    
    // constructor for matrix of given size with all values initialized to zero 
    public Matrix(int row, int col) 
    {
        array = new Fraction[row][col]; 
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                array[i][j] = new Fraction(0, 1); 
        
        rowSize = row; 
        colSize = col; 
    }
    
    public Matrix identityMatrix(int size) // creates an identity matrix of given size
    {
        Fraction[][] identityArray = new Fraction[size][size]; 
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                if (i == j)
                    identityArray[i][j] = new Fraction(1, 1); 
                else 
                    identityArray[i][j] = new Fraction(); // default is zero 
            }
        return new Matrix(identityArray); 
    }
    
    public String toString() // string representation 
    {
        StringBuilder sb = new StringBuilder(); 
        for (int i = 0; i < array.length; i++)
        {
            for (int j = 0; j < array[0].length; j++)
                sb.append(array[i][j] + " "); 
            sb.append("\n"); 
        }
        return sb.toString(); 
    }
    
    // adds that Matrix to this Matrix 
    public Matrix add(Matrix that)
    {
        if (this.rowSize != that.rowSize && this.colSize != that.colSize)
            throw new IllegalArgumentException("Cannot add matrices of " +  
                                                   "different sizes"); 
        Matrix sum = new Matrix(rowSize, colSize); 
        
        for (int i = 0; i < rowSize; i++)
            for (int j = 0; j < colSize; j++)
                sum.array[i][j] = this.array[i][j].add(that.array[i][j]);   
        return sum; 
    }
    
    // subtracts that Matrix from this Matrix 
    public Matrix subtract(Matrix that)
    {
        if (this.rowSize != that.rowSize && this.colSize != that.colSize)
            throw new IllegalArgumentException("Cannot subtract matrices of " +  
                                                   "different sizes"); 
        Matrix diff = new Matrix(rowSize, colSize); 
        
        for (int i = 0; i < rowSize; i++)
            for (int j = 0; j < colSize; j++)
                diff.array[i][j] = this.array[i][j].subtract(that.array[i][j]);   
        return diff; 
    }
    
    // multiplies this Matrix with that Matrix
    public Matrix multiply(Matrix that)
    {
        if (this.colSize != that.rowSize)
            throw new IllegalArgumentException("Cannot multiply matrices of " +
                                               "incompatible sizes"); 
         
        Matrix product = new Matrix(this.rowSize, that.colSize); 

        for (int i = 0; i < this.rowSize; i++)
            for (int j = 0; j < that.colSize; j++)
                for (int k = 0; k < this.colSize; k++)
                    product.array[i][j] = product.array[i][j].add(this.array[i][k].multiply(that.array[k][j])); 
        return product; 
    }
    
    // matrix inversion through row operations
    public Matrix inverse()
    {
        Matrix expansion = new Matrix(rowSize, 2 * colSize); 
        
        // expanded form of matrix with mirror identity matrix 
        for (int i = 0; i < this.rowSize; i++)
            for (int j = 0; j < this.colSize; j++)
                expansion.array[i][j] = this.array[i][j]; 
        int extraCol = this.colSize; // fills in identity matrix portion 
        while (extraCol < expansion.colSize)
        {
            expansion.array[extraCol - this.colSize][extraCol] = new Fraction(1, 1); 
            extraCol++; 
        }
        
        invert(expansion); // inverts matrix 
        Matrix inverted = new Matrix(rowSize, colSize); 
         for (int i = 0; i < this.rowSize; i++)
            for (int j = 0; j < this.colSize; j++)
                inverted.array[i][j] = expansion.array[i][j + this.colSize]; 
        return inverted; 
    }
    
    private void invert(Matrix m)
    {
        Fraction pivot = new Fraction(); 
        Fraction factor = new Fraction(); 
        
        for (int i = 0; i < m.rowSize; i++)
        {
            // i is row of current pivot 
            pivot = m.array[i][i]; 

            // simplifies row with pivot
            for (int j = i; j < m.colSize; j++)
            {
                m.array[i][j] = m.array[i][j].divide(pivot); 
            }
            
            // subtract pivot from other rows 
            for (int j = 0; j < m.rowSize; j++)
            {
                if (j == i) // skips row with current pivot 
                    continue; 
                
                factor = m.array[j][i].multiply(-1); 

                for (int k = i; k < m.colSize; k++)
                {
                    m.array[j][k] = m.array[j][k].add(factor.multiply(m.array[i][k])); 
                }
            }
        }
    }
    
    public static void main(String[] args) // unit testing
    {
        Fraction half = new Fraction(1, 2); 
        Fraction fourNinths = new Fraction(4, 9); 
        Fraction oneThird = new Fraction(1, 3); 
        Fraction twoNinths = new Fraction (2, 9);
        Fraction zero = new Fraction(0, 1); 
        
        // simulates Foobar example matrix 
        Fraction[][] foobarTest = new Fraction[6][6]; 
        for (int i = 0; i < foobarTest.length; i++)
            for (int j = 0; j < foobarTest.length; j++)
                foobarTest[i][j] = zero; 
        
        foobarTest[0][1] = half; 
        foobarTest[0][5] = half; 
        foobarTest[1][0] = fourNinths; 
        foobarTest[1][3] = oneThird; 
        foobarTest[1][4] = twoNinths; 
        
        Matrix test = new Matrix(foobarTest); // test toString method 
        StdOut.println(test); 
        
        Matrix identity = new Matrix(foobarTest); // test identity method
        identity = test.identityMatrix(4); 
        StdOut.println(identity); 
        StdOut.println(); 
        
        Fraction nineSevenths = new Fraction(9, 7); // test multiply method 
        Fraction nineFourteenth = new Fraction(9, 14); 
        Fraction fourSeventh = new Fraction(4, 7); 
        Fraction[][] f = new Fraction[2][2]; 
        f[0][0] = nineSevenths; 
        f[0][1] = nineFourteenth; 
        f[1][0] = fourSeventh; 
        f[1][1] = nineSevenths; 
        Matrix F = new Matrix(f); 
        Fraction[][] r = new Fraction[2][4]; 
        for (int i = 0; i < r.length; i++)
            for (int j = 0; j < r[0].length; j++)
                r[i][j] = zero;
        r[0][3] = half; 
        r[1][1] = oneThird;
        r[1][2] = twoNinths; 
        Matrix R = new Matrix(r); 
        StdOut.println(F); 
        StdOut.println(R); 
        StdOut.println("Multiplying... \n" + F.multiply(R)); 
        
        // test inversion method 
        Fraction nHalf = new Fraction(-1, 2); 
        Fraction nFourNinths = new Fraction(-4, 9); 
        Fraction one = new Fraction(1, 1); 
        Fraction[][] inverseArray = new Fraction[2][2]; 
        inverseArray[0][0] = one;
        inverseArray[0][1] = nHalf;
        inverseArray[1][0] = nFourNinths;
        inverseArray[1][1] = one; 
        Matrix inversionTest = new Matrix(inverseArray); 
        StdOut.println("Inverting... \n" + inversionTest.inverse()); 
        
        Fraction[][] inverseArray1 = new Fraction[2][2]; 
        inverseArray1[0][0] = oneThird;
        inverseArray1[0][1] = zero; 
        inverseArray1[1][0] = twoNinths;
        inverseArray1[1][1] = one; 
        inversionTest = new Matrix(inverseArray1); 
        StdOut.println(inversionTest.inverse()); 
        
        StdOut.println(inversionTest.identityMatrix(5)); 
        
        StdOut.println("Subtracting..."); 
        StdOut.println(inversionTest); 
        StdOut.println((inversionTest.identityMatrix(2)).subtract(inversionTest)); 
    }
    
}
