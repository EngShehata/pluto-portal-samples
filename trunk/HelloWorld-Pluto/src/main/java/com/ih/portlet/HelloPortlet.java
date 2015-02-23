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
						+ title + "'>" + "<input type='submit'/>" + "<input type='hidden' name='mode' value='view'/>"+ "</form>");
	}

	String title = "";

	@Override
	public void processAction(ActionRequest actionRequest,
			ActionResponse actionResponse) {
		String mode=actionRequest.getParameter("mode");
		if(mode==null ||mode.equalsIgnoreCase("view"))
		{
		title = actionRequest.getParameter("title");
		synchronized (this) {
			actionCount++;
		}
		}else
			if(mode.equalsIgnoreCase("edit"))
			{
				 String username = actionRequest.getParameter("username");
			        String password = actionRequest.getParameter("password");
			         
			        // Do some checking procedure
			        if(username != null && password != null){
			            // Use render parameters to pass the result
			            actionResponse.setRenderParameter("loggedIn", "true");
			        }
			        else {
			            // Use render parameters to pass the result
			            actionResponse.setRenderParameter("loggedIn", "false");
			        }	
			}
	}
@Override
protected void doHelp(RenderRequest request, RenderResponse response)
		throws PortletException, IOException {
		if (request.getParameter("loggedIn") == null
				|| !Boolean.parseBoolean(request.getParameter("loggedIn"))) {
			response.getWriter()
					.println(
							"<form action="
									+ response.createActionURL()
									+ ">"
									+ "Enter Username : <input type='text' id='username' name='username'/>"
									+ "Enter Password : <input type='password' id='password' name='password'/>"
									+ "<input type='hidden' name='mode' value='edit'/>"
									+ "<input type='submit' value='Login'/>"
									+ "</form>");
		} else {
			response.getWriter().println(
					"<form action=" + response.createActionURL() + ">"
							+ "<p>You're logged in</p><input type='hidden' name='loggedIn' value='true'/>" + "</form>");
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
