/*
 * Copyright 2017 Mario Lopez Jr
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

package com.mariolopezjr.benchmark.java;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.stream.IntStream;

/**
 * Created by mlopez on 2/26/17.
 */
@State(Scope.Benchmark)
public class JavaStreamBenchmark {

    final int VALUE_TO_FIND = 100_000_000;

    @Benchmark
    public int javaInfiniteStreamFilterFindFirst() {
        return IntStream.iterate(0, i -> i + 1)
                .map(i -> i * 10)
                .filter(i -> i == VALUE_TO_FIND)
                .findFirst()
                .getAsInt();
    }

    @Benchmark
    public int javaStreamFilterFindFirst() {
        return IntStream.range(0, Integer.MAX_VALUE)
                .map(i -> i - 1)
                .filter(i -> i == VALUE_TO_FIND)
                .findFirst()
                .getAsInt();
    }

    @Benchmark
    public int javaStreamDistinctFilterFindFirst() {
        return IntStream.range(0, Integer.MAX_VALUE).distinct()
                .map(i -> i - 1)
                .filter(i -> i == VALUE_TO_FIND)
                .findFirst()
                .getAsInt();
    }
}
