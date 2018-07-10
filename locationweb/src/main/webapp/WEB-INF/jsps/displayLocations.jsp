<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Locations</title>
</head>
<body>
	<h2>Locations:</h2>
	<table>
		<tr>
			<th>id</th>
			<th>code</th>
			<th>name</th>
			<th>type</th>
		</tr>

		<c:forEach items="${locations}" var="location">
			<tr>
				<td>${location.id}</td>
				<td>${location.code}</td>
				<td>${location.name}</td>
				<td>${location.type}</td>
				<td><a href="deleteLoc?locationId=${location.id}">Delete</a></td>
				<td><a href="showUpdate?locationId=${location.id}">Edit</a></td>
			</tr>
		</c:forEach>
	</table>
	
<a href="showCreate">Add Location</a>
</body>
</html>