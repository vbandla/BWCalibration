package com.asu.seatr.calibration;

import java.util.ArrayList;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Operations;
import com.asu.seatr.utils.Utils;

/**
 * @author Venkata Krishna Bandla created on July 27, 2017
 *
 */
public class EstimateSlipAndGuess {

	public static void estimateSlipAndGuess(){
		System.out.println("estimateSlipAndGuess.........");
		int NQ = GlobalConstants.total_Questions;
		int NS = GlobalConstants.total_Students;
		int NK = GlobalConstants.total_KCs;
		
		for (int Qi = 0; Qi < NQ ; Qi++) {
		
			int Q = Utils.getQuestion(Qi);
			
			Double SlipNumerator = (double)0;
			Double SlipDenominator = (double)0;
			Double GuessNumerator = (double)0;
			Double GuessDenominator = (double)0;
			
			for (int St = 0; St < NS ; St++) {
				int S = Utils.getStudent(St);
				
				for (int T = 1; T <= Utils.getLast(S); T++) {
					if (Q == Utils.getQuestion(S, T)) {
						Double AllMastered = new Double(1.0);
						ArrayList<Integer> KCs = Utils.getQuestionMatrix(Q);
						for (int list_K = 0; list_K < KCs.size(); list_K++) {
							AllMastered = Operations.multiplyDouble(Utils.getFetchBest(S, KCs.get(list_K), T), AllMastered);
						}
						//System.out.println(" AllMastered " + AllMastered);
						//SlipDenominator=Operations.addDouble(SlipDenominator, (double) 1);
						SlipDenominator = Operations.addDouble(SlipDenominator, AllMastered);
						GuessDenominator = Operations.addDouble(GuessDenominator, Operations.substractDouble((double) 1, AllMastered));
						//System.out.println("2 SAGAR getAnswer_S_A_Q("+S+","+T+","+Utils.getQuestion(S, T)+")"+Utils.getAnswer(S, T));
						if(Utils.getAnswer(S, T) == 0){
							SlipNumerator = Operations.addDouble(SlipNumerator, AllMastered);
						}else{
							GuessNumerator = Operations.addDouble(GuessNumerator, Operations.substractDouble((double) 1, AllMastered));
						}
					}
				}
			}
			
			//System.out.println(" Q " + Q + " SlipNumerator " + SlipNumerator + " SlipDenominator " + SlipDenominator);
			//System.out.println(" Q " + Q + " GuessNumerator " + GuessNumerator + " GuessDenominator " + GuessDenominator);
			//System.out.println("SETTING SLIP and GUESS");
			if(Q==0)System.out.println(" Slip[" + Q + "]  " + Operations.divideDouble(SlipNumerator, SlipDenominator));
			Utils.setSlipMap(Q,  Operations.divideDouble(SlipNumerator, SlipDenominator));
			Utils.setGuessMap(Q, Operations.divideDouble(GuessNumerator, GuessDenominator));
		}
	}
}
