package com.asu.seatr.calibration;

import java.util.ArrayList;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Operations;
import com.asu.seatr.utils.Utils;
import com.asu.seatr.helpers.AlphaHelperFunction;

/**
 * @author Venkata Krishna Bandla created on July 25, 2017
 *
 */
public class FillAlpha {

	public static void fillAlpha(){
		System.out.println("fillAlpha.........");
		
		int NS = GlobalConstants.total_Students;
		int NK = GlobalConstants.total_KCs;
		//System.out.println(NS+"  "+NK);
		for(int St = 0 ; St < NS ; St++){
			int S = Utils.getStudent(St);
			//System.out.println("fillAlpha  S "+S);
			for(int K = 0; K < NK; K++){
				int innerKc = Utils.getKc(K);
				
				Utils.updateFetchAlpha2(S, innerKc, 1, 1, Utils.getInitialMasteryMap(innerKc));
				Utils.updateFetchAlpha2(S, innerKc, 1, 0, Operations.substractDouble((double) 1, Utils.getInitialMasteryMap(innerKc)));
				
				//Utils.updateFetchAlpha(S, innerKc, 0, 1, Utils.getInitialMasteryMap(innerKc));
				//Utils.updateFetchAlpha(S, innerKc, 0, 0, Operations.substractDouble((double) 1, Utils.getInitialMasteryMap(innerKc)));
			}
			
			for (int T = 2; T <= Utils.getLast(S); T++) {
				//System.out.println("T "+T);
				int question = Utils.getQuestion(S, T);
				ArrayList<Integer> KCs = Utils.getQuestionMatrix(question);
				for(int K = 0; K < NK; K++){
					//System.out.println("K "+K);
					int innerKc = Utils.getKc(K);
					if (KCs.contains(innerKc)) {
						AlphaHelperFunction.Alpha(S,innerKc,T,0,"fillAlpha");
						AlphaHelperFunction.Alpha(S,innerKc,T,1,"fillAlpha");
						
						//System.out.println();
						//System.out.println();
						
					}
					
					else{
						
						Utils.updateFetchAlpha(S, innerKc, T, 1, Utils.getFetchAlpha(S, innerKc, T-1, 1));
						Utils.updateFetchAlpha(S, innerKc, T, 0, Utils.getFetchAlpha(S, innerKc, T-1, 0));
						
					}
				}
			}	
		}
	}
}
