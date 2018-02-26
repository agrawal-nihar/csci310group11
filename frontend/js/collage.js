
/* Main function that controls image display */
/* Basically uses image attributes to set the dynamic image title and src */

/*
 * When the collage.html is launched, it will create the topic section with "Collage for Topic <topic>"
 * It will store the current URL of the page as well as the topic.
 */
window.onload = function initialCollage(){
  var url_string = window.location.href;
  var url = new URL(url_string);
  var title = url.searchParams.get("title");
  var topicHeader = document.getElementById("topic");
  topicHeader.innerHTML = "Collage for Topic " + title;
}

/*
 * This function changes the content of collage.html whenever the user clicks one of the previous collage
 * inside of history bar. It will change the main collage image to the collage that user pressed and it would
 * also change the title of the page.
 */
function toggleCollage(e){
  var topicHeader = document.getElementById("topic");
  var mainCollage = document.getElementById("main_collage");
  topicHeader.innerHTML = "Collage for Topic " + e.target.alt;
  mainCollage.style.background = "url('" + e.target.src + "')";
  mainCollage.style.backgroundRepeat = "no-repeat";
  mainCollage.style.backgroundSize = "cover";
}

/*
 * This fucntion enable the Build Another Collage button when the user inputs a text into the input text box.
 * If the input text is empty, it won't allow users to click the Build Collage button by disabling it.
 */
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

/*
 * This function triggers when the user clicks for another collage by inserting the text inside of input text of collage.html
 * If will then call the serlet to find the valid images and create a new collage. It will lead users into another collage.html
 * that would display the images of inserted text.
 */
function buildAnotherCollage(){
  var title = document.getElementById("text_input").value;
  var topicHeader = document.getElementById("topic");
  topicHeader.innerHTML = "Collage for Topic " + title;
  //need to change mainCollage
  var xHttp = new XMLHttpRequest();
  xHttp.open("GET", "BuildCollage?title="+title, false);
  xHttp.send();
}


/*
 * This function allow users to download the current collage displayed onto their storage. 
 * It would send the signal back to servlet to trigger the downloadCollageToUserStorage.
 */
function exportCollage(){
	var xHttp = new XMLHttpRequest();
	xHttp.open("GET", "ExportCollage", false);
	xHttp.send();
}
