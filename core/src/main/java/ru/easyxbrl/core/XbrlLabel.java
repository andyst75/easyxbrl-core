package ru.easyxbrl.core;

import java.io.Serializable;

/**
 * Текстовое описание элемента
 * Core.lang = "ru";
 * Core.labelRole = "http://www.xbrl.org/2003/role/label";
 * @author Андрей
 *
 */
public class XbrlLabel implements Serializable {

	private static final long serialVersionUID = -8131868011274206095L;
	
	public String role = null;
	public String lang = null;
	public String text = null;

	
	public XbrlLabel(String role, String lang, String text) {
		this.role = role;
		this.lang = lang;
		this.text = text;
	}
}
