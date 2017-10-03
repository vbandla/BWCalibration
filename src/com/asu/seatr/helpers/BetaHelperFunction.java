package com.asu.seatr.helpers;

import com.asu.seatr.utils.Utils;

/**
 * @author Venkata Krishna Bandla created on July 25, 2017
 *
 */
public class BetaHelperFunction {

public static Double Beta(int studentId, int kc, int T, int I, String from){
	//System.out.println();
	//System.out.print("Beta........."+from+"  ");
		Double result = null;
		double fetchBeta = 0;
		result = Utils.getFetchBeta(studentId, kc, T, I);
		//System.out.print("Beta  result1 :"+result+"  ");
		if(result == null){
			//System.out.print("Beta  result2 :"+result+"  ");
			fetchBeta = BetaCalc(studentId, kc, T, I);
			//System.out.print("Beta  fetchBeta :"+fetchBeta+"  ");
			Utils.updateFetchBeta(studentId, kc, T, I, fetchBeta);
			return fetchBeta;
		}
		//System.out.print("--------- return");
		return result.doubleValue();
		
	}

public static Double BetaCalc(int studentId, int kc, int T, int I){
	//System.out.println("BetaCalc.........");
	double betaCalc = 0;
	if(T == Utils.getLast(studentId)){
		return (double)1;
	}
	
	else{
		
		betaCalc = ((Beta(studentId,kc,T+1,0,"BetaCalc1") * GenericEmissionTransitionHelper.Transition(studentId,kc,T,I,0) * GenericEmissionTransitionHelper.Emission4beta(studentId, kc, T+1, 0)) + (Beta(studentId,kc,T+1,1,"BetaCalc2") * GenericEmissionTransitionHelper.Transition(studentId,kc,T,I,1) * GenericEmissionTransitionHelper.Emission4beta(studentId,kc,T+1,1))) ;
		//System.out.println("b0("+(T+1)+") :"+Beta(studentId,kc,T+1,0,"BetaCalc1")+"  *  a"+I+"0 :"+GenericEmissionTransitionHelper.Transition(studentId, kc, T,I,0)+" * b0(y"+T+" "+1+") :"+GenericEmissionTransitionHelper.Emission4beta(studentId, kc, T+1, 0)+"  =   "+(Beta(studentId,kc,T+1,0,"BetaCalc1") * GenericEmissionTransitionHelper.Transition(studentId,kc,T,I,0) * GenericEmissionTransitionHelper.Emission4beta(studentId, kc, T+1, 0)));
		//System.out.println("        +          ");
		//System.out.println("b1("+(T+1)+") :"+Beta(studentId,kc,T+1,1,"BetaCalc1")+"  *  a"+I+"1 :"+GenericEmissionTransitionHelper.Transition(studentId, kc, T,I,1)+" * b1(y"+T+" "+1+") :"+GenericEmissionTransitionHelper.Emission4beta(studentId, kc, T+1, 1)+"  =   "+(Beta(studentId,kc,T+1,1,"BetaCalc2") * GenericEmissionTransitionHelper.Transition(studentId,kc,T,I,1) * GenericEmissionTransitionHelper.Emission4beta(studentId,kc,T+1,1)));
		//System.out.println(" ANS = "+betaCalc);
	}
	return betaCalc;
	
}
}
