package dvt.com.news;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.blankj.utilcode.util.Utils;

import dvt.com.news.injection.component.ApplicationComponent;
import dvt.com.news.injection.component.DaggerApplicationComponent;
import dvt.com.news.injection.module.ApplicationModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class NewsApplication extends Application {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private boolean isRecorded;
    private boolean isChecked;
    private boolean isWitnessed;
    private ApplicationComponent mApplicationComponent;

    public static NewsApplication get(Context context) {
        return (NewsApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Utils.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public synchronized ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }
}