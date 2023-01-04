package com.example.medbot.service;


import com.example.medbot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {


    final BotConfig botConfig;

    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
        List<BotCommand> listOfCommands = new ArrayList<BotCommand>();
        listOfCommands.add(new BotCommand("/start", "get a Welcome message"));
        listOfCommands.add(new BotCommand("/AgePatient", "get a Welcome message"));
    }


    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String messageText = update.getMessage().getText();

            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":

                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());

                    break;



                default:

                    int  inputAge  = Integer.parseInt(messageText);
                    int result = 2022 - inputAge;

                    sendMessage(chatId, result + "");

            }


        }


    }

    private void startCommandReceived(long chatId, String name) {
        String answer = "Привет, " + name + ", Введи возраст или год рождения";

        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {

        }
    }

    private void agePatient(long chatId, String inputMessage) {


      int  inputAge  = Integer.parseInt(inputMessage);
      int result = 2022 - inputAge;

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));

        message.setText(result + "");

    }
}
