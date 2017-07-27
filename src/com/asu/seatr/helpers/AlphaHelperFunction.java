package com.asu.seatr.helpers;

import com.asu.seatr.utils.*;
/**
 * @author Venkata Krishna Bandla created on July 25, 2017
 *
 */
public class AlphaHelperFunction {

	public static Double Alpha(int studentId, int kc, int T, int I){
		
		Double result = null;
		double fetchCalc = 0;
		result = Utils.getFetchAlpha(studentId, kc, T, I);

		if(result == null){
			
			fetchCalc = AlphaCalc(studentId, kc, T, I);
	
			Utils.updateFetchAlpha(studentId, kc, T, I, fetchCalc);
			return fetchCalc;
		}
		
		return result.doubleValue();
		
	}
	
	public static Double AlphaCalc(int studentId, int kc, int T, int I){
		
		double emission = 0;
		double alpha2 = 0;
		if(T == 1){
			
			if(I == 1){
				return Utils.getInitialMasteryMap(kc);
			}
			else{
				return Operations.substractDouble( (double) 1, Utils.getInitialMasteryMap(kc)).doubleValue();
			}
		}
		
		else{
			
			emission = Emission(studentId, kc, T, I);
			alpha2 = Alpha2(studentId, kc, T, I);
			
			return Operations.multiplyDouble(emission, alpha2).doubleValue();
			
		}
	}
	
	public static Double Alpha2(int studentId, int kc, int T, int I){
		
		Double result = null;
		double fetch2 = 0;
		result = Utils.getFetchAlpha2(studentId, kc, T, I);
		
		if(result == null){
			
			fetch2 = AlphaCalc2(studentId, kc, T, I);
			//	fetch(alpha, studentId, kc, T, I) = fetchCalc;
			Utils.updateFetchAlpha2(studentId, kc, T, I, fetch2);
			return fetch2;
			
			
		}
		
		return result.doubleValue();
		
	}
	
	public static Double AlphaCalc2(int studentId, int kc, int T, int I){
		
		double alphaCalc2 = 0;
		alphaCalc2 = (Alpha(studentId, kc, T-1, 0) * GenericEmissionTransitionHelper.Transition(studentId, kc, T-1, I, 0)) + (Alpha(studentId, kc,T-1,1) * GenericEmissionTransitionHelper.Transition(studentId, kc, T-1, I, 1));
		
		return alphaCalc2;
	}
	
}
