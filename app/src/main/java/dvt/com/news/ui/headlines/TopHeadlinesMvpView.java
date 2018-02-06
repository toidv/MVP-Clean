package dvt.com.news.ui.headlines;

import java.util.List;

import dvt.com.news.data.models.Article;
import dvt.com.news.ui.base.BaseMvpView;

/**
 * Created by quanlt on 4/30/17.
 */

public interface TopHeadlinesMvpView extends BaseMvpView {

    void updateArticles(List<Article> articles);
}
