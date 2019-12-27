package ru.easyxbrl.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Данные из пространства имён ядра таксономии
 * definitions - словари таксономии
 * getElement - получить элемент
 * getDescription - получить описание для namespace
 * @author Андрей
 *
 */
public class Namespaces implements Serializable, DimensionDefault {

	private static final long serialVersionUID = -826524565006084774L;
	
	private Map<String, String> descMap = new HashMap<>();                   // храним описания для элементов (секций)  targetNamespace, description
	private Map<String, Map<String, XbrlElement>> dictMap = new HashMap<>(); // храним xsd:id_element из словарей
	private Map<String, Map<String, XbrlElement>> dictMapName = new HashMap<>(); // храним xsd:name_element из словарей
	private Map<String, Map<String, String>> roleUriMap = new HashMap<>();   // храним все roleUri: родительский targetNamespace, <id, targetNamespace>
	private Map<String, String> roleUriRevMap = new HashMap<>();             // обратный поиск roleUri: целевой targetNamespace, родительский targetNamespace#id
	private Map<XbrlElement, Set<XbrlElement>> defaults = new HashMap<>();   // dimension-default, element : set<axis>
	
	/**
	 * Словари ядра таксономии, разделы definition
	 * namespace : XbrlDefinition
	 */
	public Map<String, XbrlDefinition> definitions = new HashMap<>();		 // списки, таблицы definition

	/**
	 * Очищаем все массивы
	 */
	public void clear() {
		descMap.clear();
		dictMapName.clear();
		dictMap.clear();
		roleUriMap.clear();
		roleUriRevMap.clear();
		definitions.clear();
		defaults.clear();
	}

	/**
	 * Получаем элемент по uri, name
	 * @param uri
	 * @param id
	 * @return
	 */
	public XbrlElement getElementByName(String uri, String name) {
		return dictMapName.containsKey(uri) ? dictMapName.get(uri).get(name) : null;
	}


	/**
	 * Получаем элемент по {uri} name
	 * @param uri
	 * @param name
	 * @return
	 */
	public XbrlElement getElementByName(String uri) {
		if (uri.contains("} ")) {
			String[] path = uri.split("} ");
			return dictMapName.containsKey(path[0].substring(1)) ? dictMapName.get(path[0].substring(1)).get(path[1]) : null;
		} else {
			return null;
		}
	}

	
	
	
	/**
	 * Получаем элемент по uri, id
	 * @param uri
	 * @param id
	 * @return
	 */
	public XbrlElement getElement(String uri, String id) {
		return dictMap.containsKey(uri) ? dictMap.get(uri).get(id) : null;
	}

	/**
	 * Получаем элемент по uri#id или {uri} id
	 * @param uri
	 * @return - null, если элемент не найден
	 */
	public XbrlElement getElement(String uri) {
		if (uri.contains("#")) {
			String[] path = uri.split("#");
			return dictMap.containsKey(path[0]) ? dictMap.get(path[0]).get(path[1]) : null;
		} else {
			return null;
		}
	}

	/**
	 * Получаем текстовое описание секции / элементов или null, если для секции нет локального описания
	 * @param uri
	 * @return
	 */
	public String getDescription(String uri) {
		return descMap.get(uri);
	}
	
	/**
	 * Получаем ссылку на нужное пространство через roleUri
	 * @param uri
	 * @param id
	 * @return
	 */
	public String getNamespaceByRoleId(String uri, String id) {
		return roleUriMap.containsKey(uri) ? roleUriMap.get(uri).get(id) : null;
	}
	
	/**
	 * Получаем ссылку на элемент, в котором было объявлено пространство uri
	 * @param uri
	 * @return
	 */
	public String getUriByNamespace(String uri) {
		//roleUriRevMap.keySet().forEach( k-> System.out.println(k));
		
		return roleUriRevMap.get(uri);
	}
	
	/**
	 * Добавляем элемент в словарь
	 * Обязательные параметры объекта:
	 * ссылка на словарь - parent
	 * ссылка на идентификатор элемента - id
	 * @param element
	 */
	public void putElement(XbrlElement element) {
		dictMap.putIfAbsent(element.parent, new HashMap<>());
		dictMap.get(element.parent).put(element.id, element);
		dictMapName.putIfAbsent(element.parent, new HashMap<>());
		dictMapName.get(element.parent).put(element.name, element);
	}
	
	/**
	 * Помещаем элементы справочника в массив
	 * @param uri
	 * @param elements
	 */
	public void putDictionary(String uri, List<XbrlElement> elements) {
		
		final Map<String, XbrlElement> dict = new HashMap<>(elements.size());
		final Map<String, XbrlElement> dictName = new HashMap<>(elements.size());
		
		elements.forEach( e -> {
			dict.put(e.id, e);
			dictName.put(e.name, e);
		});

		if (dictMap.containsKey(uri)) {
			dictMap.get(uri).putAll(dict);
		} else {
			dictMap.put(uri, dict);
		}

		if (dictMapName.containsKey(uri)) {
			dictMapName.get(uri).putAll(dictName);
		} else {
			dictMapName.put(uri, dictName);
		}
	}
	
	/**
	 * Помещаем текстовое описание секции, связь parent#id -> namespace
	 * @param parentNs - родительский ns
	 * @param id - ключ доступа через родительский ns
	 * @param targetNs - целевой ns
	 * @param description - описание секции / элемента
	 */
	public synchronized void putRoleUri(String parentNs, String id, String targetNs, String description) {
		roleUriMap.putIfAbsent(parentNs, new HashMap<>());
		roleUriMap.get(parentNs).put(id, new String(targetNs));
		roleUriRevMap.put(targetNs, parentNs+"#"+id);
		descMap.put(targetNs, description);
	}
	
	/**
	 * Сочетание axis - element является осью по умолчанию (dimension-default)
	 * @param axis - закрытая ось
	 * @param element - элемент
	 * @return true - если сочетание найдено
	 */
	@Override
	public boolean isDefaults(XbrlElement axis, XbrlElement element) {
		return defaults.getOrDefault(element, new HashSet<>()).contains(axis);
	}
	
	/**
	 * Помещаем сочетания axis - element, общие для всех таблиц, в список осей по умолчанию
	 * @param axis
	 * @param element
	 */
	@Override
	public synchronized void putDefaults(XbrlElement axis, XbrlElement element) {
		
		defaults.putIfAbsent(element, new HashSet<>());
		defaults.get(element).add(axis);
	}
	
}
