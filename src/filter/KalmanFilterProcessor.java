package filter;

import org.apache.commons.math3.filter.DefaultMeasurementModel;
import org.apache.commons.math3.filter.DefaultProcessModel;
import org.apache.commons.math3.filter.KalmanFilter;
import org.apache.commons.math3.filter.MeasurementModel;
import org.apache.commons.math3.filter.ProcessModel;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.CholeskyDecomposition;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * Using Apache math library for prediction.
 * @author fraenklt
 *
 */
public class KalmanFilterProcessor {

	private KalmanFilter filter;
	private RealVector x;
	private RealMatrix P0;
	private RealMatrix A;

	private RealMatrix Q;
	private RealMatrix R;
	private RealMatrix H;
	private RealMatrix B;
	private Array2DRowRealMatrix I;

	public KalmanFilterProcessor(double initX , double initY , double initVeloX , double initVeloY , double dt) {


		x = new ArrayRealVector(new double[] { initX , initY , initVeloX , initVeloY  });
		P0 = new Array2DRowRealMatrix(new double[][] {{ 0, 0 , 0 , 0} , { 0 ,  0 , 0 , 0  } , { 0 , 0 , 1000.0  , 0  } , { 0 , 0 , 0.0  , 1000.0  }} );

		I = new Array2DRowRealMatrix(new double[][] {{ 1, 0  , 0 , 0} , { 0 , 1 , 0 , 0 } ,  { 0 , 0 , 1 , 0 } ,   { 0 , 0 , 0 , 1 }} );
		B = null;
		A = new Array2DRowRealMatrix(new double[][] {{ 1 , 0 , dt , 0 } , { 0 , 1 , 0 , dt } , { 0 , 0  , 1 , 0 }, { 0 , 0  , 0 , 1 }} );
		Q = new Array2DRowRealMatrix(new double[][] {{ 0 , 0 , 0 ,0} , { 0 , 0 , 0 , 0} } );

		H = new Array2DRowRealMatrix(new double[][] { {1.0 , 0.0 , 0.0 , 0.0} , {0.0 , 1.0 , 0.0 , 0.0 } }  );
		R = new Array2DRowRealMatrix(new double[][] { {0.1  , 0.0}, {0.0  , 0.1  } }   );

	}


	public double[] estimate_next_position(double xPos , double yPos){

		x = A.operate(x);
		P0 = (Array2DRowRealMatrix) A.multiply(P0).multiply(A.transpose());
//		Measurement Update (Correction)

		Array2DRowRealMatrix S = (Array2DRowRealMatrix) H.multiply(P0).multiply(H.transpose()).add(R);
		RealMatrix sInverse = MatrixUtils.inverse(S);
		Array2DRowRealMatrix K = (Array2DRowRealMatrix) P0.multiply(H.transpose()).multiply(sInverse);

		RealVector Z = new ArrayRealVector(new double[] { xPos, yPos });

//		# Update the estimate via z
		RealVector y = Z.subtract(H.operate(x));
		x = x.add(K.operate(y));
//		# Update the error covariance

		RealMatrix t2 = K.multiply(H);
		RealMatrix temp = I.subtract(t2);
		P0 = temp.multiply(P0);

		return x.toArray();
	}


}
