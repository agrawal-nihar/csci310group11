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

function buildCollage(){
	var title = document.getElementById("text_input").value;
	window.location="collage.html?title=" + title;
}