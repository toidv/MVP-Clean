package dvt.com.news.injection.component;


import dagger.Component;
import dvt.com.news.injection.PerActivity;
import dvt.com.news.injection.module.ActivityModule;
import dvt.com.news.ui.details.HeadlineDetailView;
import dvt.com.news.ui.headlines.TopHeadlinesView;

/**
 * Created by TOIDV on 4/4/2016.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)

public interface ActivityComponent {

    void inject(TopHeadlinesView topHeadlinesView);

    void inject(HeadlineDetailView headlineDetailView);


}
