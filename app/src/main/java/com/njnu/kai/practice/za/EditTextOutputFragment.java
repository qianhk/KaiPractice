package com.njnu.kai.practice.za;

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
