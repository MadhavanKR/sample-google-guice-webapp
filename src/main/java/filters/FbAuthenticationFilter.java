package filters;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.eclipse.jetty.http.HttpHeader;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FbAuthenticationFilter implements Filter {

    private final OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Authorizing user");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //fetch token
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String authHeader = httpServletRequest.getHeader(HttpHeader.AUTHORIZATION.asString());
            //check if bearer token is available
            if (authHeader == null || !authHeader.split(" ")[0].equalsIgnoreCase("bearer")) {
                response.setStatus(401);
                response.getOutputStream().print("{\"error\": \"unauthorized\"}");
            } else {
                String bearerToken = authHeader.split(" ")[1];
                String fbValidateTokenUrl = "https://graph.facebook.com/v3.0/me?fields=id,first_name,middle_name,last_name,name,email,verified,is_verified";
                Request fbUserDetailsGetRequest = new Request.Builder().addHeader(HttpHeader.AUTHORIZATION.asString(),
                        "Bearer " + bearerToken).url(fbValidateTokenUrl).get().build();
                String responseString = null;
                try {
                    Response fbUserDetailsResponse = okHttpClient.newCall(fbUserDetailsGetRequest).execute();
                    responseString = fbUserDetailsResponse.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    response.setStatus(401);
                    response.getOutputStream().print("{\"error\": \"" + e.getMessage() + "\" }");
                }
                try {
                    JSONObject fbUserDetailsResponseJson = new JSONObject(responseString);
                    String user = fbUserDetailsResponseJson.getString("name");
                    servletRequest.setAttribute("user", fbUserDetailsResponseJson.getString("name"));
                    filterChain.doFilter(servletRequest, servletResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("no name given, unauthorized");
                    response.setStatus(401);
                    response.getOutputStream().print("{\"error\": \""+ e.getMessage() +"\" }");
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
