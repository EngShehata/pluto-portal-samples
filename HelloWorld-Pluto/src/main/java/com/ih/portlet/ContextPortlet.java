package com.ih.portlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public class ContextPortlet extends GenericPortlet {
	@Override
	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		response.getWriter().println(
				"Attribute:<b> contextAttr</b>="
						+ getPortletContext().getAttribute("contextAttr"));
		response.getWriter().print(
				"<form action='/HelloWorldPortlet/contextservlet'>"
						 + "<input type='submit' value='Go Servlet'/>" + "</form>");
	}

	@Override
	public void init(PortletConfig config) throws PortletException {
		// TODO Auto-generated method stub
		super.init(config);
		Enumeration<String> attrs = config.getPortletContext()
				.getAttributeNames();
		while (attrs != null && attrs.hasMoreElements()) {
			String attr = attrs.nextElement();
			System.out.println("Attribute: " + attr + "="
					+ config.getPortletContext().getAttribute(attr));
		}
		config.getPortletContext().setAttribute("contextAttr",
				"a Value Passed from Protlet Context");
	}
}
