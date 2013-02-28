package edu.stevens.cs581.representation;

public class PollException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String exceptionMsg;
	private int exCode;
	
	public PollException(int code, String msg)
	{
		exCode = code; exceptionMsg = msg;
	}
	
	public int getExceptionCode()
	{
		return exCode;
	}
	public String getExceptionText()
	{
		return exceptionMsg;
	}

}
