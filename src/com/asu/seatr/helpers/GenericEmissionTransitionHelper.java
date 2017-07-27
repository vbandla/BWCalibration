package com.asu.seatr.helpers;

import java.util.ArrayList;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Operations;
import com.asu.seatr.utils.Utils;

/**
 * @author Venkata Krishna Bandla created on July 25, 2017
 *
 */
public class GenericEmissionTransitionHelper {
	
	public static Double Emission4beta(int studentId, int kc, int T, int I){
		
		int Q = 0;
		double A = 0;
		
		
		Q = Utils.getQuestion(studentId, T);
		if(I == 0){
			
			if(Utils.getAnswer(studentId, Q) == 1){
				
				return Utils.getGuessMap(Q);
			}
			
			else{
				
				return Operations.substractDouble((double)1, Utils.getGuessMap(Q)).doubleValue();
			}
		}
		
		else{
			
			A = AllOtherMastered4beta(studentId, Q, kc, T);
			if(Utils.getAnswer(studentId, Q) == 1){
				
				double A_Multiply_OneMinusSlip = Operations.multiplyDouble(A, Operations.substractDouble((double)1, Utils.getSlipMap(Q)));
				double Guess_multiply_oneMinusA = Operations.multiplyDouble(Utils.getGuessMap(Q), Operations.substractDouble((double)1, A));
				double add_A_Multiply_OneMinusSlip_Guess_multiply_oneMinusA = Operations.addDouble(A_Multiply_OneMinusSlip, Guess_multiply_oneMinusA);
				
				return add_A_Multiply_OneMinusSlip_Guess_multiply_oneMinusA;
			}
			
			else{
				
				double A_multiply_Slip = Operations.multiplyDouble(A, Utils.getSlipMap(Q));
				double oneMinusGuess_multiply_oneMinusA = Operations.multiplyDouble(Operations.substractDouble((double)1, Utils.getGuessMap(Q)), Operations.substractDouble((double)1, A));
				double add_A_multiply_Slip_oneMinusGuess_multiply_oneMinusA = Operations.substractDouble(A_multiply_Slip, oneMinusGuess_multiply_oneMinusA);
				
				return add_A_multiply_Slip_oneMinusGuess_multiply_oneMinusA;
			}
			
		}
		
		
	}
	
	public static Double AllOtherMastered4beta(int studentId, int Q, int kc, int T){
		
		double result = 1;
		ArrayList<Integer> kcList = Utils.getQMatrixMap(Q);
		for (int KcIndex = 0; KcIndex < kcList.size(); KcIndex++) {
			double OtherK = kcList.get(KcIndex);
			if(OtherK != kc){
				double mastered4beta = Mastered4beta(studentId, OtherK, T);
				result = Operations.multiplyDouble(result, mastered4beta).doubleValue();
			}
			
		}
		return result;
		
	}
	
	public static Double Mastered4beta(int studentId, int OtherK, int T){
		
		
	}
	
	
	public static Double Transition(int studentId, int kc, int T, int I, int J){
		
		double L = 0;
		double lengthOfQmatrix = 0;
		double learn = 0;
		
		if(I == 1) {
			
			if(J == 1){
				return (double)1.0;
			}
			else{
				return (double)0.0;
			}
		}
		
		else{
			
			learn = Utils.getLearnMap(kc);
			lengthOfQmatrix = Utils.getQMatrixMap(Utils.getQuestion(studentId, T)).size();
			
			L = Operations.divideDouble(learn, lengthOfQmatrix).doubleValue();
			
			if(J == 1){
				
				return L;
			}
			else{
				
				return Operations.substractDouble((double)1, L).doubleValue();
			}
		}
		
	}

}
