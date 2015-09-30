package filter;


/**
 * Using Apache math library for prediction.
 * @author fraenklt
 *
 */
public class SimpleTestKalmanFilterProcessor {

	private KalmanFilterProcessor kalman;

//	double[][] measurements = new double[][]{{5,10} , {6,8} , {7,6}, {8,4}, {9,2} , {10,0} };
	double[][] measurements = new double[][]{{1,4} , {6,0} , {11,-4}, {16,-8} };


//	double[] init = new double[]{4,12};
	double[] init = new double[]{-4,8};
//	double[] init = new double[]{4,12};


	public SimpleTestKalmanFilterProcessor() {

		kalman = new KalmanFilterProcessor(init[0], init[1] , 1. , 1. , 1.  , 0.1);
	}

	public static void main(String[] args) {

		SimpleTestKalmanFilterProcessor simple = new SimpleTestKalmanFilterProcessor();
		simple.test();
	}

	private void test() {

		double[] erg = null;
		for (int i = 0; i < measurements.length; i++) {

			erg = kalman.estimate_next_position(measurements[i][0], measurements[i][1]);

		}
		System.out.println(erg[0] +" , "+ erg[1] + " , "+erg[2] +" , "+ erg[3]);
	}

}
