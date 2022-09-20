public class Polynomial {
	
	double [] coefficients;

	public Polynomial(){
		coefficients = new double[]{0};
	}
	
	public Polynomial(double [] array){
		coefficients = new double[array.length];

		for(int i=0; i<array.length; i++){
			coefficients[i]=array[i];
		}
	}

	public Polynomial add(Polynomial x){
		int length;

		if (this.coefficients.length <= x.coefficients.length){
			length = x.coefficients.length;
		}
		else {
			length = this.coefficients.length;
		}

		double [] add = new double[length];
		
		for(int i=0; i<length; i++){
			
			if(i<=this.coefficients.length-1 && i<=x.coefficients.length-1){
				add[i]=this.coefficients[i]+x.coefficients[i];
				
			}
			else if(i>this.coefficients.length-1){
				add[i]=x.coefficients[i];
			}
			else{
				add[i]=this.coefficients[i];
			}

			
		}

		Polynomial result = new Polynomial (add);

		return result;
	}
	
	public double evaluate(double x){
		double result=0;

		for (int i=0; i<this.coefficients.length;i++){
			double power = 1;
			for (int j=0; j<i; j++){
				power *= x;
			}
			result += this.coefficients[i]*power;
		}

		return result;
	}

	public boolean hasRoot(double x){
		double result=0;

		for (int i=0; i<this.coefficients.length;i++){
			result += this.coefficients[i]*x;
		}
		
		return result==0;
	}
}