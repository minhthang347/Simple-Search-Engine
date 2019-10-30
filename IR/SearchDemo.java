import Model.RetrievelResults;
import Model.Evaluate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author ThangHuynh
 */
public class SearchDemo {
	public static String urlData = "./data/Cranfield";
	
	public static ArrayList<Map.Entry<String, Double>> getQuery(String query) {
		RetrievelResults results = new RetrievelResults();
		results.ProcessFile(urlData);
		results.ResultOfQuery(query);
		return results.getResults();
	}

	public static void PrintResult(String query, int number) throws IOException {
		ArrayList<Map.Entry<String, Double>> result = getQuery(query);

		for (int i = 0; i < result.size(); ++i) {
			String[] row = { String.valueOf(i + 1), result.get(i).getKey(), result.get(i).getValue().toString() };
			System.out.println(row[0] + " | File: " + row[1] + " | Do tuong dong: " + row[2]);
		}

		String urlResult = "./DEV-TEST/RES/" + Integer.toString(number) + ".txt";

		Evaluate.Calculate(result, urlResult);

		System.out.println("Do phu: " + Evaluate.getRecall() + "\nDo chinh xac: " +	 Evaluate.getPrecision());
	}

	public static ArrayList<String> GetQueryData() throws IOException {
		ArrayList<String> listQuery = new ArrayList<String>();

		BufferedReader reader = new BufferedReader(new FileReader("./DEV-TEST/query.txt"));
		String readQuery;

		while ((readQuery = reader.readLine()) != null) {
			if (readQuery =="")
				continue;
			else
			{
				String[] sentence = readQuery.split("\t");
				if(sentence.length>1)
					listQuery.add(sentence[1]);
			}
		}
		reader.close();
		return listQuery;
	}

	public static void main(String args[]) throws IOException {
		ArrayList<String> listQuery = SearchDemo.GetQueryData();

		System.out.print("Nhap thu tu cau query: ");
		// Scanner in = new Scanner(System.in);
		int number ;//= in.nextInt();
		number = 100;
		SearchDemo.PrintResult(listQuery.get(number - 1), number);

	}
}
