<html>
<head>
  <title>JSS Application Console</title>
  <jsp:include page="/jss/jss_dependencies.jsp"></jsp:include>
  <style>
    .prompt{
		color: #666666;
		font-family: sans-serif;
	}
	
	.code{
		font-family: monospace;
	}
	
	.response{
		color:red;
		font-weight:bold;
		font-family: monospace;
	}
  </style>
  <script>
  JSS.console = function(code){
		
		if(typeof code !=='string'){
			return;
		}
		
		var r = "";
		function cb(data){
			r = data;
		}
		$.ajax({
			   type: "POST",
			   url: JSS.contextName+"/sj_console/",
			   data: {jscode:code},
			   async:false,
			   success: cb,
			   error: function(XMLHttpRequest, textStatus, errorThrown){alert('error '+textStatus+' '+errorThrown)}
			 });
		
		return r;
	}

  </script>
</head>
<body>
	<div style="width:800px;">
		<p>
			<b>Application Console </b>
			<span style="color:#666">Type your server js code below. 
				Disable for production instances!</span>
		</p>
		<textarea style="width:800px; height:40px;"></textarea>
		<center>...</center>
		<div id="holder">
		</div>
	</div>
	<script>
		$().ready(function(){
			function doit(){
				var text = $("textarea").val();
				$("textarea").val("");
				var html='<p><span class="prompt">></span>&nbsp;<span class="code"></span><br/>&nbsp;&nbsp;&nbsp;<span class="response">"true"</span></p>';
				$("#holder").append(html);
				$("#holder>p:last").find(".code").html(text);
				
				var result = JSS.console(text);
				$("#holder>p:last").find(".response").html(result);
			}
			
			$("input").click(function(){
				doit();
			});
			
			$("textarea").keypress(function(e){
				var code = (e.keyCode ? e.keyCode : e.which);
				 if(code == 13) { //Enter keycode
				   doit();
				}
			});
		});
	</script>
</body>
</html>