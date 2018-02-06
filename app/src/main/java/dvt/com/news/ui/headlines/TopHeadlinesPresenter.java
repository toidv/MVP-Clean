package dvt.com.news.ui.headlines;

import java.util.List;

import javax.inject.Inject;

import dvt.com.news.data.models.ApiError;
import dvt.com.news.data.models.Article;
import dvt.com.news.domain.TopHeadlinesUseCase;
import dvt.com.news.ui.base.BasePresenter;
import dvt.com.news.utils.AppUtils;
import dvt.com.news.utils.ErrorUtils;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Retrofit;

import static com.blankj.utilcode.util.Utils.getContext;

public class TopHeadlinesPresenter extends BasePresenter<TopHeadlinesMvpView> {
    private final TopHeadlinesUseCase topHeadlinesUseCase;
    private final Retrofit retrofit;

    @Inject
    public TopHeadlinesPresenter(TopHeadlinesUseCase topHeadlinesUseCase, Retrofit retrofit) {
        this.topHeadlinesUseCase = topHeadlinesUseCase;
        this.retrofit = retrofit;

    }

    public void getTopHeadlines() {
        topHeadlinesUseCase.execute(new DisposableSubscriber<List<Article>>() {
            @Override
            public void onNext(List<Article> articles) {
                getMvpView().updateArticles(articles);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                ApiError error = ErrorUtils.getError(t, retrofit);
                if(AppUtils.isConnectivityAvailable(getContext())) {
                    getMvpView().showAlertDialog(error.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        }, null);
    }


    @Override
    public void detachView() {
        topHeadlinesUseCase.dispose();
        super.detachView();
    }
}
