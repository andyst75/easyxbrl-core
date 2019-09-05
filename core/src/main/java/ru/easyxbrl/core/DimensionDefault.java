package ru.easyxbrl.core;

public interface DimensionDefault {

	/**
	 * Сочетание axis - element является осью по умолчанию (dimension-default)
	 * @param axis - закрытая ось
	 * @param element - элемент
	 * @return
	 */
	public boolean isDefaults(XbrlElement axis, XbrlElement element);
	
	/**
	 * Помещаем сочетания axis - element, общие для всех таблиц, в список осей по умолчанию
	 * @param axis
	 * @param element
	 */
	public void putDefaults(XbrlElement axis, XbrlElement element);

}
