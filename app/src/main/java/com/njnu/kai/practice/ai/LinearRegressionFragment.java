package com.njnu.kai.practice.ai;

import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.TestFunction;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.util.Locale;

/**
 * @author kai
 * @since 2018/2/20
 */
public class LinearRegressionFragment extends BaseTestListFragment {

    private TensorFlowInferenceInterface mTensorFlowInterface;

    @Override
    public void onLoadFinished() {
        super.onLoadFinished();
        mTensorFlowInterface = new TensorFlowInferenceInterface(getActivity().getAssets(), "file:///android_asset/kai_linear_only_mul.pb");
    }

    @TestFunction("input 0")
    public void onTestPosition01() {
        tryTestInput(0);
    }

    @TestFunction("input 1")
    public void onTestPosition02() {
        tryTestInput(1);
    }

    @TestFunction("intput 2")
    public void onTestPosition03() {
        tryTestInput(2);
    }

    @TestFunction("input 100")
    public void onTestPosition04() {
        tryTestInput(100);
    }

    @TestFunction("input 3.5")
    public void onTestPosition05() {
        tryTestInput(3.5f);
    }

    private void tryTestInput(float input) {
        mTensorFlowInterface.feed("input", new float[]{input});
        mTensorFlowInterface.run(new String[]{"k", "b", "calcY"}, false);

        float[] result = new float[1];

        mTensorFlowInterface.fetch("calcY", result);
        float calcY = result[0];

        mTensorFlowInterface.fetch("k", result);
        float k = result[0];

        mTensorFlowInterface.fetch("b", result);
        float b = result[0];

        setResult(String.format(Locale.getDefault(), "y = %.4fx + %.2f \ninput = %.2f \nresult = %.4f", k, b, input, calcY));
    }
}
