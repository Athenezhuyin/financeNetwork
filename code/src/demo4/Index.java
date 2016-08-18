package demo4;

public class Index {
	//粉丝，转发，回复，捐钱，收藏
	private double[][] A_index = 
		{
				{1,	  3,   7,  7,  7,},
				{1/3, 1,   5,  5,  5,},
				{1/7, 1/5, 1,  1,  1,},
				{1/7, 1/5, 1,  1,  1,},
				{1/7, 1/5, 1,  1,  1,}
		};
	/*
	public static void main(String[] args) {
		Index index = new Index();
		double[] weight = index.getWeight();
		System.out.println("weight:");
		for(int i=0; i<weight.length; i++){
			System.out.print(weight[i]+"\t");
		}
		System.out.println();
	}
	*/
	public double[] getColSum(){
		double[] colSum = new double[5];
		for(int i=0; i<A_index.length; i++){
			for(int j=0; j<A_index.length; j++){
				colSum[i] += A_index[j][i];
			}
		}
/*		System.out.println("colSum");
		for(int i=0; i<colSum.length; i++){
			System.out.print(colSum[i]+"\t");
		}
		System.out.println();
*/
		return colSum;
	}
	
	public double[][] getNewIndex(){
		double[][] B_index = new double[5][5];
		double[] colSum = getColSum();
//		System.out.println("B");
		for(int i=0; i<A_index.length; i++){
			for(int j=0; j<A_index.length; j++){
				B_index[j][i] = A_index[j][i] / colSum[i];
//				System.out.print(B_index[i][j] + "\t");
			}
//			System.out.println();
		}
		return B_index;
	}
	
	public double[] getRowSum(){
		double[][] B_index = getNewIndex();
		double[] rowSum = new double[5];
		
		for(int i=0; i<B_index.length; i++){
			for(int j=0; j<B_index.length; j++){
				rowSum[i] += B_index[i][j];
			}
		}
/*		System.out.println("rowSum");
		for(int i=0; i<rowSum.length; i++){
			System.out.print(rowSum[i]+"\t");
		}
		System.out.println();
*/
		return rowSum;
	}
	
	public double[] getWeight(){
		double[] weight = new double[5];
		double[] rowSum = getRowSum();
		
		for(int i=0; i<rowSum.length; i++){
			weight[i] = rowSum[i] / 5;
		}
		return weight;
	}
}
