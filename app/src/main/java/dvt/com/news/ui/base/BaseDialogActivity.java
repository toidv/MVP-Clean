package dvt.com.news.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;

import dvt.com.news.R;
import dvt.com.news.utils.AppUtils;

/**
 * Created by TOIDV on 5/19/2016.
 */
public abstract class BaseDialogActivity extends BaseActivity implements BaseMvpView {
    public MaterialDialog progressDialog, alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createAlertDialog();
        createProgressDialog();
        setupDialogTitle();
    }

    @Override
    public void createProgressDialog() {
        progressDialog = AppUtils.createProgress(this, getString(R.string.app_name));
    }

    @Override
    public void createAlertDialog() {
        alertDialog = AppUtils.createAlertDialog(this, getString(R.string.app_name));
    }

    protected abstract void setupDialogTitle();

    @Override
    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void showProgressDialog(boolean value) {
        if (value) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showAlertDialog(String errorMessage) {
        alertDialog.setContent(errorMessage);
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        dismissDialog();
        try {
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
