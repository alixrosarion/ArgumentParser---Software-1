import java.util.*;

public class Book {
    public String lang;
    public String title;
    public String id;
    public String isbn;
    public Date regDate;
    public String publisher;
    public int price;
    public List<String> authors;
    public Book(){
        authors=new ArrayList<String>();
    }
	public String toString() {
		String s = title + ", " + isbn + ", " + price;
		s += ", " + id + ": ";
		for(String a : authors) {
			s += a + ",";
		}
		return s;
	}
}