package dvt.com.news.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dvt.com.news.data.remote.RetrofitService;
import dvt.com.news.executor.JobExecutor;
import dvt.com.news.executor.PostExecutionThread;
import dvt.com.news.executor.ThreadExecutor;
import dvt.com.news.executor.UIThread;
import dvt.com.news.injection.ApplicationContext;
import dvt.com.news.utils.AppUtils;
import retrofit2.Retrofit;

/**
 * Created by TOIDV on 4/4/2016.
 */

@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    RetrofitService provideInnovatubeService(Retrofit retrofit) {
        return retrofit.create(RetrofitService.class);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofitInstance() {
        return RetrofitService.Creator.newRetrofitInstance(mApplication.getApplicationContext(),
                AppUtils.isConnectivityAvailable(mApplication.getApplicationContext()));
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

}
