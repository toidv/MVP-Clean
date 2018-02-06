package dvt.com.news.utils;


import com.google.gson.stream.MalformedJsonException;

import java.lang.annotation.Annotation;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import dvt.com.news.R;
import dvt.com.news.data.models.ApiError;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Retrofit;

import static com.blankj.utilcode.util.NetworkUtils.isConnected;
import static com.blankj.utilcode.util.Utils.getContext;

/**
 * Created by steve on 9/6/17.
 */

public class ErrorUtils {
    public static final String GENERIC_ERROR = "General error, please try again later";

    public static ApiError getError(Throwable e, Retrofit retrofit) {
        ApiError error = new ApiError();
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            try {
                Converter<ResponseBody, ApiError> converter =
                        retrofit.responseBodyConverter(ApiError.class, new Annotation[0]);
                error = converter.convert(responseBody);
            } catch (MalformedJsonException e1) {
                error.setMessage(getContext().getString(R.string.fail_to_connect_to_server));
            } catch (Exception e2) {
                error.setMessage(GENERIC_ERROR);
            }
        } else if (e instanceof UnknownHostException) {
            if (isConnected()) {
                error.setMessage(getContext().getString(R.string.fail_to_connect_to_server));
            } else {
                error.setMessage(getContext().getString(R.string.no_internet_connections));
            }
        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException || e instanceof TimeoutException) {
            error.setMessage(getContext().getString(R.string.fail_to_connect_to_server));
        }
        return error;
    }


}
