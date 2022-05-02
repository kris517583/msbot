// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.sample.echo;

import com.microsoft.bot.builder.ActivityHandler;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.schema.*;
import com.microsoft.bot.schema.teams.O365ConnectorCard;
import com.microsoft.bot.schema.teams.O365ConnectorCardHttpPOST;
import com.microsoft.bot.schema.teams.O365ConnectorCardSection;
import com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
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
    	
    	/*log.info(turnContext.getActivity().toString());
    	log.info("Recipient name: " + turnContext.getActivity().getRecipient().getName());
    	log.info("Recipient ID: " + turnContext.getActivity().getRecipient().getId());
    	log.info("Recipient" + turnContext.getActivity().getRecipient().getProperties());
    	log.info("ChannelId : " + turnContext.getActivity().getChannelId());
    	log.info("Text : " + turnContext.getActivity().getText());
    	log.info("getServiceUrl : " + turnContext.getActivity().getServiceUrl());
    	log.info("getChannelData : " + turnContext.getActivity().getChannelData());
    	log.info("getDeliveryMode : " + turnContext.getActivity().getDeliveryMode());
    	log.info("getConversation : " + turnContext.getActivity().getConversation());
    	log.info("getType : " + turnContext.getActivity().getType());*/

	    System.out.println("Hello");
        Activity reply =MessageFactory.attachment(createAdaptiveCardAttachment(turnContext.getActivity().getFrom().getId()
		,turnContext.getActivity().getFrom().getName()));

        return turnContext.sendActivity(MessageFactory.text("Hi")).thenApply(sendResult -> null);

		//return sendIntroCard(turnContext).thenApply(result -> null);
    }

	private static Attachment createAdaptiveCardAttachment(String Id, String name) {
		try (
				InputStream inputStream = Thread.currentThread().
						getContextClassLoader().getResourceAsStream("Flight.json")
		) {
			String adaptiveCardJson = IOUtils
					.toString(inputStream, StandardCharsets.UTF_8.toString());

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("$schema","http://adaptivecards.io/schemas/adaptive-card.json");
			jsonObject.put("version","1.0");
			jsonObject.put("type","AdaptiveCard");
			jsonObject.put("speak","Query details");
			JSONArray jsonArray = new JSONArray();
			jsonArray.put(new JSONObject().put("type","TextBlock")
					.put("text","Hi <at>"+name+"</at>, \nI am Dolores! I handle queries for **SmartComms team**")
					.put("weight","Bolder"));
			jsonObject.put("body",jsonArray);
			jsonObject.put("msteams",new JSONObject().put("entities",new JSONArray().put(
					new JSONObject().put("type","mention").put("text","<at>Bot</at>")
							.put("mentioned",new JSONObject().put("id",Id)
									.put("name",name))
			)));

			Attachment attachment = new Attachment();
			attachment.setContentType("application/vnd.microsoft.card.adaptive");
			attachment.setContent(Serialization.jsonToTree(jsonObject.toString()));

			return attachment;

		} catch (IOException e) {
			e.printStackTrace();
			return new Attachment();
		}
	}

	private CompletableFuture<ResourceResponse> sendIntroCard(TurnContext turnContext) {
		HeroCard card = new HeroCard();
		card.setTitle("Welcome to Bot Framework!");
		card.setText(
				"Welcome to Welcome Users bot sample! This Introduction card "
						+ "is a great way to introduce your Bot to the user and suggest "
						+ "some things to get them started. We use this opportunity to "
						+ "recommend a few next steps for learning more creating and deploying bots."
		);

		CardImage image = new CardImage();
		image.setUrl("https://aka.ms/bf-welcome-card-image");

		card.setImages(Collections.singletonList(image));

		CardAction overviewAction = new CardAction();
		overviewAction.setType(ActionTypes.OPEN_URL);
		overviewAction.setTitle("Get an overview");
		overviewAction.setText("Get an overview");
		overviewAction.setDisplayText("Get an overview");
		overviewAction.setValue(
				"https://docs.microsoft.com/en-us/azure/bot-service/?view=azure-bot-service-4.0"
		);

		CardAction questionAction = new CardAction();
		questionAction.setType(ActionTypes.OPEN_URL);
		questionAction.setTitle("Ask a question");
		questionAction.setText("Ask a question");
		questionAction.setDisplayText("Ask a question");
		questionAction.setValue("https://stackoverflow.com/questions/tagged/botframework");

		CardAction deployAction = new CardAction();
		deployAction.setType(ActionTypes.OPEN_URL);
		deployAction.setTitle("Learn how to deploy");
		deployAction.setText("Learn how to deploy");
		deployAction.setDisplayText("Learn how to deploy");
		deployAction.setValue(
				"https://docs.microsoft.com/en-us/azure/bot-service/bot-builder-howto-deploy-azure?view=azure-bot-service-4.0"
		);
		card.setButtons(Arrays.asList(overviewAction, questionAction, deployAction));

		ThumbnailCard thumbnailCard = new ThumbnailCard();
		Activity response = MessageFactory.attachment(card.toAttachment());
		return turnContext.sendActivity(response);
	}

}
