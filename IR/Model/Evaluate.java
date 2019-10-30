package Model;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class Evaluate {
	private static float _recall;
	private static float _precision;
	
	public static void Calculate(ArrayList<Map.Entry<String, Double>> result, String url) throws IOException {
		BufferedReader reader;
		int countSimilar = 0;
		int countTestResult = 0;
		ArrayList<String> testResult = new ArrayList<String>();
		ArrayList<String> searchResult = new ArrayList<String>();
		int Size = 0;
		
		System.out.println(url);
		try {
			reader = new BufferedReader(new FileReader(url));
			String line;
			
	        while ((line = reader.readLine())!= null){
	        	countTestResult++;
		        String[] lines = line.split(" ");
		        String[] liness = lines[1].split("\t");
		        testResult.add(liness[0] + ".txt");
	        }
	        
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Size = searchResult.size(); if (result.size() > 10) Size = 10; else Size =
		 * result.size();
		 */
		Size = testResult.size();
		for (int i = 0; i < Size; ++i) {
			String[] row = { String.valueOf(i + 1), result.get(i).getKey(), result.get(i).getValue().toString() };
			searchResult.add(row[1]);
		}
		
		for (String text:testResult)
		{
			if (searchResult.contains(text))
				countSimilar ++;
		}
		System.out.println("So tai lieu giong: "+countSimilar);
		
		setRecall((float)countSimilar/countTestResult);
        
		setPrecision((float)countSimilar/Size);
	}
	
	public static float getRecall() {
		return _recall;
	}

	public static void setRecall(float _recall) {
		Evaluate._recall = _recall;
	}

	public static float getPrecision() {
		return _precision;
	}

	public static void setPrecision(float _precision) {
		Evaluate._precision = _precision;
	}
}
