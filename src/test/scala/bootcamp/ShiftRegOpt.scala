package bootcamp

import chisel3._
import chisel3.util._
import chisel3.tester._
import chisel3.tester.RawTester.test
import chisel3.iotesters.PeekPokeTester

class ShiftRegOptTester(c: ShiftRegOpt) extends PeekPokeTester(c) {
  val inSeq = Seq(0, 1, 1, 1, 0, 1, 1, 0, 0, 1)
  var state = c.init
  var i = 0
  poke(c.io.en, true.B)
  while (i < 10 * c.n) {
    // poke in repeated inSeq
    val toPoke = inSeq(i % inSeq.length)
    poke(c.io.in, (toPoke != 0).B)
    // update expected state
    state = ((state * 2) + toPoke) & BigInt("1"*c.n, 2)

    step(1)
    expect(c.io.out, state.U)

    i += 1
  }
}

object ShiftRegOptTester extends App {
  for (i <- Seq(3, 4, 8, 24, 65)) {
    println(s"Testing n=$i")
    iotesters.Driver.execute(Array[String](), () => new ShiftRegOpt(n = i)) {
      c => new ShiftRegOptTester(c)
    }
  }
}
