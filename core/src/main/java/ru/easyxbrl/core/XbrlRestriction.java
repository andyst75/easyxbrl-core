package ru.easyxbrl.core;

import java.io.Serializable;
import java.util.regex.Pattern;

import ru.easyxbrl.define.XbrlType;

/**
 * Дополнительные ограничения, накладываемые на значение элемента
 * @author a.starikov
 *
 */
public class XbrlRestriction implements Serializable {

	private static final long serialVersionUID = -4373192886917584648L;

	/**
	 * Тип элемента
	 */
	public XbrlType base = null;
	
	/**
	 * для XbrlType.STRING и XbrlType.W3_STRING - минимальная длина
	 */
	public int minLength = 0; 

	/**
	 * для XbrlType.STRING и XbrlType.W3_STRING - максимальная длина, отрицательное значение - без ограничений
	 */
	public int maxLength = -1;
	
	/**
	 * Скомпилированная строка ограничений (regexp)
	 */
	public Pattern pattern = null; 
	
	/**
	 * Проверка значения на правильность
	 * @param elementValue
	 * @return
	 */
	public boolean test(String elementValue) {
		if (elementValue==null) return true;
		boolean test = true;
		// TODO: дописать проверку на остальные форматы
		if (base!=null) {
			switch (base) {
			case STRING:
			case W3_STRING:
			case TEXT_BLOCK:
				int len = elementValue.length();
				test = test && (len >= minLength);
				test = test && ((len <= maxLength) || maxLength < 0);
				break;

			case W3_DATE:
			case DATE:
				break;
			case DATETIME:
				break;

			case INTEGER:
				break;
				
			case DECIMAL:
			case MONETARY:
				break;

			case DOMAIN_ITEM:
			case ENUM:
			case ESCAPED:
			case PERCENT:
			case PER_SHARE:
			case PURE:
			case SHARES:
			default:
				System.out.println("restriction -> " + base + " = " + elementValue);
				break;
			}
		}
		return (pattern == null ? true : pattern.matcher(elementValue).find()) && true;
	}
}
