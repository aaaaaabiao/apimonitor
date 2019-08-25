package com.ecar.apm.task;

import com.ecar.apm.common.Constant;
import com.ecar.apm.http.client.HttpClientHandler;
import com.ecar.apm.http.client.HttpSequenceHandle;
import com.ecar.apm.model.HttpRequest;
import com.ecar.apm.model.HttpRequestLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class HttprequestTask {

    @Async
    public Future<String> doTask(HttpSequenceHandle handler, HttpRequest request){
        HttpClientHandler handle = new HttpClientHandler(request,handler);
        HttpRequestLog requestLog = handle.execute();
        requestLog.setPguid(request.getGuid());
        requestLog.setPpguid(handler.getHttpSequence().getGuid());
        handler.httpRequestLogList.add(requestLog);
        return new AsyncResult(Constant.SUCCESS);
    }
}
