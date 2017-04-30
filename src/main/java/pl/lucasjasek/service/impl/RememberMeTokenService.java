package pl.lucasjasek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lucasjasek.model.security.RememberMeToken;
import pl.lucasjasek.repositories.RememberMeTokenRepository;

import java.util.Date;

@Service
@Transactional
public class RememberMeTokenService implements PersistentTokenRepository{

    private final RememberMeTokenRepository rememberMeTokenRepository;

    @Autowired
    public RememberMeTokenService(RememberMeTokenRepository rememberMeTokenRepository) {
        this.rememberMeTokenRepository = rememberMeTokenRepository;
    }


    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        rememberMeTokenRepository.save(new RememberMeToken(
                token.getSeries(),
                token.getUsername(),
                token.getTokenValue(),
                token.getDate()));
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        RememberMeToken rememberMeToken = rememberMeTokenRepository.findBySeries(series);
        rememberMeTokenRepository.save(new RememberMeToken(series, rememberMeToken.getUsername(), tokenValue, lastUsed));
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        RememberMeToken rememberMeToken = rememberMeTokenRepository.findBySeries(seriesId);

        return new PersistentRememberMeToken(
                rememberMeToken.getUsername(),
                rememberMeToken.getSeries(),
                rememberMeToken.getTokenValue(),
                rememberMeToken.getDate());
    }

    @Override
    public void removeUserTokens(String username) {
        RememberMeToken rememberMeToken = rememberMeTokenRepository.findByUsername(username);

        if(rememberMeToken != null){
            rememberMeTokenRepository.delete(rememberMeToken);
        }
    }
}
