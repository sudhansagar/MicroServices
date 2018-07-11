<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Vendor</title>
</head>
<body>

<form action="saveVendor" method="post">
<pre>
Id: <input type="text" name="id"/>
Code: <input type="text" name="code"/>
Name: <input type="text" name="name"/>
Type: <select name="type">
<option>Regular</option>
<option>Contract</option>

</select>
Email: <input type="text" name="email"/>
Phone: <input type="text" name="phone"/>
Address: <textarea rows="5" cols="15" name="address"></textarea>
<input type="submit" value="save"/>
</pre>
</form>
${msg}

<a href="displayVendors">View vendors</a>
</body>
</html>