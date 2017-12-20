package com.asu.seatr.calibration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.asu.seatr.database.DatabaseResponse;
import com.asu.seatr.database.SimulateDataBase;
import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Operations;
import com.asu.seatr.utils.Utils;

/**
 * @author Lakshmisagar Kusnoor created on May 11, 2017
 *
 */
public class Calibration {

	static int climb = 0;
	static HashMap<Integer, ArrayList<Double>> climbMap = new HashMap<Integer, ArrayList<Double>>();
	static Double old_initalMastery[];
	static Double old_Learn[];
	static Double old_slip[];
	static Double old_guess[];

	static int total_students;
	static int total_KCs;
	static int total_Q;

	static Double average_IM;
	static Double average_L;
	static Double average_S;
	static Double average_G;
	
	static Double Random_IM;
	static Double Random_L;
	static Double Random_S;
	static Double Random_G;
	
	static boolean isFirst = true;
	static Double Sim_Random_IM;
	static Double Sim_Random_L;
	static Double Sim_Random_S;
	static Double Sim_Random_G;
	
	private static String AUXExcelFilePath = "C:/Users/lkusnoor/Downloads/LOGS/MAINAUXExcelFile.xls";
	private static  int rowIndex = 0;
	private static FileOutputStream fileOut;
	private static HSSFWorkbook workbook;
	private static HSSFSheet worksheet ;

	static double BestAUC = 0;
	static int bestClimb = 1;
	static int climbCounter = 0;
	static Double fillrandom_initalMastery[];
	static Double fillrandom_Learn[];
	static Double fillrandom_slip[];
	static Double fillrandom_guess[];
	
	private static Double climbOnce() throws FileNotFoundException {
		// System.out.println("CLIMBONCE****************************** ");
	
		counter++;
		//OUT======================================================================================================START
		/*PrintStream o;
		o = new PrintStream(new File("C:/Users/lkusnoor/Downloads/LOGS/"+counter+"CALIB3.txt"));
		System.setOut(o);*/
		//OUT======================================================================================================END
		saveParameters();
		
		Utils.clearMapsofAlphaBetaBest();
		SimulateDataBase.initalizeAlphaAlpha2BetaBestMapper();
		
		
		FillAlpha.fillAlpha();
		FillBeta.fillBeta();
		FillBest.fillBest();
//		EstimateKcMastery.Estimate_KC_mastery_Best();
		calculateNewParameters();// update initalMaster,Learn,slip,guess
		Double change = changeInParameter();
		System.out.println("CHANGE******************************   " + change);
		return change;
	}

	static int counter=0;
	private static void calculateNewParameters() throws FileNotFoundException {
		EstimateSlipAndGuess.estimateSlipAndGuess();
		
		
		
		EstimateTransition.estimateTransition();
		
		//o = new PrintStream(new File("C:/Users/lkusnoor/Downloads/CALIB3.txt"));
		//System.setOut(o);
		
		EstimateInitialMastery.estimateIntialMastery();
	}

	private static Double changeInParameter() {
		// System.out.println("changeInParameter()");
		Double sum_initalMaster = (double) 0;
		Double sum_Learn = (double) 0;
		Double sum_slip = (double) 0;
		Double sum_guess = (double) 0;

		Double maxChange, IMChange, LChange, SChange, GChange;

		for (int K = 0; K < total_KCs; K++) {
			// System.out.println(K);
			// System.out.println("sum_Learn :"+sum_Learn);
			int Kc = Utils.getKc(K);
			Double diff_IM = Operations.substractDouble( Utils.getInitialMasteryMap(Kc),old_initalMastery[K]);
			// System.out.println("diff_IM :"+diff_IM+" =
			// "+old_initalMastery[K]+" - "+Utils.getInitialMasteryMap(Kc));
//			System.out.println("old_initalMastery[K] :"+old_initalMastery[K]+"  Utils.getInitialMasteryMap(Kc):"+Utils.getInitialMasteryMap(Kc));
			Double denomDiff_IM = Operations.addDouble(old_initalMastery[K], Utils.getInitialMasteryMap(Kc));
			Double change_IM = Operations.divideDouble(diff_IM,denomDiff_IM );
			// System.out.println("change_IM :"+change_IM);
			sum_initalMaster = Operations.addDouble(sum_initalMaster, change_IM);
			// System.out.println("sum_initalMaster :"+sum_initalMaster);

			//System.out.println("old_Learn[K] :"+old_Learn[K]+"  Utils.getLearnMap(Kc) :"+Utils.getLearnMap(Kc));
			Double diff_L = Operations.substractDouble(Utils.getLearnMap(Kc), old_Learn[K]);
			//System.out.println("diff_L :"+diff_L+" = "+old_Learn[K]+"  "+Utils.getLearnMap(Kc));
			Double denomDiff_L = Operations.addDouble(old_Learn[K], Utils.getLearnMap(Kc));
			//System.out.println("diff_L :"+diff_L+"denomDiff_L :"+denomDiff_L);
			Double change_L = Operations.divideDouble(diff_L, denomDiff_L);
			//System.out.println("change_L :"+change_L);
			sum_Learn = Operations.addDouble(sum_Learn, change_L);
//			System.out.println();
		    //System.out.println("sum_Learn = sum_Learn+change_L "+sum_Learn);
		}
		//System.out.println("sum_slip :"+sum_slip);
		for (int Q = 0; Q < total_Q; Q++) {
			int question = Utils.getQuestion(Q);
			
			//System.out.println("old_slip[Q] :"+old_slip[Q]+"  Utils.getSlipMap("+question+") :"+Utils.getSlipMap(question));
			Double diff_S = Operations.substractDouble(Utils.getSlipMap(question),old_slip[Q]);
			//System.out.println(" diff_S :"+ Utils.getSlipMap(question)+"-"+old_slip[Q]+"="+diff_S);
			Double denomDiff_S = Operations.addDouble(old_slip[Q], Utils.getSlipMap(question));
			//System.out.println(" denomDiff_S :"+denomDiff_S);
			Double change_S = Operations.divideDouble(diff_S, denomDiff_S);
			//System.out.println(" change_S = diff_S/denomDiff_S :"+change_S);
			sum_slip = Operations.addDouble(sum_slip, change_S);
			//System.out.println("sum_slip :"+sum_slip);
//			System.out.println("old_guess[Q] :"+old_guess[Q]+"  Utils.getGuessMap(question) :"+Utils.getGuessMap(question));
			Double diff_G = Operations.substractDouble(Utils.getGuessMap(question), old_guess[Q]);
			Double denomDiff_G = Operations.addDouble(old_guess[Q], Utils.getGuessMap(question));
			//System.out.println("denomDiff_G "+denomDiff_G+" = "+old_guess[Q]+" + "+Utils.getGuessMap(question));
			Double change_G = Operations.divideDouble(diff_G, denomDiff_G);
			//System.out.println("sum_guess "+sum_guess+" = "+sum_guess+" + "+change_G);
			sum_guess = Operations.addDouble(sum_guess, change_G);
			//System.out.println();
		}
		 //System.out.println("LChange = "+sum_Learn+ "  "+Double.valueOf(total_KCs));
		 //System.out.println("GChange = "+sum_guess+ "  "+Double.valueOf(total_Q));
		//System.out.println("SChange = "+sum_slip+ "  "+Double.valueOf(total_Q));
		IMChange = Operations.divideDouble(sum_initalMaster, Double.valueOf(total_KCs));
		LChange = Operations.divideDouble(sum_Learn, Double.valueOf(total_KCs));
		SChange = Operations.divideDouble(sum_slip, Double.valueOf(total_Q));
		GChange = Operations.divideDouble(sum_guess, Double.valueOf(total_Q));
		
		// Consider both +ve change and -ve change as same
		IMChange = Math.abs(IMChange);
		LChange = Math.abs(LChange);
		SChange = Math.abs(SChange);
		GChange = Math.abs(GChange);
		/*if(SChange<(double)0.1)*/	
		System.out.println("IMChange   " + IMChange + "   LChange:  " + LChange + "  SChange: " + SChange+ "    GChange:  " + GChange);
		maxChange = Math.max(IMChange, LChange);
		maxChange = Math.max(maxChange, SChange);
		maxChange = Math.max(maxChange, GChange);
		return maxChange;
	}

	private static void saveParameters() {
		for (int KcIndex = 0; KcIndex < total_KCs; KcIndex++) {
			int kc = Utils.getKc(KcIndex);
			old_initalMastery[KcIndex] = Utils.getInitialMasteryMap(kc);
			old_Learn[KcIndex] = Utils.getLearnMap(kc);
		}
		for (int Q = 0; Q < total_Q; Q++) {
			int question = Utils.getQuestion(Q);
			old_slip[Q] = Utils.getSlipMap(question);
			old_guess[Q] = Utils.getGuessMap(question);
		}
		printRandomParameters();
	}

	private static void fillRandomParameters() {
		//System.out.println("RANDOM");
		Random r = new Random();
		for (int KcIndex = 0; KcIndex < total_KCs; KcIndex++) {
			
			  double r_initalMaster = 0.05 + r.nextDouble() * (0.95 - 0.05);
			  double r_Learn = 0.05 + r.nextDouble() * (0.5 - 0.05);
			 

			// SIMULATION
			// double randomValue = rangeMin + (rangeMax - rangeMin) *
			// r.nextDouble();
/*			double r_initalMaster = 0.1 + r.nextDouble() * (0.7 - 0.1);
			double r_Learn = 0.1 + r.nextDouble() * (0.7 - 0.1);*/
			

			int Kc = Utils.getKc(KcIndex);
			Utils.setInitialMasteryMap(Kc, Double.valueOf(r_initalMaster));
			Utils.setLearnMap(Kc, Double.valueOf(r_Learn));
			fillrandom_initalMastery[KcIndex] = Utils.getInitialMasteryMap(Kc);
			fillrandom_Learn[KcIndex] = Utils.getLearnMap(Kc);
			//System.out.println("Kc :"+Utils.getKc(KcIndex)+" IM: "+Utils.getInitialMasteryMap(Kc)+" L: "+Utils.getLearnMap(Kc));
		}
		// System.out.println("START");
		for (int Q = 0; Q < total_Q; Q++) {
			
			 double r_slip = 0.05 + r.nextDouble() * (0.45 - 0.05); 
			 double r_guess = 0.01 + r.nextDouble() * (0.5 - 0.01);
			 

			// SIMULATION
			/*double r_slip = 0.01 + r.nextDouble() * (0.1 - 0.01);
			double r_guess = 0.01 + r.nextDouble() * (0.1 - 0.01);
*/
			int question = Utils.getQuestion(Q);
			Utils.setSlipMap(question, Double.valueOf(r_slip));
			Utils.setGuessMap(question, Double.valueOf(r_guess));
			
			fillrandom_slip[Q] = Utils.getSlipMap(question);
			fillrandom_guess[Q] = Utils.getGuessMap(question);
			//System.out.println("Q :"+question+" S: "+Utils.getSlipMap(question)+" G: "+Utils.getGuessMap(question));
		}
		// System.out.println("STOP");
		 printRandomParameters();
		
		//SIMULATION
		//SimulateDataBase.setInitialCompetence();
	}

	private static void printRandomParameters() {
		
		Double sum_initalMaster = (double) 0;
		Double sum_Learn = (double) 0;
		Double sum_slip = (double) 0;
		Double sum_guess = (double) 0;
		
		//System.out.println("SAGAR");
		for (int K = 0; K < total_KCs; K++) {
			// System.out.println(K);
			// System.out.println("sum_Learn :"+sum_Learn);
			int Kc = Utils.getKc(K);
			//System.out.println(" SAGAR R_IM :"+K+"  "+ Utils.getInitialMasteryMap(Kc));
			sum_initalMaster = Operations.addDouble(sum_initalMaster, Utils.getInitialMasteryMap(Kc));
			// System.out.println("sum_initalMaster :"+sum_initalMaster);
			//System.out.println(" SAGAR R_L :"+K+"  "+ Utils.getLearnMap(Kc));
			sum_Learn = Operations.addDouble(sum_Learn, Utils.getLearnMap(Kc));
			//System.out.println(sum_Learn+" "+Utils.getLearnMap(Kc));
			 //System.out.println("sum_Learn = sum_Learn+change_L "+sum_Learn);

		}
		for (int Q = 0; Q < total_Q; Q++) {
			int question = Utils.getQuestion(Q);
			sum_slip = Operations.addDouble(sum_slip, Utils.getSlipMap(question));

			sum_guess = Operations.addDouble(sum_guess, Utils.getGuessMap(question));
			// System.out.println("sum_guess "+sum_guess+" = "+sum_guess+" + "+change_G);
			//System.out.println(" SAGAR R_S :"+Q+"  "+ Utils.getSlipMap(question));
			//System.out.println(" SAGAR R_G :"+Q+"  "+ Utils.getGuessMap(question));
		}
		Random_IM = Operations.divideDouble(sum_initalMaster, Double.valueOf(total_KCs));
		Random_L = Operations.divideDouble(sum_Learn, Double.valueOf(total_KCs));
		Random_S = Operations.divideDouble(sum_slip, Double.valueOf(total_Q));
		Random_G = Operations.divideDouble(sum_guess, Double.valueOf(total_Q));
		System.out.println("SAVE_IM   " + Random_IM + "   SAVE_L:  " + Random_L + "  SAVE_S: " + Random_S	+ "    SAVE_G:  " + Random_G);
	/*	//if(isFirst){
			//isFirst = false;
			Sim_Random_IM = Random_IM;
			Sim_Random_L = Random_L;
			Sim_Random_S = Random_S;
			Sim_Random_G = Random_G;
			System.out.println("Sim_SAVE_IM   " + Sim_Random_IM + "   Sim_SAVE_L:  " + Sim_Random_L + "  Sim_SAVE_S: " + Sim_Random_S	+ "    Sim_SAVE_G:  " + Sim_Random_G);
		//}
*/	}
	 

	private static void setDatabase() {
		//System.out.println("setDatabase()");
		DatabaseResponse.setAllStudentsData();
	}

	private static void setSimulatedData() {
		SimulateDataBase.setAllStudentsData();
	}

	/*
	 * private static void setKcs() { for(int i=0;i<total_KCs;i++){
	 * Utils.setKc(i, i); } }
	 * 
	 * private static void setAnswerValues() { for( int
	 * S=0;S<total_students;S++){ HashMap<Integer,Integer> answer_AC_Map= new
	 * HashMap<Integer,Integer>(); for(int A=0;A<Utils.getLast(S);A++){ int
	 * value =(Math.random()<0.5)?0:1; answer_AC_Map.put(A, value); }
	 * Utils.setAnswer(S, answer_AC_Map); } }
	 */
	/*
	 * private static void setQuestionValues() { for( int
	 * St=0;St<total_students;St++){ int S = Utils.getStudent(St);
	 * HashMap<Integer,Integer> question_AQ_Map= new HashMap<Integer,Integer>();
	 * int i = 0; int Q = Utils.getQuestion(i);
	 * System.out.println(Utils.getLast(S)); for(int
	 * A=0;A<Utils.getLast(S);A++){ int value =(Math.random()<0.5)?0:1;
	 * //System.out.println("SQA :"+S+" "+Q+" "+A); question_AQ_Map.put(A, Q);
	 * i++; Q=Utils.getQuestion(i); } Utils.setQuestion(S, question_AQ_Map); } }
	 */

	/**
	 * get final parameters
	 */
	private static void updateNewPrameters() {
		Double sum_IM = (double) 0;
		Double sum_L = (double) 0;
		Double sum_S = (double) 0;
		Double sum_G = (double) 0;
		for (int K = 0; K < total_KCs; K++) {
			int Kc = Utils.getKc(K);
			sum_IM = Operations.addDouble(sum_IM, Utils.getInitialMasteryMap(Kc));
			// System.out.println("sum_IM :"+sum_IM);
			sum_L = Operations.addDouble(sum_L, Utils.getLearnMap(Kc));
			// System.out.println("sum_L :"+sum_L);
			//System.out.println(" SAGAR C_IM :"+K+"  "+ Utils.getInitialMasteryMap(Kc));
			//System.out.println(" SAGAR C_L :"+K+"  "+ Utils.getLearnMap(Kc));
		}
		average_IM = Operations.divideDouble(sum_IM, Double.valueOf(total_KCs));
		average_L = Operations.divideDouble(sum_L, Double.valueOf(total_KCs));
		// System.out.println("average_IM :"+average_IM);
		// System.out.println("average_L :"+average_L);
		for (int Q = 0; Q < total_Q; Q++) {
			int question = Utils.getQuestion(Q);
			sum_S = Operations.addDouble(sum_S, Utils.getSlipMap(question));
			sum_G = Operations.addDouble(sum_G, Utils.getGuessMap(question));
			// System.out.println("sum_S :"+sum_S);
			// System.out.println("sum_G :"+sum_G);
			//System.out.println(" SAGAR C_S :"+Q+"  "+ Utils.getSlipMap(question));
			//System.out.println(" SAGAR C_G :"+Q+"  "+ Utils.getGuessMap(question));
		}
		average_S = Operations.divideDouble(sum_S, Double.valueOf(total_Q));
		average_G = Operations.divideDouble(sum_G, Double.valueOf(total_Q));
		// System.out.println("average_S :"+average_S);
		// System.out.println("average_G :"+average_G);
	}

	/**
	 * FINAL RESULT
	 * @throws FileNotFoundException 
	 */
	private static void PrintResult() throws FileNotFoundException {
		for (Map.Entry<Integer, ArrayList<Double>> entry : climbMap.entrySet()) {
			System.out.println();
			System.out.println(" CLIMB " + entry.getKey());
			ArrayList<Double> list = entry.getValue();
			System.out.println(" InitialMastery :" + list.get(0));
			System.out.println(" Learn :" + list.get(1));
			System.out.println(" Slip :" + list.get(2));
			System.out.println(" Guess :" + list.get(3));
			
			printHillTopsDifference();
		}
		try {
			CalculateROCCurve();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void CalculateROCCurve() throws IOException {
		
		int[] TP = new int[11];
		int[] FP = new int[11];
		int P = 0;
		int N = 0;
		double[] TPR = new double[11];
		double[] FPR = new double[11];
		//PrintStream o = new PrintStream(new File("C:/Users/lkusnoor/Downloads/CalculateROCCurve.txt"));
		//System.setOut(o);
		for (int Qi = 0; Qi < GlobalConstants.total_Questions ; Qi++) {
			int Q = Utils.getQuestion(Qi);
			System.out.println("Question "+Q);
			for (int St = 0; St < GlobalConstants.total_Students ; St++) {
				int S = Utils.getStudent(St);
				System.out.println("Student "+S);
				for (int T = 1; T <= Utils.getLast(S); T++) {
					//System.out.println("Attempt "+T);
					if (Q == Utils.getQuestion(S, T)) {
						ArrayList<Integer> KCs = Utils.getQuestionMatrix(Q);
						Double Threshold = new Double(1.0);
						
						for (int list_K = 0; list_K < KCs.size(); list_K++) {
							
							Threshold = Operations.multiplyDouble(Utils.getFetchBest(S, KCs.get(list_K), T), Threshold);
							System.out.print("KC"+list_K+" "+(Utils.getFetchBest(S, KCs.get(list_K), T))+" * ");
						}
						System.out.println();
						System.out.println("Threshold "+Threshold);
						String thresholdString = Double.toString(Threshold);
						String[] values = thresholdString.split("\\.");
						int n=0;
						if(values.length>1){
							n = Integer.valueOf(values[1].substring(0, 1));
						}
						
						if(Utils.getAnswer(S, T)==1){
							System.out.println("Answer is correct");
							for(int i=n+1; i<11;i++){
								TP[i]++;
							}
						}else{
							for(int i=0; i<=n;i++){
								FP[i]++;
							}
						}
					}
				}
				System.out.println();
			}
			System.out.print("TP Values  ");
			
			for(int i=0;i<11;i++){
				System.out.print(TP[i]+"  ");
			}
			System.out.println();
			System.out.print("FP Values  ");
			for(int i=0;i<11;i++){
				System.out.print(FP[i]+"  ");
			}
			System.out.println();
			System.out.println();
			System.out.println();
			
		}
		System.out.print("TP Values  ");
		
		for(int i=0;i<11;i++){
			System.out.print(TP[i]+"  ");
			P+=TP[i];
		}
		System.out.println();
		System.out.print("FP Values  ");
		for(int i=0;i<11;i++){
			System.out.print(FP[i]+"  ");
			N+=FP[i];
		}
		System.out.println();
		//TODO TPR calculation
		
		System.out.print("TPR Values  ");
		for(int i=0;i<11;i++){
			int tp =0;
			for(int j=i;j<11;j++){
				tp+=TP[j];
			}
			
			TPR[i] = Operations.divideDouble((double)tp, (double)P);
			System.out.print(TPR[i]+"  ");
		}
		System.out.println();
		System.out.print("FPR Values  ");
		for(int i=0;i<11;i++){
			int fp =0;
			for(int j=i;j<11;j++){
				fp+=FP[j];
			}
			
			FPR[i] = Operations.divideDouble((double)fp, (double)N);
			System.out.print(FPR[i]+"  ");
		}
		System.out.println();
		//o = new PrintStream(new File("C:/Users/lkusnoor/Downloads/CALIB3.txt"));
		//System.setOut(o);
		
			// index from 0,0... cell A1 is cell(0,0)
			HSSFRow row1 = worksheet.createRow(rowIndex++);

			HSSFCell cellA1 = row1.createCell((short) 0);
			cellA1.setCellValue("FPR");
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellA1.setCellStyle(cellStyle);

			HSSFCell cellB1 = row1.createCell((short) 1);
			cellB1.setCellValue("TPR");
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellB1.setCellStyle(cellStyle);

			HSSFCell cellC1 = row1.createCell((short) 2);
			cellC1.setCellValue("AUC");
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.RED.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellB1.setCellStyle(cellStyle);
			double curAUC = 0;
			
			for(int i=0;i<11;i++){
				HSSFRow row = worksheet.createRow(rowIndex++);
				HSSFCell fprcell = row.createCell(0);
				fprcell.setCellValue(new Double(FPR[i]));
				HSSFCell tprcell = row.createCell(1);
				tprcell.setCellValue(new Double(TPR[i]));
				HSSFCell aucCell = row.createCell(2);
				if(i>0){
					double Add = Operations.addDouble(TPR[i], TPR[i-1]);
					double devide = Operations.divideDouble(Add, (double)2);
					double sub = Operations.substractDouble(FPR[i-1],FPR[i]);
					double AUCresult = Operations.multiplyDouble(devide, sub);
					aucCell.setCellValue(new Double(AUCresult));
					curAUC+=AUCresult;
				}
			}
			climbCounter++;
			if(curAUC>BestAUC){
				BestAUC = curAUC;
				bestClimb=climbCounter;
				CalculateChangeInParams_BestAUC();
			}
			rowIndex++;
		
	}

	private static void printHillTopsDifference(){
		 int size = climbMap.size();
		 System.out.println();
		 System.out.println("printHillTopsDifference ");
		 for(int i=1;i<=size;i++){
		 	ArrayList<Double> list1 = climbMap.get(i);
		 	for(int j=i+1;j<=size;j++){
		 		ArrayList<Double> list2 = climbMap.get(j);
		 		Double IM_DIFF = Math.abs(Operations.substractDouble(list1.get(0), list2.get(0)));
		 		Double L_DIFF = Math.abs(Operations.substractDouble(list1.get(1), list2.get(1)));
		 		Double S_DIFF = Math.abs(Operations.substractDouble(list1.get(2), list2.get(2)));
		 		Double G_DIFF = Math.abs(Operations.substractDouble(list1.get(3), list2.get(3)));
		 				
		 		Double IM_Change = Operations.divideDouble(IM_DIFF,list1.get(0));
		 		Double L_Change = Operations.divideDouble(L_DIFF,list1.get(1));
		 		Double S_Change = Operations.divideDouble(S_DIFF,list1.get(2));
		 		Double G_Change = Operations.divideDouble(G_DIFF,list1.get(3));
		 		if((Double.compare(IM_Change, (Double)0.1) <=0 ) && (Double.compare(L_Change, (Double)0.1) <=0 ) && (Double.compare(S_Change, (Double)0.1) <=0 ) && (Double.compare(G_Change, (Double)0.1) <=0 )){
		 			System.out.println(i+ "  "+ list1);
		 			System.out.println(j+ "  "+ list2);
		 			System.out.println();
		 				}
		 			
		 			}
		 			
		 	}
		 	}
	
	private static void printCalibSimDifference(Double average_IM, Double average_L, Double average_S, Double average_G){
		 System.out.println("printCalibSimDifference ");
		 		Double IM_DIFF = Math.abs(Operations.substractDouble(average_IM, Random_IM));
		 		Double L_DIFF = Math.abs(Operations.substractDouble(average_L, Random_L));
		 		Double S_DIFF = Math.abs(Operations.substractDouble(average_S, Random_S));
		 		Double G_DIFF = Math.abs(Operations.substractDouble(average_G, Random_G));
		 				
		 		Double IM_Change = Operations.divideDouble(IM_DIFF,Random_IM);
		 		Double L_Change = Operations.divideDouble(L_DIFF,Random_L);
		 		Double S_Change = Operations.divideDouble(S_DIFF,Random_S);
		 		Double G_Change = Operations.divideDouble(G_DIFF,Random_G);
		 	/*	//if((Double.compare(IM_Change, (Double)0.1) <=0 ) && (Double.compare(L_Change, (Double)0.1) <=0 ) && (Double.compare(S_Change, (Double)0.1) <=0 ) && (Double.compare(G_Change, (Double)0.1) <=0 )){
		 			System.out.println(i+ "  "+ list1);
		 			System.out.println(j+ "  "+ list2);
		 			System.out.println();
		 				}
		 			
		 			//}
*/	
		 		System.out.println("IM_ChangeSC   " + IM_Change + "   L_ChangeSC:  " + L_Change + "  S_ChangeSC: " + S_Change	+ "    G_ChangeSC:  " + G_Change);
		 	}

	private static void CalculateChangeInParams_BestAUC() {
		HSSFSheet worksheet = workbook.createSheet("BESTCURVE"+bestClimb);
		rowIndex=0;
		HSSFRow row1 = worksheet.createRow(rowIndex++);

		HSSFCell cellA1 = row1.createCell((short) 0);
		cellA1.setCellValue("Simulated");
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellA1.setCellStyle(cellStyle);

		HSSFCell cellB1 = row1.createCell((short) 1);
		cellB1.setCellValue("Climb");
		cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellB1.setCellStyle(cellStyle);
		
		
		for (int K = 0; K < total_KCs; K++) {
			int Kc = Utils.getKc(K);
			HSSFRow row = worksheet.createRow(rowIndex++);
			HSSFCell SimCell = row.createCell(0);
			SimCell.setCellValue(new Double(fillrandom_initalMastery[K]));
			HSSFCell ClimbCell = row.createCell(1);
			ClimbCell.setCellValue(new Double(Utils.getInitialMasteryMap(Kc)));
		}
		
		for (int K = 0; K < total_KCs; K++) {
			int Kc = Utils.getKc(K);
			HSSFRow row = worksheet.createRow(rowIndex++);
			HSSFCell SimCell = row.createCell(0);
			SimCell.setCellValue(new Double(fillrandom_Learn[K]));
			HSSFCell ClimbCell = row.createCell(1);
			ClimbCell.setCellValue(new Double(Utils.getLearnMap(Kc)));
		}
		
		for (int Q = 0; Q < total_Q; Q++) {
			int question = Utils.getQuestion(Q);
			HSSFRow row = worksheet.createRow(rowIndex++);
			HSSFCell SimCell = row.createCell(0);
			SimCell.setCellValue(new Double(fillrandom_slip[Q]));
			HSSFCell ClimbCell = row.createCell(1);
			ClimbCell.setCellValue(new Double(Utils.getSlipMap(question)));
		}
		
		for (int Q = 0; Q < total_Q; Q++) {
			int question = Utils.getQuestion(Q);
			HSSFRow row = worksheet.createRow(rowIndex++);
			HSSFCell SimCell = row.createCell(0);
			SimCell.setCellValue(new Double(fillrandom_guess[Q]));
			HSSFCell ClimbCell = row.createCell(1);
			ClimbCell.setCellValue(new Double(Utils.getGuessMap(question)));
		}
			
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {

		// MySQLConnection.SetConnection();
		System.out.println("CALIBRATION.....................");
		
		fileOut = new FileOutputStream(AUXExcelFilePath);
		workbook = new HSSFWorkbook();
		worksheet = workbook.createSheet("AUC");
		PrintStream o = new PrintStream(new File("C:/Users/lkusnoor/Downloads/LOGS/MAIN/NEW/CALIB3.txt"));
		System.setOut(o);
		// SetDB
		// setDatabase();
		setSimulatedData();

		// TODO get data from DB
		total_students = GlobalConstants.total_Students;
		total_KCs = GlobalConstants.total_KCs;
		total_Q = GlobalConstants.total_Questions;

		// instantiation of old values
		old_initalMastery = new Double[GlobalConstants.total_KCs + 1];
		old_Learn = new Double[GlobalConstants.total_KCs + 1];
		old_slip = new Double[GlobalConstants.total_Questions + 1];
		old_guess = new Double[GlobalConstants.total_Questions + 1];
		
		// instantiation of fillRandom values
		fillrandom_initalMastery = new Double[GlobalConstants.total_KCs + 1];
		fillrandom_Learn = new Double[GlobalConstants.total_KCs + 1];
		fillrandom_slip = new Double[GlobalConstants.total_Questions + 1];
		fillrandom_guess = new Double[GlobalConstants.total_Questions + 1];
		// DatabaseResponse.setKcs();
		// setAnswerValues();
		// setQuestionValues();

		while (climb < 5) {
			//OUT======================================================================================================START
			/*o = new PrintStream(new File("C:/Users/lkusnoor/Downloads/LOGS/MAIN/"+climb+"fillRandomParameters.txt"));
			System.setOut(o);*/
			//OUT======================================================================================================END
			fillRandomParameters();
			/*if*/while (climbOnce().compareTo(Double.valueOf(0.1)) != -1) {}
				updateNewPrameters();
				ArrayList<Double> list = new ArrayList<Double>();
				list.add(average_IM);
				list.add(average_L);
				list.add(average_S);
				list.add(average_G);
				
				printCalibSimDifference(average_IM,average_L,average_S,average_G);
				
				climb++;
				
				climbMap.put(climb, list);
				//OUT======================================================================================================START
				/*o = new PrintStream(new File("C:/Users/lkusnoor/Downloads/LOGS/MAIN/"+climb+"parametersOP.txt"));
				System.setOut(o);*/
				//OUT======================================================================================================END
				System.out.println("CLIMB -----------------------------------------> " + climb);
				
				for (int KcIndex = 0; KcIndex < total_KCs; KcIndex++) {
					int Kc = Utils.getKc(KcIndex);
					String str = Utils.getInitialMasteryMap(Kc)>fillrandom_initalMastery[KcIndex]?"+":"-";
					System.out.println("OLD IM: "+ fillrandom_initalMastery[KcIndex]+"   "+"NEW IM: "+ Utils.getInitialMasteryMap(Kc)+"        "+str); 
				}
				System.out.println();
				for (int KcIndex = 0; KcIndex < total_KCs; KcIndex++) {
					int Kc = Utils.getKc(KcIndex);
					String str = Utils.getLearnMap(Kc)>fillrandom_Learn[KcIndex]?"+":"-";
					System.out.println("OLD L: "+ fillrandom_Learn[KcIndex]+"   "+"NEW L: "+ Utils.getLearnMap(Kc)+"      "+str); 
				}
				System.out.println();
				for (int Q = 0; Q < total_Q; Q++) {
					int q = Utils.getQuestion(Q);
					String str = Utils.getSlipMap(q)>fillrandom_slip[Q]?"+":"-";
					System.out.println("OLD S: "+ fillrandom_slip[Q]+"    "+"NEW S: "+ Utils.getSlipMap(q)+"     "+str); 
				}
				System.out.println();
				for (int Q = 0; Q < total_Q; Q++) {
					int q = Utils.getQuestion(Q);
					String str = Utils.getGuessMap(q)>fillrandom_guess[Q]?"+":"-";
					System.out.println("OLD G: "+ fillrandom_guess[Q]+"    "+"NEW G: "+ Utils.getGuessMap(q)+"      "+str); 
				}
				System.out.println();
				System.out.println();
				//PrintResult();
			}
		
		/*}*/
		
		//CalculateChangeInParams_BestAUC();
		
		
		try {
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
