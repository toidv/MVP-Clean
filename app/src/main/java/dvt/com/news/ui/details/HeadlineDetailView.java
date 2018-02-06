package dvt.com.news.ui.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dvt.com.news.R;
import dvt.com.news.data.models.Article;
import dvt.com.news.ui.base.BaseDialogFragment;
import dvt.com.news.ui.home.MainActivity;

public class HeadlineDetailView extends BaseDialogFragment implements HeadlineDetailMvpView {


    private static final String ARTICLE = "article";
    @Inject
    HeadlineDetailPresenter mHeadlineDetailPresenter;
    @BindView(R.id.img_article)
    ImageView imgArticle;
    @BindView(R.id.tv_headline_title)
    AppCompatTextView tvHeadlineTitle;
    @BindView(R.id.tv_headline_description)
    AppCompatTextView tvHeadlineDescription;
    @BindView(R.id.tv_headline_timestamp)
    AppCompatTextView tvHeadlineTimestamp;
    private Article article;
    private String backupTitle;

    public static HeadlineDetailView newInstance(Article article) {

        Bundle args = new Bundle();
        args.putParcelable(ARTICLE, article);
        HeadlineDetailView fragment = new HeadlineDetailView();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            article = getArguments().getParcelable(ARTICLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.headline_detail_view, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        mHeadlineDetailPresenter.attachView(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity) {
            backupTitle = ((MainActivity) activity).getCurrentTitle();
            ((MainActivity) activity).setTitle(article.getTitle());
        }
        setArticle(article);


    }

    private void setArticle(Article article) {
        String urlToImage = article.getUrlToImage();
        if (!TextUtils.isEmpty(urlToImage)) {
            Glide.with(this).load(urlToImage).into(imgArticle);
        } else {
            Glide.with(this).load(R.drawable.empty).into(imgArticle);
        }
        tvHeadlineTitle.setText(article.getTitle());
        tvHeadlineDescription.setText(article.getDescription());
        tvHeadlineTimestamp.setText(article.getPublishedAt());
    }

    @Override
    protected void setupDialogTitle() {

    }

    @Override
    public void onDestroyView() {
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).setTitle(backupTitle);
        }
        mHeadlineDetailPresenter.detachView();
        super.onDestroyView();
    }
}
