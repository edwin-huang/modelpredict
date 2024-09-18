import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class gloombashing3 {
	
	public static void main(String[] args) throws IOException {
		
		FileReader fr = new FileReader("goodGloom.txt");
		BufferedReader in = new BufferedReader(fr);
		PrintWriter out = new PrintWriter("finalGloom.txt");
		ArrayList<String> outputs = new ArrayList<>();
		
		int counter = 0;
		
		String str;
		String previous = "";

		//out.printf("%-20s%-20s%s%n","Player 1", "Player 2", "Confidence");

		while((str = in.readLine()) != null) {
			String[] iForgotThisNameBeforeRefactoring = str.split(" ");
			String a = iForgotThisNameBeforeRefactoring[0];
			String b = iForgotThisNameBeforeRefactoring[1];
			double real = Double.parseDouble(iForgotThisNameBeforeRefactoring[2]);
			double exp = Double.parseDouble(iForgotThisNameBeforeRefactoring[3]);
			double likely = Double.parseDouble(iForgotThisNameBeforeRefactoring[4]);
			double shared = Double.parseDouble(iForgotThisNameBeforeRefactoring[5]);

			double dfactor = (exp - real - 15 * Math.sqrt(real)) * Math.max(0.01, likely - 0.5) * 3.6 * (1 + 0.5 * Math.min(shared, 5) + (shared > 0 ? 0.5 : 0)) - 15;
			//dfactor = (exp - real - 1 * Math.sqrt(real)) * 1.5 * likely - 50 + (real>0?0:-10000);
			//dfactor = (real - exp - 3 * Math.sqrt(exp)) * 2 * likely * likely * likely - 15;
			if(dfactor > 0) {
				if(!a.equals(previous)) {
					//out.println();
					//out.println(a);
				}
				previous = a;
				int value = (int) Math.floor(dfactor/5) * 5 + 15;
				//double value = Math.floor(dfactor * 100)/100 + 15;
				outputs.add(a + " " + b + " " + (value >= 40 ? "40+" : value));
				//outputs.add(a + " " + b);
				//out.printf("%-20s%-20s%s%n",a, b, Math.round((dfactor + 15) * 10)/10.0);
				//String c = dfactor > -10 ? dfactor > 0 ? dfactor > 5 ? dfactor > 10 ? dfactor > 15 ? "Very High" : "High" : "Medium" : "Low" : "Unknown" : "None";
				//out.println("-" + b + " " + c);
				//out.println(str);
				counter++;
			}
		}

		outputs.sort(Comparator.comparing(String::toLowerCase));
		for(String outp : outputs){
			out.println(outp);
		}
		
		out.close();
		System.out.println("done");
		System.out.println(counter);
	}
}

