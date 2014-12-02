package edu.jsu.mcis;

public class InvalidGroupException extends RuntimeException

{
	public InvalidGroupException(String message)
	{
		super(message);
	}
	public InvalidGroupException (){}
}