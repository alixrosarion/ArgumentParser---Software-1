package edu.jsu.mcis;

public class ArgumentValues
{
	private String wid;
	private String len;
	private String hei;
	
	public ArgumentValues(String l, String w, String h){
		len = l;
		wid = w;
		hei = h;
	}
	
	public String getLength()
	{
		return len;
	}
	public String getWidth()
	{
		return wid;
	}
	public String getHeight()
	{
		return hei;
	}
	
	public void main(String[] args){
		if(args.length==3){
			ArgumentValues ro = new ArgumentValues(args[0], args[1],args[2]);
		}
	
	}
}