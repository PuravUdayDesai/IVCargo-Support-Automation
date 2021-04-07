/*
This is the Main javaScrpit file in which
we have Richtext embedded with query utilities
*/
//start of RichTextEditor
var data="";
CKEDITOR.replace( 'editor' , {
    uiColor: '#8a7eff' 
  }
  );
  CKEDITOR.instances.editor.on('change', function() { 
    data=CKEDITOR.instances.editor.getData();
    if(data.length>=1&&document.getElementById("subject").value.length>=1)
    {
      var subject=document.getElementById("subject").value;
          $("#customerDetailsDiv").show(1000);
          $("#customerTable").show(1000);
     document.getElementById("subject").value=subject;
    }
    else
    {
    $("#customerDetailsDiv").hide(1000);
     $("#customerTable").hide(1000);
     $("#submit").hide();
    }
	
});

	 
    //end of RichTextEditor
      //----------------------------------------------
//start of Query Utilities

    $(document).ready(function(){
        $("#subject").keyup(function(){
         if(data.length>=1&&document.getElementById("subject").value.length>=1)
         {
        var subject=document.getElementById("subject").value;
        $("#customerDetailsDiv").show(1000);
        $("#customerTable").show(1000);
        document.getElementById("subject").value=subject;
         }
         else
         {
            $("#customerDetailsDiv").hide(1000);
            $("#customerTable").hide(1000);
            $("#submit").hide();
         }
        });
      });
      //end of Query Utilities
      //----------------------------------------------
      //start of Submit Stories
      $("#submit").mouseover(function()
    {
        console.log("Here");
        document.getElementById("editor").value=data;
        console.log(document.getElementById("editor").value);
    });
      //end of Submit Stories