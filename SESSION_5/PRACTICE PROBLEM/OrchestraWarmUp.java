abstract class Instrument {

    public Instrument() {
    }

    public String play() {
        return "";
    }
}

class StringInstrument extends Instrument {

    public StringInstrument() {
        super();
    }

    @Override
    public String play() {
        return super.play() + "Strumming the strings";
    }
}

class Violin extends StringInstrument {

    public Violin() {
        super();
    }

    @Override
    public String play() {
        return super.play()
                + ", with a bow drawn across four strings";
    }
}

public class OrchestraWarmUp {

    public static void main(String[] args) {
        StringInstrument stringInstrument =
                new StringInstrument();

        Violin violin = new Violin();

        System.out.println(stringInstrument.play());
        System.out.println(violin.play());
    }
}