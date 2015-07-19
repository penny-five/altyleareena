/*
 * Copyright 2015 Joonas Lehtonen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pennyfive.altyleareena.model.json;

import com.github.pennyfive.altyleareena.model.TextBundle;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

public class TextBundleDeserializer implements JsonDeserializer<TextBundle> {
    @Override
    public TextBundle deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        TreeMap<String, String> bundle = new TreeMap<>();
        for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
            bundle.put(entry.getKey(), entry.getValue().getAsString());
        }
        return new TextBundle(bundle);
    }
}
