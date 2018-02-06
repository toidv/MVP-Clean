package dvt.com.news.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import dvt.com.news.data.HeadlinesRepository;
import dvt.com.news.data.remote.RetrofitService;
import dvt.com.news.executor.PostExecutionThread;
import dvt.com.news.executor.ThreadExecutor;
import dvt.com.news.injection.ApplicationContext;
import dvt.com.news.injection.module.ApplicationModule;
import dvt.com.news.injection.module.HeadlinesRepositoryModule;
import retrofit2.Retrofit;

/**
 * Created by TOIDV on 4/4/2016.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        HeadlinesRepositoryModule.class
})
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    Retrofit retrofit();

    RetrofitService inploiService();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    HeadlinesRepository healinesRepository();
}
