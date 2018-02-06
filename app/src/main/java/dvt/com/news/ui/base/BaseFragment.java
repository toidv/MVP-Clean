package dvt.com.news.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import dvt.com.news.NewsApplication;
import dvt.com.news.R;
import dvt.com.news.injection.component.ActivityComponent;
import dvt.com.news.injection.component.DaggerActivityComponent;


/**
 * Created by TOIDV on 5/19/2016.
 */
public class BaseFragment extends Fragment {

    public static final int IN_RIGHT_OUT_LEFT = 1;
    public static final int IN_LEFT_OUT_RIGHT = -1;
    public static final int WITHOUT_ANIMATION = 0;
    protected FragmentActivity activity;
    ActivityComponent mActivityComponent;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(NewsApplication.get(getActivity()).getComponent())
                    .build();
        }
        return mActivityComponent;
    }


    public void replaceFragment(Fragment fragment, int containerId) {
        replaceFragment(fragment, containerId, 0);
    }

    public void replaceFragment(Fragment fragment, int containerId, int customAnimations) {
        if (fragment != null) {
            String backStateName = fragment.getClass().getName();
            boolean fragmentPopped = getFragmentManager().popBackStackImmediate(backStateName, 0);
            if (!fragmentPopped) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                if (customAnimations == IN_RIGHT_OUT_LEFT) {
                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                } else if (customAnimations == IN_LEFT_OUT_RIGHT) {
                    transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                transaction.replace(containerId, fragment, fragment.getClass().getSimpleName());
                transaction.addToBackStack(fragment.getClass().getSimpleName()).commit();
            }
        }
    }
}
