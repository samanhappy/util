package com.saman.util.httpclient;

import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.reactor.IOReactorException;
import org.junit.Test;

public class HttpClientTest {

	@Test
	public void testAsyncClient() throws IOReactorException, Exception {
		final HttpAsyncClient httpclient = new DefaultHttpAsyncClient();
		httpclient.start();
		HttpGet[] requests = new HttpGet[] {
				new HttpGet("http://www.apache.org/"),
				new HttpGet("http://www.baide.com/"),
				new HttpGet("http://www.qq.com/") };
		final CountDownLatch latch = new CountDownLatch(requests.length);
		try {
			for (final HttpGet request : requests) {
				httpclient.execute(request, new FutureCallback<HttpResponse>() {

					public void completed(final HttpResponse response) {
						latch.countDown();
						System.out.println(request.getRequestLine() + "->"
								+ response.getStatusLine());
					}

					public void failed(final Exception ex) {
						latch.countDown();
						ex.printStackTrace();
					}

					public void cancelled() {
						latch.countDown();
					}

				});
			}
			System.out.println("Doing...");
		} finally {
			latch.await();
			httpclient.shutdown();
		}
		System.out.println("Done");
	}

}
