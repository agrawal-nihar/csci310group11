
/* Main function that controls image display */
/* Basically uses image attributes to set the dynamic image topic and src */

//not being called for some reason

class Collage {
  constructor(url, topic) {
    this.url = url;
    this.topic = topic;
  }
}

var historyBarCollages = [];
var mainCollage = new Collage("", "");



window.onload = function initialCollage(){
	var url_string = window.location.href;
  var url = new URL(url_string);
  
  var topic = url.searchParams.get("topic");
  var topicHeader = document.getElementById("topic");
  topicHeader.innerHTML = "Collage for Topic " + topic;
  
  var collageUrl = url.searchParams.get("collageUrl");
  var mainCollage = document.getElementById("main_collage");
  mainCollage.style.background = "url('" + collageUrl + "')";
  mainCollage.style.backgroundRepeat = "no-repeat";
  mainCollage.style.backgroundSize = "cover";
}

function toggleCollage(element){
	addMainCollageToHistoryBar();
	
	  //add old main collage to history bar
	  var mainCollageImage = document.getElementById("main_img");  
	  var historyBar = document.getElementById("history");
	  
	  //update the main collage image
	  console.log(element.src);
	  mainCollageImage.src = element.src ;  
  //DELETE THE CURRENT COLLAGE from history bar!!!
  
}

function handleDisable(){
  var button = document.getElementById("build");
  var inputText = document.getElementById("text_input");
  if(inputText.value == 0 || inputText.value == "" || inputText.value == null){
    button.disabled = true;
  }
  else{
    button.disabled = false;
  }
}

function buildAnotherCollage(){
  var topic = document.getElementById("text_input").value;
  var topicHeader = document.getElementById("topic");
  topicHeader.innerHTML = "Collage for Topic " + topic;

  var xHttp = new XMLHttpRequest();
  xHttp.open("GET", "CollageGeneratorServlet?action=build&topic="+topic+"&newUser=false", false);
  xHttp.send();
  var url = "";
  if (xHttp.responseText.trim().length > 0) {
	  url = xHttp.responseText.trim();
  }
  
  var collage = new Collage(url, topic);
  mainCollage = collage;
  historyBarCollages.push(collage);
  drawHistoryBar();
  //addMainCollageToHistoryBar();
  displayNewMainCollage();
}


function addMainCollageToHistoryBar() {
	  //add old main collage to history bar
	historyBarCollages.push(mainCollage);
	drawHistoryBar();
	
//	  var topicHeader = document.getElementById("topic");
//	  var topic = topicHeader.innerHTML.substring(17);
//	
//	  var mainCollageImage = document.getElementById("main_img");  
//	  var historyBar = document.getElementById("history");
//	  var currentMainCollageUrl = mainCollageImage.src;
//	  historyBar.innerHTML += "<img onclick = \"toggleCollage(this)\" alt = \"" + topic + "\" src = \"" + currentMainCollageUrl + "\"/>" 	  
}


function displayNewMainCollage(){
	  var mainCollageImage = document.getElementById("main_img");  
	  var historyBar = document.getElementById("history");
	  
	  //update the main collage image
	  mainCollageImage.src = mainCollage.url ;

  //mainCollage.style.background = "url('" + e.target.src + "')";
  var mainCollage = document.getElementById("main_collage");
  mainCollage.style.backgroundRepeat = "no-repeat";
  mainCollage.style.backgroundSize = "cover";
  
}

function drawHistoryBar() {
	console.log(historyBarCollages);
	console.log(historyBarCollages[0].topic);
	console.log(historyBarCollages[0].url);

	var historyBar = document.getElementById("history");

	for (var i = 0; i < historyBarCollages.length; i++) {
		historyBar.innerHTML += "<img onclick = \"toggleCollage(this)\" alt = \"" + historyBarCollages[i].topic + "\" src = \"" + historyBarCollages[i].url + "\"/>" 	  
	}
}

function exportCollage(e){
	var xHttp = new XMLHttpRequest();
	xHttp.open("GET", "CollageGeneratorServlet?action=download"+"&url="+e.target.src+"&newUser=false", false);
	xHttp.send();
}
