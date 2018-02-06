package dvt.com.news.ui.headlines;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dvt.com.news.R;
import dvt.com.news.data.models.Article;
import dvt.com.news.ui.base.BaseDialogFragment;
import dvt.com.news.ui.details.HeadlineDetailView;
import io.reactivex.disposables.CompositeDisposable;

public class TopHeadlinesView extends BaseDialogFragment implements TopHeadlinesMvpView {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    TopHeadlinesAdapter topHeadlinesAdapter;

    @Inject
    TopHeadlinesPresenter topHeadlinesPresenter;

    private CompositeDisposable compositeDisposable;

    public static TopHeadlinesView newInstance() {

        Bundle args = new Bundle();
        TopHeadlinesView fragment = new TopHeadlinesView();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_headlines_view, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        compositeDisposable = new CompositeDisposable();
        topHeadlinesPresenter.attachView(this);
        if(topHeadlinesAdapter == null) {
            topHeadlinesAdapter = new TopHeadlinesAdapter();
            topHeadlinesPresenter.getTopHeadlines();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(topHeadlinesAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        compositeDisposable.add(topHeadlinesAdapter.getOnClickItem().subscribe(article -> {
            TopHeadlinesView.this.replaceFragment(HeadlineDetailView.newInstance(article), R.id.container);
        }));
    }

    @Override
    protected void setupDialogTitle() {

    }

    @Override
    public void onDestroyView() {
        topHeadlinesPresenter.detachView();
        compositeDisposable.clear();
        super.onDestroyView();
    }

    @Override
    public void updateArticles(List<Article> articles) {
        topHeadlinesAdapter.setList(articles);
    }
}
