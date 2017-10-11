/**
 * 
 */
package com.asu.seatr.utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Lakshmisagar Kusnoor created on May 15, 2017
 *
 */
/**
 * @author lkusnoor
 *
 */
public class Utils {

	private static int[] mKC = new int[GlobalConstants.total_KCs];
	private static int[] studentsList = new int[GlobalConstants.total_Students];
	private static int[] questionsList = new int[GlobalConstants.total_Questions];
	private static ArrayList<Integer> list = new ArrayList<Integer>();

	// Datastructure to implement question id question
	static HashMap<Integer, Integer> id_question_map = new HashMap<Integer, Integer>();

	// Datastructure to implement Last[Student]
	static HashMap<Integer, Integer> last_map = new HashMap<Integer, Integer>();

	// Datastructure to implement Kc InitialMater and Learn
	static HashMap<Integer, HashMap<Integer, Double>> kc_initialMastery_Learn_map = new HashMap<Integer, HashMap<Integer, Double>>();

	// Datastructure to implement Question Qmatrix Slip and Guess
	static HashMap<Integer, HashMap<Integer, String>> Q_QM_Slip_Guess_map = new HashMap<Integer, HashMap<Integer, String>>();

	// Datastructure to implement Question(S,A,Q)
	static HashMap<Integer, HashMap<Integer, Integer>> question_SA_Map = new HashMap<Integer, HashMap<Integer, Integer>>();

	// Datastructure to implement QuestionMatrix(Q)
	static HashMap<Integer, ArrayList<Integer>> qMatrix_map = new HashMap<Integer, ArrayList<Integer>>();

	// Datastructure to implement Answer(S,A,Q)
	static HashMap<Integer, HashMap<Integer, Integer>> answer_SA_Map = new HashMap<Integer, HashMap<Integer, Integer>>();

	// TODO change the logic from 3 maps to 1 map here in utils like Questionand
	// Answer
	// Datastructure to implement BEST
	static HashMap<Integer, Double> best_innerBestMap = new HashMap<Integer, Double>();
	static HashMap<Integer, HashMap<Integer, Double>> best_innerKcBestMap = new HashMap<Integer, HashMap<Integer, Double>>();
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> best_outerStudentKcMap = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>();

	// Datastructure to implement FORWARD
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> forward_outerStudentKcMap = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>();

	// Datastructure to implement BACKWARD
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> backward_outerStudentKcMap = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>();
	
	
	//Data structure to implement fetch_alpha
	
	static HashMap<Integer, Double> fetch_alpha_inner_I_stateMap = new HashMap<Integer, Double>();
	static HashMap<Integer, HashMap<Integer, Double>> fetch_alpha_innerAttempt_T_Map = new HashMap<Integer, HashMap<Integer, Double>>();
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> fetch_alpha_innerKc_K_Map = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>();
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>> fetch_alpha_outerStudentId_S_Map = new HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>>();
	
	//Data structure to implement fetch_alpha2
	
	static HashMap<Integer, Double> fetch_alpha2_inner_I_stateMap = new HashMap<Integer, Double>();
	static HashMap<Integer, HashMap<Integer, Double>> fetch_alpha2_innerAttempt_T_Map = new HashMap<Integer, HashMap<Integer, Double>>();
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> fetch_alpha2_innerKc_K_Map = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>();
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>> fetch_alpha2_outerStudentId_S_Map = new HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>>();

	
	//Data structure to implement fetch_beta
	
	static HashMap<Integer, Double> fetch_beta_inner_I_stateMap = new HashMap<Integer, Double>();
	static HashMap<Integer, HashMap<Integer, Double>> fetch_beta_innerAttempt_T_Map = new HashMap<Integer, HashMap<Integer, Double>>();
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> fetch_beta_innerKc_K_Map = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>();
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>> fetch_beta_outerStudentId_S_Map = new HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>>();
	
	//Data structure to implement fetch_best
	
	static HashMap<Integer, Double> fetch_best_innerAttempt_T_Map = new HashMap<Integer, Double>();
	static HashMap<Integer, HashMap<Integer, Double>> fetch_best_innerKc_K_Map = new HashMap<Integer, HashMap<Integer, Double>>();
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> fetch_best_outerStudentId_S_Map = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>();
		
	/*
	 * Students List
	 */

	public static int[] getStudentsList() {
		return studentsList;
	}

	public static void setStudentsList(int[] studentsList) {
		Utils.studentsList = studentsList;
	}
	/*
	 * Student
	 */

	public static int getStudent(int index) {
		return studentsList[index];
	}

	public static void setStudent(int index, int studentid) {
		Utils.studentsList[index] = studentid;
	}

	/*
	 * QMatrix
	 */
	public static void setQuestionMatrix(int mQuestion, int kc) {
		ArrayList<Integer> list = qMatrix_map.get(mQuestion);
		// System.out.println();
		// System.out.println("setQuestionMatrix :"+mQuestion+" : "+kc+" : " +list);
		if (list == null) {
			list = new ArrayList<Integer>();
			list.add(kc);
		} else {
			list.add(kc);
		}
		qMatrix_map.put(mQuestion, list);
		//System.out.println("setQuestionMatrix :"+mQuestion+" : "+kc+" : " +list);
		//System.out.println("count :"+qMatrix_map.size());
	}

	public static ArrayList<Integer> getQuestionMatrix(int mQuestion) {
		//System.out.println(" here in getQuestionMatrix ");
		ArrayList<Integer> list = qMatrix_map.get(mQuestion);
		// TODO remove below condition once you match with correct kc from table
		if (list == null) {
			list = new ArrayList<Integer>();
			list.add(getKc(0));
		}
		return list;
	}

	/*
	 * Answer
	 */
	public static void setAnswer(int s, HashMap<Integer, Integer> answer_AC_Map) {
		answer_SA_Map.put(s, answer_AC_Map);
	}

	public static int getAnswer(int S, int A) {
		HashMap<Integer, Integer> innerAC_map = answer_SA_Map.get(S);
		return innerAC_map.get(A);
	}

	/*
	 * Question
	 */
	public static void setQuestion(int s, HashMap<Integer, Integer> question_AQ_Map) {
		//System.out.println("Set SQA :"+s+" "+question_AQ_Map);
		question_SA_Map.put(s, question_AQ_Map);
	}

	public static int getQuestion(int S, int A) {
		HashMap<Integer, Integer> innerAQ_map = question_SA_Map.get(S);
		//System.out.println("get SAQ :"+S+" "+A+" "+innerAQ_map.get(A));
		return innerAQ_map.get(A);
	}

	/*
	 * KCs List
	 */
	public static int[] getKcList() {
		return mKC;
	}

	public static void setKcList(int[] questionList) {
		mKC = questionList;
	}

	public static int getKc(int index) {
		return mKC[index];
	}

	public static void setKc(int index, int questionid) {
		mKC[index] = questionid;
	}

	// *************MAP - Kc IM L *********************************
	public static void setKcMap(int Kc) {
		HashMap<Integer, Double> map = new HashMap<Integer, Double>();
		kc_initialMastery_Learn_map.put(Kc, map);
	}

	public static void setInitialMasteryMap(int Kc, Double value) {
		kc_initialMastery_Learn_map.get(Kc).put(GlobalConstants.IM, value);
	}

	public static Double getInitialMasteryMap(int Kc) {
		return kc_initialMastery_Learn_map.get(Kc).get(GlobalConstants.IM);
	}

	public static void setLearnMap(int Kc, Double value) {
		System.out.println(" setLearnMap  : "+Kc+"  "+value);
		kc_initialMastery_Learn_map.get(Kc).put(GlobalConstants.Learn, value);
	}

	public static Double getLearnMap(int Kc) {
		return kc_initialMastery_Learn_map.get(Kc).get(GlobalConstants.Learn);
	}

	/*
	 * Question List
	 */
	public static int[] getQuestionsList() {
		return questionsList;
	}

	public static void setQuestionsList(int[] questionList) {
		Utils.questionsList = questionList;
	}

	public static int getQuestion(int index) {
		// System.out.println("getQuestion :"+index+" ");
		return questionsList[index];
	}

	public static void setQuestion(int index, int questionid) {
		Utils.questionsList[index] = questionid;
	}

	// *************MAP **/*******************************

	// **************class question and its id*****************
	public static void setClassIdQuestion(int index, int questionid) {
		id_question_map.put(index, questionid);
	}

	public static int getClassIdQuestion(int index) {
		return id_question_map.get(index);
	}

	// ************* Q S G QM **/*******************************
	public static void setQuestionMap(int question) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		Q_QM_Slip_Guess_map.put(question, map);
	}

	public static void setSlipMap(int question, Double value) {
		//System.out.println("setSlipMap :" + question+" "+ value);
		Q_QM_Slip_Guess_map.get(question).put(GlobalConstants.Slip, value.toString());
	}

	public static Double getSlipMap(int question) {
		//System.out.println("getSlipMap :" + question+" "+ Q_QM_Slip_Guess_map.get(question).get(GlobalConstants.Slip));
		return new Double(Q_QM_Slip_Guess_map.get(question).get(GlobalConstants.Slip));
	}

	public static void setGuessMap(int question, Double value) {
		Q_QM_Slip_Guess_map.get(question).put(GlobalConstants.Guess, value.toString());
	}

	public static Double getGuessMap(int question) {
		return new Double(Q_QM_Slip_Guess_map.get(question).get(GlobalConstants.Guess));
	}

	public static void setQMatrixMap(int question, ArrayList<Integer> list) {
		//System.out.println("setQMatrixMap :"+question+" "+list);
		String listString = "";
		for (Integer kc : list) {
			listString += kc + "\t";
		}
		Q_QM_Slip_Guess_map.get(question).put(GlobalConstants.QMatrix, listString);
	}

	public static ArrayList<Integer> getQMatrixMap(int question) {
		list.clear();
		String s = Q_QM_Slip_Guess_map.get(question).get(GlobalConstants.QMatrix);
		//System.out.println("string s " + s);
		String[] arr = s.split("\t");
		for (int i = 0; i < arr.length; i++) {
			list.add(Integer.parseInt(arr[i]));
		}
		return list;
	}

	/*
	 * Last
	 */
	public static int getLast(int mStudentId) {
		if (!last_map.containsKey(mStudentId)) {
			return 0;
		}
		return last_map.get(mStudentId);
	}

	public static void setLast(int mStudentId, int questionsCount) {
		last_map.put(mStudentId, questionsCount);
	}

	/*
	 * Forward Backward Best
	 */
	public static void initalizeForwardBackwardBestMap(int S,
			HashMap<Integer, HashMap<Integer, Double>> inner_KcA_Forward_Map,
			HashMap<Integer, HashMap<Integer, Double>> inner_KcA_Backward_Map,
			HashMap<Integer, HashMap<Integer, Double>> inner_KcA_Best_Map) {
		forward_outerStudentKcMap.put(S, inner_KcA_Forward_Map);
		backward_outerStudentKcMap.put(S, inner_KcA_Backward_Map);
		best_outerStudentKcMap.put(S, inner_KcA_Best_Map);
	}
	
	
	public static void initalizeAlphaAlpha2BetaBestMap(int S,
			HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> inner_KcTI_Alpha_Map,
			HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> inner_KcTI_Alpha2_Map,
			HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> inner_KcTI_Beta_Map,
			HashMap<Integer, HashMap<Integer, Double>> inner_KcT_Best_Map) {
		fetch_alpha_outerStudentId_S_Map.put(S, inner_KcTI_Alpha_Map);
		fetch_alpha2_outerStudentId_S_Map.put(S, inner_KcTI_Alpha2_Map);
		fetch_beta_outerStudentId_S_Map.put(S, inner_KcTI_Beta_Map);
		fetch_best_outerStudentId_S_Map.put(S, inner_KcT_Best_Map);
	}

	/*
	 * Forward
	 */
	public static void updateForward(int S, int K, int A, Double forwardfillingValue) {
		// System.out.println("set Forward - S:"+S+" K:"+K+" A:"+A+"
		// ="+forwardfillingValue);
		forward_outerStudentKcMap.get(S).get(K).put(A, forwardfillingValue);

	}

	public static Double getForward(int S, int K, int A) {
		 //System.out.println("GET Forward - S:"+S+" K:"+K+" A:"+A+" ="+forward_outerStudentKcMap.get(S).get(K).get(A));
		return forward_outerStudentKcMap.get(S).get(K).get(A);
	}

	/*
	 * Backward
	 */
	public static void updateBackward(int S, int K, int A, Double backwardfillingValue) {
		//if(S==0)System.out.println("updateBackward S:"+S+" K:"+K+" A:"+A+" : "+backwardfillingValue);
		backward_outerStudentKcMap.get(S).get(K).put(A, backwardfillingValue);
	}

	public static Double getBackward(int S, int K, int A) {
		// System.out.println("getBackward S:"+S+" K:"+K+" A:"+A+" - "+ backward_outerStudentKcMap.get(S).get(K).get(A));
		return backward_outerStudentKcMap.get(S).get(K).get(A);
	}

	/*
	 * Best
	 */
	public static void updateBest(int S, int K, int A, Double bestValue) {
		//System.out.println(" S:"+S+" K:"+K+" A"+A+" bestValue"+bestValue);
		best_outerStudentKcMap.get(S).get(K).put(A, bestValue);
	}

	public static Double getBest(int S, int K, int A) {
		return best_outerStudentKcMap.get(S).get(K).get(A);
	}

	/*
	 * fetch_alpha
	 */
	public static void updateFetchAlpha(int S, int K, int T, int I, Double alpha) {
		//System.out.println(" S:"+S+" K:"+K+" T"+T+" alpha: "+alpha);
		/*if(T == 0){
			System.out.println(" T == 0" );
			System.out.println("alpha "+alpha+" fetch_alpha_inner_I_stateMap"+fetch_alpha_inner_I_stateMap);
			fetch_alpha_outerStudentId_S_Map.get(S).get(K).put(0, fetch_alpha_inner_I_stateMap).put(I, alpha);
		}
		else {
			fetch_alpha_outerStudentId_S_Map.get(S).get(K).get(T).put(I, alpha);
		}*/
		
		
		/*System.out.println("newCalib i :"+I );
		if(I==0){
			System.out.println("newCalib pai"+I+": "+Utils.getInitialMasteryMap(K));
		}else{
			System.out.println("newCalib pai"+I+": "+Operations.substractDouble((double)1,Utils.getInitialMasteryMap(K)));
		}*/
		
		fetch_alpha_outerStudentId_S_Map.get(S).get(K).get(T).put(I, alpha);
		//System.out.println("updateAlpha S:"+S+" K:"+K+" T"+T+" I: "+I+" = "+fetch_alpha_outerStudentId_S_Map.get(S).get(K).get(T).get(I));
	}

	public static Double getFetchAlpha(int S, int K, int T, int I) {
		return fetch_alpha_outerStudentId_S_Map.get(S).get(K).get(T).get(I);
	}
	
	/*
	 * fetch_alpha2
	 */
	public static void updateFetchAlpha2(int S, int K, int T, int I, Double alpha) {
		//System.out.println("updateFetchAlpha2 S: "+S+" K: "+K+" T "+T+" I "+I+ " alpha2: "+alpha);
		fetch_alpha2_outerStudentId_S_Map.get(S).get(K).get(T).put(I, alpha);
	}

	public static Double getFetchAlpha2(int S, int K, int T, int I) {
		return fetch_alpha2_outerStudentId_S_Map.get(S).get(K).get(T).get(I);
	}
	
	
	/*
	 * fetch_beta
	 */
	public static void updateFetchBeta(int S, int K, int T, int I, Double beta) {
		//System.out.println("updateFetchBeta S:"+S+" K:"+K+" T"+T+"I"+I+" beta: "+beta);
		fetch_beta_outerStudentId_S_Map.get(S).get(K).get(T).put(I, beta);
	}

	public static Double getFetchBeta(int S, int K, int T, int I) {
		return fetch_beta_outerStudentId_S_Map.get(S).get(K).get(T).get(I);
	}
	
	/*
	 * fetch_best
	 */
	
	public static void updateFetchBest(int S, int K, int T, Double best) {
		//System.out.println("updateFetchBest  S:"+S+" K:"+K+" T"+T+" best: "+best);
		fetch_best_outerStudentId_S_Map.get(S).get(K).put(T, best);
	}
	
	public static Double getFetchBest(int S, int K, int T) {
		//System.out.println("getFetchBest  S:"+S+" K:"+K+" T"+T+" best: "+fetch_best_outerStudentId_S_Map.get(S).get(K).get(T));
		return fetch_best_outerStudentId_S_Map.get(S).get(K).get(T);
	}
	
	
	//****************************************** SIMULATION**************************************************
	// Datastructure to implement Competence
	static HashMap<Integer, HashMap<Integer, Double>> competence_Map = new HashMap<Integer, HashMap<Integer, Double>>();
	// Competence
	public static void initalizeCompetence(int S, HashMap<Integer, Double> inner_KcV_Map) {
		competence_Map.put(S, inner_KcV_Map);
	}

	public static void setCompetence(int s, int kc, Double initialMastery) {
		competence_Map.get(s).put(kc, initialMastery);
	}

	public static Double getCompetence(int s, int kC) {
		return competence_Map.get(s).get(kC);
	}
	
	// Datastructure to implement SetAnswer
		static HashMap<Integer, HashMap<Integer, Integer>> setAnswer_Map = new HashMap<Integer, HashMap<Integer, Integer>>();
		// Competence
		/*public static void simulateInitalizeSetAnswer(int S, HashMap<Integer, Integer> inner_SetAnswer_Map) {
			setAnswer_Map.put(S, inner_SetAnswer_Map);
		}

		public static void simulateSetAnswer(int s, int q, int i) {
	//		System.out.println(" simulateSetAnswer s " + s+ " q "+ q +" i " +i);
			setAnswer_Map.get(s).put(q, i);
		}

		public static Integer simulategetSetAnswer(int s, int q) {
//			System.out.println(" simulategetSetAnswer " +setAnswer_Map.get(s).get(q));
			return setAnswer_Map.get(s).get(q);
		}*/
		
		public static void clearMapsofAlphaBetaBest(){
			
			fetch_alpha_outerStudentId_S_Map.clear();
			fetch_alpha2_outerStudentId_S_Map.clear();
			fetch_beta_outerStudentId_S_Map.clear();
			fetch_best_outerStudentId_S_Map.clear();
						
		}

}
