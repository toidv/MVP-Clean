package dvt.com.news.data.remote;

import javax.inject.Inject;
import javax.inject.Singleton;

import dvt.com.news.data.HeadlinesDataSource;
import dvt.com.news.data.models.Headlines;
import io.reactivex.Flowable;


@Singleton
public class HeadlinesRemoteDataSource implements HeadlinesDataSource {
    private final RetrofitService retrofitService;

    @Inject
    public HeadlinesRemoteDataSource(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    @Override
    public Flowable<Headlines> getHeadlines() {
        return retrofitService.getHeadlines();
    }

    @Override
    public void save(Headlines headlines) {
        throw new UnsupportedOperationException();
    }
}
