<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css"> 

table.one { margin: 1em; empty-cells:hide; background: #fc9;}
table.two {border-collapse:collapse; empty-cells:hide; background: #fc9;}

td.b {
padding: .3em; border: 1px #ccc solid; background: #9cf;
}
td.a { padding: .3em; border: 1px #ccc solid;
	 }

</style>


<title>LookUp Poll</title>
</head>
<body>
	<h1>Poll Display</h1>
	<form name="LookUpForm" action="SaveLookUpServlet" method="post">

	<table border="1" class="one">
		<c:forEach items="${pollList}" var="poll">
			<tr>
				<td class="b">Poll Topic</td><td class="a"><c:out value="${poll.shortText}" /></td><td></td>
			</tr>		
			<tr>
				<td class="b">Poll Description</td><td class="a"><c:out value="${poll.description}" /></td><td></td>
			</tr>
			<tr>
				<td class="b">Poll Details</td>
				<td>
				<table class="two">
				<c:forEach items="${poll.connectionsPolled}" var="conn">
					<tr>
						<td>Connection:</td>
						<td><c:out value="${conn.connectionId}" /></td>
					</tr>				
					<tr>
						<td>Poll Sent On:</td>
						<td><c:out value="${conn.pollSentDate}" /></td>
					</tr>
					<tr>
						<td>Poll Response:</td>					
						<td><c:out value="${conn.connectionResponse}" /></td>
					</tr>
					<tr>
						<td>Rating:</td>
						<td><input type="text" id='<c:out value="${conn.pollId}"/>' name='<c:out value="${conn.connectionId}"/>' value='<c:out value="${conn.rating}"/>'></input></td>
					</tr>
					<tr>
						<td>Response Received On:</td>					
						<td><c:out value="${conn.responseReceivedDate}" /></td>
					</tr>					
				</c:forEach>
				</table>
				</td>
				<td>
					<textarea rows="6" cols="30" readonly="readonly" id='<c:out value="${poll.id}"/>'  name='<c:out value="${poll.id}"/>'>
						<c:out value="${poll.yelpResult}" />
					</textarea>				
				</td>
			</tr>			
		</c:forEach>
		<tr>
			<td><input type="submit" value="Save" name="saveLookUp" onclick="checkSubmit()"></td>
			<td><input type="button" value="Cancel" name="cancel" onclick="returnToMainPage();"></td>
		</tr>
	</table>

	<input type="hidden" id="allUserIds" name="allUserIds" value='' ></input>
	<input type="hidden" id="allFBPollIds" name="allFBPollIds" value='' ></input>
	<input type="hidden" id="allRatings" name="allRatings" value=''></input>
	</form>
	<script type="text/javascript">
	function checkSubmit()
	{
		//if(IsNumeric() == true)
		//{
  			var x=document.getElementsByTagName("input");
			var ratingArray = new Array();
			var pollIdArray = new Array();
			var connIdArray = new Array();		
  			//alert(x.length);
  			for(var i=0; i<(x.length-5); i++)
  			{
  				ratingArray[i] = document.getElementsByTagName("input")[i].value;
  				pollIdArray[i] = document.getElementsByTagName("input")[i].id;
  				connIdArray[i] = document.getElementsByTagName("input")[i].name;  			
  			}
  			//alert(ratingArray);
  			//alert(pollIdArray);
  			//alert(connIdArray);
  			document.getElementById("allFBPollIds").value = pollIdArray;
  			document.getElementById("allUserIds").value = connIdArray;
  			document.getElementById("allRatings").value = ratingArray;
  			document.LookUpForm.submit();
  		//}	
	}
	
	function returnToMainPage()
	{
		document.location.href="http://www.facebook.com/";
	}
	function IsNumeric()
	//  check for valid numeric strings	
	{
  		var x = document.getElementsByTagName("input");
  		var len = (x.length)-5;
		var strValidChars = "012345";
		var strChar;
		var blnResult = true;  			
  		for(var i=0; i<len; i++)
  		{
			var strString = document.getElementsByTagName("input")[i].value;
			if (strString.length == 0)
			{
				alert("Please enter rating.");
				blnResult = false;
				break;			
			}
			else if(strString.length != 1)
			{
				alert("Please enter a single digit rating value between 0 to 5.");
				blnResult = false;
				break;
			}
			else
			{
				//  test strString consists of valid characters listed above
				for (var i = 0; i < strString.length && blnResult == true; i++)
				{
					strChar = strString.charAt(i);
					if (strValidChars.indexOf(strChar) == -1)
					{
						blnResult = false;
					}
				}
				if(blnResult == false)
				{
					alert("Please enter rating value between 0 to 5.");
					break;
				}
			}					
  		}
  		return blnResult;
	}
	</script>
</body>
</html>