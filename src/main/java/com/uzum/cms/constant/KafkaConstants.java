package com.uzum.cms.constant;

public class KafkaConstants {
    public static final String USER_VALIDATE_TOPIC = "cards.emission.validate.user-id";
    public static final String USER_VALIDATE_GROUP = "cards.emission.validate.user-id.group";

    public static final String ACCOUNT_VALIDATE_TOPIC = "cards.emission.validate.account-id";
    public static final String ACCOUNT_VALIDATE_GROUP = "cards.emission.validate.account-id.group";

    public static final String CARD_CREATION_TOPIC = "cards.emission.creation";
    public static final String CARD_CREATION_GROUP = "cards.emission.creation.group";


    public static final String WEBHOOK_TOPIC = "cards.webhook";
    public static final String WEBHOOK_GROUP = "cards.webhook.group";

    public static final String TRUSTED_PACKAGE = "com.uzum.cms.dto.event";
}
