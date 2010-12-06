var JSS = {};

// The context name is to be populated when serving the
// request (see jss_dependencies.js)
JSS.contextName = undefined;

JSS.log = function(text) {

	$logDiv = $("#logDiv");
	if ($logDiv.length == 0) {
		$("body").append('<div id="logDiv"></div>');
		$logDiv = $("#logDiv");
	}

	$logDiv.append(text + "<br/>");
}

JSS.sajax = function(fnName, args) {
	var o = {};
	function cb(data) {
		o.data = data;
	}

	var argmts = JSON.stringify(Array.prototype.slice.call(args));

	JSS.ajax({
		'fn' : fnName,
		'ar' : argmts
	}, cb);
	if (o.data != "null" || o.data != "undefined") {
		return o.data;
	}
};

JSS.ajax = function(params, callback) {

	$.ajax({
		type : "POST",
		url : JSS.contextName + "/sj_ajax/",
		data : params,
		async : false,
		success : callback,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('error ' + textStatus + ' ' + errorThrown);
			if (errorThrown == 405) {
				window.location.reload();
			}
		}
	});
};

JSS.event = function(type, data) {
	this.type = type;
	if (data)
		this.data = data;
}

JSS.events = new function() {
	var listeners = [];

	this.fireEvent = function(event, received) {

//		if (typeof event === "string") {
			// shortcut for when a simple event is fired
//			event = new JSS.event(event);
//		}

		JSS.log("Firing event with type: " + event.type)

		if (listeners[event.type]) {
			var callbacks = listeners[event.type];
			JSS.log(callbacks.length+" listeners were found");
			for ( var i = 0; i < callbacks.length; i++) {
				callbacks[i](event);
			}
		}

		if (received === true) {
			return;
		} else {
			// fire event to the server
			JSS.comet({
				'event' : event
			});
		}
	};

	this.receiveEvent = function(event) {
		this.fireEvent(event, true);
	};

	this.bind = function(eventType, callback) {
		if (listeners[eventType] === undefined) {
			listeners[eventType] = [callback];
		} else {
			listeners.push(callback);
		}
	};

	this.unbind = function(eventType, callback) {
		var typeListeners = listeners[type];
		for ( var i = 0; i < typeListeners.length; i++) {
			if (typeListeners[i] === callback) {
				typeListeners.splice(i, 1);
				break;
			}
		}
	};
};

JSS.comet = function(params) {

	function callback(event) {
		if (event !== null) {
			JSS.events.fireEvent(JSON.parse(event));
		}
//		JSS.comet({});
	}

	$.ajax({
		type : "POST",
		url : JSS.contextName + "/sj_comet/",
		data : params,
		async : true,
		success : callback,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//alert('error ' + textStatus + ' ' + errorThrown);
//			JSS.commet({});
		}
	});
};

$(document).ready(function() {
	//Begin an asynchronous connection with the server ASAP.
//	JSS.comet({});
});
