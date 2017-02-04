package in.ponshere.loaderhelper.loaders;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.Toast;

import in.ponshere.loaderhelper.misc.BaseRequest;
import in.ponshere.loaderhelper.misc.ResultListener;

/**
 * @author Ponsuyambu
 * @since 4/2/17.
 */

public class LoaderHelper implements LoaderManager.LoaderCallbacks,ResultListener {

    Context context;
    LoaderManager loaderManager;
    Handler handler = new Handler(Looper.getMainLooper());
    ResultListener listener;

    public LoaderHelper(Context context, LoaderManager loaderManager, ResultListener listener) {
        this.context = context;
        this.loaderManager = loaderManager;
        this.listener = listener;
    }

    public void doHttpsCall(int id, String url, BaseRequest requestObject, Class responseClass){
        Bundle bundle = new Bundle();
        bundle.putSerializable("RESP_TYPE",responseClass);
        bundle.putSerializable("REQUEST",requestObject);
        bundle.putString("URL",url);
        loaderManager.initLoader(id,bundle,this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Log.d("Loaders","onCreateLoader");
        return new JsonAsyncTaskLoader(context);
    }

    @Override
    public void onLoadFinished(final Loader loader, final Object data) {
        loaderManager.destroyLoader(loader.getId());
        Log.d("Loaders","onLoadFinished");
        onTaskCompleted(loader.getId(),data);
        Toast.makeText(context, data.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoaderReset(Loader loader) {
        Log.d("Loaders","onLoaderReset");
    }
    @Override
    public void onTaskCompleted(final int id, final Object result) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onTaskCompleted(id,result);
            }
        });

    }

    @Override
    public void onError() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onError();
            }
        });
    }
}
