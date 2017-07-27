package com.asu.seatr.helpers;

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
				
				double A_Multiply_OneMinusSlip = 
			}
			
		}
		
		
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
