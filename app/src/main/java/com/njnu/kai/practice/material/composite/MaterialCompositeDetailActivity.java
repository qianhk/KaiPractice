package com.njnu.kai.practice.material.composite;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.njnu.kai.practice.R;
import com.njnu.kai.support.SDKVersionUtils;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MaterialCompositeDetailActivity extends Activity {

    ImageView pic;

    int position;

    int picIndex = 0;

    Actor actor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set Explode enter transition animation for current activity
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
//        getWindow().setEnterTransition(new Explode().setDuration(500));
        position = getIntent().getIntExtra("pos", -1);

        if (getIntent().getBooleanExtra("useTransition", false) && SDKVersionUtils.hasLollipop()) {
            Transition transition;
            int yuNumber = position % 3;
            if (yuNumber == 0) {
                transition = new Explode();
            } else if (yuNumber == 1) {
                transition = new Fade();
            } else {
                transition = new Slide();
            }
            getWindow().setEnterTransition(transition.setDuration(500));
//        getWindow().setExitTransition(new Explode().setDuration(3000));
//        getWindow().setReturnTransition(new Explode().setDuration(3000));
        }
        setContentView(R.layout.detail_layout);

        actor = MaterialCompositeActivity.actors.get(position);
        pic = (ImageView) findViewById(R.id.detail_pic);

        TextView name = (TextView) findViewById(R.id.detail_name);
        TextView works = (TextView) findViewById(R.id.detail_works);
        TextView role = (TextView) findViewById(R.id.detail_role);
        ImageButton btn = (ImageButton) findViewById(R.id.detail_btn);
        if (SDKVersionUtils.hasLollipop()) {
            btn.setBackgroundResource(R.drawable.oval);
        }

        // set detail info
        ViewCompat.setTransitionName(pic, position + "pic");
        pic.setImageDrawable(getResources().getDrawable(actor.getImageResourceId(this)));
        name.setText("姓名：" + actor.name);
        works.setText("代表作：" + actor.works);
        role.setText("饰演：" + actor.role);
        // set action bar title
        setTitle(MaterialCompositeActivity.actors.get(position).name);

        // floating action button
        btn.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_gallery));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set first animation
                Animator animator = createAnimation(pic, true);
                animator.start();
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        picIndex++;
                        if (actor.getPics() != null) {
                            if (picIndex >= actor.getPics().length) {
                                picIndex = 0;
                            }
                            // set second animation
                            doSecondAnim();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });
    }

    /**
     * exec second animation for pic view
     */
    private void doSecondAnim() {
        pic.setImageDrawable(getResources().getDrawable(actor.getImageResourceId(this, actor.getPics()[picIndex])));
        Animator animator = createAnimation(pic, false);
        animator.start();
    }

    /**
     * create CircularReveal animation with first and second sequence
     */
    public Animator createAnimation(View v, Boolean isFirst) {

        Animator animator;

        if (isFirst) {
            animator = ViewAnimationUtils.createCircularReveal(
                    v,
                    v.getWidth() / 2,
                    v.getHeight() / 2,
                    v.getWidth(),
                    0);
        } else {
            animator = ViewAnimationUtils.createCircularReveal(
                    v,
                    v.getWidth() / 2,
                    v.getHeight() / 2,
                    0,
                    v.getWidth());
        }

        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(500);
        return animator;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pic.setImageDrawable(getResources().getDrawable(actor.getImageResourceId(this, actor.picName)));
        ActivityCompat.finishAfterTransition(this);
    }

}

