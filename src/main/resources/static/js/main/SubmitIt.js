/*
This javaScript file is used for displaying 
of "SUBMIT" button and final validation
*/
var allTrue=false;
var checkName=false;
var checkContact=false;
var checkEmail=false;
var checkBranch=false;
$(document).ready(function(){
    //customer utilities
    $("#branch,#email,#name,#contact").keyup(function(){
        //verifies the miminum amount of length for validation
        if(document.getElementById("name").value.length>=1 && document.getElementById("contact").value.length==10 && document.getElementById("email").value.length>=3 && document.getElementById("branch").value.length>=1)
        {
                if(finalValidate())//function that checks all the credentials again
                {
                 $("#submit").show();//displays button
                }
                else
                {
                    $("#submit").hide();//hides button
                }
        }
        else
        {
            $("#submit").hide();//hides button
        }
        });
    $("#submit").mouseover(function()//reverifies correct inputs
    {
        var checkFinal=false;
        checkName=finalValidateName();
        checkContact=finalValidateContact();
        checkEmail=finalValidateEmail();
        checkBranch=finalValidateBranch();
        if(checkName&&checkContact&&checkEmail&&checkBranch)
        {
         checkFinal=true;
        }
        if(!checkFinal)
        {
            if(!checkName)
            {
                $.notify("Name entered was Incorrect Please re enter again", "error");//Incorrect Name
            }
            if(!checkEmail)
            {
                $.notify("Email entered was Incorrect Please re enter again", "error");//Incorrect Email
            }
            if(!checkContact)
            {
                $.notify("Contact entered was Incorrect Please re enter again", "error");;//Incorrect Contact
            }
            if(!checkBranch)
            {
                $.notify("Branch entered was Incorrect Please re enter again", "error");//Incorrect Branch
            }

        }
    });

  })
  function finalValidate()//Validates name, contact, email, branch fields
  {
checkName=finalValidateName();//validates name
checkContact=finalValidateContact();//validates contact
checkEmail=finalValidateEmail();//validate email
checkBranch=finalValidateBranch();//validates branch
if(checkName&&checkContact&&checkEmail&&checkBranch)
{
    return true;//correct inputs
}
return false;//incorrect inputs
  }


  function finalValidateName()//verifies Name field
  {
      var name=document.getElementById("name").value;
      for(var i=0;i<name.length;i++)
      {
          //checks that is there any number in the name
          if(name.charAt(i)=='0'||name.charAt(i)=='1'||name.charAt(i)=='2'||name.charAt(i)=='3'||name.charAt(i)=='4'||name.charAt(i)=='5'||name.charAt(i)=='6'||name.charAt(i)=='7'||name.charAt(i)=='8'||name.charAt(i)=='9')
          {
              $.notify("Please do not enter digits in Name", "warn");
            return false;//number detected
          }
      }
      return true;//name field correct
  }
  function finalValidateContact()//verifies Contact field
  {
      var contact=document.getElementById("contact").value;
      for(var i=0;i<10;i++)
      {
          if(i==0)
          {
              //verifies initials on contact field
              //(19.06.2019) only 7, 8 or 9 can be accepted as initials
              if(contact.charAt(i)=='7'||contact.charAt(i)=='8'||contact.charAt(i)=='9')
              {
              }
              else
              {
                return false;//initial incorrect
              }
          }
          else
          {
              //verifies for any number
              if(contact.charAt(i)=='0'||contact.charAt(i)=='1'||contact.charAt(i)=='2'||contact.charAt(i)=='3'||contact.charAt(i)=='4'||contact.charAt(i)=='5'||contact.charAt(i)=='6'||contact.charAt(i)=='7'||contact.charAt(i)=='8'||contact.charAt(i)=='9')
              {
              }
              else
              {
                  return false;//any character in between incorrect
              }
          }
      }
      return true;
  }
  function finalValidateEmail()//validates email field
  {
      var atTheRate=false;
      var dot=false;
      var email=document.getElementById("email").value;
      for(var i=0;i<email.length;i++)
      {
          if(email.charAt(i)=='@')//checks for @ in email field
          {
              atTheRate=true;//@ is present
          }
          if(email.charAt(i)=='.')//checks for . in email fiels
          {
              dot=true;//. is present
          }
      }
      if(atTheRate&&dot)//checks if both(@ and .) are present
      {
          return true;//present
      }
      return false;//not present
  }
  function finalValidateBranch()//validates for branch field
  {
      var branch=document.getElementById("branch").value;
      if(branch.length>=1)//branch name's length is greater than 1
      {
          return true;//length is greater than or equal than 1
      }
      return false;//length is smaller than 1
  }