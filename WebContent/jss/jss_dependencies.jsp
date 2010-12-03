<%@ taglib uri="/WEB-INF/tld/jslib.tld" prefix="sj" %>
<% String contextPath = request.getContextPath(); %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
<script src="<%=contextPath%>/jss/jss-client.js"></script>
<script src="<%=contextPath%>/jss/json2.js"></script>
<sj:script src="/WEB-INF/server-scripts/application.js" runat="server"></sj:script>
<script>
	JSS.contextName = '<%=contextPath%>'+'/';
</script>