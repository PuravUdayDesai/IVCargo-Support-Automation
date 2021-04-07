/*
This javaScript file is used to validate name 
and contact field in Customer Utilities
*/
var contactLength=0;
$(document).ready(function(){
    
  $("#subject").keypress(function(){
      var key = event.keyCode;  
      var numberEnterd=false;
	  if(key!=8)
		  {
      var stringKey = String.fromCharCode(key); 
        if(key>=48&&key<=57||key>=65&&key<=90||key>=97&&key<=122||key==32||key==16||key==20||key==33)
        {
            var subject=document.getElementById("subject").value;
            var newElement="";
            for(var i=0;i<subject.length;i++)
            {
                if(subject.charAt(i)>='a'&&subject.charAt(i)<='z'||subject.charAt(i)>='A'&&subject.charAt(i)<='Z'||subject.charAt(i)>='0'&&subject.charAt(i)<='9'||subject.charAt(i)=='!'||subject.charAt(i)==' ')
                {
                    newElement+=subject.charAt(i);
                }
            }
            document.getElementById("subject").value=newElement;
            notCorrect=false;
        }
	    else
	    {
            notCorrect=true;
            var subject=document.getElementById("subject").value;
            var newElement="";
            for(var i=0;i<subject.length;i++)
            {
                if(subject.charAt(i)>='a'&&subject.charAt(i)<='z'||subject.charAt(i)>='A'&&subject.charAt(i)<='Z'||subject.charAt(i)>='0'&&subject.charAt(i)<='9'||subject.charAt(i)=='!'||subject.charAt(i)==' ')
                {
                    newElement+=subject.charAt(i);
                }
            }
            document.getElementById("subject").value=newElement;
        }
		  }
});