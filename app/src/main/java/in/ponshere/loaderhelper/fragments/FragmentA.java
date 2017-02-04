package in.ponshere.loaderhelper.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.ponshere.loaderhelper.R;
import in.ponshere.loaderhelper.misc.BaseRequest;
import in.ponshere.loaderhelper.misc.BaseResponse;
import in.ponshere.loaderhelper.loaders.LoaderHelper;
import in.ponshere.loaderhelper.misc.ResultListener;


/**
 * https://medium.com/google-developers/making-loading-data-on-android-lifecycle-aware-897e12760832#.fgxj7kl6w
 *
 * @author Ponsuyambu
 * @since 4/2/17.
 */

public class FragmentA extends Fragment implements ResultListener {

    static Handler handler = new Handler();
    LoaderHelper loaderHelper = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loaderHelper = new LoaderHelper(getContext(),getLoaderManager(),this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_a,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getView().findViewById(R.id.btnStartFragmentB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaderHelper.doHttpsCall(1000,"url",new BaseRequest(), BaseResponse.class);
            }
        });
    }


    @Override
    public void onTaskCompleted(int id, Object result) {
        getFragmentManager().beginTransaction().replace(R.id.rlContainer,new FragmentB()).addToBackStack(null).commit();
    }

    @Override
    public void onError() {

    }
}
