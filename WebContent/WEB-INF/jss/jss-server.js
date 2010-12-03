/*!
 * jss-server.js
 * http://jss-framework.net/
 *
 * Copyright 2010, Julien Dreux
 * 
 * Date: Wed Dec 1st, 2010. 
 */

//Import core Java packages
importPackage(java.lang);

// Import core JSS packages
importPackage(net.jss.js.adapters);

// Add support for the setTimeout, setInterval and clearInterval Javascript
// functions

var intervals = [];

setTimeout = function(callback, time) {
	var id;
	return id = setInterval(function() {
		callback();
		clearInterval(id);
	}, time);
};

setInterval = function(callback, time) {
	var id = intervals.length;

	intervals[id] = new java.lang.Thread(new java.lang.Runnable({
		run : function() {
			while (true) {
				java.lang.Thread.currentThread().sleep(time);
				callback();
			}
		}
	}));

	intervals[id].start();

	return id;
};

clearInterval = function(id) {
	if (intervals[id]) {
		intervals[id].stop();
		delete intervals[id];
	}
};

var JSS = {};

// Build the JSS object
if(!JSS.db) JSS.db = PersistenceAdapter.getInstance();

if(!JSS.session) JSS.session = SessionAdapter.getInstance();

if(!JSS.authentication) JSS.authentication = AuthenticationAdapter.getInstance();

if(!JSS.utils) JSS.utils = JavascriptSupportAdapter.getInstance();

if(!JSS.form) JSS.form = FormHelperAdapter.getInstance();

if(!JSS.events) JSS.events = EventsAdapter.getInstance();
