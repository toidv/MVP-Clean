package dvt.com.news.ui.headlines;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dvt.com.news.R;
import dvt.com.news.data.models.Article;
import io.reactivex.subjects.PublishSubject;

public class TopHeadlinesAdapter extends RecyclerView.Adapter<TopHeadlinesAdapter.ViewHolder> {

    private List<Article> articles;
    private Context context;

    private PublishSubject<Article> onClickItem;

    public TopHeadlinesAdapter() {
        this.articles = new ArrayList<>();
        this.onClickItem = PublishSubject.create();
    }

    public void setList(List<Article> mList) {
        this.articles.clear();
        this.articles.addAll(mList);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_top_headlines, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articles.get(position);
        String urlToImage = article.getUrlToImage();
        if (!TextUtils.isEmpty(urlToImage)) {
            Glide.with(context).load(urlToImage).into(holder.imgArticle);
        } else {
            Glide.with(context).load(R.drawable.empty).into(holder.imgArticle);
        }
        holder.tvHeadlineTitle.setText(article.getTitle());
        holder.tvHeadlineDescription.setText(article.getDescription());
        holder.tvHeadlineTimestamp.setText(article.getPublishedAt());

    }

    public PublishSubject<Article> getOnClickItem() {
        return onClickItem;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_article)
        ImageView imgArticle;
        @BindView(R.id.tv_headline_title)
        AppCompatTextView tvHeadlineTitle;
        @BindView(R.id.tv_headline_description)
        AppCompatTextView tvHeadlineDescription;
        @BindView(R.id.tv_headline_timestamp)
        AppCompatTextView tvHeadlineTimestamp;
        @BindView(R.id.ln_item)
        LinearLayout lnItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ln_item)
        public void onArticleClicked() {
            Article article = articles.get(getAdapterPosition());
            onClickItem.onNext(article);
        }


    }
}
