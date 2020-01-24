package ru.easyxbrl.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Основной класс для работы с элементами и справочниками таксономии
 * @author Андрей
 *
 */
public class Core implements Serializable {

	private static final long serialVersionUID = -3062548209495550742L;
	
	/**
	 * Наименование файла версии ядра таксономии в архиве 
	 */
	public static final String fileCoreVersion = "core-version";

	/**
	 * Наименование файла версии таксономии в архиве 
	 */
	public static final String fileTaxonomyVersion = "taxonomy-version";

	/**
	 * Наименование файла ядра таксономии в архиве 
	 */
	public static final String fileCore = "core.bin";

	/**
	 * Версия данной сборки ядра таксономии 
	 */
	public static final String version = "20200130";
	
	/**
	 * URL hyper-dimension
	 */
	public static final String eurofilingHyp = "http://www.eurofiling.info/eu/fr/xbrl/ext/model.xsd#hyp";
	
	/**
	 * Язык для текстовых меток по умолчанию
	 */
	public static String lang = "ru";

	/**
	 * URL для текстовых меток по умолчанию - http://www.xbrl.org/2003/role/label
	 */
	public static String labelRole = "http://www.xbrl.org/2003/role/label";
	
	/**
	 * Текстовая информация о таксономии,
	 * .version - дата релиза,
	 * .comments - название
	 */
	public final TaxonomyInfo info = new TaxonomyInfo(); // 
	
	/**
	 * Точки входа таксономии
	 * http://www.cbr.ru/xbrl/bfo/rep/2018-12-31/ep/ep_purcb_y.xsd : EntryPointElement
	 */
	public Map<String, EntryPointElement> entryPoints = new HashMap<>();
	
	/**
	 * Общие словари (ядро таксономии)
	 * методы: getElement, getDescription, getNamespaceByRoleId, getUriByNamespace
	 */
	public final Namespaces dicts = new Namespaces();
	
	/**
	 * Таблицы отчётности
	 * http://www.cbr.ru/xbrl/nso/purcb/rep/2019-05-01/tab/SR_0420409 : tables
	 */
	public final Map<String, XbrlTable> tables  = new HashMap<>();
	
	/**
	 * Соотношение namespace:location
	 * http://www.cbr.ru/xbrl/bfo/dict : http://www.cbr.ru/xbrl/bfo/dict/dictionary.xsd
	 */
	public final Map<String, String> location = new HashMap<>();
	
	/**
	 * Соотношение location : namespace
	 * http://www.cbr.ru/xbrl/bfo/dict/dictionary.xsd : http://www.cbr.ru/xbrl/bfo/dict
	 */
	public final Map<String, String> revLocation = new HashMap<>();

	/**
	 * Очищаем данные
	 */
	public void clear() {
		info.clear();
		dicts.clear();
		entryPoints.clear();
		tables.clear();
		location.clear();
		revLocation.clear();
	}
	
	/**
	 * Загружаем таксономию из потока в виде zip-архива
	 * @param is
	 * @return
	 */
	public static Core load(InputStream is) {
		Core core = null;
		String version = null;

		try(var zin = new ZipInputStream( is , Charset.forName("UTF8")))
        {		
			final long start = System.currentTimeMillis();
			
			final Map<ZipEntry,byte[]> files = new HashMap<>();
			final Map<String, ZipEntry> filenames = new HashMap<>();
			
			// считываем файлы таксономии в память
			String rootPrefix = null;
			try (ZipInputStream zip = new ZipInputStream(is)) {
				System.out.println("Начало загрузки файлов из потока.");
	    		ZipEntry entry;
	            while((entry=zip.getNextEntry())!=null){
	            	final String entryName = entry.getName().replace("\\", "/");
	            	filenames.put(entryName, entry);
	            	if (rootPrefix == null) {
	            		final int startPosition = entryName.indexOf("/");
	            		if (startPosition == -1) {
	            			rootPrefix = "";
	            		} else {
	                		rootPrefix = entryName.substring(0, startPosition + 1);
	            		}
	            	} else {
	            		if (!entry.getName().startsWith(rootPrefix)) System.out.println(entry.getName());
	            	}
	            	
	            	files.put(entry, zip.readAllBytes());
	            }
			}

			// чтение версии ядра
			{
	    		final String description = rootPrefix+fileCoreVersion;
	    		if (filenames.containsKey(description)) {
	    			final ByteArrayInputStream ba = new ByteArrayInputStream(files.get(filenames.get(description)));
    	    		version = new String(ba.readAllBytes());
	    		}
			}
			
			if (Core.version.equals(version)) {
				// чтение ядра
				{
		    		final String description = rootPrefix+fileCore;
		    		if (filenames.containsKey(description)) {
		    			final ByteArrayInputStream ba = new ByteArrayInputStream(files.get(filenames.get(description)));
		    	    	try {
		    				try (ObjectInputStream ois = new ObjectInputStream(ba)) {
		    					core = (Core) ois.readObject();
		    				} catch (Exception e) {
		    					e.printStackTrace();
		    				}
						} catch (Exception e) {
							e.printStackTrace();
						}
		    		}
				}
				System.out.println("Успешная загрузка файла за "+(System.currentTimeMillis()-start)+" мсек.");
			}
			
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		if (core == null) {
			System.out.println("Ошибка загрузки обработанного файла таксономии.");
			if (!Core.version.equals(version)) {
	        	System.out.println("Ожидалась версия "+Core.version+", в файле - " + version + ".");
			}
		}
		
		return core;
	}
}
