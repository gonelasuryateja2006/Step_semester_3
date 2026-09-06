class LibraryMember {
    private String membershipPin;
    String branchCode;
    protected double finesOwed;
    public String displayName;
}
public class Problem1{
    static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier.equals("private")) {
            return accessorContext.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";
        }
        if (fieldModifier.equals("default")) {
            return accessorContext.equals("SAME_CLASS") ||
                    accessorContext.equals("SAME_PACKAGE")
                    ? "ALLOWED" : "DENIED";
        }
        if (fieldModifier.equals("protected")) {
            return accessorContext.equals("SAME_CLASS") ||
                    accessorContext.equals("SAME_PACKAGE")
                    ? "ALLOWED" : "DENIED";
        }
        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }
        return "DENIED";
    }
    static String summarizeByModifier(String[][] attempts) {
        String[] modifiers = {"private", "default", "protected", "public"};
        StringBuilder result = new StringBuilder();
        for (String modifier : modifiers) {
            int allowed = 0;
            int denied = 0;
            for (String[] attempt : attempts) {
                if (attempt[0].equals(modifier)) {
                    String access = classifyAccess(attempt[0], attempt[1]);
                    if (access.equals("ALLOWED"))
                        allowed++;
                    else
                        denied++;
                }
            }
            if (result.length() > 0)
                result.append(" | ");
            result.append(modifier)
                    .append(": ")
                    .append(allowed)
                    .append(" allowed / ")
                    .append(denied)
                    .append(" denied");
        }
        return result.toString();
    }
    public static void main(String[] args) {
        String[][] attempts = {
                {"private", "SAME_CLASS"},
                {"private", "SAME_PACKAGE"},
                {"default", "SAME_PACKAGE"},
                {"default", "DIFFERENT_PACKAGE"},
                {"protected", "SAME_PACKAGE"},
                {"protected", "SAME_CLASS"},
                {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(summarizeByModifier(attempts));
    }
}