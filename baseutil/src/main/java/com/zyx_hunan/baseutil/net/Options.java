package com.zyx_hunan.baseutil.net;


public class Options<T> {
    public String url="";
    public int default_time=0;
    public Class<T> apiPath = null;

    public String getUrl() {
        return url;
    }

    public Options setUrl(String url) {
        this.url = url;
        return this;
    }

    public int getDefault_time() {
        return default_time;
    }

    public Options setDefault_time(int default_time) {
        this.default_time = default_time;
        return this;
    }

    public Options setApiPath(Class<T> apiPath) {
        this.apiPath = apiPath;
        return this;
    }
}
