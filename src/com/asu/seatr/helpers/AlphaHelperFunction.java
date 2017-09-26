package com.asu.seatr.helpers;

import com.asu.seatr.utils.*;
/**
 * @author Venkata Krishna Bandla created on July 25, 2017
 *
 */
public class AlphaHelperFunction {

	public static Double Alpha(int studentId, int kc, int T, int I, String passedfrom){
		//System.out.println();
		//System.out.println("passedfrom :"+passedfrom);
		//System.out.println("Alpha.........T = "+T);
		Double result = null;
		double fetchCalc = 0;
		result = Utils.getFetchAlpha(studentId, kc, T, I);
		//System.out.println("Alpha.....  result :"+result);
		if(result == null){
			
			fetchCalc = AlphaCalc(studentId, kc, T, I);
			//System.out.println("Alpha.......fetchCalc :"+fetchCalc);
			Utils.updateFetchAlpha(studentId, kc, T, I, fetchCalc);
			return fetchCalc;
		}
		
		return result.doubleValue();
		
	}
	
	public static Double AlphaCalc(int studentId, int kc, int T, int I){
		//System.out.println("AlphaCalc.........");
		double emission = 0;
		double alpha2 = 0;
		//System.out.println();
		//emission = GenericEmissionTransitionHelper.Emission4alpha(studentId, kc, T, I);
		//if(I==0)System.out.println("Emmision b"+I+"(y"+T+") "+emission);
		if(T == 1){
			//System.out.println();
			//System.out.println("newCalib 1 y"+T+" Answered - "+Utils.getAnswer(studentId, T));
			emission = GenericEmissionTransitionHelper.Emission4alpha(studentId, kc, T, I);
			//System.out.println("newCalib 1 Emmision b"+I+"(y"+T+") "+emission);
			if(I == 1){
				//System.out.println("pai"+I+":"+Utils.getInitialMasteryMap(kc));
				//System.out.println("Final-1 newCalib 1  Answered - "+Utils.getAnswer(studentId, T)+" b"+I+"(y"+T+") "+Operations.multiplyDouble(emission, Utils.getInitialMasteryMap(kc)));
				//System.out.println();
				//Utils.updateFetchAlpha2(studentId, kc, T, I, Operations.multiplyDouble(emission, Utils.getInitialMasteryMap(kc)));
				return Operations.multiplyDouble(emission, Utils.getInitialMasteryMap(kc));
			}
			else{
				//System.out.println("pai"+I+":"+Operations.substractDouble( (double) 1, Utils.getInitialMasteryMap(kc)));
			//	System.out.println("aplha("+T+") I"+I+" :"+Operations.multiplyDouble(emission,Operations.substractDouble( (double) 1, Utils.getInitialMasteryMap(kc))));
				//System.out.println();
				//Utils.updateFetchAlpha2(studentId, kc, T, I, Operations.multiplyDouble(emission,Operations.substractDouble( (double) 1, Utils.getInitialMasteryMap(kc))));
				return Operations.multiplyDouble(emission,Operations.substractDouble( (double) 1, Utils.getInitialMasteryMap(kc)));
			}
		}
		
		else{
			//System.out.println("AlphaCalc  T:"+T);
			//System.out.println("newCalib 2 y"+T+" Answered -"+Utils.getAnswer(studentId, T));
			emission = GenericEmissionTransitionHelper.Emission4alpha(studentId, kc, T, I);
			//System.out.println("newCalib 2 Emmision b"+I+"(y"+T+") "+emission);
			//System.out.println("AlphaCalc T:"+T);
			alpha2 = Alpha2(studentId, kc, T, I);
			//System.out.println();
			//System.out.println("newCalib 2 emission * alpha2 = "+emission+" * "+alpha2);
			//if(I==0)System.out.println("aplha("+T+") I"+I+" :"+Operations.multiplyDouble(emission, alpha2));
			return Operations.multiplyDouble(emission, alpha2).doubleValue();
		}
	}
	
	public static Double Alpha2(int studentId, int kc, int T, int I){
		//System.out.println("Alpha2.........S: "+studentId+" K: "+kc+" T "+T+" I "+I);
		Double result = null;
		double fetch2 = 0;
		result = Utils.getFetchAlpha2(studentId, kc, T, I);
		//System.out.println("Alpha2.......result :"+result);
		if(result == null){
			
			fetch2 = AlphaCalc2(studentId, kc, T, I);
			//	fetch(alpha, studentId, kc, T, I) = fetchCalc;
			Utils.updateFetchAlpha2(studentId, kc, T, I, fetch2);
			return fetch2;
			
			
		}
		
		return result.doubleValue();
		
	}
	
	public static Double AlphaCalc2(int studentId, int kc, int T, int I){
		//System.out.println("AlphaCalc2.........");
		double alphaCalc2 = 0;
		
		alphaCalc2 = Operations.addDouble((Alpha(studentId, kc, T-1, 0,"AlphaCalc2") * GenericEmissionTransitionHelper.Transition(studentId, kc, T-1, 0, I)) , (Alpha(studentId, kc,T-1,1,"AlphaCalc2") * GenericEmissionTransitionHelper.Transition(studentId, kc, T-1, 1, I)));
		//System.out.println("newCalib 2 Alplacal2 alpah-0("+(T-1)+") :"+Alpha(studentId, kc, T-1, 0)+"  *  a0"+I+" :"+GenericEmissionTransitionHelper.Transition(studentId, kc, T-1, 0, I)+" =   "+(Alpha(studentId, kc, T-1, 0) * GenericEmissionTransitionHelper.Transition(studentId, kc, T-1, 0, I)));
		//System.out.println("        +          ");
		//System.out.println("newCalib 2 Alplacal2 alpah-1("+(T-1)+") :"+Alpha(studentId, kc, T-1, 1)+"  *  a1"+I+" :"+GenericEmissionTransitionHelper.Transition(studentId, kc, T-1, 1, I)+" =  "+(Alpha(studentId, kc,T-1,1) * GenericEmissionTransitionHelper.Transition(studentId, kc, T-1, 1, I)));
		//System.out.println(" ANS = "+alphaCalc2);
		return alphaCalc2;
	}	
}
