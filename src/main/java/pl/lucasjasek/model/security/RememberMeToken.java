package pl.lucasjasek.model.security;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class RememberMeToken {

    @Id
    private String series;
    private String username;
    private String tokenValue;
    private Date lastUsed;

    public RememberMeToken() {
    }

    public RememberMeToken(String series, String username, String tokenValue, Date lastUsed) {
        this.series = series;
        this.username = username;
        this.tokenValue = tokenValue;
        this.lastUsed = lastUsed;
    }

    public String getSeries() {
        return series;
    }

    public String getUsername() {
        return username;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public Date getDate() {
        return lastUsed;
    }
}
