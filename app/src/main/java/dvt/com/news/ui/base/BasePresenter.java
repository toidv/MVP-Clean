package dvt.com.news.ui.base;

import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

/**
 * Created by TOIDV on 4/4/2016.
 */
public class BasePresenter<V extends MvpView> implements Presenter<V> {
    private V mMvpView;

    @Override
    public void attachView(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new MvpViewNotAttachedException();
        }
    }

    protected void dispose(CompositeDisposable compositeSubscription) {
        if (compositeSubscription != null) {
            compositeSubscription.dispose();
        }
    }

    protected void closeRealm(Realm realm) {
        if (realm != null) {
            realm.close();
        }
    }

    public void onResume() {

    }

    public void onPause() {

    }

    private static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before requesting data to presenter");
        }
    }
}
