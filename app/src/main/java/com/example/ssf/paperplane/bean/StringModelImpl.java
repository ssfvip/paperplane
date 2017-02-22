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

package com.example.ssf.paperplane.bean;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ssf.paperplane.app.VolleySingleton;
import com.example.ssf.paperplane.interfaces.OnStringListener;

/**
 * Volley框架的解析类
 * Created by Lizhaotailang on 2016/9/15.
 */

public class StringModelImpl {

    private Context context;

    public StringModelImpl(Context context) {
        this.context = context;
    }

    /**
     * 传入URL，实现接口中的两个方法，
     * @param url
     * @param listener 监听回调   一个加载成功一个加载失败
     */
    public void load(String url, final OnStringListener listener) {
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                listener.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });

       // 添加StringRequest到RequestQueue对象中
        /**
         * Constructs a new retry policy.
         * @param initialTimeoutMs The initial timeout for the policy. 最初的超时策略。
         * @param maxNumRetries The maximum number of retries.重试的最大数量
         * @param backoffMultiplier Backoff multiplier for the policy. 补偿政策乘数
         */
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getVolleySingleton(context).addToRequestQueue(request);
    }

}
