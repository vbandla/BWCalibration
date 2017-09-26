package com.asu.seatr.helpers;

import com.asu.seatr.utils.Utils;

/**
 * @author Venkata Krishna Bandla created on July 25, 2017
 *
 */
public class BetaHelperFunction {

public static Double Beta(int studentId, int kc, int T, int I){
	//System.out.println("Beta.........");
		Double result = null;
		double fetchBeta = 0;
		result = Utils.getFetchBeta(studentId, kc, T, I);

		if(result == null){
			
			fetchBeta = BetaCalc(studentId, kc, T, I);
	
			Utils.updateFetchBeta(studentId, kc, T, I, fetchBeta);
			return fetchBeta;
		}
		
		return result.doubleValue();
		
	}

public static Double BetaCalc(int studentId, int kc, int T, int I){
	//System.out.println("BetaCalc.........");
	double betaCalc = 0;
	if(T == Utils.getLast(studentId)){
		return (double)1;
	}
	
	else{
		
		betaCalc = ((Beta(studentId,kc,T+1,0) * GenericEmissionTransitionHelper.Transition(studentId,kc,T,I,0) * GenericEmissionTransitionHelper.Emission4beta(studentId, kc, T+1, I)) + (Beta(studentId,kc,T+1,1) * GenericEmissionTransitionHelper.Transition(studentId,kc,T,I,1) * GenericEmissionTransitionHelper.Emission4beta(studentId,kc,T+1,1))) ;
	}
	return betaCalc;
	
}
}
