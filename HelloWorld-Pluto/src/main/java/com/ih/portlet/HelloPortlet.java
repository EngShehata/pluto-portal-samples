package com.ih.portlet;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public class HelloPortlet extends GenericPortlet {
	private static int renderCount = 0;
	private static int actionCount = 0;

	@Override
	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		response.setContentType("text/html");
		synchronized (this) {
			renderCount++;
		}
		PortletURL url = response.createActionURL();
		System.out.println(url.toString());
		response.getWriter().print(
				"<form action=" + url + ">" + "<p>Render has executed "
						+ renderCount + "</p>" + "<p>Render has executed "
						+ actionCount + "</p>"
						+ " Title <input type='text' name='title'value='"
						+ title + "'>" + "<input type='submit'/>" + "</form>");
	}

	String title = "";

	@Override
	public void processAction(ActionRequest actionRequest,
			ActionResponse actionResponse) {
		String[] titles = actionRequest.getParameterMap().get("title");
		if (titles != null && titles.length > 0)
			title = titles[0];
		synchronized (this) {
			actionCount++;
		}
	}

	@Override
	protected String getTitle(RenderRequest request) {
		if (title != null && !title.isEmpty())
			return title;
		// If it's like that, just get the defined bundle
		ResourceBundle bundle = this.getPortletConfig().getResourceBundle(
				new Locale("en"));
		// Retrun the string that's corresponded for anyTitle property
		return (String) bundle.getObject("javax.portlet.title");
	}

	@Override
	public void init(PortletConfig config) throws PortletException {
		super.init(config);
	}
}
