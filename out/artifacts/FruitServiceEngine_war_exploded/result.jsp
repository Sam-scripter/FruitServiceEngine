<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String result = (String) request.getAttribute("result");
    if (result != null && !result.trim().isEmpty()) {
%>
<div class="result-box">
    <strong>Result:</strong>
    <pre><%= result %></pre>
</div>
<%
    }
%>
