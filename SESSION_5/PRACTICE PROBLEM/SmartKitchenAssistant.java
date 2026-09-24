abstract class KitchenTool {
    private int speedLevel;

    public KitchenTool() {
        speedLevel = 1;
    }

    public int getSpeedLevel() {
        return speedLevel;
    }

    public void setSpeedLevel(int speedLevel) {
        if (speedLevel >= 1 && speedLevel <= 5) {
            this.speedLevel = speedLevel;
        } else {
            System.out.println(
                    "Rejected, speed level stays "
                            + this.speedLevel
            );
        }
    }

    public abstract String prepare();
}

interface Washable {
    String clean();
}

class Blender extends KitchenTool implements Washable {

    public Blender() {
        super();
    }

    @Override
    public String prepare() {
        return "Blending at speed " + getSpeedLevel();
    }

    @Override
    public String clean() {
        return "Blender rinsed and dried";
    }
}

public class SmartKitchenAssistant {

    public static void main(String[] args) {
        Blender blender = new Blender();

        blender.setSpeedLevel(3);
        System.out.println(blender.getSpeedLevel());

        blender.setSpeedLevel(9);

        System.out.println(blender.prepare());
        System.out.println(blender.clean());
    }
}