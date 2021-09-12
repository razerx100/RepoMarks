package com.example.repoMarks;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetworkManagerSingleton {
    private static NetworkManagerSingleton s_instance;
    private RequestQueue m_requestQueue;

    private NetworkManagerSingleton(Context context) {
        if(m_requestQueue == null)
            m_requestQueue = Volley.newRequestQueue(context);
    }

    public <T> void AddToRequestQueue(Request<T> request) {
        m_requestQueue.add(request);
    }

    public static synchronized NetworkManagerSingleton Get() {
        return s_instance;
    }

    public static void Init(Context context) {
        if(s_instance == null)
            s_instance = new NetworkManagerSingleton(context);
    }
}
