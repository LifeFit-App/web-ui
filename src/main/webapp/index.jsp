<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LifeFit</title>
</head>
<body bgcolor="#FDF5E6">
<h1>.: LifeFit :.</h1>
<h3>Your personal health assistant</h3>

<form name="flogin" method="post" action="Login">
	<table border="0">
		<tr>
			<td>Email</td>		
			<td>: <input type="text" name="email" size="30" maxlength="50" autofocus/></td>			
		</tr>
		<tr>
			<td>Password</td>			
			<td>: <input type="password" name="password" size="30" maxlength="15"/></td>			
		</tr>
		<tr>
			<td></td>
			<td>&nbsp;&nbsp;<input type="submit" name="submit" value="Login"/>
			<input type="reset" name="reset" value="Clear"/></td>
		</tr>	
	</table>
</form>
</body>
</html>