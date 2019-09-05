package ru.easyxbrl.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Описание конкретной таблицы (ОКУД) с определённым namespace (role)
 * имеет списки definition & presentation
 * @author Андрей
  */
public class XbrlTable extends Namespaces {

	private static final long serialVersionUID = 6744792990610806699L;
	
	/**
	 * Ссылка на таблицу в виде http://www.cbr.ru/xbrl/nso/purcb/rep/2018-12-31/tab/SR_0420409
	 */
	public String role            = null;

	/**
	 * Разделы presentation
	 * namespace : XbrlDefinition
	 */
	public Map<String, XbrlDefinition> presentation = new HashMap<>();
	
	/**
	 * Дополнительные / замещающие метки для данной таблицы
	 */
	public Map<XbrlElement, List<XbrlLabel>> labels = new HashMap<>();

	public XbrlTable(String targetNamespace) {
		this.role = targetNamespace;
	}
	
	/**
	 * Очищаем все массивы
	 */
	@Override
	public void clear() {
		super.clear();
		presentation.clear();
		labels.clear();
	}

}
