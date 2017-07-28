package com.asu.seatr.calibration;

import java.util.ArrayList;

import com.asu.seatr.helpers.*;
import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Operations;
import com.asu.seatr.utils.Utils;

/**
 * @author Venkata Krishna Bandla created on July 27, 2017
 *
 */
public class EstimateTransition {
	
	public static void estimateTransition(){
		
		int NK = GlobalConstants.total_KCs;
		
		for (int K = 0; K < GlobalConstants.total_KCs; K++) {
			int Kc = Utils.getKc(K);
			Double LearnNumerator = new Double(0);
			Double LearnDenominator = new Double(0);

			for (int St = 0; St < GlobalConstants.total_Students; St++) {
				int S = Utils.getStudent(St);
				for (int T = 1; T < Utils.getLast(S) - 1; T++) {
					
					int Q = Utils.getQuestion(S, T);
					ArrayList<Integer> KCs = Utils.getQuestionMatrix(Q);
					double KN = KCs.size();
					
					double numPart1 = Operations.multiplyDouble(AlphaHelperFunction.Alpha(S,Kc,T,0), GenericEmissionTransitionHelper.Transition(S,Kc,T+1,0,1));
					double numPart2 = Operations.multiplyDouble(BetaHelperFunction.Beta(S,Kc,T+1,1), GenericEmissionTransitionHelper.Emission4beta(S,Kc,T,1));
					
					double Num = Operations.multiplyDouble(numPart1, numPart2);
					
					double denomPart1 = Operations.multiplyDouble(AlphaHelperFunction.Alpha(S,Kc,T,1), BetaHelperFunction.Beta(S,Kc,T,1));
					double denomPart2 = Operations.multiplyDouble(AlphaHelperFunction.Alpha(S,Kc,T,0), BetaHelperFunction.Beta(S,Kc,T,0));
					
					double Denom = Operations.addDouble(denomPart1, denomPart2);
					
					double NumeratorPart1 = Operations.divideDouble(Num, Denom);
					double NumeratorPart2 = Operations.multiplyDouble(KN, NumeratorPart1);
					
					LearnNumerator = Operations.addDouble(LearnNumerator, NumeratorPart2);
					LearnDenominator = Operations.addDouble(LearnDenominator, Operations.substractDouble((double)1, Utils.getFetchBest(S, Kc, T)));
					
				}
			}	
			
			Utils.setLearnMap(Kc, Operations.divideDouble(LearnNumerator, LearnDenominator));
		}	
		
	}
	
	

}
