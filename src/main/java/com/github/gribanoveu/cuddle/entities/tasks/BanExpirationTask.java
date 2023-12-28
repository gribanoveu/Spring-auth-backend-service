package com.github.gribanoveu.cuddle.entities.tasks;

import com.github.gribanoveu.cuddle.constants.Constants;
import com.github.gribanoveu.cuddle.constants.CronRules;
import com.github.gribanoveu.cuddle.entities.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Evgeny Gribanov
 * @version 22.12.2023
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BanExpirationTask {
    private final UserService userService;

    @Scheduled(cron = CronRules.EVERY_HOUR)
    public void mercyUser() {
        log.info("[ TASK ] USER UNBAN - STARTED: {}]", LocalDateTime.now().format(Constants.DEFAULT_TIME_FORMAT));
        var unbannedUsers = userService.removeExpiredBans();
        log.info("[ TASK ] USER UNBAN - ENDED: unbanned users: {}]", unbannedUsers);
    }

}
