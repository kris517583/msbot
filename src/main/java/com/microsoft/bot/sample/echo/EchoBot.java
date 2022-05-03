// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.sample.echo;

import com.google.common.io.Resources;
import com.microsoft.bot.builder.ActivityHandler;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.schema.Attachment;
import com.microsoft.bot.schema.Serialization;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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


        Attachment attachment = new Attachment();
        attachment.setContentType("application/vnd.microsoft.card.adaptive");
        try {
            String card = readFile("Flight.json");
            attachment.setContent(Serialization.jsonToTree(card));
            return turnContext.sendActivity(
                    MessageFactory.attachment(attachment)
            ).thenApply(sendResult -> null);

        }  catch (Exception ex) {
            ex.printStackTrace();
            return turnContext.sendActivity(
                    MessageFactory.text("Echo From Springboot: " + turnContext.getActivity().getText())
            ).thenApply(sendResult -> null);
        }

    }

    protected String readFile(String fileName) throws IOException
    {
        URL file = Resources.getResource(fileName);
        return Resources.toString(file, Charset.defaultCharset());
    }
}
