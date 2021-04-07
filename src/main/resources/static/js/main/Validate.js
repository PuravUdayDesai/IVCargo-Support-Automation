/*
This javaScript file is used to validate name 
and contact field in Customer Utilities
*/
var contactLength=0;
$(document).ready(function(){
    //for name field
    $("#name").keyup(function(){
        if(event.keyCode==48||event.keyCode==49||event.keyCode==50||event.keyCode==51||event.keyCode==52||event.keyCode==53||event.keyCode==54||event.keyCode==55||event.keyCode==56||event.keyCode==57)
        {
            $.notify("Please do not enter digits in Name", "warn");
            var name=document.getElementById("name").value;
            var finalName="";
            for(var i=0;i<name.length-1;i++)
            {
                finalName+=name.charAt(i);
            }
            document.getElementById("name").value=finalName;
        }
    });
    //for contact field
    $("#contact").keyup(function(){
        if(event.keyCode==8&&contactLength!=0)//backspace
        {
            --contactLength;
        }
        else
        {
            //verifies that the character entered is between 0 to 9 
        if(event.keyCode==48||event.keyCode==49||event.keyCode==50||event.keyCode==51||event.keyCode==52||event.keyCode==53||event.keyCode==54||event.keyCode==55||event.keyCode==56||event.keyCode==57)
        {
            if(contactLength==10)
            {
                contactLength=10;
            }
            else
            {
            ++contactLength;
            }
            //check for initials
            if(contactLength==1)
            {
                //(19.06.2019) Initials of a Mobile/Cellular phone can only start from 7, 8 or 9
                if(event.keyCode==55||event.keyCode==56||event.keyCode==57)
                {

                }
                else
                {
                    $.notify("Please do not enter Illegal Initials In Contact", "warn");
                    //deletes currently entered character(initial)
                    var contact=document.getElementById("contact").value;
                    var finalContact="";
                    for(var i=0;i<contact.length-1;i++)
                    {
                        finalContact+=contact.charAt(i);
                    }
                    document.getElementById("contact").value=finalContact;
                    --contactLength;
                }
                
            }
        }
        else
        {
            $.notify("Please do not enter letters in Contact", "warn");
            //deletes currently entered character(current(this))
            var contact=document.getElementById("contact").value;
            var finalContact="";
            for(var i=0;i<contact.length-1;i++)
            {
                finalContact+=contact.charAt(i);
            }
            document.getElementById("contact").value=finalContact;
        }
    }
    });
  })