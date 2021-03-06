package unpsjb.wikiprecios.http;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;

/**
 * Controlador de consultas Http, se ocupa de realizar las consultas al servidor, pasa los parametros,
 * permite realizar consultar por GET y POST
 */
public class HttpHandler extends JsonHttpResponseHandler {
    // Tipo de consultas
    public static final int GET_REQUEST= 0;
    public static final int POST_REQUEST= 1;

    public static final int TIME = 3000;
    // Url a consultar
    private String baseUrl;
    // Clase que espera la respuesta de dicha consulta
    private HttpResponseHandler listener;
    // Parametros de la consulta
    private RequestParams requestParams;
    // Tipo de consulta a realizar = GET o POST
    private int requestMode;

    public HttpHandler(String baseUrl, int requestMode) {
        this.baseUrl = baseUrl;
        this.requestParams = new RequestParams();
        this.requestMode = requestMode;
    }

    public Boolean sendRequest() {
        Boolean request = true;
        Log.e("HttpHandler","sending request");
        try {

            AsyncHttpClient client = new AsyncHttpClient();
            client.setConnectTimeout(TIME);
            client.setTimeout(TIME);
            client.setResponseTimeout(TIME);

            Log.e("HTTP", client.getConnectTimeout()+" "+client.getResponseTimeout()+ " ");
            if(requestMode == GET_REQUEST){
                client.get(baseUrl, requestParams, this);
                Log.e("HttpHandler", "sending request GET");
            }else{
                client.post(baseUrl, requestParams, this);
                Log.e("HttpHandler", "sending request POST");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("HttpHandlerExcetion", e + "");
        }
        return request;
    }

    public void addParams(String key, String value) {
        requestParams.add(key, value);
        Log.e("HttpHandler","addParams : "+key +"  "+value);
    }

    public void setListener(HttpResponseHandler listener) {
        this.listener = listener;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        try {
            listener.onSuccess(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        Log.e("HttpHandler onFailure", "");
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        try {
            listener.onSuccess(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Log.e("HttpHandler Failure", responseString);
        super.onFailure(statusCode, headers, responseString, throwable);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        Log.e("HttpHandler Failure", statusCode +" "+throwable.toString());
        if(throwable instanceof ConnectTimeoutException || throwable instanceof ConnectException){
            listener.onFailure();
        }else {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            sendRequest();
        }
    }



}