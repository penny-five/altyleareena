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

package com.github.pennyfive.altyleareena.model.api;

public class ProgramQueryOrderParams {
    public enum OrderBy {
        PLAYCOUNT("playcount");
        private final String value;

        OrderBy(String value) {
            this.value = value;
        }
    }

    public enum TimeWindow {
        SIX_HOURS("6h"),
        DAY("24h"),
        WEEK("week"),
        MONTH("month");

        private final String value;

        TimeWindow(String value) {
            this.value = value;
        }
    }

    public enum SortOrder {
        DESC("desc"),
        ASC("asc");

        private final String value;

        SortOrder(String value) {
            this.value = value;
        }
    }

    private OrderBy orderBy = OrderBy.PLAYCOUNT;
    private TimeWindow timeWindow = TimeWindow.WEEK;
    private SortOrder sortOrder = SortOrder.ASC;

    public ProgramQueryOrderParams setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public ProgramQueryOrderParams setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public ProgramQueryOrderParams setTimeWindow(TimeWindow timeWindow) {
        this.timeWindow = timeWindow;
        return this;
    }

    @Override
    public String toString() {
        return orderBy.value + "." + timeWindow.value + ":" + sortOrder.value;
    }
}
