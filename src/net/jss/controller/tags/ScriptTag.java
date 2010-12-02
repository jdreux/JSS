package net.jss.controller.tags;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

 import net.jss.controller.FrontController;
 import net.jss.js.ScriptCore;

public class ScriptTag extends BodyTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String PROTECTED_ATTRIBUTE_VALUE = "protected";
	private static final String SERVER_ATTRIBUTE_VALUE = "server";

	private String runat;
	private String src;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getRunat() {
		return runat;
	}

	public void setRunat(String runat) {
		this.runat = runat;
	}

	@Override
	public int doStartTag() throws JspException {
		System.out.println("Processing tag with runat " + this.getRunat() + " and src " + this.getSrc());
		String runatParameter = this.getRunat();
		String src = this.getSrc();
		if (runatParameter == null) {
			throw new JspException("runat paramenter cannot be null");
		} else if (src != null) {

			System.out.println("Received script with src " + src + " running at " + runatParameter);
			ServletContext sc = FrontController.context.getServletContext();
			try {
				Reader reader = new FileReader(new File(sc.getRealPath(src)));
				if (runatParameter.equals(ScriptTag.PROTECTED_ATTRIBUTE_VALUE)) {

					this.writeProtectedScript(ScriptCore.getInstance().receiveProtectedScript(reader));
				} else if (runatParameter.equals(ScriptTag.SERVER_ATTRIBUTE_VALUE)) {
					ScriptCore.getInstance().receiveServerScript(reader);
				} else {
					throw new JspException("runat paramenter '" + runatParameter
							+ "' is not supported. Supported values are server|protected.");
				}
			} catch (FileNotFoundException e) {
				throw new JspException("Error handling script with src " + src, e);
			} catch (IOException e) {
				throw new JspException(e.getMessage(), e);
			}

			return BodyTagSupport.SKIP_BODY;
		} else {
			return super.doStartTag();
		}

	}

	@Override
	public int doAfterBody() throws JspException {
		String runatParameter = this.getRunat();
		String src = this.getSrc();

		if (runatParameter == null) {
			throw new JspException("runat paramenter cannot be null");

		} else if (src != null) {
			// do nothing. should not be here.
		} else {

			String scriptCode = this.getBodyContent().getString();

			// clean up
			this.getBodyContent().clearBody();

			System.out.println(runatParameter + " script contains code: " + scriptCode);

			ScriptCore scriptCore = ScriptCore.getInstance();

			if (runatParameter.equals(ScriptTag.PROTECTED_ATTRIBUTE_VALUE)) {
				try {
					this.writeProtectedScript(scriptCore.receiveProtectedScript(scriptCode));
				} catch (IOException e) {
					throw new JspException(e.getMessage(), e);
				}
			} else if (runatParameter.equals(ScriptTag.SERVER_ATTRIBUTE_VALUE)) {
				scriptCore.receiveServerScript(scriptCode);
			} else {
				throw new JspException("runat paramenter '" + runatParameter
						+ "' is not supported. Supported values are server|protected.");
			}

		}

		return BodyTagSupport.SKIP_BODY;
	}

	private void writeProtectedScript(String scriptToWrite) throws IOException {
		JspWriter out = this.getPreviousOut();
		out.write("<script");
		if (this.type != null) {
			out.write(" type=\"" + this.type + "\"");
		}
		out.write(">" + scriptToWrite + "</script>");
	}
}
