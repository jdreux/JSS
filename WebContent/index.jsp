<%@ taglib uri="/WEB-INF/jss/jslib.tld" prefix="sj" %>
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
		<sj:script runat="protected">
			function getSystemInfo(){
				var obj = {};
				//Access properties of the java.lang.System and java.lang.Runtime objects.
				obj.osName = System.getProperty("os.name");
				obj.availableCores = Runtime.getRuntime().availableProcessors();
				obj.freeMemory = Runtime.getRuntime().freeMemory();
				obj.maxMemory = Runtime.getRuntime().maxMemory();
				obj.totalMemory = Runtime.getRuntime().totalMemory();
				obj.currentTime = java.util.Calendar.getInstance().getTime().toString();
				System.out.println("Returning "+JSON.stringify(obj));
				return obj;
			}
		</sj:script>
		
		<sj:script runat="server">
			//var systemInfo = getSystemInfo();
			
			function getEvent(){
				return {type: 'systemInfoUpdate', data: getSystemInfo()};
			}

			function callback(){
				var event = getEvent();
				JSS.events.fireEvent(event);
			}

			if(this.interval!==undefined){
				clearInterval(this.interval);
			}

			for(var i = 0 ; i < 20; i ++)
				setTimeout(callback, i*1000);
			//this.interval = setTimeout(callback, 1000);
			
			

		</sj:script>
		
		<script>
		$(document).ready(function(){
			var systemInfo = getSystemInfo();
			
			function displaySystemInfo(data){
				$("#osName").html(data.osName);
				$("#availableCores").html(data.availableCores);
				$("#totalMemory").html(data.totalMemory);
				$("#freeMemory").html(data.freeMemory);
				$("#currentTime").html(data.currentTime);
			}
			
			displaySystemInfo(systemInfo);
			
			JSS.events.bind('systemInfoUpdate', function(event){displaySystemInfo(event.data);});
		});
		</script>
		
		<p>
			<b>System Information: </b> running <span id="osName"><sj:value expr="systemInfo.osName"/></span>,
			 <span id="availableCores"><sj:value expr="systemInfo.availableCores"/></span> cores, 
			 <span id="totalMemory"><sj:value expr="systemInfo.totalMemory"/></span>
			total memory ( 
			<span id="freeMemory"><sj:value expr="systemInfo.freeMemory"/></span> free). The current time is 
			<span id="currentTime"><sj:value expr="systemInfo.currentTime"/></span>.
		</p>
		
		<br/>
		<p style="font-size:small; color:#999999;">© 2010 JSS</p>
	</div>
</body>
</html>
