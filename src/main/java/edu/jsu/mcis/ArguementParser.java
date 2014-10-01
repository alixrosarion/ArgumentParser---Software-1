package edu.jsu.mcis;

public class ArguementParser{
	private String arg1;
	private String arg2;
	private String arg3;
	
	public ArguementParser(String l, String w, String h){
		arg1 = l;
		arg2 = w;
		arg3 = h;
	}
	
	public String getArg1(){
		return arg1;
	}
	
	public String getArg2(){
		return arg2;
	}
	
	public String getArg3(){
		return arg3;
	}
	
	public int getArgsLength(String [] array)
	{
		return array.length;
	}
	public static void main(String[] args){
		
		if( args.length == 3){
			ArguementParser commandLine = new ArguementParser(args[0], args[1], args[2]);
		}
		else if (args.length < 3){
			System.out.println("Not Enough Arguments");
			System.exit(0);
		}
		else{
			System.out.println("Too Many Arguments");
			System.exit(0);
		}
		
	}

}