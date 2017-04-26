package pl.lucasjasek.registration;

import org.springframework.context.ApplicationEvent;
import pl.lucasjasek.model.User;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private String appUrl;
    private User user;

    public OnRegistrationCompleteEvent(User user, String appUrl) {
        super(user);

        this.user = user;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public User getUser() {
        return user;
    }
}