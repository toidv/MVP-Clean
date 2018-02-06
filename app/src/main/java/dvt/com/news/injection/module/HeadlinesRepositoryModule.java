package dvt.com.news.injection.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dvt.com.news.data.HeadlinesDataSource;
import dvt.com.news.data.local.HeadlinesLocalDataSource;
import dvt.com.news.data.remote.HeadlinesRemoteDataSource;
import dvt.com.news.data.remote.RetrofitService;
import dvt.com.news.injection.Local;
import dvt.com.news.injection.Remote;


@Module
public class HeadlinesRepositoryModule {
    @Provides
    @Singleton
    @Local
    HeadlinesDataSource provideLocalHeadlinesDataSource() {
        return new HeadlinesLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    HeadlinesDataSource provideRemoteHeadlinesDataSource(RetrofitService retrofitService) {
        return new HeadlinesRemoteDataSource(retrofitService);
    }
}
