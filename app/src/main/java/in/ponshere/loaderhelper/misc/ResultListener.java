package in.ponshere.loaderhelper.misc;

/**
 * @author Ponsuyambu
 * @since 4/2/17.
 */

public interface ResultListener {

    public void onTaskCompleted(int id, Object result);
    public void onError();

}
