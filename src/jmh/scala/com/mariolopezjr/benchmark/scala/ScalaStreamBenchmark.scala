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

package com.mariolopezjr.benchmark.scala

import org.openjdk.jmh.annotations.{Benchmark, Scope, State}

/**
  * Created by mlopez on 2/26/17.
  */
@State(Scope.Benchmark)
class ScalaStreamBenchmark {

  val ValueToFind = 100000000

  @Benchmark
  def scalaInfiniteStreamFilterHead(): Int = {
    Stream.from(0).map(_ * 10).filter(_ == ValueToFind).head
  }

  @Benchmark
  def scalaInfiniteStreamFind(): Int = {
    Stream.from(0).map(_ * 10).find(_ == ValueToFind).get
  }

  @Benchmark
  def scalaStreamFilterHead(): Int = {
    (0 to Int.MaxValue).toStream.map(_ - 1).filter(_ == ValueToFind).head
  }

  @Benchmark
  def scalaStreamFind(): Int = {
    (0 to Int.MaxValue).toStream.map(_ - 1).find(_ == ValueToFind).get
  }
}
