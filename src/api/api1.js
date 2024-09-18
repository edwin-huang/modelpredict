function delay(time) {
  return new Promise(resolve => setTimeout(resolve, time));
}

var pTarget = 0;

var textFile = null,

  makeTextFile = async function (min, max) {
    var bigString = "";
	var ind = min;
	var cid = -1;
	while(ind < max){
		
		var perc = 100*(ind - min)/(max - min);
		if (perc >= pTarget){
			console.log(pTarget + "%");
			pTarget++;
		}
		
		var numString = JSON.stringify({killid: ind, sort: "dps"});
		if(cid !== -1){
			numString = JSON.stringify({killid: ind, sort: "dps", classid: cid});
		}
		const e = await fetch(process.env.URL1, { method: "POST", body: new Blob([numString], { type: 'text/plain' } ) });
		const s = await e.json();
		
		if(cid !== -1 || s.length < 100){
			for(var ind2 = 0; ind2 < s.length; ind2++){
				bigString += JSON.stringify(s[ind2]) + "\n";
			}
		}
		if(cid === -1 && s.length < 100){
			cid += 5;
		}
		
		cid++;
		if(cid > 3){
			cid = -1;
			ind++;
		}
	}
	
	
	
    var data = new Blob([bigString], {type: 'text/plain'});

    // If we are replacing a previously generated file we need to
    // manually revoke the object URL to avoid memory leaks.
    if (textFile !== null) {
      window.URL.revokeObjectURL(textFile);
    }

    textFile = window.URL.createObjectURL(data);
	
	console.log("100%");
	console.log("done");
    // returns a URL you can use as a href
	console.log(textFile);
    return textFile;
  };
 makeTextFile(41434, 41587);
 //output into miniFile2.txt