package ru.easyxbrl.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.easyxbrl.define.XbrlPeriodType;
import ru.easyxbrl.define.XbrlSubstitutionGroup;
import ru.easyxbrl.define.XbrlType;
import ru.easyxbrl.define.XbrlTypeBalance;

/**
 * Параметры элемента таксономии
 * @author Андрей
 *
 */
public class XbrlElement implements Serializable {

	private static final long serialVersionUID = 4053955057259962303L;
	
	/**
	 * Ссылка на каталог (родительский namespace)
	 */
	public String parent = null;
	

	public String name = null;								// name из таксономии
	public String id = null;								// id из таксономии
	
	public XbrlType type = null;                            // {http://www.xbrl.org/2003/instance}stringItemType
	public XbrlSubstitutionGroup substitutionGroup = null;  // {http://xbrl.org/2005/xbrldt}hypercubeItem
	
	public boolean abstractElement = false;
	public boolean nillable = false;
	
	public XbrlPeriodType periodType = null;				// instant / durable
	public XbrlTypeBalance balance = null; 					// debit / credit
	
	public String domain = null;                            // {http://www.cbr.ru/xbrl/bfo/dict}SpisokStranList - ссылка на список элементов
	public String linkrole = null;							// http://www.cbr.ru/xbrl/bfo/dict/Countries_list
	public String typedDomain = null;						// http://www.cbr.ru/xbrl/udr/dim/dim-int#dim-int_IDEmitent_TypedName

	public List<XbrlLabel> label = new ArrayList<>();		// список текстовых меток
	
	public List<XbrlRestriction> restrictions = new ArrayList<>(); // список правил проверки значения элемента
	
	public XbrlElement() {};
	
	public XbrlElement(String id) { 
		this.name = id;
		this.id = id;
	};
	
	/**
	 * Получить полное наименование
	 * @return
	 */
	public String getFullName() {
		return parent + "#" + id;
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
	    if (!(obj instanceof XbrlElement))return false;
	    
	    final XbrlElement o = (XbrlElement)obj;
	    return ((id!=null && id.equals(o.id)) || (id==null && o.id==null))
	    		&& ((name!=null && name.equals(o.name)) || (name==null && o.name==null));
	}

	@Override
    public int hashCode() {
        return super.hashCode();
    }

	/**
	 * Возвращает текстовое представление элемента
	 * @param element
	 * @return
	 */
	public String getLabelValue(XbrlDefElement element) {
		return getLabelValue(element.preferredLabel, Core.lang);
	}
	
	
	/**
	 * Возвращает текстовое представление элемента исходя из его Роли и языка.
	 * @param role - null = default
	 * @param lang - null = default
	 * @return
	 */
	public String getLabelValue(String labelRole, String lang) {
		final String findRole = (labelRole==null || "".equals(labelRole)) ? Core.labelRole : labelRole;
		final String findLang = (lang==null || "".equals(lang)) ? Core.lang : lang;
		String out = name;
		for (XbrlLabel x:label) {
			if (findRole.equals(x.role) && findLang.equals(x.lang)) {
				out = x.text;
				break;
			}
		}
		return out;
	}

	/**
	 * Возвращает текстовое представление элемента по умолчанию
	 * Если доступно только одно описание, то его, если несколько - исходя из текущей роли и языка
	 * @return
	 */
	public String getLabelValue() {
		return label.size()==1?label.get(0).text:getLabelValue(null, null);
	}

}
