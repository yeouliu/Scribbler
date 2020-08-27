/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.json;

import com.google.gson.*;
import com.yeou.scribbler.model.transfer.ClientAction;

import java.lang.reflect.Type;

/**
 * Adapter class to convert {@link ClientAction} to JSON and back to Java enum.
 *
 * @author Yeou Liu
 */
public class ClientActionEnumAdapter implements JsonSerializer<ClientAction>, JsonDeserializer<ClientAction>
{
    @Override
    public JsonElement serialize(ClientAction src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getAction());
    }

    @Override
    public ClientAction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return ClientAction.getEnum(json.getAsString());
    }
}
