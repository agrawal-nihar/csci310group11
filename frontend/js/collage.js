
/* Main function that controls image display */
/* Basically uses image attributes to set the dynamic image title and src */

window.onload = function initialCollage(){
  var url_string = window.location.href;
  var url = new URL(url_string);
  var title = url.searchParams.get("title");
  var topicHeader = document.getElementById("topic");
  topicHeader.innerHTML = "Collage for Topic " + title;
}

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

function buildAnotherCollage(){
  var title = document.getElementById("text_input").value;
  var topicHeader = document.getElementById("topic");
  topicHeader.innerHTML = "Collage for Topic " + title;
  //need to change mainCollage
//  var mainCollage = document.getElementById("main_collage");
//  mainCollage.style.background = "url('" + src + "')";
//  mainCollage.style.backgroundRepeat = "no-repeat";
//  mainCollage.style.backgroundSize = "cover";
}
