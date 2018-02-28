
window.onload = function onEnter(){
	var inputText = document.getElementById("text_input");
	var button = document.getElementById("build");
	inputText.addEventListener("keyup", function(event){
		if(event.keyCode === 13){
			console.log("enter");
			button.click();
		}
	});
}

/*
 * This function controls the availability of Build Collage button which enables the button 
 * if and only if the user has given a term into intput text. If there is nothing on the input text
 * it will remain disabled.
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
 * Once the user clickes Build Collage, it will trigger this function to create a collage by sending singal
 * to our servlet to begin searching for the images and generate collage using those images. Once the servlet generates
 * the collage, it will direct user to collage.html to display the collage.
 */
function buildCollage(){
	var topic = document.getElementById("text_input").value;
	var xHttp = new XMLHttpRequest();	
	var collageURL;

	xHttp.open("GET", "CollageGeneratorServlet?action=build&topic="+topic+"&newUser=true", true);
	xHttp.send();
	xHttp.onreadystatechange = function() {
		collageURL = this.responseText;
		sessionStorage.setItem("url", collageURL)
		window.location="collage.html?topic=" + topic ;
	}
	

}