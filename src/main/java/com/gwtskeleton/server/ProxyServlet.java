package com.gwtskeleton.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gwt.core.ext.TreeLogger;

public final class ProxyServlet extends HttpServlet {
	private static final long serialVersionUID = -3381881613341996415L;

	private static final String LOGGER_ATTRIBUTE_NAME = "com.google.gwt.dev.shell.logger";

	private static final String DEFAULT_PROTOCOL = "http";
	private static final int DEFAULT_HTTP_PORT = 80;
	private static final int DEFAULT_HTTPS_PORT = 443;

	private static final Pattern COOKIE_SECURE_ATTR = Pattern.compile(";\\s*Secure", Pattern.CASE_INSENSITIVE);

	private String protocol;
	private String hostname;
	private int port;

	@Override
	public void init() throws ServletException {
		super.init();

		protocol = getInitParameter("protocol");
		if (protocol == null) {
			protocol = DEFAULT_PROTOCOL;
		}
		assert ("http".equalsIgnoreCase(protocol) || "https".equalsIgnoreCase(protocol));

		boolean isHTTPS = false;
		if ("https".equalsIgnoreCase(protocol)) {
			isHTTPS = true;

			// @see
			// http://en.wikibooks.org/wiki/Programming:WebObjects/Web_Services/How_to_Trust_Any_SSL_Certificate
			try {
				SSLContext context = SSLContext.getInstance("SSL");
				context.init(null, new TrustManager[] { new FakeX509TrustManager() }, new SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
				HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
			} catch (GeneralSecurityException gse) {
				throw new ServletException(gse);
			}
		}

		hostname = getInitParameter("hostname");
		assert (hostname != null && hostname.length() > 0);

		String portStr = getInitParameter("port");
		if (portStr != null) {
			port = Integer.parseInt(portStr, 10);
		} else {
			port = isHTTPS ? DEFAULT_HTTPS_PORT : DEFAULT_HTTP_PORT;
		}
		assert (port > 0);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		StringBuffer requestUrl = req.getRequestURL();
		String requestQuery = req.getQueryString();
		if (requestQuery != null) {
			requestUrl.append('?').append(requestQuery);
		}
		URL translatedUrl = new URL(protocol, hostname, port, new URL(requestUrl.toString()).getFile());

		TreeLogger logger = getLogger().branch(TreeLogger.INFO, "Request to: " + translatedUrl, null);

		HttpURLConnection conn = (HttpURLConnection) translatedUrl.openConnection();
		conn.setAllowUserInteraction(false);
		conn.setInstanceFollowRedirects(false);

		String method = req.getMethod();
		conn.setRequestMethod(method);
		conn.setDoInput(true);

		String msg;
		String methodOverride = req.getHeader("X-HTTP-Method-Override");
		if (methodOverride != null && methodOverride.length() > 0) {
			msg = "Method: " + methodOverride + " (via " + method + " override)";
		} else {
			msg = "Method: " + method;
		}
		TreeLogger reqLogger = logger.branch(TreeLogger.INFO, msg, null);

		for (Enumeration<String> headerNames = req.getHeaderNames(); headerNames.hasMoreElements();) {
			String headerName = headerNames.nextElement();
			if (!"Host".equalsIgnoreCase(headerName)) {
				for (Enumeration<String> headerValues = req.getHeaders(headerName); headerValues.hasMoreElements();) {
					String headerValue = headerValues.nextElement();
					conn.addRequestProperty(headerName, headerValue);
					reqLogger.log(TreeLogger.INFO, headerName + ": " + headerValue, null);
				}
			}
		}
		if ("POST".equalsIgnoreCase(method)) {
			InputStream requestBody = req.getInputStream();
			if (requestBody != null) {
				conn.setDoOutput(true);
				logAndCopy(reqLogger, req.getContentType(), requestBody, conn.getOutputStream());
			} else {
				conn.setDoOutput(false);
			}
		} else {
			// HACK: GWT only sends GET or POST requests (because of a
			// limitation in Safari2's XMLHttpRequest)
			if (!"GET".equalsIgnoreCase(method)) {
				logger.log(TreeLogger.WARN, "Request method is neither GET nor POST", null);
			}
			conn.setDoOutput(false);
		}

		conn.connect();

		InputStream responseBody;
		try {
			responseBody = conn.getInputStream();
		} catch (Exception e) {
			responseBody = conn.getErrorStream();
		}

		TreeLogger resLogger = logger
				.branch(TreeLogger.INFO, "Response: " + conn.getResponseCode() + " " + conn.getResponseMessage(), null);
		res.setStatus(conn.getResponseCode());
		resLogger.log(TreeLogger.INFO, "Content-Type: " + conn.getContentType(), null);
		res.setContentType(conn.getContentType());
		String setCookie = conn.getHeaderField("Set-Cookie");
		if (setCookie != null) {
			res.setHeader("Set-Cookie", COOKIE_SECURE_ATTR.matcher(setCookie).replaceAll(""));
		}
		res.setHeader("Cache-Control", "no-cache");
		res.setHeader("Pragma", "no-cache");
		if (responseBody != null) {
			logAndCopy(resLogger, conn.getContentType(), responseBody, res.getOutputStream());
		}
	}

	private void logAndCopy(TreeLogger logger, String contentType, InputStream is, OutputStream os) throws IOException {
		if (contentType != null) {
			int semiColonPos = contentType.indexOf(';');
			if (semiColonPos >= 0) {
				contentType = contentType.substring(0, semiColonPos);
			}
			contentType = contentType.toLowerCase(Locale.ROOT).trim();

			if (contentType.equals("application/json")) {
				String json = copyAndGetAsString(is, os);
				logger.log(TreeLogger.INFO, prettifyJSON(logger, json), null);
			} else if (contentType.equals("application/x-www-form-urlencoded") || contentType.startsWith("text/")
					|| contentType.endsWith("/xml") || contentType.endsWith("+xml")) {
				logger.log(TreeLogger.INFO, copyAndGetAsString(is, os), null);
			} else {
				copy(is, os);
			}
		} else {
			copy(is, os);
		}
	}

	private void copy(InputStream is, OutputStream os) throws IOException {
		byte[] buffer = new byte[4096];
		for (int read = is.read(buffer); read > 0; read = is.read(buffer)) {
			os.write(buffer, 0, read);
		}
		is.close();
		os.close();
	}

	private String copyAndGetAsString(InputStream is, OutputStream os) throws IOException {
		ByteArrayOutputStream input = new ByteArrayOutputStream();
		copy(is, input);
		input.writeTo(os);
		os.close();
		return input.toString("utf-8");
	}

	private String prettifyJSON(TreeLogger logger, String json) {
		JSONTokener parser = new JSONTokener(json);
		try {
			Object obj = parser.nextValue();
			if (obj == null || obj.equals(null)) {
				return JSONObject.NULL.toString();
			} else if (obj instanceof JSONObject) {
				return ((JSONObject) obj).toString(2);
			} else if (obj instanceof JSONArray) {
				return ((JSONArray) obj).toString(2);
			} else if (obj instanceof Boolean) {
				return obj.toString();
			} else if (obj instanceof Number) {
				return JSONObject.numberToString((Number) obj);
			} else {
				return JSONObject.quote((String) obj);
			}
		} catch (JSONException e) {
			logger.log(TreeLogger.ERROR, null, e);
			return json;
		}
	}

	private TreeLogger getLogger() {
		return (TreeLogger) getServletContext().getAttribute(LOGGER_ATTRIBUTE_NAME);
	}

	private static class FakeX509TrustManager implements X509TrustManager {
		private static final X509Certificate[] ACCEPTED_ISSUERS = new X509Certificate[0];

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return ACCEPTED_ISSUERS;
		}
	}
}
