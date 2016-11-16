package com.njnu.kai.practice.za;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;
import com.njnu.kai.support.ViewUtils;

import org.threeten.bp.Instant;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16/5/15
 */
public class EditTextOutputFragment extends BaseTestFragment {

    private EditText mEdtResult;

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frament_edit_text_output, container, false);
        mEdtResult = ViewUtils.findEditTextById(view, R.id.edt_result);
        mEdtResult.setSingleLine(true);

        mEdtResult.setLines(10); //前面被设置过singleLine true后, 此处lines 10会不起作用
        mEdtResult.setSingleLine(false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Instant nowInstant = Instant.now();
        appendResult(nowInstant);
        ZonedDateTime nowZonedDateTime = ZonedDateTime.now();
        appendResult(nowZonedDateTime);
        appendResult(nowZonedDateTime.withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE_TIME));
        appendResult(nowZonedDateTime.plusDays(2).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        Resources resources = getResources();
        appendResult(resources.getQuantityString(R.plurals.plurals_test, 0));
        appendResult(resources.getQuantityString(R.plurals.plurals_test, 1));
        appendResult(resources.getQuantityString(R.plurals.plurals_test, 2));
        appendResult(resources.getQuantityString(R.plurals.plurals_test, 3));
        appendResult(resources.getQuantityString(R.plurals.plurals_test, 4));
        appendResult(resources.getQuantityString(R.plurals.plurals_test, 5));
        appendResult("xxxx");
        appendResult(resources.getQuantityString(R.plurals.plurals_test, 0, 0));
        appendResult(resources.getQuantityString(R.plurals.plurals_test, 1, 1));
        appendResult(resources.getQuantityString(R.plurals.plurals_test, 2, 2));
        appendResult(resources.getQuantityString(R.plurals.plurals_test, 3, 3));
        appendResult(resources.getQuantityString(R.plurals.plurals_test, 4, 4));
        appendResult(resources.getQuantityString(R.plurals.plurals_test, 5, 5));

        appendResult(resources.getQuantityString(R.plurals.buy_kindle, 1));
        appendResult(resources.getQuantityString(R.plurals.buy_kindle, 2));
    }

    private String transformToStr(Object object) {
        return object != null ? object.toString() : "<object=null>";
    }

    public void setResult(Object object) {
        mEdtResult.setText(transformToStr(object));
    }

    public void appendResult(Object object) {
        String oriStr = mEdtResult.getText().toString();
        mEdtResult.setText(oriStr + "\n" + transformToStr(object));
    }
}
