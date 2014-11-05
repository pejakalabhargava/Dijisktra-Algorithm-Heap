/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dijkstrafib;


import java.util.*;
import java.lang.Object.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.generate.*;


import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel. Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author amitskatti
 */
public class DijkstraMain {
    
    
    static double [][] SpareResult = new double [30][4];
    static double [][] DenseResult = new double [30][4];

    
    
    public static void exportData(String fileName, String tabName, double[][] data) throws FileNotFoundException, IOException
    {
        //Create new workbook and tab
        Workbook wb = new HSSFWorkbook();
        FileOutputStream fileOut = new FileOutputStream(fileName);
        Sheet sheet = wb.createSheet(tabName);
 
        //Create 2D Cell Array
        Row[] row = new Row[data.length];
        Cell[][] cell = new Cell[row.length][];
 
        //Define and Assign Cell Data from Given
        for(int i = 0; i < row.length; i ++)
        {
            row[i] = sheet.createRow(i);
            cell[i] = new Cell[data[i].length];
 
            for(int j = 0; j < cell[i].length; j ++)
            {
                cell[i][j] = row[i].createCell(j);
                cell[i][j].setCellValue(data[i][j]);
            }
        }
 
        //Export Data
        wb.write(fileOut);
        fileOut.close();
    }
    
    
    public static  double[] SparseGraphDijkstraExperiment(int vertNum, int edgeNum){
       
       int NumberOfVertices = vertNum;
       int NumberOfEdges = edgeNum;
       double [] result = new double[3];
       
       java.util.Random edgeR = new Random();
       
       long start_time;
       
       Map<Integer, Double> m;
       
       long end_time;
       double difference;
       
 
       RandomGraphGenerator<Integer, DefaultWeightedEdge> randomSparseGenerator = new RandomGraphGenerator<Integer, DefaultWeightedEdge>(NumberOfVertices, NumberOfEdges);      
       SimpleWeightedGraph<Integer, DefaultWeightedEdge> sparseGraph = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
       VertexFactory intfact = new IntegerVertexFactory(0);
       
       randomSparseGenerator.generateGraph(sparseGraph, intfact, null);
       Set <DefaultWeightedEdge> edges = sparseGraph.edgeSet();
     
       DirectedGraph dgraph = new DirectedGraph();
       for(int i=1; i<=NumberOfVertices; i++)
                dgraph.addNode(i);
 
       for (DefaultWeightedEdge e : edges)
       {
           int SourceV = sparseGraph.getEdgeSource(e);
           int DestV = sparseGraph.getEdgeTarget(e);
           double Weight = edgeR.nextInt(20)+1;
           dgraph.addEdge(SourceV, DestV, Weight);           
       }
       
       int Source = 1;
       for (int i = 2; i <= NumberOfVertices; i++)
       {
           if (sparseGraph.degreeOf(i) > sparseGraph.degreeOf(Source))
               Source = i;
       }
       
       Iterator<Integer> it = dgraph.iterator();
       while (it.hasNext())
       {
           Object obj = it.next();
           System.out.print(obj + "    ");
           System.out.println(dgraph.edgesFrom(obj));
       }
       
       start_time = System.nanoTime();
       m = DijkstraPlain.shortestPaths(dgraph, Source);
       end_time = System.nanoTime();
       difference = (end_time - start_time)/1e6;
       result[0] = difference;
       for (Map.Entry<Integer, Double> x : m.entrySet())
       {
           System.out.print (x.getKey() + " - " + x.getValue() + "     ");
       }
       System.out.println ("Time taken using Unordered Arrays = " + difference);
       
       
       start_time = System.nanoTime();
        m = DijkstraBinHeap.shortestPaths(dgraph, Source);
       end_time = System.nanoTime();
       difference = (end_time - start_time)/1e6;
       result[1] = difference;
       for (Map.Entry<Integer, Double> x : m.entrySet())
       {
           System.out.print (x.getKey() + " - " + x.getValue() + "     ");
       }
       System.out.println ("Time taken using Binary Heap = " + difference);

       
       start_time = System.nanoTime();
        m = DijkstraFibHeap.shortestPaths(dgraph, Source);
       end_time = System.nanoTime();
       difference = (end_time - start_time)/1e6;
       result[2] = difference;
       for (Map.Entry<Integer, Double> x : m.entrySet())
       {
           System.out.print (x.getKey() + " - " + x.getValue() + "     ");
       }
       System.out.println ("Time taken using Fibonacci Heap = " + difference);

       return result;
    }
    
    public static double [] DenseGraphDijkstraExperiment(int vertNum){
       
       int NumberOfVertices = vertNum;
       double [] result = new double[3];
 
       java.util.Random edgeR = new Random();
       
       long start_time;
       
       Map<Integer, Double> m;
       
       long end_time;
       double difference;
        
       CompleteGraphGenerator<Integer, DefaultWeightedEdge> DenseGraphGenerator = new CompleteGraphGenerator<Integer, DefaultWeightedEdge>(NumberOfVertices);      
       Pseudograph<Integer, DefaultWeightedEdge> denseGraph = new Pseudograph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
       VertexFactory DenseVFact = new IntegerVertexFactory(0);
       
       DenseGraphGenerator.generateGraph(denseGraph, DenseVFact, null);
       Set <DefaultWeightedEdge> edges = denseGraph.edgeSet();
     
       DirectedGraph<Integer> dgraph = new DirectedGraph<Integer>();
       for(int i=1; i<=NumberOfVertices; i++)
                dgraph.addNode(i);

       for (DefaultWeightedEdge e : edges)
       {
           int SourceV = denseGraph.getEdgeSource(e);
           int DestV = denseGraph.getEdgeTarget(e);
           double Weight = edgeR.nextInt(20)+1;
           dgraph.addEdge(SourceV, DestV, Weight);           
       }

       Iterator<Integer> it = dgraph.iterator();
       Integer obj = it.next();
       System.out.print(obj + "    ");
       System.out.println(dgraph.edgesFrom(obj));
       
       start_time = System.nanoTime();
       m = DijkstraPlain.shortestPaths(dgraph, 1);
       end_time = System.nanoTime();
       difference = (end_time - start_time)/1e6;
       result[0] = difference;
       for (Map.Entry<Integer, Double> x : m.entrySet())
       {
           System.out.print (x.getKey() + " - " + x.getValue() + "     ");
       }
       System.out.println ("Time taken using Unordered Arrays = " + difference);
       

       
       start_time = System.nanoTime();
        m = DijkstraBinHeap.shortestPaths(dgraph, 1);
       end_time = System.nanoTime();
       difference = (end_time - start_time)/1e6;
       result[1] = difference;
       for (Map.Entry<Integer, Double> x : m.entrySet())
       {
           System.out.print (x.getKey() + " - " + x.getValue() + "     ");
       }
       System.out.println ("Time taken using Binary Heap = " + difference);

       
       start_time = System.nanoTime();
        m = DijkstraFibHeap.shortestPaths(dgraph, 1);
       end_time = System.nanoTime();
       difference = (end_time - start_time)/1e6;
       result[2] = difference;
       for (Map.Entry<Integer, Double> x : m.entrySet())
       {
           System.out.print (x.getKey() + " - " + x.getValue() + "     ");
       }
       System.out.println ("Time taken using Fibonacci Heap = " + difference);
       
       return result;
    }
   
    
    

    public static void main(String[] args) throws IOException {
        
        for (int i =0; i<30; i++)
            for (int j=0; j<4;j++)
            {
                SpareResult[i][j] = -1;
                DenseResult [i][j] = -1;
            }
        
        double [] tempRes;
        for (int i = 1; i<=30; i++)
        {
            int vertnum = i*100;
            SpareResult[i-1][0] = vertnum;
            tempRes = SparseGraphDijkstraExperiment(vertnum,2*vertnum);
            SpareResult[i-1][1] = tempRes[0];
            SpareResult[i-1][2] = tempRes[1];
            SpareResult[i-1][3] = tempRes[2];
        }
        

        
        for (int i = 1; i<=30; i++)
        {
            int vertnum = i*100;
            DenseResult[i-1][0] = vertnum;
            tempRes = DenseGraphDijkstraExperiment(vertnum);
            DenseResult[i-1][1] = tempRes[0];
            DenseResult[i-1][2] = tempRes[1];
            DenseResult[i-1][3] = tempRes[2];
        }
    

        
        /*Export Data to Excel File*/
        exportData("C:\\Users\\amitskatti\\Desktop\\NCSU\\CSC 505 - Algo\\Project\\SparseResult.xls","Sparse",SpareResult);
        exportData("C:\\Users\\amitskatti\\Desktop\\NCSU\\CSC 505 - Algo\\Project\\DenseResult.xls","Dense",DenseResult);
        
 
    }
 
    
}
