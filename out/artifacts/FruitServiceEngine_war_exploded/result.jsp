<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Get the 'result' attribute that was set by the FruitServlet
    String result = (String) request.getAttribute("result");

    // Only show the result box if there is a non-empty result message
    if (result != null && !result.trim().isEmpty()) {
%>
<!-- Output the result inside a styled box -->
<div class="result-box">
    <strong>Result:</strong>
    <pre><%= result %></pre>  <!-- Display result with formatting preserved -->
</div>
<%
    }
%>
