import Model.Evaluate;
import Model.RetrievelResults;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class TestQueries {
	public static ArrayList<String> listQuery = new ArrayList<String>();
	
	public static void main(String args[]) throws IOException {
		long startTime = System.currentTimeMillis();
		
		System.out.println("Writting Results ....");
		ArrayList<String> listQuery = SearchDemo.GetQueryData();
		int count = 0;
		float map = 0;
		for (String query : listQuery)
		{
			count++;
			ArrayList<Map.Entry<String, Double>> result = SearchDemo.getQuery(query);
            BufferedWriter FileWriter = new BufferedWriter(new FileWriter("./AllQueryResult/" + Integer.toString(count) + ".txt"));
            for (int i = 0; i < result.size(); i++) {
    			String[] row = { String.valueOf(i + 1), result.get(i).getKey(), result.get(i).getValue().toString() };
    			FileWriter.write(row[0] + "\t" + row[1] + "   \t " + row[2]);
    			FileWriter.newLine();
            }
            String testResult = "./DEV-TEST/RES/" + Integer.toString(count) + ".txt";
            Evaluate.Calculate(result, testResult);
            float p = Evaluate.getPrecision();
            map +=p;
            System.out.println(query);
            System.out.println("P "+p);
            FileWriter.close();
		}
		// calculate time run 
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println((float)totalTime/1000 + "s");
		System.out.println(" ============== \n Done !");
		System.out.println("mAP: "+map/count);
	}
}
