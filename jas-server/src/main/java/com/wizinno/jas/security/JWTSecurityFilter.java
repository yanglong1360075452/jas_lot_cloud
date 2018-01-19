package com.wizinno.jas.security;
import com.google.gson.Gson;
import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.exception.CustomExceptionCode;
import com.wizinno.jas.common.util.JwtUtil;
import com.wizinno.jas.user.domain.model.User;
import com.wizinno.jas.user.service.OperationLogService;
import com.wizinno.jas.user.service.UserService;
import com.wizinno.jas.user.service.dto.OperationLogDto;
import com.wizinno.jas.user.service.dto.UserDto;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by LiuMei on 2017-07-26.
 */
public class JWTSecurityFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(JWTSecurityFilter.class);
    private Gson gson = new Gson();

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private UserService userService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
         String method = httpRequest.getMethod();
        String requestURI = httpRequest.getServletPath();
        String scheme=httpRequest.getScheme();
        String servername=httpRequest.getServerName();
        String parameter=httpRequest.getQueryString();
        String ipAddress = httpRequest.getLocalAddr();
        String allUrl = scheme+"://"+ servername+ requestURI  +"?"+ parameter;
        //获取响应
        HttpServletResponse httpResponse=(HttpServletResponse)response;
        User user = null;
        OperationLogDto operationLogDto = new OperationLogDto();
        ResponseVO responseVO;
        operationLogDto.setCreatetime(new Date());
        operationLogDto.setEvent(requestURI);
        operationLogDto.setIpAddress(ipAddress);
        operationLogDto.setRequestAddress(allUrl);
        operationLogDto.setStatusCode(String.valueOf(httpResponse.getStatus()));
        if (requestURI.indexOf("/api/wechat/auth/user") == -1 && requestURI.indexOf("/api/wechat/register") == -1
                && !method.equals("OPTIONS") && requestURI.indexOf("/api/wechat/authCode") == -1 && requestURI.indexOf("api/wechat/ma/img") == -1) {
            String auth = httpRequest.getHeader("Authorization");
            if (auth == null) {
                responseVO = new ResponseVO(CustomExceptionCode.AuthFailed, "认证失败");
                String responseMessage="请求头信息为空";
                response(response, responseVO);
            } else {
                try {
                    Claims claims = jwtUtil.parseJWT(auth);
                    if (claims != null) {
                        String subject = claims.getSubject();
                        Gson gson = new Gson();
                        user = gson.fromJson(subject, User.class);
                        if(!"".equals(user.getOpenId()) && user.getOpenId() !=null){
                            UserDto userDto = new UserDto();
                            userDto = userService.getUserByOpenId(user.getOpenId());
                            if(null !=userDto){
                                operationLogDto.setUserId(userDto.getId());
                                operationLogDto.setUserName(userDto.getRealName());
                            }

                        }
                        Map info = gson.fromJson(subject, Map.class);
                        request.setAttribute("openId", info.get("openId"));
                        request.setAttribute("phone", info.get("phone"));
                    }
                } catch (Exception e) {
                    responseVO = new ResponseVO(CustomExceptionCode.AuthFailed, "认证失败");
                    response(response, responseVO);
                }
            }
        }
        ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, wrapper);
        byte[] result = wrapper.getResponseData();
        response.getOutputStream().write(result);
        operationLogDto.setReponseContent(new String(result,"UTF-8"));
        operationLogService.createLog(operationLogDto);
    }

    private void response(ServletResponse response, ResponseVO responseVO) {
        try {
            response.getOutputStream().write(gson.toJson(responseVO).toString().getBytes("UTF-8"));
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void destroy() {
    }
}
