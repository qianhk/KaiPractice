package me.drakeet.multitype;

/**
 * @author kai
 * @since 17/1/28
 */
public interface OnMultiTypeViewListener {

    void onMultiTypeValueChanged(BaseVO data, String action);

    void onMultiTypeViewClicked(BaseVO data, String action);
}
