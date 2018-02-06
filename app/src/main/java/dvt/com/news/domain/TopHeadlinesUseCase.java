package dvt.com.news.domain;

import java.util.List;

import javax.inject.Inject;

import dvt.com.news.data.HeadlinesRepository;
import dvt.com.news.data.models.Article;
import dvt.com.news.executor.PostExecutionThread;
import dvt.com.news.executor.ThreadExecutor;
import io.reactivex.Flowable;

/**
 * Created by quanlt on 11/17/17.
 */

public class TopHeadlinesUseCase extends FlowableDelayErrorUseCase<List<Article>, Void> {
    private final HeadlinesRepository headlinesRepository;

    @Inject
    public TopHeadlinesUseCase(HeadlinesRepository headlinesRepository, ThreadExecutor threadExecutor,
                               PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.headlinesRepository = headlinesRepository;
    }

    @Override
    Flowable<List<Article>> buildUseCaseObservable(Void params) {
        //TODO implement build usecase observable
        return headlinesRepository.getHeadlines()
                .concatMap(headlines -> Flowable.just(headlines.getArticles()));
    }
}
