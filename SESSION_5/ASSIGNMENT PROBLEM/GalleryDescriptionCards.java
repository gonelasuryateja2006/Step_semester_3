abstract class ArtPiece {
    private static int counter = 0;

    private final String pieceId;
    protected final String title;

    public ArtPiece(String title) {
        counter++;
        this.pieceId = "ART-" + counter;
        this.title = title;
    }

    public String getPieceId() {
        return pieceId;
    }

    public abstract String describe();
}

class Painting extends ArtPiece {

    public Painting(String title) {
        super(title);
    }

    @Override
    public String describe() {
        return "Painting: " + title + ", framed on canvas";
    }
}

class Sculpture extends ArtPiece {

    public Sculpture(String title) {
        super(title);
    }

    @Override
    public String describe() {
        return "Sculpture: " + title + ", carved from stone";
    }
}

public class GalleryDescriptionCards {

    public static void main(String[] args) {
        Painting painting = new Painting("Sunset Fields");
        Sculpture sculpture = new Sculpture("The Thinker II");

        System.out.println(painting.getPieceId());
        System.out.println(painting.describe());

        System.out.println(sculpture.getPieceId());
        System.out.println(sculpture.describe());
    }
}