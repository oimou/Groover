package com.syfm.groover.data.network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.syfm.groover.data.storage.PlayDataDBController;

import org.json.JSONObject;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by lycoris on 2015/09/27.
 */
public class ApiClient {

    private PlayDataCallback playDataCallback;

    public void tryLogin(final String serial, final String pass, final LoginListener listener) {

        final String url = "https://mypage.groovecoaster.jp/sp/login/auth_con.php";
        final String serialNoKey = "nesicaCardId";
        final String passwordKey = "password";


        AppController.getInstance().addToRequestQueue(new MyStringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Unko", response.toString());
                        listener.onFailure();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Unko", error.networkResponse.toString());
                        listener.onSuccess();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(serialNoKey, serial);
                params.put(passwordKey, pass);
                return params;
            }

        });
    }

    /*                                      start of PlayData                                     */

    public void fetchAllPlayData(PlayDataCallback callback) {
        this.playDataCallback = callback;
        try {
            fetchPlayerData();
            fetchShopSalesData();
            fetchAverageScore();
            fetchStageData();
            playDataCallback.isSuccess(true);
        } catch (Exception e) {
            //エラー処理
            playDataCallback.isSuccess(false);
            Log.d("UnkoException", e.toString());
        }
    }

    public void fetchPlayerData() {
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                PlayDataDBController db = new PlayDataDBController();
                                db.insertToPlayerData(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("getPlayerDataError", error.toString());
                            }
                        })
        );
    }

    public void fetchShopSalesData() {
        String url = "https://mypage.groovecoaster.jp/sp/json/shop_sales_data.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("getShopDataResponse", response.toString());
                                PlayDataDBController db = new PlayDataDBController();
                                db.insertToShopSalesData(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("getShopDataError", error.toString());
                            }
                        })
        );
    }

    public void fetchAverageScore() {
        String url = "https://mypage.groovecoaster.jp/sp/json/average_score.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("getAverageScoreResponse", response.toString());
                                PlayDataDBController db = new PlayDataDBController();
                                db.insertToAverageScore(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("getAverageScoreError", error.toString());
                            }
                        })
        );
    }

    public void fetchStageData() {
        String url = "https://mypage.groovecoaster.jp/sp/json/stage_data.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("getAverageScoreResponse", response.toString());
                                PlayDataDBController db = new PlayDataDBController();
                                db.insertToStageData(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("stageDataError", error.toString());
                            }
                        })
        );
    }

    /*                                      end of PlayData                                       */

    public void getStageData() {
        String url = "https://mypage.groovecoaster.jp/sp/json/stage_data.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("getStageDataResponse", response.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("getStageDataError", error.toString());
                            }
                        })
        );
    }

    public void fetchMusicList() {
        String url = "https://mypage.groovecoaster.jp/sp/json/music_list.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("getMusicListResponse", response.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("getMusicListError", error.toString());
                            }
                        })
        );
    }

    public void fetchMusicDetail() {
        String url = "https://mypage.groovecoaster.jp/sp/json/music_detail.php";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("getMusicDetailResponse", response.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("getMusicDetailError", error.toString());
                            }
                        })
        );
    }

    public interface LoginListener extends EventListener {
        public void onSuccess();
        public void onFailure();
    }

    public interface PlayDataCallback {
        public void isSuccess(Boolean isSuccess);
    }

}
