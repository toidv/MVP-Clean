package dvt.com.news.injection.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dvt.com.news.injection.ActivityContext;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

/**
 * Created by TOIDV on 4/4/2016.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    public Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context provideApplication() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeSubscription() {
        return new CompositeDisposable();
    }

    @Provides
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }
}
