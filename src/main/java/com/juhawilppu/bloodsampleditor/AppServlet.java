package com.juhawilppu.bloodsampleditor;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.server.VaadinServlet;

@WebServlet(asyncSupported = false, urlPatterns = { "/*",
		"/VAADIN/*" }, initParams = {
				@WebInitParam(name = "ui", value = "com.juhawilppu.bloodsampleditor.ui.AppUI") })
public class AppServlet extends VaadinServlet {

}
