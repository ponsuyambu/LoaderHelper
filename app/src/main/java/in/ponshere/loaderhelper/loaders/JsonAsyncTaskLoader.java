package in.ponshere.loaderhelper.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ponsuyambu
 * @since 4/2/17.
 */

public class JsonAsyncTaskLoader extends
        AsyncTaskLoader<List<String>> {

    private List<String> mData;



    public JsonAsyncTaskLoader(Context context) {
        super(context);
    }
    @Override
    protected void onStartLoading() {
        //This method always will be triggered when and all activity's onStart is triggered.
        Log.d("Loaders","onStartLoading -- "+getId());
        if (mData != null) {
            // Use cached data
            deliverResult(mData);
        } else {
            // We have no data, so kick off loading it
            forceLoad();
        }
    }
    @Override
    public List<String> loadInBackground() {
        Log.d("Loaders","loadInBackground -- "+getId());

        //Do a rest service here.

        int i = 1;
        while ( i<5){
            Log.d("Loaders","i === "+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i = i+1;

        }


        List<String> data = new ArrayList<>();
        data.add("PS");
        data.add("PS");

        // Parse the JSON using the library of your choice
        // Check isLoadInBackgroundCanceled() to cancel out early
        return data;
    }
    @Override
    public void deliverResult(List<String> data) {
        Log.d("Loaders","deliverResult -- "+getId());
        // We’ll save the data for later retrieval
        mData = data;
        // We can do any pre-processing we want here
        // Just remember this is on the UI thread so nothing lengthy!
        super.deliverResult(data);
        reset();
    }
}
