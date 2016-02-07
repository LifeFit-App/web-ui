<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LifeFit</title>
</head>
<body bgcolor="#FDF5E6">
<h1>.: LifeFit :.</h1>
<h3>Your personal health assistant</h3>
<p><b>Add new measure</b></p>
<form name="fmeasure" action="Measure" method="post">
	<table>
		<tr>
			<td>Measure type</td>
			<td>:
				<select name="measureName">
					<option value="">-- Please select --</option>
					<c:forEach var="measure" items="${measureTypes}">
						<option value="${measure.measureName}">${measure.measureName}</option>
					</c:forEach>
				</select>			
			</td>
		</tr>	
		<tr>
			<td>Value</td>
			<td>: <input type="text" name="measureValue" maxlength="5" size="5"/></td>
		</tr>
		<tr>
			<td><input type="hidden" name="action" value="saveHP"/></td>
			<td>&nbsp;&nbsp;<input type="submit" name="submit" value=" Save "/>
			<input type="reset" name="reset" value="Cancel"/></td>
		</tr>	
	</table>
	<c:url value="Home" var="home"/>
	<p><a href="${home}">&lt;&lt; back</a></p>
</form>
</body>
</html>