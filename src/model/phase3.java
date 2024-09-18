import java.io.*;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class gloombashing2 {
	public static LocalDate l = LocalDate.of(2021, 12, 11);
	public static LocalDate lStart = LocalDate.of(2021, 12, 11);
	public static int weeksBetween(LocalDate startDate, LocalDate endDate) {
		return (int) startDate.until(endDate, ChronoUnit.DAYS)/7;
	}

	public static int realOverlap(HashMap<Long, HashMap<Integer, HashSet<Integer>>> data2, long pid1, long pid2) {
		if(!data2.containsKey(pid1) || !data2.containsKey(pid2)) {
			return -1;
		}
		int count = 0;
		if(data2.get(pid1).size() >= data2.get(pid2).size()) {
			long temp = pid1;
			pid1 = pid2;
			pid2 = temp;
		}
		for(int val : data2.get(pid1).keySet()) {
			if(!data2.get(pid2).containsKey(val)) {
				continue;
			}
			HashSet<Integer> set1 = data2.get(pid1).get(val);
			HashSet<Integer> set2 = data2.get(pid2).get(val);
			HashSet<Integer> smaller = set1;
			HashSet<Integer> larger = set2;
			if(set1.size() > set2.size()) {
				larger = set1;
				smaller = set2;
			}
			for(int val2 : smaller) {
				if(larger.contains(val2)) {
					count++;
				}
			}
		}
		return count;
	}
	
	public static double expectedOverlap(HashMap<Long, HashMap<Integer, int[]>> data, HashMap<Integer, int[]> globaldata, long pid1, long pid2) {
		if(!data.containsKey(pid1) || !data.containsKey(pid2)) {
			return -1;
		}
		double count = 0;
		for(int val : data.get(pid1).keySet()) {
			if(data.get(pid2).containsKey(val)) {
				for(int i = 0; i < 8; i++) {
					if(globaldata.get(val)[i] != 0) {
						count += (0.0 + data.get(pid1).get(val)[i]) * data.get(pid2).get(val)[i]/globaldata.get(val)[i];
					}
				}
			}
		}
		return count;
	}

	public static double errorFunction(HashMap<Long, double[]> data3, long pid1, long pid2) {
		double max1 = 0;
		double max2 = 0;
		double c1 = 0;
		double c2 = 0;
		for(int indx = 0; indx < 8; indx++) {
			c1 += data3.get(pid1)[indx];
			c2 += data3.get(pid2)[indx];
			if(c1 - c2 > max1){
				max1 = c1 - c2;
			}
			if(c2 - c1 > max2){
				max2 = c2 - c1;
			}
		}
		return 1 - max1 - max2;
	}

	public static int sharedItems(HashMap<Long, HashSet<String>> itemOwn, long pid1, long pid2) {
		int ans = 0;
		if(!(itemOwn.containsKey(pid1) && itemOwn.containsKey(pid2))){
			return ans;
		}
		for(String id1 : itemOwn.get(pid1)){
			if(itemOwn.get(pid2).contains(id1)){
				ans++;
			}
		}
		return ans;
	}
	
	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(new File("lessGiantFile.txt"));
		Scanner in2 = new Scanner(new File("itemOwns.txt"));
		PrintWriter out = new PrintWriter("goodGloom.txt");
		HashMap<Long, HashMap<Integer, int[]>> data = new HashMap<>();
		HashMap<Long, HashMap<Integer, HashSet<Integer>>> data2 = new HashMap<>();
		HashMap<Long, double[]> data3 = new HashMap<>();
		HashMap<Long, int[]> data4 = new HashMap<>();
		HashMap<Integer, int[]> globaldata = new HashMap<>();
		HashMap<Long, String> pids = new HashMap<>();
		HashMap<String, Long> rpids = new HashMap<>();
		HashMap<Long, HashSet<String>> itemOwn = new HashMap<>();
		
		int counter = 0;
		int lastgloom = -1;

		while(in2.hasNext()){
			long pid = in2.nextLong();
			in2.nextLine();
			itemOwn.put(pid,new HashSet<>(Arrays.asList(in2.nextLine().split(" "))));
		}

		while(in.hasNext()) {
			String name = in.next();
			long pid = in.nextLong();
			int killid = in.nextInt();
			in.nextInt(); //patch
			in.nextInt(); //dps
			in.nextInt(); //hps
			in.nextInt(); //mps
			in.nextInt(); //gs
			in.nextInt(); //duration
			in.nextInt(); //active
			in.nextInt(); //deaths
			int perf = in.nextInt();
			String time = in.next();
			
			int year = Integer.parseInt(time.substring(0, 4));
			int month = Integer.parseInt(time.substring(5, 7));
			int day = Integer.parseInt(time.substring(8, 10));
			int session = Integer.parseInt(time.substring(11, 13))/3;

			l = LocalDate.of(year, month, day);
			int isWeekend = 0;
			if(l.getDayOfWeek().getValue() > 5) {
				isWeekend = 1;
			}
			
			int week2 = 2 * weeksBetween(lStart, l) + isWeekend;
			int weekletsession = 8 * week2 + session;
			
			if(perf > 50) {
				pids.put(pid, name);
				rpids.put(name, pid);
				if(data.containsKey(pid)) {
					int[] sessioncounter = data.get(pid).containsKey(week2) ? data.get(pid).get(week2) : new int[8];
					sessioncounter[session]++;
					data.get(pid).put(week2, sessioncounter);
					
					if(data2.get(pid).containsKey(weekletsession)) {
						data2.get(pid).get(weekletsession).add(killid);
					}
					else {
						HashSet<Integer> a2 = new HashSet<>();
						a2.add(killid);
						data2.get(pid).put(weekletsession, a2);
					}
					
					data3.get(pid)[session]++;
					data4.get(pid)[1] = killid;
				}
				else {
					HashMap<Integer, int[]> a1 = new HashMap<>();
					int[] sessioncounter = new int[8];
					sessioncounter[session]++;
					a1.put(week2, sessioncounter);
					data.put(pid, a1);
					
					HashMap<Integer, HashSet<Integer>> a2m = new HashMap<>();
					HashSet<Integer> a2 = new HashSet<>();
					a2.add(killid);
					a2m.put(weekletsession, a2);
					data2.put(pid, a2m);
					
					double[] a3 = {0, 0, 0, 0, 0, 0, 0, 0};
					a3[session]++;
					data3.put(pid, a3);

					int[] a4 = {killid, killid};
					data4.put(pid, a4);
				}
			}
			if(killid != lastgloom) {
				int[] sessioncounter = globaldata.containsKey(week2) ? globaldata.get(week2) : new int[8];
				sessioncounter[session]++;
				globaldata.put(week2, sessioncounter);
			}
			lastgloom = killid;
			
			counter++;
		}
		
		HashSet<Long> toDelete = new HashSet<>();
		for(long pid : data2.keySet()) {
			int size2 = 0;
			for(int wk : data2.get(pid).keySet()) {
				size2 += data2.get(pid).get(wk).size();
			}
			if(size2 < 10) {
				toDelete.add(pid);
				continue;
			}
			for(int indx = 0; indx < 8; indx++) {
				data3.get(pid)[indx] = Math.round(1000*data3.get(pid)[indx]/size2)/1000.0;
			}
		}
		
		for(long pid : toDelete) {
			data.remove(pid);
			data2.remove(pid);
			data3.remove(pid);
		}
		
		System.out.println(data.size());


		for(long pid1 : data.keySet()) {
			for(long pid2 : data.keySet()) {
				if(pid1 > pid2 && data4.get(pid1)[0] <= data4.get(pid2)[1] && data4.get(pid2)[0] <= data4.get(pid1)[1]) {
					double expect = expectedOverlap(data, globaldata, pid1, pid2);
					if(expect > 0) {
						String strout = (realOverlap(data2, pid1, pid2) + " " + Math.round(10 * expect) / 10.0 + " " + Math.round(100 * errorFunction(data3, pid1, pid2)) / 100.0 + " " + sharedItems(itemOwn, pid1, pid2));
						out.println(pids.get(pid1) + " " + pids.get(pid2) + " " + strout);
						out.println(pids.get(pid2) + " " + pids.get(pid1) + " " + strout);
					}
				}
			}
		}
		
		out.close();
		
		System.out.println(counter);
		System.out.println("done");
	}
}

