function delay(time) {
  return new Promise(resolve => setTimeout(resolve, time));
}

var pTarget = 0;
var largeArr = [
//insert idSearch.txt













];

var textFile = null,

  makeTextFile = async function () {
    var bigString = "";
	for(var ind = 0; ind < largeArr.length; ind++){
		
		var perc = 100*ind/largeArr.length;
		if (perc >= pTarget){
			console.log(pTarget + "%");
			pTarget++;
		}
		
		var numString = JSON.stringify({id: largeArr[ind]});
		const e = await fetch(process.env.URL2, { method: "POST", body: new Blob([numString], { type: 'text/plain' } ) });
		const s = await e.json();
		
		for(var ind2 = 0; ind2 < s.length; ind2++){
			bigString += JSON.stringify(s[ind2]) + "\n";
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
makeTextFile();

//output into allids.txt