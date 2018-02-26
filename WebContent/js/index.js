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

function buildCollage() {
	//first time a collage is being built in this session
	var topic = document.getElementById("text_input").value;
	var xHttp = new XMLHttpRequest();
	xHttp.open("GET", "CollageGeneratorServlet?action=build&topic="+topic+"&newUser=true", false);
	xHttp.send();
	
	var url = "";
	if (xHttp.responseText.trim().length > 0) {
		url = xHttp.responseText.trim();
	}
	
	window.location="collage.html?topic=" + topic + "&collageUrl=" + url;
}