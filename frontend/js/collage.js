
/* Main function that controls image display */
/* Basically uses image attributes to set the dynamic image title and src */
function toggleCollage(e){
  var topicHeader = document.getElementById("topic");
  var mainCollage = document.getElementById("main_collage");
  topicHeader.innerHTML = "Collage for Topic " + e.target.alt;
  mainCollage.style.background = "url('" + e.target.src + "')";
  mainCollage.style.backgroundRepeat = "no-repeat";
  mainCollage.style.backgroundSize = "cover";
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
