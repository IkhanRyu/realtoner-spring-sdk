package net.realtoner.web.social.facebook;

/**
 *
 * @author RyuIkHan
 */
public class FacebookUser {

    private String id = null;
    private String name = null;
    private String email = null;

    private String gender;

    public static final String MALE = "남성";
    public static final String FEMALE = "여성";

    public FacebookUser() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {

        return gender;
    }

    public boolean getBooleanGender() {

        return gender.equals("남성") || gender.equalsIgnoreCase("male");
    }
}
