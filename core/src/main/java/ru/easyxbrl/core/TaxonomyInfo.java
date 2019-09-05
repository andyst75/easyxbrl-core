package ru.easyxbrl.core;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Текстовая информация о таксономии
 * version - дата релиза
 * comments - название
 * @author a.starikov
 *
 */
public class TaxonomyInfo implements Serializable {

	private static final long serialVersionUID = 6515768089078914713L;
	
	public String version = null; 			// 20190401
	public String comments = null; 			// Таксономия XBRL Банка России версия 3.0
	public LocalDateTime dateBegin = null; 	// 2019-04-01T00:00:00
	public LocalDateTime dateEnd = null; 	// 2020-06-30T00:00:00
	public LocalDateTime datePublic = null; //
	
	public String tpVersion = null; 		// http://www.cbr.ru/xbrl/2018-12-31.zip
	public String tpName = null; 			// Таксономия XBRL Банка России версия 3.0
	public String tpIdentifier = null; 		// 3.0
	
	
	public void clear() {
		version = null;
		comments = null;
		dateBegin = null;
		dateEnd = null;
		datePublic = null;
		
		tpVersion = null;
		tpName = null;
		tpIdentifier = null;
	}
	
}
