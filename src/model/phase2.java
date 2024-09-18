import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Comparator;

import java.awt.*;

public class gloombashing extends Canvas {
	
	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("giantFile.txt");
		BufferedReader in = new BufferedReader(fr);
		PrintWriter out = new PrintWriter("lessGiantFile.txt");
		PrintWriter out2 = new PrintWriter("itemSearch.txt");
		PrintWriter out3 = new PrintWriter("idSearch.txt");
		PrintWriter out4 = new PrintWriter("itemOwns.txt");
		
		HashMap<Long, Integer> totalkills = new HashMap<>();
		HashMap<Long, Integer> lastglm = new HashMap<>();
		HashMap<Integer, String> itemMap = new HashMap<>();
		HashMap<Long, String> pids = new HashMap<>();
		HashMap<Long, HashSet<String>> itemOwn = new HashMap<>();

		HashSet<Integer> uniqueids = new HashSet<>();

		HashMap<Long, Integer> classMap = new HashMap<>();

		int counter = 0;
		int mecount = 0;
		String str;
		int lastgloom = 0;
		 
		
		while((str = in.readLine()) != null) {
			int id = Integer.parseInt(str.substring(str.indexOf("id") + 4, str.indexOf("playerid") - 2));
			long pid = Long.parseLong(str.substring(str.indexOf("playerid") + 10, str.indexOf("bossid") - 2));
			int killid = Integer.parseInt(str.substring(str.indexOf("killid") + 8, str.indexOf("patch") - 2));
			int patch = Integer.parseInt(str.substring(str.indexOf("patch") + 7, str.indexOf("dps") - 2));
			int dps = Integer.parseInt(str.substring(str.indexOf("dps") + 5, str.indexOf("hps") - 2));
			int hps = Integer.parseInt(str.substring(str.indexOf("hps") + 5, str.indexOf("ohps") - 2));
			int mps = Integer.parseInt(str.substring(str.indexOf("mps") + 5, str.indexOf("gs") - 2));
			int gs = Integer.parseInt(str.substring(str.indexOf("gs") + 4, str.indexOf("duration") - 2));
			int duration = Integer.parseInt(str.substring(str.indexOf("duration") + 10, str.indexOf("active") - 2));
			int active = Integer.parseInt(str.substring(str.indexOf("active") + 8, str.indexOf("deaths") - 2));
			int deaths = Integer.parseInt(str.substring(str.indexOf("deaths") + 8, str.indexOf("perfdps") - 2));
			int perfdps = Integer.parseInt(str.substring(str.indexOf("perfdps") + 9, str.indexOf("perfhps") - 2));
			int perfhps = Integer.parseInt(str.substring(str.indexOf("perfhps") + 9, str.indexOf("perfmit") - 2));
			int perfmit = Integer.parseInt(str.substring(str.indexOf("perfmit") + 9, str.indexOf("dmg") - 2));
			int level = Integer.parseInt(str.substring(str.indexOf("level") + 7, str.indexOf("}")));
			int perf = Math.max(perfdps, Math.max(perfhps, perfmit));
			int classy = Integer.parseInt(str.substring(str.indexOf("\"class") + 8, str.indexOf("\"class") + 9));
			String time = str.substring(str.indexOf("time") + 7, str.indexOf("dpstotal") - 3);
			String name = str.substring(str.indexOf("name") + 7, str.indexOf("faction") - 3);
			String[] casts = str.substring(str.indexOf("casts") + 8, str.indexOf("skills") - 3).split(",");
			String[] skills = str.substring(str.indexOf("skills") + 9, str.indexOf("stats") - 3).split(",");
			String[] stats = str.substring(str.indexOf("stats") + 8, str.indexOf("equip") - 3).split(",");
			String[] equip = str.substring(str.indexOf("equip") + 8, str.indexOf("time") - 3).split(",");

			classMap.put(pid, classy);

			double[] newstats = new double[8];
			for(int i = 0; i < 8; i++) {
				newstats[i] = Double.parseDouble(stats[i]);
			}

			pids.put(pid, name);


		    if(equip.length > 1) {
			    for(String ssid : equip) {
					HashSet<String> pidOwn = itemOwn.getOrDefault(pid, new HashSet<>());
					pidOwn.add(ssid);
					itemOwn.put(pid, pidOwn);
			    	if(!uniqueids.contains(id) && !itemMap.containsKey(Integer.parseInt(ssid)) && killid >= appendage.idthing){
			    		uniqueids.add(id);
			    	}
			    	itemMap.put(Integer.parseInt(ssid), name + " " + killid);
			    }
		    }
		    if(killid > 0) {
		    	totalkills.put(pid, totalkills.getOrDefault(pid, 0) + 1);
		    }

			if(lastglm.containsKey(pid) && lastglm.get(pid) >= killid) {System.out.println("repeat detected at " + lastgloom + " " + killid + " " + name);}
			lastglm.put(pid, killid);
			if(killid < lastgloom || killid > lastgloom + 2) {System.out.println("repeat detected at " + lastgloom + " " + killid);}

		    lastgloom = killid;
			
			out.println(name);
			out.println(pid);
			out.println(killid);
			out.println(patch);
			out.println(dps);
			out.println(hps);
			out.println(mps);
			out.println(gs);
			out.println(duration);
			out.println(active);
			out.println(deaths);
			out.println(perf);
			out.println(time);
			out.println();
		}
		
		for(int key : itemMap.keySet()) {
			out2.println(key + " " + itemMap.get(key));
		}

        ArrayList<Integer> glomids = new ArrayList<>(uniqueids);
		glomids.sort(Comparator.naturalOrder());
		for(int key : glomids) {
			out3.print(key + ",");
		}

		for(long pid : itemOwn.keySet()){
			out4.println(pid);
			for(String idOwn : itemOwn.get(pid)){
				out4.print(idOwn + " ");
			}
			out4.println();
		}
		
		out.close();
		out2.close();
		out3.close();
		out4.close();

		System.out.println(lastgloom);
		System.out.println("done");
		System.out.println(counter);
		System.out.println(mecount);
	}
}

