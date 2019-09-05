package ru.easyxbrl.core;

import java.io.Serializable;
import java.util.ArrayList;

import ru.easyxbrl.define.XbrlArcrole;

/**
 * Дополнительные атрибуты объекта XbrlElement
 * включая дочерние элементы (на основе definition & presentation)
 * @author Андрей
 *
 */
public class XbrlDefElement implements Serializable {

	private static final long serialVersionUID = -3519813899698516485L;
	
	public XbrlArcrole arcrole = null;
	public XbrlElement element = null;
	public ArrayList<XbrlDefElement> child  = new ArrayList<>();
	public XbrlDefElement parent = null;
	public String order = "0";
	public String targetRole = null;
	public String preferredLabel = Core.labelRole;
	public String label = null;

	/**
	 * Список элементов, связанных XbrlArcrole.NOT_ALL
	 */
	public ArrayList<XbrlDefElement> notAll = new ArrayList<>();

}
