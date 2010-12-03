<%@ taglib uri="/WEB-INF/tld/jslib.tld" prefix="sj" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/jss/jss_dependencies.jsp"/>
<title>JSS Startup page</title>
<style type="">
	body {
		font-type:sans-serif
	}
</style>
</head>

<body>
	<div style="text-align:center">
		<h1>Welcome to JSS</h1>
		<p>
			If you are reading this page then your installation of JSS was successful.
			You can go on to read the <a href="#">JSS quick start guide</a> or <a href="#">JSS documentation</a>.
		</p>
		
		<!-- Show off some capabilities of JSS -->
		<sj:script runat="server">
			
			function getSystemInfo(){
				var obj = {};
				//Access properties of the java.lang.System and java.lang.Runtime objects.
				obj.osName = System.getProperty("os.name");
				obj.availableCores = Runtime.getRuntime().availableProcessors();
				obj.freeMemory = Runtime.getRuntime().freeMemory();
				obj.maxMemory = Runtime.getRuntime().maxMemory();
				obj.totalMemory = Runtime.getRuntime().totalMemory();
				obj.currentTime = java.util.Calendar.getInstance().getTime().toString();
				return obj;
			}
			var systemInfo = getSystemInfo();
			
			function callback(){
				var event = {type: 'systemInfoUpdate', data: getSystemInfo};
				JSS.events.fireEvent(event);
			}

			if(this.interval!==undefined){
				clearInterval(this.interval);
			}
	
			this.interval = setInterval(callback, 1000);
			
		</sj:script>
		
		<p>
			<b>System Information: </b> running <sj:value expr="systemInfo.osName"/>, <sj:value expr="systemInfo.availableCores"/> cores, <sj:value expr="systemInfo.totalMemory"/>
			total memory ( <sj:value expr="systemInfo.freeMemory"/> free). The current time is <sj:value expr="systemInfo.currentTime"/>.
		</p>
		
		<br/>
		<p style="font-size:small; color:#999999;">© 2010 JSS</p>
	</div>
</body>
</html>
