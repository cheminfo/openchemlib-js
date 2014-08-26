package java.lang.reflect;

public final class Array {
	
	private Array() {}
	
	public static native int getLength(Object array) /*-{
		
		return array.length;
		
	}-*/;
	
	public static native Object newInstance(Class<?> componentType, int length) /*-{
		
		return new Array(length);
		
	}-*/;

}
