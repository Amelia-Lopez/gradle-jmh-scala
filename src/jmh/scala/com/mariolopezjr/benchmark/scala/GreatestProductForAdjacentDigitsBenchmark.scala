package com.mariolopezjr.benchmark.scala

import org.openjdk.jmh.annotations.{Benchmark, Scope, Setup, State}

/**
  * Created by mlopez on 2/5/17.
  */
@State(Scope.Benchmark)
class GreatestProductForAdjacentDigitsBenchmark {
  import GreatestProductForAdjacentDigitsBenchmark._

  var numOfDigits: Int = _

  @Setup
  def setUp(): Unit = {
    numOfDigits = 4
  }

  @Benchmark
  def usingWhileAndForLoops(): Long = {
    val maxPos = Data.length - numOfDigits
    var curPos = 0
    var maxValPos = 0
    var curMaxVal = 0
    var curVal = 0
    while (curPos < maxPos) {
      curVal = 0

      for (i <- 0 until numOfDigits) {
        // do the math using addition since it'll accomplish the same thing using less memory
        Data(curPos + i) match {
          case 0 => curVal = -100
          case 1 => // do nothing
          case x => curVal += x
        }
      }

      if (curVal > curMaxVal) {
        curMaxVal = curVal
        maxValPos = curPos
      }

      curPos += 1
    }

    Data.slice(maxValPos, maxValPos + numOfDigits).foldLeft(1L)(_ * _)
  }

  @Benchmark
  def usingMapAndMaxBy(): Long = {
    val maxPos = Data.length - numOfDigits

    val maxValPos = (0 until maxPos).map { curPos =>
      // do the math using addition since it'll accomplish the same thing using less memory
      (productMagnitude((0 until numOfDigits).map(i => Data(curPos + i))), curPos)
    }.maxBy(_._1)._2

    Data.slice(maxValPos, maxValPos + numOfDigits).foldLeft(1L)(_ * _)
  }

  @Benchmark
  def usingReduce(): Long = {
    val maxPos = Data.length - numOfDigits

    // mapping the list of positions to a list of tuples to make the type checker happy
    val maxValPos = (0 until maxPos).map((0, _)).reduceLeft[(Int, Int)] { case ((maxValSoFarPos, maxValSoFar), (_, curPos)) =>
      // do the math using addition since it'll accomplish the same thing using less memory
      val curVal = productMagnitude((0 until numOfDigits).map(i => Data(curPos + i)))
      List((curPos, curVal), (maxValSoFarPos, maxValSoFar)).maxBy(_._2)
    }._1

    Data.slice(maxValPos, maxValPos + numOfDigits).foldLeft(1L)(_ * _)
  }

}

object GreatestProductForAdjacentDigitsBenchmark {
  val DataRaw: String = """73167176531330624919225119674426574742355349194934
                          |96983520312774506326239578318016984801869478851843
                          |85861560789112949495459501737958331952853208805511
                          |12540698747158523863050715693290963295227443043557
                          |66896648950445244523161731856403098711121722383113
                          |62229893423380308135336276614282806444486645238749
                          |30358907296290491560440772390713810515859307960866
                          |70172427121883998797908792274921901699720888093776
                          |65727333001053367881220235421809751254540594752243
                          |52584907711670556013604839586446706324415722155397
                          |53697817977846174064955149290862569321978468622482
                          |83972241375657056057490261407972968652414535100474
                          |82166370484403199890008895243450658541227588666881
                          |16427171479924442928230863465674813919123162824586
                          |17866458359124566529476545682848912883142607690042
                          |24219022671055626321111109370544217506941658960408
                          |07198403850962455444362981230987879927244284909188
                          |84580156166097919133875499200524063689912560717606
                          |05886116467109405077541002256983155200055935729725
                          |71636269561882670428252483600823257530420752963450""".stripMargin.replace("\n", "")
  val Data: Array[Int] = DataRaw.toArray.map(_.toString.toInt)

  def productMagnitude(xs: Seq[Int]): Int = xs.map {
    case 0 => -10000
    case 1 => 0
    case x => x
  }.sum
}