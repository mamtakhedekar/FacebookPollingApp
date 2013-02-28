<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html xmlns="http://www.w3.org/1999/xhtml"
  xmlns:fb="https://www.facebook.com/2008/fbml">
  <head>

<style type="text/css"> 

table.two {border-collapse:collapse; empty-cells:hide; background: #fc9;}

td.b {
padding: .3em; border: 1px #ccc solid; background: #9cf;
}
td.a { padding: .3em; border: 1px #ccc solid;
	 }

</style>  
    <title>Request Example</title>
  </head>

  <body>
    <form name="CreatePollForm" action="SaveValidatedPollServlet" method="post">
    <script src="http://connect.facebook.net/en_US/all.js">
    </script>
    <div>      
    	<table border="1" align="center" class="two">
    	<tr>
    		<th colspan="2" style="padding: .3em; border: 1px #ccc solid; background: #9cf;"> Create Poll </th>
    	</tr>
    	<tr>  
			<td class="b">Poll Topic : </td>
			<td class="a"><input type="text" id="topicName" name="topicName"></input></td>
		</tr>
		<tr>
			<td class="b">Topic Description : </td>
			<td class="a"><input type="text" id="topicDesc" name="topicDesc"></input></td>
		</tr>
		<tr>
			<td>
				<input type="submit" value="Send Request to Many Users" onclick="sendRequestViaMultiFriendSelector(); return false;"></input>
			</td>
			<td>
				<input type="button" name="cancel" value="Cancel" onclick="window.location='index.html'"></input>
			</td>
		</tr>
		</table>
		
		<input type="hidden" id="polledConn" name="polledConn"></input>
		<input type="hidden" id="pollId" name="pollId"></input>
		<input type="hidden" id="userId" name="userId" value='<c:out value="${UserId}" />'></input>
		<input type="hidden" id="userName" name="userName" value='<c:out value="${UserName}" />' ></input>
		<input type="hidden" id="userLocation" name="userLocation" value='<c:out value="${UserLocation.name}" />' ></input>
	</div>
    
    <script>
      FB.init({
        appId  : '459011260803995',
        frictionlessRequests: true
      });
      
      function sendRequestViaMultiFriendSelector() {
		  if(document.getElementById("topicName").value.length == 0)
			  alert("Please Enter Topic Name before proceeding further");
		  else if(document.getElementById("topicDesc").value.length == 0)
			  alert("Please Enter Topic Description before proceeding further");
		  else {
    	  FB.ui({method: 'apprequests',
          message: document.getElementById("topicName").value
        }, requestCallback);
		}
      }
      
	function requestCallback(response) {
		var polled_user_ids = response.to;
		//alert(polled_user_ids);
		if(polled_user_ids == '')
		{
			alert("Please select connection(s) before proceeding further");
		}
		else
		{
			document.getElementById('polledConn').value = polled_user_ids;
			document.getElementById('pollId').value = response.request;
			console.log(response);
			document.CreatePollForm.submit();			
		}
	}
    </script>
    </form>
  </body>
</html>