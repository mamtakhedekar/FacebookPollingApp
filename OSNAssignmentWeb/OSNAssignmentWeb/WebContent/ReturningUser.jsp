<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Returning User</title>
</head>
<body>
	<h1>Poll - Please Reply!</h1>
	<form name="savePollForm" action="SaveResponsePollServlet" method="post">
		<table border="1">
			<tr>
				<td>Poll Topic</td>
				<td><c:out value="${PollText}" /></td>
			</tr>
			<tr>
				<td>Poll Description</td>
				<td><c:out value="${PollDescription}" /></td>
			</tr>
			<tr>
				<td>You responded on</td>
				<td><c:out value="${ConnectionResponseDate}" /></td>
			</tr>
			<tr>			
				<td>Poll Created On</td>
				<td><c:out value="${ConnectionPollSentDate}" /></td>
			</tr>
			<tr>
				<td>Please Enter Your Reply</td>
				<td><input type="text" name="userReply" id="userReply" value='<c:out value="${ConnectionResponse}" />'></td>
			</tr>
			<tr>
				<td><input type="submit" value="Send Reply" name="submit" onclick="checkSubmit();"></td>
				<td><input type="button" value="Cancel" name="cancel" onclick="returnToMainPage();"></td>
			</tr>
		</table>
		<input type="hidden" type="text" id="userId" name="userId" value='<c:out value="${UserId}" />'></input>
		<input type="hidden" type="text" id="pollId" name="pollId" value='<c:out value="${pollId}" />'></input>
		<input type="hidden" type="text" id="connPollId" name="connPollId" value='<c:out value="${connPollId}" />'></input>
	</form>
	<script type="text/javascript">
		function returnToMainPage()
		{
			//alert("return");
			document.location.href = "https://www.facebook.com";
		}
		
		function checkSubmit()
		{
			if(document.getElementById("userReply") == "")
			{
				alert("Please Enter Your Reply");
			}
			else
			{
				document.savePollForm.submit();
			}
		}
	</script>
</body>
</html>