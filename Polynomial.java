import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;


public class Polynomial {
	
	double [] coefficients;
	int [] exponents;

	public Polynomial(){
		coefficients = null;
		exponents = null;
	}
	
	public Polynomial(double [] coefficientArray, int [] exponentArray){
		coefficients = new double[coefficientArray.length];
		exponents = new int[exponentArray.length];

		for(int i=0; i<coefficientArray.length; i++){
			coefficients[i]=coefficientArray[i];
			exponents[i]=exponentArray[i];
		}
	}
	
	public Polynomial(File file) throws Exception{
		 BufferedReader input = new BufferedReader(new FileReader(file)); 
		 String line = input.readLine();
		 String [] polynomials = line.split("[+-]");
		 
		 int length = polynomials.length;
		 coefficients = new double[length];
		 exponents = new int[length];
		 
		 for(int i=0; i<polynomials.length;i++) {
			 String [] split = polynomials[i].split("x");
			 if(split.length==1) {
				 coefficients[i]=Double.parseDouble(split[0]);
				 exponents[i]=0;
			 }else {
				 coefficients[i]=Double.parseDouble(split[0]);
				 exponents[i]=Integer.parseInt(split[1]);
			 }
		 }
		 
		 System.out.println("file exponents: " + Arrays.toString(this.exponents));
		 System.out.println("file coefficients: " + Arrays.toString(this.coefficients));
		 input.close();
	}
	
	public void saveToFile(String file) throws Exception {
		String polynomial = "";
		
		for(int i=0;i<this.coefficients.length;i++) {
			if(this.coefficients[i]==0) {
				polynomial += "";
			}else if (this.exponents[i]==0) {
				if(i!=0 && coefficients[i]>=0) polynomial = polynomial + "+" + (int)this.coefficients[i];
				else polynomial = polynomial + (int)this.coefficients[i];
			}else {
				if(i!=0 && coefficients[i]>=0) polynomial = polynomial + "+" + (int)this.coefficients[i] + "x" + (int)this.exponents[i];
				else polynomial = polynomial + (int)this.coefficients[i] + "x" + (int)this.exponents[i];
			}
		}
		
		PrintStream ps = new PrintStream(file);
		ps.println(polynomial);
	}
	
	public boolean inArray(int x, int[] exponent) {
		for(int i=0;i<exponent.length;i++) {
			if (x == exponent[i]) {
				return true;
			}
		}
		return false;
	}

	public Polynomial add(Polynomial x){
		int length=this.exponents.length;
		
		//finds length
		for(int i=0; i<x.coefficients.length;i++) {
			boolean isNew= false;
			for(int j=0; j<this.coefficients.length;j++) {
				if(x.exponents[i]==this.exponents[j]) {
					isNew = true;
				}
			}
			if(isNew == false) {
				length++;
			}
		}
		
		double [] addCoefficients = new double[length];
		int [] exponents = new int[length];
		
		for (int i=0; i<this.exponents.length;i++) {
			exponents[i] = this.exponents[i];
		}
		
		int count=0;
		for (int i=0; i<x.exponents.length;i++) {
			if (inArray(x.exponents[i], exponents) == false) {
				exponents[this.exponents.length+count] = x.exponents[i];
				count ++;
			}
		}
		
		Arrays.sort(exponents);
		
//		System.out.println("p1 exponents: " + Arrays.toString(this.exponents));
//		System.out.println("p2 exponents: " + Arrays.toString(x.exponents));
//		System.out.println("length: " + length);
		
		for (int i=0; i<length; i++) {
			for(int j=0; j<this.exponents.length;j++) {
				if (exponents[i]==this.exponents[j]) {
					addCoefficients[i] += this.coefficients[j];
				}
			}
			
			for(int k=0; k<x.exponents.length;k++) {
				if (exponents[i]==x.exponents[k]) {
					addCoefficients[i] += x.coefficients[k];
				}
			}
		}
		
//		System.out.println("p1 coefficients: " + Arrays.toString(this.coefficients));
//		System.out.println("p2 coefficients: " + Arrays.toString(x.coefficients));
//		
//		System.out.println("exponents: " + Arrays.toString(exponents));
//		System.out.println("coefficients: " + Arrays.toString(addCoefficients));
		
		int finalLength =0;
		int exponentLength =0;
		
		for(int i=0; i<length;i++) {
			if(addCoefficients[i]==0) {
				exponentLength ++;
			}
		}
		
		int [] finalExponent = new int[length-exponentLength];
		
		int counter=0;
		for(int i=0; i<addCoefficients.length;i++) {
			if(addCoefficients[i]!=0 && counter<=length-exponentLength) {
				finalExponent[counter]=exponents[i];
				counter++;
			}
		}
		
		for( int sourceIndex = 0;  sourceIndex < addCoefficients.length;  sourceIndex++ )
		{
		    if( addCoefficients[sourceIndex] != 0 ) {
		    	addCoefficients[finalLength++] = addCoefficients[sourceIndex];
		    }
		}
		
		
		
		double [] finalCoefficients =new double[finalLength];
		System.arraycopy( addCoefficients, 0, finalCoefficients, 0, finalLength );
		
//		System.out.println("final exponents: " + Arrays.toString(finalExponent));
//		System.out.println("final coefficients: " + Arrays.toString(finalCoefficients));
		
		Polynomial result = new Polynomial (finalCoefficients, finalExponent);

		return result;
	}
	
	public double evaluate(double x){
		double result=0;

		for (int i=0; i<this.coefficients.length;i++){
			result += this.coefficients[i]*(Math.pow(x, this.exponents[i]));
		}

		return result;
	}

	public boolean hasRoot(double x){
		return evaluate(x) == 0;
	}
	
	public Polynomial multiply(Polynomial x) {
		int length = this.coefficients.length * x.coefficients.length;
		
		double [] multiplyCoefficients = new double[length];
		int [] exponents = new int[length];
		
		int counter=0;
		
		for(int i=0; i<this.coefficients.length;i++) {
			for(int j=0; j<x.coefficients.length;j++) {
				multiplyCoefficients[counter]=this.coefficients[i]*x.coefficients[j];
				exponents[counter]=this.exponents[i]+x.exponents[j];
				counter++;
			}
		}
		
//		System.out.println("coefficients: " + Arrays.toString(multiplyCoefficients));
//		System.out.println("exponents: " + Arrays.toString(exponents));
		
		int finalLength=1;
		
		int [] copyArray = new int[length];
		System.arraycopy(exponents, 0, copyArray, 0, length);
		
		Arrays.sort(copyArray);
		for(int i=1; i<length;i++) {
			if(copyArray[i - 1] != copyArray[i]) {
				finalLength++;
			}
		}
//		System.out.println("final length: " + finalLength);
		
		double [] finalCoefficients = new double[finalLength];
		int [] finalExponents = new int[finalLength];
		
		int count=0;
		for (int i=0; i<length;i++) {
			if (inArray(exponents[i], finalExponents) == false) {
				finalExponents[count] = exponents[i];
				count ++;
			}
		}
		
		Arrays.sort(finalExponents);
//		System.out.println("final exponents: " + Arrays.toString(finalExponents));
		
		for(int i=0;i<finalLength;i++) {
			for(int j=0;j<length;j++) {
				if(finalExponents[i]==exponents[j]) {
					finalCoefficients[i] += multiplyCoefficients[j];
				}
			}
		}
		
		int exponentLength=0;
		
		for(int i=0;i<finalLength;i++) {
			if(finalCoefficients[i]==0) {
				exponentLength++;
			}
		}
		
		int [] removeZeroExponents = new int[finalLength-exponentLength];
		
		int counts=0;
		for(int i=0; i<finalCoefficients.length;i++) {
			if(finalCoefficients[i]!=0 && counts<=finalLength-exponentLength) {
				removeZeroExponents[counts]=finalExponents[i];
				counts++;
			}
		}
		
		int end=0;
		for( int sourceIndex = 0;  sourceIndex < finalCoefficients.length;  sourceIndex++ )
		{
		    if( finalCoefficients[sourceIndex] != 0 ) {
		    	finalCoefficients[end++] = finalCoefficients[sourceIndex];
		    }
		}
		
		
		
		double [] removeZeroCoefficients = new double[finalLength-exponentLength];
		System.arraycopy( finalCoefficients, 0, removeZeroCoefficients, 0, end );
		
//		System.out.println("final coefficients: " + Arrays.toString(finalCoefficients));
//		System.out.println("no 0 exponents: " + Arrays.toString(removeZeroExponents));
//		System.out.println("no 0 coefficients: " + Arrays.toString(removeZeroCoefficients));
		
		Polynomial result = new Polynomial (removeZeroCoefficients, removeZeroExponents);
		return result;
	}
}