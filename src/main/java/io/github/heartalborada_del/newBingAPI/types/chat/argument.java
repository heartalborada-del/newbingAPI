package io.github.heartalborada_del.newBingAPI.types.chat;

public class argument {
    private final String source = "cib";
    private final String[] optionsSets={"nlu_direct_response_filter", "deepleo", "disable_emoji_spoken_text", "responsible_ai_policy_235", "enablemm", "harmonyv3", "actions", "chatgpt", "wlthrottle", "cpcttl7d", "blocklistv2", "disbing", "dv3sugg"};
    private final String[] allowedMessageTypes={"Chat", "InternalSearchQuery", "InternalSearchResult", "Disengaged", "InternalLoaderMessage", "RenderCardRequest", "AdsQuery", "SemanticSerp", "GenerateContentQuery", "SearchQuery"};
    private final String[] sliceIds={"scfraithtr3", "scraith30", "linkimgincf", "308sahara", "228h3adss0", "h3adss0", "0310wlthrot", "cache0307", "ssoverlap50", "ssplon", "sssreduce", "sswebtop2", "302blocklist", "308disbing", "224locals0", "224locals0"};
    private final String traceId;
    private final boolean isStartOfSession;
    private final message message;
    private final String conversationSignature;
    private final participant participant;
    private final String conversationId;
    private final previousMessages[] previousMessages;
    /*Like this
    [{
			"text": "谢谢你! 知道你什么时候准备好继续前进总是很有帮助的。我现在能为你回答什么问题?",
			"author": "bot",
			"adaptiveCards": [],
			"suggestedResponses": [{
				"text": "世界上最小的哺乳动物是什么?",
				"contentOrigin": "DeepLeo",
				"messageType": "Suggestion",
				"messageId": "6d8e1634-813b-a801-c656-049a500bb1ff",
				"offense": "Unknown"
			}, {
				"text": "我想学习一项新技能",
				"contentOrigin": "DeepLeo",
				"messageType": "Suggestion",
				"messageId": "de18858d-a1de-9f23-f81f-da27ced0def5",
				"offense": "Unknown"
			}, {
				"text": "火烈鸟为何为粉色?",
				"contentOrigin": "DeepLeo",
				"messageType": "Suggestion",
				"messageId": "d6ba65f9-f2da-2751-f527-56e9fdcb9665",
				"offense": "Unknown"
			}],
			"messageId": "22d46a73-d4dd-37ac-5cf2-309c23021bf4",
			"messageType": "Chat"
		}]
     */
    public argument(String traceId, boolean isStartOfSession, io.github.heartalborada_del.newBingAPI.types.chat.message message, String conversationSignature, io.github.heartalborada_del.newBingAPI.types.chat.participant participant, String conversationId, io.github.heartalborada_del.newBingAPI.types.chat.previousMessages[] previousMessages) {
        this.traceId = traceId;
        this.isStartOfSession = isStartOfSession;
        this.message = message;
        this.conversationSignature = conversationSignature;
        this.participant = participant;
        this.conversationId = conversationId;
        this.previousMessages = previousMessages;
    }
}