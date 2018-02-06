package dvt.com.news.data.remote;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

import dvt.com.news.R;
import dvt.com.news.data.models.Headlines;
import io.reactivex.Flowable;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

//TODO change api
public interface RetrofitService {

    @GET("top-headlines")
    Flowable<Headlines> getHeadlines();


    class Creator {

        private static Interceptor cacheInterceptor;

        public static Retrofit newRetrofitInstance(final Context context,
                                                   final boolean isNetworkAvailable
        ) {

            cacheInterceptor = chain -> {
                Request request = chain.request();
                if (!isNetworkAvailable) {
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    request.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
                Response originalResponse = chain.proceed(request);
                return originalResponse;
            };
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            //Setup cache
            File httpCacheDirectory = new File(context.getCacheDir().getAbsolutePath(), "OKHttpCache");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(httpCacheDirectory, cacheSize);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addNetworkInterceptor(cacheInterceptor)
                    .cache(cache)
                    .addInterceptor(interceptor)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            HttpUrl url = request.url().newBuilder()
                                    .addQueryParameter("apiKey", "22430dee112d4d3f9e4e6a7f23e3fc76")
                                    .addQueryParameter("country", "us")
                                    .build();
                            request = request.newBuilder().url(url).build();
                            return chain.proceed(request);
                        }
                    })
                    .build();
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ")
                    .create();

            String ENDPOINT = context.getResources().getString(R.string.api_end_point);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                    .client(client)
                    .build();

            return retrofit;
        }
    }
}
