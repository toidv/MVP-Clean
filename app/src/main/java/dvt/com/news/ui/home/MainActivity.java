package dvt.com.news.ui.home;

import android.os.Bundle;

import dvt.com.news.R;
import dvt.com.news.ui.base.BaseActivity;
import dvt.com.news.ui.headlines.TopHeadlinesView;

public class MainActivity extends BaseActivity {

    private TopHeadlinesView topHeadlinesView = TopHeadlinesView.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(topHeadlinesView, R.id.container);
    }

    @Override
    public void onBackPressed() {
        if (topHeadlinesView.isVisible()) {
            finishAffinity();
        }
        super.onBackPressed();
    }

    public String getCurrentTitle() {
        return getSupportActionBar().getTitle().toString();
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
