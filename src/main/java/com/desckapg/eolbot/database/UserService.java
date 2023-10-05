package com.desckapg.eolbot.database;

import com.desckapg.eolbot.EOLBot;
import com.desckapg.eolbot.database.pojo.BotUser;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mongodb.client.model.Filters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class UserService {

    private static final int CLEANUP_DELAY = 10;

    private final Logger logger;

    private final MongoService mongoService;

    private final LoadingCache<Long, BotUser> users;

    public UserService() {
        this.logger = LogManager.getLogger("USER_REPOSITORY");
        this.mongoService = EOLBot.getMongoService();
        this.users = CacheBuilder.newBuilder()
                .expireAfterAccess(Duration.ofMinutes(CLEANUP_DELAY))
                .removalListener(notification -> {
                    save((Long) notification.getKey(), (BotUser) notification.getValue());
                })
                .build(new CacheLoader<>() {
                    @NotNull
                    @Override
                    public BotUser load(@NotNull Long tgID) {
                        return getOrCreateUser(tgID);
                    }
                });
        startCleanupTimer();
    }

    public BotUser getUser(Long tgID) {
        logger.info("Request for a user data with ID {}", tgID);
        BotUser user = null;
        try {
            user = users.get(tgID);
        } catch (ExecutionException e) {
            logger.error(() -> "Handled exception", e);
        }
        return user;
    }

    public CompletableFuture<BotUser> getUserAsync(Long tgID) {
        return CompletableFuture.supplyAsync(() -> getUser(tgID));
    }

    private void startCleanupTimer() {
        Timer clearTimer = new Timer();
        clearTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                users.cleanUp();
            }
        }, TimeUnit.MINUTES.toMillis(CLEANUP_DELAY), TimeUnit.MINUTES.toMillis(CLEANUP_DELAY));
    }

    private void save(Long tgID, BotUser user) {
        mongoService.getUsersCollection().replaceOne(Filters.eq("tdID", tgID), user);
        logger.info("Updated {} info on Mongo", tgID);
    }


    private BotUser getOrCreateUser(Long tgID) {
        BotUser user = mongoService.getUsersCollection()
                .find(Filters.eq("tgID", tgID)).first();
        if (user == null) {
            logger.info("Writing new user data with ID {}", tgID);
            user = BotUser.defaultData(tgID);
            mongoService.getUsersCollection().insertOne(user);
        }
        return user;
    }

}
