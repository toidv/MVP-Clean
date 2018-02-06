package dvt.com.news.data.local;

import javax.inject.Inject;
import javax.inject.Singleton;

import dvt.com.news.data.HeadlinesDataSource;
import dvt.com.news.data.models.Headlines;
import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;


@Singleton
public class HeadlinesLocalDataSource implements HeadlinesDataSource {

    @Inject
    public HeadlinesLocalDataSource() {
    }

    @Override
    public Flowable<Headlines> getHeadlines() {
        try (Realm realm = Realm.getDefaultInstance()) {
            Headlines headlines = realm.where(Headlines.class).findFirst();
            if (headlines != null) {
                return Flowable.just(realm.copyFromRealm(headlines));
            }
            return Flowable.error(new Error("Headlines not found"));
        }
    }

    @Override
    public void save(Headlines headlines) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(realm1 -> {
                RealmResults<Headlines> all = realm1.where(Headlines.class).findAll();
                all.deleteAllFromRealm();
                realm1.copyToRealm(headlines);
            });
        }

    }
}
