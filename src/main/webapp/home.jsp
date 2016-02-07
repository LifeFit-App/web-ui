<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LifeFit</title>
<style>
	table {
	    border-collapse: collapse;
	}				
	table, td, th {
	    border: 2px solid brown;
	    padding:3px;				   				   
	}				
</style>
</head>
<body bgcolor="#FDF5E6">
<h1>.: LifeFit :.</h1>
<h3>Your personal health assistant</h3>
<p><b><font color="brown">Welcome <c:out value="${person.firstname}"/>!</font></b></p>
<form name="fhome" action="Logout" method="post">
	<p><a href="Measure">Add Measure</a> | <a href="Goal">Set Goal</a>
	<p>Below is your today's health profile.</p>
	<table>
		<c:forEach var="lifeStatus" items="${personLifeStatus}" varStatus="counter">
			<tr>
				<td><c:out value="${lifeStatus.measure.measureName}"/></td>
				<td><b><font size="3"><fmt:formatNumber type="number" value="${lifeStatus.value}" maxFractionDigits="0"/></font></b></td>
			</tr>
		</c:forEach>		
	</table>
	<p><b><font color="brown">Your daily goal: <fmt:formatNumber type="number" value="${goal.goalTarget}" maxFractionDigits="0"/> <c:out value="${goal.measure.measureName}"/></font></b></p>
	<p><i><b><c:out value="${goalStatus}"/></b></i></p>
	<p><input type="submit" value="Logout"/></p>
</form>
</body>
</html>