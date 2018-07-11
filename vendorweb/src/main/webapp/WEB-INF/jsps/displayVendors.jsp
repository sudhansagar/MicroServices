<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Vendors</title>
</head>
<body>
	<h2>Vendors:</h2>
	<table>
		<tr>
			<th>id</th>
			<th>code</th>
			<th>name</th>
			<th>type</th>
			<th>email</th>
			<th>phone</th>
			<th>address</th>
		</tr>

		<c:forEach items="${vendors}" var="vendor">
			<tr>
				<td>${vendor.id}</td>
				<td>${vendor.code}</td>
				<td>${vendor.name}</td>
				<td>${vendor.type}</td>
				<td>${vendor.email}</td>
				<td>${vendor.phone}</td>
				<td><textarea rows="5" cols="15">${vendor.address}</textarea></td>
				<td><a href="deleteVendor?vendorId=${vendor.id}">Delete</a></td>
				<td><a href="showUpdate?vendorId=${vendor.id}">Edit</a></td>
			</tr>
		</c:forEach>
	</table>
	
<a href="showCreate">Add Vendor</a>
</body>
</html>