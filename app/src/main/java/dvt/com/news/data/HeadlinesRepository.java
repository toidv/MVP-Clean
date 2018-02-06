package dvt.com.news.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import dvt.com.news.data.models.Headlines;
import dvt.com.news.injection.Local;
import dvt.com.news.injection.Remote;
import io.reactivex.Flowable;


@Singleton
public class HeadlinesRepository implements HeadlinesDataSource {
    private final HeadlinesDataSource headlinesLocalDataSource;
    private final HeadlinesDataSource headlinesRemoteDataSource;

    @Inject
    public HeadlinesRepository(@Local HeadlinesDataSource headlinesLocalDataSource,
                               @Remote HeadlinesDataSource headlinesRemoteDataSource) {
        this.headlinesLocalDataSource = headlinesLocalDataSource;
        this.headlinesRemoteDataSource = headlinesRemoteDataSource;
    }

    @Override
    public Flowable<Headlines> getHeadlines() {
        return Flowable.mergeDelayError(headlinesRemoteDataSource.getHeadlines()
                .doOnNext(this::save), headlinesLocalDataSource.getHeadlines());
    }

    @Override
    public void save(Headlines headlines) {
        headlinesLocalDataSource.save(headlines);
    }
}
