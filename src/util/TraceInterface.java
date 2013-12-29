package util;

public interface TraceInterface {
	public void message(String msg);
	public void warning(String msg);
	public void error(String msg);
	public void critical(String msg);
}
