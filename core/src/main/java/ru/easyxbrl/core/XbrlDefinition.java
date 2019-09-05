package ru.easyxbrl.core;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Стартовый элемент для описания namespace definition (секции презентации)
 * @author Андрей
 *
 */
public class XbrlDefinition implements Serializable {

	private static final long serialVersionUID = -1432813899698516485L;
	
	/**
	 * namespace (role) для списка элементов
	 */
	public String role = null;
	
	public ArrayList<XbrlDefElement> child = new ArrayList<>();

	public XbrlDefinition() { };
	
	/**
	 * Корневой элемент списка элементов XbrlDefElement
	 * @param role
	 */
	public XbrlDefinition(String role) {
		this.role = role;
	}
}
