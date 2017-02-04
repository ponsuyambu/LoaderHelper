package in.ponshere.loaderhelper.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.ponshere.loaderhelper.R;
import in.ponshere.loaderhelper.misc.ResultListener;


/**
 * @author Ponsuyambu
 * @since 4/2/17.
 */

public class FragmentB extends Fragment implements ResultListener {
    static Handler handler = new Handler();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_b,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getView().findViewById(R.id.btnStartFragmentC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TestTask(FragmentB.this,handler).execute();
//                getLoaderManager().initLoader(1000,null,FragmentA.this);
            }
        });
    }

    @Override
    public void onTaskCompleted(int id, Object result) {
        getFragmentManager().beginTransaction().replace(R.id.rlContainer,new FragmentA()).addToBackStack(null).commit();
    }

    @Override
    public void onError() {

    }

    static class TestTask extends AsyncTask<String,String,String>{

        ResultListener listener = null;
        Handler handler;

        public TestTask(ResultListener listener, Handler handler){
            this.listener = listener;
            this.handler = handler;
        }

        @Override
        protected String doInBackground(String... strings) {

            int i = 1;
            while ( i<5){
                Log.d("Loaders AsyncTask","i === "+i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i = i+1;

            }

            return "Welcome";
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onTaskCompleted(0,s);
                }
            });

        }
    }
}
