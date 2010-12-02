<%@ taglib uri="/WEB-INF/tld/jslib.tld" prefix="sj" %>
<% String contextPath = request.getContextPath(); %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
<script src="<%=contextPath%>/JSS/JSS.js"></script>
<script src="<%=contextPath%>/JSS/json2.js"></script>
<sj:script src="/WEB-INF/server-scripts/application.js" runat="server"></sj:script>
<script>
	$(document).ready(function(){
		JSS.contextName = '<%=contextPath%>';
	})
</script>