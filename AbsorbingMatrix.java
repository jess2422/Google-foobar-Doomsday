import edu.princeton.cs.algs4.StdOut; 

public class AbsorbingMatrix
{
    public Matrix matrix; 
    private int numArrays, numTerminalRows, numNonTerminalRows;
    private boolean[] isTerminalRow; // keeps track of terminal rows  
    private Matrix R, Q; // based off of Markov chain & Transition Matrix conventions
    public int[] result; // result for Foobar 
    
    public AbsorbingMatrix(int[][] input)
    {           
        if (input.length == 1)
        {
            int[] cornerCaseResult = {1, 1}; 
            result = cornerCaseResult; 
        }
        
        numArrays = input.length; 
        isTerminalRow = new boolean[numArrays]; // max # of Terminal Rows 
        numTerminalRows = 0; 
        for (int i = 0; i < numArrays; i++)
        {
            if (isTerminal(input[i]))
            {
                isTerminalRow[i] = true; 
                numTerminalRows++; 
            }
        }
        numNonTerminalRows = numArrays - numTerminalRows; 
            
        matrix = new Matrix(convertToFraction(input)); 
        
        // creates sub-arrays R and Q 
        Fraction[][] arrayR = new Fraction[numNonTerminalRows][numTerminalRows];
        Fraction[][] arrayQ= new Fraction[numNonTerminalRows][numNonTerminalRows]; 
        int nextRow = 0; 
        int nextCol = 0; 
        int rowTracker = 0; 
        int colTracker = 0; 
        for (int i = 0; i < arrayR.length; i++)
        {
            // searches for next non-terminal row in matrix 
            while (isTerminalRow[rowTracker]) 
            {
                rowTracker++; 
                nextRow++;
            }
            
            // sub-array R holds columns corresponding to terminal rows  
            for (int j = 0; j < arrayR[0].length; j++)
            {
                while (!isTerminalRow[colTracker]) 
                {
                    colTracker++; 
                    nextCol++; 
                }
 
                arrayR[i][j] = matrix.array[nextRow][nextCol]; 
                colTracker++; 
                nextCol++;
            }
            
            // resets column counters 
            colTracker = 0; 
            nextCol = 0; 
            
            // sub-array Q holds columns corresponding to non-terminal rows 
            for (int j = 0; j < arrayQ[0].length; j++)
            {
                while (isTerminalRow[colTracker])
                {
                    colTracker++; 
                    nextCol++; 
                }
                arrayQ[i][j] = matrix.array[nextRow][nextCol]; 
                colTracker++; 
                nextCol++;
            }
            
            colTracker = 0; 
            nextCol = 0; 
            rowTracker++; 
            nextRow++;
                
        }
        
        // creates new matrices with fraction arrays 
        R = new Matrix(arrayR.length, arrayR[0].length);
        R.array = arrayR; 
        Q = new Matrix(arrayQ.length, arrayQ[0].length); 
        Q.array = arrayQ;  
        
        // takes first row of limiting matrix for result 
        Matrix limiting = limitingMatrix(); 
        result = new int[limiting.colSize + 1]; 
        
        // finds lcm of denominators of first row numbers 
        int lcm = lcm(limiting.array[0]); 
        
        // converts to specified output format 
        for (int i = 0; i < result.length - 1; i++) 
            result[i] = limiting.array[0][i].multiply(lcm).num; 
        
        result[result.length - 1] = lcm;  
    }
    
    // helper method to computes lcm of two integers through gcd reduction
    private static int lcm(int a, int b)
    {
        return a * (b / gcd(a, b));
    }
    
    // finds lcm of given Fraction array 
    private static int lcm(Fraction[] input)
    {
        int result = input[0].den;
        for (int i = 1; i < input.length; i++) 
            result = lcm(result, input[i].den);
        return result;
    }
    
    // helper method to lcm that computes gcd
    private static int gcd(int a, int b)
    {
        while (b > 0)
        {
            int temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }
    
    // finds first terminal row (all zeroes)
    private boolean isTerminal(int[] a)
    {
        for (int i = 0; i < a.length; i++)
            if (a[i] != 0)
                return false;
        return true; 
    }
    
    // helper method to convert int array to probabilities 
    private Fraction[][] convertToFraction(int[][] array)
    {
        int sum;
        Fraction[][] fractionArray = new Fraction[numArrays][numArrays]; 
        for (int i = 0; i < fractionArray.length; i++)
        {
            if (isTerminalRow[i]) // is terminal row, initialize to zeroes 
            {
                for (int j = 0; j < array[0].length; j++)
                    fractionArray[i][j] = new Fraction(); 
            }
            else
            {
                sum = 0; // reset sum 
                for (int j = 0; j < array[0].length; j++)
                    sum += array[i][j]; 
                for (int j = 0; j < array[0].length; j++) // convert 
                    fractionArray[i][j] = new Fraction(array[i][j], sum);
            }
        }
        
        return fractionArray; 
    }
    
    // computes limiting matrix (currently only F * R)
    private Matrix limitingMatrix()
    {
        Matrix F = new Matrix(Q.rowSize, Q.colSize); 
        F = Q.identityMatrix(Q.rowSize).subtract(Q);
        F = F.inverse(); 
 
        return F.multiply(R);  
    }
    
    // prints out result 
    public String toString()
    {
        StringBuilder sb = new StringBuilder(); 
        for (int i : result)
            sb.append(i + " "); 
        return sb.toString(); 
    }
    
    // unit testing
    public static void main(String[] args)
    {
        int[][] testArray = {{0, 2, 1, 0, 0}, {0, 0, 0, 3, 4}, {0, 0, 0, 0, 0}, 
            {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
        AbsorbingMatrix test = new AbsorbingMatrix(testArray); 
        StdOut.println(test.matrix); 
        StdOut.println("R: \n" + test.R); 
        StdOut.println("Q: \n" + test.Q); 
        StdOut.println(test.limitingMatrix()); 
        for (int i = 0; i < test.result.length; i++)
            StdOut.print(test.result[i] + " "); 
        StdOut.println(); 
        
        int[][] testArray1 = {{0, 1, 0, 0, 0, 1}, {4, 0, 0, 3, 2, 0},
        {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, 
        {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
        AbsorbingMatrix test1 = new AbsorbingMatrix(testArray1); 
        StdOut.println(test1.matrix); 
        StdOut.println("R: \n" + test1.R); 
        StdOut.println("Q: \n" + test1.Q); 
        StdOut.println(test1.limitingMatrix()); 
        for (int i = 0; i < test1.result.length; i++)
            StdOut.print(test1.result[i] + " "); 
        StdOut.println(); 
    
    }
}
