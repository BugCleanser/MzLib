package mz.mzlib.util;

import com.google.gson.JsonObject;

public interface JsonUtil
{
    static Editor<JsonObject> addChild(JsonObject parent, String key)
    {
        return Editor.of(
            JsonObject::new, ThrowableBiConsumer.of(parent::add).bindFirst(ThrowableSupplier.constant(key)));
    }
}
