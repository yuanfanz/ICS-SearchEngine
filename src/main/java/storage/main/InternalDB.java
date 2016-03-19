package storage.main;

public interface InternalDB {
	
	public void reset();
	public void init(String URI, String DB_Name);
}
