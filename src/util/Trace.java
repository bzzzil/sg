package util;

public class Trace {
	private static final Trace INSTANCE = new Trace();
	private TraceInterface traceHandler;
	
	public static Trace getInstance()
	{
		return INSTANCE;
	}
	
	public void setHandler(TraceInterface handler)
	{
		traceHandler = handler;
	}
	
	public static void message(String msg)
	{
		getInstance().traceHandler.message(msg);
	}

	public static void warning(String msg)
	{
		getInstance().traceHandler.warning(msg);
	}

	public static void error(String msg)
	{
		getInstance().traceHandler.error(msg);
	}
	
	public static void critical(String msg)
	{
		getInstance().traceHandler.critical(msg);
	}
}
