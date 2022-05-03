package com.microsoft.bot.sample.echo;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.ResourceResponse;
 

@Slf4j
@RestController
@RequestMapping(value = "/v3/conversations")
public class MockChannelController {
		

	@RequestMapping(value = "{conversationId}/activities/{activityId}",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"})
      public ResponseEntity<ResourceResponse> ReplyToActivity( @PathVariable("conversationId") String conversationId, @PathVariable("activityId") String activityId, @RequestBody Activity activity) {
         UUID uuid = UUID.randomUUID();
	     String id = uuid.toString();
	     log.info("IN  MOCK CONTROLLER - ReplyToActivity - " + activity);
	     log.info("*****************************************************");
	     log.info("ConversationId: "+ conversationId);
	     log.info("ActivityId: " + conversationId);
	     log.info("Message: "+ activity.getText());
 
	     log.info("activity: "+ getActvityToString(activity)); 
        return ResponseEntity.ok().body(new ResourceResponse(id));
	
	}
	
	public String getActvityToString(Activity activity) {
		StringBuilder sb = new StringBuilder("{");
		sb.append("action:"+activity.getAction()+";");
		sb.append(":"+activity.getCallerId()+";");
		sb.append(":"+activity.getChannelId()+";");
		sb.append(":"+activity.getDeliveryMode()+";");
		sb.append(":"+activity.getId()+";");
		sb.append(":"+activity.getImportance()+";");
		sb.append(":"+activity.getLabel()+";");
		sb.append(":"+activity.getLocale()+";");
		sb.append(":"+activity.getLocalTimezone()+";");
		sb.append(":"+activity.getName()+";");
		sb.append(":"+activity.getReplyToId()+";");
		sb.append(":"+activity.getServiceUrl()+";");
		sb.append(":"+activity.getSpeak()+";");
		sb.append(":"+activity.getSummary()+";");
		sb.append(":"+activity.getType()+";");
		sb.append(":"+activity.getText()+";");
		sb.append(":"+activity.getTopicName()+";");
		sb.append("{conversation:{id:"+activity.getConversation().getId()+"}};");
		sb.append("{recipient:{id:"+activity.getRecipient().getId()+"}};");	 
		sb.append("{from:{id:"+activity.getFrom().getId()+"}}};");	
	
		return sb.toString();
	}
	
	
}
