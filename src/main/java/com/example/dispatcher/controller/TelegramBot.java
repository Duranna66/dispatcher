package com.example.dispatcher.controller;

import com.example.dispatcher.utils.BotUtils;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Log4j
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private BotUtils botUtils;
    private static final Logger logger =  Logger.getLogger(TelegramBot.class);
    @Value("${bot_token}")
    private  String botToken;
    @Value("${bot_name}")
    private  String botName;

    @Override
    public String getBotToken() {
         return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String text = message.getText();
       logger.debug("Received message: " + text);
       UpdateController updateController = new UpdateController(botUtils);
       updateController.registerBot(this);
       updateController.processUpdate(update);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
    public void sendMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
