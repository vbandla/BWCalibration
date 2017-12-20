package com.asu.seatr.database;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Utils;

public class SimulateDataBase {
	public static void setAllStudentsData() {
		//System.out.println("SimulateDataBase.........");
		GlobalConstants.total_KCs = 25;
		GlobalConstants.total_Questions = 25;
		GlobalConstants.total_Students = 100;
		Random r = new Random();

		// KC
		for (int i = 0; i < GlobalConstants.total_KCs; i++) {
			int kcValue = i;
			Utils.setKc(i, kcValue);
			Utils.setKcMap(kcValue);
		}

		// S
		int[] ids = new int[GlobalConstants.total_Students];
		for (int i = 0; i < GlobalConstants.total_Students; i++) {
			ids[i] = i;
		}
		Utils.setStudentsList(ids);

		// Q
		for (int q = 0; q < GlobalConstants.total_Questions; q++) {
			Utils.setQuestion(q, q);
			Utils.setQuestionMap(q);
			Utils.setClassIdQuestion(q, q);
			// Q KC
			int n_KCs = r.nextInt((GlobalConstants.total_KCs - 1) + 1) + 1;
			for (int j = 0; j < n_KCs; j++) {
				int kc = r.nextInt(((GlobalConstants.total_KCs - 1) - 0) + 1) + 0;
				Utils.setQuestionMatrix(q, kc);
			}
		}

		// LAST
		// set of Q answered by student
				
		for (int st = 0; st < GlobalConstants.total_Students; st++) {
			int id = st;
			HashMap<Integer, Integer> question_AQ_Map = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> answer_AC_Map = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> inner_setAnswer = new HashMap<Integer, Integer>();
			int numberOfQuestionsAttempted =/* r.nextInt((*/GlobalConstants.total_Questions /*- 1) + 1) + 1*/;
			Set<Integer> generated = new LinkedHashSet<Integer>();
			while(generated.size()<numberOfQuestionsAttempted){
				int Q = r.nextInt(((GlobalConstants.total_Questions - 1) - 0) + 1) + 0;
				generated.add(Q);
			}
			for(Integer Q : generated){
				inner_setAnswer.put(Q, 0);
				int correct = (Math.random() < 0.5) ? 0 :1;
				int count = Utils.getLast(id);
				count++;
				Utils.setLast(id, count);
				int A = count;
				/*if(A==1)correct=1;
				if(A==2)correct=0;
				if(A==3)correct=0;
				if(A==4)correct=1;*/
				answer_AC_Map.put(A, correct);
				Utils.setAnswer(st, answer_AC_Map);
				question_AQ_Map.put(A, Q);
				//System.out.println("setQuestion "+st+" "+A+" "+Q);
				Utils.setQuestion(st, question_AQ_Map);
				//System.out.println("getQuestion ("+st+","+A+") "+Utils.getQuestion(st, A));
				//System.out.println(st+" "+A+" "+Q+" = "+correct);
				//System.out.println("1 SAGAR getAnswer_S_A_Q("+st+","+A+","+Utils.getQuestion(st, A)+")"+Utils.getAnswer(st, A));
			}
			//Utils.simulateInitalizeSetAnswer(id,inner_setAnswer);
			
		}
		
		/*System.out.println("----------------------------------------------------");
		for (int Qi = 0; Qi < GlobalConstants.total_Questions ; Qi++) {
			int Q = Utils.getQuestion(Qi);
			for (int st2 = 0; st2 < GlobalConstants.total_Students; st2++) {
				int S = Utils.getStudent(st2);
				for (int T = 1; T <= Utils.getLast(S); T++) {
					//if (Q == Utils.getQuestion(S, T)) {
						//System.out.println("getQuestion ("+S+","+T+") "+Utils.getQuestion(S, T));
						System.out.println("2 SAGAR getAnswer_S_A_Q("+S+","+T+","+Utils.getQuestion(S, T)+")"+Utils.getAnswer(S, T));
					//}
				}
			}
			System.out.println("----------------------------------------------------");
		//}
*/		
		
		
/*		// ************** MAP - S K A ***************************
		for (int St = 0; St < GlobalConstants.total_Students; St++) {
			int S = Utils.getStudent(St);
			HashMap<Integer, HashMap<Integer, Double>> inner_KcA_Forward_Map = new HashMap<Integer, HashMap<Integer, Double>>();
			HashMap<Integer, HashMap<Integer, Double>> inner_KcA_Backward_Map = new HashMap<Integer, HashMap<Integer, Double>>();
			HashMap<Integer, HashMap<Integer, Double>> inner_KcA_Best_Map = new HashMap<Integer, HashMap<Integer, Double>>();
			for (int K = 0; K < GlobalConstants.total_KCs; K++) {
				int Kc = Utils.getKc(K);
				HashMap<Integer, Double> inner_AV_Forward_Map = new HashMap<Integer, Double>();
				HashMap<Integer, Double> inner_AV_Backward_Map = new HashMap<Integer, Double>();
				HashMap<Integer, Double> inner_AV_Best_Map = new HashMap<Integer, Double>();
				inner_KcA_Forward_Map.put(Kc, inner_AV_Forward_Map);
				inner_KcA_Backward_Map.put(Kc, inner_AV_Backward_Map);
				inner_KcA_Best_Map.put(Kc, inner_AV_Best_Map);
			}
			Utils.initalizeForwardBackwardBestMap(S, inner_KcA_Forward_Map, inner_KcA_Backward_Map, inner_KcA_Best_Map);
		}*/

		
		initalizeAlphaAlpha2BetaBestMapper();
		
		
		
		// ************** MAP - S K COMPETENCE ***************************
		for (int St = 0; St < GlobalConstants.total_Students; St++) {
			int S = Utils.getStudent(St);
			HashMap<Integer, Double> inner_KcV_Map = new HashMap<Integer, Double>();
			for (int K = 0; K < GlobalConstants.total_KCs; K++) {
				int Kc = Utils.getKc(K);
				inner_KcV_Map.put(Kc, (double) 0);
			}
			Utils.initalizeCompetence(S, inner_KcV_Map);
		}

		// ************** MAP - S Q COMPETENCE ***************************
	}

	// SIMULATION
	/*public static void setInitialCompetence() {
		for (int St = 0; St < GlobalConstants.total_Students; St++) {
			int S = Utils.getStudent(St);
			for (int K = 0; K < GlobalConstants.total_KCs; K++) {
				int Kc = Utils.getKc(K);
				Utils.setCompetence(S, Kc, Utils.getInitialMasteryMap(Kc));
			}
		}
		OKCompetence();
	}*/

	/*public static void OKCompetence() {
		for (int St = 0; St < GlobalConstants.total_Students; St++) {
			int S = Utils.getStudent(St);
			for(int q=0;q<GlobalConstants.total_Questions;q++){
				int Q = Utils.getQuestion(q);
				Double OK = (double)1;
				ArrayList<Integer> KCs = Utils.getQuestionMatrix(Q);
				for(int kc =0;kc<KCs.size();kc++){
					int KC = Utils.getKc(kc);
					OK = OK*Utils.getCompetence(S,KC);
				}
				Double A = OK*(1-Utils.getSlipMap(Q))+(1-OK)*Utils.getGuessMap(Q);
				int randomC = (Math.random() < 0.5) ? 0 : 1;
				int ans = 0;
				if(A>=randomC){
					ans=1;
				}
				//System.out.println("simulateSetAnswer : "+A+" >= "+randomC+" = "+ans);
				Utils.simulateSetAnswer(S, Q, ans);
				System.out.println("2  SAGAR simulateGetAnswer_S_Q ("+S+","+Q+") :"+Utils.simulategetSetAnswer(S, Q));
				for(int kc =0;kc<KCs.size();kc++){
					int KC = Utils.getKc(kc);
					Double value = Utils.getCompetence(S,KC)+Utils.getLearnMap(KC)*(1-Utils.getCompetence(S,KC));
					Utils.setCompetence(S, KC, value);
				}
			}
			
		}
		
		System.out.println("----------------------------------------------------------------------------------------------");
	}*/
	
	// ************** MAP - S K T I ***************************
			public static void initalizeAlphaAlpha2BetaBestMapper(){
				
				for (int St = 0; St < GlobalConstants.total_Students; St++) {
					int S = Utils.getStudent(St);
					HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> inner_KcTI_Alpha_Map = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>();
					HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> inner_KcTI_Alpha2_Map = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>();
					HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> inner_KcTI_Beta_Map = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>();
					HashMap<Integer, HashMap<Integer, Double>> inner_KcT_Best_Map = new HashMap<Integer, HashMap<Integer, Double>>();
					for (int K = 0; K < GlobalConstants.total_KCs; K++) {
						int Kc = Utils.getKc(K);
						HashMap<Integer, HashMap<Integer, Double>> inner_TI_Alpha_Map = new HashMap<Integer, HashMap<Integer, Double>>();
						HashMap<Integer, HashMap<Integer, Double>> inner_TI_Alpha2_Map = new HashMap<Integer, HashMap<Integer, Double>>();
						HashMap<Integer, HashMap<Integer, Double>> inner_TI_Beta_Map = new HashMap<Integer, HashMap<Integer, Double>>();
						HashMap<Integer, Double> inner_TI_Best_Map = new HashMap<Integer, Double>();
						
						for (int T = 0; T <= Utils.getLast(S); T++) {
							HashMap<Integer, Double> inner_I_Alpha_Map = new HashMap<Integer, Double>();
							HashMap<Integer, Double> inner_I_Alpha2_Map = new HashMap<Integer, Double>();
							HashMap<Integer, Double> inner_I_Beta_Map = new HashMap<Integer, Double>();
							
							inner_TI_Alpha_Map.put(T, inner_I_Alpha_Map);
							inner_TI_Alpha2_Map.put(T, inner_I_Alpha2_Map);
							inner_TI_Beta_Map.put(T, inner_I_Beta_Map);
							
						}
						inner_KcTI_Alpha_Map.put(Kc, inner_TI_Alpha_Map);
						inner_KcTI_Alpha2_Map.put(Kc, inner_TI_Alpha2_Map);
						inner_KcTI_Beta_Map.put(Kc, inner_TI_Beta_Map);
						inner_KcT_Best_Map.put(Kc, inner_TI_Best_Map);
					}
					
					Utils.initalizeAlphaAlpha2BetaBestMap(S, inner_KcTI_Alpha_Map, inner_KcTI_Alpha2_Map, inner_KcTI_Beta_Map, inner_KcT_Best_Map);
				}
				
			}
	
}
