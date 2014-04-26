<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
	<title>Send Message</title>
</head>
<body>
<h1>send message</h1>
<form:form action="${pageContext.request.contextPath}/sendMessage" method="post">

  <input type="submit" value="Send Message"/>
</form:form>
<form:form action="${pageContext.request.contextPath}/applicationEventsQueue" method="get">

  <input type="submit" value="Number of Messages"/>
</form:form>
<form:form action="${pageContext.request.contextPath}/cleanRedis" method="get">

  <input type="submit" value="Clean Redis"/>
</form:form>

</body>
</html>
