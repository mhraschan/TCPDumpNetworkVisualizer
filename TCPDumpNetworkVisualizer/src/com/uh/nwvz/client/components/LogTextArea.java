package com.uh.nwvz.client.components;

import com.google.gwt.user.client.ui.RichTextArea;

public class LogTextArea extends RichTextArea {

	private final static String ERROR_PREFIX = "[ERROR]";

	private final static String WARN_PREFIX = "[WARN]";

	private final static String INFO_PREFIX = "[INFO]";

	private final static String ERROR_COLOR = "FF0000";

	private final static String WARN_COLOR = "00FF00";

	private final static String INFO_COLOR = "0000FF";

	public LogTextArea() {
		super();
		setEnabled(false);
	}

	private void appendText(String message, String color) {
		String html = this.getHTML();
		html += "<br/><font color=\"#" + color + "\">" + message + "</font>";
		this.setHTML(html);
	}

	public void logError(String message) {
		appendText(ERROR_PREFIX + message, ERROR_COLOR);
	}

	public void logInfo(String message) {
		appendText(INFO_PREFIX + message, INFO_COLOR);
	}

	public void logWarn(String message) {
		appendText(WARN_PREFIX + message, WARN_COLOR);
	}

}
