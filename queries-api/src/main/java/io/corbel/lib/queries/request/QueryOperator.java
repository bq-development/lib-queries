package io.corbel.lib.queries.request;

public enum QueryOperator {
    $EQ(false), $GT(false), $GTE(false), $IN(true), $NIN(true), $ALL(true), $LT(false), $LTE(false), $NE(false), $LIKE(false), $ELEM_MATCH(
            true), $EXISTS(false), $SIZE(false);

    boolean arrayOperator;

    private QueryOperator(boolean arrayOperator) {
        this.arrayOperator = arrayOperator;
    }

    public boolean isArrayOperator() {
        return arrayOperator;
    }

}
