abstract class GardenTool {

    public GardenTool() {
        System.out.println("GardenTool constructed");
    }

    public String use() {
        return "Using the tool in the garden";
    }
}

class CuttingTool extends GardenTool {

    public CuttingTool() {
        super();
    }

    @Override
    public String use() {
        return super.use() + ", blade sharpened first";
    }
}

class Pruner extends CuttingTool {

    public Pruner() {
        super();
    }

    @Override
    public String use() {
        return super.use()
                + ", then trimming branches precisely";
    }
}

public class BackyardToolshedRoutine {

    public static void main(String[] args) {
        CuttingTool cuttingTool = new CuttingTool();
        System.out.println(cuttingTool.use());

        System.out.println();

        Pruner pruner = new Pruner();
        System.out.println(pruner.use());
    }
}