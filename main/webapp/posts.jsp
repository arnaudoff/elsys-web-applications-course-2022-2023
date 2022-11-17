<%--
  Created by IntelliJ IDEA.
  User: HP2Laz
  Date: 17.11.2022
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="uploadPost" method="get">
        Add Photo:<input type="file" id="myFile" name="photoUser">
        <label for="textUser" >Comment:</label>
        <textarea id = "textUser" name="textUser"></textarea>
        <input type="submit">
    </form>

</body>
</html>
