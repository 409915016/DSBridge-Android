package wendu.jsbdemo;

import android.os.CountDownTimer;
import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

import wendu.dsbridge.CompletionHandler;

/**
 * Created by du on 16/12/31.
 */

public class JsApi{
    @JavascriptInterface
    public String testSyn(Object msg)  {
        return msg + "［syn call］";
    }

    @JavascriptInterface
    public void testAsyn(Object msg, CompletionHandler<String> handler){
        handler.complete(msg+" [ asyn call]");
    }

    @JavascriptInterface
    public void created(Object msg, CompletionHandler<Boolean> handler) {
        handler.complete( true );
    }

    @JavascriptInterface
    public void updated(Object obj, CompletionHandler<Boolean> handler) throws JSONException{
        JSONObject jsonObject = (JSONObject) obj;

        String type = jsonObject.getString("type");
        String data = jsonObject.getString("data");
        switch (type) {
            case "config":
                Log.d("config", data );
                break;
            case "user":
                Log.d("user", data );
                break;
        }
        handler.complete( true );
    }

    @JavascriptInterface
    public void content(Object id, CompletionHandler<JSONObject> handler) throws JSONException {
        JSONObject content  = new JSONObject();
        JSONObject _content = new JSONObject();
        _content.put("id", 147022);
        _content.put("status", 4);
        content.put("content", _content);
        handler.complete(content);
    }

    @JavascriptInterface
    public void newContent(Object id, CompletionHandler<Boolean> handler) {
        String content_id = (String) id;
        /*
        open new content code in here
         */
        handler.complete( true );
    }

    @JavascriptInterface
    public void imageViewer(Object obj, CompletionHandler<Boolean> handler) throws JSONException {
        JSONObject images = (JSONObject) obj;

        String    current = images.getString("current");
        JSONArray data    = images.getJSONArray("data");
        Log.d("data", data.toString());
        /*
        open imageViewer
         */
        handler.complete( true );
    }

    @JavascriptInterface
    public void share(Object type, CompletionHandler<Boolean> handler) {
        String shareType = (String) type;
    /*
    share code in here
     */
        handler.complete( true );
    }

    @JavascriptInterface
    public String testNoArgSyn(Object arg) throws JSONException {
        return  "testNoArgSyn called [ syn call]";
    }

    @JavascriptInterface
    public void testNoArgAsyn(Object arg,CompletionHandler<String> handler) {
        handler.complete( "testNoArgAsyn   called [ asyn call]");
    }


    //@JavascriptInterface
    //without @JavascriptInterface annotation can't be called
    public String testNever(Object arg) throws JSONException {
        JSONObject jsonObject= (JSONObject) arg;
        return jsonObject.getString("msg") + "[ never call]";
    }

    @JavascriptInterface
    public void callProgress(Object args, final CompletionHandler<Integer> handler) {

        new CountDownTimer(11000, 1000) {
            int i=10;
            @Override
            public void onTick(long millisUntilFinished) {
                //setProgressData can be called many times util complete be called.
                handler.setProgressData((i--));

            }
            @Override
            public void onFinish() {
                //complete the js invocation with data; handler will be invalid when complete is called
                handler.complete(0);

            }
        }.start();
    }
}