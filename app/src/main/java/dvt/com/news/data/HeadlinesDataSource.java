package dvt.com.news.data;

import dvt.com.news.data.models.Headlines;
import io.reactivex.Flowable;

public interface HeadlinesDataSource {

    Flowable<Headlines> getHeadlines();

    void save(Headlines headlines);
}
