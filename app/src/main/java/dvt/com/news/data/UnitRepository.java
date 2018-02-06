package dvt.com.news.data;

import android.app.job.JobScheduler;
import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import dvt.com.news.injection.ApplicationContext;
import dvt.com.news.injection.Local;
import dvt.com.news.injection.Remote;

/**
 * Created by TOIDV on 11/14/17.
 */
@Singleton
public class UnitRepository implements UnitDataSource {
    private final UnitDataSource unitLocalDataSource;
    private final UnitDataSource unitRemoteDataSource;
    private final JobScheduler jobScheduler;
    private final Context context;

    @Inject
    public UnitRepository(@Local UnitDataSource unitLocalDataSource,
                          @Remote UnitDataSource unitRemoteDataSource,
                          JobScheduler jobScheduler, @ApplicationContext Context context) {
        this.unitLocalDataSource = unitLocalDataSource;
        this.unitRemoteDataSource = unitRemoteDataSource;
        this.jobScheduler = jobScheduler;
        this.context = context;
    }
}