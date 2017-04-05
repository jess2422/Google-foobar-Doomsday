//package com.google.challenges; 
import edu.princeton.cs.algs4.StdOut; 

public class AnswerDoomsday 
{   
    public static void main(String[] args) 
    {
        int[][] m = {{0, 2, 1, 0, 0}, 
            {0, 0, 0, 3, 4}, 
            {0, 0, 0, 0, 0}, 
            {0, 0, 0, 0, 0}, 
            {0, 0, 0, 0, 0}};
        int[][] n = {{8, 1, 1, 0}, 
            {4, 4, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0}};
        int[][] p = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        int[][] s = {{1, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}};
        AbsorbingMatrix solution = new AbsorbingMatrix(m);
        for (int i = 0; i < solution.result.length; i++)
            StdOut.print(solution.result[i] + " "); 
        
    }
}
        

