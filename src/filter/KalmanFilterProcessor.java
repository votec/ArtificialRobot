package filter;


import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * Using Apache math library for prediction.
 * @author fraenklt
 *
 */
public class KalmanFilterProcessor {

	private RealVector X;
	private RealMatrix P;
	private RealMatrix A;
	private RealMatrix Q;
	private RealMatrix R;
	private RealMatrix H;
	private RealMatrix B;
	private Array2DRowRealMatrix I;

	public KalmanFilterProcessor(double initX , double initY , double initHeading , double initTurning_angle , double initDistance ,double measurementNoise) {

//		B = null;
//		Q = new Array2DRowRealMatrix(new double[][] {{ 0 , 0 , 0 ,0} , { 0 , 0 , 0 , 0} } );
		X = new ArrayRealVector(new double[] { initX , initY , initHeading , initTurning_angle , initDistance  });
		I = (Array2DRowRealMatrix) MatrixUtils.createRealIdentityMatrix(X.getDimension());
		P = I.scalarMultiply(1000.0);
		H = new Array2DRowRealMatrix(new double[][] { {1.0 , 0.0 , 0.0 , 0.0 , 0.0} , {0.0 , 1.0 , 0.0 , 0.0 , 0.0 } }  );
		R = new Array2DRowRealMatrix(new double[][] { {measurementNoise  , 0.0}, {0.0  , measurementNoise  } }   );

	}

	public double[] estimate_next_position(double xPos , double yPos){

		RealVector Z = new ArrayRealVector(new double[] { xPos, yPos });

		// measurement update
		RealVector Y = Z.subtract(H.operate(X));
		Array2DRowRealMatrix S = (Array2DRowRealMatrix) H.multiply(P).multiply(H.transpose()).add(R);
		RealMatrix sInverse = MatrixUtils.inverse(S);
		Array2DRowRealMatrix K = (Array2DRowRealMatrix) P.multiply(H.transpose()).multiply(sInverse);
		X = X.add(K.operate(Y));
		RealMatrix t2 = K.multiply(H);
		RealMatrix temp = I.subtract(t2);
		P = temp.multiply(P);


		//----------------------------------------------------
		// predict
		double x = X.getEntry(0);
		double y = X.getEntry(1);
		double heading = X.getEntry(2);
		double turning = X.getEntry(3);
		double distance = X.getEntry(4);

		double[] updateRow0 = new double[]{1.0,0.0,distance*Math.cos(heading +turning), distance*Math.cos(heading +turning) , Math.sin(heading +turning)};
		double[] updateRow1 = new double[]{0.0,1.0,-distance*Math.sin(heading +turning), -distance*Math.sin(heading +turning) , Math.cos(heading +turning)};

		A = new Array2DRowRealMatrix(new double[][] {updateRow0 , updateRow1 , { 0.0 , 0.0 , 1.0 , 1.0 , 0.0 }, { 0.0 , 0.0  , 0.0 , 1.0 , 0.0 } , { 0.0 , 0.0  , 0.0 , 0.0 , 1.0 }} );

		// update P
		P = (Array2DRowRealMatrix) A.multiply(P).multiply(A.transpose());

		// update X
		X.setEntry(0, x + distance*Math.sin(heading + turning));
		X.setEntry(1, y + distance*Math.cos(heading + turning));
		X.setEntry(2, heading + turning);
		X.setEntry(3, turning);
		X.setEntry(4, distance);

		return X.toArray();
	}


}
