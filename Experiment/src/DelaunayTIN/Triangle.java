package DelaunayTIN;

public class Triangle {

    /**
     * �����ε�������
     */
    private Line firstLine;
    private Line secondLine;
    private Line thirdLine;

    /**
     * ���췽��
     * @param firstLine
     * @param secondLine
     * @param thirdLine
     */
    public Triangle(Line firstLine, Line secondLine, Line thirdLine) {
        this.firstLine = firstLine;
        this.secondLine = secondLine;
        this.thirdLine = thirdLine;
    }

    public Line getFirstLine() {
        return firstLine;
    }

    public Line getSecondLine() {
        return secondLine;
    }

    public Line getThirdLine() {
        return thirdLine;
    }
}
