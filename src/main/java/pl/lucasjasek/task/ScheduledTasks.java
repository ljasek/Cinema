package pl.lucasjasek.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.lucasjasek.repositories.PasswordResetTokenRepository;
import pl.lucasjasek.repositories.VerificationTokenRepository;

import java.time.Instant;
import java.util.Date;

@Component
@Transactional
public class ScheduledTasks {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public ScheduledTasks(VerificationTokenRepository verificationTokenRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }


    @Scheduled(cron = "${cron.purge.expression}")
    public void purgeExpiredTokens() {

        Date now = Date.from(Instant.now());
        LOG.info("Purge expired tokens");

        passwordResetTokenRepository.deleteAllExpiredSince(now);
        verificationTokenRepository.deleteAllExpiredSince(now);
    }
}