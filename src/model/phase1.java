import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class appendage {
	public static final int idthing = 41434;
	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("miniFile.txt");
		BufferedReader in = new BufferedReader(fr);
		PrintWriter out = new PrintWriter(new FileOutputStream("giantFile.txt", false));
		String str;
		while((str = in.readLine()) != null) {
			out.println(str);
		}
		out.close();
		System.out.println("done");
		
		FileReader fr2 = new FileReader("miniFile2.txt");
		BufferedReader in2 = new BufferedReader(fr2);
		PrintWriter out2 = new PrintWriter(new FileOutputStream("giantFile.txt", true));
		String str2;
		while((str2 = in2.readLine()) != null) {
			out2.println(str2);
		}
		out2.close();
		System.out.println("done2");
	}
}
