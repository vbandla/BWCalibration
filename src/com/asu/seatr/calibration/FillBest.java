package com.asu.seatr.calibration;

import com.asu.seatr.helpers.AlphaHelperFunction;
import com.asu.seatr.helpers.BetaHelperFunction;
import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Operations;
import com.asu.seatr.utils.Utils;

/**
 * @author Venkata Krishna Bandla created on July 25, 2017
 *
 */
public class FillBest {

	public static void fillBest(){
		
		int NS = GlobalConstants.total_Students;
		int NK = GlobalConstants.total_KCs;
	
		for(int St = 0 ; St < NS ; St++){
			int S = Utils.getStudent(St);
		
			for (int T = 1; T <= Utils.getLast(S); T++) {
				
				for(int K = 0; K < NK; K++){
					int innerKc = Utils.getKc(K);
					
					double Numerator = Operations.multiplyDouble(AlphaHelperFunction.Alpha(S, innerKc, T, 1), BetaHelperFunction.Beta(S,innerKc,T,1));
					double denominatorPart1 = Operations.multiplyDouble(AlphaHelperFunction.Alpha(S, innerKc, T, 0), BetaHelperFunction.Beta(S,innerKc,T,0));
					double denominatorFinal = Operations.addDouble(Numerator, denominatorPart1);
					
					double fillbest = Operations.divideDouble(Numerator, denominatorFinal);
					
					Utils.updateFetchBest(S, innerKc, T, fillbest);
				}	
			}
		}
	}
}
