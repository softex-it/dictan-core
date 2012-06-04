package info.softex.dictionary.core.collation;

import java.text.Collator;
import java.text.ParseException;

/**
 * 
 * @since version 2.5, 07/12/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
public interface CollatorFactory {

	public Collator createCollator(String rules, Integer strength, Integer decomposition) throws ParseException;
	
}
