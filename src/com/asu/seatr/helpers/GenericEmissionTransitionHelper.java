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
		//System.out.println();
		//System.out.println("  Emission4beta.........");
		int Q = 0;
		double A = 0;
		
		
		Q = Utils.getQuestion(studentId, T);
		if(I == 0){
			
			if(Utils.getAnswer(studentId, T) == 1){
				//System.out.print("  Utils.getGuessMap(Q) :"+Utils.getGuessMap(Q));
				return Utils.getGuessMap(Q);
			}
			
			else{
				//System.out.print(" 1-guess :"+Operations.substractDouble((double)1, Utils.getGuessMap(Q)));
				return Operations.substractDouble((double)1, Utils.getGuessMap(Q));
			}
		}
		
		else{
			
			A = AllOtherMastered4beta(studentId, Q, kc, T);
			if(Utils.getAnswer(studentId, T) == 1){
				
				double A_Multiply_OneMinusSlip = Operations.multiplyDouble(A, Operations.substractDouble((double)1, Utils.getSlipMap(Q)));
				double Guess_multiply_oneMinusA = Operations.multiplyDouble(Utils.getGuessMap(Q), Operations.substractDouble((double)1, A));
				double add_A_Multiply_OneMinusSlip_Guess_multiply_oneMinusA = Operations.addDouble(A_Multiply_OneMinusSlip, Guess_multiply_oneMinusA);
				//System.out.print("  (A*(1-Slip[Q]) + Guess[Q]*(1-A)) :"+add_A_Multiply_OneMinusSlip_Guess_multiply_oneMinusA+"   =  (A*(1-Slip[Q]) ="+A_Multiply_OneMinusSlip+"  Guess[Q]*(1-A) ="+Guess_multiply_oneMinusA);
				return add_A_Multiply_OneMinusSlip_Guess_multiply_oneMinusA;
			}
			
			else{
				
				double A_multiply_Slip = Operations.multiplyDouble(A, Utils.getSlipMap(Q));
				double oneMinusGuess_multiply_oneMinusA = Operations.multiplyDouble(Operations.substractDouble((double)1, Utils.getGuessMap(Q)), Operations.substractDouble((double)1, A));
				double add_A_multiply_Slip_oneMinusGuess_multiply_oneMinusA = Operations.addDouble(A_multiply_Slip, oneMinusGuess_multiply_oneMinusA);
				//System.out.print("  (A*Slip[Q] + (1-Guess[Q])*(1-A)) :"+add_A_multiply_Slip_oneMinusGuess_multiply_oneMinusA+"   =  A*Slip[Q] ="+A_multiply_Slip+"  (1-Guess[Q])*(1-A) ="+oneMinusGuess_multiply_oneMinusA);
				return add_A_multiply_Slip_oneMinusGuess_multiply_oneMinusA;
			}
			
		}
		
		
	}
	
	public static Double AllOtherMastered4beta(int studentId, int Q, int kc, int T){
		//System.out.println("AllOtherMastered4beta.........");
		double result = 1;
		
		ArrayList<Integer> kcList = Utils.getQuestionMatrix(Q);
		for (int KcIndex = 0; KcIndex < kcList.size(); KcIndex++) {
			int OtherK = kcList.get(KcIndex);
			if(OtherK != kc){
				double mastered4beta = Mastered4beta(studentId, OtherK, T);
				result = Operations.multiplyDouble(result, mastered4beta);
			}
			
		}
		return result;
		
	}
	
	public static Double Mastered4beta(int studentId, int OtherK, int T){
		//System.out.println("Mastered4beta.........");
		double resultMastered4beta = 0;
		
		//System.out.println("studentId"+studentId+"OtherK"+OtherK+"T"+T+"1");
		double numeratorBeta  = Operations.multiplyDouble(BetaHelperFunction.Beta(studentId, OtherK, T, 1,"Mastered4beta"), Utils.getInitialMasteryMap(OtherK));  
		double denominatorPart1 = Operations.multiplyDouble(BetaHelperFunction.Beta(studentId, OtherK, T, 1,"Mastered4beta"), Utils.getInitialMasteryMap(OtherK));
		double denominatorPart2 = Operations.multiplyDouble(BetaHelperFunction.Beta(studentId, OtherK, T, 0,"Mastered4beta"), Operations.substractDouble((double)1 , Utils.getInitialMasteryMap(OtherK)));
		double denominatorFinal = Operations.addDouble(denominatorPart1, denominatorPart2);
		                          
		resultMastered4beta = Operations.divideDouble(numeratorBeta, denominatorFinal);
		return resultMastered4beta;
		
				
	}
	
	
	/*
	 * Emission4alpha
	 * 
	 * 
	 */
	
	
	public static Double Emission4alpha(int studentId, int kc, int T, int I){
		//System.out.println("Emission4alpha.........");
		int Q = 0;
		double A = 0;
		
		//System.out.println(" studentId :"+studentId+" T :"+T);
		Q = Utils.getQuestion(studentId, T);
		//System.out.println("Q "+Q);
		if(I == 0){
			
			if(Utils.getAnswer(studentId, T) == 1){
				//System.out.println("Utils.getGuessMap(Q) "+Utils.getGuessMap(Q));
				return Utils.getGuessMap(Q);
			}
			
			else{
				//System.out.println("Operations.substractDouble((double)1, Utils.getGuessMap(Q)) "+Operations.substractDouble((double)1, Utils.getGuessMap(Q)));
				return Operations.substractDouble((double)1, Utils.getGuessMap(Q));
			}
		}
		
		else{
			
			A = AllOtherMastered4alpha(studentId, Q, kc, T);
			if(Utils.getAnswer(studentId, T) == 1){
				
				double A_Multiply_OneMinusSlip = Operations.multiplyDouble(A, Operations.substractDouble((double)1, Utils.getSlipMap(Q)));
				double Guess_multiply_oneMinusA = Operations.multiplyDouble(Utils.getGuessMap(Q), Operations.substractDouble((double)1, A));
				double add_A_Multiply_OneMinusSlip_Guess_multiply_oneMinusA = Operations.addDouble(A_Multiply_OneMinusSlip, Guess_multiply_oneMinusA);
				
				/*System.out.println(" A " + A);
				System.out.println(" slip "+ Utils.getSlipMap(Q));
				System.out.println("A_Multiply_OneMinusSlip "+A_Multiply_OneMinusSlip);
				System.out.println("Guess_multiply_oneMinusA "+Guess_multiply_oneMinusA);
				System.out.println("add_A_Multiply_OneMinusSlip_Guess_multiply_oneMinusA "+add_A_Multiply_OneMinusSlip_Guess_multiply_oneMinusA);
*/				return add_A_Multiply_OneMinusSlip_Guess_multiply_oneMinusA;
			}
			
			else{
				
				double A_multiply_Slip = Operations.multiplyDouble(A, Utils.getSlipMap(Q));
				double oneMinusGuess_multiply_oneMinusA = Operations.multiplyDouble(Operations.substractDouble((double)1, Utils.getGuessMap(Q)), Operations.substractDouble((double)1, A));
				double add_A_multiply_Slip_oneMinusGuess_multiply_oneMinusA = Operations.addDouble(A_multiply_Slip, oneMinusGuess_multiply_oneMinusA);
				
				/*System.out.println(" A " + A);
				System.out.println(" slip "+ Utils.getSlipMap(Q));
				System.out.println("A_multiply_Slip "+A_multiply_Slip);
				System.out.println("oneMinusGuess_multiply_oneMinusA "+oneMinusGuess_multiply_oneMinusA);
				System.out.println("add_A_multiply_Slip_oneMinusGuess_multiply_oneMinusA "+add_A_multiply_Slip_oneMinusGuess_multiply_oneMinusA);
*/				return add_A_multiply_Slip_oneMinusGuess_multiply_oneMinusA;
			}
			
		}
		
		
	}
	
	
	
public static Double AllOtherMastered4alpha(int studentId, int Q, int kc, int T){
	//System.out.println("AllOtherMastered4alpha.........");
		double result = 1;
		
		ArrayList<Integer> kcList = Utils.getQuestionMatrix(Q);
		if(kcList == null){
			//System.out.println(" kcList is null ");
		}
		for (int KcIndex = 0; KcIndex < kcList.size(); KcIndex++) {
			int OtherK = kcList.get(KcIndex);
			if(OtherK != kc){
				double mastered4alpha = Mastered4alpha(studentId, OtherK, T);
				result = Operations.multiplyDouble(result, mastered4alpha);
			}
			
		}
		return result;
		
	}

public static Double Mastered4alpha(int studentId, int OtherK, int T){
	//System.out.println("Mastered4alpha.........");
	double resultMastered4alpha = 0;
	//System.out.println("Mastered4alpha T:"+T);
	double numerator = AlphaHelperFunction.Alpha2(studentId, OtherK, T, 1);
	double denominatorPart1 = AlphaHelperFunction.Alpha2(studentId, OtherK, T, 1);
	double denominatorPart2 = AlphaHelperFunction.Alpha2(studentId, OtherK, T, 0);
	double denominatorFinal = Operations.addDouble(denominatorPart1, denominatorPart2);
	
	resultMastered4alpha = Operations.divideDouble(numerator, denominatorFinal);
	
	return resultMastered4alpha;
	
}



	
	public static Double Transition(int studentId, int kc, int T, int I, int J){
		//System.out.println("Transition.........");
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
			//lengthOfQmatrix = Utils.getQuestionMatrix(Utils.getQuestion(studentId, T)).size();
			//System.out.println("learn "+learn);
			//System.out.println("lengthOfQmatrix "+lengthOfQmatrix);
			//L = Operations.divideDouble(learn, lengthOfQmatrix);
			L = learn;
			if(J == 1){
				//System.out.println("J "+J +"  L"+L);
				return L;
			}
			else{
				//System.out.println("J "+J +" 1-L"+Operations.substractDouble((double)1, L));
				return Operations.substractDouble((double)1, L);
			}
		}
		
	}

}
