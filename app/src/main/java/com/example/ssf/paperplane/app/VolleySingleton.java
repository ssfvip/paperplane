
/*
 * Copyright 2017 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ssf.paperplane.app;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * VolleySingleton 单例模式
 * Created by Lizhaotailang on 2016/8/11.
 */

public class VolleySingleton {

    private static VolleySingleton volleySingleton;
    // 创建
    private RequestQueue requestQueue;

    private VolleySingleton(Context context){
        // 创建
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    /**
     * 获取实例
     * @param context
     * @return
     */
    public static synchronized VolleySingleton getVolleySingleton(Context context){
        if(volleySingleton == null){
            volleySingleton = new VolleySingleton(context);
        }
        return volleySingleton;
    }

    /**
     * 获取消息队列
     * @return
     */
    public RequestQueue getRequestQueue(){
        return this.requestQueue;
    }

    /**
     * 添加消息队列
     * @param req
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

}
