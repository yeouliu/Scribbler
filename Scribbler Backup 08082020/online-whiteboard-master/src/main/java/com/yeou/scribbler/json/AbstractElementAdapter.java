/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.json;

import com.google.gson.*;
import com.yeou.scribbler.model.base.AbstractElement;
import com.yeou.scribbler.model.transfer.TruncatedElement;

import java.lang.reflect.Type;

/**
 * Adapter class to convert any whiteboard elements to JSON and back to Java model.
 *
 * @author Yeou Liu
 */
public class AbstractElementAdapter implements JsonSerializer<AbstractElement>, JsonDeserializer<AbstractElement>
{
    @Override
    public JsonElement serialize(AbstractElement src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        JsonObject properties = context.serialize(src, src.getClass()).getAsJsonObject();

        if (src instanceof TruncatedElement) {
            result.add("type", new JsonPrimitive(((TruncatedElement) src).getClassName()));
            properties.remove("className");
        } else {
            result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        }

        result.add("properties", properties);

        return result;
    }

    @Override
    public AbstractElement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
            return context.deserialize(element, Class.forName("com.yeou.scribbler.model.element." + type));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }
}
