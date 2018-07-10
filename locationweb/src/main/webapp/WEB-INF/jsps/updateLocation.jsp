<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Locations</title>
</head>
<body>

<form action="updateLoc" method="post">
<pre>
Id: <input type="text" name="id" value="${location.id}" readonly/>
Code: <input type="text" name="code" value="${location.code}"/>
Name: <input type="text" name="name" value="${location.name}"/>
Type: Urban <input type="radio" name="type" value="URBAN" ${location.type=='URBAN'?'checked':''}/>
	Rural <input type="radio" name="type" value="RURAL" ${location.type=='RURAL'?'checked':''}/>
<input type="submit" value="update"/>
</pre>
</form>
</body>
</html>