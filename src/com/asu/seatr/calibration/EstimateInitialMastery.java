package com.asu.seatr.calibration;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Operations;
import com.asu.seatr.utils.Utils;

/**
 * @author Venkata Krishna Bandla created on July 27, 2017
 *
 */
public class EstimateInitialMastery {
	
	public static void estimateIntialMastery(){
		//System.out.println("estimateIntialMastery.........");
		int NK = GlobalConstants.total_KCs;
		int NS = GlobalConstants.total_Students;
		
		for (int K = 0; K < NK; K++) {
			int Kc = Utils.getKc(K);
			
			Double Numerator = (double)0;
			Double Denominator = (double)0;
			for (int St = 0; St < NS; St++) {
				int S = Utils.getStudent(St);
				
				Numerator = Operations.addDouble(Numerator, Utils.getFetchBest(S, Kc, 1));
				Denominator = Operations.addDouble(Denominator, (double)1);
			}
			
			Utils.setInitialMasteryMap(Kc, Operations.divideDouble(Numerator, Denominator));
		}
	}

}
