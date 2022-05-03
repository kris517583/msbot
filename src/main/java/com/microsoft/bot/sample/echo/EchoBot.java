// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.sample.echo;

import com.codepoetics.protonpack.collectors.CompletableFutures;
import com.microsoft.bot.builder.ActivityHandler;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.schema.ChannelAccount;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.ObjectWriter; 
/**
 * This class implements the functionality of the Bot.
 *
 * <p>
 * This is where application specific logic for interacting with the users would be added. For this
 * sample, the {@link #onMessageActivity(TurnContext)} echos the text back to the user. The {@link
 * #onMembersAdded(List, TurnContext)} will send a greeting to new conversation participants.
 * </p>
 */
@Slf4j
public class EchoBot extends ActivityHandler {

	
    @Override
    protected CompletableFuture<Void> onMessageActivity(TurnContext turnContext) {

    	/*log.info("Receipient name: " + turnContext.getActivity().getRecipient().getName());
    	log.info("Receipient ID: " + turnContext.getActivity().getRecipient().getId());
    	log.info("Receipient" + turnContext.getActivity().getRecipient().getProperties());
    	log.info("ChannelId : " + turnContext.getActivity().getChannelId());
    	log.info("Text : " + turnContext.getActivity().getText());
    	log.info("getServiceUrl : " + turnContext.getActivity().getServiceUrl());
    	log.info("getChannelData : " + turnContext.getActivity().getChannelData());
    	log.info("getDeliveryMode : " + turnContext.getActivity().getDeliveryMode());
    	log.info("getConversation : " + turnContext.getActivity().getConversation());
    	log.info("getType : " + turnContext.getActivity().getType());*/
    	 
    	
    	
        return turnContext.sendActivity(
            MessageFactory.text("Echo From Springboot: " + turnContext.getActivity().getText())
        ).thenApply(sendResult -> null);
    }

    @Override
    protected CompletableFuture<Void> onMembersAdded(
        List<ChannelAccount> membersAdded,
        TurnContext turnContext
    ) {
        String welcomeText = "Hello and welcome!";
        log.info("On onMembersAdded...");
        return membersAdded.stream()
            .filter(
                member -> !StringUtils
                    .equals(member.getId(), turnContext.getActivity().getRecipient().getId())
            ).map(channel -> turnContext.sendActivity(MessageFactory.text(welcomeText, welcomeText, null)))
            .collect(CompletableFutures.toFutureList()).thenApply(resourceResponses -> null);
    }
}
