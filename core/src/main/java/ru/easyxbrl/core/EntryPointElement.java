package ru.easyxbrl.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Описание точки входа (на основе entry_point.xml & taxonomyPackage.xml)
 * @author Андрей
 *
 */
public class EntryPointElement implements Serializable {
	private static final long serialVersionUID = 9147309977589510068L;
	
	public String nfoType             = null; // 532P
	public String reportType          = null; // BFO9
	public String reportPeriodType    = null; // y, q, m, w, r
	public String nfoTypeRus          = null; // Профессиональный участник рынка ценных бумаг, акционерный инвестиционный фонд, организатор торговли, центральный контрагент, клиринговая организация, специализированный депозитарий инвестиционного фонда, паевого инвестиционного фонда и негосударственного пенсионного фонда, управляющая компания инвестиционного фонда, паевого инвестиционного фонда и негосударственного пенсионного фонда, бюро кредитных историй, кредитное рейтинговое агентство, страховой брокер 
	public String reportTypeRus       = null; // Бухгалтерская (финансовая) отчётность в соответствии с МСФО (IFRS) 9
	public String reportPeriodTypeRus = null; // Отчётность на конец года

	public String tpName              = null; // ep_purcb_y
	public String tpDescription       = null; // Профессиональный участник рынка ценных бумаг, акционерный инвестиционный фонд, организатор торговли, центральный контрагент, клиринговая организация, специализированный депозитарий инвестиционного фонда, паевого инвестиционного фонда и негосударственного пенсионного фонда, управляющая компания инвестиционного фонда, паевого инвестиционного фонда и негосударственного пенсионного фонда, бюро кредитных историй, кредитное рейтинговое агентство, страховой брокер. Бухгалтерская (финансовая) отчетность в соответствии с МСФО (IFRS) 9. Отчетность на конец года
	public String entryPointDocument  = null; // http://www.cbr.ru/xbrl/bfo/rep/2018-12-31/ep/ep_purcb_y.xsd
	
	/**
	 * Список таблиц, относящихся к точке входа, в виде namespace
	 * http://www.cbr.ru/xbrl/nso/purcb/rep/2018-12-31/tab/SR_INFO
	 */
	public final List<String> tables = new ArrayList<>();
}
