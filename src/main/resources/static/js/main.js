/*
This is the Main javaScrpit file in which
we have Richtext embedded with query utilities
*/
//start of RichTextEditor
var data="";
var selected=-1;
var mails=["mail.com","ahoo.com","vgroup.in","ediffmail.com"];
var firstMailEntry=false;
CKEDITOR.replace( 'editor' , {
    uiColor: '#8a7eff' 
  }
  );
  CKEDITOR.instances.editor.on('change', function() { 
    data=CKEDITOR.instances.editor.getData();
    if(data.length>=1&&document.getElementById("subject").value.length>=1)
    {
      var subject=document.getElementById("subject").value;
          $("#customerDetailsDiv").show(100);
          $("#customerTable").show(1000);
     document.getElementById("subject").value=subject;
    }
    else
    {
    $("#customerDetailsDiv").hide(100);
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
        $("#customerDetailsDiv").show(100);
        $("#customerTable").show(1000);
        document.getElementById("subject").value=subject;
         }
         else
         {
            $("#customerDetailsDiv").hide(100);
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

        if(selected==-1)
        {
          var mailContents=document.getElementById("email").value;
          var atPresent=false;
          var dotPresent=false;
          var anonymousExtension="";
          for(var i=0;i<mailContents.length;i++)
          {
            if(atPresent)
            {
              if(mailContents.charAt(i)=='.')
              {
                break;
              }
              anonymousExtension+=mailContents.charAt(i);
            }
            if(mailContents.charAt(i)=='@')
            {
              atPresent=true;
            }

          }
          if(anonymousExtension=="gmail")
          {
            selected=0;
          }
          else if(anonymousExtension=="yahoo")
          {
            selected=1;
          }
          else if(anonymousExtension=="ivgroup")
          {
            selected=2;
          }
          else if(anonymousExtension=="rediffmail")
          {
            selected=3;
          }
        }







        document.getElementById("editor").value=data;
        var newMail="";
        var mailContent=document.getElementById("email").value;
        var extensionMail="";
        var atHere=false;
        var presentMail="";
        for(var i=0;i<mailContent.length;i++)
        {
          if(mailContent.charAt(i)=='@')
          {
            atHere=true;
          }
          if(atHere)
          {
            if(mailContent.charAt(i)=='.')
            {
              break;
            }
            extensionMail+=mailContent.charAt(i);
          }
        }
        console.log("MailContent: "+mailContent);
        console.log("ExtensionMail: "+extensionMail);
        var fullCorrect=true;
        if(selected==0)
        {
          var presentMail="@g"+mails[selected];
        }
        else if(selected==1)
        {
          var presentMail="@y"+mails[selected];
        }
        else if(selected==2)
        {
          var presentMail="@i"+mails[selected];
        }
        else if(selected==3)
        {
          var presentMail="@r"+mails[selected];
        }
        console.log("Present Mail: "+presentMail);
        for(var i=0;i<extensionMail.length;i++)
        {
          if(presentMail.charAt(i)!=extensionMail.charAt(i))
          {
            fullCorrect=false;
            break;
          }
          if(extensionMail.charAt(i)=='.')
          {
              break;
          }
        }
        console.log("FullCorrect: "+fullCorrect);        
        if(fullCorrect)
        {
        for(var i=0;i<mailContent.length;i++)
        {
          newMail+=mailContent.charAt(i);
          if(selected!=-1)
          {
            if(mailContent.charAt(i)=='@')
            {
              if(selected==0)
              {
                newMail+=("g"+mails[selected]);
              }
              else if(selected==1)
              {
                newMail+=("y"+mails[selected]);
              }
              else if(selected==2)
              {
                newMail+=("i"+mails[selected]);
              }
              else if(selected==3)
              {
                newMail+=("r"+mails[selected]);
              }
              break;
            }
          }
        }
        document.getElementById("email").value=newMail;
      }
      else
      {
        var newStart=0;
        var canExtend=false;
        for(var i=0;i<mailContent.length;i++)
        {
          newMail+=mailContent.charAt(i);
          if(!canExtend)
          {
          if(mailContent.charAt(i+1)=='@')
          {
            newStart=i+1;
            newMail+=extensionMail;
            newMail+='.';
            i+=(extensionMail.length+1);
            canExtend=true;
          }
          }
        }
        console.log("New Mail: "+newMail);
        document.getElementById("email").value=newMail;
      }


       

    });
      //end of Submit Stories
      //-----------------------------------------------

      $("#email").keyup(function()
      {
        var inAt=false;
        var getIn=false;
        var hurrayDotPresent=false;
        var contentsAfterAt="";
        var mailContent=document.getElementById("email").value;
        if(mailContent.charAt(0)!='@')
        {
        for(var i=0;i<mailContent.length;i++)
        {
        if(getIn)
        {
          contentsAfterAt+=mailContent.charAt(i);
        }
          if(!inAt)
          {
          if(mailContent.charAt(i)=='@')
          {
            inAt=true;
            getIn=true;
          }
        }
          if(inAt)
          {
          if(mailContent.charAt(i)=='.')
          {
            hurrayDotPresent=true;
            break;
          }

        }

      }
    }
    else
    {
      $.notify("Please do not @ at starting", "warn");
    }
    if(firstMailEntry==true)
    {
      if(contentsAfterAt=="")
      {
        hurrayDotPresent=false;
      }
      else{
        hurrayDotPresent=true;
      }
    }
      var contentsOfMail=document.getElementById("email").value;
      var atPresent=false;
      var dotPresent=false;
      for(var i=0;i<contentsOfMail.length;i++)
      {
        if(contentsOfMail.charAt(i)=='@')
        {
          atPresent=true;
        }
        if(atPresent)
        {
        if(contentsOfMail.charAt(i)=='.')
        {
          dotPresent=true;
          break;
        }
        }
      }
      if(!hurrayDotPresent)
      {
        selected=-1;
      }
        if(getIn&&selected==-1&&!dotPresent)
        {
        if(event.keyCode==71)
        {
          selected=0;
          var mail=document.getElementById("email").value;
          document.getElementById("email").value=mail+mails[0];
          firstMailEntry=true;
        }
        else if(event.keyCode==89)
        {
          selected=1;
          var mail=document.getElementById("email").value;
          document.getElementById("email").value=mail+mails[1];
          firstMailEntry=true;
        }
        else if(event.keyCode==73)
        {
          selected=2;
          var mail=document.getElementById("email").value;
          document.getElementById("email").value=mail+mails[2];
          firstMailEntry=true;
        }
        else if(event.keyCode==82)
        {
          selected=3;
          var mail=document.getElementById("email").value;
          document.getElementById("email").value=mail+mails[3];
          firstMailEntry=true;
        }
      }
      });
