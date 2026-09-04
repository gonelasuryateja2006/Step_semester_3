class Library{
    String title;
    String isbns;
    public Library(String title, String isbn){
        this.title = title;
        this.isbns = isbns;
    }
    public Library(String title){
        this(title, "Pending");
    }
    public void display(){
        System.out.println(title +"|" +isbns +"|cataloged:true");
    }
}
public class Librarylist{
    public static void main(String[] args){
        String[]title = {"clean code","untitted draft","1984","notes"};
        String[]isbns = {"978-0132350884","","9780451524935",""};
        for (int i = 0; i < title.length; i++){
            Library book;
            if(isbns[i].equals("")){
                book = new Library(title[i]);
            }else{
                book = new Library(title[i],isbns[i]);
            }
              book.display();
        }
    }
}