package com.njnu.kai.practice.material.composite;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import com.njnu.kai.practice.R;
import com.njnu.kai.support.DisplayUtils;
import com.njnu.kai.support.SDKVersionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MaterialCompositeActivity extends Activity {

    private RecyclerView mRecyclerView;

    private MyAdapter myAdapter;

    ImageButton button;

    Context context;

    public static List<Actor> actors = new ArrayList<Actor>();

    private static String[] names = {"朱茵", "张柏芝", "张敏", "莫文蔚", "黄圣依", "赵薇", "如花"};

    private static String[] pics = {"p1", "p2", "p3", "p4", "p5", "p6", "p7"};

    private static String[] works = {"大话西游", "喜剧之王", "p3", "p4", "p5", "p6", "p7"};

    private static String[] role = {"紫霞仙子", "柳飘飘", "p3", "p4", "p5", "p6", "p7"};

    private static String[][] picGroups = {{"p1","p1_1", "p1_2", "p1_3"},{"p2","p2_1", "p2_2", "p2_3"},{"p3"},{"p4"},{"p5"},{"p6"},{"p7"}};

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set Explode enter transition animation for current activity
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
//        getWindow().setEnterTransition(new Explode().setDuration(3000));
        setContentView(R.layout.main_material_composite);

        // init data
        this.context = this;
        actors.clear();
        actors.add(new Actor(names[0], pics[0], works[0], role[0], picGroups[0]));
        setTitle("那些年我们追的星女郎");

        // init RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // set adapter
        myAdapter = new MyAdapter(this, actors);
        mRecyclerView.setAdapter(myAdapter);

        // set outline and listener for floating action button
        button = (ImageButton) this.findViewById(R.id.add_button);
        if (SDKVersionUtils.hasLollipop()) {
            button.setBackgroundResource(R.drawable.oval);
            button.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    int shapeSize = (int)getResources().getDimension(R.dimen.shape_size);
                    Rect rect = new Rect(0, 0, shapeSize, shapeSize);
                    int offset = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()) + 0.5f);
                    rect.offset(-offset, -offset);
                    outline.setRoundRect(rect, shapeSize / 2);
                }
            });
            button.setClipToOutline(true);
        }
        button.setOnClickListener(new MyOnClickListener());

    }

    public class MyOnClickListener implements View.OnClickListener {
        boolean isAdd = true;

        @Override
        public void onClick(View v) {

            if (SDKVersionUtils.hasLollipop()) {
                // start animation
                Animator animator = createAnimation(v);
                animator.start();
            }

            // add item
            if (myAdapter.getItemCount() != names.length && isAdd) {

                actors.add(new Actor(names[myAdapter.getItemCount()], pics[myAdapter.getItemCount()], works[myAdapter.getItemCount()], role[myAdapter.getItemCount()], picGroups[myAdapter.getItemCount()]));
                mRecyclerView.scrollToPosition(myAdapter.getItemCount() - 1);
                myAdapter.notifyDataSetChanged();
            }
            // delete item
            else {
                actors.remove(myAdapter.getItemCount() - 1);
                mRecyclerView.scrollToPosition(myAdapter.getItemCount() - 1);
                myAdapter.notifyDataSetChanged();
            }

            if (myAdapter.getItemCount() == 0) {
                button.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_input_add));
                isAdd = true;
            }
            if (myAdapter.getItemCount() == names.length) {
                button.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_delete));
                isAdd = false;
            }
        }
    }

    /**
     * start detail activity
     */
    public void startActivity(final View v, final int position) {

        View pic = v.findViewById(R.id.pic);
        View add_btn = this.findViewById(R.id.add_button);

        if (position == 3) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(v, DisplayUtils.dp2px(40), v.getHeight() / 2, 0, 0);
            Intent intent = new Intent(context, MaterialCompositeDetailActivity.class);
            intent.putExtra("pos", position);
            ActivityCompat.startActivity(this, intent, activityOptionsCompat.toBundle());
//        } else if (position == 4) {
//            boolean drawingCacheEnabled = add_btn.isDrawingCacheEnabled();
//            if (!drawingCacheEnabled) {
//                add_btn.setDrawingCacheEnabled(true);
//            }
//            add_btn.buildDrawingCache();
//            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(v, add_btn.getDrawingCache(), 0, 0);
//            Intent intent = new Intent(context, MaterialCompositeDetailActivity.class);
//            intent.putExtra("pos", position);
//            ActivityCompat.startActivity(this, intent, activityOptionsCompat.toBundle());
//            if (!drawingCacheEnabled) {
//                add_btn.setDrawingCacheEnabled(drawingCacheEnabled);
//            }
        } else {
            // set share element transition animation for current activity
//            Transition ts = new ChangeTransform();
//            ts.setDuration(3000);
//            getWindow().setExitTransition(ts);
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    Pair.create(pic, position + "pic"),
                    Pair.create(add_btn, "ShareBtn"));

            // start activity with share element transition
            Intent intent = new Intent(context, MaterialCompositeDetailActivity.class);
            intent.putExtra("pos", position);
            intent.putExtra("useTransition", true);
            ActivityCompat.startActivity(this, intent, activityOptionsCompat.toBundle());
        }

    }

    /**
     * create CircularReveal animation
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Animator createAnimation(View v) {
        // create a CircularReveal animation
        Animator animator = ViewAnimationUtils.createCircularReveal(
                v,
                v.getWidth() / 2,
                v.getHeight() / 2,
                0,
                v.getWidth());
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);
        return animator;
    }

}
