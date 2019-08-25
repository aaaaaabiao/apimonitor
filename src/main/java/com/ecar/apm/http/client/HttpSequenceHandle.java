package com.ecar.apm.http.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.ecar.apm.context.BeanProvider;
import com.ecar.apm.task.HttprequestTask;
import com.google.common.collect.Lists;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecar.apm.model.HttpRequest;
import com.ecar.apm.model.HttpRequestLog;
import com.ecar.apm.model.HttpSequence;
import com.ecar.apm.model.HttpSequenceLog;
import com.github.pagehelper.StringUtil;

public class HttpSequenceHandle {

	protected static final Logger LOGGER = LoggerFactory.getLogger(HttpClientHandler.class);


	private HttpSequence httpSequence;

	public HashMap<String,String> variables = new HashMap<String,String>();
	
	public List<HttpRequestLog> httpRequestLogList = Collections.synchronizedList(new ArrayList<>());
	
	public HttpSequenceLog httpSequenceLog;


	public HttprequestTask task = BeanProvider.getBean(HttprequestTask.class);
	
	public HttpSequenceHandle(HttpSequence httpSequence) {
		this.httpSequence = httpSequence;
	}


	//cookie保存
	CookieStore cookieStore=new BasicCookieStore();
	
	public void execute(){
		 List<HttpRequest> requests = httpSequence.getHttpRequest();
		 for(HttpRequest request : requests){
			 HttpClientHandler handle = new HttpClientHandler(request,this);
			 HttpRequestLog requestLog = handle.execute();
			 requestLog.setPguid(request.getGuid());
			 requestLog.setPpguid(httpSequence.getGuid());
			 httpRequestLogList.add(requestLog);
		 }
		 handleLog();
	}


	public void execute(boolean isAsyn){
		if (isAsyn) {
			List<HttpRequest> requests = httpSequence.getHttpRequest();
			List<Future<String>> futures = Lists.newArrayList();
			for(HttpRequest request : requests){
				futures.add(task.doTask(this, request));
			}
			//等待异步任务执行完
			for (Future<String> future : futures) {
				try {
					future.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			handleLog();
		}else {
			execute();
		}

	}
	
	private void handleLog(){
		httpSequenceLog = new HttpSequenceLog();
		StringBuffer tempLog = new StringBuffer();
		long costTime = 0l;
		for(HttpRequestLog log : httpRequestLogList){
			costTime += log.getCostTime();
			if(StringUtil.isNotEmpty(log.getLog())){
				if(tempLog.length() != 0){
					tempLog.append("\r\n");
				}
				tempLog.append(log.getLog());
			}
		}
		httpSequenceLog.setPguid(httpSequence.getGuid());
		httpSequenceLog.setCostTime(costTime);
		httpSequenceLog.setLog(tempLog.toString());
		httpSequenceLog.setStatus(StringUtil.isEmpty(tempLog.toString()));
	}
	
	
	public void setVariables(String key, String value){
		variables.put(key, value);
	}
	
	public String getVariables(String val){
		for (String variableName : variables.keySet()) {
			if(variableName.equals(val)){
				return variables.get(variableName);
			}
		}
		return null;
	}
	
	public HttpSequence getHttpSequence() {
		return this.httpSequence;
	}
}
