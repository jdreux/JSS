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

//Import core JSS packages
importPackage(net.jss.js.adapters);

var JSS = {};

//Build the JSS object
JSS.db = PersistenceAdapter.getInstance();

JSS.session = SessionAdapter.getInstance();

JSS.authentication = AuthenticationAdapter.getInstance();

JSS.utils = JavascriptSupportAdapter.getInstance();

JSS.form = FormHelperAdapter.getInstance();
