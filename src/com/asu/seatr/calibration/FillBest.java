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
		System.out.println("fillBest.........");
		int NS = GlobalConstants.total_Students;
		int NK = GlobalConstants.total_KCs;
	
		for(int St = 0 ; St < NS ; St++){
			int S = Utils.getStudent(St);
			for(int K = 0; K < NK; K++){
			    
				for (int T = 1; T <= Utils.getLast(S); T++) {
				
					int innerKc = Utils.getKc(K);
					
					double Numerator = Operations.multiplyDouble(AlphaHelperFunction.Alpha(S, innerKc, T, 1,"fillBest"), BetaHelperFunction.Beta(S,innerKc,T,1,"estimateTransition"));
					double denominatorPart1 = Operations.multiplyDouble(AlphaHelperFunction.Alpha(S, innerKc, T, 0,"fillBest"), BetaHelperFunction.Beta(S,innerKc,T,0,"estimateTransition"));
					System.out.println("Alpha(S, innerKc, T, 0) :"+AlphaHelperFunction.Alpha(S, innerKc, T, 0,"fillBest")+" Beta(S,innerKc,T,0)"+BetaHelperFunction.Beta(S,innerKc,T,0,"estimateTransition"));
					double denominatorFinal = Operations.addDouble(Numerator, denominatorPart1);
					
					double fillbest = Operations.divideDouble(Numerator, denominatorFinal);
					System.out.println("a1b1/a1b1+a0b0 :"+Numerator+"/"+denominatorFinal);
					Utils.updateFetchBest(S, innerKc, T, fillbest);
					int Q = Utils.getQuestion(S, T);
					System.out.println("IM :"+Utils.getInitialMasteryMap(innerKc)+"  Slip: "+Utils.getSlipMap(Q)+" Guess :"+Utils.getGuessMap(Q) + "  Lear :"+Utils.getLearnMap(innerKc));
					System.out.println("Answer = "+Utils.getAnswer(S, T)+"  Best["+T+"] :"+ " mastered = "+fillbest+"  not mastered = "+Operations.substractDouble((double)1, fillbest));
					System.out.println();
				}	
			}
			System.out.println();
		}
	}
}
