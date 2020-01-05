import java.util.Arrays;

public class Situation {

    static final int k = Content.values().length;
    static final int n = Offset.values().length;
    private final int code;

    public Situation(Content[] contents) {
        code = encode(contents);
    }

    public Situation(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public Content[] getContents() {
        return decode(this.code);
    }

    public static Content[] decode(int situationCode) {
        Content[] contents = new Content[n];
        for (int positionIndex=0; positionIndex<n; positionIndex++) {
            int contentCode = (situationCode / (int)Math.pow(k, positionIndex)) % k;
            contents[positionIndex] = Content.values()[contentCode];
        }
        return contents;
    }

    private static int encode(Content[] contents) {
        int situationCode = 0;
        for (int positionIndex=0; positionIndex<n; positionIndex++) {
            situationCode += (int)Math.pow(k, positionIndex) * contents[positionIndex].ordinal();
        }
        return situationCode;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.getContents());
    }
}

