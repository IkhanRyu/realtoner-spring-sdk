package net.realtoner.web.social.facebook;

import net.realtoner.http.HttpRequest;
import net.realtoner.http.HttpRequestBuilder;
import net.realtoner.http.HttpResponse;
import net.realtoner.utils.CodeUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

/**
 * @author RyuIkHan
 */
public class FacebookAPI {

    /*
    * information for facebook
    * */
    private String appId = null;
    private String appSecret = null;


    private String apiUrl = null;

    private Locale locale = Locale.KOREA;

    /*
    * Fields for API path
    * */
    private final String FRIEND_LIST = "/friends";

    /*
    * Fields for property
    * */
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String GENDER = "gender";

    /*
    * Fields for parameter name
    * */
    private static final String ACCESS_TOKEN = "access_token";
    private static final String APPSECRET_PROOF = "appsecret_proof";
    private static final String FIELDS = "fields";
    private static final String LOCALE = "locale";

    /*
    * Constants for friend api
    * */
    private static final int DEFAULT_LIMIT_OF_FRIEND_LIST = 25;

    private int limitOfFriendList = DEFAULT_LIMIT_OF_FRIEND_LIST;

    public FacebookAPI() {

    }

    public FacebookAPI(String appId, String appSecret, String apiUrl) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.apiUrl = apiUrl;
    }

    /*
    * normal getters and setters
    * */
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }


    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /*
    * operation for facebook
    * */

    /**
     *
     * */
    public FacebookUser getUserInfo(String accessToken , String userId) throws FacebookRequestException {
        HttpRequest request = getDefaultRequestBuilder(accessToken , apiUrl + userId, HttpRequest.Method.GET)
                .addParameter(FIELDS, "email,name,gender")
                .build();

        String content;

        try {

            HttpResponse response = request.process();
            content = response.getStringContent();

        } catch (IOException e) {

            return null;
        }

        JSONObject jsonObj = new JSONObject(content);

        FacebookUser facebookUser = new FacebookUser();

        if (jsonObj.has(ID)) {
            facebookUser.setId(jsonObj.getString(ID));
        } else {
            throw new FacebookRequestException(
                    "requested facebook user's information does not has \'" + ID + "\' field");
        }

        if (jsonObj.has(EMAIL)) {
            facebookUser.setEmail(jsonObj.getString(EMAIL));
        }

        if (jsonObj.has(NAME)) {
            facebookUser.setName(jsonObj.getString(NAME));
        } else {
            throw new FacebookRequestException(
                    "requested facebook user's information does not has \'" + NAME + "\' field");
        }

        if (jsonObj.has(GENDER)) {
            facebookUser.setGender(jsonObj.getString(GENDER));
        } else {
            throw new FacebookRequestException(
                    "requested facebook user's information does not has \'" + GENDER + "\' field");
        }

        return facebookUser;
    }

    public FacebookUser getOwnUserInfo(String accessToken) throws FacebookRequestException {

        HttpRequest request = getDefaultRequestBuilder(accessToken , apiUrl + "/me", HttpRequest.Method.GET)
                .addParameter(FIELDS, "email,name,gender")
                .build();


        String content;

        try {
            HttpResponse response = request.process();
            content = response.getStringContent();

        } catch (IOException e) {

            return null;
        }

        JSONObject jsonObj = new JSONObject(content);

        FacebookUser facebookUser = new FacebookUser();

        if (jsonObj.has(ID)) {
            facebookUser.setId(jsonObj.getString(ID));
        } else {
            throw new FacebookRequestException(
                    "requested facebook user's information does not has \'" + ID + "\' field");
        }

        if (jsonObj.has(EMAIL)) {
            facebookUser.setEmail(jsonObj.getString(EMAIL));
        }

        if (jsonObj.has(NAME)) {
            facebookUser.setName(jsonObj.getString(NAME));
        } else {
            throw new FacebookRequestException(
                    "requested facebook user's information does not has \'" + NAME + "\' field");
        }

        if (jsonObj.has(GENDER)) {
            facebookUser.setGender(jsonObj.getString(GENDER));
        } else {
            throw new FacebookRequestException(
                    "requested facebook user's information does not has \'" + GENDER + "\' field");
        }

        return facebookUser;
    }

    protected HttpRequestBuilder getRequestBuilder(String accessToken) {

        return HttpRequestBuilder.newInstance()
                .addParameter(ACCESS_TOKEN, accessToken)
                .addParameter(APPSECRET_PROOF,
                        CodeUtils.decodeBySHA256(appSecret, accessToken));
    }

    protected HttpRequestBuilder getDefaultRequestBuilder(String accessToken , String url, HttpRequest.Method method) {

        return getRequestBuilder(accessToken)
                .setUrl(url)
                .setMethod(method)
                .addParameter(LOCALE, locale.getLanguage().toLowerCase() + "_" + locale.getCountry().toLowerCase());
    }
}
