<html>
    <head>
            <title>IVCargo Support</title>
            <link rel="stylesheet" href="/css/Styles.css">
            <link href='https://fonts.googleapis.com/css?family=Tauri' rel='stylesheet'>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">  
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
            <script src="/js/ckeditor.js"></script>
            <script type="text/javascript" >
            function showLoader()
     		 {
     			 document.getElementById("shadow").style.display = "block";
      			document.getElementById("question").style.display = "block";
      		 }
            function AlertFilesize()
            {
                if(window.ActiveXObject){
                    var fso = new ActiveXObject("Scripting.FileSystemObject");
                    var filepath = document.getElementById('fileInput').value;
                    var thefile = fso.getFile(filepath);
                    var sizeinbytes = thefile.size;
                    var filename=thefile.name;
                }
                else
                {
                var fil = document.getElementById('customFile');
                var totalSize=0;
                var sizeinbytes;
                var filename,allname="";
                
                	for (var i = 0; i <= fil.files.length - 1; i++)
                	{
                		filename=fil.files[i].name;
                   		sizeinbytes = fil.files[i].size;
              			totalSize=totalSize+sizeinbytes;
                   		allname+="</br>"+filename;
                	}
                }
                if(totalSize>=0&&totalSize<=1000*1024)
                {
                $("#submit").show();
                document.getElementById("fileSize").style.backgroundColor ="green";
                }
                else if(totalSize>=1001*1024&&totalSize<=15000*1024)
                {
                $("#submit").show();
                document.getElementById("fileSize").style.backgroundColor ="khaki";
                }
                else
                {
                $("#submit").show();
                document.getElementById("fileSize").style.backgroundColor ="burlywood";
                }
                if(totalSize>=25000*1024)
                {
                $("#submit").hide();
                document.getElementById("fileSize").style.backgroundColor ="red";
                navigator.vibrate(250);
            	$.notify("Please do not upload files greater than or near by 25MB", "warn");
                }

                var fSExt = new Array('Bytes', 'KB', 'MB', 'GB');
                fSize = totalSize; i=0;while(fSize>900){fSize/=1024;i++;}
                document.getElementById('fileNames').innerHTML=allname;
                document.getElementById('fileSize').innerHTML=((Math.round(fSize*100)/100)+' '+fSExt[i]);
            }
            </script>
            <style>
            .opaqueLayer {
   position: fixed;
   top: 0;
   right: 0;
   bottom: 0;
   left: 0;
   height: 100%;
   width: 100%;
   margin: 0;
   padding: 0;
   background: #000000;
   opacity: .8;
   filter: alpha(opacity=80);
   -moz-opacity: .8;
   display: none;
   z-Index: 99998;
}
.questionLayer {
   position: fixed;
   top: 50%;
   left: 40%;
   width: 250px;
   height: 20px;
   z-Index: 1001;
   color: #ffffff;
   text-align: center;
   vertical-align: middle;
   font-family: Verdana;
   font-size: 20pt;
   font-weight: bold;
   display: none;
   z-Index: 99999;
   white-space: nowrap;
}
            #fullAttchment
            {
            padding: 20px; 
  transform: translate(0%,0%);
padding: 10px;
  box-sizing:border-box;
	box-shadow:0 15px 25px rgb(120, 122, 122);
  border-radius:20px;
  background-color:  rgba(181, 192, 196, 0.404);
            }
            #fileSize
            {
            background-color:  rgba(79, 77, 148, 0.466);
            }
            </style>
    </head>
    <body>
        <div id="mainForm">
        <form action="createTicket" method="post" enctype="multipart/form-data" onsubmit="showLoader()">
            <center>
                <table  id="mainTable">
                        <tr>
                            <td>
                                    <div id="queryTableDiv"  align="center">
                                    <table id="queryTable">
                                            <tr>
                                                <td><div id="subjectText">Subject:</div></td>
                                                <td><input type="text" placeholder="Subject Of Query" id="subject" name="subject" required/></td>
                                            </tr>
                                            <tr>
                                                    <td><div id="descriptionText">Description:</div></td>
                                                    <td>
                                                        <textarea name="content" id="editor" class="editor" placeholder="Description about Query" required></textarea>
                                                    </td>
                                            </tr>
                                            <tr>
                                                <td><div id="priorityText">Priority:</div></td>
                                                <td>
                                                    <select name="priority" id="priorirty" class="custom-select mb-3">
                                                        <option value="Low">Low</option>
                                                        <option value="Normal">Normal</option>
                                                        <option value="High">High</option>
                                                        <option value="Immediate">Immediate</option>
                                                    </select>
                                                </td>
                                            </tr>
                                        </table>
                                        </div>
                            </td>
                            <td>
                                <div id="customerDetailsDiv">
                                    <p id="customerDetails" align="center"><b>Customer Details</b></p>
                                </div>
                                <div id="customerTableDiv">
                                <table id="customerTable">
                                <tr>
                                <td>
                                <input type="hidden" name="accountGroupId" id="accountGroupId" value="<%=request.getParameter("accountGroupId") %>"/>
                                <input type="hidden" name="executiveId" id="executiveId" value="<%=request.getParameter("executiveId") %>"/>
                                </td>
                                </tr>
                                    <tr>
                                        <td><div id="nameText">Name:</div></td>
                                        <td><input type="text" readonly="readonly" placeholder="Your Name" id="name" value="<%=request.getParameter("executiveName") %>" name="p_name" required/></td>
                                    </tr>
                                    <tr>
                                        <td><div id="emailText">Email:</div></td>
                                        <td><input type="email" value="<%=(request.getParameter("emailAddr") != null || !request.getParameter("emailAddr").isEmpty()) ? request.getParameter("emailAddr") : "" %>" placeholder="example@ivgroup.in" id="email" name="email" required /></td>
                                    </tr>
                                    <tr>
                                        <td><div id="branchText">Branch:</div></td>
                                        <td><input readonly="readonly" type="text" placeholder="Mumbai" id="branch" name="branch" value="<%=request.getParameter("branchName") %>" required /></td>
                                    </tr>
                                    <tr>
                                        <td><div id="contactText">Contact Number:</div></td>
                                        <td><input type="tel" placeholder="9800000000" id="contact" name="con_no" maxlength="10" required /></td>
                                    </tr>
                                    <tr>
                                        <td><div id="attachmentText">Attachment:</div></td>
                                        <td>  
                                        <div id="attachments">
                                                <input type="file"  id="customFile" name="uploadingFiles" onchange="AlertFilesize();"  multiple>
                                                <p id="fileSize"></p>
                                                <p id="fileNames"></p>
                                         </div>
                                        </td>
                                    </tr>
                                </table>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <center><input type="submit" value="Submit" class="btn btn-success" id="submit")></center>
                            </td>
                        </tr>
                    </table>
            </center>
            <div id="shadow" style="display: none;" class="opaqueLayer"></div>
<div id="question" style="display: none;">
<span class="questionLayer" style="display: block;">Please wait...</span>
</div>
         </form>
         </div>
    </body>
    <script src="/js/notify.js"></script>
    <script src="/js/FirstLoad.js"></script>
    <script src="/js/main.js"></script>
    <script src="/js/Validate.js"></script>
    <script src="/js/SubmitIt.js"></script>
</html>
