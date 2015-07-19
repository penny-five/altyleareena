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

package com.github.pennyfive.altyleareena.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TextBundle implements Parcelable {
    private final TreeMap<String, String> bundle;

    public TextBundle(TreeMap<String, String> bundle) {
        this.bundle = bundle;
    }

    @SuppressWarnings("unchecked")
    protected TextBundle(Parcel in) {
        this.bundle = new TreeMap<>((Map<String, String>) in.readSerializable());
    }

    public String get(Language language) {
        return get(language, FallbackPolicy.RETURN_EMPTY);
    }

    public String get(Language language, FallbackPolicy policy) {
        String text = bundle.get(language.getCode());
        if (text != null) {
            return text;
        }
        return policy.applyTo(bundle, language);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeSerializable(bundle);
    }

    public static final Creator<TextBundle> CREATOR = new Creator<TextBundle>() {
        @Override
        public TextBundle createFromParcel(@NonNull Parcel in) {
            return new TextBundle(in);
        }

        @NonNull
        @Override
        public TextBundle[] newArray(int size) {
            return new TextBundle[size];
        }
    };

    public enum FallbackPolicy {
        RETURN_EMPTY {
            @Override
            String applyTo(NavigableMap<String, String> bundle, Language expected) {
                return "";
            }
        },
        RETURN_NULL {
            @Override
            String applyTo(NavigableMap<String, String> mapping, Language expected) {
                return null;
            }
        },
        RETURN_NEXT_AVAILABLE {
            @Override
            String applyTo(NavigableMap<String, String> bundle, Language expected) {
                Map.Entry<String, String> entry = bundle.higherEntry(expected.getCode());
                if (entry != null) {
                    return entry.getValue();
                }
                entry = bundle.firstEntry();
                return entry != null ? entry.getValue() : "";
            }
        };

        abstract String applyTo(NavigableMap<String, String> mapping, Language expected);
    }
}
