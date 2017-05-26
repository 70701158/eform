package org.skynet.bi.framework.web;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.skynet.bi.framework.util.ConvertException;
import org.skynet.bi.framework.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 控制器基类
 * @author javadebug
 *
 */
public class BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 设置缓存控制策略
	 * @param response
	 * @param cacheControl
	 */
	protected void applyCacheControl(HttpServletResponse response, CacheControl cacheControl) {
		if (cacheControl != null) {
			long adddaysM = cacheControl.getMaxAge();
			String maxAgeDirective = "max-age=" + cacheControl.getMaxAge();
			response.setHeader("Cache-Control", maxAgeDirective);
			response.setStatus(HttpServletResponse.SC_OK);
			response.addDateHeader("Last-Modified", System.currentTimeMillis());
			response.addDateHeader("Expires", System.currentTimeMillis() + adddaysM);
		}
	}

	/**
	 * 返回成功的json
	 * @param response
	 * @param json
	 * @throws IOException
	 */
	public void success(HttpServletResponse response, String json) {
		success(response, json, (CacheControl) null);
	}

	/**
	 * 返回成功的json
	 * @param response
	 * @param json
	 * @throws IOException
	 */
	public void success(HttpServletResponse response, String json, CacheControl cacheControl) {
		try {
			applyCacheControl(response, cacheControl);
			response.setCharacterEncoding("utf-8");
			if (json != null) {
				// response.getOutputStream().write(json.getBytes());
				// response.getOutputStream().flush();
				// response.getOutputStream().close();
				response.getWriter().write(json);
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (IOException e) {
			failure(response);
		}
	}

	/**
	 * 返回成功的对象的json
	 * @param response
	 * @param obj
	 */
	public void success(HttpServletResponse response, Object obj) {
		success(response, obj, (Set<String>) null);
	}

	public void success(HttpServletResponse response, Object obj, CacheControl cacheControl) {
		success(response, obj, null, cacheControl);
	}

	/**
	 * 返回成功的json
	 * @param response
	 * @param obj
	 * @param ignoreFields 忽略的字段
	 */
	public void success(HttpServletResponse response, Object obj, Set<String> ignoreFields) {
		success(response, obj, ignoreFields, null);
	}

	public void success(HttpServletResponse response, Object obj, Set<String> ignoreFields, CacheControl cacheControl) {
		try {
			applyCacheControl(response, cacheControl);
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-type","Content-type: application/json");
			success(response, JSONUtil.toJson(obj, ignoreFields));
		} catch (ConvertException e) {
			logger.error(e.getMessage(), e);
			failure(response);
		}
	}

	/**
	 * 申明失败
	 * @param response
	 * @param msg
	 * @throws IOException
	 */
	public void failure(HttpServletResponse response, String msg) {
		failure(response, 500, msg);
	}

	/**
	 * 申明失败，返回失败的Json
	 * @param response
	 * @param statusCode
	 * @param msg
	 * @throws IOException
	 */
	public void failure(HttpServletResponse response, int statusCode, String msg) {
		failure(response, statusCode, null, msg);
	}

	/**
	 * 申明失败，返回失败的Json
	 * @param response
	 * @param statusCode
	 * @param errorCode
	 * @param msg
	 * @throws IOException
	 */
	public void failure(HttpServletResponse response, int statusCode, String errorCode, String msg) {
		response.setStatus(statusCode);
		response.setCharacterEncoding("utf-8");
		try {
			if (msg != null) {
				String json = JSONUtil.toJson(new FailureMessage(errorCode, msg));
				response.getOutputStream().write(json.getBytes("utf-8"));
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 返回失败的Json
	 * @param response
	 * @throws IOException
	 */
	public void failure(HttpServletResponse response) {
		failure(response, null);
	}
}
