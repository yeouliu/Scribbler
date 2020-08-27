/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yeou.scribbler.model.base.AbstractElement;
import com.yeou.scribbler.model.transfer.ClientAction;

/*
* Singleton instance of Gson {@link http://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html}.
* 
 * @author Yeou Liu 
*/
public class JsonConverter
{
    private static final JsonConverter INSTANCE = new JsonConverter();
    private Gson gson;

    private JsonConverter() {
        GsonBuilder gsonBilder = new GsonBuilder();
        gsonBilder.registerTypeAdapter(AbstractElement.class, new AbstractElementAdapter());
        gsonBilder.registerTypeAdapter(ClientAction.class, new ClientActionEnumAdapter());
        gsonBilder.serializeNulls();
        gson = gsonBilder.create();
    }

    public static Gson getGson() {
        return INSTANCE.gson;
    }
}
