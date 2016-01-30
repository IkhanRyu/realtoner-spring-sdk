package net.realtoner.web.controller;

import net.realtoner.web.rest.RestResponse;

import java.util.Locale;

/**
 *
 * @author RyuIkHan
 */
public abstract class BaseController {

    protected static final String INDEX = "index";

    private String viewPath = "";

    /**
     *
     * */
    protected String makeViewName(String viewName){

        String controllerName = viewPath.trim();

        if(controllerName.startsWith("/"))
            controllerName = controllerName.substring(1);

        viewName = viewName.trim();

        if(viewName.startsWith("/"))
            viewName = viewName.substring(1);

        return controllerName + "/" + viewName;
    }

    /**
     *
     */
    public void setViewPath(String viewPath){
        this.viewPath = viewPath;
    }

    /**
     *
     * */
    protected RestResponse createRestResponse(int responseCode , String responseMessage){
        return new RestResponse(responseCode , responseMessage);
    }

    protected abstract Locale getDefaultLocale();

    /**
     *
     * */
    protected String createLocaleCode(String localeStr){

        Locale locale;

        switch(localeStr.toLowerCase()){

            case "en":
            case "us":
                locale = Locale.US;

                break;

            case "kr":
            case "ko":
                locale = Locale.KOREA;

                break;

            default:
                locale = getDefaultLocale();
        }

        return createLocaleCodeFromLocale(locale);
    }

    /**
     *
     * */
    protected String createLocaleCodeFromLocale(Locale locale){
        return locale.getLanguage().toLowerCase() + "-" + locale.getCountry().toLowerCase();
    }
}
