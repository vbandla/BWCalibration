package com.asu.seatr.calibration;

import java.util.ArrayList;
import java.util.Arrays;

import com.asu.seatr.helpers.BetaHelperFunction;
import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Utils;

/**
 * @author Venkata Krishna Bandla created on July 25, 2017
 *
 */
public class FillBeta {

	
	public static void fillBeta(){
		System.out.println("fillBeta.........");
		int NS = GlobalConstants.total_Students;
		int NK = GlobalConstants.total_KCs;
	
		for(int St = 0 ; St < NS ; St++){
			int S = Utils.getStudent(St);
		
			for(int K = 0; K < NK; K++){
				int innerKc = Utils.getKc(K);
				Utils.updateFetchBeta(S, innerKc, Utils.getLast(S), 1, 1.0);
				Utils.updateFetchBeta(S, innerKc, Utils.getLast(S), 0, 1.0);
						
			}
			
			for (int T = Utils.getLast(S) - 1; T >= 1; T--) {
				
				int question = Utils.getQuestion(S, T);
				ArrayList<Integer> KCs = Utils.getQuestionMatrix(question);
				
				for(int K = 0; K < NK; K++){
					
					int innerKc = Utils.getKc(K);
					if (KCs.contains(innerKc)) {
						
						BetaHelperFunction.Beta(S,innerKc,T,1);
						BetaHelperFunction.Beta(S,innerKc,T,0);
					}
					
					else{
						Utils.updateFetchBeta(S, innerKc, T, 1, Utils.getFetchBeta(S, innerKc, T+1, 1));
						Utils.updateFetchBeta(S, innerKc, T, 0, Utils.getFetchBeta(S, innerKc, T+1, 0));
						
					}
						
				}
			}
		
		}
	
	}
	
}






