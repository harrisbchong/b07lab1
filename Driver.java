import java.io.File;
import java.util.Arrays;

public class Driver { 
 public static void main(String [] args) throws Exception { 
  File f = new File("C:\\Users\\chong\\b07\\b07lab1\\file.txt");
  String str = "C:\\\\Users\\\\chong\\\\b07\\\\b07lab1\\\\file.txt";
  Polynomial p = new Polynomial(); 
  //System.out.println(p.evaluate(3)); 
  double [] c1 = {6,0,0,5}; 
  int [] c1ex = {0, 1, 2, 3};
  Polynomial p1 = new Polynomial(c1, c1ex); 
  double [] c2 = {0,-2,0,0,-9}; 
  int [] c2ex = {0, 1, 2, 3, 4};
  Polynomial p2 = new Polynomial(c2,c2ex); 
  Polynomial s = p1.add(p2); 
  double [] c3 = {2,1}; 
  int [] c3ex = {1,0};
  Polynomial p3 = new Polynomial(c3,c3ex); 
  double [] c4 = {5,6}; 
  int [] c4ex = {1,0};
  Polynomial p4 = new Polynomial(c4,c4ex); 
  Polynomial t = p3.multiply(p4);
  double [] c5 = {1,1}; 
  int [] c5ex = {1,0};
  Polynomial p5 = new Polynomial(c5,c5ex); 
  double [] c6 = {1,-1}; 
  int [] c6ex = {1,0};
  Polynomial p6 = new Polynomial(c6,c6ex); 
  Polynomial u = p5.multiply(p6);
  Polynomial file = new Polynomial(f);
  p1.saveToFile(str);
  
  System.out.println("s(0.1) = " + s.evaluate(0.1)); 
  if(s.hasRoot(1)) 
   System.out.println("1 is a root of s"); 
  else 
   System.out.println("1 is not a root of s"); 
  
 } 
} 