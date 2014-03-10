package info.softex.dictionary.core.attributes;

/**
 * 
 * @since version 4.2, 03/07/2014
 *
 */
public interface KeyValueInfo<T> {
	
	public T getKey();
	
	public void setKey(T key);
	
	public T getValue();
	
	public void setValue(T value);

}
