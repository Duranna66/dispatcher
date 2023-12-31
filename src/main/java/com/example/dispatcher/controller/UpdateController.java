package com.example.dispatcher.controller;

import com.example.dispatcher.utils.BotUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Log4j
public class UpdateController {
    private TelegramBot telegramBot;
    private BotUtils botUtils;
    @Autowired

    public UpdateController(BotUtils botUtils) {
        this.botUtils = botUtils;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }
    public void processUpdate (Update update) {
        if(update == null) {
            log.error("null");
            return;
        }
        if(update.getMessage() != null) {
            distributeMessageByType(update);
        }
        else {
            log.error("null");
        }
    }

    private void distributeMessageByType(Update update) {
        Message message = update.getMessage();
        if(message.getText() != null) {
            processTextMessage(update);
        }
        else if (message.getDocument() != null) {
            processDocumentMessage(update);
        }
        else if(message.getAudio() != null) {
            processAudioMessage(update);
        }
        else {
                telegramBot.sendMessage(botUtils.unSupportMessages(update, "unSupport"));
        }

    }

    private void processDocumentMessage(Update update) {
    }


    private void processAudioMessage(Update update) {
    }

    private void processTextMessage(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage = null;
        if(message.getText().equals("/start")) {
            sendMessage = botUtils.unSupportMessages(update, "Добро пожаловать, " +
                    message.getChat().getFirstName());
        }
        else {
            sendMessage = botUtils.unSupportMessages(update, "пока что нечего ответить");
        }
        telegramBot.sendMessage(sendMessage);
    }
}
