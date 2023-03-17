package io.github.heartalborada_del.newBingAPI.types.chat;

public class Argument {
    private final String source = "cib";
    private final String[] optionsSets = {
            "nlu_direct_response_filter",
            "deepleo",
            "disable_emoji_spoken_text",
            "responsible_ai_policy_235",
            "enablemm",
            "galileo",
            "wlthrottle",
            "blocklistv2",
            "disbing",
            "dv3sugg"
    };
    private final String[] allowedMessageTypes = {
            "Chat",
            "InternalSearchQuery",
            "InternalSearchResult",
            "Disengaged",
            "InternalLoaderMessage",
            "RenderCardRequest",
            "AdsQuery",
            "SemanticSerp",
            "GenerateContentQuery",
            "SearchQuery"
    };
    private final String[] sliceIds = {
            "revdv3cf",
            "perfinstcf",
            "0310wlthrot",
            "302blocklist",
            "308disbing",
            "314glprompts0"
    };
    private final String traceId;
    private final boolean isStartOfSession;
    private final Message message;
    private final String conversationSignature;
    private final Participant participant;
    private final String conversationId;
    private final PreviousMessages[] previousMessages;

    public Argument(String traceId, boolean isStartOfSession, Message message, String conversationSignature, Participant participant, String conversationId, PreviousMessages[] previousMessages) {
        this.traceId = traceId;
        this.isStartOfSession = isStartOfSession;
        this.message = message;
        this.conversationSignature = conversationSignature;
        this.participant = participant;
        this.conversationId = conversationId;
        this.previousMessages = previousMessages;
    }
}
