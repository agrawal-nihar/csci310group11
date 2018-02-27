
/* Main function that controls image display */
/* Basically uses image attributes to set the dynamic image title and src */

// window.onload = function initialCollage(){
//   var url_string = window.location.href;
//   var url = new URL(url_string);
//   var title = url.searchParams.get("title");
//   var topicHeader = document.getElementById("topic");
//   topicHeader.innerHTML = "Collage for Topic " + title;
// }

var currentCollage = null;
var idCounter = 0;
/*testing with dummy response image for correct toggling behavior */

window.onload = function initialCollage(){
  var mainCollage = document.getElementById("main_collage");
  var topicHeader = document.getElementById("topic");
  
  //extract collageURL and topic from the URL of the page
  var url_string = window.location.href;
  var url = new URL(url_string);
  var topic = url.searchParams.get("topic");
  //var collageURL = url.searchParams.get("collageURL");  
  //var collageURL = "assets/brain.png";
  var collageURL = "";
  
  //New Collage as a DOM element
  var newCollageToDisplay = document.createElement("IMG");
  idCounter++;

  newCollageToDisplay.setAttribute("src", collageURL);
  newCollageToDisplay.setAttribute("alt", topic);
  newCollageToDisplay.setAttribute("id", idCounter);
  newCollageToDisplay.setAttribute("onclick", "toggleCollage(event)");

  var historyBar = document.getElementById("history");
  historyBar.insertAdjacentElement('afterbegin', newCollageToDisplay);
  var newCollage = document.getElementById("history").children[0];
  newCollage.style.display = "none";
  currentCollage = newCollage;

  topicHeader.innerHTML = "Collage for Topic " + topic;
  if(collageURL == null || collageURL == ""){
    currentCollage = null;
    displayError();
  }
  else{
	mainCollage.style.background = "url('" + collageURL + "')";
    mainCollage.style.backgroundRepeat = "no-repeat";
    mainCollage.style.backgroundSize = "cover";
  }
}

/*
 * This function changes the content of collage.html whenever the user clicks one of the previous collage
 * inside of history bar. It will change the main collage image to the collage that user pressed and it would
 * also change the title of the page.
 */
function toggleCollage(e){
  var topicHeader = document.getElementById("topic");
  var mainCollage = document.getElementById("main_collage");

/* turning history thumbnail back on */
  if(currentCollage != null){
    var currentImg = document.getElementById(currentCollage.id);
    currentImg.style.display = "inline";
  }

/* changing current collage variable to image pressed and applying image pressed to main collage */
  hideError();
  currentCollage = e.target;
  topicHeader.innerHTML = "Collage for Topic " + e.target.alt;
  mainCollage.style.background = "url('" + e.target.src + "')";
  mainCollage.style.backgroundRepeat = "no-repeat";
  mainCollage.style.backgroundSize = "cover";

/* hiding new current collage history display */
  hideCurrentCollageThumbnail();
}

function hideCurrentCollageThumbnail(){
  var currentImg = document.getElementById(currentCollage.id);
  currentImg.style.display = "none";
}

/* if input text box is empty, "build collage" button is disabled */

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

/* displaying error div */
function displayError(){
  document.getElementById("error").style.display = "block";
}

/* hiding error div */
function hideError(){
  document.getElementById("error").style.display = "none";
}

/*sending get request to backend */

function buildAnotherCollage(){
  var topic = document.getElementById("text_input").value;
  var topicHeader = document.getElementById("topic");
  topicHeader.innerHTML = "Collage for Topic " + topic;
  
  //need to change mainCollage
  var xHttp = new XMLHttpRequest();
  xHttp.open("GET", "CollageGeneratorServlet?action=build&topic="+topic+"&newUser=false", true);
  /* build new collage here */
  /* basically what will happen is upon retrieval, we will make a new image child under the history bar */
  /* after making a new child, we will append call the newCollage method */
  xHttp.send();
  xHttp.onreadystatechange = function() {
	  collageURL = this.responseText;
	  displayNewCollage(collageURL);
  }
}

function displayNewCollage(collageURL){
  var mainCollage = document.getElementById("main_collage");
  var topicHeader = document.getElementById("topic");
  
  //New Collage as a DOM element
  var newCollageToDisplay = document.createElement("IMG");
  idCounter++;
  newCollageToDisplay.setAttribute("src", collageURL);
  newCollageToDisplay.setAttribute("alt", topic);
  newCollageToDisplay.setAttribute("id", idCounter);
  newCollageToDisplay.setAttribute("onclick", "toggleCollage(event)");

  var historyBar = document.getElementById("history");
  historyBar.insertAdjacentElement('afterbegin', newCollageToDisplay);
  var newCollage = document.getElementById("history").children[0];
  newCollage.style.display = "none";
  currentCollage = newCollage;

  var text = document.getElementById("text_input").value;
  topicHeader.innerHTML = "Collage for Topic " + text;
  if(currentCollage.src == null || currentCollage.src == ""){
    currentCollage = null;
    displayError();
  }
  else{
    mainCollage.style.background = "url('" + currentCollage.src + "')";
    mainCollage.style.backgroundRepeat = "no-repeat";
    mainCollage.style.backgroundSize = "cover";
  }
}


/*
 * This function allow users to download the current collage displayed onto their storage. 
 * It would send the signal back to servlet to trigger the downloadCollageToUserStorage.
 */
function exportCollage() {
	var mainCollage = document.getElementById("main_collage");
	console.log("Main collage URL");
	
	//Parses the background to extract just the server destination of the file
	var mainCollageUrl = mainCollage.style.background.substring(49, mainCollage.style.background.length-26);
	console.log(mainCollageUrl); //in the format of assets/DOGXXXX.png
	var urlToSend =  mainCollageUrl;
	console.log("Collage URL to send to backend");
	console.log(urlToSend);

	var xHttp = new XMLHttpRequest();
	xHttp.open("GET", "CollageGeneratorServlet?action=download" + "&url=" + mainCollageUrl + "&newUser=false", false);
	xHttp.send();
}
