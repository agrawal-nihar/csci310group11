/* Main function that controls image display */
/* Basically uses image attributes to set the dynamic image title and src */

/* Global variable to keep track of current collage clicked */
var currentCollage = null;
var idCounter = 0;

/* What happens on initial loading page */
window.onload = function initialCollage(){
	//Adding event listener
	var inputText = document.getElementById("text_input");
	var button = document.getElementById("build");
	inputText.addEventListener("keyup", function(event){
		if(event.keyCode === 13){
			button.click();
		}
	});
  var mainCollage = document.getElementById("main_collage");
  var topicHeader = document.getElementById("topic");

  //Extract collageURL and topic from the URL of the page
  var url_string = window.location.href;
  var url = new URL(url_string);
  var topic = url.searchParams.get("topic");
  var preCollageURL = sessionStorage.getItem("collageURL");
  
  var collageMetaData = "data:image/png;base64, ";
  var collageURL = collageMetaData + preCollageURL;
  
  //Creating a new image element with new resources
  var newCollageToDisplay = document.createElement("IMG");

  idCounter++;


  newCollageToDisplay.setAttribute("src", collageURL);
  newCollageToDisplay.setAttribute("alt", topic);
  newCollageToDisplay.setAttribute("id", idCounter);



  newCollageToDisplay.setAttribute("onclick", "toggleCollage(event)");

  //Appending new image to history bar 
  var historyBar = document.getElementById("history");
  historyBar.insertAdjacentElement('afterbegin', newCollageToDisplay);
  var newCollage = document.getElementById("history").children[0];
  newCollage.style.display = "none";
  currentCollage = newCollage;

  //Setting topic header
  topicHeader.innerHTML = "Collage for Topic " + topic;
  
  //If collage URL is null, display error
  if(preCollageURL === "null"){
    currentCollage = null;
    displayError();
  }
  //Otherwise display it as the main collage
  else{
	var mainImg = document.getElementById("main_image");
	mainImg.setAttribute("src", collageURL);
  }
}

/*
 * This function changes the content of collage.html whenever the user clicks one of the previous collage
 * inside of history bar. It will change the main collage image to the collage that user pressed and it would
 * also change the title of the page.
 */
function toggleCollage(e){
  var mainImg = document.getElementById("main_image");

/* turning history thumbnail back on */
  if(currentCollage != null){
    var currentImg = document.getElementById(currentCollage.id);
    currentImg.style.display = "inline";
  }

/* changing current collage variable to image pressed and applying image pressed to main collage */
  hideError();
  currentCollage = e.target;

  mainImg.setAttribute("src", e.target.src);
  mainImg.setAttribute("alt", e.target.alt);
  document.getElementById("topic").innerHTML = "Collage for Topic " + e.target.alt;


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
  document.getElementById("main_image").style.display = "none";
}

/* hiding error div */
function hideError(){
  document.getElementById("error").style.display = "none";
  document.getElementById("main_image").style.display = "block";
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
    // this is called on every state change
	  if (xHttp.readyState === 4 && xHttp.status === 200) {
		  collageURL = this.responseText;
		  displayNewCollage(collageURL); 
	}
  }
}

//callback to buildAnotherCollage method where you append and change new items.
function displayNewCollage(collageURL){
	var topic = document.getElementById("text_input").value;
  var mainImg = document.getElementById("main_image");
  var topicHeader = document.getElementById("topic");
  /* first collageURL to delete for final deployment */
  var preCollageURL = collageURL;
  var collageURL = "data:image/png;base64, " + collageURL;
  
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


  var text = document.getElementById("text_input").value;
  topicHeader.innerHTML = "Collage for Topic " + text;
 
  //displaying history thumbnail
  if(currentCollage != null){
	    var currentImg = document.getElementById(currentCollage.id);
	    currentImg.style.display = "inline";
  }
  
  currentCollage = newCollage;
  
  //showing error if new image is null
  if(currentCollage.src == null || currentCollage.src == ""){
    currentCollage = null;
    displayError();
  }
  
  //setting main image to new image if it is not null
  else{
	if ( document.getElementById("error").style.display == "block") {
    		document.getElementById("error").style.display = "none";
    }
	    
    mainImg.setAttribute("src", currentCollage.src);
    mainImg.setAttribute("alt", currentCollage.alt);
    mainImg.style.display = "block";

  }
}

/*
 * This function allow users to download the current collage displayed onto their storage.
 * It would send the signal back to servlet to trigger the downloadCollageToUserStorage.
 */
function downloadCollage() {
	var xHttp = new XMLHttpRequest();
	xHttp.open("POST", "CollageGeneratorServlet", false);
	xHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	var id = currentCollage.id;
	xHttp.send("newUser=false&action=download&currentCollageId=" + id);
}
