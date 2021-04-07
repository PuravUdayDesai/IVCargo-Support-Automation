/*
This script loads when the document(html file) gets loaded
on the browser, it hides the mainForm div in IVCargoSupport.html
It is the Initial load.
*/
$(document).ready(function(){
  $("#customerDetailsDiv").hide();
      $("#customerTable").hide();//It hides Customer Table/Fields
      $("#submit").hide();//It Hides Submit Button
    })